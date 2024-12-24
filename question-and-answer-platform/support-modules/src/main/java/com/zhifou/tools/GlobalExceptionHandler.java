package com.zhifou.tools;

import com.zhifou.common.AppHttpCodeEnum;
import com.zhifou.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理自定义异常
    @ExceptionHandler(SystemException.class)
    public Response<?> systemExceptionHandler(SystemException e) {
        // 打印完整的异常堆栈信息
        log.error("系统异常发生！", e);
        // 从异常对象中获取提示信息并封装返回
        return Response.error(e.getCode(), e.getMessage());
    }

    // 处理一般的异常
    @ExceptionHandler(Exception.class)
    public Response<?> exceptionHandler(Exception e) {
        // 打印完整的异常堆栈信息
        log.error("未捕获的异常发生！", e);
        // 返回默认错误信息
        return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),
                e.getMessage() != null ? e.getMessage() : "系统发生未知错误，请联系管理员");
    }

    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Response<?> nullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常！", e);
        return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "系统发生空指针错误");
    }

    // 处理非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("非法参数异常！", e);
        return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleValidationException(MethodArgumentNotValidException e) {
        // 获取所有的验证错误信息
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        // 打印校验失败的错误信息
        log.error("参数校验失败: {}", String.join("; ", errorMessages));

        // 返回参数校验错误的响应
        return Response.error(AppHttpCodeEnum.PARAMETER_ERROR.getCode(),
                String.join("; ", errorMessages));
    }
}
