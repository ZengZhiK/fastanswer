package com.zzk.fastanswer.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzk.fastanswer.common.annotation.ModelView;
import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.common.constant.TableColConstants;
import com.zzk.fastanswer.model.dto.AccessTokenDTO;
import com.zzk.fastanswer.model.dto.GithubUserDTO;
import com.zzk.fastanswer.model.dto.UserDTO;
import com.zzk.fastanswer.model.entity.User;
import com.zzk.fastanswer.provider.GithubProvider;
import com.zzk.fastanswer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * GitHub登录 回调处理
 *
 * @author zzk
 * @create 2020-10-14 22:18
 */
@Controller
public class OAuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect-uri}")
    private String redirectUri;

    /**
     * GitHub登入回调
     *
     * @param code GitHub返回的code
     */
    @GetMapping("/callback")
    @ModelView
    public String callback(@RequestParam(name = "code") String code, HttpSession session) {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUri).build();
        // 获取github access token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        // 根据access token获取github用户信息
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);

        // 根据githubUser的id查询数据库是否存在用户，如果存在则表示登录，如果不存在则表示注册
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(TableColConstants.GITHUB_ID, githubUserDTO.getGithubId());
        User user = userService.getOne(wrapper);

        if (ObjectUtils.isEmpty(user)) {
            // 保存用户到数据库
            user = new User();
            BeanUtils.copyProperties(githubUserDTO, user);
            userService.save(user);
        } else {
            // 更新用户到数据库
            BeanUtils.copyProperties(githubUserDTO, user);
            userService.updateById(user);
        }

        // 将User转为UserDTO保存到session域
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);

        // 登录处理，将用户信息保存到session域，之后重定向到首页
        session.setAttribute(SessionConstants.LOGIN_USER, userDTO);
        return "redirect:/";
    }
}
