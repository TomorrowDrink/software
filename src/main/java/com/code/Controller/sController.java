package com.code.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.code.Entity.JsonResponse;
import com.code.Entity.Task;
import com.code.Entity.Task_s;
import com.code.Entity.User;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngine;
import javax.servlet.http.HttpSessionEvent;
import java.security.Principal;
import java.util.List;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class sController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;



    @RequestMapping("")
    public String student(){
              return "student";
    }

//    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/password")
    public String password(){
        return "changepassword";
    }

//    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/password")
    public String uppassword(@RequestParam("pwd") String pwd,
                             @RequestParam("newpwd") String newpwd,
                             @RequestParam("renewpwd") String renewpwd,
                             Principal principal){
        if(newpwd.equals(renewpwd)) {
            String username = principal.getName();
            User user = userService.findUserByUsername(username);
            boolean flag = new BCryptPasswordEncoder().matches(pwd,user.getPassword());
            System.out.println(flag);
            if (flag) {

                newpwd = new BCryptPasswordEncoder().encode(newpwd);
                userService.updatePwd(user.getPassword(), newpwd);
                return "redirect:/student";
            } else {
                return "redirect:/student/password?error1";
            }
        }
        else {
            return "redirect:/student/password?error2";
        }
    }

    /**
     *学生课题显示
     */
    @RequestMapping(value = {"/s_TaskShow"},method = {RequestMethod.POST,RequestMethod.GET})
    public String s_TaskShow(@ModelAttribute Task task, Model model){
//        List<Task> list = taskService.getAll();
        String taskrate ="已通过";
        List<Task> list = taskService.findTaskByTaskstate(taskrate);
        System.out.println("111");
        model.addAttribute("initdata",list);
        return  "s_TaskShow";
    }
    @GetMapping("/taskdata")
    public JsonResponse<Task> get_sTaskData(Model model){
//        List<Task> list = taskService.getAll();已通过
        String taskrate ="已通过";
        List<Task> list = taskService.findTaskByTaskstate(taskrate);
        JsonResponse<Task> response = new JsonResponse<Task>(list);
        return response;
    }

//    @PostMapping()
//    @RequestMapping(value = {"/s_TaskEdit"},method = {RequestMethod.POST,RequestMethod.GET})
//    public String s_TaskEdit(@ModelAttribute Task task, Model model,
//                            Principal principal ){
//
//        String stuid= principal.getName();
//        int stu_id =new Integer(stuid).intValue();
//        List<Task_s> s_list =taskService.findbystuid(stu_id);
//        System.out.println(s_list);
//        Task_s s = s_list.get(1);
//        int id =s.getTaskid();
//        System.out.println(id);
//
//        int task_id =2;
//        List<Task> list = taskService.findTaskByTaskid(task_id);
//        model.addAttribute("initdata",list);
//        return  "s_TaskEdit";
//    }
//    @GetMapping("/staskdata")
//    public JsonResponse<Task> get_mTaskData(Model model,Principal principal){
//
//        String stuid= principal.getName();
//        int stu_id =new Integer(stuid).intValue();
//        List<Task_s> s_list =taskService.findbystuid(stu_id);
//        System.out.println(s_list);
//        Task_s s = s_list.get(0);
//        int id =s.getTaskid();
//        System.out.println(id);
//        int task_id =2;
//        List<Task> list = taskService.findTaskByTaskid(task_id);
//        JsonResponse<Task> response = new JsonResponse<Task>(list);
//        return response;
//    }
//





    /**
     * 选课
     */
    @PostMapping("/addtomy")
    public String addtomyTask(@RequestParam("addtomy_taskid") String taskid,
                              @RequestParam("addtomy_taskname") String task_name,
                              Principal principal){
        String stuid= principal.getName();
        int stu_id =new Integer(stuid).intValue();
        int task_id=new Integer(taskid).intValue();
        System.out.println(stu_id);

        User user =userService.findUserById(stu_id);
        String stu_name =user.getName();
        taskService.chooseTask(stu_id,stu_name,task_id,task_name);

        return "redirect:/student/s_TaskShow";
    }
    /**
     * 查询课题
     */
    @PostMapping("/findtask")
    public String findTask(@RequestParam("find_tasktype")String tasktype){
        taskService.findTaskByTasktype(tasktype);
        return "redirect:/student/s_TaskShow";
    }






}
