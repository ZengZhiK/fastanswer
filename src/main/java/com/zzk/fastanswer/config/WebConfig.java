package com.zzk.fastanswer.config;

import com.zzk.fastanswer.interceptor.LoginInterceptor;
import com.zzk.fastanswer.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置
 *
 * @author zzk
 * @create 2020-11-07 16:22
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    /**
     * 添加拦截器，拦截未登录用户
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/system/login", "/callback", "/question/**", "/comment", "/css/**", "/fonts/**", "/img/**", "/js/**", "/editor.md/**");

        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/fonts/**", "/img/**", "/js/**", "/editor.md/**");
    }
}
