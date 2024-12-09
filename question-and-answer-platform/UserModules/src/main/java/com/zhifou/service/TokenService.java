package com.zhifou.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    // 密钥可以使用更强的方式存储，如配置文件或环境变量等
    private static final String SECRET_KEY = "mySecretKeyhjfkjkjhdfjksadkhkgsjahkfasdklglkshdjgkfhweiuhskfjgdsa"; // 密钥可以放到配置文件中

    // 设置 token 有效期为 1小时
    private static final long EXPIRATION_TIME = 60 * 60 * 1000 * 24; // 24小时


    /**
     * 生成 JWT Token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 设置主题（用户信息）
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 使用 HMAC-SHA256 算法加密，并传入密钥
                .compact(); // 构建并返回 token 字符串
    }

    /**
     * 验证 token 并提取用户名
     */
    public String validateTokenAndGetUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // 获取载荷中的用户名
    }
}
