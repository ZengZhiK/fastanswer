package com.zzk.fastanswer.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * User Output DTO
 *
 * @author zzk
 * @create 2020-11-20 21:35
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 6811834374228618465L;

    /**
     * 主键
     */
    private Long id;

    /**
     * GitHub name
     */
    private String githubName;

    /**
     * GitHub html url
     */
    private String githubHtmlUrl;

    /**
     * GitHub avatar url
     */
    private String githubAvatarUrl;
}
