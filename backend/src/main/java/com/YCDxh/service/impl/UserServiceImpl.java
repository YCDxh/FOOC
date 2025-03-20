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
import com.YCDxh.utils.RedisCacheUtil;
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
    private final RedisCacheUtil redisCacheUtil;


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
    public UserDTO.UserResponse updateUser(UserDTO.UpdateRequest updateRequest
    ) throws UserException {

        // 1. 通过工具类获取用户信息（先查缓存，未命中则查 DB）
        User user = redisCacheUtil.getFromCacheOrDBWithLock(
                "user:info",
                updateRequest.getUserId(),
                () -> {
                    // 数据库查询（抛异常）
                    return (
                            userRepository.findById(updateRequest.getUserId())
                                    .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_EXIST))
                    );
                },
                30 * 60, // 30分钟（单位：秒）
                User.class // 目标类型
        );
        // 2. 校验旧密码（如果修改密码）
        if (StringUtils.isNotBlank(updateRequest.getPassword())) {
            if (!BCrypt.checkpw(
                    updateRequest.getOldPassword(),
                    user.getPasswordHash()
            )) {
                throw new UserException(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
            }
        }

        // 3. 构建更新对象
        User newUser = new User();
        newUser.setUserId(updateRequest.getUserId());
        if (StringUtils.isNotBlank(updateRequest.getUsername())) {
            newUser.setUsername(updateRequest.getUsername());
        }
        if (StringUtils.isNotBlank(updateRequest.getEmail())) {
            newUser.setEmail(updateRequest.getEmail());
        }
        if (StringUtils.isNotBlank(updateRequest.getPassword())) {
            newUser.setPasswordHash(BCrypt.hashpw(
                    updateRequest.getPassword(), BCrypt.gensalt()
            ));
        }

        // 4. 执行更新
        User updatedUser = userRepository.save(newUser);

        // 5. 更新缓存
        updateRedisCache(updatedUser);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public UserDTO.UserResponse getUserInfo(Long userId) {

        // 调用工具类的通用方法
        return redisCacheUtil.getFromCacheOrDBWithLock(
                "user:info",
                userId,
                () -> {
                    // 数据库查询逻辑
                    return userMapper.toResponse(
                            userRepository.findById(userId)
                                    .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_EXIST))
                    );
                },
                30 * 60, // 过期时间（30分钟）
                UserDTO.UserResponse.class // 目标类型
        );
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

    private void updateRedisCache(User user) {
        String key = "user:info:" + user.getUserId();
        UserDTO.UserResponse dto = userMapper.toResponse(user);
        String json = JSON.toJSONString(dto);
        redisTemplate.opsForValue().set(key, json, 30, TimeUnit.MINUTES);
    }


}

