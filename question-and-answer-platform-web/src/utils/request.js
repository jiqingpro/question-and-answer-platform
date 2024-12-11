import axios from 'axios';
import {baseUrl} from "@/config/config";
// 创建一个 axios 实例
const service = axios.create({
    baseURL: baseUrl || '/', // 基础 URL，根据环境变量配置
    timeout: 5000, // 请求超时时间
});

// 用于取消重复请求
const pendingRequests = new Map();

const addPendingRequest = (config) => {
    const requestKey = `${config.method} ${config.url} ${JSON.stringify(config.params)} ${JSON.stringify(config.data)}`;
    if (!pendingRequests.has(requestKey)) {
        const cancelToken = axios.CancelToken.source();
        config.cancelToken = cancelToken.token;
        pendingRequests.set(requestKey, cancelToken);
    }
};

const removePendingRequest = (config) => {
    const requestKey = `${config.method} ${config.url} ${JSON.stringify(config.params)} ${JSON.stringify(config.data)}`;
    if (pendingRequests.has(requestKey)) {
        const cancelToken = pendingRequests.get(requestKey);
        cancelToken.cancel(`Canceled duplicate request: ${requestKey}`);
        pendingRequests.delete(requestKey);
    }
};

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        // 在发送请求之前执行，例如添加 token
        if (localStorage.getItem('token')) {
            config.headers['Authorization'] = `Bearer ${localStorage.getItem('token')}`;
        }

        // 处理重复请求
        removePendingRequest(config); // 取消重复请求
        addPendingRequest(config); // 添加当前请求

        return config;
    },
    (error) => {
        // 请求错误处理
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        // 在响应后，移除请求
        removePendingRequest(response.config);

        // 响应数据处理
        const res = response.data;
        if (res.code !== 200) {
            // 根据后端返回的 code 判断请求是否成功
            console.error(res.message || 'error');
            return Promise.reject(new Error(res.message || 'error'));
        }
        return res;
    },
    (error) => {
        // 响应错误处理
        if (axios.isCancel(error)) {
            console.warn('Request canceled', error.message);
        } else {
            console.error(error.message || 'Response Error');
        }
        return Promise.reject(error);
    }
);

// 封装 GET 请求
export const get = (url, params = {}, config = {}) => {
    return service({
        method: 'get',
        url,
        params,
        ...config,
    });
};

// 封装 POST 请求
export const post = (url, data = {}, config = {}) => {
    return service({
        method: 'post',
        url,
        data,
        ...config,
    });
};

// 封装其他请求方法 (PUT, DELETE, etc.)
export const put = (url, data = {}, config = {}) => {
    return service({
        method: 'put',
        url,
        data,
        ...config,
    });
};

export const del = (url, params = {}, config = {}) => {
    return service({
        method: 'delete',
        url,
        params,
        ...config,
    });
};

// 导出 axios 实例
export default service;
