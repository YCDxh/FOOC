package com.YCDxh.controller;


import com.YCDxh.aop.Log;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
public class testController {

    private final UserService userService;


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
}
