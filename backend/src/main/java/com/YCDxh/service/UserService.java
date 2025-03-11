package com.YCDxh.service;

import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO.UserResponse>getAllUsers();

    UserDTO.UserResponse getUsers(Long userId);

    User register(UserDTO.RegisterRequest request);



}
