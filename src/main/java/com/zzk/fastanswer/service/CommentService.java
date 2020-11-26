package com.zzk.fastanswer.service;

import com.zzk.fastanswer.model.dto.CommentDTO;
import com.zzk.fastanswer.model.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author zzk
 * @since 2020-11-16
 */
public interface CommentService extends IService<Comment> {

    /**
     * 保存评论、更新问题评论数、更新通知表
     * @param comment
     */
    void saveComment(Comment comment);

    /**
     * 根据问题id查询评论
     * @param questionId
     * @return
     */
    List<CommentDTO> listCommentDtoByQuestionId(Long questionId);
}
