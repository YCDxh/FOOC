package com.YCDxh.service;

public interface EnrollmentService {
    void addEnrollment(Long studentId, Long courseId);

    void deleteEnrollment(Long studentId, Long courseId);
}
