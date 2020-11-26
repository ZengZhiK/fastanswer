package com.zzk.fastanswer.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

/**
 * GitHub Access Token Input DTO
 *
 * @author zzk
 * @create 2020-10-15 8:47
 */
@Data
@Builder
public class AccessTokenDTO {
    /**
     * GitHub client ID
     */
    @JSONField(name = "client_id")
    private String clientId;

    /**
     * GitHub client secret
     */
    @JSONField(name = "client_secret")
    private String clientSecret;

    /**
     * GitHub response code
     */
    private String code;

    /**
     * GitHub redirect uri
     */
    @JSONField(name = "redirect_uri")
    private String redirectUri;
}
