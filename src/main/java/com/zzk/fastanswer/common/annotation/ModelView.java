package com.zzk.fastanswer.common.annotation;

import java.lang.annotation.*;

/**
 * ModelView注解，标注该注解的方法如果发生异常，则返回error.html，而不是返回异步数据AjaxResponse.error()
 *
 * @author zzk
 * @create 2020-11-05 14:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ModelView {
}
