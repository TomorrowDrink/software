package com.code.Controller;

import com.code.Entity.User;
import com.code.Service.UserService;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.SshKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Key;
import java.security.Principal;
import java.util.List;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class sController {

    @Autowired
    private UserService userService;



    @RequestMapping("")
    public String student(){
              return "student";
    }

//    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/password")
    public String password(){
        return "changepassword";
    }

//    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/password")
    public String uppassword(@RequestParam("pwd") String pwd,
                             @RequestParam("newpwd") String newpwd,
                             @RequestParam("renewpwd") String renewpwd,
                             Principal principal){
        if(newpwd.equals(renewpwd)) {
            String username = principal.getName();
            User user = userService.findUserByUsername(username);
            boolean flag = new BCryptPasswordEncoder().matches(pwd,user.getPassword());
            System.out.println(flag);
            if (flag) {

                newpwd = new BCryptPasswordEncoder().encode(newpwd);
                userService.updatePwd(user.getPassword(), newpwd);
                return "redirect:/student";
            } else {
                return "redirect:/student/password?error1";
            }
        }
        else {
            return "redirect:/student/password?error2";
        }
    }


    @GetMapping("/gitlab")
    public String gitlab(){
        return "git";
    }

    @GetMapping("/newproject")
    public String newproject(){
        return "newproject";
    }

    @PostMapping("/newproject")
    public String createproject(@RequestParam("pname") String pname,
                                @RequestParam("pdes") String pdes,
                                Principal principal){

        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");

        try {
            gitLabApi.sudo(principal.getName());
            List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects();
            if(projects.isEmpty()) {
                Project projectSpec = new Project()
                        .withName(pname)
                        .withDescription(pdes)
                        .withIssuesEnabled(true)
                        .withMergeRequestsEnabled(true)
                        .withWikiEnabled(true)
                        .withSnippetsEnabled(true)
                        .withPublic(true);

                Project newProject = gitLabApi.getProjectApi().createProject(projectSpec);
            gitLabApi.unsudo();
            }
            else {
                return "redirect:/student/newproject?error";
            }
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }


        return "redirect:/student/gitproject";
    }

    @GetMapping("/gitproject")
    public String gitproject(Principal principal,
                             Model model){
        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");

        try {
            gitLabApi.sudo(principal.getName());

            List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects();
            if(projects.isEmpty()) {
                model.addAttribute("flag", "0");
                gitLabApi.unsudo();
            }
            else {
                List<Commit> commits= gitLabApi.getCommitsApi().getCommits(projects.get(0).getId());
                model.addAttribute("pro", projects.get(0));
                model.addAttribute("flag", "1");
                model.addAttribute("commit", commits);
                gitLabApi.unsudo();
            }
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return "gitproject";
    }

    @PostMapping("/gitproject")
    public String deleteproject(Principal principal){
        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");

        try {
            gitLabApi.sudo(principal.getName());
            List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects();
            gitLabApi.getProjectApi().deleteProject(projects.get(0));
            gitLabApi.unsudo();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return "redirect:/student/gitproject";
    }

    @GetMapping("/ssh")
    public String ssh(Model model,Principal principal){
        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");

        try {
            gitLabApi.sudo(principal.getName());
            List<SshKey> sshKeys= gitLabApi.getUserApi().getSshKeys();

            model.addAttribute("keys",sshKeys);
            gitLabApi.unsudo();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return "ssh";
    }

    @PostMapping("/ssh")
    public String addssh(@RequestParam("ssh") String ssh,
                         @RequestParam("sshname") String sshname,
                         Principal principal){
        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");

        try {
            gitLabApi.sudo(principal.getName());
            gitLabApi.getUserApi().addSshKey(sshname,ssh);
            gitLabApi.unsudo();
        } catch (GitLabApiException e) {
            e.printStackTrace();
            return "redirect:/student/ssh?error";
        }
        return "redirect:/student/ssh?success";
    }






}
