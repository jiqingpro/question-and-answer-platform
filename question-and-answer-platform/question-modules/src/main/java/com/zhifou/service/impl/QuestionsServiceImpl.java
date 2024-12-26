package com.zhifou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhifou.common.AppHttpCodeEnum;
import com.zhifou.entity.Questions;
import com.zhifou.exception.SystemException;
import com.zhifou.mapper.QuestionsMapper;
import com.zhifou.service.QuestionsServise;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xzj
 * @file QuestionsServiseImpl
 * @description
 * @date 2024/12/25 21:39
 */

@Service
public class QuestionsServiceImpl implements QuestionsServise {

    @Resource
    private QuestionsMapper questionsMapper;

    /**
     * 创建问题 问题模块的业务逻辑接口 提问功能
     * @param question 问题对象
     * @return
     */
    @Override
    public Questions createQuestion(Questions question) {
        int insert = questionsMapper.insert(question);
        if (insert != 1) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return question;
    }

    @Override
    public List<Questions> getNewQuestions() {
        QueryWrapper<Questions> queryWrapper = new QueryWrapper<>();
        // 按照创建时间降序排列，查询最新的问题
        queryWrapper.orderByDesc("created_at");
        return questionsMapper.selectList(queryWrapper);
    }
}
