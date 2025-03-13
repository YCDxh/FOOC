package com.YCDxh.service.impl;

import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.CourseMapper;
import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.model.entity.Course;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.repository.CourseRepository;
import com.YCDxh.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author YCDxhg
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    private final CourseMapper courseMapper;
    private Validator validator;


    @Override
    public CourseDTO.CourseResponse createCourse(CourseDTO.CreateRequest request) {
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

        return courseMapper.toCourseResponse(savedCourse);
    }


    @Override
    public CourseDTO.CourseResponse getCourseById(Long courseId) {
        if (courseId == null) {
            throw new UserException(ResponseCode.PARAM_ERROR);
        }
        return courseMapper.toCourseResponse
                (courseRepository.findById(courseId).orElseThrow(() ->
                        new UserException(ResponseCode.COURSE_NOT_EXIST)));
    }
}
