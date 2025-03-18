package com.YCDxh.repository;

import com.YCDxh.model.dto.ChapterDTO;
import com.YCDxh.model.dto.LearningProgressDTO;
import com.YCDxh.model.entity.Chapter;
import com.YCDxh.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author YCDxhg
 */
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Chapter findByChapterId(Long chapterId);

    boolean existsByChapterId(Long chapterId);

    List<Chapter> findAllByCourse(Course course);

    void deleteAllByCourseCourseId(Long courseId);


}
