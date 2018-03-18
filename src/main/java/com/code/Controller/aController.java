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
import java.security.Principal;
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

    @GetMapping("/newstudent")
    public String newstudent(){
        return "newstudent";
    }

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

//            org.gitlab4j.api.models.User gitUser = new org.gitlab4j.api.models.User();
//            String email = id + "@pop.zjgsu.edu.cn";
//            gitUser.setEmail(email);
//            gitUser.setName(name);
//            gitUser.setUsername(String.valueOf(id));
//
//            try {
//            GitLabApi gitLabApi =  GitLabApi.login("http://gitlab.example.com:30080", "root","wenwen917");
//                gitLabApi.getUserApi().createUser(gitUser, "123456789", 10);
//            } catch (GitLabApiException e) {
//                e.printStackTrace();
//            }
        }

        return "redirect:/admin";
    }

    @GetMapping("/newteacher")
    public String newteacher(){
        return "newteacher";
    }

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


    /**
     * 课题审查，显示待审查课题
     * @return 待审查课题
     */
    @RequestMapping(value = {"/a_TaskReview"},method = {RequestMethod.POST,RequestMethod.GET})
    public String a_TaskReview(@ModelAttribute Task task,Model model){
        List<Task> list = taskService.findTaskByTaskstate("待审核");
        model.addAttribute("initdata",list);
        return  "a_TaskReview";
    }

    /***
     * 删除课题记录
     */
    @PostMapping("/a_TaskDelete")
    public String a_TaskDelete(@RequestParam("del_taskid")String taskid){
        taskService.delTask(taskid);
        System.out.println(taskid);
        return "redirect:/admin/a_TaskShow";
    }

    /**
     * 审核通过课题
     */
    @PostMapping("/a_TaskPass")
    public  String a_TaskPass(@RequestParam("pass_taskid") String passtaskid){
        taskService.updataTaskState(passtaskid);
        System.out.println(passtaskid);
        return "redirect:/admin/a_TaskReview";
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
    @PostMapping("/a_ShowTaskDetail")
    public String a_ShowTaskDetail(@RequestParam("detail_task")int  taskid){
        taskService.findTaskByTaskid(taskid);
        System.out.println(taskid);
        return "redirect:/admin/a_TaskShow";
    }

    /**
     * 审核界面筛选课题findtask
     */
    @RequestMapping(value = {"/a_FindTaskR"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String t_findTaskR(@ModelAttribute Task task, Model model,
                              @RequestParam("tasktypeSelection") String task_type
    ) {
        String task_state ="待审核";

        List<Task> list;

        if (task_type.equals("全部")){
            list =taskService.findTaskByTaskstate(task_state);
            model.addAttribute("tasktypeSelection_D1",task_type);
        }else if (task_type.equals("系统设计")){
            list =taskService.a_findTaskByTypeState(task_type,task_state);
            model.addAttribute("tasktypeSelection_D2",task_type);
        }else if(task_type.equals("算法设计")){
            list =taskService.a_findTaskByTypeState(task_type,task_state);
            model.addAttribute("tasktypeSelection_D3",task_type);
        }else {
            list =taskService.a_findTaskByTypeState(task_type,task_state);
            model.addAttribute("tasktypeSelection_D4",task_type);
        }
        model.addAttribute("initdata", list);
        return "a_TaskReview";
    }

    /**
     * 课题管理界面课题查询
     */

    @RequestMapping(value = {"/a_FindTask"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String a_TaskFind(@ModelAttribute Task task, Model model,
                             @RequestParam("tasktypeSelection") String task_type,
                             @RequestParam("taskstateSelection") String task_state
    ) {

        System.out.println(task_type+task_state);

        List<Task> list1 = null;
        if (task_type.equals("全部")&&task_state.equals("全部")){
            list1 =taskService.getAll();
        }else if (task_type.equals("全部")&&!task_state.equals("全部")){
            list1 =taskService.a_findTaskByState(task_state);
        }else if (!task_type.equals("全部")&&task_state.equals("全部")){
            list1 =taskService.a_findTaskByType(task_type);
        }else{
            list1 =taskService.a_findTaskByTypeState(task_type,task_state);
        }

        if (task_type.equals("全部")){
            model.addAttribute("tasktypeSelection_D1",task_type);
        }else if (task_type.equals("系统设计")){
            model.addAttribute("tasktypeSelection_D2",task_type);
        }else if(task_type.equals("算法设计")){
            model.addAttribute("tasktypeSelection_D3",task_type);
        }else {
            model.addAttribute("tasktypeSelection_D4",task_type);
        }

        if(task_state.equals("全部")){
            model.addAttribute("taskstateSelection_C1",task_state);
        }else if (task_state.equals("待审核")){
            model.addAttribute("taskstateSelection_C2",task_state);
        }else if(task_state.equals("已通过")){
            model.addAttribute("taskstateSelection_C3",task_state);
        }else {
            model.addAttribute("taskstateSelection_C4",task_state);
        }


        model.addAttribute("initdata", list1);
        return "a_TaskShow";
    }
}
