package com.zzk.fastanswer.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzk.fastanswer.mapper.QuestionMapper;
import com.zzk.fastanswer.mapper.UserMapper;
import com.zzk.fastanswer.model.dto.PageInfo;
import com.zzk.fastanswer.model.dto.QuestionBaseDTO;
import com.zzk.fastanswer.model.dto.QuestionDTO;
import com.zzk.fastanswer.model.dto.QuestionDetailDTO;
import com.zzk.fastanswer.model.entity.Question;
import com.zzk.fastanswer.model.entity.User;
import com.zzk.fastanswer.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 问题表 服务实现类
 * </p>
 *
 * @author zzk
 * @since 2020-11-02
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<Question> listQuestionPage(Integer page, Integer size, Integer navigatePages, Wrapper<Question> queryWrapper) {
        // 分页查询问题
        IPage<Question> questionPage = new Page<>(page, size);
        questionPage = baseMapper.selectPage(questionPage, queryWrapper);
        // 封装为PageInfo返回
        return new PageInfo<>(questionPage, navigatePages);
    }

    @Override
    public PageInfo<QuestionBaseDTO> listQuestionBaseDtoPage(Integer page, Integer size, Integer navigatePages, Wrapper<Question> queryWrapper) {
        // 分页查询问题
        PageInfo<Question> questionPageInfo = listQuestionPage(page, size, navigatePages, queryWrapper);

        List<QuestionBaseDTO> questionBaseDTOList = new ArrayList<>();
        for (Question question : questionPageInfo.getList()) {
            QuestionBaseDTO questionBaseDTO = new QuestionBaseDTO();
            BeanUtils.copyProperties(question, questionBaseDTO);
            questionBaseDTOList.add(questionBaseDTO);
        }

        return questionPageInfo.covertTo(questionBaseDTOList);
    }

    @Override
    public PageInfo<QuestionDTO> listQuestionDtoPage(Integer page, Integer size, Integer navigatePages, Wrapper<Question> queryWrapper) {
        // 分页查询问题
        PageInfo<Question> questionPageInfo = listQuestionPage(page, size, navigatePages, queryWrapper);

        // 将问题列表的每个记录转换为questionDTO
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questionList = questionPageInfo.getList();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();

            User user = userMapper.selectById(question.getCreator());
            BeanUtils.copyProperties(user, questionDTO.getUserDTO());
            BeanUtils.copyProperties(question, questionDTO);

            questionDTOList.add(questionDTO);
        }

        // 封装为PageInfo返回
        return questionPageInfo.covertTo(questionDTOList);
    }

    @Override
    public QuestionDetailDTO getQuestionDetailDtoById(Long id) {
        QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
        Question question = baseMapper.selectById(id);
        User user = userMapper.selectById(question.getCreator());
        BeanUtils.copyProperties(user, questionDetailDTO.getUserDTO());
        BeanUtils.copyProperties(question, questionDetailDTO);
        return questionDetailDTO;
    }

    @Override
    public void increaseView(Long id) {
        baseMapper.updateViewCountOnce(id);
    }

    @Override
    public List<QuestionDTO> listRelatedQuestion(QuestionDetailDTO questionDetailDTO) {
        if (StringUtils.isBlank(questionDetailDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDetailDTO.getTag(), ",");
        String regexpTag = String.join("|", tags);
        Question question = new Question();
        question.setId(questionDetailDTO.getId());
        question.setTag(regexpTag);

        List<Question> questionList = baseMapper.selectRelated(question);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question relatedQuestion : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(relatedQuestion, questionDTO);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
