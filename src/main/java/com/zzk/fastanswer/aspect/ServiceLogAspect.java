package com.zzk.fastanswer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Service日志切面
 *
 * @author zzk
 * @create 2020-11-25 16:45
 */
@Slf4j
@Aspect
@Component
public class ServiceLogAspect {
    /**
     * 以自己编写的Service作为切入点
     */
    @Pointcut("execution(* com.zzk.fastanswer.service..*(..))")
    public void log() {
    }

    /**
     * 业务处理前打印日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.info("业务处理 : {ip={}, method={}}", ip, classMethod);
    }
}
