package com.YCDxh.controller;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.LearningProgressDTO;
import com.YCDxh.service.LearningProgressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "LearningProgressController", tags = "Progress")
@RequestMapping(value = "/api/learning-progress")
@RequiredArgsConstructor
public class LearningProgressController {
    private final LearningProgressService learningProgressService;

    // 更新学习进度（POST）
    @PostMapping("/update")
    @ApiOperation(value = "更新学习进度")
    public ApiResponse<LearningProgressDTO.ProgressResponse> updateProgress(
            @RequestHeader("user-id") Long userId, // 假设从Header获取用户ID
            @RequestBody @Valid LearningProgressDTO.UpdateRequest request
    ) {

        return learningProgressService.updateProgress(userId, request);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建学习进度")
    public void createProgress(
            @RequestHeader("user-id") Long userId, // 假设从Header获取用户ID
            @RequestBody @Valid LearningProgressDTO.UpdateRequest request
    ) {
        learningProgressService.createProgress(userId, request.getChapterId());
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除学习进度")
    public void deleteProgress(
            @RequestHeader("user-id") Long userId, // 假设从Header获取用户ID
            @RequestBody @Valid LearningProgressDTO.UpdateRequest request
    ) {
        learningProgressService.deleteProgress(request.getChapterId(), userId);
    }

    @PostMapping("/get")
    @ApiOperation(value = "获取学习进度")
    public ApiResponse<LearningProgressDTO.ProgressResponse> getProgress(
            @RequestHeader("user-id") Long userId, // 假设从Header获取用户ID
            @RequestBody @Valid LearningProgressDTO.UpdateRequest request
    ) {
        return learningProgressService.getProgressByUserIdAndChapterId(userId, request.getChapterId());
    }


}
