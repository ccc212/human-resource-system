package com.hrsys.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IMinIOService {

    String uploadFile(MultipartFile file);

    void downloadFile(String fileName, HttpServletResponse response);

    void deleteFile(String fileName);

    String getPresignedUrl(String fileName);

}
