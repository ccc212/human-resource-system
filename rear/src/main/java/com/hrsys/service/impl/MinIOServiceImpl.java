package com.hrsys.service.impl;

import com.hrsys.config.properties.MinIOProperties;
import com.hrsys.service.IMinIOService;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements IMinIOService {

    private final MinIOProperties minioProperty;
    private final MinioClient minioClient;

    @Override
    public String uploadFile(MultipartFile file) {
        // 获取桶名
        String bucketName = minioProperty.getBucket();
        log.info("开始向桶 {} 上传文件", bucketName);
        //给文件生成一个唯一名称  当日日期-uuid.后缀名
        String folderName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        String fileName = String.valueOf(UUID.randomUUID());
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//文件后缀名
        String objectName = folderName + "-" + fileName + extName;

        String contentType = file.getContentType();
        if (contentType == null || contentType.isEmpty()) {
            // 根据文件扩展名设置默认的 contentType
            switch (extName.toLowerCase()) {
                case ".jpg":
                case ".jpeg":
                    contentType = "image/jpeg";
                    break;
                case ".png":
                    contentType = "image/png";
                    break;
                case ".gif":
                    contentType = "image/gif";
                    break;
                default:
                    contentType = "application/octet-stream"; // 默认类型
            }
        }
        try {
            // 确保在使用后关闭 InputStream
            try (InputStream inputStream = file.getInputStream()) {
                // 配置参数
                PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                        .stream(inputStream, file.getSize(), -1).contentType(contentType).build();
                //文件名称相同会覆盖
                minioClient.putObject(objectArgs);
            }
        } catch (Exception e) {
            log.error("文件上传失败: " + e);
            throw new RuntimeException(e);
        }
        log.info("文件上传成功，文件名为：{}", objectName);
        return getPresignedUrl(objectName);
    }

    @Override
    public void downloadFile(String fileName, HttpServletResponse response) {
        // 获取桶名
        String bucketName = minioProperty.getBucket();
        if (StringUtils.isBlank(fileName)) {
            log.error("文件名为空！");
            return;
        }
        try {
            // 获取文件流
            InputStream file = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/") + 1), StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            // 获取输出流
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
            log.info("文件{}下载成功", fileName);
        } catch (Exception e) {
            log.error("文件名: " + fileName + "下载文件时出现异常: " + e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        // 获取桶名
        String bucketName = minioProperty.getBucket();
        try {
            if (StringUtils.isBlank(fileName)) {
                log.error("删除文件失败，文件名为空！");
                return;
            }
            // 判断桶是否存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            // 桶存在
            if (isExist) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
            } else { // 桶不存在
                log.error("删除文件失败，桶{}不存在", bucketName);
            }
        } catch (Exception e) {
            log.error("删除文件时出现异常: " + e.getMessage());
        }
    }

    //http://prf.ccc212.cn:7484/hrsys/2024-12-16-17-20-41-c382cc2c-dee2-49ec-aa38-1b65e20a27ce.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ccc212%2F20241216%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241216T092042Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=8a35d05c5011eab8b2739eca41458f73cec256b4dcf0b4b11978deabd34568bc
    @Override
    public String getPresignedUrl(String fileName) {
        // 获取桶名
        String bucketName = minioProperty.getBucket();
        String presignedUrl = null;
        try {
            if (StringUtils.isBlank(fileName)) {
                log.error("获取文件预览url失败，文件名为空！");
                return presignedUrl;
            }
            // 判断桶是否存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                presignedUrl = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucketName)
                                .object(fileName)
                                .expiry(1, TimeUnit.DAYS) // 一天过期时间
                                .build());
                return presignedUrl;
            } else {
                log.error("获取文件预览url失败，桶{}不存在", bucketName);
            }
        } catch (Exception e) {
            log.error("获取文件预览url时出现异常: " + e.getMessage());
        }
        return presignedUrl;
    }
}
