package com.code.Controller;

import com.code.Entity.JsonResponse;
import com.code.Entity.PageInfo;
import com.code.Entity.PaperInfo;
import com.code.Entity.User;
import com.code.Service.PaperInfoService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String teacher(){
               return "teacher";
    }


    @Autowired
    private PaperInfoService paperInfoService;

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/review")
    public String literatureReview(){
        return "LiteratureReview";
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/reviewshow")
    public String review(){
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

        if(!stuname.trim().equals(""))System.out.println(stuname);
        System.out.println(checkValue1);System.out.println(checkValue2);

        List<PaperInfo> list1,list2,list3;
            list1 = (List<PaperInfo>) paperInfoService.findPaperInfoById(checkValue1);
            list2 = (List<PaperInfo>) paperInfoService.findPaperInfoByState(checkValue2);
            list3 = (List<PaperInfo>) paperInfoService.findPaperInfoByTaskAndState(checkValue1,checkValue2);
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list3);
        return response;
        /*
        * 1.目前实现的第一个select的value并不是用的taskname 而是id
        * 2.list3这里的参数checkvalue1改成checkvalue3=taskname
        * 3.select option动态获取
        * 4.文献综述评阅界面UI设计
        * 5.jqTable page 参数
        * 6.前台分页
        * 7.目前文献综述评阅界面还缺少 应该从若雯的登录界面获取教师姓名和ID 从而直接得到和该教师直接相关的jqgrid表
        * 8.不联网还是获取不到jqgrid
        * 9.pdfcontainer 是如何实现iframe就可以缩放等的
        * 10.把操作data的函数移到java文件中调用
        * */
    }









}
