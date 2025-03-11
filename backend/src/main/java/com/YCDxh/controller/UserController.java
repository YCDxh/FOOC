package com.YCDxh.controller;


import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "userController", tags = "user")
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @ApiOperation(value = "根据ID获取User")
    @GetMapping("/info/{id}")
    public ApiResponse<UserDTO.UserResponse> getUsers(@PathVariable("id") Long userId) {
        return ApiResponse.success(userService.getUsers(userId));
    }

    @ApiOperation(value = "登陆")
    @GetMapping("/login")
    public ApiResponse<String> login(@RequestBody UserDTO.LoginRequest loginRequest){


        return null;
    }

}
