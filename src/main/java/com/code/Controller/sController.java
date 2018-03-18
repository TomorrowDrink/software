package com.code.Controller;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class sController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;



    @RequestMapping("")
    public String student(){
        return "student";
    }

    @GetMapping("/password")
    public String password(){
        return "changepassword";
    }

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

    @Autowired
    private  StorageService storageService;
    /**
     *学生选题显示
     */
    @RequestMapping(value = {"/s_TaskShow"},method = {RequestMethod.POST,RequestMethod.GET})
    public String s_TaskShow(@ModelAttribute Task task, Model model){
//        List<Task> list = taskService.getAll();
        String taskrate ="已通过";
        List<Task> list = taskService.findTaskByTaskstate(taskrate);
        System.out.println("111");
        model.addAttribute("initdata",list);
        return  "s_TaskShow";
    }
    @GetMapping("/taskdata")
    public JsonResponse<Task> get_sTaskData(Model model){
//        List<Task> list = taskService.getAll();已通过
        String taskstate ="已通过";
        List<Task> list = taskService.findTaskByTaskstate(taskstate);
        JsonResponse<Task> response = new JsonResponse<Task>(list);
        return response;
    }

    @GetMapping("/gitlab")
    public String gitlab(){
        return "gitloginput";
    }

    @GetMapping("/renwushu")
    public String rlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllAssignment(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "renwushu";
    }
    /**
     *学生MyTask显示
     */

    @PostMapping()
    @RequestMapping(value = {"/s_MyTask"},method = {RequestMethod.POST,RequestMethod.GET})
    public String s_TaskEdit(@ModelAttribute Task task, Model model,
                             Principal principal ){
        String stuid= principal.getName();

        int stu_id =new Integer(stuid).intValue();
        List<Task_s> s_list =taskService.findbystuid(stu_id);
        int task_id;
        if (s_list.isEmpty()){
            task_id=0;
        }
        else {
            task_id = s_list.get(0).getTaskid();
            System.out.println("课题id" + task_id);
        }

        List<Task> list = taskService.findTaskByTaskid(task_id);
        model.addAttribute("initdata",list);
        return  "s_MyTask";
    }

    @GetMapping("/staskdata")
    public JsonResponse<Task> get_mTaskData(Model model,Principal principal){

        String stuid= principal.getName();

        int stu_id =new Integer(stuid).intValue();
        List<Task_s> s_list =taskService.findbystuid(stu_id);
        int task_id =s_list.get(0).getTaskid();

        List<Task> list = taskService.findTaskByTaskid(task_id);
        JsonResponse<Task> response = new JsonResponse<Task>(list);
        return response;
    }



    @GetMapping("/newproject")
    public String newproject(){
        return "newproject";
    }
    @GetMapping("/wenxian")
    public String wlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllReview(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "wenxian";
    }




    /**
     * 选题addtomy
     */
    @PostMapping("/addtomy")
    public String addtomyTask(@RequestParam("addtomy_taskid") String taskid,
                              @RequestParam("addtomy_taskname") String task_name,
                              Principal principal){
        String stuid= principal.getName();
        int stu_id =new Integer(stuid).intValue();
        int task_id=new Integer(taskid).intValue();
        User user =userService.findUserById(stu_id);
        String stu_name =user.getName();
        System.out.println("学生:"+stu_id+"\n姓名:"+stu_name+"\n课题id:"+task_id+"\n课题名称:"+task_name);
        taskService.chooseTask(stu_id,stu_name,task_id,task_name);

        return "redirect:/student/s_MyTask";
    }

    /**
     *学生取消选题
     */
    @PostMapping("/del_MyTask")
    public  String del_MyTaskData(@RequestParam("del_taskid")String task_id){
        taskService.s_delMyTask(task_id);
        System.out.println("取消选课的课程标号"+task_id);
        return "redirect:/student/s_MyTask";
    }

    @GetMapping("/fanyi")
    public String flistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllLiterature(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "fanyi";
    }

    /**
     * 查询课题findtask
     */
    @RequestMapping(value = {"/s_FindTask"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String s_FindTask(@ModelAttribute Task task, Model model,
                             @RequestParam("tasktypeSelection") String task_type,
                             Principal principle) {
        int tutor_id = new Integer(principle.getName()).intValue();
        List<Task> list;

        if (task_type.equals("全部")){
            String taskstate ="已通过";
            list = taskService.findTaskByTaskstate(taskstate);
            System.out.println(list);
            model.addAttribute("tasktypeSelection_D1",task_type);
        }else if (task_type.equals("系统设计")){
            list =taskService.s_findTask(task_type);
            model.addAttribute("tasktypeSelection_D2",task_type);
        }else if(task_type.equals("算法设计")){
            list = taskService.s_findTask(task_type);
            model.addAttribute("tasktypeSelection_D3",task_type);
        }else{
            list = taskService.s_findTask(task_type);
            model.addAttribute("tasktypeSelection_D4",task_type);
        }
        model.addAttribute("initdata", list);
        return "s_TaskShow";
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

                String localPath = "D:/mylz/allgit/"+ principal.getName() + "/" + name ;
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


                /*调用gitinspector*/
                CommandLine cmdLine = new CommandLine("/home/alison/gitinspector/gitinspector.py");
//                CommandLine cmdLine = new CommandLine("cmd.exe python D:/gitinspector/gitinspector.py");
                Map map = new HashMap();
                map.put("FILE", file);
//                cmdLine.addArgument("");
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
//                    System.out.println(error);

//                    String results = "";
//                    Reader reader = new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray()));
//                    BufferedReader r = new BufferedReader(reader);
//                    String tmp = null;
//                    while ((tmp = r.readLine()) != null)
//                    {
//                        results += tmp+"\n";
//                    }
//
//
//                    System.out.println(results);
                    model.addAttribute("newLineChar", '\n');
                    model.addAttribute("out",out);

                } catch (IOException e) {
                    e.printStackTrace();
                }

        return "gitlog";
    }

    @GetMapping("/kaiti")
    public String klistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllOpeningReport(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "kaiti";
    }

    @GetMapping("/zhongqi")
    public String zlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllMidterm(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "zhongqi";
    }

    @GetMapping("/guocheng")
    public String glistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllProcess(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "guocheng";
    }

    @GetMapping("/lunwen")
    public String llistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllPaper(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "lunwen";
    }



    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @Autowired
    private FilesssService filesssService;

    @PostMapping("/renwushu")
    public String rhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storeAssignment(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss1 = new Filesss();
        filesss1.setId(Integer.valueOf(principal.getName()));
        filesss1.setFilename(dateFormat.format(date) + "_" + "assignment"+ principal.getName() + file.getOriginalFilename());
        filesss1.setAstype("Assignment");
        filesss1.setState("未审核");
        filesss1.setTaskname("rrr");
        filesssService.insert(filesss1);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/renwushu";
    }
    @PostMapping("/wenxian")
    public String whandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storeReview(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss = new Filesss();
        filesss.setId(Integer.valueOf(principal.getName()));
        filesss.setFilename(dateFormat.format(date) + "_" + "review"+ principal.getName() + file.getOriginalFilename());
        filesss.setAstype("Review");
        filesss.setState("未审核");
        filesss.setTaskname("www");
        filesssService.insert(filesss);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/wenxian";
    }
    @PostMapping("/fanyi")
    public String fhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storeLiterature(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss3 = new Filesss();
        filesss3.setId(Integer.valueOf(principal.getName()));
        filesss3.setFilename(dateFormat.format(date) + "_" + "literature"+ principal.getName() + file.getOriginalFilename());
        filesss3.setAstype("Literature");
        filesss3.setState("未审核");
        filesss3.setTaskname("fff");
        filesssService.insert(filesss3);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/fanyi";
    }
    @PostMapping("/kaiti")
    public String khandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storeOpeningReport(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss4 = new Filesss();
        filesss4.setId(Integer.valueOf(principal.getName()));
        filesss4.setFilename(dateFormat.format(date) + "_" + "openingreport"+ principal.getName() + file.getOriginalFilename());
        filesss4.setAstype("OpeningReport");
        filesss4.setState("未审核");
        filesss4.setTaskname("kkk");
        filesssService.insert(filesss4);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/kaiti";
    }
    @PostMapping("/zhongqi")
    public String zhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storeMidterm(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss5 = new Filesss();
        filesss5.setId(Integer.valueOf(principal.getName()));
        filesss5.setFilename(dateFormat.format(date) + "_" + "midterm"+ principal.getName() + file.getOriginalFilename());
        filesss5.setAstype("Midterm");
        filesss5.setState("未审核");
        filesss5.setTaskname("zzz");
        filesssService.insert(filesss5);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/zhongqi";
    }
    @PostMapping("/guocheng")
    public String ghandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storeProcess(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss6 = new Filesss();
        filesss6.setId(Integer.valueOf(principal.getName()));
        filesss6.setFilename(dateFormat.format(date) + "_" + "process"+ principal.getName() + file.getOriginalFilename());
        filesss6.setAstype("Process");
        filesss6.setState("未审核");
        filesss6.setTaskname("ggg");
        filesssService.insert(filesss6);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/guocheng";
    }
    @PostMapping("/lunwen")
    public String lhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.storePaper(file,principal);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String dir = simpleDateFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Filesss filesss7 = new Filesss();
        filesss7.setId(Integer.valueOf(principal.getName()));
        filesss7.setFilename(dateFormat.format(date) + "_" + "paper"+ principal.getName() + file.getOriginalFilename());
        filesss7.setAstype("Paper");
        filesss7.setState("未审核");
        filesss7.setTaskname("lll");
        filesssService.insert(filesss7);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/lunwen";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();}

    @ResponseBody
    @GetMapping(value = "/rtest")
    public List<Filesss> findFileByIdAndFilenameAssignment(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()), "Assignment");
    }
    @ResponseBody
    @GetMapping(value = "/wtest")
    public List<Filesss> findFileByIdAndFilenameReview(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Review");
    }
    @ResponseBody
    @GetMapping(value = "/ftest")
    public List<Filesss> findFileByIdAndFilenameLiterature(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Literature");
    }
    @ResponseBody
    @GetMapping(value = "/ktest")
    public List<Filesss> findFileByIdAndFilenameOpeningReport(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"OpeningReport");
    }
    @ResponseBody
    @GetMapping(value = "/ztest")
    public List<Filesss> findFileByIdAndFilenameMidterm(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Midterm");
    }
    @ResponseBody
    @GetMapping(value = "/gtest")
    public List<Filesss> findFileByIdAndFilenameProcess(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Process");
    }
    @ResponseBody
    @GetMapping(value = "/ltest")
    public List<Filesss> findFileByIdAndFilenamePaper(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()), "Paper");
    }


}
