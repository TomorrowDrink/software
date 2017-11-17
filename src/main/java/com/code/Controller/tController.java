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










}
