package com.code.Controller.aController;

import com.code.Entity.Grade;
import com.code.Entity.PaperInfo;
import com.code.Entity.Task;
import com.code.Entity.User;
import com.code.Service.GradeService;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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

    /**
     * 管理员安排交叉评阅
     */
    @GetMapping("/areview")
    public String areview(@ModelAttribute PaperInfo paperInfo, Model model){
        List<PaperInfo> list = paperInfoService.getAllkt();
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
/*    @PostMapping("/test")
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
    }*/

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
        System.out.println("del_stuname:----"+stuname);
        return "redirect:/admin/areview";

    }

    /**
     * 管理员编辑论文记录
     */
    @PostMapping("/editrecord")
    public String editData(@RequestParam("edit_stuname1") String stuname,
                           @RequestParam("edit_tutorname") String newtutorname,
                           @RequestParam("edit_crosstutor") String newcrosstutor,
                           @RequestParam("edit_type") String edit_type,
                           @RequestParam("edit_state") String newstate){

        User user = userService.findUserByName(newtutorname);
        int tutorid = user.getId();
//        System.out.println("tutorid"+tutorid);
        String type = edit_type;
        paperInfoService.editRecord(newstate,newtutorname,newcrosstutor,tutorid,type,stuname);
//        List<PaperInfo> list = paperInfoService.findPaperInfoByStunameAndType(stuname,"开题报告");
//        for(PaperInfo paperInfo: list){
//            paperInfo.setTutorname(newtutorname);
//            paperInfo.setState(newstate);
//        }
        return "redirect:/admin/areview";
    }

    @PostMapping("/editcrosstutor")
    public String editCrosstutor(@RequestParam("edit_id") String edit_id,
//                           @RequestParam("edit_tutorname") String newtutorname,
                           @RequestParam("edit_crosstutor") String newcrosstutor,
                           @RequestParam("edit_type") String edit_type
//                           @RequestParam("edit_state") String newstate
    ){
        System.out.println("newcrosstutor------"+newcrosstutor);
        System.out.println("edit_id------"+edit_id);
        int id = Integer.parseInt(edit_id);
        System.out.println("id------"+id);
        paperInfoService.editCrosstutor(newcrosstutor,id);
        if(edit_type.equals("开题报告")){
            return "redirect:/admin/areview";
        }else{
            return "redirect:/admin/crossproposal";
        }
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

        list = paperInfoService.findPaperInfoByTasknameAndType(taskname,type);
        model.addAttribute("initdata", list);
        if(ftype.equals("开题报告")){        return  "areview";
        }else{
            return "crossproposal";
        }
    }

    @RequestMapping("/selstate")
    public String selState(@RequestParam ("stateSelection") String state,
                           @RequestParam("ftype1") String ftype1,
                           @RequestParam("flag") String flag,
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
        }else if (state.equals("allstate")){
            model.addAttribute("stateSelectionValue1",state);
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



    @RequestMapping("/editktgroup")
    public String editktgroup(@RequestParam("pid") String pid,
                              @RequestParam("group") String group){
        System.out.println("------------------------"+ pid);
        System.out.println("---"+group+"---");
        paperInfoService.editKtgroup(group,Integer.parseInt(pid));
        return "redirect:/admin/ktgroup";
    }

    /**
     * 管理员查看成绩
     */
    @GetMapping("/allgrades")
    public String allgrades(@ModelAttribute PaperInfo paperInfo, Model model){
        List<Grade> list = gradeService.getAll();
        model.addAttribute("initdata",list);

//        List<Task> task = taskService.findTaskByTaskstate("已通过");
//        for(Task tasks:task){
//            System.out.println(tasks.getTaskname());
//        }
//        model.addAttribute("tasklist",task);

        return "allgrades";
    }

    /**
     * 管理员推优状态查询
     */

    @RequestMapping("/selgreat")
    public String selGreat(@RequestParam("stateSelection") String great,
//                           @RequestParam("flag") String flag,
                           RedirectAttributes attr,
                           Model model, Principal principal) {
        System.out.println("selectgreat" + great);
        List<Grade> list;
//        int tutorid = new Integer(principal.getName()).intValue();

        if (great.equals("allstate")) {
            list = gradeService.getAll();
            model.addAttribute("initdata", list);
//            model.addAttribute("stateSelectionValue1", type);
        } else if (great.equals("已推优")) {
            list = gradeService.findGradesByIsgreat(1);
            model.addAttribute("initdata", list);
//            model.addAttribute("stateSelectionValue2", type);
        } else if (great.equals("未推优")) {
            list = gradeService.findGradesByIsgreat(0);
            model.addAttribute("initdata", list);
//            model.addAttribute("stateSelectionValue3", type);
        }
        return "allgrades";

    }

    /**
     * 管理员推优
     */
    @RequestMapping("/isgreat")
    public String isgreat(Model model, Principal principal,
                          @RequestParam("edit_sno") String sno,
                          @RequestParam("edit_isgreat") String isgreat,
                          @RequestParam("edit_advice") String advice) {

        int tutorid = new Integer(principal.getName()).intValue();
        gradeService.editIsgreat(Integer.parseInt(sno),Integer.parseInt(isgreat),advice);
        return "redirect:/admin/allgrades";
    }

    @GetMapping("/test1")
    public String test1(Model model){
        return "test1";
    }
}
