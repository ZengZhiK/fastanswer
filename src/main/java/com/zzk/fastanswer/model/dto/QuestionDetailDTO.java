package com.zzk.fastanswer.model.dto;

import lombok.Data;

/**
 * QuestionDetail Output DTO
 *
 * @author zzk
 * @create 2020-11-11 15:57
 */
@Data
public class QuestionDetailDTO extends QuestionDTO {
    /**
     * 问题描述
     */
    private String description;

    /**
     * 问题标签
     */
    private String tag;
}
