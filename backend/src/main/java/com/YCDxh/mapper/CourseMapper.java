package com.YCDxh.mapper;


import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.model.entity.Course;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO.CourseResponse toCourseResponse(Course course);


}