package com.zzk.fastanswer.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 评论 VO
 * @author zzk
 * @create 2020-11-16 11:01
 */
@Data
public class CommentVO {
    /**
     * 评论内容
     */
    @NotEmpty(message = "评论内容不能为空")
    @Length(max=512, message = "评论内容不能大于512个字符")
    private String content;

    /**
     * 问题id
     */
    @NotNull(message = "问题id不能为空")
    private Long questionId;

    /**
     * 根评论id（一级评论为空）
     */
    private Long rootId;

    /**
     * 父评论id（一级评论为空）
     */
    private Long parentId;
}
