package com.code.Controller;

import com.code.Entity.*;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
    public String teacher() {
        return "teacher";
    }


    @Autowired
    private PaperInfoService paperInfoService;

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/review")
    public String literatureReview() {
        return "LiteratureReview";
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/reviewshow")
    public String review() {
        return "review";
    }


    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ResponseBody
    @RequestMapping("/data")
    public JsonResponse<PaperInfo> getData() {
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();*/
        /*System.out.println("Username:"
                + securityContextImpl.getAuthentication().getName());*/
        Object username = SecurityContextHolder.getContext().getAuthentication().getName();
        int tutorid = Integer.parseInt(String.valueOf(username));
        System.out.println(tutorid);
        User user = userService.findUserById(tutorid);
        /*List<PaperInfo> list = paperInfoService.getAll();*/
        List<PaperInfo> list = paperInfoService.findPaperInfoByTutorname(user.getName());
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

        /*if(checkValue1.equals("all1")&&checkValue2.equals("all2")&&stuname.trim().equals("")){ getData(); }*/
        /*else if(checkValue1.equals("all1")&&!checkValue2.equals("all2")&&!stuname.equals(null)){}*/

        if (!stuname.trim().equals("")) System.out.println(stuname);
        System.out.println(checkValue1);
        System.out.println(checkValue2);

        List<PaperInfo> list1, list2, list3;
        list1 = (List<PaperInfo>) paperInfoService.findPaperInfoById(checkValue1);
        list2 = (List<PaperInfo>) paperInfoService.findPaperInfoByState(checkValue2);
        list3 = (List<PaperInfo>) paperInfoService.findPaperInfoByTaskAndState(checkValue1, checkValue2);
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list3);
        return response;

        /*
        * 1.html界面复用思路
        * 2.页面垂直滚动条不见了
        * 3.select option动态获取
        * 4.文献综述评阅界面UI设计
        * 5.review界面还没获取用户信息 没和list界面连接起来
        * 6.前台分页  page参数获取不到
        * 7.review界面PDF的插件都无法显示？ 暂时用了iframe
        * 8.静态资源拦截造成pdf无法显示  pdf放在templates下404  路径变为../file/1.pdf就找得到？
        * 9.把操作data的函数移到java文件中调用
        * */
    }

    @Autowired
    private TaskService taskService;

    /**
     * 教师课题显示
     */
    @RequestMapping(value = {"/t_TaskShow"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String t_TaskShow(@ModelAttribute Task task, Model model,
                             Principal principle) {
        int tutor_id = new Integer(principle.getName()).intValue();
        System.out.println(tutor_id);
        List<Task> list = taskService.findTaskBytutorid(tutor_id);
        model.addAttribute("initdata", list);
        return "t_TaskShow";
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
    @PostMapping("/t_TaskDelete")
    public String t_TaskDelete(@RequestParam("del_taskid") String taskid) {
        taskService.delTask(taskid);
        System.out.println(taskid);
        return "redirect:/teacher/t_TaskShow";
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
    @RequestMapping(value = {"/t_FindTask"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String t_FindTask(@ModelAttribute Task task, Model model,
                             @RequestParam("tasktypeSelection") String task_type,
                             @RequestParam ("taskstateSelection") String task_state,
//                             @PathVariable("tasktypeSelection") String searchtask_type,
                             Principal principle) {
        int tutor_id = new Integer(principle.getName()).intValue();

        List<Task> list;

        if (task_type.equals("全部")&& !task_state.equals("全部")){
            list =taskService.findstateTaskFor(tutor_id,task_state);
        }else if (task_state.equals("全部")&&!task_type.equals("全部")){
            list =taskService.findtypeTaskFor(tutor_id,task_type);
        }else if(task_state.equals("全部")&& task_type.equals("全部")){
            System.out.println("全部");
            list = taskService.findTaskBytutorid(tutor_id);
        }else{
            list = taskService.findTaskFor(tutor_id,task_type,task_state);
        }
        if (task_type.equals("全部")){
            model.addAttribute("tasktypeSelection_A1",task_type);
        }else if (task_type.equals("系统设计")){
            model.addAttribute("tasktypeSelection_A2",task_type);
        }else if (task_type.equals("算法设计")){
            model.addAttribute("tasktypeSelection_A3",task_type);
        }else {
            model.addAttribute("tasktypeSelection_A4",task_type);
        }

        if (task_state.equals("全部")){
            model.addAttribute("taskstateSelection_B1",task_type);
        }else if (task_state.equals("待审核")){
            model.addAttribute("taskstateSelection_B2",task_type);
        }else if (task_state.equals("已通过")){
            model.addAttribute("taskstateSelection_B3",task_type);
        }else{
            model.addAttribute("taskstateSelection_B4",task_type);
        }

        model.addAttribute("initdata", list);
        return "t_TaskShow";
    }


    /**
     * 教师新课题提交
     */
    @GetMapping("/t_AddTask")
    public String t_AddTask() {
        return "t_AddTask";
    }

    @PostMapping("/t_AddTask")
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

        return "redirect:/teacher/t_TaskShow";
    }

}