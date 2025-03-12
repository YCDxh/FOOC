package com.YCDxh.service;

import com.YCDxh.model.dto.CourseDTO;

public interface CourseService {
    CourseDTO.CourseResponse createCourse(CourseDTO.CreateRequest request);

    CourseDTO.CourseResponse getCourseById(Long courseId);

}
