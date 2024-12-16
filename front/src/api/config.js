// API配置文件
const config = {
  // 开发环境API地址
  development: {
    API_BASE_URL: '/api',
  },
  // 生产环境API地址
  production: {
    API_BASE_URL: 'https://api.yourproduction.com',
  },
  // 测试环境API地址
  test: {
    API_BASE_URL: 'https://api.testing.com',
  }
};

// 根据当前环境获取配置
const env = process.env.NODE_ENV || 'development';
const currentConfig = config[env];

export const API_BASE_URL = currentConfig.API_BASE_URL;

// 统一的请求超时时间
export const TIMEOUT = 5000;

// API版本
export const API_VERSION = 'v1';

// 请求头配置
export const HEADERS = {
  'Content-Type': 'application/json',
  'Accept': 'application/json',
}; 