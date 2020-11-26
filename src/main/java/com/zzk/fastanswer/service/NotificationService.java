package com.zzk.fastanswer.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzk.fastanswer.model.dto.NotificationDTO;
import com.zzk.fastanswer.model.dto.PageInfo;
import com.zzk.fastanswer.model.entity.Notification;
import com.zzk.fastanswer.model.entity.Question;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author zzk
 * @since 2020-11-26
 */
public interface NotificationService extends IService<Notification> {
    /**
     * 分页查询所有通知
     *
     * @param page          页码
     * @param size          每页大小
     * @param navigatePages 导航页码数
     * @param queryWrapper  查询条件
     * @return
     */
    PageInfo<NotificationDTO> listNotificationDtoPage(Integer page, Integer size, Integer navigatePages, Wrapper<Notification> queryWrapper);

    /**
     * 查询用户未读通知数量
     *
     * @param receiverId 用户Id
     * @return 未读通知数量
     */
    int countUnReadByReceiver(Long receiverId);
}
