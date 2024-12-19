import axios from 'axios';
import { baseUrl } from "@/config/config";
import AppHttpCodeEnum from '@/utils/AppHttpCodeEnum';

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

        // 如果 code 在定义的错误码中，直接返回错误信息而不抛出异常
        if (Object.values(AppHttpCodeEnum).some(item => item.code === res.code)) {
            console.log(`API Error: ${res.msg}`); // 打印错误信息
            return res; // 返回错误信息
        }

        // 如果是正常响应，code === 200
        if (res.code === AppHttpCodeEnum.SUCCESS.code) {
            return res;
        }

        // 对于未定义的 code 或错误的 code，抛出异常
        const errorMessage = Object.values(AppHttpCodeEnum).find(item => item.code === res.code)?.msg || '未知错误';
        console.error(errorMessage);
        return Promise.reject(new Error(errorMessage));
    },
    (error) => {
        // 响应错误处理
        if (axios.isCancel(error)) {
            console.warn('Request canceled', error.message);
        } else {
            // 网络或其他非业务错误
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
