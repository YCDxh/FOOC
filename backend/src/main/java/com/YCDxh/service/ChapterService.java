package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.ChapterDTO;

import java.util.List;

public interface ChapterService {
    ApiResponse<ChapterDTO.ChapterResponse> createChapter(ChapterDTO.CreateRequest request);

    ApiResponse<ChapterDTO.ChapterResponse> getChapterById(Long chapterId);

    ApiResponse<ChapterDTO.ChapterResponse> deleteChapter(Long chapterId);

    ApiResponse<ChapterDTO.ChapterResponse> getChapterByCourseId(Long courseId);

    ApiResponse<List<ChapterDTO.ChapterResponse>> getAllChapters(Long courseId);

    void deleteAllChapters(Long courseId);

}
