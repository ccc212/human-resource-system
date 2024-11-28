package com.hrsys.handler;

import com.hrsys.pojo.entity.Result;
import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result<?> bizExceptionHandler(BizException ex) {
        log.error("业务异常信息：{}", ex.getMessage());
        ex.printStackTrace();
        return ex.getStatusCodeEnum() != null ?
                Result.error(ex.getStatusCodeEnum(), ex.getMessage()) :
                Result.error(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Result.error(StatusCodeEnum.PARAM_ERROR, "参数验证失败: " + errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));
        return Result.error(StatusCodeEnum.PARAM_ERROR, "参数验证失败: " + errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleGenericException(Exception ex) {
        log.error("未捕获的异常：", ex);
        return Result.error(StatusCodeEnum.SERVER_ERROR);
    }
}