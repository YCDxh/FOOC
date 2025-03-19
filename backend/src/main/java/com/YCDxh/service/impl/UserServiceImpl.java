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
import com.YCDxh.utils.LogUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.normalizer.ICUBinary;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO.UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
//        return null;
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
    public ApiResponse<UserDTO.UserResponse> register(UserDTO.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(ResponseCode.EMAIL_OCCUPIED);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        // 有待商议
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        user.setEmail(request.getEmail());
        User userTemp = userRepository.save(user);
        return ApiResponse.success(userMapper.toResponse(userTemp));
    }

    /**
     * 用户登录实现
     */
    // UserServiceImpl.java（修改后）
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

