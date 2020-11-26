package com.zzk.fastanswer.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzk.fastanswer.model.dto.PageInfo;
import com.zzk.fastanswer.model.dto.QuestionBaseDTO;
import com.zzk.fastanswer.model.dto.QuestionDTO;
import com.zzk.fastanswer.model.dto.QuestionDetailDTO;
import com.zzk.fastanswer.model.entity.Question;

import java.util.List;

/**
 * <p>
 * 问题表 服务类
 * </p>
 *
 * @author zzk
 * @since 2020-11-02
 */
public interface QuestionService extends IService<Question> {
    /**
     * 分页查询所有的问题
     *
     * @param page          页码
     * @param size          每页大小
     * @param navigatePages 导航页码数
     * @param queryWrapper  查询条件
     * @return
     */
    PageInfo<Question> listQuestionPage(Integer page, Integer size, Integer navigatePages, Wrapper<Question> queryWrapper);

    /**
     * 页查询所有的问题并转为QuestionBaseDTO形式
     *
     * @param page          页码
     * @param size          页码
     * @param navigatePages 导航页码数
     * @param queryWrapper  查询条件
     * @return
     */
    PageInfo<QuestionBaseDTO> listQuestionBaseDtoPage(Integer page, Integer size, Integer navigatePages, Wrapper<Question> queryWrapper);

    /**
     * 分页查询所有的问题并转为QuestionDTO形式
     *
     * @param page          页码
     * @param size          页码
     * @param navigatePages 导航页码数
     * @param queryWrapper  查询条件
     * @return
     */
    PageInfo<QuestionDTO> listQuestionDtoPage(Integer page, Integer size, Integer navigatePages, Wrapper<Question> queryWrapper);

    /**
     * 根据id查询问题并转为QuestionDetailDTO形式
     *
     * @param id 问题id
     * @return
     */
    QuestionDetailDTO getQuestionDetailDtoById(Long id);

    /**
     * 问题评论数增1
     *
     * @param id 问题id
     */
    void increaseView(Long id);

    /**
     * 查询相关问题
     *
     * @param questionDetailDTO 查询结果与此问题相关
     * @return
     */
    List<QuestionDTO> listRelatedQuestion(QuestionDetailDTO questionDetailDTO);

}
