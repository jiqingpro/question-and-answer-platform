package com.zhifou.controller;

import com.zhifou.common.AppHttpCodeEnum;
import com.zhifou.entity.Users;
import com.zhifou.exception.SystemException;
import com.zhifou.service.TokenService;
import com.zhifou.service.UsersService;
import com.zhifou.tools.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    @Resource
    private UsersService usersService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/login")
    public Response login(@RequestBody Users user) {
        try {
            boolean isAuthenticated = usersService.authenticate(user.getUsername(), user.getPassword());
            if (isAuthenticated) {
                String token = tokenService.generateToken(user.getUsername());
                return Response.success("登录成功", token);
            } else {
                return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "登录失败");
            }
        } catch (Exception e) {
            return handleException(e, "登录异常");
        }
    }

    @PostMapping("/register")
    public Response register(@RequestBody Users user) {
        try {
            String encryptedPassword = usersService.encryptPassword(user.getPassword());
            usersService.saveNewUser(user, encryptedPassword);
            String token = tokenService.generateToken(user.getUsername());
            return Response.success("注册成功", token);
        } catch (Exception e) {
            return handleException(e, "注册异常");
        }
    }

    @PostMapping("/logout")
    public Response<?> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            tokenService.invalidateToken(token);
        }
        return Response.success("退出成功");
    }

    private Response handleException(Exception e, String logMessage) {
        if (e instanceof SystemException) {
            log.error("{}: {}", logMessage, e.getMessage());
            SystemException se = (SystemException) e;
            return Response.error(se.getCode(), se.getMsg());
        } else {
            log.error("{}: {}", logMessage, e.getMessage(), e);
            return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "系统错误，请稍后再试");
        }
    }
}