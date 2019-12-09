package me.zhengjie.exception.handler;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.base.BaseResponse;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.utils.ThrowableUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public BaseResponse handleException(Throwable e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
       return buildResponseEntity("服务器异常");

    }

    /**
     * 处理 接口无权访问异常AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse handleAccessDeniedException(AccessDeniedException e){
        // 打印堆栈信息
//        log.error(ThrowableUtil.getStackTrace(e));

        return buildResponseEntity("权限不足");

    }

    /**
     * 处理自定义异常
     */
	@ExceptionHandler(value = BadRequestException.class)
	public BaseResponse badRequestException(BadRequestException e) {
        // 打印堆栈信息
//        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(e.getMessage());
	}

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public BaseResponse entityExistException(EntityExistException e) {
        // 打印堆栈信息
//        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(e.getMessage());
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public BaseResponse entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
//        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(e.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
//        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        if("不能为空".equals(message)){
            message = str[1] + ":" + message;
        }
        return buildResponseEntity(message);
    }

    /**
     * 统一返回
     */
    private BaseResponse buildResponseEntity(String errorMessage) {
        return BaseResponse.failed().message(errorMessage).build();
    }
}
