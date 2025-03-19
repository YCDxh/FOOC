package com.YCDxh.service;

import com.YCDxh.exception.UserException;
import com.YCDxh.model.enums.ResponseCode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CaptchaService {
    public static void validateCaptcha(String userCaptcha, HttpServletRequest request) {
        // 1. 检查request是否为null
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null");
        }

        // 2. 获取Session，如果不存在则抛出验证码错误
        HttpSession session = request.getSession(false); // 不强制创建新Session
        if (session == null) {
            throw new UserException(ResponseCode.INVALID_CAPTCHA); // 或自定义异常提示会话不存在
        }

        // 3. 检查验证码是否存在且匹配
        String storedCaptcha = (String) session.getAttribute("captcha");
        if (storedCaptcha == null || !storedCaptcha.equalsIgnoreCase(userCaptcha)) {
            throw new UserException(ResponseCode.INVALID_CAPTCHA);
        }

        // 4. 清除已使用的验证码
        session.removeAttribute("captcha");
    }
}
