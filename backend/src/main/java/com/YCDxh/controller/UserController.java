package com.YCDxh.controller;


import com.YCDxh.aop.Log;
import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.UserMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.security.MyUserDetails;
import com.YCDxh.service.UserService;
import com.YCDxh.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.YCDxh.utils.LogUtils.logKeep;

/**
 * @author YCDxhg
 */
@Slf4j
@RestController
@Api(value = "userController", tags = "user")
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @ApiOperation(value = "根据ID获取用户信息")
    @GetMapping("/{id}")
    public ApiResponse<UserDTO.UserResponse> getUserById(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @ApiOperation(value = "用户登陆")
    @PostMapping("/login")
    @Log
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO.LoginRequest loginRequest,
                                   HttpServletRequest request) {

        try {
            UserDTO.UserResponse userResponse = userService.login(loginRequest, request);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), userResponse));

        } catch (UserException e) {
            return ResponseEntity.status(e.getCode())
                    .body(new ApiResponse<>(e.getCode(), e.getMessage(), null));
        }

    }


    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ApiResponse<UserDTO.UserResponse> register(@Valid @RequestBody UserDTO.RegisterRequest registerRequest,
                                                      HttpServletRequest request) {
        ApiResponse<UserDTO.UserResponse> userResponse = userService.register(registerRequest);

        // 1. 从Session中获取存储的验证码（小写）
        HttpSession session = request.getSession(false); // 不自动创建新Session
        String storedCaptcha = (String) session.getAttribute("captcha");
        // 2. 验证码校验
        if (storedCaptcha == null ||
                !storedCaptcha.equalsIgnoreCase(registerRequest.getCaptcha())) { // 不区分大小写
            session.removeAttribute("captcha"); // 验证失败后清除验证码
            return new ApiResponse<>(
                    ResponseCode.INVALID_CAPTCHA.getCode(),
                    ResponseCode.INVALID_CAPTCHA.getMessage(),
                    null
            );
        }
        // 3. 验证成功后清除Session中的验证码（防复用）
        session.removeAttribute("captcha");

        return userResponse;
    }


    // UserController.java
    @ApiOperation("更新用户信息")
    @PutMapping("/{userId}")
    public ApiResponse<UserDTO.UserResponse> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody @Valid UserDTO.UpdateRequest updateRequest
    ) {
        return userService.updateUser(userId, updateRequest);
    }


}
