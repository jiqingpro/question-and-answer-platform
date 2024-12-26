<template>
    <div class="login-container">
        <div class="login-box">
            <div class="login-content">
                <h2 class="login-title">欢迎回来</h2>
                <p class="login-subtitle">请登录您的账号</p>

                <form @submit.prevent="login" class="login-form">
                    <!-- 用户名输入框 -->
                    <el-input
                        v-model.trim="user.username"
                        placeholder="请输入用户名"
                        prefix-icon="el-icon-user"
                        class="input-field"
                        :class="{ 'error-input': errors.username }"
                        clearable
                        @input="clearError('username')"
                    />
                    <span v-if="errors.username" class="error-text">{{ errors.username }}</span>

                    <!-- 密码输入框 -->
                    <el-input
                        v-model.trim="user.password"
                        placeholder="请输入密码"
                        type="password"
                        prefix-icon="el-icon-lock"
                        class="input-field"
                        :class="{ 'error-input': errors.password }"
                        clearable
                        @keyup.enter="login"
                        @input="clearError('password')"
                    />
                    <span v-if="errors.password" class="error-text">{{ errors.password }}</span>

                    <!-- 记住我选项 -->
                    <div class="remember-forgot">
                        <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                        <a href="#" @click.prevent="onForgotPassword" class="forgot-link">忘记密码?</a>
                    </div>

                    <!-- 登录按钮 -->
                    <el-button
                        type="primary"
                        @click="login"
                        class="login-btn"
                        :loading="loading"
                    >
                        {{ loading ? '登录中...' : '登录' }}
                    </el-button>

                    <!-- 错误信息 -->
                    <div v-if="errorMessage" class="error-message">
                        <i class="el-icon-warning"></i>
                        {{ errorMessage }}
                    </div>

                    <!-- 注册链接 -->
                    <div class="register-link">
                        还没有账号？
                        <a href="#" @click.prevent="onRegister">立即注册</a>
                    </div>
                </form>
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
            errors: {
                username: '',
                password: '',
            },
            errorMessage: '',
            loading: false,
            rememberMe: false
        };
    },
    created() {
        const rememberMe = localStorage.getItem('rememberMe') === 'true';
        if (rememberMe) {
            const savedUsername = localStorage.getItem('username');
            const savedPassword = localStorage.getItem('password');
            if (savedUsername && savedPassword) {
                this.user.username = savedUsername;
                this.user.password = savedPassword;
                this.rememberMe = true;
            }
        }
    },
    methods: {
        clearError(field) {
            this.errors[field] = '';
            this.errorMessage = '';
        },
        validateForm() {
            let isValid = true;
            this.errors = {
                username: '',
                password: ''
            };
            if (!this.user.username) {
                this.errors.username = '请输入用户名';
                isValid = false;
            }
            if (!this.user.password) {
                this.errors.password = '请输入密码';
                isValid = false;
            }
            return isValid;
        },
        async login() {
            if (!this.validateForm()) return;

            this.loading = true;
            this.errorMessage = '';
            try {
                const response = await post('/api/users/login', this.user);
                const {code, data, message} = response;
                if (code === 200 && data) {
                    localStorage.setItem('authToken', data.token);
                    if (this.rememberMe) {
                        localStorage.setItem('username', this.user.username);
                        localStorage.setItem('password', this.user.password);
                        localStorage.setItem('rememberMe', 'true');
                        localStorage.setItem('userId', data.userId);
                    } else {
                        sessionStorage.setItem('authToken', data);
                        localStorage.removeItem('username');
                        localStorage.removeItem('password');
                        localStorage.removeItem('rememberMe');
                        localStorage.setItem('userId', data.userId);
                    }
                    this.$message({
                        message: '登录成功',
                        type: 'success'
                    });
                    await this.$router.push({name: 'MainPage'});
                } else {
                    this.errorMessage = message || '登录失败，请重试';
                }
            } catch (error) {
                console.error('Login error:', error);
                this.errorMessage = error.response?.data?.message || '网络错误，请稍后重试';
            } finally {
                this.loading = false;
            }
        },
        onForgotPassword() {
            this.$message({
                message: '请联系管理员找回密码',
                type: 'info'
            });
        },
        onRegister() {
            this.$router.push({name: 'RegisterPage'});
        },
    },
}
</script>

<style scoped>
.login-container {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, rgba(0, 242, 254, 0.1), rgba(0, 0, 0, 0.1)),
    url('@/assets/background/preview.jpg') no-repeat center center;
    background-size: cover;
    position: relative;
}

.login-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(5px);
}

.login-box {
    width: 90%;
    max-width: 420px;
    position: relative;
    z-index: 1;
}

.login-content {
    background: rgba(255, 255, 255, 0.95);
    padding: 2.5rem;
    border-radius: 15px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.login-title {
    font-size: 2.2rem;
    font-weight: 600;
    color: #333;
    margin-bottom: 0.5rem;
}

.login-subtitle {
    color: #666;
    margin-bottom: 2rem;
    font-size: 1rem;
}

.login-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.input-field {
    transition: all 0.3s ease;
}

.input-field:hover {
    transform: translateY(-1px);
}

.error-input {
    border-color: #ff4949;
}

.error-text {
    color: #ff4949;
    font-size: 0.8rem;
    margin-top: -0.5rem;
}

.remember-forgot {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0.5rem 0;
}

.forgot-link {
    color: #409EFF;
    text-decoration: none;
    font-size: 0.9rem;
    transition: color 0.3s ease;
}

.forgot-link:hover {
    color: #66b1ff;
}

.login-btn {
    width: 100%;
    padding: 0.8rem;
    font-size: 1.1rem;
    border-radius: 8px;
    margin: 1rem 0;
    transition: all 0.3s ease;
}

.login-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.error-message {
    background: #fff2f0;
    border: 1px solid #ffccc7;
    padding: 0.8rem;
    border-radius: 4px;
    color: #ff4d4f;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.register-link {
    text-align: center;
    margin-top: 1rem;
    color: #666;
}

.register-link a {
    color: #409EFF;
    text-decoration: none;
    font-weight: 500;
    margin-left: 0.5rem;
    transition: color 0.3s ease;
}

.register-link a:hover {
    color: #66b1ff;
    text-decoration: underline;
}

@media (max-width: 480px) {
    .login-content {
        padding: 1.5rem;
    }

    .login-title {
        font-size: 1.8rem;
    }

    .login-subtitle {
        font-size: 0.9rem;
    }
}
</style>