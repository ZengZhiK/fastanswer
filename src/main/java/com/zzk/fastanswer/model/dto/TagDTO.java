package com.zzk.fastanswer.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zzk
 * @create 2020-11-23 21:34
 */
@Data
public class TagDTO {
    /**
     * 标签分类
     */
    private String categoryName;

    /**
     * 标签名
     */
    private List<String> tagList;
}
