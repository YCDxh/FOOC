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


    @ApiOperation(value = "根据ID获取用户信息")
    @GetMapping("/{id}")
    public ApiResponse<UserDTO.UserResponse> getUserById(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @ApiOperation(value = "通过Redis获取用户信息")
    @GetMapping("/redis/{id}")
    public ApiResponse<UserDTO.UserResponse> getUserInfo(@PathVariable("id") Long userId) {
        try {
            UserDTO.UserResponse userResponse = userService.getUserInfo(userId);
            return ApiResponse.success(userResponse);
        } catch (UserException e) {
            return ApiResponse.fail(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation(value = "用户登陆")
    @PostMapping("/login")
    @Log
    public ApiResponse<UserDTO.UserResponse> login(
            @Valid @RequestBody UserDTO.LoginRequest loginRequest, HttpServletRequest request) {
        try {
            UserDTO.UserResponse userResponse = userService.login(loginRequest, request);
            return ApiResponse.success(userResponse);
        } catch (UserException e) {
            return ApiResponse.fail(e.getCode(), e.getMessage());
        }

    }


    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @Log
    public ApiResponse<UserDTO.UserResponse> register(
            @Valid @RequestBody UserDTO.RegisterRequest registerRequest, HttpServletRequest request) {
        try {
            UserDTO.UserResponse userResponse = userService.register(registerRequest, request);
            return ApiResponse.success(userResponse);
        } catch (UserException e) {
            return ApiResponse.fail(e.getCode(), e.getMessage());
        }

    }


    // UserController.java
    @ApiOperation("更新用户信息")
    @PutMapping("/update")
    public ApiResponse<UserDTO.UserResponse> updateUser(
            @RequestBody @Valid UserDTO.UpdateRequest updateRequest
    ) {
        try {
            UserDTO.UserResponse userResponse = userService.updateUser(updateRequest);
            return ApiResponse.success(userResponse);
        } catch (UserException e) {
            return ApiResponse.fail(e.getCode(), e.getMessage());
        }

    }


}
