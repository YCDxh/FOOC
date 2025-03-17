package com.YCDxh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.hutool.captcha.*;

import java.io.IOException;

/**
 * 验证码控制器，负责生成并返回验证码图片及存储验证码文本到Session。
 *
 * @author YCDxhg
 * @since 2025-03-17
 */
@RestController
public class CaptchaController {

    /**
     * 生成验证码图片并存储到Session，响应前端请求。
     *
     * @param request  HTTP请求对象，用于获取Session
     * @param response HTTP响应对象，用于输出验证码图片
     * @throws IOException 若输出流操作失败时抛出
     */
    @GetMapping("/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 生成验证码（宽、高、位数、干扰线数量）
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        // 将验证码存入Session
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captcha.getCode().toLowerCase()); // 转小写存储
        // 设置响应头
        response.setContentType("image/png");
        response.setHeader("Pragma", "No-cache");
        // 输出图片流
        captcha.write(response.getOutputStream());
    }
}
