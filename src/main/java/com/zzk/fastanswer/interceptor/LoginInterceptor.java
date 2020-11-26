package com.zzk.fastanswer.interceptor;

import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zzk
 * @create 2020-11-07 16:25
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录后均可访问
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(SessionConstants.LOGIN_USER);
        if (!ObjectUtils.isEmpty(loginUser)) {
            return true;
        }

        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        log.info("拦截处理 : {ip={}, url={}, method={}} 用户未登录, 请求被拦截, 跳转至首页", ip, url, method);

        // 没登录返回首页
        response.sendRedirect("/");
        return false;
    }
}
