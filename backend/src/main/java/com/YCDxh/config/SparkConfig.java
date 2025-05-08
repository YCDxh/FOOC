package com.YCDxh.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spark")
@Getter
@Setter
public class SparkConfig {
    private String hostUrl = "https://spark-api.xf-yun.com/v1/x1";
    private String domain = "x1";
    private String appid = "c9b0bce3";
    private String apiSecret = "ODRmNmQ4YTdkZWNmYjdmYTExNDUwMTM0";
    private String apiKey = "a584865b662a5807c6b2ad6c2ffd02de";

    // Getters and Setters
}
