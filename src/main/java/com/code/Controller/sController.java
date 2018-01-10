package com.code.Controller;

import com.code.Entity.User;
import com.code.Service.UserService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.SshKey;
import org.omg.CosNaming.BindingIteratorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/gitlog")
    public String gitlog(Principal principal,
                         Model model){

        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "9NPRBbxVTFbjszzEncVM");

        try {
            gitLabApi.sudo(principal.getName());

            List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects();
            /*判断有没有项目*/
            /*没有项目*/
            if(projects.isEmpty()) {
                model.addAttribute("flag", "0");
                gitLabApi.unsudo();
            }
            /*有项目*/
            else {
                model.addAttribute("flag", "1");
                String url =  projects.get(0).getHttpUrlToRepo();/*http下载地址*/
                gitLabApi.unsudo();

                String localPath = "/home/alison/Documents/allgit/"+ principal.getName() + "/" +projects.get(0).getName();

                File file = new File(localPath);

                /*有文件夹就pull*/
                if (file.exists()){

                    File gitfile = new File(localPath+"/.git");
                    // now open the created repository
                    FileRepositoryBuilder builder = new FileRepositoryBuilder();
                    Repository repository = null;
                    try {
                        repository = builder.setGitDir(gitfile).readEnvironment() // scan environment GIT_* variables
                                .findGitDir() // scan up the file system tree
                                .build();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Git git = new Git(repository);
                    try {
                        git.pull().call();
                    } catch (GitAPIException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Pulled from remote repository to local repository at " + repository.getDirectory());

                    repository.close();
                }
                /*没有就clone*/
                else {
                    try{
                        System.out.println("开始下载......");

                        CloneCommand cc = Git.cloneRepository().setURI(url);
                        cc.setDirectory(file).call();

                        System.out.println("下载完成......");

                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }


                /*调用gitinspector*/
                CommandLine cmdLine = new CommandLine("/home/alison/gitinspector/gitinspector.py");
                Map map = new HashMap();
                map.put("FILE", file);
                cmdLine.addArgument("-wTHL");
                cmdLine.addArgument("${FILE}");
                cmdLine.setSubstitutionMap(map);

                DefaultExecutor executor = new DefaultExecutor();


                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
                PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);
                executor.setStreamHandler(streamHandler);
                try {
                    executor.execute(cmdLine);
                    String out = outputStream.toString("utf-8");//获取程序外部程序执行结果
                    System.out.println(out);
                    String error = errorStream.toString("utf-8");
                    System.out.println(error);

                    model.addAttribute("newLineChar", '\n');
                    model.addAttribute("out",out);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }






        return "gitlog";
    }




}
