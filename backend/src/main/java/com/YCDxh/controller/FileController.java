package com.YCDxh.controller;

import com.YCDxh.utils.MinioUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 校验文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("文件不能为空");
            }

            // 生成唯一文件名并上传
            String objectName = minioUtil.upload(file);
            if (objectName == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("上传失败，请检查 MinIO 连接或文件格式");
            }

            return ResponseEntity.ok("文件上传成功，路径: " + objectName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletResponse response) {
        try {
            minioUtil.download(fileName, response);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("文件不存在或下载失败");
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            boolean success = minioUtil.remove(fileName);
            if (success) {
                return ResponseEntity.ok("文件删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("文件不存在或删除失败");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("删除失败: " + e.getMessage());
        }
    }

    @GetMapping("/preview/{fileName}")
    public ResponseEntity<String> previewFile(@PathVariable String fileName) {
        try {
            String url = minioUtil.preview(fileName);
            if (url != null) {
                return ResponseEntity.ok(url);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("文件不存在");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("预览失败: " + e.getMessage());
        }
    }
}
