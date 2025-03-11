package com.YCDxh.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


public class CourseDTO {

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank
        private String title;

        private String description;
        private String category;
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
