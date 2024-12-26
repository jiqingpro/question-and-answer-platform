package com.zhifou.service;

import com.zhifou.entity.Questions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xzj
 * @file QuestionsServise
 * @description
 * @date 2024/12/25 21:38
 */
@Service
public interface QuestionsServise {

    /**
     * 创建问题 问题模块的业务逻辑接口 提问功能
     * @param question
     * @return
     */
    Questions createQuestion(Questions question);


    List<Questions> getNewQuestions();
}
