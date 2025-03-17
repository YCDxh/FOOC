package com.YCDxh.model.dto;

import com.YCDxh.model.enums.UserRole;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * @author YCDxhg
 */
public class UserDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = "用户名不能为空")
        @Size(min = 3, max = 13, message = "用户名长度需在3-13位之间")
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,13}$",
                message = "用户名只能包含字母、数字、下划线和连字符")
        private String username;

        @NotBlank(message = "密码不能为空")
        @Size(min = 6, max = 20, message = "密码长度需在6-20位之间")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$",
                message = "密码需包含字母和数字")
        private String password;

        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String email;

        @NotBlank(message = "验证码不能为空")
        @Size(min = 4, max = 4, message = "验证码长度需为4")
        private String captcha;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        @NotNull(message = "用户ID不能为空")
        @Min(value = 1, message = "用户ID必须大于0")
        private Long userId;

        @Size(min = 3, max = 50, message = "用户名长度需在3-50位之间")
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,50}$",
                message = "用户名只能包含字母、数字、下划线和连字符")
        private String username;

        @Email(message = "邮箱格式不正确")
        private String email;

        @Size(min = 6, message = "密码长度至少6位")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
                message = "密码需包含字母和数字")
        private String password;

        @NotBlank(message = "旧密码不能为空")
        private String oldPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "用户名不能为空")
        @Size(min = 3, max = 13, message = "用户名长度需在3-13位之间")
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,13}$",
                message = "用户名只能包含字母、数字、下划线和连字符")
        private String username;

        @NotBlank(message = "密码不能为空")
        @Size(min = 6, message = "密码长度至少6位")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
                message = "密码需包含字母和数字")
        private String password;

        @NotBlank(message = "验证码不能为空")
        @Size(min = 4, max = 4, message = "验证码长度需为4")
        private String captcha;
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
