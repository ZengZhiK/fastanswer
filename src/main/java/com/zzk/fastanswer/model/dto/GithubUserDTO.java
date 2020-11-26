package com.zzk.fastanswer.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * GitHub 返回的用户信息
 *
 * @author zzk
 * @create 2020-10-17 14:59
 */
@Data
public class GithubUserDTO {
    /**
     * GitHub ID
     */
    @JSONField(name = "id")
    private Long githubId;

    /**
     * GitHub name
     */
    @JSONField(name = "login")
    private String githubName;

    /**
     * GitHub homepage
     */
    @JSONField(name = "html_url")
    private String githubHtmlUrl;

    /**
     * GitHub avatar url
     */
    @JSONField(name = "avatar_url")
    private String githubAvatarUrl;
}
