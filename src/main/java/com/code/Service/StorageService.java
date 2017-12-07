package com.code.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAllAssignment();
    Stream<Path> loadAllReview();
    Stream<Path> loadAllLiterature();
    Stream<Path> loadAllOpeningReport();
    Stream<Path> loadAllMidterm();
    Stream<Path> loadAllProcess();
    Stream<Path> loadAllPaper();
    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
