package com.code.Service.lmpl;

import com.code.Service.StorageService;
import com.code.Config.StorageException;
import com.code.Config.StorageFileNotFoundException;
//import com.code.Config.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

@PreAuthorize("hasRole('ROLE_STUDENT')")
@Service
public class FileSystemStorageServicelmpl implements StorageService {

//    private final Path rootLocation;

    private Path rootLocation;


//    @Autowired
//    public FileSystemStorageServicelmpl(StorageProperties properties) {
//        this.rootLocation = Paths.get(properties.getLocation());
//    }

    @Autowired
    public FileSystemStorageServicelmpl( ) {
        this.rootLocation = Paths.get("upload-dir/person");
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

//重命名
//            filename = filename + UUID.randomUUID();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
            String dir = simpleDateFormat.format(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            if(filename.matches(".*assignment.*")){ filename ="a" + dateFormat.format(date) + "_" + file.getOriginalFilename(); }
            if(filename.matches(".*review.*")){ filename ="b" + dateFormat.format(date) + "_" + file.getOriginalFilename();}
            if(filename.matches(".*literature.*")){ filename ="c" + dateFormat.format(date) + "_" + file.getOriginalFilename();}
            if(filename.matches(".*openingReport.*")){ filename ="d" + dateFormat.format(date) + "_" + file.getOriginalFilename();}
            if(filename.matches(".*midterm.*")){ filename ="e" + dateFormat.format(date) + "_" + file.getOriginalFilename();}
            if(filename.matches(".*process.*")){ filename ="f" + dateFormat.format(date) + "_" + file.getOriginalFilename();}
            if(filename.matches(".*paper.*")){ filename ="g" + dateFormat.format(date) + "_" + file.getOriginalFilename();}

            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAllAssignment() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*assignment.*"));
//                    .filter(path -> !path.equals(this.rootLocation))
//                    .map(path -> this.rootLocation.relativize(path)).sorted().limit(countA);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllReview() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*review.*"));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllLiterature() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*literature.*"));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllOpeningReport() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*openingReport.*"));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllMidterm() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*midterm.*"));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllProcess() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*process.*"));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllPaper() {
        try {
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(".*paper.*"));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) { return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
