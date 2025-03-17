package com.YCDxh.mapper;

import com.YCDxh.model.dto.ChapterDTO;
import com.YCDxh.model.entity.Chapter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    ChapterDTO.ChapterResponse toResponse(Chapter chapter);
}
