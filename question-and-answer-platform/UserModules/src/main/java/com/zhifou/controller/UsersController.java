package com.zhifou.controller;

import com.zhifou.common.AppHttpCodeEnum;
import com.zhifou.entity.Users;
import com.zhifou.exception.SystemException;
import com.zhifou.service.TokenService;
import com.zhifou.service.UsersService;
import com.zhifou.tools.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    @Resource
    private UsersService usersService;

    @Resource
    private TokenService tokenService;


    // 登录接口
    @PostMapping("/login")
    public Response login(@Valid @RequestBody Users user) {
        try {
            // 调用业务逻辑服务来验证用户
            boolean isAuthenticated = usersService.authenticate(user.getUsername(), user.getPassword());

            if (isAuthenticated) {
                // 登录成功，返回 token 或其他信息
                String token = tokenService.generateToken(user.getUsername());
                return Response.success("登录成功",token);
            } else {
                // 登录失败'
                return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "登录失败");
            }
        }catch (SystemException e) {
            log.error("登录异常: {}", e.getMessage());
            return Response.error(e.getCode(), e.getMsg());

        } catch (Exception e){
            log.error(e.getMessage());
            return  Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "程序错误", e.getMessage());
        }

    }

    @PostMapping("/register")
    public Response register(@RequestBody Users user) {
        try {
            String encryptedPassword = usersService.encryptPassword(user.getPassword());

            // 保存用户到数据库
            usersService.saveNewUser(user, encryptedPassword);

            // 生成Token
            String token = tokenService.generateToken(user.getUsername());

            return Response.success("注册成功", token);

        } catch (SystemException e) {
            log.error("注册异常: {}", e.getMessage());
            return Response.error(e.getCode(), e.getMsg());

        } catch (Exception e) {
            // 处理其他异常
            log.error("Unexpected error: {}", e.getMessage(), e);
            return Response.error(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "系统错误，请稍后再试");
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
