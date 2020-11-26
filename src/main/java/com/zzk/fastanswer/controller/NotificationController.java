package com.zzk.fastanswer.controller;


import com.zzk.fastanswer.common.constant.ExceptionConstants;
import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.common.enums.CustomExceptionType;
import com.zzk.fastanswer.common.exception.CustomException;
import com.zzk.fastanswer.common.utils.AjaxResponse;
import com.zzk.fastanswer.model.dto.UserDTO;
import com.zzk.fastanswer.model.entity.Notification;
import com.zzk.fastanswer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 通知表 前端控制器
 * </p>
 *
 * @author zzk
 * @since 2020-11-26
 */
@Controller
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PutMapping
    @ResponseBody
    public AjaxResponse updateNotificationStatus(@RequestParam("id") Long id, HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        Notification notification = new Notification();
        notification.setId(id).setStatus(true);
        notificationService.updateById(notification);

        // 更新session域的通知数
        int unread = notificationService.countUnReadByReceiver(loginUser.getId());
        session.setAttribute(SessionConstants.UNREAD, unread);

        return AjaxResponse.success();
    }
}

