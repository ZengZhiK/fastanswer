package com.zzk.fastanswer.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzk.fastanswer.common.constant.TableColConstants;
import com.zzk.fastanswer.mapper.CommentMapper;
import com.zzk.fastanswer.mapper.QuestionMapper;
import com.zzk.fastanswer.mapper.UserMapper;
import com.zzk.fastanswer.model.dto.NotificationDTO;
import com.zzk.fastanswer.model.dto.PageInfo;
import com.zzk.fastanswer.model.entity.Comment;
import com.zzk.fastanswer.model.entity.Notification;
import com.zzk.fastanswer.mapper.NotificationMapper;
import com.zzk.fastanswer.model.entity.Question;
import com.zzk.fastanswer.model.entity.User;
import com.zzk.fastanswer.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2020-11-26
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public PageInfo<NotificationDTO> listNotificationDtoPage(Integer page, Integer size, Integer navigatePages, Wrapper<Notification> queryWrapper) {
        // 分页查询通知
        IPage<Notification> notificationPage = new Page<>(page, size);
        notificationPage = baseMapper.selectPage(notificationPage, queryWrapper);

        List<NotificationDTO> notificationDtoList = new ArrayList<>();

        for (Notification notification : notificationPage.getRecords()) {
            NotificationDTO notificationDTO = new NotificationDTO();

            User user = userMapper.selectById(notification.getNotifier());
            Question question = questionMapper.selectById(notification.getQuestionId());
            Comment comment = commentMapper.selectById(notification.getCommentId());

            notificationDTO.setId(notification.getId());
            notificationDTO.setNotifierGithubName(user.getGithubName());
            notificationDTO.setNotifierGithubAvatarUrl(user.getGithubAvatarUrl());
            notificationDTO.setQuestionId(question.getId());
            notificationDTO.setQuestionTitle(question.getTitle());
            notificationDTO.setCommentContent(comment.getContent());
            notificationDTO.setStatus(notification.getStatus());

            if (notification.getParentCommentId() != null) {
                Comment parentComment = commentMapper.selectById(notification.getParentCommentId());
                notificationDTO.setParentCommentContent(parentComment.getContent());
            }

            notificationDtoList.add(notificationDTO);
        }

        return new PageInfo<>(notificationPage, navigatePages).covertTo(notificationDtoList);
    }

    @Override
    public int countUnReadByReceiver(Long receiverId) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TableColConstants.STATUS, false);
        queryWrapper.eq(TableColConstants.RECEIVER, receiverId);
        return baseMapper.selectCount(queryWrapper);
    }
}
