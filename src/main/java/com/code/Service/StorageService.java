package com.code.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.security.Principal;
import java.util.stream.Stream;

public interface StorageService {

    void init();
    void storeAssignment(MultipartFile file,Principal principal);
    void storeReview(MultipartFile file,Principal principal);
    void storeLiterature(MultipartFile file,Principal principal);
    void storeOpeningReport(MultipartFile file,Principal principal);
    void storeMidterm(MultipartFile file,Principal principal);
    void storeProcess(MultipartFile file,Principal principal);
    void storePaper(MultipartFile file,Principal principal);
    Stream<Path> loadAllAssignment(Principal principal);
    Stream<Path> loadAllReview(Principal principal);
    Stream<Path> loadAllLiterature(Principal principal);
    Stream<Path> loadAllOpeningReport(Principal principal);
    Stream<Path> loadAllMidterm(Principal principal);
    Stream<Path> loadAllProcess(Principal principal);
    Stream<Path> loadAllPaper(Principal principal);
    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
