package com.YCDxh.mapper;


import com.YCDxh.model.dto.CourseDTO;
import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.Course;
import com.YCDxh.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO.CourseResponse toCourseResponse(Course course);


}