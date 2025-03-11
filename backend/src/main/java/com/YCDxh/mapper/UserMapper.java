package com.YCDxh.mapper;



import com.YCDxh.model.dto.UserDTO;
import com.YCDxh.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "courseCount", ignore = true)
    UserDTO.UserResponse toResponse(User user);

    default UserDTO.UserResponse toResponseWithCourseCount(User user, Integer courseCount) {
        UserDTO.UserResponse response = toResponse(user);
        response.setCourseCount(courseCount);
        return response;
    }
}