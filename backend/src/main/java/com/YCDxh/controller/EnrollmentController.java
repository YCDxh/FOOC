package com.YCDxh.controller;

import com.YCDxh.service.EnrollmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("/add")
    @ApiOperation(value = "自动添加选课记录")
    public void addEnrollment(Long userId, Long courseId) {
        enrollmentService.addEnrollment(userId, courseId);
    }
}
