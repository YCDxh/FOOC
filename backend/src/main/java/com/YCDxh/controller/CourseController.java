package com.YCDxh.controller;


import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author YCDxhg
 */
@Slf4j
@RestController
@Api(value = "courseController", tags = "course")
@RequestMapping(value = "/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @ApiOperation(value = "创建课程")
    @PostMapping("/create")
    public ApiResponse<CourseDTO.CourseResponse> createCourse(CourseDTO.CreateRequest request) {
        return courseService.createCourse(request);
    }

    @ApiOperation(value = "根据课程ID获取课程信息")
    @GetMapping("/{courseId}")
    public ApiResponse<CourseDTO.CourseResponse> getCourseById(@PathVariable("courseId") Long courseId) {
        return courseService.getCourseById(courseId);
    }

}
