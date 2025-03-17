package com.YCDxh.service.impl;

import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.CourseMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.model.dto.PagedResult;
import com.YCDxh.model.entity.Course;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.repository.CourseRepository;
import com.YCDxh.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author YCDxhg
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final Validator validator;


    @Override
    public ApiResponse<CourseDTO.CourseResponse> createCourse(CourseDTO.CreateRequest request) {
        // 1. 校验DTO对象
        Set<ConstraintViolation<CourseDTO.CreateRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // 2. 业务规则校验：检查课程名称是否重复
        if (courseRepository.existsByTitle(request.getTitle())) {
            throw new IllegalArgumentException("课程名称已存在");
        }

        // 3. 执行创建逻辑...
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setCoverUrl(request.getCoverUrl());
        Course savedCourse = courseRepository.save(course);

        return ApiResponse.success(courseMapper.toCourseResponse(savedCourse));
    }


    @Override
    public ApiResponse<CourseDTO.CourseResponse> getCourseById(Long courseId) {
        if (courseId == null) {
            throw new UserException(ResponseCode.PARAM_ERROR);
        }
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new UserException(ResponseCode.COURSE_NOT_EXIST));
        if (course == null) {
            throw new UserException(ResponseCode.COURSE_NOT_EXIST);
        }
        return ApiResponse.success(courseMapper.toCourseResponse(course));
    }

    @Override
    public ApiResponse<PagedResult<CourseDTO.CourseResponse>> searchCoursesByName(String courseName, int page, int size) {
        // 参数校验
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("页码和每页数量必须大于0");
        }

        // 分页参数转换
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("courseId").ascending());

        // 执行查询
        String searchTerm = "%" + courseName.toLowerCase() + "%";
        Page<CourseDTO.CourseResponse> coursePage = courseRepository.searchByName(searchTerm, pageable);

        // 转换为DTO
        PagedResult<CourseDTO.CourseResponse> result = new PagedResult<>(coursePage);
//        result.setContent(coursePage.getContent().stream()
//                .map(courseMapper::toCourseResponse)
//                .collect(Collectors.toList()));

        return ApiResponse.success(result);
    }
}
