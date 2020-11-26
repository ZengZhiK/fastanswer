package com.zzk.fastanswer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzk.fastanswer.common.annotation.ModelView;
import com.zzk.fastanswer.common.constant.AppConstants;
import com.zzk.fastanswer.common.constant.TableColConstants;
import com.zzk.fastanswer.model.dto.PageInfo;
import com.zzk.fastanswer.model.dto.QuestionDTO;
import com.zzk.fastanswer.model.entity.Question;
import com.zzk.fastanswer.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页 请求处理
 *
 * @author zzk
 * @create 2020-10-18 18:26
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    /**
     * 社区首页
     */
    @GetMapping("/")
    @ModelView
    public String index(@RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE) Integer page,
                        @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                        @RequestParam(name = "navigatePages", defaultValue = AppConstants.DEFAULT_NAVIGATE_PAGES) Integer navigatePages,
                        @RequestParam(name = "keywords", required = false) String keywords,
                        Model model) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(keywords)) {
            keywords = keywords.trim();
            String[] split = StringUtils.split(keywords, " ");
            String search = String.join("|", split);
            queryWrapper.apply("(" + TableColConstants.TITLE + " regexp {0} " + "or " + TableColConstants.TAG + " regexp {0})", search);
            model.addAttribute("keywords", keywords);
        }
        queryWrapper.orderByDesc(TableColConstants.GMT_MODIFIED);
        PageInfo<QuestionDTO> pageInfo = questionService.listQuestionDtoPage(page, size, navigatePages, queryWrapper);
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }
}
