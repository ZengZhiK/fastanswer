package com.zzk.fastanswer.aspect;

import com.zzk.fastanswer.common.exception.ModelViewException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 当标注ModelView注解的方法发生异常时，将异常转为ModelViewException
 *
 * @author zzk
 * @create 2020-11-05 14:25
 */
@Aspect
@Component
public class ModelViewAspect {
    /**
     * 设置切入点，拦截被@ModelView注解的方法
     */
    @Pointcut("@annotation(com.zzk.fastanswer.common.annotation.ModelView)")
    public void pointcut() {
    }

    /**
     * 当有ModelView的注解的方法抛出异常的时候，做如下的处理
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowable(Throwable e) {
        throw ModelViewException.transfer(e);
    }
}
