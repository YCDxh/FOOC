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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author YCDxhg
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LearningProgressServiceImpl implements LearningProgressService {
    private final LearningProgressRepository learningProgressRepository;
    private final LearningProgressMapper learningProgressMapper;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;

    @Override
    public ApiResponse<LearningProgressDTO.ProgressResponse> updateProgress(Long progressId, LearningProgressDTO.UpdateRequest request) {
        LearningProgress learningProgress = learningProgressRepository.findByProgressId(progressId);
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

        learningProgressRepository.save(learningProgress);
    }
}
