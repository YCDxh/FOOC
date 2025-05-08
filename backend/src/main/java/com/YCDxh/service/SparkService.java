package com.YCDxh.service;

import com.YCDxh.Client.SparkWebSocketClient;
import com.YCDxh.config.SparkConfig;
import com.YCDxh.utils.SparkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class SparkService {

    @Autowired
    private SparkConfig sparkConfig;

    public String askQuestion(String question) throws Exception {
        String authUrl = SparkUtil.getAuthUrl(sparkConfig.getHostUrl(), sparkConfig.getApiKey(), sparkConfig.getApiSecret());
        SparkWebSocketClient client = new SparkWebSocketClient();
        return client.sendQuestionAndWaitForResponse(question, authUrl);
    }
}
