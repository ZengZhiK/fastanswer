package com.zzk.fastanswer.mapper;

import com.zzk.fastanswer.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 问题表 Mapper 接口
 * </p>
 *
 * @author zzk
 * @since 2020-11-02
 */
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 增加一次阅读数
     *
     * @param id 问题id
     */
    void updateViewCountOnce(Long id);

    /**
     * 增加一次评论数
     *
     * @param id 问题id
     */
    void updateCommentCountOnce(Long id);

    /**
     * 查询相关问题
     *
     * @param question 查询结果与此问题相关
     * @return
     */
    List<Question> selectRelated(Question question);
}
