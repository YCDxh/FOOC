package com.YCDxh.service.impl;

import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.UserMapper;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO.UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
//        return null;
    }


    public UserDTO.UserResponse getUsers(Long userId) {

        return userRepository.findById(userId)
                .map(userMapper::toResponse)
                .orElseThrow(()-> new UserException(ResponseCode.USER_NOT_EXIST.getCode(), ResponseCode.USER_NOT_EXIST.getMessage()));

    }

    @Override
    public User register(UserDTO.RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserException(ResponseCode.EMAIL_OCCUPIED.getCode(), ResponseCode.EMAIL_OCCUPIED.getMessage());
        }
        User user = new User();
        user.setUsername(request.getUsername());
        // 未实现
        user.setPasswordHash(request.getPassword());

        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }
}
