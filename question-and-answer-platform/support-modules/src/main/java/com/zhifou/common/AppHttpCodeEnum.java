package com.zhifou.common;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    USER_NAME_EXIST(502,"用户名已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "用户名不能为空"),
    REQUIRE_CONTENT(520, "评论内容不能为空"),
    REQUIRE_ROLE_NAME(518, "角色名称不能为空"),
    LOGIN_ERROR(505,"用户名不存在，请先注册"),
    PASSWORD_ERROR(505,"密码错误"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
   FILE_TYPE_ERROR (507,"文件类型错误，只能上传png类型") ;
    final int code;
    final String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

