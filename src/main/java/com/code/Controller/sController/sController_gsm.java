package com.code.Controller.sController;

import com.code.Entity.JsonResponse;
import com.code.Entity.Task;
import com.code.Entity.Task_s;
import com.code.Entity.User;
import com.code.Service.StorageService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class sController_gsm {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @Autowired
    private StorageService storageService;

    /**
     *学生选题显示
     */
    @RequestMapping(value = {"/stu_kadaiselect"},method = {RequestMethod.POST,RequestMethod.GET})
    public String s_TaskShow(@ModelAttribute Task task, Model model){
//        List<Task> list = taskService.getAll();
        String taskrate ="已通过";
        List<Task> list = taskService.findTaskByTaskstate(taskrate);
        System.out.println("111");
        model.addAttribute("initdata",list);
        return  "stu_kadaiselect";
    }
    @GetMapping("/taskdata")
    public JsonResponse<Task> get_sTaskData(Model model){
//        List<Task> list = taskService.getAll();已通过
        String taskstate ="已通过";
        List<Task> list = taskService.findTaskByTaskstate(taskstate);
        JsonResponse<Task> response = new JsonResponse<Task>(list);
        return response;
    }

    /**
     *学生MyTask显示
     */

    @PostMapping()
    @RequestMapping(value = {"/stu_ownkadai"},method = {RequestMethod.POST,RequestMethod.GET})
    public String s_TaskEdit(@ModelAttribute Task task, Model model,
                             Principal principal ){
        String stuid= principal.getName();

        int stu_id =new Integer(stuid).intValue();
        List<Task_s> s_list =taskService.findbystuid(stu_id);
        int task_id;
        if (s_list.isEmpty()){
            task_id=0;
        }
        else {
            task_id = s_list.get(0).getTaskid();
            System.out.println("课题id" + task_id);
        }

        List<Task> list = taskService.findTaskByTaskid(task_id);
        model.addAttribute("initdata",list);
        return  "stu_ownkadai";
    }

//    @GetMapping("/staskdata")
//    public JsonResponse<Task> get_mTaskData(Model model,Principal principal){
//
//        String stuid= principal.getName();
//
//        int stu_id =new Integer(stuid).intValue();
//        List<Task_s> s_list =taskService.findbystuid(stu_id);
//        int task_id =s_list.get(0).getTaskid();
//
//        List<Task> list = taskService.findTaskByTaskid(task_id);
//        JsonResponse<Task> response = new JsonResponse<Task>(list);
//        return response;
//    }

    /**
     * 选题addtomy
     */
    @PostMapping("/addtomy")
    public String addtomyTask(@RequestParam("addtomy_taskid") String taskid,
                              @RequestParam("addtomy_taskname") String task_name,
                              Principal principal){
        String stuid= principal.getName();
        int stu_id =new Integer(stuid).intValue();
        int task_id=new Integer(taskid).intValue();
        User user =userService.findUserById(stu_id);
        String stu_name =user.getName();
        System.out.println("学生:"+stu_id+"\n姓名:"+stu_name+"\n课题id:"+task_id+"\n课题名称:"+task_name);
        taskService.chooseTask(stu_id,stu_name,task_id,task_name);

        return "redirect:/student/stu_ownkadai";
    }

    /**
     *学生取消选题
     */
    @PostMapping("/del_MyTask")
    public  String del_MyTaskData(@RequestParam("del_taskid")String task_id,
                                  Principal principal){

        String stuid= principal.getName();
        int stu_id =new Integer(stuid);
        taskService.s_delMyTask(task_id,stu_id);
        System.out.println("取消选课的课程标号"+task_id);
        return "redirect:/student/stu_ownkadai";
    }


    /**
     * 查询课题findtask
     */
    @RequestMapping(value = {"/s_FindTask"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String s_FindTask(@ModelAttribute Task task, Model model,
                             @RequestParam("tasktypeSelection") String task_type,
                             Principal principle) {
        int tutor_id = new Integer(principle.getName()).intValue();
        List<Task> list;

        if (task_type.equals("全部")){
            String taskstate ="已通过";
            list = taskService.findTaskByTaskstate(taskstate);
            System.out.println(list);
            model.addAttribute("tasktypeSelection_D1",task_type);
        }else if (task_type.equals("系统设计")){
            list =taskService.s_findTask(task_type);
            model.addAttribute("tasktypeSelection_D2",task_type);
        }else if(task_type.equals("算法设计")){
            list = taskService.s_findTask(task_type);
            model.addAttribute("tasktypeSelection_D3",task_type);
        }else{
            list = taskService.s_findTask(task_type);
            model.addAttribute("tasktypeSelection_D4",task_type);
        }
        model.addAttribute("initdata", list);
        return "stu_kadaiselect";
    }


}
