package com.zzk.fastanswer.model.dto;

import lombok.Data;

/**
 * Notification Output DTO
 *
 * @author zzk
 * @create 2020-11-26 9:44
 */
@Data
public class NotificationDTO {
    /**
     * 通知id
     */
    private Long id;

    /**
     * 通知者Github name
     */
    private String notifierGithubName;

    /**
     * 通知者 GitHub html url
     */
    private String notifierGithubHtmlUrl;

    /**
     * 通知者Github avatar url
     */
    private String notifierGithubAvatarUrl;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 问题标题
     */
    private String questionTitle;

    /**
     * 父评论内容
     */
    private String parentCommentContent;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 是否被阅读
     */
    private Boolean status;
}
