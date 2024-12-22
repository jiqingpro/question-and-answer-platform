<template>
    <div class="register-container">
        <div class="register-box">
            <div class="register-content">
                <h2 class="register-title">创建账号</h2>
                <p class="register-subtitle">加入我们的社区</p>

                <form @submit.prevent="register" class="register-form">
                    <!-- 用户名输入框 -->
                    <el-input
                        v-model.trim="formData.username"
                        placeholder="请输入用户名"
                        prefix-icon="el-icon-user"
                        class="input-field"
                        :class="{ 'error-input': errors.username }"
                        clearable
                        @input="clearError('username')"
                    />
                    <span v-if="errors.username" class="error-text">{{ errors.username }}</span>

                    <!-- 邮箱输入框组 -->
                    <div class="email-input-group">
                        <el-input
                            v-model.trim="emailPrefix"
                            placeholder="请输入邮箱"
                            prefix-icon="el-icon-message"
                            class="email-prefix-input"
                            :class="{ 'error-input': errors.email }"
                            clearable
                            @input="handleEmailInput"
                        />
                        <span class="email-separator">@</span>
                        <el-select 
                            v-model="emailSuffix"
                            class="email-suffix-select"
                            :class="{ 'error-input': errors.email }"
                            filterable
                            allow-create
                            default-first-option
                            placeholder="选择或输入"
                            @input="handleEmailInput"
                        >
                            <el-option
                                v-for="suffix in emailSuffixes"
                                :key="suffix"
                                :label="suffix"
                                :value="suffix"
                            />
                        </el-select>
                    </div>
                    <span v-if="errors.email" class="error-text">{{ errors.email }}</span>

                    <!-- 密码输入框 -->
                    <el-input
                        v-model.trim="formData.password"
                        placeholder="请输入密码"
                        type="password"
                        prefix-icon="el-icon-lock"
                        class="input-field"
                        :class="{ 'error-input': errors.password }"
                        clearable
                        @input="clearError('password')"
                    />
                    <span v-if="errors.password" class="error-text">{{ errors.password }}</span>

                    <!-- 确认密码输入框 -->
                    <el-input
                        v-model.trim="formData.confirmPassword"
                        placeholder="请再次输入密码"
                        type="password"
                        prefix-icon="el-icon-lock"
                        class="input-field"
                        :class="{ 'error-input': errors.confirmPassword }"
                        clearable
                        @input="clearError('confirmPassword')"
                    />
                    <span v-if="errors.confirmPassword" class="error-text">{{ errors.confirmPassword }}</span>

                    <!-- 注册按钮 -->
                    <el-button
                        type="primary"
                        class="register-btn"
                        :loading="loading"
                        @click="register"
                    >
                        {{ loading ? '注册中...' : '立即注册' }}
                    </el-button>

                    <!-- 错误信息 -->
                    <div v-if="errorMessage" class="error-message">
                        <i class="el-icon-warning"></i>
                        {{ errorMessage }}
                    </div>

                    <!-- 返回登录链接 -->
                    <div class="login-link">
                        已有账号？
                        <a href="#" @click.prevent="goToLogin">立即登录</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { post } from '@/utils/request';

export default {
    name: "RegisterPage",
    data() {
        return {
            formData: {
                username: "",
                email: "",
                password: "",
                confirmPassword: "",
            },
            emailPrefix: "",
            emailSuffix: "",
            emailSuffixes: [
                'qq.com',
                '163.com',
                'gmail.com',
                'outlook.com',
                'hotmail.com',
                'yahoo.com',
                'foxmail.com'
            ],
            errors: {
                username: '',
                email: '',
                password: '',
                confirmPassword: ''
            },
            errorMessage: "",
            loading: false,
        };
    },
    methods: {
        clearError(field) {
            this.errors[field] = '';
            this.errorMessage = '';
        },

        handleEmailInput() {
            this.formData.email = this.emailPrefix && this.emailSuffix 
                ? `${this.emailPrefix}@${this.emailSuffix}`
                : '';
            this.clearError('email');
        },

        validateForm() {
            let isValid = true;
            this.errors = {
                username: '',
                email: '',
                password: '',
                confirmPassword: ''
            };

            // 用户名验证
            if (!this.formData.username) {
                this.errors.username = '请输入用户名';
                isValid = false;
            } else if (this.formData.username.length < 3) {
                this.errors.username = '用户名长度不能少于3个字符';
                isValid = false;
            }

            // 邮箱验证
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!this.formData.email) {
                this.errors.email = '请输入邮箱地址';
                isValid = false;
            } else if (!emailRegex.test(this.formData.email)) {
                this.errors.email = '请输入有效的邮箱地址';
                isValid = false;
            }

            // 密码验证
            if (!this.formData.password) {
                this.errors.password = '请输入密码';
                isValid = false;
            }

            // 确认密码验证
            if (!this.formData.confirmPassword) {
                this.errors.confirmPassword = '请确认密码';
                isValid = false;
            } else if (this.formData.password !== this.formData.confirmPassword) {
                this.errors.confirmPassword = '两次输入的密码不一致';
                isValid = false;
            }

            return isValid;
        },

        async register() {
            if (!this.validateForm()) return;

            this.loading = true;
            this.errorMessage = '';

            try {
                const response = await post("/api/users/register", {
                    username: this.formData.username,
                    email: this.formData.email,
                    password: this.formData.password,
                });

                const { code, message } = response;

                if (code === 200) {
                    this.$message({
                        message: '注册成功',
                        type: 'success'
                    });
                    setTimeout(() => this.$router.push({ name: "LoginPage" }), 1500);
                } else {
                    this.errorMessage = message || "注册失败，请重试";
                }
            } catch (error) {
                console.error("Register error:", error);
                this.errorMessage = error.response?.data?.message || "网络错误，请稍后重试";
            } finally {
                this.loading = false;
            }
        },

        goToLogin() {
            this.$router.push({ name: "LoginPage" });
        },
    },
};
</script>

<style scoped>
.register-container {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, rgba(0, 242, 254, 0.1), rgba(0, 0, 0, 0.1)),
                url('@/assets/background/preview.jpg') no-repeat center center;
    background-size: cover;
    position: relative;
}

.register-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(5px);
}

.register-box {
    width: 90%;
    max-width: 420px;
    position: relative;
    z-index: 1;
}

.register-content {
    background: rgba(255, 255, 255, 0.95);
    padding: 2.5rem;
    border-radius: 15px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.register-title {
    font-size: 2.2rem;
    font-weight: 600;
    color: #333;
    margin-bottom: 0.5rem;
}

.register-subtitle {
    color: #666;
    margin-bottom: 2rem;
    font-size: 1rem;
}

.register-form {
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

.email-input-group {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.email-prefix-input {
    flex: 1;
}

.email-separator {
    color: #666;
    font-size: 1.2rem;
    margin: 0 0.3rem;
}

.email-suffix-select {
    width: 140px;
}

.error-input {
    border-color: #ff4949;
}

.error-text {
    color: #ff4949;
    font-size: 0.8rem;
    margin-top: -0.5rem;
}

.register-btn {
    width: 100%;
    padding: 0.8rem;
    font-size: 1.1rem;
    border-radius: 8px;
    margin: 1rem 0;
    transition: all 0.3s ease;
}

.register-btn:hover {
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

.login-link {
    text-align: center;
    margin-top: 1rem;
    color: #666;
}

.login-link a {
    color: #409EFF;
    text-decoration: none;
    font-weight: 500;
    margin-left: 0.5rem;
    transition: color 0.3s ease;
}

.login-link a:hover {
    color: #66b1ff;
    text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 480px) {
    .register-content {
        padding: 1.5rem;
    }

    .register-title {
        font-size: 1.8rem;
    }

    .register-subtitle {
        font-size: 0.9rem;
    }

    .email-input-group {
        flex-direction: column;
    }

    .email-separator {
        display: none;
    }

    .email-suffix-select {
        width: 100%;
    }
}
</style>
