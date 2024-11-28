package com.hrsys.exception;

import com.hrsys.enums.StatusCodeEnum;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final StatusCodeEnum statusCodeEnum;
    private final Integer code;

    public BizException(String message) {
        this(StatusCodeEnum.FAIL, message);
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this(statusCodeEnum, statusCodeEnum.getDesc());
    }

    public BizException(StatusCodeEnum statusCodeEnum, String message) {
        super(message);
        this.statusCodeEnum = statusCodeEnum;
        this.code = statusCodeEnum.getCode();
    }
}
