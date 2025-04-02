package com.YCDxh.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "check_time")
    private String checkTime;

    @Column(name = "work_duration")
    private String workDuration;
}
