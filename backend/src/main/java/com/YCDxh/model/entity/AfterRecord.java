package com.YCDxh.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author YCDxhg
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "after_record")
public class AfterRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "department")
    private String department;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;


    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;  // 起始时间
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;    // 结束时间

    @Column(name = "work_duration")
    private String workDuration;

    public AfterRecord(String employeeId, String employeeName, String department, LocalDate workDate, LocalTime startTime, LocalTime endTime, String workDuration) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDuration = workDuration;
    }


}
