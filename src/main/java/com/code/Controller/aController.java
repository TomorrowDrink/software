package com.code.Controller;

import com.code.Entity.JsonResponse;
import com.code.Entity.PaperInfo;
import com.code.Entity.User;
import com.code.Service.PaperInfoService;
import com.code.Service.UserService;
/*import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class aController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaperInfoService paperInfoService;


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

     /*   org.gitlab4j.api.models.User gitUser = new org.gitlab4j.api.models.User();
        String email = id + "@pop.zjgsu.edu.cn";
        gitUser.setEmail(email);
        gitUser.setName(name);
        gitUser.setUsername(String.valueOf(id));
        
        GitLabApi gitLabApi = new GitLabApi("http://gitlab.example.com:30080", "iUtVKCxSA2sSpDwsjtTE");
        try {
            gitLabApi.getUserApi().createUser(gitUser,"123456789",10);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }*/

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

//    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping("/areview")
    public String areview(@ModelAttribute PaperInfo paperInfo, Model model){
        List<PaperInfo> list = paperInfoService.getAllkt();
        model.addAttribute("initdata",list);
        return "areview";
    }

//    @PreAuthorize("hasRole('ROLE_TEACHER')")
//    @ResponseBody
    @GetMapping("/data")
    public JsonResponse<PaperInfo> getData(Model model) {
        List<PaperInfo> list = paperInfoService.getAll();
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
        String tutorname = request.getParameter("tutorname");

        /*if(checkValue1.equals("all1")&&checkValue2.equals("all2")&&stuname.trim().equals("")){ getData(); }*/
        /*else if(checkValue1.equals("all1")&&!checkValue2.equals("all2")&&!stuname.equals(null)){}*/

        if(!stuname.trim().equals(""))System.out.println(stuname);
        if(!tutorname.trim().equals(""))System.out.println(tutorname);

        System.out.println(checkValue1);System.out.println(checkValue2);

        List<PaperInfo> list1,list2,list3;
        list1 = (List<PaperInfo>) paperInfoService.findPaperInfoById(checkValue1);
        list2 = (List<PaperInfo>) paperInfoService.findPaperInfoByState(checkValue2);
        list3 = (List<PaperInfo>) paperInfoService.findPaperInfoByTaskAndState(checkValue1,checkValue2);
        JsonResponse<PaperInfo> response = new JsonResponse<PaperInfo>(list3);
        return response;
    }

    @PostMapping("/addrecord")
    public String addData(@RequestParam("txt_taskname") String taskname,
                                           @RequestParam("txt_stuname") String stuname,
                                           @RequestParam("txt_tutorname") String tutorname,
                                           @RequestParam("txt_tutorid") String tutorid){

        List<PaperInfo> list = paperInfoService.findPaperInfoByMaxId();
        PaperInfo paperInfo = new PaperInfo();

        int newId = Integer.parseInt(list.get(0).getId().toString())+1;
        int tutorId = Integer.parseInt(tutorid);
        paperInfo.setId(newId);
        paperInfo.setState("待评阅");
        paperInfo.setTaskname(taskname);
        paperInfo.setStuname(stuname);
        paperInfo.setTutorname(tutorname);
        paperInfo.setTutorid(tutorId);
        paperInfo.setType("开题报告");
        paperInfoService.addRecord(paperInfo);

        return "redirect:/admin/areview";
    }

    @PostMapping("/delrecord")
    public String delData(@RequestParam("del_stuname") String stuname){
        paperInfoService.delRecord(stuname);
        return "redirect:/admin/areview";

    }

    @PostMapping("/editrecord")
    public String editData(@RequestParam("edit_stuname1") String stuname,
                           @RequestParam("edit_tutorname") String newtutorname,
                           @RequestParam("edit_state") String newstate){
        System.out.println("stuname"+stuname);
        System.out.println("tutorname"+newtutorname);
        System.out.println("state"+newstate);

        User user = userService.findUserByName(newtutorname);
        int tutorid = user.getId();
        System.out.println("tutorid"+tutorid);
        String type = "开题报告";
        paperInfoService.editRecord(newstate,newtutorname,tutorid,type,stuname);
//        List<PaperInfo> list = paperInfoService.findPaperInfoByStunameAndType(stuname,"开题报告");
//        for(PaperInfo paperInfo: list){
//            paperInfo.setTutorname(newtutorname);
//            paperInfo.setState(newstate);
//        }

        return "redirect:/admin/areview";

    }

    @GetMapping("/crossproposal")
    public String crossproposal(Model model){
        List<User> user = userService.findUsernameByRole(2);
        for(User users:user){
            System.out.println(users.getName());
        }
        List<PaperInfo> list = paperInfoService.getAlllunwen();
        model.addAttribute("initdata",list);
        model.addAttribute("teacher",user);
        return "crossproposal";
    }

    @PostMapping("/crossproposal")
    public String crosstutor(@RequestParam("tname") String tname,
                             @RequestParam("pid") String pid){
        System.out.println(tname +"------------------------"+ pid);
        return "redirect:/admin/crossproposal";

    }

}
