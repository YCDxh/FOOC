package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.LearningProgressDTO;

public interface LearningProgressService {

    ApiResponse<LearningProgressDTO.ProgressResponse> updateProgress(Long progressId, LearningProgressDTO.UpdateRequest request);


    ApiResponse<LearningProgressDTO.ProgressResponse> getProgressById(Long progressId);

    void createProgress(Long userId, Long chapterId);
}
