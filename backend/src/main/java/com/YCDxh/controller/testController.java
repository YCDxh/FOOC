package com.YCDxh.controller;


import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
}
