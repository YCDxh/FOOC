package com.YCDxh.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.UserMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.CaptchaService;
import com.YCDxh.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author YCDxhg
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    @Transactional(readOnly = true)
    public List<UserDTO.UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public ApiResponse<UserDTO.UserResponse> getUser(Long userId) {
        if (userId == null) {
            throw new UserException(ResponseCode.PARAM_ERROR);
        }
        return ApiResponse.success(
                userRepository.findById(userId)
                        .map(userMapper::toResponse)
                        .orElseThrow(() -> new UserException(
                                ResponseCode.USER_NOT_EXIST.getCode(),
                                String.format("User with ID %d not found", userId))));
    }

    // UserServiceImpl.java
    @Override
    public ApiResponse<UserDTO.UserResponse> updateUser(
            Long userId,
            UserDTO.UpdateRequest updateRequest
    ) throws UserException {
        // 1. 校验用户是否存在
        User user = userRepository.findByUsername(updateRequest.getUsername()).orElse(null);

        if (user == null) {
            throw new UserException(ResponseCode.USER_NOT_EXIST);
        }

        // 2. 校验旧密码（如果修改密码）
        if (StringUtils.isNotBlank(updateRequest.getPassword())) {
            if (!BCrypt.checkpw(
                    updateRequest.getOldPassword(),
                    user.getPassword()
            )) {
                throw new UserException(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
            }
        }

        // 3. 更新字段（仅更新非空值）
        if (StringUtils.isNotBlank(updateRequest.getUsername())) {
            user.setUsername(updateRequest.getUsername());
        }
        if (StringUtils.isNotBlank(updateRequest.getEmail())) {
            user.setEmail(updateRequest.getEmail());
        }
        if (StringUtils.isNotBlank(updateRequest.getPassword())) {
            user.setPasswordHash(BCrypt.hashpw(
                    updateRequest.getPassword(), BCrypt.gensalt()
            ));
        }

        // 4. 执行更新
        User userTemp = userRepository.save(user);
        return ApiResponse.success(userMapper.toResponse(userTemp));
    }

    @Override
    public UserDTO.UserResponse getUserInfo(Long userId) {
        log.info("Getting user info for user: {}", userId);
        // 1. 构建Redis键名（添加命名空间）
        String key = "user:info:" + userId;

        // 2. 尝试从Redis获取缓存
        String userJson = (String) redisTemplate.opsForValue().get(key);
        log.debug("Getting user info from Redis for user: {}", userJson);
        if (userJson != null) {
            log.debug("User info cached found for user: {}", userId);
            return JSON.parseObject(userJson, UserDTO.UserResponse.class);
        }

        // 3. 数据库查询（抛出自定义异常）
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_EXIST));

        // 4. 使用统一的DTO转换方式（与项目其他方法一致）
        UserDTO.UserResponse dto = userMapper.toResponse(user);

        // 5. 缓存30分钟（添加过期时间）
        redisTemplate.opsForValue().set(key, JSON.toJSONString(dto), 30, TimeUnit.MINUTES);

        return dto;
    }


    @Override
    public UserDTO.UserResponse register(UserDTO.RegisterRequest request
            , HttpServletRequest httpRequest) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(ResponseCode.EMAIL_OCCUPIED);
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserException(ResponseCode.USERNAME_OCCUPIED);
        }

        CaptchaService.validateCaptcha(request.getCaptcha(), httpRequest);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        User userTemp = userRepository.save(user);

        StpUtil.login(userTemp.getUserId());
        return userMapper.toResponse(userTemp);
    }

    /**
     * 用户登录实现
     */
    @Override
    public UserDTO.UserResponse login(UserDTO.LoginRequest request
            , HttpServletRequest httpRequest) {

        CaptchaService.validateCaptcha(request.getCaptcha(), httpRequest);

        // 1. 检查用户是否存在, 密码是否正确
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UserException(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }
        // 2. 登录，生成token
        StpUtil.login(user.getUserId());
        return userMapper.toResponse(user);
    }
}

