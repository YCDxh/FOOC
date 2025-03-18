package com.YCDxh.controller;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.service.EnrollmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YCDxhg
 */
@Slf4j
@RestController
@Api(value = "enrollmentController", tags = "enrollment")
@RequestMapping(value = "/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/add")
    @ApiOperation(value = "自动添加选课记录")
    public ApiResponse<?> addEnrollment(Long studentId, Long courseId) {
        enrollmentService.addEnrollment(studentId, courseId);
        return ApiResponse.success("成功添加选课记录");
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除选课记录")
    @Transactional
    public ApiResponse<?> deleteEnrollment(Long studentId, Long courseId) {
        enrollmentService.deleteEnrollment(studentId, courseId);
        return ApiResponse.success("成功删除选课记录");
    }
}
