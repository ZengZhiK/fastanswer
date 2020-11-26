package com.zzk.fastanswer.interceptor;

import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.model.dto.UserDTO;
import com.zzk.fastanswer.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录后每次请求都刷新未读通知数
 *
 * @author zzk
 * @create 2020-11-26 19:55
 */
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录后需要刷新
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute(SessionConstants.LOGIN_USER);
        if (!ObjectUtils.isEmpty(loginUser)) {
            String ip = request.getRemoteAddr();
            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            log.info("拦截处理 : {ip={}, url={}, method={}} 更新未读通知数量", ip, url, method);

            int unread = notificationService.countUnReadByReceiver(loginUser.getId());
            request.getSession().setAttribute(SessionConstants.UNREAD, unread);
        }
        return true;
    }
}
