package com.YCDxh.repository;

import com.YCDxh.model.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YCDxhg
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
//    @Query("SELECT c FROM Course c JOIN FETCH c.teacher WHERE c.category = :category")
//    List<Course> findByCategoryWithTeacher(@Param("category") String category);

    Course findByCourseId(Long courseId);

    boolean existsByCourseId(Long courseId);

    boolean existsCourseByCourseId(Long courseId);

    boolean existsByTitle(String title);
}