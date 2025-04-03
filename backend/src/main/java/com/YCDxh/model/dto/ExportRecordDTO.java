package com.YCDxh.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExportRecordDTO {
    @ExcelProperty("员工ID")
    private String employeeId;

    @ExcelProperty("姓名")
    private String employeeName;

    @ExcelProperty("部门")
    private String department;


    @ExcelProperty("日期")
    private String workDate;

    @ExcelProperty("开始时间")
    private String startTime;

    @ExcelProperty("结束时间")
    private String endTime;

    @ExcelProperty("工作时长（小时）")
    private int workDuration;

    // 构造函数、Getter/Setter（Lombok可自动生成）
    public ExportRecordDTO(String employeeId, String employeeName, String department, String workDate, String startTime, String endTime, int workDuration) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDuration = workDuration;
    }
}
