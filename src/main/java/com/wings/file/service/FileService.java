package com.wings.file.service;

import com.wings.file.model.FileDO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    FileDO upload(String name, MultipartFile file, boolean isProfileImage);

    Resource download(String fileName);
}
