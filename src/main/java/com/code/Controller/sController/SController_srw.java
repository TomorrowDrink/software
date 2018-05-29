package com.code.Controller.sController;

import com.code.Config.StorageFileNotFoundException;
import com.code.Entity.Filesss;
import com.code.Entity.JsonResponse;
import com.code.Entity.Task;
import com.code.Entity.Task_s;
import com.code.Entity.User;
import com.code.Service.FilesssService;
import com.code.Service.StorageService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class SController_srw {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;



    @RequestMapping("")
    public String student(){
        return "student";
    }



    @Autowired
    private  StorageService storageService;


    @GetMapping("/gitlab")
    public String gitlab(){
        return "gitloginput";
    }





    @GetMapping("/newproject")
    public String newproject(){
        return "newproject";
    }







    @GetMapping("/gitlog")
    public String gitloginput(){
        return "gitloginput";
    }


    @PostMapping("/gitlog")
    public String gitlog(Principal principal,
                        Model model,
                         @RequestParam("address") String address,
                         @RequestParam("name") String name){

                 System.out.println(address + name);
//
                String url =  address;/*http下载地址*/
//                gitLabApi.unsudo();

                String localPath = "/home/alison/Document/allgit/"+ principal.getName() + "/" + name ;
//                String localPath = "/home/alison/Documents/allgit/"+ principal.getName() + "/" + "test" ;

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

//                String biaoji = ">";

                /*调用gitinspector*/
                CommandLine cmdLine = new CommandLine("gitinspector");
//                CommandLine cmdLine = new CommandLine("cmd.exe python D:/gitinspector/gitinspector.py");
                Map map = new HashMap();
                map.put("FILE", file);
//                cmdLine.addArgument("--format=html --timeline --localize-output -w");
//                cmdLine.addArgument("-wTHL");
                cmdLine.addArgument("${FILE}");
                cmdLine.setSubstitutionMap(map);
//                cmdLine.addArgument(biaoji);
//                cmdLine.addArgument("/home/alison/Document/html/"+principal.getName() + ".html");
//              String cmd = "gitinspector -format=html --timeline --localize-output -w "+localPath+" > /home/alison/Document/html/"+principal.getName() + ".html";

                DefaultExecutor executor = new DefaultExecutor();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,outputStream);
        executor.setStreamHandler(streamHandler);
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String out = outputStream.toString();//获取程序外部程序执行结果
//        System.out.println(out);


//
        model.addAttribute("newLineChar", '\n');
        model.addAttribute("out",out);

        return "gitlog";
//        return "student";
    }




}
