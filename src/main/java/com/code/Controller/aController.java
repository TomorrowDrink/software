package com.code.Controller;

import com.code.Entity.User;
import com.code.Service.UserService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class aController {

    @Autowired
    private UserService userService;


    @RequestMapping("")
    public String admin(){
               return "admin";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/newstudent")
    public String newstudent(){
        return "newstudent";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

            org.gitlab4j.api.models.User gitUser = new org.gitlab4j.api.models.User();
            String email = id + "@pop.zjgsu.edu.cn";
            gitUser.setEmail(email);
            gitUser.setName(name);
            gitUser.setUsername(String.valueOf(id));

            GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");
            try {
                gitLabApi.getUserApi().createUser(gitUser,"123456789",1);
            } catch (GitLabApiException e) {
                e.printStackTrace();
            }

            return "redirect:/admin";
        }

    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/newteacher")
    public String newteacher(){
        return "newteacher";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
