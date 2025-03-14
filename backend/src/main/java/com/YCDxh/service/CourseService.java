package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.CourseDTO;

public interface CourseService {
    ApiResponse<CourseDTO.CourseResponse> createCourse(CourseDTO.CreateRequest request);

    ApiResponse<CourseDTO.CourseResponse> getCourseById(Long courseId);

}
