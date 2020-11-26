package com.zzk.fastanswer.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 问题 VO
 *
 * @author zzk
 * @create 2020-11-4
 */
@Data
public class QuestionVO {
    /**
     * 问题ID
     */
    private Long id;

    /**
     * 问题标题
     */
    @NotEmpty(message = "标题内容不能为空")
    @Length(max=50, message = "标题长度不能大于50个字符")
    private String title;

    /**
     * 问题描述
     */
    @NotEmpty(message = "问题描述不能为空")
    private String description;

    /**
     * 问题标签
     */
    @Length(max=104, message = "标签太长")
    private String tag;
}
