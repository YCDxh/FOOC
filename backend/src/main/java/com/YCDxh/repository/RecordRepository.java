package com.YCDxh.repository;

import com.YCDxh.model.entity.AfterRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<AfterRecord, Long> {

    // 自动根据方法名生成查询
    Optional<AfterRecord> findByEmployeeIdAndWorkDate(String employeeId, LocalDate workDate);

}
