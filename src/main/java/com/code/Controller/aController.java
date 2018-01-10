package com.code.Controller;

import com.code.Entity.JsonResponse;
import com.code.Entity.PaperInfo;
import com.code.Entity.Task;
import com.code.Entity.User;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
/*import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;*/
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

     /*   org.gitlab4j.api.models.User gitUser = new org.gitlab4j.api.models.User();
        String email = id + "@pop.zjgsu.edu.cn";
        gitUser.setEmail(email);
        gitUser.setName(name);
        gitUser.setUsername(String.valueOf(id));

        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "iUtVKCxSA2sSpDwsjtTE");
        try {
            gitLabApi.getUserApi().createUser(gitUser,"123456789",10);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }*/

        return "redirect:/admin";
    }

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

    /**
     * 管理员安排交叉评阅
     */
    @GetMapping("/areview")
    public String areview(@ModelAttribute PaperInfo paperInfo, Model model){
        List<PaperInfo> list = paperInfoService.getAll();
        model.addAttribute("initdata",list);

        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        model.addAttribute("tasklist",task);

        return "areview";
    }

    /**
     * 管理员添加论文记录
     */
    @PostMapping("/addrecord")
    public String addData(@RequestParam("txt_taskname") String taskname,
                                           @RequestParam("txt_stuname") String stuname,
                                           @RequestParam("txt_tutorname") String tutorname,
                                           @RequestParam("txt_tutorid") String tutorid){

        List<PaperInfo> list = paperInfoService.findPaperInfoByMaxId();
        PaperInfo paperInfo = new PaperInfo();

        int newId = Integer.parseInt(list.get(0).getId().toString())+1;
        int tutorId = Integer.parseInt(tutorid);
        paperInfo.setId(newId);
        paperInfo.setState("待评阅");
        paperInfo.setTaskname(taskname);
        paperInfo.setStuname(stuname);
        paperInfo.setTutorname(tutorname);
        paperInfo.setTutorid(tutorId);
        paperInfo.setType("开题报告");
        paperInfoService.addRecord(paperInfo);

        return "redirect:/admin/areview";
    }
    /**
     * 管理员删除论文记录
     */
    @PostMapping("/delrecord")
    public String delData(@RequestParam("del_stuname") String stuname){
        paperInfoService.delRecord(stuname);
        return "redirect:/admin/areview";

    }

    /**
     * 管理员编辑论文记录
     */
    @PostMapping("/editrecord")
    public String editData(@RequestParam("edit_stuname1") String stuname,
                           @RequestParam("edit_tutorname") String newtutorname,
                           @RequestParam("edit_state") String newstate){

        User user = userService.findUserByName(newtutorname);
        int tutorid = user.getId();
//        System.out.println("tutorid"+tutorid);
        String type = "开题报告";
        paperInfoService.editRecord(newstate,newtutorname,tutorid,type,stuname);
//        List<PaperInfo> list = paperInfoService.findPaperInfoByStunameAndType(stuname,"开题报告");
//        for(PaperInfo paperInfo: list){
//            paperInfo.setTutorname(newtutorname);
//            paperInfo.setState(newstate);
//        }
        return "redirect:/admin/areview";
    }
    /**
     * 管理员查询论文记录
     */
    @PostMapping("/selrecord")
    public void selData(@ModelAttribute  Model model
                            )
    {
        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        model.addAttribute("tasklist",task);
//        return "redirect:/admin/areview";

    }

    @RequestMapping("/seltaskname")
    public String selTaskname(@RequestParam ("tasknameSelection") String taskname,
                              @RequestParam("ftype") String ftype,
                              Model model){
        System.out.println("selectstate"+taskname);
        List<PaperInfo> list ;
        String type = ftype;
        list = paperInfoService.findPaperInfoByTasknameAndType(taskname,type);
        model.addAttribute("initdata", list);

        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        List<User> user = userService.findUsernameByRole(2);
        for(User users:user){
            System.out.println(users.getName());
        }
        model.addAttribute("teacher",user);
        model.addAttribute("tasklist",task);
        model.addAttribute("tasknameSelectionValue",taskname);
        if(ftype.equals("开题报告")){        return  "areview";
        }else{
            return "crossproposal";
        }
    }

    @RequestMapping("/selstate")
    public String selState(@RequestParam ("stateSelection") String state,
                           @RequestParam("ftype1") String ftype1,
                           Model model){
        System.out.println("selectstate"+state);
        List<PaperInfo> list ;
        String type = ftype1;
        list = paperInfoService.findPaperInfoByStateAndType(state,type);
        model.addAttribute("initdata", list);

        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        List<User> user = userService.findUsernameByRole(2);
        for(User users:user){
            System.out.println(users.getName());
        }
        model.addAttribute("teacher",user);
        model.addAttribute("tasklist",task);
        if(state.equals("待评阅")){
            model.addAttribute("stateSelectionValue2",state);
        }else if (state.equals("已通过")){
            model.addAttribute("stateSelectionValue3",state);
        }else{
            model.addAttribute("stateSelectionValue4",state);
        }
        if(ftype1.equals("开题报告")){        return  "areview";
        }else{
            return "crossproposal";
        }
    }

    @RequestMapping("/seltype")
    public String selType(@RequestParam ("tasknameSelection") String taskname,
                           Model model){
        System.out.println("selectstate"+taskname);
        List<PaperInfo> list ;
        String type = "开题报告";
        list = paperInfoService.findPaperInfoByTasknameAndType(taskname,type);
        model.addAttribute("initdata", list);

        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        model.addAttribute("tasklist",task);
        model.addAttribute("tasknameSelectionValue",taskname);
        return  "areview";
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
        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        model.addAttribute("tasklist",task);

        model.addAttribute("teacher",user);
        return "crossproposal";
    }

    @RequestMapping("/crossproposal")
    public String crosstutor(@RequestParam("tname") String tname,
                             @RequestParam("pid") String pid){
        System.out.println(tname +"------------------------"+ pid);
        User user = userService.findUserByUsername(tname);
        String crosstutor = user.getName();
        paperInfoService.editCrosstutor(crosstutor,Integer.parseInt(pid));
        return "redirect:/admin/crossproposal";
    }

    /**
     * 安排开题分组
     */
    @GetMapping("/ktgroup")
    public String ktgroup(Model model){
        List<PaperInfo> list = paperInfoService.getAllkt();
        model.addAttribute("initdata",list);
        List<Task> task = taskService.findTaskByTaskstate("已通过");
        for(Task tasks:task){
            System.out.println(tasks.getTaskname());
        }
        model.addAttribute("tasklist",task);

        return "ktgroup";
    }

    @RequestMapping("/editktgroup")
    public String editktgroup(@RequestParam("pid") String pid,
                              @RequestParam("group") String group){
        System.out.println("------------------------"+ pid);
        System.out.println("---"+group+"---");
        paperInfoService.editKtgroup(group,Integer.parseInt(pid));
        return "redirect:/admin/ktgroup";
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
