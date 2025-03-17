package com.YCDxh.mapper;

import com.YCDxh.model.dto.LearningProgressDTO;
import com.YCDxh.model.entity.LearningProgress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LearningProgressMapper {
    LearningProgressDTO.ProgressResponse toProgressResponse(LearningProgress progress);
}
