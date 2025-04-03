package com.YCDxh.controller;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.service.ExcelService;
import com.YCDxh.service.FileService;
import com.YCDxh.utils.MinioUtil;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


/**
 * @author YCDxhg
 */
@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传excel文件")
    public ApiResponse<?> uploadFile(@RequestParam("file") MultipartFile file) {
        excelService.getExcelData(file);
        return ApiResponse.success();
    }

    @PostMapping("/export")
    @ApiOperation(value = "导出excel数据")
    public void exportData(HttpServletResponse response, @RequestParam String startDate, @RequestParam String endDate) throws IOException {
        // 设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=attendance.xlsx");

        // 调用导出方法
        excelService.exportData(
                response.getOutputStream(),
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}
