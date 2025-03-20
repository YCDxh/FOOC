package com.YCDxh.service;

import com.YCDxh.exception.UserException;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.OperaLog;
import com.YCDxh.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface UserService {
    List<UserDTO.UserResponse> getAllUsers();

    ApiResponse<UserDTO.UserResponse> getUser(Long userId);

    ApiResponse<UserDTO.UserResponse> updateUser(
            Long userId, UserDTO.UpdateRequest request)
            throws UserException;

    UserDTO.UserResponse getUserInfo(Long userId);


    UserDTO.UserResponse register(UserDTO.RegisterRequest request, HttpServletRequest httpRequest);

    UserDTO.UserResponse login(UserDTO.LoginRequest request, HttpServletRequest httpRequest);


}
