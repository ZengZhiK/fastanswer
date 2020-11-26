package com.zzk.fastanswer.controller;

import com.zzk.fastanswer.common.annotation.ModelView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 系统登录登出 请求处理
 *
 * @author zzk
 * @create 2020-11-09 8:49
 */
@Controller
@RequestMapping("/system")
public class SystemController {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.redirect-uri}")
    private String redirectUri;

    /**
     * 登录
     */
    @GetMapping("/login")
    @ModelView
    public String login() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=user";
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    @ModelView
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
