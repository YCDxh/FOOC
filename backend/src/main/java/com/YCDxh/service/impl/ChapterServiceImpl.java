package com.YCDxh.service.impl;

import com.YCDxh.exception.UserException;
import com.YCDxh.mapper.ChapterMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.ChapterDTO;
import com.YCDxh.model.entity.Chapter;
import com.YCDxh.model.entity.Course;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.repository.ChapterRepository;
import com.YCDxh.repository.CourseRepository;
import com.YCDxh.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author YCDxhg
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;
    private final Validator validator;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponse<ChapterDTO.ChapterResponse> createChapter(
            ChapterDTO.CreateRequest request) {
        // 1. 校验DTO对象
        Set<ConstraintViolation<ChapterDTO.CreateRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // 2. 查询对应的Course对象
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new UserException(ResponseCode.COURSE_NOT_EXIST));

        Chapter chapter = new Chapter();
        chapter.setCourse(course); // 如何设置课程ID
        chapter.setTitle(request.getTitle());
        chapter.setContent(request.getContent());
        chapter.setSortOrder(request.getSortOrder());
        return ApiResponse.success(
                chapterMapper.toResponse(chapterRepository.save(chapter)));
    }

    @Override
    public ApiResponse<ChapterDTO.ChapterResponse> getChapterById(Long chapterId) {
        return null;
    }

    @Override
    public ApiResponse<ChapterDTO.ChapterResponse> deleteChapter(Long chapterId) {
        return null;
    }

    @Override
    public ApiResponse<ChapterDTO.ChapterResponse> getChapterByCourseId(Long courseId) {
        return null;
    }
}
