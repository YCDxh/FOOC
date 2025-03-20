package com.YCDxh.controller;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.model.enums.ResponseCode;
import com.YCDxh.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController {

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.fail(ResponseCode.PARAM_NOT_NULL);
        }
        try {
            String objectName = minioUtil.upload(file);
            if (objectName == null) {
                return ApiResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR);
            }
            return ApiResponse.success(objectName);
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

    @GetMapping("/download")
    @ApiOperation("下载文件")
    public ApiResponse<Void> downloadFile(@RequestParam String fileName, HttpServletResponse response) {
        try {
            minioUtil.download(fileName, response);
            return ApiResponse.success();
        } catch (Exception e) {
            return ApiResponse.fail(ResponseCode.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除文件")
    public ApiResponse<Void> deleteFile(@RequestParam String fileName) {
        try {
            boolean success = minioUtil.remove(fileName);
            if (success) {
                return ApiResponse.success();
            } else {
                return ApiResponse.fail(ResponseCode.NOT_FOUND);
            }
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

    @GetMapping("/preview")
    @ApiOperation("预览文件")
    public ApiResponse<String> previewFile(@RequestParam String fileName) {
        log.info("previewFile fileName:{}", fileName);
        try {
            String url = minioUtil.preview(fileName);
            if (url == null) {
                return ApiResponse.fail(ResponseCode.NOT_FOUND);
            }
            return ApiResponse.success(url);
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }
}
