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
    @RequestMapping("/test")
    public String test(){
        return "test";
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
        * 1.html界面复用思路
        * 3.select option动态获取
        * 4.文献综述评阅界面UI设计
        * 5.review界面还没获取用户信息 没和list界面连接起来
        * 6.前台分页  page参数获取不到
        * 7.review界面PDF的插件都无法显示？ 暂时用了iframe
        * 8.静态资源拦截造成pdf无法显示  pdf放在templates下404  路径变为../file/1.pdf就找得到？
        * 9.把操作data的函数移到java文件中调用
        * 10.ajax+jq+jqgrid
        * */
    }









}
