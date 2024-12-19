<template>
    <div class="register-container">
        <div class="register-box">
            <h2 class="register-title">注册</h2>

            <!-- 用户名输入框 -->
            <el-input
                v-model="username"
                placeholder="请输入用户名"
                prefix-icon="el-icon-user"
                class="input-field"
                clearable
            />

            <!-- 邮箱输入框 -->
            <el-input
                v-model="email"
                placeholder="请输入邮箱"
                prefix-icon="el-icon-message"
                class="input-field"
                clearable
            />

            <!-- 密码输入框 -->
            <el-input
                v-model="password"
                placeholder="请输入密码"
                type="password"
                prefix-icon="el-icon-lock"
                class="input-field"
                clearable
            />

            <!-- 确认密码输入框 -->
            <el-input
                v-model="confirmPassword"
                placeholder="请再次输入密码"
                type="password"
                prefix-icon="el-icon-lock"
                class="input-field"
                clearable
            />

            <!-- 注册按钮 -->
            <el-button
                type="primary"
                class="register-btn"
                :disabled="isRegistering"
                @click="register"
            >
                {{ isRegistering ? '注册中...' : '注册' }}
            </el-button>

            <!-- 提示信息 -->
            <div v-if="message" :class="messageType === 'error' ? 'error-message' : 'success-message'">
                {{ message }}
            </div>

            <!-- 返回登录链接 -->
            <div class="additional-links">
                <a href="#" @click.prevent="goToLogin">返回登录</a>
            </div>
        </div>
    </div>
</template>

<script>
import {post} from '@/utils/request'; // 使用封装的 POST 方法

export default {
    name: "RegisterPage",
    data() {
        return {
            username: "",
            email: "",
            password: "",
            confirmPassword: "",
            isRegistering: false, // 防止重复点击
            message: "", // 提示信息
            messageType: "", // 提示类型（success/error）
        };
    },
    methods: {
        // 注册逻辑
        async register() {
            // 清空提示信息
            this.message = "";
            this.messageType = "";

            // 表单校验
            if (!this.username || !this.email || !this.password || !this.confirmPassword) {
                this.message = "请完整填写所有字段";
                this.messageType = "error";
                return;
            }

            if (this.password !== this.confirmPassword) {
                this.message = "两次输入的密码不一致";
                this.messageType = "error";
                return;
            }

            // 设置注册中状态
            this.isRegistering = true;

            try {
                // 调用注册接口
                const { code, message, data } = await post("/api/users/register", {
                    username: this.username,
                    email: this.email,
                    password: this.password,
                });

                // 成功逻辑处理
                if (code === 200 && data) {
                    this.message = "注册成功，请前往登录";
                    this.messageType = "success";
                    setTimeout(() => this.$router.push({ name: "LoginPage" }), 2000); // 两秒后跳转到登录页面
                } else {
                    // 根据后端返回的消息展示错误
                    this.message = message || "注册失败，请重试";
                    this.messageType = "error";
                }
            } catch (error) {
                // 捕获错误并区分类型
                console.error("Register error:", error);

                if (error.response) {
                    // 后端返回的错误
                    this.message = error.response.data.message || "注册失败，请重试";
                } else {
                    // 网络或其他错误
                    this.message = "网络错误，请稍后重试";
                }

                this.messageType = "error";
            } finally {
                // 重置注册状态
                this.isRegistering = false;
            }
        },

        // 返回登录页面
        goToLogin() {
            this.$router.push({name: "LoginPage"});
        },
    },
};
</script>

<style scoped>
/* 页面样式 */
.register-container {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(to right, #4facfe, #00f2fe);
}

.register-box {
    width: 90%;
    max-width: 400px;
    padding: 2rem;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.register-title {
    font-size: 2rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
}

.input-field {
    width: 100%;
    margin-bottom: 1rem;
}

.register-btn {
    width: 100%;
    padding: 1rem;
    font-size: 1.1rem;
    margin-top: 1rem;
}

.additional-links {
    margin-top: 1rem;
    display: flex;
    justify-content: center;
}

.additional-links a {
    color: #00f2fe;
    text-decoration: none;
    font-size: 0.9rem;
}

.additional-links a:hover {
    text-decoration: underline;
}

/* 错误或提示信息样式 */
.message {
    margin-top: 1rem;
    font-size: 0.9rem;
}

.error-message {
    color: red;
}

.success-message {
    color: green;
}
</style>
