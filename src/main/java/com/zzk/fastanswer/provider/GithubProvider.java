package com.zzk.fastanswer.provider;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.zzk.fastanswer.common.utils.StringToMapUtils;
import com.zzk.fastanswer.model.dto.AccessTokenDTO;
import com.zzk.fastanswer.model.dto.GithubUserDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * GitHub OAuth API
 *
 * @author zzk
 * @create 2020-10-15 8:46
 */
@Component
public class GithubProvider {
    /**
     * 获取Access Token
     *
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        String responseBody = HttpRequest.post("https://github.com/login/oauth/access_token")
                .body(JSON.toJSONString(accessTokenDTO))
                .execute().body();
        Map<String, String> parse = StringToMapUtils.parse(responseBody, "&");
        return parse.get("access_token");
    }

    /**
     * 获取GitHub用户信息
     *
     * @param accessToken
     * @return
     */
    public GithubUserDTO getUser(String accessToken) {
        String responseBody = HttpRequest.get("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .execute().body();
        return JSON.parseObject(responseBody, GithubUserDTO.class);
    }
}
