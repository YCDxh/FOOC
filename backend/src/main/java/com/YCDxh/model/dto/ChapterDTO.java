package com.YCDxh.model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class ChapterDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotNull(message = "课程ID不能为空")
        @Min(value = 1, message = "课程ID必须大于0")
        private Long courseId;

        @NotBlank(message = "章节标题不能为空")
        @Size(min = 2, max = 100, message = "章节标题长度需在2-100字符之间")
        private String title;

        @Size(max = 5000, message = "章节内容不能超过5000字符")
        private String content;

        @Min(value = 0, message = "章节排序必须大于等于0")
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