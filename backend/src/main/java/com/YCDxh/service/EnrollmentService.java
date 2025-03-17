package com.YCDxh.service;

public interface EnrollmentService {
    void addEnrollment(Long userId, Long courseId);

    void deleteEnrollment(Long userId, Long courseId);
}
