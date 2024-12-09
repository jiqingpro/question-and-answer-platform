package com.zhifou.backend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhifou.entity.Users;
import com.zhifou.service.UsersService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChattingSoftwareBackendApplicationTests {

    @Resource
    private UsersService usersService;

    @Test
    void contextLoads() {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", "10").like("username", "John");// WHERE  (user_id = ? AND username LIKE ?)
//        queryWrapper.ge("user_id",10);//WHERE  (userid >= ?)
        queryWrapper.eq("user_id", 10);
        System.out.println(usersService.customSelectList(queryWrapper));
    }

}
