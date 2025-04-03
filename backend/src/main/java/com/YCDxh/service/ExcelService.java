package com.YCDxh.service;

import com.YCDxh.model.dto.ExportRecordDTO;
import com.YCDxh.model.entity.AfterRecord;
import com.YCDxh.model.entity.OriginalRecord;
import com.YCDxh.repository.RecordRepository;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import jodd.typeconverter.impl.LocalDateConverter;
import jodd.typeconverter.impl.LocalTimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.spi.ConversionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author YCDxhg
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelService {
    private final RecordRepository recordRepository;


    // 1、把Excel文件中【原始考勤记录报表】sheet的数据清洗后存入到MySql数据库中，字段名称与Excel表中的数据相同，支持数据多次覆盖更新。
    //2、按照指定的日期（开始时间（包含）、截止时间（包含））读取清洗后的数据，进行考勤统计，输出表样子如【出勤统计表】，自动生成数据列和每个格子的数据。
    //说明：每个人每天都有出勤的开始时间（第一次刷卡时间）和结束时间（最后一次刷卡时间）

    public void getExcelData(MultipartFile file) {

        try {
            // 读取Excel内容
            EasyExcel.read(file.getInputStream(), OriginalRecord.class, new ReadListener<OriginalRecord>() {
                AfterRecord afterRecord = new AfterRecord();
                LocalDate localDate = null;
                LocalTime localTime = null;
                LocalTime lastLocalTime = null;
                int flag = 1;

                @Override
                public void invoke(OriginalRecord originalRecord, AnalysisContext context) {


                    // 数据库时间字段为字符串, 需修改为起始时间 和 结束时间 两个字段
                    if (flag == 1) {
                        localTime = originalRecord.getCheckTime().toLocalTime();
                        localDate = originalRecord.getCheckTime().toLocalDate();

                        afterRecord.setEmployeeName(originalRecord.getName());

                        int lastSlashIndex = originalRecord.getDepartment().lastIndexOf("/");
                        afterRecord.setDepartment(originalRecord.getDepartment().substring(lastSlashIndex + 1));

                        afterRecord.setWorkDate(localDate);

                        afterRecord.setEmployeeId(originalRecord.getId().substring(1));
                        flag = 0;
                    } else if (!localDate.isEqual(originalRecord.getCheckTime().toLocalDate())) {
                        afterRecord.setStartTime(localTime);
                        afterRecord.setEndTime(lastLocalTime);
                        afterRecord.setWorkDuration(String.valueOf(Duration.between(localTime, lastLocalTime).toHours()));
                        save(afterRecord);
                        afterRecord = new AfterRecord();

                        localTime = originalRecord.getCheckTime().toLocalTime();
                        localDate = originalRecord.getCheckTime().toLocalDate();

                        afterRecord.setEmployeeName(originalRecord.getName());

                        int lastSlashIndex = originalRecord.getDepartment().lastIndexOf("/");
                        afterRecord.setDepartment(originalRecord.getDepartment().substring(lastSlashIndex + 1));

                        afterRecord.setWorkDate(localDate);

                        afterRecord.setEmployeeId(originalRecord.getId().substring(1));
                    }
                    lastLocalTime = originalRecord.getCheckTime().toLocalTime();

                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }

            }).sheet("原始考勤记录报表").doRead();
        } catch (Exception e) {
            throw new RuntimeException("Excel解析失败", e);
        }
    }


    public void exportData(OutputStream outputStream, LocalDate startDate, LocalDate endDate) {
        List<AfterRecord> records = recordRepository.findAllByWorkDateBetween(startDate, endDate);

        // 转换数据为导出DTO
        List<ExportRecordDTO> exportRecords = records.stream()
                .map(record -> new ExportRecordDTO(
                        record.getEmployeeId(),
                        record.getEmployeeName(),
                        record.getDepartment(),
                        record.getWorkDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        record.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        record.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        Integer.parseInt(record.getWorkDuration())
                ))
                .collect(Collectors.toList());

        // 使用EasyExcel导出
        EasyExcel.write(outputStream, ExportRecordDTO.class)
                .sheet("出勤统计表")
                .doWrite(exportRecords);
    }


    private void save(AfterRecord newRecord) {
        // 通过唯一键查询现有记录
        AfterRecord existingRecord = recordRepository.findByEmployeeIdAndWorkDate(
                newRecord.getEmployeeId(),
                newRecord.getWorkDate()
        ).orElse(null);

        if (existingRecord != null) {
            // 更新现有记录的字段（保留需要覆盖的字段）
            existingRecord.setStartTime(newRecord.getStartTime());
            existingRecord.setEndTime(newRecord.getEndTime());
            existingRecord.setWorkDuration(String.valueOf(
                    Duration.between(existingRecord.getStartTime(), existingRecord.getEndTime()).toHours()));


            // 使用现有记录对象触发更新（关键！）
            recordRepository.save(existingRecord);
        } else {
            // 保存新记录
            recordRepository.save(newRecord);
        }
    }


}
