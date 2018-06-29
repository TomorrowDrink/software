package com.code.Controller.sController;

import com.code.Entity.Grade;
import com.code.Entity.PaperInfo;
import com.code.Service.GradeService;
import com.code.Service.PaperInfoService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class sController_xzx {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PaperInfoService paperInfoService;

    @Autowired
    private GradeService gradeService;

    /**
     * 学生成绩查看
     */
    @RequestMapping("/mygrades")
    public String mygrades(Model model, Principal principal) {
        int sno = new Integer(principal.getName()).intValue();

        int wx_score = paperInfoService.findScoreByStuidAndType(sno,"文献综述").getScore();
        String wx_comment = paperInfoService.findScoreByStuidAndType(sno,"文献综述").getComment();
        model.addAttribute("wx_score", wx_score);
        model.addAttribute("wx_comment", wx_comment);

        int kt_score = paperInfoService.findScoreByStuidAndType(sno,"开题报告").getScore();
        String kt_comment = paperInfoService.findScoreByStuidAndType(sno,"开题报告").getComment();
        model.addAttribute("kt_comment", kt_comment);
        model.addAttribute("kt_score", kt_score);

        int lw_score = paperInfoService.findScoreByStuidAndType(sno,"论文").getScore();
        String lw_comment = paperInfoService.findScoreByStuidAndType(sno,"论文").getComment();
        model.addAttribute("lw_comment", lw_comment);
        model.addAttribute("lw_score", lw_score);

        String taskname = paperInfoService.findScoreByStuidAndType(sno,"文献综述").getTaskname();
        model.addAttribute("mypaper", taskname);

        int tscore = gradeService.findGradesBySno(sno);
        model.addAttribute("table_score", tscore);

        System.out.println("wx_score----"+wx_score);
        System.out.println("wx_comment----"+wx_comment);

        return "mygrades";
    }

}
