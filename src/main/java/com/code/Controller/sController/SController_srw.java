package com.code.Controller.sController;


import com.code.Service.StorageService;
import com.code.Service.TaskService;
import com.code.Service.UserService;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.*;
import java.io.IOException;
import java.security.Principal;

import java.util.*;


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



    /*查看已有项目*/
    @GetMapping("/gitlogcheck")
    public String gitloginput(Principal principal,
                              Model model){

        int flag = 1;
        ArrayList<String> files = new ArrayList<String>();
        String path = "/home/alison/Documents/allgit/"+ principal.getName() ;
        File file = new File(path);
        File[] tempList = file.listFiles();

        if(tempList != null) {
            for (int i = 0; i < tempList.length; i++) {

                if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
                    files.add(tempList[i].getName());
                }
            }
        }
       else {
             flag = 0;
        }

        model.addAttribute("files",files);
        model.addAttribute("flag",flag);
        model.addAttribute("principal",principal.getName());


        return "gitlogcheck";
    }


    @PostMapping("/gitlog")
    public String gitlog(Principal principal,
                        Model model,
                         @RequestParam("filename") String filename){


        String Path = "/var/www/html/gitpage/" + principal.getName();
        File myPath = new File(Path);

        try {

            if ( !myPath.exists()){//若此目录不存在，则创建之
                myPath.mkdir();
                System.out.println("创建文件夹路径为："+ Path);
            }

                    Process p = Runtime.getRuntime().exec(
                            new String[] { "/bin/sh", "-c", "gitinspector --format=html /home/alison/Documents/allgit/"+principal.getName()+"/"+filename+" >/var/www/html/gitpage/"+principal.getName()+"/"+filename+".html"}, null, null);
//                            new String[] { "/bin/sh", "-c", "gitinspector --format=html /home/alison/Documents/allgit/"+principal.getName()+"/"+filename+" >/home/alison/IdeaProjects/"+principal.getName()+"/"+filename+"/"+filename+".html"}, null, null);
                    p.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        File file=new File(Path + "/" + filename+".html");
        if(file.exists())
            return "redirect:/student/gitlogcheck?success";
        else
            return "redirect:/student/gitlogcheck?no";

//          return outpath;
    }



    /*删除项目*/
    @PostMapping("/gitdelete")
    public String gitdelete( @RequestParam("filename") String filename,
                             Principal principal,
                             Model model){

        System.out.println(filename);
        String path = "/home/alison/Documents/allgit/"+ principal.getName() + "/" + filename ;

        File file = new File(path);

        boolean flag = deleteDir(file);


        if(flag == true)
            return "redirect:/student/gitlogcheck";
        else
            return "redirect:/student/gitlogcheck?fail";



    }

    /*删除文件夹的函数*/
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();

            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /*输入项目*/
    @GetMapping("/gitinput")
    public String gitin(){
        return "gitloginput";
    }


    /*添加项目*/
    @PostMapping("/gitproadd")
    public String gitproadd(Principal principal,
                            Model model,
                            @RequestParam("address") String address,
                            @RequestParam("name") String name){

        System.out.println(address + name);
        String url =  address;/*http下载地址*/

        String localPath = "/home/alison/Documents/allgit/"+ principal.getName() + "/" + name ;

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

            return "redirect:/student/gitlogcheck";

        }
        /*没有就clone*/
        else {
            try{
                System.out.println("开始下载......");

                CloneCommand cc = Git.cloneRepository().setURI(url);
                cc.setDirectory(file).call();

                System.out.println("下载完成......");

                return "redirect:/student/gitlogcheck";


            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        return "redirect:/student/gitlogcheck?error";

    }

}
