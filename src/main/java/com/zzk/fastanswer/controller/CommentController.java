package com.zzk.fastanswer.controller;


import cn.hutool.core.bean.BeanUtil;
import com.zzk.fastanswer.common.annotation.ModelView;
import com.zzk.fastanswer.common.constant.ExceptionConstants;
import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.common.enums.CustomExceptionType;
import com.zzk.fastanswer.common.exception.CustomException;
import com.zzk.fastanswer.common.utils.AjaxResponse;
import com.zzk.fastanswer.model.dto.CommentDTO;
import com.zzk.fastanswer.model.dto.UserDTO;
import com.zzk.fastanswer.model.entity.Comment;
import com.zzk.fastanswer.model.vo.CommentVO;
import com.zzk.fastanswer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author zzk
 * @since 2020-11-16
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 刷新回复
     */
    @GetMapping
    @ModelView
    public String getComment(@RequestParam("questionId") Long questionId, Model model) {
        List<CommentDTO> commentDTOList = commentService.listCommentDtoByQuestionId(questionId);
        model.addAttribute("commentDTOList", commentDTOList);
        return "question :: comment";
    }

    /**
     * 保存回复
     */
    @PostMapping
    @ResponseBody
    public AjaxResponse saveComment(@Valid CommentVO commentVO, HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        // 保存评论、更新问题评论数、更新通知表
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentVO, comment);
        comment.setCommentator(loginUser.getId());
        commentService.saveComment(comment);

        return AjaxResponse.success();
    }
}

