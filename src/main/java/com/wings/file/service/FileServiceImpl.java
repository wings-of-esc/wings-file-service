package com.wings.file.service;

import com.wings.file.config.FileStorageConfig;
import com.wings.file.exception.FileServiceException;
import com.wings.file.model.FileDO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileServiceImpl implements FileService {
    private final Path fileStorageLocation;
    private final Path profileImgStorageLocation;

    @Autowired
    public FileServiceImpl(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(
                fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.profileImgStorageLocation = Paths.get(
                fileStorageConfig.getProfileImgUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.profileImgStorageLocation);
        } catch (Exception ex) {
            throw new FileServiceException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public List<String> retrieveAllFiles() {
        try{
           return Files.list(this.fileStorageLocation).filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException ex) {
            throw new FileServiceException("exception in retrieve all files", ex);
        }
    }

    @Override
    public FileDO upload(String name, MultipartFile file, boolean isProfileImage) {
        String fileName = name + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileServiceException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation =
                    isProfileImage ? this.profileImgStorageLocation.resolve(fileName) : this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return new FileDO(fileName, targetLocation.toString());
        } catch (IOException ex) {
            throw new FileServiceException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource download(String fileName, boolean isProfileImage) {
        try {
            Path filePath =
                    isProfileImage ? this.profileImgStorageLocation.resolve(fileName).normalize() : this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileServiceException("File not found " + fileName, new FileNotFoundException());
            }
        } catch (MalformedURLException ex) {
            throw new FileServiceException("File not found " + fileName, ex);
        }
    }
}
