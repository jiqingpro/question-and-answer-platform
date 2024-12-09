package com.zhifou.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhifou.entity.Users;
import com.zhifou.mapper.UsersMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder; // 使用 BCryptPasswordEncoder 进行密码验证

    public List<Users> customSelectList(Wrapper<Users> queryWrapper) {
        return usersMapper.selectList(queryWrapper);
    }


    public boolean authenticate(String username, String password) {
        // 1. 从数据库中查询用户
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        Users user = usersMapper.selectOne(queryWrapper.eq("username", username));
        if (user == null) {
            return false; // 用户不存在
        }

        // 2. 校验密码是否正确
        return passwordEncoder.matches(password, user.getPassword()); // 使用密码加密方式进行验证
    }

    // 生成加密密码（用于注册新用户时）
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // 保存新用户
    public void saveNewUser(Users user, String encryptedPassword) {
        user.setPassword(encryptedPassword);
        user.setUserId((int) Math.floor(Math.random() * 100000000));
        // 插入到数据库
        usersMapper.insert(user);

    }
}

