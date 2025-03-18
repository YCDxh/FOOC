package com.YCDxh.mapper;

import com.YCDxh.model.dto.LearningProgressDTO;
import com.YCDxh.model.entity.LearningProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LearningProgressMapper {
    @Mapping(source = "chapter.chapterId", target = "chapterId")
    LearningProgressDTO.ProgressResponse toProgressResponse(LearningProgress progress);
}
