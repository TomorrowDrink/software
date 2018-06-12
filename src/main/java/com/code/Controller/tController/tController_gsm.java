package com.code.Controller.tController;


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

import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_TEACHER')")
@RequestMapping("/teacher")
public class tController_gsm {

    @Autowired
    private UserService userService;

    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private GradeService gradeService;

    /**
     * 教师课题查询
     */
    @Autowired
    private TaskService taskService;

    /**
     * 教师课题显示
     */
    @RequestMapping(value = {"/kadai"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String t_TaskShow(@ModelAttribute Task task, Model model,
                             Principal principle) {
        int tutor_id = new Integer(principle.getName()).intValue();
        System.out.println(tutor_id);
        List<Task> list = taskService.findTaskBytutorid(tutor_id);
        model.addAttribute("initdata", list);
        return "kadai";
    }

    /**
     * 编辑课题edittask
     */

    @PostMapping("/t_TaskEdit")
    public String t_TaskEdit(
            @RequestParam("edit_taskid") int taskid,
            @RequestParam("edit_taskname") String taskname,
            @RequestParam("edit_tutorname") String tutorname,
            @RequestParam("edit_tasktype") String tasktype,
            @RequestParam("edit_taskrate") String taskrate,
            @RequestParam("edit_taskmaxchoose") int taskmaxchoose,
            @RequestParam("edit_taskdescrib") String taskdescrib,
            Principal principal) {
        int tutorid = new Integer(principal.getName());
        System.out.println("编辑" + tutorid + "\n" + taskid);

        System.out.println(taskid + taskname + tutorname + tasktype + taskrate + taskmaxchoose + taskdescrib + tutorid);
        Task task = new Task();
        task.setTaskid(taskid);
        task.setTaskname(taskname);
        task.setTaskrate(taskrate);
        task.setTasktype(tasktype);
        task.setTaskdescrib(taskdescrib);
        task.setTaskmaxchoose(taskmaxchoose);
        task.setTutorname(tutorname);
        task.setTutorid(tutorid);
        task.setTaskstate("待审核");
        taskService.updataTask(task);
        return "redirect:/teacher/t_TaskShow";
    }


    /***
     * 删除课题记录deltask
     */
    @PostMapping("/kadaiDelete")
    public String t_TaskDelete(@RequestParam("del_taskid") String taskid) {
        taskService.delTask(taskid);
        System.out.println(taskid);
        return "redirect:/teacher/kadai";
    }

    /**
     * 查询课题详细
     */
    @PostMapping("/t_ShowTaskDetail")
    public String t_ShowTaskDetail(@RequestParam("detail_task") int taskid) {
        taskService.findTaskByTaskid(taskid);
        System.out.println(taskid);
        return "redirect:/teacher/t_TaskShow";
    }

    /**
     * 筛选课题findtask
     */
    @RequestMapping(value = {"/a_FindTask"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String t_FindTask(@ModelAttribute Task task, Model model,
//                             @RequestParam("tasktypeSelection") String task_type,
                             @RequestParam ("taskstateSelection") String task_state,
//                             @PathVariable("tasktypeSelection") String searchtask_type,
                             Principal principle) {
        int tutor_id = new Integer(principle.getName()).intValue();

        List<Task> list ;

//        if (task_type.equals("全部")&& !task_state.equals("全部")){
//            list =taskService.findstateTaskFor(tutor_id,task_state);
//        }else if (task_state.equals("全部")&&!task_type.equals("全部")){
//            list =taskService.findtypeTaskFor(tutor_id,task_type);
//        }else if(task_state.equals("全部")&& task_type.equals("全部")){
//            System.out.println("全部");
//            list = taskService.findTaskBytutorid(tutor_id);
//        }else{
//            list = taskService.findTaskFor(tutor_id,task_type,task_state);
//        }
//        if (task_type.equals("全部")){
//            model.addAttribute("tasktypeSelection_A1",task_type);
//        }else if (task_type.equals("系统设计")){
//            model.addAttribute("tasktypeSelection_A2",task_type);
//        }else if (task_type.equals("算法设计")){
//            model.addAttribute("tasktypeSelection_A3",task_type);
//        }else {
//            model.addAttribute("tasktypeSelection_A4",task_type);
//        }
//
//        if (task_state.equals("全部")){
//            model.addAttribute("taskstateSelection_B1",task_type);
//        }else if (task_state.equals("待审核")){
//            model.addAttribute("taskstateSelection_B2",task_type);
//        }else if (task_state.equals("已通过")){
//            model.addAttribute("taskstateSelection_B3",task_type);
//        }else{
//            model.addAttribute("taskstateSelection_B4",task_type);
//        }
        if(task_state.equals("全部")){
            list =taskService.findTaskBytutorid(tutor_id);
            model.addAttribute("taskstateSelection_B1",task_state );
        }else if (task_state.equals("待审核")){
            list =taskService.findstateTaskFor(tutor_id,task_state);
            model.addAttribute("taskstateSelection_B2",task_state );
        }else if ( task_state.equals("未通过")){
            list =taskService.findstateTaskFor(tutor_id,task_state);
            model.addAttribute("taskstateSelection_B4",task_state );
        }else{
            list =taskService.findstateTaskFor(tutor_id,task_state);
            model.addAttribute("taskstateSelection_B3",task_state);
        }

        model.addAttribute("initdata", list);
        return "kadai";
    }


    /**
     * 教师新课题提交
     */
    @GetMapping("/newKadai")
    public String t_AddTask() {
        return "newKadai";
    }

    @PostMapping("/newKadai")
    public String t_AddNewTask(
            @RequestParam("taskid") Integer taskid,
            @RequestParam("taskname") String taskname,
            @RequestParam("tasktype") String taskType,
            @RequestParam("taskrate") String taskrate,
            @RequestParam("taskdescrib") String taskdecrib,
            @RequestParam("taskmaxchoose") Integer taskmaxchoose,
            Principal principal) {

        User user =userService.findUserById(new Integer(principal.getName()));
        String tutorname=user.getName();
        Task task = new Task();
        int tutorid =new Integer(principal.getName());
        task.setTaskid(taskid);
        task.setTaskname(taskname);
        task.setTaskdescrib(taskdecrib);
        task.setTasktype(taskType);
        task.setTaskrate(taskrate);
        task.setTaskmaxchoose(taskmaxchoose);
        task.setTutorid(tutorid);
        task.setTutorname(tutorname);
        task.setTaskstate("待审核");
        System.out.println("看一看"+tutorid);

        taskService.insertTask(task);

        return "redirect:/teacher/kadai";
    }


    /**
     * 教师课题编辑
     */
//    @GetMapping("/edittask")
//    public String edittask(){return "editTask";}
    @PostMapping("/editkadai")
    public String editoldtask(
            @RequestParam("edit_taskname")String newtaskname,
            @RequestParam("edit_tasktype")String newtasktype,
            @RequestParam("edit_taskrate")String newtaskrate,
            @RequestParam("edit_taskmaxchoose")Integer newtaskmaxchoose,
            @RequestParam("edit_taskdescrib")String newtaskdescrib,
            @RequestParam("edit_tutorname")String newtutorname,
            @RequestParam("edit_taskid")Integer newtaskid,
            Principal principal
    ){
        int tutor_id = new Integer(principal.getName()).intValue();
        Task task =new Task();
        task.setTaskid(newtaskid);
        task.setTaskname(newtaskname);
        task.setTaskrate(newtaskrate);
        task.setTasktype(newtasktype);
        task.setTaskdescrib(newtaskdescrib);
        task.setTaskmaxchoose(newtaskmaxchoose);
        task.setTutorname(newtutorname);
        task.setTaskstate("待审核");
        task.setTutorid(tutor_id);


        taskService.updataTask(task);
        return "redirect:/teacher/kadai";
    }

}
