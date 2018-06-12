package com.code.Controller.aController;

import com.code.Entity.*;
import com.code.Service.GradeService;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.security.Principal;
import java.util.List;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AController_srw {

    @Autowired
    private UserService userService;

    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private GradeService gradeService;


    @RequestMapping("")
    public String admin(){
               return "admin";
    }

    @GetMapping("/newstudent")
    public String newstudent(){
        return "newstudent";
    }

    @PostMapping("/newstudent")
    public String addnewstudent(@RequestParam("id") Integer id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password){
        if(userService.findUserById(id) != null){
            return "redirect:/admin/newstudent?error";
        }
        else {
            User user = new User();
            password = new BCryptPasswordEncoder().encode(password);

            user.setId(id);
            user.setName(name);
            user.setPassword(password);
            user.setUsername(String.valueOf(id));
            userService.insertUser(user);
            userService.insertSrole(id);

            int stu_id =new Integer(id).intValue();
            String stu_name =name;
//            taskService.chooseTask(stu_id,stu_name,task_id,task_name);
            taskService.A_addStu(stu_id,stu_name);

//            org.gitlab4j.api.models.User gitUser = new org.gitlab4j.api.models.User();
//            String email = id + "@pop.zjgsu.edu.cn";
//            gitUser.setEmail(email);
//            gitUser.setName(name);
//            gitUser.setUsername(String.valueOf(id));
//
//            try {
//            GitLabApi gitLabApi =  GitLabApi.login("http://gitlab.example.com:30080", "root","wenwen917");
//                gitLabApi.getUserApi().createUser(gitUser, "123456789", 10);
//            } catch (GitLabApiException e) {
//                e.printStackTrace();
//            }
        }

        return "redirect:/admin";
    }

    @GetMapping("/newteacher")
    public String newteacher(){
        return "newteacher";
    }

    @PostMapping("/newteacher")
    public String addnewteacher(@RequestParam("id") Integer id,
                                @RequestParam("name") String name,
                                @RequestParam("password") String password,
                                @RequestParam("isAdmin") String isAdmin){
        User user = new User();
        password = new BCryptPasswordEncoder().encode(password);

        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setUsername(String.valueOf(id));

        userService.insertUser(user);
        userService.insertTrole(id);

        if (isAdmin.equals("1"))
        {
            userService.insertArole(id);
        }
        return "redirect:/admin";
    }



}
