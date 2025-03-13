package com.YCDxh.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志记录实体类
 */
@Data
public class OperaLog implements Serializable {
    private static final long serialVersionUID = 1L;

    // 日志主键
    private Long id;

    // 模块标题
    private String title;

    // 日志内容
    private String content;

    // 方法名称
    private String method;

    // 请求方式（如 GET/POST）
    private String requestMethod;

    // 操作人员
    private String operName;

    // 请求URL
    private String requestUrl;

    // 请求IP地址
    private String ip;

    // IP归属地
    private String ipLocation;

    // 请求参数（JSON格式）
    private String requestParam;

    // 方法响应参数（JSON格式）
    private String responseResult;

    // 操作状态（0正常 1异常）
    private Integer status;

    // 错误消息（异常时记录）
    private String errorMsg;

    // 操作时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operaTime;

    // 方法执行耗时（单位：毫秒）
    private Long takeTime;
}
