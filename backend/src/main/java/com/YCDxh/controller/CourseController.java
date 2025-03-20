package com.YCDxh.controller;


import com.YCDxh.exception.UserException;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.repository.ChapterRepository;
import com.YCDxh.repository.EnrollmentRepository;
import com.YCDxh.repository.LearningProgressRepository;
import com.YCDxh.service.CourseService;
import com.YCDxh.service.EnrollmentService;
import com.YCDxh.service.LearningProgressService;
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
    private final EnrollmentRepository enrollmentRepository;
    private final LearningProgressRepository learningProgressRepository;
    private final ChapterRepository chapterRepository;


    /**
     * 创建课程
     *
     * @param request
     * @return ApiResponse
     * @throws UserException
     * @author YCDxhg
     * @date 2023/4/16 15:09
     * <p>
     * 1. 上传封面文件
     * 2. 获取文件 URL
     * 3. 回传 URL至表单
     * 4. 提交表单
     */
    @ApiOperation(value = "创建课程")
    @PostMapping("/create")
    public ApiResponse<CourseDTO.CourseResponse> createCourse(CourseDTO.CreateRequest request) {
        try {
            return ApiResponse.success(courseService.createCourse(request));
        } catch (UserException e) {
            return ApiResponse.fail(e);
        }
    }

    @ApiOperation(value = "根据课程ID获取课程信息")
    @GetMapping("/{courseId}")
    public ApiResponse<CourseDTO.CourseResponse> getCourseById(@PathVariable("courseId") Long courseId) {
        try {
            return ApiResponse.success(courseService.getCourseById(courseId));
        } catch (UserException e) {
            return ApiResponse.fail(e);
        }
    }

    @GetMapping("/search")
    @ApiOperation(value = "根据课程名称搜索课程")
    public ApiResponse<?> searchCoursesByName(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return courseService.searchCoursesByName(name, page, size);
    }

    @ApiOperation(value = "根据课程ID删除课程")
    @DeleteMapping("/{courseId}")
    public ApiResponse<?> deleteCourse(@PathVariable("courseId") Long courseId) {

        enrollmentRepository.deleteAllByCourseCourseId(courseId);

        chapterRepository.deleteAllByCourseCourseId(courseId);
        courseService.deleteCourse(courseId);
        return ApiResponse.success("删除成功");
    }


}
