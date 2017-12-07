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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ResponseBody
    @RequestMapping("/taskdata")
    public JsonResponse<Task> getTaskData() {
        Object username = SecurityContextHolder.getContext().getAuthentication().getName();
        int tutorid = Integer.parseInt(String.valueOf(username));
        System.out.println(tutorid);
        User user = userService.findUserById(tutorid);
        List<Task> list = taskService.findTaskByTutorname(user.getName());
        JsonResponse<Task> response = new JsonResponse<Task>(list);
        return response;
    }

    @ResponseBody
    @RequestMapping("/searchtaskdata")
    public JsonResponse<Task> getSearchTaskData() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String checkValue1 = request.getParameter("checkValue1");
        String checkValue2 = request.getParameter("checkValue2");
        //  String tutorname = request.getParameter("tutorname");

        //  if (!tutorname.trim().equals(""))System.out.println(tutorname);
        System.out.println(checkValue1);
        System.out.println(checkValue2);

        List<Task> list1, list2, list3;
        list1 = (List<Task>) taskService.findTaskByTaskname(checkValue1);
        list2 = (List<Task>) taskService.findTaskByTaskstate(checkValue2);
//        list3 = (List<Task>) taskService.findTaskByTasknameAndstate(checkValue1, checkValue2);
        JsonResponse<Task> response = new JsonResponse<Task>(list1);
        JsonResponse<Task> response1 = new JsonResponse<Task>(list2);
//        JsonResponse<Task> response2= new JsonResponse<Task>(list3);

        return response;
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



}
