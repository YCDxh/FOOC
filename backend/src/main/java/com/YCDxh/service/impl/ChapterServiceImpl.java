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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public ApiResponse<List<ChapterDTO.ChapterResponse>> getAllChapters(Long courseId) {
        // 1. 校验课程存在
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new UserException(ResponseCode.COURSE_NOT_EXIST));
        // 重点修改：将实体列表转换为DTO列表
        List<Chapter> chapters = chapterRepository.findAllByCourse(course);
        List<ChapterDTO.ChapterResponse> responses = chapters.stream()
                .map(chapterMapper::toResponse)
                .collect(Collectors.toList());
        ; // 或使用chapterMapper.toResponseList(chapters)（若存在批量转换方法）

        return ApiResponse.success(responses);

//        return ApiResponse.success(chapterRepository.findAllByCourse(course));
    }
}
