package com.zhifou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("questions")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "question_id", type = IdType.AUTO)
    private Long questionId;

    /**
     * 提问者的用户ID
     */
    private Long userId;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 问题分类
     */
    private String category;

    /**
     * 问题标签
     */
    private Integer views;

    /**
     * 问题标签
     */
    private Integer likes;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 最后修改时间
     */
    private Timestamp updatedAt;
}