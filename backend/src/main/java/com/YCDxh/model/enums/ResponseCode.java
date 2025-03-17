package com.YCDxh.model.enums;

import lombok.Getter;

/**
 * @author YCDxhg
 */

@Getter
public enum ResponseCode {

    SUCCESS(200, "操作成功!"),
    FAILURE(201, "操作失败"),
    /**
     * 系统相关的错误码：5开头
     **/
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    /**
     * 资源相关的错误码：4开头
     **/

    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    INVALID_CAPTCHA(402, "验证码错误"),
    /**
     * 参数相关的错误码：1开头
     **/
    PARAM_ERROR(1000, "参数异常"),
    PARAM_NOT_NULL(1001, "参数不能为空"),
    PARAM_NOT_VALID(1002, "参数校验不通过"),


    /**
     * 权限相关的错误码：2开头
     **/

    INVALID_TOKEN(2001, "访问令牌不合法"),
    ACCESS_DENIED(2002, "没有权限访问该资源"),
    USERNAME_OR_PASSWORD_ERROR(2003, "用户名或密码错误"),
    EMAIL_OCCUPIED(2004, "此邮箱已被占用"),
    USER_NOT_EXIST(2005, "用户不存在"),
    USERNAME_OCCUPIED(2006, "此用户名已被占用"),
    INVALID_CREDENTIALS(2007, "用户名或密码错误"),
    INVALID_EMAIL(2009, "邮箱错误"),
    INVALID_ROLE(2010, "角色错误"),
    INVALID_USER(2011, "用户错误"),
    INVALID_COURSE(2012, "课程错误"),
    INVALID_ENROLLMENT(2013, "报名错误"),
    INVALID_LEARNING_PROGRESS(2014, "学习进度错误"),
    INVALID_CHAPTER(2015, "章节错误"),
    INVALID_VIDEO(2016, "视频错误"),
    INVALID_COMMENT(2017, "评论错误"),
    INVALID_REPLY(2018, "回复错误"),
    INVALID_CATEGORY(2019, "分类错误"),
    INVALID_CATEGORY_NAME(2020, "分类名错误"),
    INVALID_CATEGORY_ID(2021, "分类id错误"),
    COURSE_NOT_EXIST(2020, "课程不存在");


    private final int code;

    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
