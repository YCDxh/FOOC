package com.YCDxh.repository;

import com.YCDxh.model.entity.Course;
import com.YCDxh.model.entity.Enrollment;
import com.YCDxh.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    void deleteByCourseCourseIdAndStudentUserId(Long courseId, Long studentId);

    void deleteAllByCourseCourseId(Long courseId);

}
