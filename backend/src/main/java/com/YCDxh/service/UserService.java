package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;

import java.util.List;


public interface UserService {
    List<UserDTO.UserResponse> getAllUsers();

    ApiResponse<UserDTO.UserResponse> getUser(Long userId);

    ApiResponse<UserDTO.UserResponse> register(UserDTO.RegisterRequest request);

    User login(UserDTO.LoginRequest request);


}
