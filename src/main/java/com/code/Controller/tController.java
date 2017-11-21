package com.code.Controller;

import com.code.Entity.JsonResponse;
import com.code.Entity.PageInfo;
import com.code.Entity.PaperInfo;
import com.code.Entity.User;
import com.code.Service.PaperInfoService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
/*    @Autowired
    private PageInfo pageInfo;*/

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @RequestMapping("/review")
    public String literatureReview(){
        return "LiteratureReview";
    }


    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ResponseBody
    @RequestMapping("/data")
    public JsonResponse<PaperInfo> getData(PageInfo pageInfo) {
        List<PaperInfo> list = paperInfoService.getAll();
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list, pageInfo);
        return response;
    }

    @ResponseBody
    @RequestMapping("/searchdata")
    public JsonResponse<PaperInfo> getSearchData(PageInfo pageInfo) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String checkValue1 = request.getParameter("checkValue1");
        String checkValue2 = request.getParameter("checkValue2");
        String stuname = request.getParameter("stuname");
        System.out.println(stuname);
        System.out.println(checkValue1);
        System.out.println(checkValue2);
        List<PaperInfo> list1,list2,list3;
            list1 = (List<PaperInfo>) paperInfoService.findPaperInfoById(checkValue1);
            list2 = (List<PaperInfo>) paperInfoService.findPaperInfoByState(checkValue2);
            list3 = (List<PaperInfo>) paperInfoService.findPaperInfoByTaskAndState(checkValue1,checkValue2);
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list3, pageInfo);
        return response;
        /*
        * 1.目前实现的第一个select的value并不是用的taskname 而是id
        * 2.list3这里的参数checkvalue1改成checkvalue3=taskname
        * 3.select option动态获取
        * 4.文献综述评阅界面UI设计
        * 5.jqTable page 参数
        * 7.目前文献综述评阅界面还缺少 应该从若雯的登录界面获取教师姓名和ID 从而直接得到和该教师直接相关的jqgrid表
        * */
    }









}
