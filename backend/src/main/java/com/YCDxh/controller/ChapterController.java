package com.YCDxh.controller;

import com.YCDxh.aop.Log;
import com.YCDxh.mapper.ChapterMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.ChapterDTO;
import com.YCDxh.service.impl.ChapterServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(value = "ChapterController", tags = "chapter")
@RequestMapping(value = "/api/chapters")
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterMapper chapterMapper;
    private final ChapterServiceImpl chapterService;

    @PostMapping("/create")
    @ApiOperation(value = "创建章节")
    public ApiResponse<ChapterDTO.ChapterResponse> createChapter(ChapterDTO.CreateRequest request) {
        return chapterService.createChapter(request);
    }

    @PostMapping("/getChapterByCourseId")
    @ApiOperation(value = "根据课程ID获取章节信息")
    @Log
    public ApiResponse<List<ChapterDTO.ChapterResponse>> getChapterByCourseId(Long courseId) {
        return chapterService.getAllChapters(courseId);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "根据课程ID删除所有章节")
    public ApiResponse<?> deleteAllChapters(Long courseId) {
        chapterService.deleteAllChapters(courseId);
        return ApiResponse.success();
    }

}
