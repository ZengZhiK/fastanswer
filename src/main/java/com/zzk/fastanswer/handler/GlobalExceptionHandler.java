package com.zzk.fastanswer.handler;

import com.zzk.fastanswer.common.enums.CustomExceptionType;
import com.zzk.fastanswer.common.exception.CustomException;
import com.zzk.fastanswer.common.exception.ModelViewException;
import com.zzk.fastanswer.common.utils.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author zzk
 * @create 2020-11-05 10:30
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理ModelAndView异常、请求不支持异常、未能捕获（遗漏的）异常
     */
    @ExceptionHandler({ModelViewException.class, HttpRequestMethodNotSupportedException.class, Exception.class})
    public ModelAndView viewExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("系统发生异常: {}, 跳转至error页面", e.toString());

        ModelAndView modelAndView = new ModelAndView();

        //将异常信息设置如modelAndView
        modelAndView.addObject("exception", e);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("error");

        //返回ModelAndView
        return modelAndView;
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public AjaxResponse customerException(CustomException e) {
        log.error("系统发生异常: {}, 返回异步数据", e.getMessage());
//        if (e.getCode() == CustomExceptionType.SYSTEM_ERROR.getCode()) {
//            // 400异常不需要持久化，将异常信息以友好的方式告知用户就可以
//            // 将500异常信息持久化处理，方便运维人员处理
//        }
        return AjaxResponse.error(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResponse handleBindException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数校验错误: {}, 返回异步数据", fieldError.getDefaultMessage());
        return AjaxResponse.error(new CustomException(CustomExceptionType.USER_INPUT_ERROR,
                fieldError.getDefaultMessage()));
    }


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public AjaxResponse handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数校验错误: {}, 返回异步数据", fieldError.getDefaultMessage());
        return AjaxResponse.error(new CustomException(CustomExceptionType.USER_INPUT_ERROR,
                fieldError.getDefaultMessage()));
    }

//    /**
//     * 处理
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public AjaxResponse exception(Exception e) {
//        return AjaxResponse.error(new CustomException(
//                CustomExceptionType.OTHER_ERROR));
//    }
}
