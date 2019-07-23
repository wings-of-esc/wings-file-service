package com.wings.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private String uploadDir;
    private String profileImgUploadDir;

    public String getProfileImgUploadDir() {
        return profileImgUploadDir;
    }

    public void setProfileImgUploadDir(String profileImgUploadDir) {
        this.profileImgUploadDir = profileImgUploadDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
