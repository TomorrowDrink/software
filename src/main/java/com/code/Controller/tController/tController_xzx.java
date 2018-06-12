package com.code.Controller.tController;


import com.code.Entity.Grade;
import com.code.Entity.PaperInfo;
import com.code.Entity.User;
import com.code.Service.GradeService;
import com.code.Service.PaperInfoService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_TEACHER')")
@RequestMapping("/teacher")
public class tController_xzx {

    @Autowired
    private UserService userService;


    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private GradeService gradeService;


    /**
     * 教师评阅列表界面
     */

    @GetMapping("/review")
    public String literaturereview(Model model, Principal principal) {

        int tutorid = new Integer(principal.getName()).intValue();
        List<PaperInfo> list = paperInfoService.findPaperInfoByTutoridAndType(tutorid, "文献综述");
        model.addAttribute("initdata", list);
        return "literaturereview";
    }


    @GetMapping("/ktreview")
    public String ktreview(Model model, Principal principal) {

        int tutorid = new Integer(principal.getName()).intValue();
        List<PaperInfo> list = paperInfoService.findPaperInfoByTutoridAndType(tutorid, "开题报告");
        model.addAttribute("initdata", list);
        return "ktreview";
    }


    @GetMapping("/lunwenreview")
    public String lunwenreview(Model model, Principal principal) {

        int tutorid = new Integer(principal.getName()).intValue();
        List<PaperInfo> list = paperInfoService.findPaperInfoByTutoridAndType(tutorid, "论文");
        model.addAttribute("initdata", list);
        return "lunwenreview";
    }

    /**
     * 教师评阅界面
     */
    @RequestMapping("/reviewshow")
    public String review(@RequestParam("stuname") String stuname,
                         @RequestParam("taskname") String taskname,
                         @RequestParam("type") String type,
                         @RequestParam("id") int id,
                         @RequestParam("score") int score,
                         @RequestParam("comment") String comment,
                         @RequestParam("tutorname") String tutorname,
                         Model model) {
        model.addAttribute("stuname", stuname);
        model.addAttribute("taskname", taskname);
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        if (paperInfoService.findScoreById(id).getScore() == null) {
            score = 0;
        } else {
            score = paperInfoService.findScoreById(id).getScore();
        }
        comment = paperInfoService.findCommentById(id).getComment();
        model.addAttribute("score", score);
        model.addAttribute("comment", comment);
        model.addAttribute("path", "/static/file/sample.pdf");
        return "review";
    }

    /**
     * 教师评分
     */
    @RequestMapping("/editScore")
    public String editScore(@RequestParam("stuname") String stuname,
                            @RequestParam("taskname") String taskname,
                            @RequestParam("type") String type,
                            @RequestParam("id") String stringid,
                            @RequestParam("score") String stringscore,
                            @RequestParam("comment") String comment,
                            RedirectAttributes attr,
                            Principal principal,
                            Model model) {
        PaperInfo paperInfo;
        int id = Integer.parseInt(stringid);
        String crosstutorname = paperInfoService.findPaperInfoById(id).getTutorname();
        int tutorid = new Integer(principal.getName()).intValue();
//        String flag = "crossproposal";
        try {
            int score = Integer.parseInt(stringscore);
            if (score >= 0 && score <= 100) {
                paperInfoService.editScoreAndComment(score, comment, id);
                if (score >= 60) {
                    paperInfoService.editState(id, "已通过");
                } else {
                    paperInfoService.editState(id, "未通过");
                }
                attr.addAttribute("stuname", stuname);
                attr.addAttribute("taskname", taskname);
                attr.addAttribute("type", type);
                attr.addAttribute("id", id);
                attr.addAttribute("score", score);
                attr.addAttribute("comment", comment);
                attr.addAttribute("tutorname", crosstutorname);
                if (crosstutorname.equals(userService.findUserById(tutorid).getUsername())) {
                    return "redirect:/teacher/reviewshow";//show?stuname&taskname&type&id&score&comment
                } else {
                    return "redirect:/teacher/reviewshow?flag";//show?stuname&taskname&type&id&score&comment
                }
            } else {
                score = 0;
                attr.addAttribute("stuname", stuname);
                attr.addAttribute("taskname", taskname);
                attr.addAttribute("type", type);
                attr.addAttribute("id", id);
                attr.addAttribute("score", score);
                attr.addAttribute("comment", comment);
                attr.addAttribute("tutorname", crosstutorname);
                if (crosstutorname.equals(userService.findUserById(tutorid).getUsername())) {
                    return "redirect:/teacher/reviewshow?error";//show?stuname&taskname&type&id&score&comment
                } else {
                    return "redirect:/teacher/reviewshow?error&&flag";//show?stuname&taskname&type&id&score&comment
                }
            }
        } catch (NumberFormatException e) {
            int score = 0;
            attr.addAttribute("stuname", stuname);
            attr.addAttribute("taskname", taskname);
            attr.addAttribute("type", type);
            attr.addAttribute("id", id);
            attr.addAttribute("score", score);
            attr.addAttribute("comment", comment);
            attr.addAttribute("tutorname", crosstutorname);
            if (crosstutorname.equals(userService.findUserById(tutorid).getUsername())) {
                return "redirect:/teacher/reviewshow?error";//show?stuname&taskname&type&id&score&comment
            } else {
                return "redirect:/teacher/reviewshow?error&&flag";//show?stuname&taskname&type&id&score&comment
            }
        }
//        }else{
//            return "redirect:/teacher/reviewshow?error";
//        }

    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * 教师评阅状态查询
     */

    @RequestMapping("/selstate")
    public String selState(@RequestParam("stateSelection") String state,
                           @RequestParam("type") String type,
                           @RequestParam("flag") String flag,
                           RedirectAttributes attr,
                           Model model, Principal principal) {
        System.out.println("selectstate" + state);
        List<PaperInfo> list;
        int tutorid = new Integer(principal.getName()).intValue();
        User user = userService.findUserByUsername(principal.getName());

        if (state.equals("allstate")) {
            if(flag.equals("tcross")){
                list = paperInfoService.findByCrosstutor(user.getName());
            }else {
                list = paperInfoService.findPaperInfoByTutoridAndType(tutorid, type);
            }
        } else {
            if(flag.equals("tcross")){
                list = paperInfoService.findByCrosstutorState(user.getName(),state);
            }else {
                list = paperInfoService.findPaperInfoByTutoridTypeState(tutorid, state, type);
            }
        }
        model.addAttribute("initdata", list);

//        attr.addAttribute("flag",flag);
        if (flag.equals("literaturereview")) {
            return "literaturereview";
        } else if (flag.equals("ktreview")) {
            return "ktreview";
        } else if (flag.equals("lunwenreview")) {
            return "lunwenreview";
        } else if(flag.equals("tcross")){
            return "tcross";
        }else{
            return "tcross";
        }
    }

    /**
     * 教师交叉评阅列表
     */
    @GetMapping("/tcross")
    public String tcross(Principal principal,
                         Model model) {
        User user = userService.findUserByUsername(principal.getName());
        List<PaperInfo> lunwen = paperInfoService.findByCrosstutor(user.getName());

        model.addAttribute("initdata", lunwen);
        return "tcross";
    }

    /**
     * 教师评分评语提交
     */
    @GetMapping("/tcomment")
    public String tcomment() {
        return "test";
    }

    @PostMapping("/tcomment")
    public String tcomment(
            @RequestParam("score") Integer score,
            @RequestParam("comment") String comment,
            @RequestParam("id") Integer id,
            Principal principal) {

        User user = userService.findUserById(new Integer(principal.getName()));
        String tutorname = user.getName();
        PaperInfo paperInfo = new PaperInfo();
        int tutorid = new Integer(principal.getName());
        if(score.equals("null")){
            score = 0;
        }
        if(comment.equals(null)){
            comment = "无";
        }
        paperInfo.setScore(score);
        paperInfo.setComment(comment);
        paperInfo.setId(id);
        System.out.println("-----------------------------------------");
        System.out.println("id:" + id + "score:" + score + "comment:" + comment);
        System.out.println("-----------------------------------------");

        paperInfoService.editScoreAndComment(score, comment, id);

        return "redirect:/teacher/test";
    }

    /**
     * 学生成绩查看
     */
    @GetMapping("/allgrades")
    public String allgrades(Model model, Principal principal) {
        int tutorid = new Integer(principal.getName()).intValue();
        List<Grade> list = gradeService.findGradesByTutorid(tutorid);
        model.addAttribute("initdata", list);
        return "allgrades";
    }

    /**
     * 教师推优
     */
    @GetMapping("/isgreat")
    public String isgreat(Model model, Principal principal,
                          @RequestParam("sno") int sno,
                          @RequestParam("isgreat") int isgreat,
                          @RequestParam("advice") String advice) {

        int tutorid = new Integer(principal.getName()).intValue();
        gradeService.editIsgreat(sno,isgreat,advice);
        return "allgrades";
    }

    /**
     * 教师推优状态查询
     */

    @RequestMapping("/selgreat")
    public String selGreat(@RequestParam("stateSelection") String great,
//                           @RequestParam("flag") String flag,
                           RedirectAttributes attr,
                           Model model, Principal principal) {
        System.out.println("selectgreat" + great);
        List<Grade> list;
        int tutorid = new Integer(principal.getName()).intValue();

        if (great.equals("allstate")) {
            list = gradeService.findGradesByTutorid(tutorid);
            model.addAttribute("initdata", list);
//            model.addAttribute("stateSelectionValue1", type);
        } else if (great.equals("已推优")) {
            list = gradeService.findGradesByTutoridIsgreat(tutorid,1);
            model.addAttribute("initdata", list);
//            model.addAttribute("stateSelectionValue2", type);
        } else if (great.equals("未推优")) {
            list = gradeService.findGradesByTutoridIsgreat(tutorid, 0);
            model.addAttribute("initdata", list);
//            model.addAttribute("stateSelectionValue3", type);
        }
        return "allgrades";
    }

}
