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
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    private FileService service;

    @GetMapping
    public List<FileDO> getFiles() {
        List<String> fileNames = service.retrieveAllFiles();
        return fileNames.stream()
                    .map(fileName -> new FileDO(fileName, linkTo(methodOn(getClass()).download(fileName)).withSelfRel()))
                        .collect(Collectors.toList());
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        Resource resource = service.download(fileName, false);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public FileDO upload(
            @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        FileDO fileDO = service.upload(name, file, false);
        fileDO.setFilePath(linkTo(methodOn(getClass())
                .download(fileDO.getFileName())).withSelfRel());
        return fileDO;
    }

    @GetMapping("/profile-img/{fileName:.+}")
    public ResponseEntity<Resource> retrieveProfileImage(@PathVariable("fileName") String fileName) {
        Resource resource = service.download(fileName, true);
        return ResponseEntity.ok().body(resource);
    }

    @PostMapping("/profile-img/{memberId}")
    public FileDO uploadProfileImage(@PathVariable("memberId") String memberId,
                                   @RequestParam("file") MultipartFile file) {
        FileDO fileDO = service.upload(memberId, file, true);
        fileDO.setFilePath(linkTo(methodOn(getClass())
                .retrieveProfileImage(fileDO.getFileName())).withSelfRel());
        return fileDO;
    }

}
