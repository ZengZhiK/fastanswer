package com.zzk.fastanswer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzk.fastanswer.common.annotation.ModelView;
import com.zzk.fastanswer.common.constant.AppConstants;
import com.zzk.fastanswer.common.constant.ExceptionConstants;
import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.common.constant.TableColConstants;
import com.zzk.fastanswer.common.enums.CustomExceptionType;
import com.zzk.fastanswer.common.exception.CustomException;
import com.zzk.fastanswer.model.dto.NotificationDTO;
import com.zzk.fastanswer.model.dto.PageInfo;
import com.zzk.fastanswer.model.dto.QuestionBaseDTO;
import com.zzk.fastanswer.model.dto.UserDTO;
import com.zzk.fastanswer.model.entity.Notification;
import com.zzk.fastanswer.model.entity.Question;
import com.zzk.fastanswer.service.NotificationService;
import com.zzk.fastanswer.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 个人页 请求处理
 *
 * @author zzk
 * @create 2020-11-07 20:49
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    /**
     * 到我的问题页面
     */
    @GetMapping("/my_question")
    @ModelView
    public String toMyQuestion(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE) Integer page,
                               @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                               @RequestParam(name = "navigatePages", defaultValue = AppConstants.DEFAULT_NAVIGATE_PAGES) Integer navigatePages,
                               Model model, HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TableColConstants.CREATOR, loginUser.getId());
        queryWrapper.orderByDesc(TableColConstants.GMT_MODIFIED);
        PageInfo<QuestionBaseDTO> pageInfo = questionService.listQuestionBaseDtoPage(page, size, navigatePages, queryWrapper);

        model.addAttribute("pageInfo", pageInfo);
        return "profile/my_question";
    }

    /**
     * 到最新回复页面
     */
    @GetMapping("/latest_reply")
    @ModelView
    public String toLatestReply(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE) Integer page,
                                @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                @RequestParam(name = "navigatePages", defaultValue = AppConstants.DEFAULT_NAVIGATE_PAGES) Integer navigatePages,
                                Model model, HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TableColConstants.RECEIVER, loginUser.getId());
        queryWrapper.orderByDesc(TableColConstants.GMT_CREATE);
        PageInfo<NotificationDTO> pageInfo = notificationService.listNotificationDtoPage(page, size, navigatePages, queryWrapper);

        model.addAttribute("pageInfo", pageInfo);
        return "profile/latest_reply";
    }
}
