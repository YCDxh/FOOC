package com.YCDxh.service;

import com.YCDxh.exception.UserException;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.OperaLog;
import com.YCDxh.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService {
    List<UserDTO.UserResponse> getAllUsers();

    ApiResponse<UserDTO.UserResponse> getUser(Long userId);

    ApiResponse<UserDTO.UserResponse> updateUser(
            Long userId, UserDTO.UpdateRequest request)
            throws UserException;

    ApiResponse<UserDTO.UserResponse> register(UserDTO.RegisterRequest request);

    User login(UserDTO.LoginRequest request);


}
