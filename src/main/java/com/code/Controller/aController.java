package com.code.Controller;

import com.code.Entity.JsonResponse;
import com.code.Entity.PaperInfo;
import com.code.Entity.Task;
import com.code.Entity.User;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class aController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private TaskService taskService;


    @RequestMapping("")
    public String admin(){
               return "admin";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/newstudent")
    public String newstudent(){
        return "newstudent";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/newstudent")
    public String addnewstudent(@RequestParam("id") Integer id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password){
        if(userService.findUserById(id) != null){
            return "redirect:/admin/newstudent?error";
        }
        else {
            User user = new User();
            password = new BCryptPasswordEncoder().encode(password);

        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setUsername(String.valueOf(id));

        userService.insertUser(user);
        userService.insertSrole(id);

        org.gitlab4j.api.models.User gitUser = new org.gitlab4j.api.models.User();
        String email = id + "@pop.zjgsu.edu.cn";
        gitUser.setEmail(email);
        gitUser.setName(name);
        gitUser.setUsername(String.valueOf(id));

        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "iUtVKCxSA2sSpDwsjtTE");
        try {
            gitLabApi.getUserApi().createUser(gitUser,"123456789",10);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/newteacher")
    public String newteacher(){
        return "newteacher";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/newteacher")
    public String addnewteacher(@RequestParam("id") Integer id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("isAdmin") String isAdmin){
        User user = new User();
        password = new BCryptPasswordEncoder().encode(password);

        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setUsername(String.valueOf(id));

        userService.insertUser(user);
        userService.insertTrole(id);

        if (isAdmin.equals("1"))
        {
            userService.insertArole(id);
        }
        return "redirect:/admin";
    }

//    @PreAuthorize("hasRole('ROLE_TEACHER')")
//    @GetMapping("/aReview")
@RequestMapping(value = {"/areview"}, method = {RequestMethod.POST,RequestMethod.GET})
    public String areview(@ModelAttribute PaperInfo paperInfo, Model model){
        List<PaperInfo> list = paperInfoService.getAll();
        model.addAttribute("initdata",list);
        return "areview";
    }

//    @PreAuthorize("hasRole('ROLE_TEACHER')")
//    @ResponseBody
    @GetMapping("/data")
    public JsonResponse<PaperInfo> getData(Model model) {
        List<PaperInfo> list = paperInfoService.getAll();
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list);
        return response;
    }

    @ResponseBody
    @RequestMapping("/searchdata")
    public JsonResponse<PaperInfo> getSearchData() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String checkValue1 = request.getParameter("checkValue1");
        String checkValue2 = request.getParameter("checkValue2");
        String stuname = request.getParameter("stuname");
        String tutorname = request.getParameter("tutorname");

        /*if(checkValue1.equals("all1")&&checkValue2.equals("all2")&&stuname.trim().equals("")){ getData(); }*/
        /*else if(checkValue1.equals("all1")&&!checkValue2.equals("all2")&&!stuname.equals(null)){}*/

        if(!stuname.trim().equals(""))System.out.println(stuname);
        if(!tutorname.trim().equals(""))System.out.println(tutorname);

        System.out.println(checkValue1);System.out.println(checkValue2);

        List<PaperInfo> list1,list2,list3;
        list1 = (List<PaperInfo>) paperInfoService.findPaperInfoById(checkValue1);
        list2 = (List<PaperInfo>) paperInfoService.findPaperInfoByState(checkValue2);
        list3 = (List<PaperInfo>) paperInfoService.findPaperInfoByTaskAndState(checkValue1,checkValue2);
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list3);
        return response;
    }

    @PostMapping("/addrecord")
    public String addData(@RequestParam("txt_taskname") String taskname,
                                           @RequestParam("txt_stuname") String stuname,
                                           @RequestParam("txt_tutorname") String tutorname){

        List<PaperInfo> list = paperInfoService.findPaperInfoByMaxId();
        PaperInfo paperInfo = new PaperInfo();

        int newId = Integer.parseInt(list.get(0).getId().toString())+1;

        paperInfo.setId(Integer.toString(newId));
        paperInfo.setState("待评阅");
        paperInfo.setTaskname(taskname);
        paperInfo.setStuname(stuname);
        paperInfo.setTutorname(tutorname);

        paperInfoService.addRecord(paperInfo);

        return "redirect:/admin/areview";
    }

    @PostMapping("/delrecord")
    public String delData(@RequestParam("del_stuname") String stuname){
        paperInfoService.delRecord(stuname);
        return "redirect:/admin/areview";

    }

    @PostMapping("/editrecord")
    public String editData(@RequestParam("edit_stuname1") String stuname,
                           @RequestParam("edit_tutorname") String newtutorname,
                           @RequestParam("edit_state1") String newstate){
        paperInfoService.editRecord(stuname,newtutorname,newstate);
        return "redirect:/admin/areview";

    }
    /**
     * 管理员课题操作
     */
    /**
     *显示课题
     */
    @RequestMapping(value = {"/a_TaskShow"},method = {RequestMethod.POST,RequestMethod.GET})
        public String a_TaskShow(@ModelAttribute Task task,Model model){
        List<Task> list = taskService.getAll();
        model.addAttribute("initdata",list);
        return  "a_TaskShow";
    }
    @GetMapping("/taskdata")
    public JsonResponse<Task> getTaskData(Model model){
            List<Task> list = taskService.getAll();
            JsonResponse<Task> response = new JsonResponse<Task>(list);
            return response;
    }

    @GetMapping("/crossproposal")
    public String crossproposal(Model model){
        List<User> user = userService.findUsernameByRole(2);
        for(User users:user){
            System.out.println(users.getName());
        }
        List<PaperInfo> list = paperInfoService.getAlllunwen();
        model.addAttribute("initdata",list);
        model.addAttribute("teacher",user);
        return "crossproposal";
    }

    @PostMapping("/crossproposal")
    public String crosstutor(@RequestParam("tname") String tname,
                             @RequestParam("pid") String pid){
        System.out.println(tname +"------------------------"+ pid);
        return "redirect:/admin/crossproposal";

    }


    /***
     * 删除课题记录
     */
    @PostMapping("/deltask")
    public String delTaskData(@RequestParam("del_taskid")String taskid){
        taskService.delTask(taskid);
        System.out.println(taskid);
        return "redirect:/admin/a_TaskShow";
    }

//    @ResponseBody
//    @RequestMapping("/searchtaskdata")
//    public JsonResponse<Task> getSearchTaskData(){
//        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder
//         .getRequestAttributes()).getRequest();
//
//    }
    /**
     * 查询课题详细
     */
    @PostMapping("/checktask")
    public String checkTask(@RequestParam("edit_task")int  taskid){
        taskService.findTaskByTaskid(taskid);
        System.out.println(taskid);
        return "redirect:/admin/a_TaskShow";
    }
}
