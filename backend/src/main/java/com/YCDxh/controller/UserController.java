package com.YCDxh.controller;


import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.UserMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.model.enums.ResponseCode;
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
    public ResponseEntity<?> login(@RequestBody UserDTO.LoginRequest loginRequest,
                                   HttpServletResponse response,
                                   HttpServletRequest request) {

        // 调用 Service 层验证用户
        User user = userService.login(loginRequest);
        if (user == null) {
            logKeep(request, false, null, loginRequest);
            // 用户不存在，返回错误响应
            return ResponseEntity.badRequest().body(new ApiResponse<>(
                    ResponseCode.INVALID_CREDENTIALS.getCode(),
                    ResponseCode.INVALID_CREDENTIALS.getMessage(), null));
        }
        logKeep(request, true, user, loginRequest);
        // 生成 JWT Token
        String token = JwtUtils.generateToken(user); // 建议将 JWT 逻辑封装到 Service
        //登录成功后，生成jwt令牌

        // 构建响应数据
        UserDTO.UserResponse userResponse = userMapper.toResponse(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", userResponse);
        data.put("token", token);

        // 返回成功响应
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token) // 设置响应头
                .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data));


    }


    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ApiResponse<UserDTO.UserResponse> register(@RequestBody UserDTO.RegisterRequest registerRequest) {
        ApiResponse<UserDTO.UserResponse> userResponse = userService.register(registerRequest);
        return userResponse;
    }
}
