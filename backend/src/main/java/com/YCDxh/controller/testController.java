package com.YCDxh.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.YCDxh.aop.Log;
import com.YCDxh.mapper.UserMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
import java.util.Optional;

import io.jsonwebtoken.security.Keys;

import java.util.Base64;

/**
 * @author YCDxhg
 */
@RestController
@Api(value = "testController", tags = "test")
@RequestMapping(value = "/api/test")
@RequiredArgsConstructor
@Slf4j
public class testController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @ApiOperation(value = "获取User")
    @GetMapping("/all")
    public ApiResponse<List<UserDTO.UserResponse>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers());
    }

    @ApiOperation(value = "发送token")
    @PostMapping("/token")
    @Log
    public ApiResponse<List<UserDTO.UserResponse>> testToken(@RequestParam String token) {
        return ApiResponse.success(userService.getAllUsers());
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResponse<UserDTO.UserResponse> login(@RequestBody UserDTO.LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        StpUtil.login(user.getUserId());
        return ApiResponse.success(userMapper.toResponse(user));
    }


    @ApiOperation(value = "测试登录状态")
    @PostMapping("/loginStatus")
    public ApiResponse<?> loginStatus() {
        log.info("token :{} ,info{}", StpUtil.getTokenValue(), StpUtil.getTokenInfo());
        return ApiResponse.success(StpUtil.isLogin());
    }


}
