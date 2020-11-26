package com.zzk.fastanswer.model.dto;

import com.zzk.fastanswer.model.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Comment Output DTO
 *
 * @author zzk
 * @create 2020-11-20 21:12
 */
@Data
public class CommentDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 根评论id（一级评论为空）
     */
    private Long rootId;

    /**
     * 父评论id（一级评论为空）
     */
    private Long parentId;

    /**
     * 评论人
     */
    private UserDTO userDTO  = new UserDTO();

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 子评论
     */
    private List<CommentDTO> subCommentDTOList = new ArrayList<>();

    /**
     * 被评论人GitHub Name
     */
    private String githubNameForBeCriticized;
}
