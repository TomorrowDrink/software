package com.code.Controller.aController;

import com.code.Entity.Task;
import com.code.Entity.Task_s;
import com.code.Entity.User;
import com.code.Service.GradeService;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class aController_gsm {

    @Autowired
    private UserService userService;



    @Autowired
    private TaskService taskService;



    /**
     * 管理员课题操作
     */

    /**
     * 开启关闭选题
     * @param model
     * @return
     */
    @RequestMapping(value = {"/a_kadaiOpenAndClose"},method = {RequestMethod.POST,RequestMethod.GET})
    public String openAndclose(Model model){
        int choseState =new Integer(taskService.choseState()).intValue();
        String state1="当前开放";
        String state2="当前关闭";
        if (choseState == 1)
            model.addAttribute("state",state1);
        if (choseState == 0)
            model.addAttribute("state",state2);
//        return "redirect:/admin/a_kadaiOpenAndClose";
        return "a_kadaiOpenAndClose";
    }

    @RequestMapping(value = {"/changeState"},method = {RequestMethod.POST,RequestMethod.GET})
    public String changeState(@RequestParam("state") String  changeState){
        System.out.println(changeState);

        if (changeState.equals("1"))
            taskService.changeState(changeState);
        if (changeState.equals("0"))
            taskService.changeState(changeState);
        return "redirect:/admin/a_kadaiOpenAndClose";
    }




    /**
     *显示课题
     */
    @RequestMapping(value = {"/a_kadai"},method = {RequestMethod.POST,RequestMethod.GET})
    public String a_TaskShow(@ModelAttribute Task task, Model model){
        List<Task> list = taskService.getAll();
        model.addAttribute("initdata",list);
        return  "a_kadai";
    }

    /**
     * 管理员课题指定
     */
    @RequestMapping(value = {"/a_kadaiorder"},method = {RequestMethod.POST,RequestMethod.GET})
    public String a_kadaiorder(@ModelAttribute Task_s task_s ,
                                @ModelAttribute Task task ,
                                Model model){
        return  "a_kadaiorder";
    }

    @PostMapping("/a_TaskAppoint")
    public String a_TaskAppoint(@ModelAttribute Task_s task_s ,
                            @ModelAttribute Task task ,
                            @RequestParam("stu_id") String stuid,
                            Model model){
        int stu_id =new Integer(stuid).intValue();
        List<Task_s> list1 =taskService.a_findAppointStuid(stu_id);
        model.addAttribute("initdata1",list1);
        System.out.println(list1);

        List<Task> list2 =taskService.findTaskByTaskstate("已通过");
        model.addAttribute("initdata2",list2);
        System.out.println(list2);
        return  "a_kadaiorder";
    }

    @PostMapping("/addtoStu")
    public String addtoStuTask(
            @RequestParam("addtomy_taskid") String taskid,
            @RequestParam("addtomy_taskname") String task_name,
            @RequestParam("student_id") String student_id
    ){


        int stu_id =new Integer(student_id).intValue();
        int task_id=new Integer(taskid).intValue();
        User user =userService.findUserById(stu_id);
        String stu_name =user.getName();
        System.out.println("学生:"+stu_id+"\n姓名:"+stu_name+"\n课题id:"+task_id+"\n课题名称:"+task_name);
        taskService.chooseTask(stu_id,stu_name,task_id,task_name);

        return "a_TaskAppoint";
    }

    @PostMapping("/a_SkadaiDel")
    public  String a_SkadaiDel(@RequestParam("stu_idDel") String stu_idDel,Model model){
        int stu_id =new Integer(stu_idDel).intValue();
        taskService.a_skadaiDel(stu_id);
        List<Task_s> list1 =taskService.a_findAppointStuid(stu_id);
        model.addAttribute("initdata1",list1);
        System.out.println(list1);

        List<Task> list2 =taskService.findTaskByTaskstate("已通过");
        model.addAttribute("initdata2",list2);
        System.out.println(list2);
        return  "a_kadaiorder";
    }

    @PostMapping("/addKadaiToStu")
    public String admin_addKadaiToStu(@RequestParam("add_taskid") String taskid,
                                      @RequestParam("add_taskname") String task_name,
                                      @RequestParam("add_stuid") String stuid,
                                      Model model){
        System.out.println(stuid);
        int task_id= new Integer(taskid).intValue();
        int stu_id =new Integer(stuid).intValue();
        User user =userService.findUserById(stu_id);
        String stu_name =user.getName();
        taskService.chooseTask(stu_id,stu_name,task_id,task_name);

        List<Task_s> list1 =taskService.a_findAppointStuid(stu_id);
        model.addAttribute("initdata1",list1);
        System.out.println(list1);

        List<Task> list2 =taskService.findTaskByTaskstate("已通过");
        model.addAttribute("initdata2",list2);
        System.out.println(list2);
        return  "a_kadaiorder";
    }









    /**
     * 课题审查，显示待审查课题
     * @return 待审查课题
     */
    @RequestMapping(value = {"/a_pending"},method = {RequestMethod.POST,RequestMethod.GET})
    public String a_TaskReview(@ModelAttribute Task task,Model model){
        List<Task> list = taskService.findTaskByTaskstate("待审核");
        model.addAttribute("initdata",list);
        return  "a_pending";
    }

    /***
     * 删除课题记录
     */
    @PostMapping("/a_TaskDelete")
    public String a_TaskDelete(@RequestParam("del_taskid")String taskid){
        taskService.delTask(taskid);
        System.out.println(taskid);
        return "redirect:/admin/a_kadai";
    }

    /**
     * 审核通过课题
     */
    @PostMapping("/a_TaskPass")
    public  String a_TaskPass(@RequestParam("pass_taskid") String passtaskid){
        taskService.updataTaskState(passtaskid);
        System.out.println(passtaskid);
        return "redirect:/admin/a_kadai";
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
        return "redirect:/admin/a_kadai";
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
        return "a_pending";
    }

    /**
     * 课题管理界面课题查询
     */

    @RequestMapping(value = {"/a_FindTask"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String a_TaskFind(@ModelAttribute Task task, Model model,
//                             @RequestParam("tasktypeSelection") String task_type,
                             @RequestParam("taskstateSelection") String task_state
    ) {


        List<Task> list1 = null;
//        if (task_type.equals("全部")&&task_state.equals("全部")){
//            list1 =taskService.getAll();
//        }else if (task_type.equals("全部")&&!task_state.equals("全部")){
//            list1 =taskService.a_findTaskByState(task_state);
//        }else if (!task_type.equals("全部")&&task_state.equals("全部")){
//            list1 =taskService.a_findTaskByType(task_type);
//        }else{
//            list1 =taskService.a_findTaskByTypeState(task_type,task_state);
//        }
//
//        if (task_type.equals("全部")){
//            model.addAttribute("tasktypeSelection_D1",task_type);
//        }else if (task_type.equals("系统设计")){
//            model.addAttribute("tasktypeSelection_D2",task_type);
//        }else if(task_type.equals("算法设计")){
//            model.addAttribute("tasktypeSelection_D3",task_type);
//        }else {
//            model.addAttribute("tasktypeSelection_D4",task_type);
//        }

        if(task_state.equals("全部")){
            model.addAttribute("taskstateSelection_C1",task_state);
            list1 =taskService.getAll();
        }else if (task_state.equals("待审核")){
            model.addAttribute("taskstateSelection_C2",task_state);
            list1 =taskService.a_findTaskByState(task_state);
        }else if(task_state.equals("已通过")){
            model.addAttribute("taskstateSelection_C3",task_state);
            list1 =taskService.a_findTaskByState(task_state);
        }else {
            model.addAttribute("taskstateSelection_C4",task_state);
            list1 =taskService.a_findTaskByState(task_state);
        }



        model.addAttribute("initdata", list1);
        return "a_kadai";
    }
}
