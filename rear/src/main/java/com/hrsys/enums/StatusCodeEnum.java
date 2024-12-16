package com.hrsys.enums;

import com.hrsys.constant.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS(HttpStatus.SUCCESS, "操作成功"),

    PARAM_ERROR(HttpStatus.BAD_REQUEST, "参数校验失败"),

    FAIL(HttpStatus.ERROR, "操作失败"),

    SERVER_ERROR(HttpStatus.ERROR, "服务器内部错误"),

    USERNAME_EXIST(HttpStatus.BAD_REQUEST, "用户已存在"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "用户不存在"),

    PASSWORD_ERROR(HttpStatus.UNAUTHORIZED, "密码错误"),

    NO_LOGIN(HttpStatus.UNAUTHORIZED, "未登录"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "无效token"),

    EXISTS(HttpStatus.BAD_REQUEST, "已存在"),

    NOT_EXISTS(HttpStatus.BAD_REQUEST, "不存在"),

    UPLOAD_FAILED(HttpStatus.ERROR, "上传失败"),

    DOWNLOAD_FAILED(HttpStatus.ERROR, "下载失败");


    private final Integer code;

    private final String desc;
}
