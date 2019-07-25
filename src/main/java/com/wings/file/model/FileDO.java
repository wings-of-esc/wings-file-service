package com.wings.file.model;

import org.springframework.hateoas.Link;

public class FileDO {
    private String fileName;
    private Link filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Link getFilePath() {
        return filePath;
    }

    public void setFilePath(Link filePath) {
        this.filePath = filePath;
    }

    public FileDO(String fileName, Link filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public FileDO() {

    }
}
