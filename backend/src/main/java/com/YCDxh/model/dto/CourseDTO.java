package com.YCDxh.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class CourseDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "标题不能为空")
        @Size(min = 3, max = 50, message = "标题长度需在3-50字符之间")
        private String title;

        @Size(max = 500, message = "描述长度不能超过500字符")
        private String description;

        @NotBlank(message = "分类不能为空")
        @Pattern(regexp = "^(课程|编程|设计|其他)$", message = "分类必须为预定义值")
        private String category;

        @NotBlank(message = "封面图片URL不能为空")
        @URL(message = "封面图片URL格式不正确")
        private String coverUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CourseResponse {
        private Long courseId;
        private String title;
        private String description;
        private String category;
        private String coverUrl;
        private LocalDateTime createdAt;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String teacherName;
    }
}
