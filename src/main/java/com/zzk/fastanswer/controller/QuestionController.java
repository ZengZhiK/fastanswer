package com.zzk.fastanswer.controller;


import com.zzk.fastanswer.common.annotation.ModelView;
import com.zzk.fastanswer.model.dto.QuestionDTO;
import com.zzk.fastanswer.model.dto.QuestionDetailDTO;
import com.zzk.fastanswer.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 * 问题表 前端控制器
 * </p>
 *
 * @author zzk
 * @create 2020-11-8 14:48
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * 问题详情
     */
    @GetMapping("/{id}")
    @ModelView
    public String toQuestionDetail(@PathVariable("id") Long id, Model model) {
        // 问题阅读数增加1
        questionService.increaseView(id);
        // 查询问题详情
        QuestionDetailDTO questionDetailDTO = questionService.getQuestionDetailDtoById(id);
        // 查询相关问题
        List<QuestionDTO> RelatedQuestionDTOList = questionService.listRelatedQuestion(questionDetailDTO);

        model.addAttribute("questionDetailDTO", questionDetailDTO);
        model.addAttribute("RelatedQuestionDTOList", RelatedQuestionDTOList);
        return "question";
    }
}

