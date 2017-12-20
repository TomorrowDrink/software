//package com.code.Controller;
//
//import com.code.Config.StorageFileNotFoundException;
////import com.code.Config.StorageProperties;
//import com.code.Service.FilesssService;
//import com.code.Service.StorageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/student")
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
//public class FileUploadController {
//
//    private final StorageService storageService;
//
//    @Autowired
//    public FileUploadController(StorageService storageService) {
//        this.storageService = storageService;
//    }
//
//
//    @GetMapping("/renwushu")
//    public String rlistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllAssignment().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "renwushu";
//    }
//
//    @GetMapping("/wenxian")
//    public String wlistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllReview().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "wenxian";
//    }
//
//    @GetMapping("/fanyi")
//    public String flistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllLiterature().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "fanyi";
//    }
//    @GetMapping("/kaiti")
//    public String klistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllOpeningReport().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "kaiti";
//    }
//    @GetMapping("/zhongqi")
//    public String zlistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllMidterm().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "zhongqi";
//    }
//    @GetMapping("/guocheng")
//    public String glistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllProcess().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "guocheng";
//    }
//    @GetMapping("/lunwen")
//    public String llistUploadedFiles(Model model) throws IOException {
//        model.addAttribute("files", storageService.loadAllPaper().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//        return "lunwen";
//    }
//
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
////    @Autowired
////    private FilesssService filesssService;
//    @PostMapping("/renwushu")
//    public String rhandleFileUpload(@RequestParam("file") MultipartFile file,
//            RedirectAttributes redirectAttributes) {
//        storageService.store(file);
////        filesssService.insert(file.getOriginalFilename());
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/renwushu";
//    }
//
//    @PostMapping("/wenxian")
//    public String whandleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/wenxian";
//    }
//    @PostMapping("/fanyi")
//    public String fhandleFileUpload(@RequestParam("file") MultipartFile file,
//                                    RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/fanyi";
//    }
//    @PostMapping("/kaiti")
//    public String khandleFileUpload(@RequestParam("file") MultipartFile file,
//                                    RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/kaiti";
//    }
//    @PostMapping("/zhongqi")
//    public String zhandleFileUpload(@RequestParam("file") MultipartFile file,
//                                    RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/zhongqi";
//    }
//    @PostMapping("/guocheng")
//    public String ghandleFileUpload(@RequestParam("file") MultipartFile file,
//                                    RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/guocheng";
//    }
//    @PostMapping("/lunwen")
//    public String lhandleFileUpload(@RequestParam("file") MultipartFile file,
//                                    RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/student/lunwen";
//    }
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
//
//}
