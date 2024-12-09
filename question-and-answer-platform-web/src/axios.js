import axios from 'axios';
import router from './router'; // 引入你的路由配置

// 创建Axios实例
const instance = axios.create({
    baseURL: 'http://localhost:11923/api', // 替换为你的后端API地址
    timeout: 5000, // 超时时间
});

// 请求拦截器
instance.interceptors.request.use(
    config => {
        const token = localStorage.getItem('authToken');
        if (token) {
            // 如果有token，将其添加到请求头
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// 响应拦截器
instance.interceptors.response.use(
    response => response, // 如果请求成功直接返回
    error => {
        if (error.response && error.response.status === 401) {
            // 如果状态码是401（未授权），跳转到登录页面
            localStorage.removeItem('authToken'); // 移除无效的token
            router.push({ name: 'login' }); // 跳转到登录页
        }
        return Promise.reject(error);
    }
);

export default instance;
