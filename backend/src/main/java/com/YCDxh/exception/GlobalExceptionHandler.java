package com.YCDxh.exception;

import com.YCDxh.model.ApiResponse;
import com.YCDxh.exception.UserException;
import com.YCDxh.model.enums.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse> handleUserException(UserException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ApiResponse<>(e.getCode(), e.getMessage(), null));
    }

    // 处理参数校验异常（如 @Valid 校验失败）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("参数校验失败");
        return ResponseEntity.status(ResponseCode.PARAM_ERROR.getCode())
                .body(new ApiResponse<>(ResponseCode.PARAM_ERROR.getCode(), errorMessage, null));
    }

    // 处理未捕获的运行时异常（如 NullPointerException）
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        return ResponseEntity.status(ResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .body(new ApiResponse<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "系统内部错误", null));
    }
}

