package com.YCDxh.model.dto;

import com.YCDxh.model.enums.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank
        @Size(min = 3, max = 50)
        private String username;

        @NotBlank
        @Size(min = 6)
        private String password;

        @Email
        @NotBlank
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        private Long userId; // 非空校验，确保唯一性

        @Size(min = 3, max = 50)
        private String username; // 可空，允许不更新

        @Email
        private String email; // 可空，允许不更新

        @Size(min = 6)
        private String password; // 可空，允许不更新

        @NotBlank
        private String oldPassword; // 安全校验：需验证旧密码才能修改
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse {
        private Long userId;
        private String username;
        private String email;
        private UserRole role;

        private LocalDateTime createdAt;

        // 可选：展示关联信息
        private Integer courseCount;  // 用户创建的课程数量（如果是教师）

    }
}
