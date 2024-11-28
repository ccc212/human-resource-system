package com.hrsys.strategy.storage;

import com.hrsys.config.properties.MinioProperties;
import com.hrsys.util.FileUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service("minioStorageStrategyImpl")
@RequiredArgsConstructor
@Slf4j
public class MinioStorageStrategyImpl extends AbstractStorageStrategyImpl {

    private final MinioProperties minioProperties;

    private final MinioClient minioClient;

    @Override
    public Boolean exists(String filePath) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().
                    bucket(minioProperties.getBucketName())
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @SneakyThrows
    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        String bucketName = minioProperties.getBucketName();
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path + fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(String path, String fileName, HttpServletResponse response) {
        try {
            // 获取文件流
            InputStream file = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(path + fileName)
                    .build());
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
        } catch (Exception e) {
            log.error("文件: " + fileName + " 下载时出现异常: " + e);
        }
    }

    @Override
    @SneakyThrows
    public MultipartFile get(String path, String fileName) {
        InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(path + fileName)
                .build());
        return FileUtil.inputStreamToMultipart(inputStream, fileName);
    }

    @Override
    @SneakyThrows
    public void delete(String path, String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(path + fileName)
                .build());
    }

    @Override
    @SneakyThrows
    public String getFileUrl(String fileName) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(minioProperties.getBucketName())
                        .object(fileName)
                        .expiry(1, TimeUnit.DAYS) // 一天过期时间
                        .build());
    }
}
