package com.zzk.fastanswer.controller;

import com.zzk.fastanswer.common.annotation.ModelView;
import com.zzk.fastanswer.common.cache.TagCache;
import com.zzk.fastanswer.common.constant.ExceptionConstants;
import com.zzk.fastanswer.common.constant.SessionConstants;
import com.zzk.fastanswer.common.enums.CustomExceptionType;
import com.zzk.fastanswer.common.exception.CustomException;
import com.zzk.fastanswer.common.utils.AjaxResponse;
import com.zzk.fastanswer.model.dto.TagDTO;
import com.zzk.fastanswer.model.dto.UserDTO;
import com.zzk.fastanswer.model.entity.Question;
import com.zzk.fastanswer.model.entity.User;
import com.zzk.fastanswer.model.vo.QuestionVO;
import com.zzk.fastanswer.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 问题发布 请求处理
 *
 * @author zzk
 * @create 2020-11-02 21:51
 */
@Controller
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private QuestionService questionService;

    /**
     * 到发布问题页面
     */
    @GetMapping
    @ModelView
    public String toPublish(Model model) {
        List<TagDTO> tagDTOList = TagCache.get();
        model.addAttribute("tagDTOList", tagDTOList);
        return "publish";
    }

    /**
     * 发布问题
     */
    @PostMapping
    @ResponseBody
    public AjaxResponse doPublish(@Valid QuestionVO questionVO,
                                  HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        // 保存问题到数据库
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        question.setCreator(loginUser.getId());
        question.setViewCount(0);
        question.setCommentCount(0);
        questionService.save(question);

        return AjaxResponse.success();
    }

    /**
     * 回显问题
     */
    @GetMapping("/{id}")
    @ModelView
    public String echoPublish(@PathVariable("id") Long id, Model model, HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        // 用户登录但问题不属于该用户，无权修改
        Question questionInDb = questionService.getById(id);
        if (!questionInDb.getCreator().equals(loginUser.getId())) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.PERMISSION_DENIED);
        }

        // 查询问题
        Question question = questionService.getById(id);
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);

        // 获取标签
        List<TagDTO> tagDTOList = TagCache.get();

        model.addAttribute("questionVO", questionVO);
        model.addAttribute("tagDTOList", tagDTOList);
        return "publish";
    }

    /**
     * 更新问题
     */
    @PutMapping
    @ResponseBody
    public AjaxResponse updatePublish(@Valid QuestionVO questionVO, HttpSession session) {
        // 从session域获取用户
        UserDTO loginUser = (UserDTO) session.getAttribute(SessionConstants.LOGIN_USER);
        // 如果获取不到则证明用户未登录
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.USER_NO_LOGIN_ERROR);
        }

        // 用户登录但问题不属于该用户，无权修改
        Question questionInDb = questionService.getById(questionVO.getId());
        if (!questionInDb.getCreator().equals(loginUser.getId())) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, ExceptionConstants.PERMISSION_DENIED);
        }

        // 更新问题
        Question question = questionService.getById(questionVO.getId());
        BeanUtils.copyProperties(questionVO, question);
        questionService.updateById(question);

        return AjaxResponse.success();
    }
}
