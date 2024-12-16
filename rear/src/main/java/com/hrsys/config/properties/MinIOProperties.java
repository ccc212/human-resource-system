package com.hrsys.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOProperties {
    // MinIO地址
    private String endpoint;
    // MinIO accessKey
    private String accessKey;
    // MinIO secretKey
    private String secretKey;
    // MiniO桶名称
    private String bucket;
}