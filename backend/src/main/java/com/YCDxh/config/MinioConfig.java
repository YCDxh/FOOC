package com.YCDxh.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {


    private String endpoint = "http://127.0.0.1:9005";
    private String bucketName = "fooc";
    private String accessKey = "F1473ZRkXJviQF2W0uaK";
    private String secretKey = "MoRvps7LrXWu0kMj7OVOlr8H12KNlqlgFRSb9Zxx";

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
