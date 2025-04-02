package com.YCDxh.repository;


import com.YCDxh.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findAll();

    boolean existsByEmail(String email);

    boolean existsByUsername(String userName);

    boolean existsByUserId(Long userId);

//    @Query(value = "SELECT u FROM User u " +
//            "WHERE u.userId IN " +
//            "(SELECT enm.userId FROM Enrollment enm WHERE enm.courseId = :courseId)",
//            nativeQuery = true)
//    List<User> findUsersByCourseId(@Param("courseId") Long courseId);

    User findByUserId(Long userId);

    User findByEmail(String email);

    Optional<User> findByUsername(String userName);
}
