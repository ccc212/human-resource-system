import request from '../request';

// React项目中使用 REACT_APP_ 前缀
const API_PATH = process.env.REACT_APP_API_URL || '/api';

// 薪资项目相关接口
export const salaryItemsAPI = {
  // 获取所有薪资项目
  getItems: () => {
    return request.get(`${API_PATH}/ssitems`);
  },
  
  // 根据ID获取薪资项目
  getItemById: (id) => {
    return request.get(`${API_PATH}/ssitems/${id}`);
  },
  
  // 添加薪资项目
  addItem: (data) => {
    return request.post(`${API_PATH}/ssitems`, data);
  },
  
  // 更新薪资项目
  updateItem: (id, data) => {
    return request.put(`${API_PATH}/ssitems/${id}`, data);
  },
  
  // 删除薪资项目
  deleteItem: (id) => {
    return request.delete(`${API_PATH}/ssitems/${id}`);
  }
};

// 薪资标准相关接口
export const salaryStandardAPI = {
  getList: () => {
    return request.get(`${API_PATH}/salary-standards`);
  },
  
  getDetail: (id) => {
    return request.get(`${API_PATH}/salary-standards/${id}`);
  },
  
  create: (data) => {
    return request.post(`${API_PATH}/salary-standards`, data);
  },
  
  update: (id, data) => {
    return request.put(`${API_PATH}/salary-standards/${id}`, data);
  },
  
  delete: (id) => {
    return request.delete(`${API_PATH}/salary-standards/${id}`);
  }
}; 