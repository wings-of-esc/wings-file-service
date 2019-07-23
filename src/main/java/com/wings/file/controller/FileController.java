package com.wings.file.controller;

import com.wings.file.model.FileDO;
import com.wings.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    private FileService service;

    @GetMapping
    public List<String> getFiles() {
        return service.retrieveAllFiles();
    }

    @PostMapping("/upload")
    public FileDO uploadFile(
            @RequestParam("name") String name, @RequestParam("file") MultipartFile file, @RequestParam("isProfileImage") boolean isProfileImg) {
        return service.upload(name, file, isProfileImg);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, @RequestParam("isProfileImage") boolean isProfileImg) {
        Resource resource = service.download(fileName, isProfileImg);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, isProfileImg ? "img/png" : "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
