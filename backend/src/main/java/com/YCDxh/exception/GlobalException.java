package com.YCDxh.exception;

import com.YCDxh.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//@Slf4j
//public class GlobalException {
//    @ExceptionHandler(value = UserException.class)
//    public ApiResponse<Void> userExceptionHandler(UserException e) {
//        log.error("全局异常信息 ex={}", e.getMessage(), e);
//        return ApiResponse.fail(e.getCode(),e.getMessage());
//    }
//}
