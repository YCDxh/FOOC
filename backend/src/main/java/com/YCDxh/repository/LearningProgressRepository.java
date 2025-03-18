package com.YCDxh.repository;

import com.YCDxh.model.dto.LearningProgressDTO;
import com.YCDxh.model.entity.Course;
import com.YCDxh.model.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {

    @Query("select u from LearningProgress u where u.chapter.chapterId = :chapterId and u.student.userId = :studentId")
    LearningProgress myFind(Long chapterId, Long studentId);

    LearningProgress findByChapterChapterIdAndStudentUserId(Long chapterId, Long studentId);

    void deleteByChapterChapterIdAndStudentUserId(Long chapterId, Long studentId);


}
