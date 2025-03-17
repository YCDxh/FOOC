package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.ChapterDTO;

public interface ChapterService {
    ApiResponse<ChapterDTO.ChapterResponse> createChapter(ChapterDTO.CreateRequest request);

    ApiResponse<ChapterDTO.ChapterResponse> getChapterById(Long chapterId);

    ApiResponse<ChapterDTO.ChapterResponse> deleteChapter(Long chapterId);

    ApiResponse<ChapterDTO.ChapterResponse> getChapterByCourseId(Long courseId);
}
