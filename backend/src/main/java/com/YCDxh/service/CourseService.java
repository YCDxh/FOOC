package com.YCDxh.service;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.model.dto.PagedResult;

public interface CourseService {
    CourseDTO.CourseResponse createCourse(CourseDTO.CreateRequest request);

    CourseDTO.CourseResponse getCourseById(Long courseId);


    ApiResponse<PagedResult<CourseDTO.CourseResponse>> searchCoursesByName(
            String courseName,
            int page,
            int size
    );

    void deleteCourse(Long courseId);

}
