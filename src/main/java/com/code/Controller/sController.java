package com.code.Controller;

import com.code.Config.StorageFileNotFoundException;
import com.code.Entity.Filesss;
import com.code.Entity.User;
import com.code.Service.FilesssService;
import com.code.Service.StorageService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.TinyBitSet;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alison on 17-10-29.
 */
@Controller
@RequestMapping("/student")
public class sController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @RequestMapping("")
    public String student(){
              return "student";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/password")
    public String password(){
        return "changepassword";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
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

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/renwushu")
    public String rlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllAssignment(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "renwushu";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/wenxian")
    public String wlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllReview(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "wenxian";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/fanyi")
    public String flistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllLiterature(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "fanyi";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/kaiti")
    public String klistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllOpeningReport(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "kaiti";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/zhongqi")
    public String zlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllMidterm(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "zhongqi";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/guocheng")
    public String glistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllProcess(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "guocheng";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/lunwen")
    public String llistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllPaper(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(sController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "lunwen";
    }



    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @Autowired
    private FilesssService filesssService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/renwushu")
    public String rhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss1 = new Filesss();
        filesss1.setId(Integer.valueOf(principal.getName()));
        filesss1.setFilename(file.getOriginalFilename());
        filesss1.setAstype("Assignment");
        filesss1.setState("未审核");
        filesss1.setTaskname("rrr");
        filesssService.insert(filesss1);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/renwushu";
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/wenxian")
    public String whandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss = new Filesss();
        filesss.setId(Integer.valueOf(principal.getName()));
        filesss.setFilename(file.getOriginalFilename());
        filesss.setAstype("Review");
        filesss.setState("未审核");
        filesss.setTaskname("www");
        filesssService.insert(filesss);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/wenxian";
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/fanyi")
    public String fhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss3 = new Filesss();
        filesss3.setId(Integer.valueOf(principal.getName()));
        filesss3.setFilename(file.getOriginalFilename());
        filesss3.setAstype("Literature");
        filesss3.setState("未审核");
        filesss3.setTaskname("fff");
        filesssService.insert(filesss3);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/fanyi";
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/kaiti")
    public String khandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss4 = new Filesss();
        filesss4.setId(Integer.valueOf(principal.getName()));
        filesss4.setFilename(file.getOriginalFilename());
        filesss4.setAstype("OpeningReport");
        filesss4.setState("未审核");
        filesss4.setTaskname("kkk");
        filesssService.insert(filesss4);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/kaiti";
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/zhongqi")
    public String zhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss5 = new Filesss();
        filesss5.setId(Integer.valueOf(principal.getName()));
        filesss5.setFilename(file.getOriginalFilename());
        filesss5.setAstype("Midterm");
        filesss5.setState("未审核");
        filesss5.setTaskname("zzz");
        filesssService.insert(filesss5);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/zhongqi";
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/guocheng")
    public String ghandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss6 = new Filesss();
        filesss6.setId(Integer.valueOf(principal.getName()));
        filesss6.setFilename(file.getOriginalFilename());
        filesss6.setAstype("Process");
        filesss6.setState("未审核");
        filesss6.setTaskname("ggg");
        filesssService.insert(filesss6);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/guocheng";
    }
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/lunwen")
    public String lhandleFileUpload(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,Principal principal) {
        storageService.store(file,principal);
        Filesss filesss7 = new Filesss();
        filesss7.setId(Integer.valueOf(principal.getName()));
        filesss7.setFilename(file.getOriginalFilename());
        filesss7.setAstype("Paper");
        filesss7.setState("未审核");
        filesss7.setTaskname("lll");
        filesssService.insert(filesss7);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/student/lunwen";
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();}

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/rtest")
    public List<Filesss> findFileByIdAndFilenameAssignment(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()), "Assignment");
    }
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/wtest")
    public List<Filesss> findFileByIdAndFilenameReview(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Review");
    }
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/ftest")
    public List<Filesss> findFileByIdAndFilenameLiterature(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Literature");
    }
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/ktest")
    public List<Filesss> findFileByIdAndFilenameOpeningReport(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"OpeningReport");
    }
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/ztest")
    public List<Filesss> findFileByIdAndFilenameMidterm(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Midterm");
    }
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/gtest")
    public List<Filesss> findFileByIdAndFilenameProcess(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()),"Process");
    }
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping(value = "/ltest")
    public List<Filesss> findFileByIdAndFilenamePaper(Principal principal) {
        return filesssService.findFileByidAndastype(Integer.valueOf(principal.getName()), "Paper");
    }


}
