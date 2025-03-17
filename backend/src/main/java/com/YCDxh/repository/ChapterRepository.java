package com.YCDxh.repository;

import com.YCDxh.model.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Chapter findByChapterId(Long chapterId);

    boolean existsByChapterId(Long chapterId);
}
