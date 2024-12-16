package com.hrsys.pojo.entity;

import com.hrsys.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 结果类，用于封装 API 返回结果
 *
 * @param <T> 数据类型
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Integer code;

    private String msg;

    private T data;

    public static Result<?> success(){
        return SUCCESS_RESULT;
    }

    public static <T> Result<T> success(T data){
        return new Result<>(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getDesc(), data);
    }

    public static Result<String> error(String msg){
        if (msg == null) {
            return ERROR_RESULT;
        }
        return new Result<>(StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getDesc(), msg);
    }


    public static Result<String> error(StatusCodeEnum statusCodeEnum) {
        return new Result<>(statusCodeEnum.getCode(), statusCodeEnum.getDesc(), statusCodeEnum.getDesc());
    }

    public static Result<String> error(StatusCodeEnum statusCodeEnum, String msg) {
        if (msg == null) {
            return ERROR_RESULT;
        }
        return new Result<>(statusCodeEnum.getCode(), statusCodeEnum.getDesc(), msg);
    }

    private static final Result<?> SUCCESS_RESULT = new Result<>(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getDesc(), null);
    private static final Result<String> ERROR_RESULT = new Result<>(StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getDesc(), "Unknown error");
}
