package com.YCDxh.service.impl;

import com.YCDxh.model.entity.Course;
import com.YCDxh.model.entity.Enrollment;
import com.YCDxh.model.entity.User;
import com.YCDxh.repository.CourseRepository;
import com.YCDxh.repository.EnrollmentRepository;
import com.YCDxh.repository.UserRepository;
import com.YCDxh.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmetnServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public void addEnrollment(Long studentId, Long courseId) {
        Enrollment enrollment = new Enrollment();

        enrollment.setCourse(courseRepository.findByCourseId(courseId));
        enrollment.setStudent(userRepository.findByUserId(studentId));
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollmentRepository.save(enrollment);

    }

    @Override
    public void deleteEnrollment(Long studentId, Long courseId) {
        enrollmentRepository.deleteByCourseCourseIdAndStudentUserId(studentId, courseId);

    }
}
