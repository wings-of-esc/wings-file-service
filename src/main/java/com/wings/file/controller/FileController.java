package com.wings.file.controller;

import com.wings.file.model.FileDO;
import com.wings.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    private FileService service;

    @PostMapping("/upload")
    public FileDO uploadFile(
            @RequestParam("name") String name, @RequestParam("file") MultipartFile file, @RequestParam("isProfileImage") boolean isProfileImg) {
        return service.upload(name, file, isProfileImg);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
        Resource resource = service.download(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
