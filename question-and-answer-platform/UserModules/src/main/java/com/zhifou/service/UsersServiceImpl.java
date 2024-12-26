package com.zhifou.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhifou.common.AppHttpCodeEnum;
import com.zhifou.entity.Users;
import com.zhifou.exception.SystemException;
import com.zhifou.mapper.UsersMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder; // 使用 BCryptPasswordEncoder 进行密码验证

    public List<Users> customSelectList(Wrapper<Users> queryWrapper) {
        return usersMapper.selectList(queryWrapper);
    }


    public Users authenticate(String username, String password) {
        // 1. 从数据库中查询用户
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        Users user = usersMapper.selectOne(queryWrapper.eq("username", username));
        if (user == null) {
           throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // 2. 校验密码是否正确
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_ERROR);
        }
        return user;
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void saveNewUser(Users user, String encryptedPassword) {
        String email = user.getEmail();
        String phoneNumber = user.getUsername();  // 假设用户类中有 getPhoneNumber 方法

        // 使用OR查询，同时检查邮箱和手机号是否已存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email).or().eq("username", phoneNumber);

        // 执行查询
        Users existingUser = usersMapper.selectOne(queryWrapper);

        // 如果用户存在，抛出相应异常
        if (existingUser != null) {
            // 根据返回的用户信息判断是邮箱冲突还是手机号冲突
            if (email.equals(existingUser.getEmail())) {
                throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
            } else if (phoneNumber.equals(existingUser.getUsername())) {
                throw new SystemException(AppHttpCodeEnum.USER_NAME_EXIST);
            }
        }

        // 设置密码和用户ID
        user.setPassword(encryptedPassword);
        user.setUserId((int) Math.floor(Math.random() * 100000000));

        // 插入到数据库
        usersMapper.insert(user);
    }


}

