package com.YCDxh.controller;


import com.YCDxh.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RedisController 是一个 REST 控制器类，用于处理客户端发来的 HTTP 请求，并通过调用 RedisService
 * 执行 Redis 数据操作。它提供了三个 API 端点：设置值、获取值和删除值
 * RestController注解作用: 表示这个类是一个 RESTful 控制器，它的所有方法的返回值都会直接作为 HTTP 响应体返回。
 * RestController注解意义: 结合 @Controller 和 @ResponseBody 的功能，简化了返回 JSON 格式数据的开发工作。
 */
@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;


    /**
     * 作用: 处理 HTTP POST 请求，设置 Redis 中的键值对
     * 1.使用 @RequestParam 注解获取请求参数 key 和 value
     * 2.调用 redisService.setValue(key, value) 方法，将 key 和 value 存储到 Redis 中
     * 3.返回一个简单的字符串 "Value set" 作为响应
     * 请求：POST /set?key=myKey&value=myValue
     * 响应："Value set"
     * RequestParam注解：获取请求参数: 它从 HTTP 请求中获取参数，并将这些参数绑定到控制器方法的参数上
     */
//    @PostMapping("/set")
    @GetMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value：(" + key + ") set";
    }

    /**
     * 作用: 处理 HTTP GET 请求，从 Redis 中获取指定键的值
     * 1.使用 @RequestParam 注解获取请求参数 key
     * 2.调用 redisService.getValue(key) 方法，从 Redis 中获取 key 对应的值，并将结果转换为 String 类型
     * 3.返回获取的值
     * 请求：GET /get?key=myKey
     * 响应："myValue" // Redis 中对应 `myKey` 的值
     */
    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        return (String) redisService.getValue(key);
    }

    /**
     * 作用: 处理 HTTP POST 请求，删除 Redis 中指定键的值
     * 1.使用 @RequestParam 注解获取请求参数 key
     * 2.调用 redisService.deleteValue(key) 方法，从 Redis 中删除 key 对应的键值对
     * 3.返回一个简单的字符串 "Value deleted" 作为响应
     * 请求：POST /delete?key=myKey
     * 响应："Value deleted"
     */
//    @PostMapping("/delete")
    @GetMapping("/delete")
    public String deleteValue(@RequestParam String key) {
        redisService.deleteValue(key);
        return "Value：(" + key + ") deleted";
    }
}