package com.YCDxh.controller;

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


}
