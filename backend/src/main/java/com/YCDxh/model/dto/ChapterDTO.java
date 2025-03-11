package com.YCDxh.model.dto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ChapterDTO {

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateRequest {
        @NotNull
        private Long courseId;

        @NotBlank
        private String title;

        private String content;
        private Integer sortOrder;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChapterResponse {
        private Long chapterId;
        private String title;
        private String content;
        private Integer sortOrder;
    }
}