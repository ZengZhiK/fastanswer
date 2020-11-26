package com.zzk.fastanswer.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * QuestionBase Output DTO
 *
 * @author zzk
 * @create 2020-11-24 15:00
 */
@Data
public class QuestionBaseDTO {
    /**
     * 问题ID
     */
    private Long id;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 阅读数
     */
    private Integer viewCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
