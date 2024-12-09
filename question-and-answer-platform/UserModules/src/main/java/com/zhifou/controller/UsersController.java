package com.zhifou.controller;

import com.zhifou.entity.Users;
import com.zhifou.service.TokenService;
import com.zhifou.service.UsersService;
import com.zhifou.tools.LoginResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Resource
    private UsersService usersService;

    @Resource
    private TokenService tokenService;


    // 登录接口
    @PostMapping("/login")
    public LoginResponse login(@RequestBody Users user) {
        try {
            // 调用业务逻辑服务来验证用户
            boolean isAuthenticated = usersService.authenticate(user.getUsername(), user.getPassword());

            if (isAuthenticated) {
                // 登录成功，返回 token 或其他信息
                String token = tokenService.generateToken(user.getUsername());
                return new LoginResponse(true, "登录成功", token);
            } else {
                // 登录失败
                return new LoginResponse(false, "用户名或密码错误", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new LoginResponse(false, "程序错误", null);
        }

    }

    @PostMapping("/register")
    public LoginResponse register(@RequestBody Users user) {
        try {
            String encryptedPassword = usersService.encryptPassword(user.getPassword());
            // 保存用户到数据库
            usersService.saveNewUser(user, encryptedPassword);
            String token = tokenService.generateToken(user.getUsername());
            return new LoginResponse(true, "注册成功", token);
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(false, "注册失败", null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // 清理服务器端的用户状态，例如 JWT 无法再次使用、销毁 Session 等
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            //tokenService.invalidateToken(token); // 自定义方法，处理 Token 无效化
        }
        return ResponseEntity.ok().body("退出成功");
    }

}
