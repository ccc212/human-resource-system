package com.hrsys.strategy.storage;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface StorageStrategy {

    String uploadFile(String path, MultipartFile file);

    void downloadFile(String path, String fileName, HttpServletResponse response);

    MultipartFile getFile(String path, String fileName);

    void deleteFile(String path, String fileName);
}
