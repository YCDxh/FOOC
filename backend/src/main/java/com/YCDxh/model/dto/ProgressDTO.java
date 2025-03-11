package com.YCDxh.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



public class ProgressDTO {

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class UpdateRequest {
        @NotNull
        private Long chapterId;

        @NotNull
        private Boolean isCompleted;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProgressResponse {
        private Long progressId;
        private Long chapterId;
        private Boolean isCompleted;
        private LocalDateTime completedAt;
    }
}