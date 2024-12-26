package com.zhifou.controller;

import com.zhifou.entity.Questions;
import com.zhifou.service.QuestionsServise;
import com.zhifou.tools.Response;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xzj
 * @file QuestionsController
 * @description 提问功能的控制器
 * @date 2024/12/25 21:35
 */

@RestController
@RequestMapping("/api/ask")
public class QuestionsController {

    @Resource
    private QuestionsServise questionsService;

    @PostMapping("/createQuestions")
    public Response createQuestion(@RequestBody @Valid Questions question) {
        try {
            Questions createdQuestion = questionsService.createQuestion(question);
            return Response.success("创建问题成功", createdQuestion);
        } catch (Exception e) {
            return Response.error(500, e.getMessage());
        }
    }
}