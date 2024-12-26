package com.zhifou.service;

import com.zhifou.entity.Users;

/**
 * @author Xzj
 * @date 2024/12/25
 */
public interface UsersService {
    /**
     * 验证用户
     * @param username 用户名
     * @param password 密码
     * @return 是否验证通过
     */
    Users authenticate(String username, String password);

    /**
     * 加密密码
     * @param password 密码
     * @return 加密后的密码
     */
    String encryptPassword(String password);

    /**
     * 保存新用户
     * @param user 用户
     * @param encryptedPassword 加密后的密码
     */
    void saveNewUser(Users user, String encryptedPassword);
}
