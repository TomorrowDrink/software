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
import java.security.Principal;
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
    public void storeAssignment(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "assignment"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    @Override
    public void storeReview(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "review"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    @Override
    public void storeLiterature(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "literature"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    @Override
    public void storeOpeningReport(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "openingreport"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    @Override
    public void storeMidterm(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "midterm"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    @Override
    public void storeProcess(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "process"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void storePaper(MultipartFile file,Principal principal) {
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

            filename = dateFormat.format(date) + "_" + "paper"+ principal.getName() + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAllAssignment(Principal principal) {
        try {
            String str = ".*assignment"+principal.getName()+".*";
//            System.out.println(str);
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
//                    .filter(path -> !path.equals(this.rootLocation))
//                    .map(path -> this.rootLocation.relativize(path)).sorted().limit(countA);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllReview(Principal principal) {
        try {
            String str = ".*review"+principal.getName()+".*";
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllLiterature(Principal principal) {
        try {
            String str = ".*literature"+principal.getName()+".*";
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllOpeningReport(Principal principal) {
        try {
            String str = ".*openingreport"+principal.getName()+".*";
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllMidterm(Principal principal) {
        try {
            String str = ".*midterm"+principal.getName()+".*";
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllProcess(Principal principal) {
        try {
            String str = ".*process"+principal.getName()+".*";
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Stream<Path> loadAllPaper(Principal principal) {
        try {
            String str = ".*paper"+principal.getName()+".*";
            return Files.find(this.rootLocation, 1,(path, basicFileAttributes) -> path.toFile().getName().matches(str));
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
