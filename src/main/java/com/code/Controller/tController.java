package com.code.Controller;

import com.code.Entity.*;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@RequestMapping("/teacher")
public class tController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("")
    public String teacher(){
               return "teacher";
    }


    @Autowired
    private PaperInfoService paperInfoService;

    /**
     * 教师评阅界面
     */
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping("/review")
    public String literatureReview(Model model){

        List<PaperInfo> list = paperInfoService.getAllwx();
        model.addAttribute("initdata",list);

//        List<Task> task = taskService.findTaskByTaskstate("已通过");
//        for(Task tasks:task){
//            System.out.println(tasks.getTaskname());
//        }
//        model.addAttribute("tasklist",task);
        return "literatureReview";
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/reviewshow")
    public String review(@RequestParam("stuname") String stuname,
                         @RequestParam("taskname") String taskname,
                         @RequestParam("type") String type,
                         Model model){
        model.addAttribute("stuname",stuname);
        model.addAttribute("taskname",taskname);
        model.addAttribute("type",type);
        model.addAttribute("path","/static/file/sample.pdf");
        return "review";
    }

    /**
     *教师评阅状态查询
     */

    @RequestMapping("/selstate")
    public String selState(@RequestParam ("stateSelection") String state,
                           Model model,Principal principal){
        System.out.println("selectstate"+state);
        List<PaperInfo> list ;
        String type = "文献综述";
        int tutorid = new Integer(principal.getName()).intValue();
        list = paperInfoService.findPaperInfoByTutoridTypeState(tutorid,state,type);
        model.addAttribute("initdata", list);

//        if (state.equals("allstate")){
//            model.addAttribute("stateSelectionValue1",type);
        /*}else if*/ if(state.equals("待评阅")){
            model.addAttribute("stateSelectionValue2",type);
        }else if (state.equals("已通过")){
            model.addAttribute("stateSelectionValue3",type);
        }else{
            model.addAttribute("stateSelectionValue4",type);
        }
        return  "literatureReview";
    }

    /**
     * 教师课题查询
     */
    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/show")
    public String TaskShow() {
        return "TaskShow";
    }

    /**
     * 教师新课题提交
     */
    @GetMapping("/addtask")
    public String addtask() {
        return "AddTask";
    }

    @PostMapping("/addtask")
    public String addnewtask(
            @RequestParam("taskid") Integer taskid,
            @RequestParam("taskname") String taskname,
            @RequestParam("tasktype") String taskType,
            @RequestParam("taskrate") String taskrate,
            @RequestParam("taskdescrib") String taskdecrib,
            @RequestParam("tutorname") String tutorname,
            @RequestParam("taskmaxchoose") Integer taskmaxchoose
//          @RequestParam("taskstate") String taskstate
    ){

        Task task = new Task();
        task.setTaskid(taskid);
        task.setTaskname(taskname);
        task.setTaskdescrib(taskdecrib);
        task.setTasktype(taskType);
        task.setTaskrate(taskrate);
        task.setTaskmaxchoose(taskmaxchoose);
        task.setTutorname(tutorname);
        task.setTaskstate("待审核");

        taskService.insertTask(task);

        return "redirect:/teacher";
    }


    /**
     * 教师课题编辑
     */
    @GetMapping("/edittask")
    public String edittask(){return "EditTask";}
    @PostMapping("/edittask")
    public String editoldtask(
            @RequestParam("newtaskname")String newtaskname,
            @RequestParam("newtasktype")String newtasktype,
            @RequestParam("newtaskrate")String newtaskrate,
            @RequestParam("newtaskmaxchoose")Integer newtaskmaxchoose,
            @RequestParam("newtaskdescrib")String newtaskdescrib,
            @RequestParam("newtutorname")String newtutorname,
            @RequestParam("newtaskid")Integer newtaskid
    ){
        Task task =new Task();
        task.setTaskid(newtaskid);
        task.setTaskname(newtaskname);
        task.setTaskrate(newtaskrate);
        task.setTasktype(newtasktype);
        task.setTaskdescrib(newtaskdescrib);
        task.setTaskmaxchoose(newtaskmaxchoose);
        task.setTutorname(newtutorname);
        task.setTaskstate("待审核");

        taskService.updataTask(task);
        return "redirect:/teacher/show";
    }
    /**
     *教师交叉评阅列表
     */
    @GetMapping("/tcross")
    public String tcross(Principal principal,
                         Model model) {
                User user = userService.findUserByUsername(principal.getName());
                List<PaperInfo> lunwen = paperInfoService.findByCrosstutor(user.getName());

        model.addAttribute("initdata",lunwen);
                return "tcross";
    }








}
