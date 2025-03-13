package com.YCDxh.utils;

import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @author YCDxhg
 */
@Slf4j
public class LogUtils {
    public static void logKeep(HttpServletRequest request, boolean loginStatus, User user, UserDTO.LoginRequest loginRequest) {
        // 生成登录流水号
        String traceId = UUID.randomUUID().toString();

        // 获取客户端信息
        String ip = request.getRemoteAddr();
        String clientComputerName = request.getRemoteHost().isEmpty() ? "Localhost" : request.getRemoteHost();
        String clientMac = "无法获取"; // HTTP协议无法直接获取MAC地址

        // 记录日志（无论成功或失败）
        log.info("登录日志: " +
                        "traceId = {}, " +
                        "用户Id = {}, " +
                        "用户名 = {}, " +
                        "客户端计算机名称 = {}, " +
                        "客户端IP = {}, " +
                        "客户端MAC地址 = {}, " +
                        "是否成功 = {}",
                traceId,
                user != null ? user.getUserId() : " ",
                user != null ? user.getUsername() : loginRequest.getUsername(),
                clientComputerName,
                ip,
                clientMac,
                loginStatus);

    }
}
