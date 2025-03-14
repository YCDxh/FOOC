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
        String traceId = user != null
                ? String.format("%s-%s-%s",
                user.getUserId(),
                new Date().getTime(),
                UUID.randomUUID().toString().substring(0, 4))
                : UUID.randomUUID().toString();


        // 获取客户端信息
        String ip = request.getRemoteAddr();
        String clientComputerName = request.getRemoteHost().isEmpty() ? "Localhost" : request.getRemoteHost();
        String clientMac = "无法获取"; // HTTP协议无法直接获取MAC地址

        // 增加空值处理示例
        String userIdPart = user != null ? user.getUserId().toString() : "GUEST";
        String usernamePart = user != null ? user.getUsername() : loginRequest.getUsername();

        // 记录日志（无论成功或失败）
        log.info("登录日志: " +
                        "traceId = {}, " +
                        "用户标识 = {}({}), " +
                        "客户端信息 = {}({}), " +
                        "登录结果 = {}",
                traceId,
                userIdPart, usernamePart,
                clientComputerName, ip,
                loginStatus);


    }
}
