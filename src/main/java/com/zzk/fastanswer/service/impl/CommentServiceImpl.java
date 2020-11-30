package com.zzk.fastanswer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzk.fastanswer.common.constant.TableColConstants;
import com.zzk.fastanswer.mapper.CommentMapper;
import com.zzk.fastanswer.mapper.NotificationMapper;
import com.zzk.fastanswer.mapper.QuestionMapper;
import com.zzk.fastanswer.mapper.UserMapper;
import com.zzk.fastanswer.model.dto.CommentDTO;
import com.zzk.fastanswer.model.entity.Comment;
import com.zzk.fastanswer.model.entity.Notification;
import com.zzk.fastanswer.model.entity.Question;
import com.zzk.fastanswer.model.entity.User;
import com.zzk.fastanswer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2020-11-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        baseMapper.insert(comment);
        questionMapper.updateCommentCountOnce(comment.getQuestionId());

        // 查询被评论的问题
        Question question = questionMapper.selectById(comment.getQuestionId());

        // 查询评论的父评论
        Comment parentComment = null;
        if (comment.getParentId() != null) {
            // 查询该评论的父评论
            parentComment = baseMapper.selectById(comment.getParentId());
        }

        /* 需要新建通知有两种情况：
         * 1、问题创建者和评论创建者不是同一个
         * 2、被评论的人和回复评论的人不是同一个
         */
        if (!question.getCreator().equals(comment.getCommentator()) ||
                (parentComment != null && !parentComment.getCommentator().equals(comment.getCommentator()))) {
            Notification notification = new Notification();
            notification.setQuestionId(question.getId());           // 通知显示评论了哪个问题
            notification.setParentCommentId(comment.getParentId()); // 通知显示评论了哪个父评论
            notification.setCommentId(comment.getId());             // 通知显示评论内容
            notification.setNotifier(comment.getCommentator());     // 通知显示谁发表评论
            notification.setStatus(false);                          // 通知显示该评论未被阅读
            if (!question.getCreator().equals(comment.getCommentator())) {
                notification.setReceiver(question.getCreator());        // 通知显示谁接受评论
            } else {
                notification.setReceiver(parentComment.getCommentator());        // 通知显示谁接受评论
            }
            notificationMapper.insert(notification);
        }
    }

    @Override
    public List<CommentDTO> listCommentDtoByQuestionId(Long questionId) {
        List<CommentDTO> commentDTOList = new ArrayList<>();

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TableColConstants.QUESTION_ID, questionId);
        List<Comment> commentList = baseMapper.selectList(queryWrapper);

        // 筛选出所有根评论
        List<Comment> rootCommentList = commentList.stream()
                .filter(comment -> comment.getRootId() == null)
                .sorted(Comparator.comparing(Comment::getGmtCreate).reversed())
                .collect(Collectors.toList());

        // 获得每个根评论的所有子评论
        Map<Long, List<Comment>> rootIdToCommentList = new HashMap<>();
        commentList.forEach(comment -> {
            if (comment.getRootId() != null) {
                List<Comment> value = rootIdToCommentList.getOrDefault(comment.getRootId(), new ArrayList<>());
                value.add(comment);
                rootIdToCommentList.put(comment.getRootId(), value);
            }
        });

        for (Comment rootComment : rootCommentList) {
            // 根评论转为CommentDTO
            User user = userMapper.selectById(rootComment.getCommentator());
            CommentDTO rootCommentDTO = new CommentDTO();
            BeanUtil.copyProperties(user, rootCommentDTO.getUserDTO());
            BeanUtil.copyProperties(rootComment, rootCommentDTO);

            // 在所有子评论中筛选出属于该根评论的子评论
            List<Comment> subCommentList = rootIdToCommentList.getOrDefault(rootComment.getId(), new ArrayList<>());

            // 子评论列表转为CommentDTO列表
            List<CommentDTO> subCommentDTOList = mapList(subCommentList);

            // 如果子评论的父评论不是根评论，则需要把被评论人的名字写入子评论
            for (CommentDTO subCommentDTO : subCommentDTOList) {
                if (!subCommentDTO.getParentId().equals(rootComment.getId())) {
                    Optional<CommentDTO> parentCommentDTO = subCommentDTOList.stream()
                            .filter(commentDTO -> commentDTO.getId().equals(subCommentDTO.getParentId()))
                            .findFirst();
                    subCommentDTO.setGithubNameForBeCriticized(parentCommentDTO.get().getUserDTO().getGithubName());
                }
            }

            rootCommentDTO.setSubCommentDTOList(subCommentDTOList);
            commentDTOList.add(rootCommentDTO);
        }

        return commentDTOList;
    }

    private List<CommentDTO> mapList(List<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = userMapper.selectById(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtil.copyProperties(user, commentDTO.getUserDTO());
            BeanUtil.copyProperties(comment, commentDTO);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
