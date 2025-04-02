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

import java.io.File;


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

}
