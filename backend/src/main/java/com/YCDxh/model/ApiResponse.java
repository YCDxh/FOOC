package com.YCDxh.model;

import com.YCDxh.exception.UserException;
import com.YCDxh.model.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    private int code;
    @Getter
    private String message;
    @Getter
    private T data;

//    public static <T> ApiResponse<T> success(T data) {
//        return new ApiResponse<>(200, "success", data);
//    }

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ApiResponse(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(ResponseCode code) {
        return new ApiResponse<>(code.getCode(), code.getMessage());
    }

    public static <T> ApiResponse<T> fail(UserException e) {
        return new ApiResponse<>(e.getCode(), e.getMessage());
    }

    public static <T> ApiResponse<T> fail(Integer code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return (new StringJoiner(", ", ApiResponse.class.getSimpleName() + "[", "]"))
                .add("code=" + this.code)
                .add("message='" + this.message + "'")
                .add("data=" + this.data)
                .toString();
    }
}
