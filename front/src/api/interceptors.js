import axios from 'axios';
import { message } from 'antd';

// 创建axios实例
const instance = axios.create({
  baseURL: process.env.REACT_APP_API_URL, // 从环境变量获取基础URL
  timeout: 10000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
instance.interceptors.request.use(
  config => {
    // 可以在这里添加token等认证信息
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['token'] = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  response => {
    // 可以统一处理响应数据
    return response.data;
  },
  error => {
    // 统一处理错误
    if (error.response) {
      switch (error.response.status) {
        case 401:
          message.error('未授权，请重新登录');
          // 可以在这里处理登出逻辑
          break;
        case 403:
          message.error('拒绝访问');
          break;
        case 404:
          message.error('请求错误，未找到该资源');
          break;
        case 500:
          message.error('服务器错误');
          break;
        default:
          message.error('网络错误');
      }
    } else {
      message.error('网络错误，请检查您的网络连接');
    }
    return Promise.reject(error);
  }
);

export default instance; 