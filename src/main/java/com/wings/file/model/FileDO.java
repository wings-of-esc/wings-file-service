package com.wings.file.model;

public class FileDO {
    private String fileName;
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileDO(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public FileDO() {

    }
}
