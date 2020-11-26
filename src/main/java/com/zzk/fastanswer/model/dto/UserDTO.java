package com.zzk.fastanswer.model.dto;

import lombok.Data;

/**
 * User Output DTO
 *
 * @author zzk
 * @create 2020-11-20 21:35
 */
@Data
public class UserDTO {
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
