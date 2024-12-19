<template>
    <div class="login-container">
        <div class="login-box">
            <h2 class="login-title">登录</h2>

            <!-- 用户名输入框 -->
            <el-input
                v-model="user.username"
                placeholder="请输入用户名"
                prefix-icon="el-icon-user"
                class="input-field"
                clearable
            />

            <!-- 密码输入框 -->
            <el-input
                v-model="user.password"
                placeholder="请输入密码"
                type="password"
                prefix-icon="el-icon-lock"
                class="input-field"
                clearable
                @keydown.enter="login"
            />

            <!-- 登录按钮 -->
            <el-button type="primary" @click="login" class="login-btn">
                登录
            </el-button>

            <!-- 错误信息 -->
            <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

            <!-- 忘记密码与注册链接 -->
            <div class="additional-links">
                <a href="#" @click.prevent="onForgotPassword">忘记密码?</a>
                <a href="#" @click.prevent="onRegister">注册</a>
            </div>
        </div>
    </div>
</template>

<script>
import { post } from '@/utils/request';

export default {
    name: 'LoginPage',
    data() {
        return {
            user: {
                username: '',
                password: '',
            },
            errorMessage: '', // 错误信息
        };
    },
    methods: {
        // 登录函数
        async login() {
            this.errorMessage = ''; // 清空之前的错误信息

            // 表单验证
            if (!this.user.password || !this.user.username) {
                this.errorMessage = '用户名和密码不能为空';
                return;
            }
            try {
                // 发送登录请求
                const response = await post('/api/users/login', this.user);

                // 直接检查响应的 code 和 message
                const { code, data, message } = response;

                if (code === 200 && data) {

                    // 将 token 存储到 localStorage
                    localStorage.setItem('authToken', data);

                    this.$message({
                        message: '登录成功',
                        type: 'success'
                    });

                    // 跳转到主页
                    await this.$router.push({ name: 'MainPage' });
                } else {
                    // 如果 success 为 false，则显示服务器消息
                    this.errorMessage = message || '登录失败，请重试';
                }
            } catch (error) {
                console.error('Login error:', error);
                // 捕获请求错误并区分错误类型
                if (error.response) {
                    // 后端返回的错误
                    this.errorMessage = error.response.data.message || '登录失败，请重试';
                } else {
                    // 网络错误
                    this.errorMessage = '网络错误，请稍后重试';
                }
                console.error('Login error:', error);
            }
        },

        // 忘记密码逻辑（可以跳转或展示提示）
        onForgotPassword() {
            this.$message('请联系管理员找回密码');
        },
        // 跳转到注册页面
        onRegister() {
            this.$router.push({ name: 'RegisterPage' });
        },
    },
};
</script>

<style scoped>
/* 页面样式 */
.login-container {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url('@/assets/background/preview.jpg') no-repeat center center;
    background-size: cover; /* 背景图适应页面大小 */

}

.login-box {
    width: 90%;
    max-width: 400px;
    padding: 2rem;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.login-title {
    font-size: 2rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
}

.input-field {
    width: 100%;
    margin-bottom: 1rem;
}

.login-btn {
    width: 100%;
    padding: 1rem;
    font-size: 1.1rem;
}

.additional-links {
    margin-top: 1rem;
    display: flex;
    justify-content: space-between;
}

.additional-links a {
    color: #00f2fe;
    text-decoration: none;
    font-size: 0.9rem;
}

.additional-links a:hover {
    text-decoration: underline;
}

/* 错误信息样式 */
.error-message {
    color: red;
    margin-top: 1rem;
}
</style>
