package com.YCDxh.repository;

import com.YCDxh.model.entity.Course;
import com.YCDxh.model.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {
    LearningProgress findByProgressId(Long progressId);


}
