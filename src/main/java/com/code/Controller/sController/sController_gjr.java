package com.code.Controller.sController;

import com.code.Config.StorageFileNotFoundException;
import com.code.Entity.Filesss;
import com.code.Service.FilesssService;
import com.code.Service.StorageService;
import com.code.Service.TaskService;
import com.code.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequestMapping("/student")
public class sController_gjr {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @Autowired
    private StorageService storageService;

    @GetMapping("/renwushu")
    public String rlistUploadedFiles(Model model, Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllAssignment(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(SController_srw.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "renwushu";
    }

    @GetMapping("/wenxian")
    public String wlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllReview(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(SController_srw.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "wenxian";
    }

    @GetMapping("/kaiti")
    public String klistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllOpeningReport(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(SController_srw.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "kaiti";
    }

    @GetMapping("/zhongqi")
    public String zlistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllMidterm(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(SController_srw.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "zhongqi";
    }

    @GetMapping("/guocheng")
    public String glistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllProcess(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(SController_srw.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "guocheng";
    }

    @GetMapping("/lunwen")
    public String llistUploadedFiles(Model model,Principal principal) throws IOException {
        model.addAttribute("files", storageService.loadAllPaper(principal).map(
                path -> MvcUriComponentsBuilder.fromMethodName(SController_srw.class,
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
                                    RedirectAttributes redirectAttributes, Principal principal) {
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
