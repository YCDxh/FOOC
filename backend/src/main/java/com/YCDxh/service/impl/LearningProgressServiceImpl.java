package com.YCDxh.service.impl;

import com.YCDxh.mapper.LearningProgressMapper;
import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.dto.LearningProgressDTO;
import com.YCDxh.model.entity.LearningProgress;
import com.YCDxh.model.entity.User;
import com.YCDxh.repository.ChapterRepository;
import com.YCDxh.repository.LearningProgressRepository;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author YCDxhg
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LearningProgressServiceImpl implements LearningProgressService {
    private final LearningProgressRepository learningProgressRepository;
    private final LearningProgressMapper learningProgressMapper;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;

    @Override
    public ApiResponse<LearningProgressDTO.ProgressResponse> updateProgress(Long userId, LearningProgressDTO.UpdateRequest request) {
        LearningProgress learningProgress = learningProgressRepository.findByChapterChapterIdAndStudentUserId(request.getChapterId(), userId);
        learningProgress.setIsCompleted(request.getIsCompleted());
        if (request.getIsCompleted()) {
            learningProgress.setCompletedAt(LocalDateTime.now());
        }
        LearningProgress savedProgress = learningProgressRepository.save(learningProgress);
        return ApiResponse.success(learningProgressMapper.toProgressResponse(savedProgress));

    }

    @Override
    public ApiResponse<LearningProgressDTO.ProgressResponse> getProgressById(Long progressId) {
        return null;
    }

    @Override
    public void createProgress(Long userId, Long chapterId) {
        LearningProgress learningProgress = new LearningProgress();

        learningProgress.setStudent(userRepository.findByUserId(userId));
        learningProgress.setChapter(chapterRepository.findByChapterId(chapterId));
        learningProgress.setIsCompleted(false);
        learningProgress.setCompletedAt(null);

        learningProgressRepository.save(learningProgress);
    }

    @Override
    public void deleteProgress(Long chapterId, Long userId) {
        learningProgressRepository.deleteByChapterChapterIdAndStudentUserId(chapterId, userId);
    }

    @Override
    public ApiResponse<LearningProgressDTO.ProgressResponse> getProgressByUserIdAndChapterId(Long userId, Long chapterId) {
//        LearningProgress learningProgress = learningProgressRepository.findByChapterChapterIdAndStudentUserId(chapterId, userId);
//        Hibernate.initialize(learningProgress.getChapter());
//        return ApiResponse.success(learningProgressMapper.toProgressResponse(learningProgress));

        LearningProgress learningProgress = learningProgressRepository.myFind(chapterId, userId);

        return ApiResponse.success(learningProgressMapper.toProgressResponse(learningProgress));


        // 丑陋
//        LearningProgressDTO.ProgressResponse learningProgressDTO = new LearningProgressDTO.ProgressResponse();
//        learningProgressDTO.setProgressId(learningProgress.getProgressId());
//        learningProgressDTO.setChapterId(learningProgress.getChapter().getChapterId());
//        learningProgressDTO.setIsCompleted(learningProgress.getIsCompleted());
//        learningProgressDTO.setCompletedAt(learningProgress.getCompletedAt());
//        return ApiResponse.success(learningProgressDTO);
    }
}
