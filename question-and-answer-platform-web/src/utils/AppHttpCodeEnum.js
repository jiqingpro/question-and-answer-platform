const AppHttpCodeEnum = {
    SUCCESS: { code: 200, msg: "操作成功" },
    NEED_LOGIN: { code: 401, msg: "需要登录后操作" },
    NO_OPERATOR_AUTH: { code: 403, msg: "无权限操作" },
    SYSTEM_ERROR: { code: 500, msg: "出现错误" },
    USERNAME_EXIST: { code: 501, msg: "用户名已存在" },
    USER_NAME_EXIST: { code: 502, msg: "用户名已存在" },
    EMAIL_EXIST: { code: 503, msg: "邮箱已存在" },
    REQUIRE_USERNAME: { code: 504, msg: "用户名不能为空" },
    REQUIRE_CONTENT: { code: 520, msg: "评论内容不能为空" },
    REQUIRE_ROLE_NAME: { code: 518, msg: "角色名称不能为空" },
    LOGIN_ERROR: { code: 505, msg: "用户名不存在，请先注册" },
    PASSWORD_ERROR: { code: 505, msg: "密码错误" },
    USERNAME_NOT_NULL: { code: 508, msg: "用户名不能为空" },
    NICKNAME_NOT_NULL: { code: 509, msg: "昵称不能为空" },
    PASSWORD_NOT_NULL: { code: 510, msg: "密码不能为空" },
    EMAIL_NOT_NULL: { code: 511, msg: "邮箱不能为空" },
    NICKNAME_EXIST: { code: 512, msg: "昵称已存在" },
    FILE_TYPE_ERROR: { code: 507, msg: "文件类型错误，只能上传png类型" },
};

export default AppHttpCodeEnum;