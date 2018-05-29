package com.code.Controller.aController;

<<<<<<< HEAD:src/main/java/com/code/Controller/aController.java
import com.code.Entity.*;
=======
import com.code.Entity.Grade;
import com.code.Entity.PaperInfo;
import com.code.Entity.Task;
import com.code.Entity.User;
>>>>>>> 8f89dec3280af0a0d5735ebaa636015ae7229b82:src/main/java/com/code/Controller/aController/aController_xzx.java
import com.code.Service.GradeService;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class aController_xzx {

    @Autowired
    private UserService userService;

    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private GradeService gradeService;
<<<<<<< HEAD:src/main/java/com/code/Controller/aController.java


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
=======
>>>>>>> 8f89dec3280af0a0d5735ebaa636015ae7229b82:src/main/java/com/code/Controller/aController/aController_xzx.java

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
     * 管理员成绩管理
     */
<<<<<<< HEAD:src/main/java/com/code/Controller/aController.java
    @PostMapping("/test")
=======
/*    @PostMapping("/test")
>>>>>>> 8f89dec3280af0a0d5735ebaa636015ae7229b82:src/main/java/com/code/Controller/aController/aController_xzx.java
    public String test(){return "redirect:/admin/test";}

    @GetMapping("/test")
    public String test(@ModelAttribute Grade grade, Model model){
        List<Grade> list = gradeService.getAll();
        int id = 1512190407;
        System.out.println("paperinfo------------------------------");
        System.out.println(paperInfoService.findScores(id));
        System.out.println("paperinfo------------------------------");
        gradeService.editTscore(paperInfoService.findScores(id),id);
        gradeService.editIsgreat();
        model.addAttribute("initdata",list);
        return "test";
<<<<<<< HEAD:src/main/java/com/code/Controller/aController.java
    }
=======
    }*/
>>>>>>> 8f89dec3280af0a0d5735ebaa636015ae7229b82:src/main/java/com/code/Controller/aController/aController_xzx.java

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
}
