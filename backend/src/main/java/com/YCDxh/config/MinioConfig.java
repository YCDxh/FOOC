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


    private String endpoint = "http://0.0.0.0:9006";
    private String bucketName = "fooc";
    private String accessKey = "oRrNvZVcSgps6BOpWQYZ";
    private String secretKey = "bkIN0gsoP5etPItz63uOS5PztHXPFmLNVcInVytR";

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
