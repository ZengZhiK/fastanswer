package com.zzk.fastanswer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller日志切面
 *
 * @author zzk
 * @create 2020-11-25 16:45
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {
    /**
     * 以自己编写的Controller作为切入点
     */
    @Pointcut("execution(* com.zzk.fastanswer.controller..*(..))")
    public void log() {
    }

    /**
     * 请求处理前打印日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();

        log.info("请求处理 : {ip={}, url={}, method={}, classMethod={}}", ip, url, method, classMethod);
    }

    /**
     * 请求处理后打印日志
     */
    @AfterReturning(pointcut = "log()", returning = "result")
    public void doAfterReturning(Object result) {
        log.info("响应结果 : {}", result);
    }
}
