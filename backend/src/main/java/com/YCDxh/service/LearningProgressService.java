package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.LearningProgressDTO;
import org.springframework.transaction.annotation.Transactional;


public interface LearningProgressService {

    ApiResponse<LearningProgressDTO.ProgressResponse> updateProgress(Long chapterId, LearningProgressDTO.UpdateRequest request);


    ApiResponse<LearningProgressDTO.ProgressResponse> getProgressById(Long progressId);

    ApiResponse<?> createProgress(Long userId, Long chapterId);

    ApiResponse<?> deleteProgress(Long chapterId, Long userId);

    @Transactional(readOnly = true)
    ApiResponse<LearningProgressDTO.ProgressResponse> getProgressByUserIdAndChapterId(Long userId, Long chapterId);
}
