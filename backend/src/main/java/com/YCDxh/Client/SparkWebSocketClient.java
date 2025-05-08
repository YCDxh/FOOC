package com.YCDxh.Client;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SparkWebSocketClient extends WebSocketListener {

    private final Gson gson = new Gson();
    private volatile String responseContent = "";
    private final CountDownLatch latch = new CountDownLatch(1);

    private String currentResponse = "";

    public String sendQuestionAndWaitForResponse(String question, String authUrl) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        String wsUrl = authUrl.replace("http://", "ws://").replace("https://", "wss://");

        Request request = new Request.Builder().url(wsUrl).build();
        WebSocket webSocket = client.newWebSocket(request, this);
        sendRequest(webSocket, question);

        boolean await = latch.await(300, TimeUnit.SECONDS); // 设置超时等待时间
        if (!await) {
            webSocket.close(1000, "Timeout");
            throw new RuntimeException("请求超时");
        }

        return currentResponse;
    }

    private void sendRequest(WebSocket webSocket, String question) {
        try {
            // 1. 构建请求 JSON
            JSONObject requestJson = new JSONObject();

            // 2. 构建 header
            JSONObject header = new JSONObject();
            header.put("app_id", "c9b0bce3"); // 替换为你的 app_id 或从配置注入
            header.put("uid", UUID.randomUUID().toString().substring(0, 10)); // 用户唯一标识

            // 3. 构建 parameter
            JSONObject chat = new JSONObject();
            chat.put("domain", "x1"); // 模型版本 x1/x2/x3/x4
            chat.put("temperature", 0.5); // 温度参数，控制随机性
            chat.put("max_tokens", 4096); // 最大输出 token 数量

            JSONObject parameter = new JSONObject();
            parameter.put("chat", chat);

            // 4. 构建 payload.message.text 数组
            JSONArray textArray = new JSONArray();
            // 构建模板
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "目标任务,请根据我给出的话，做出动作和语气的回应,需求说明,要求是作为一只小猫娘,可爱,活泼");

            textArray.add(systemMessage);

            // 构建用户问题对象
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question != null ? question : "介绍下科大讯飞");

            textArray.add(userMessage);

            // 构建 message 对象
            JSONObject message = new JSONObject();
            message.put("text", textArray);

            // 构建 payload
            JSONObject payload = new JSONObject();
            payload.put("message", message);

            // 5. 组装完整请求体
            requestJson.put("header", header);
            requestJson.put("parameter", parameter);
            requestJson.put("payload", payload);

            // 6. 发送 WebSocket 请求
            webSocket.send(requestJson.toString());

            // 可选：打印发送内容用于调试
            // System.out.println("发送请求：" + requestJson.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
            webSocket.close(1000, "Send Request Error");
        }
    }


    // 放在 SparkWebSocketClient 类内部
    private JSONObject buildRequestJson(String question) {
        // 使用默认值防止空指针
        String userQuestion = (question == null || question.isEmpty()) ? "介绍下科大讯飞" : question;

        // 构建 header
        JSONObject header = new JSONObject();
        header.put("app_id", "c9b0bce3"); // 应从配置中注入
        header.put("uid", UUID.randomUUID().toString().substring(0, 10));

        // 构建 parameter
        JSONObject chat = new JSONObject();
        chat.put("domain", "x1"); // 根据模型版本调整（x1/x2/x3/x4）
        chat.put("temperature", 0.5); // 温度参数
        chat.put("max_tokens", 4096); // 最大输出长度

        JSONObject parameter = new JSONObject();
        parameter.put("chat", chat);

        // 构建 payload.message.text 数组
        JSONArray textArray = new JSONArray();

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", userQuestion);
        textArray.add(userMessage);

        JSONObject message = new JSONObject();
        message.put("text", textArray);

        JSONObject payload = new JSONObject();
        payload.put("message", message);

        // 组合最终请求体
        JSONObject requestJson = new JSONObject();
        requestJson.put("header", header);
        requestJson.put("parameter", parameter);
        requestJson.put("payload", payload);

        return requestJson;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        System.out.print("大模型：");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        try {
            JsonParse result = gson.fromJson(text, JsonParse.class);
            if (result.header.code != 0) {
                System.err.println("错误码：" + result.header.code);
                webSocket.close(1000, "Error occurred");
                return;
            }

            if (result.payload != null && result.payload.choices != null &&
                    result.payload.choices.text != null && !result.payload.choices.text.isEmpty()) {

                String content = result.payload.choices.text.get(0).content;

                if (content != null && !content.isEmpty()) {
                    currentResponse += content;
                    System.out.print(content); // 流式打印 AI 的回答内容
                }
            }

            if (result.payload.choices.status == 2) {
                System.out.println("\n【AI回答结束】");
                latch.countDown(); // 响应完成
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JSON 解析失败: " + text);
        }
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        t.printStackTrace();
        if (response != null) {
            System.err.println("onFailure code: " + response.code());
        }
    }

    // 内部类定义保持不变（JsonParse、Header、Payload、Text等）

    static class JsonParse {
        public Header header;
        public Payload payload;
    }

    static class Header {
        public int code;
        public String message;
        public String sid;
        public int status;
    }

    static class Payload {
        public Choices choices;
    }

    static class Choices {
        public int status;
        public int seq;
        public List<Text> text;
    }

    static class Text {
        public String reasoning_content;
        public String content;
        public String role;
        public int index;
    }

    static class RoleContent {
        String role;
        String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
