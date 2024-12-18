import request from '../interceptors';

// 薪酬标准相关接口
export const salaryStandardAPI = {
  // 获取所有薪酬标准
  getAllStandards: () => {
    return request.get('/salarystrandary/getAll');
  },
  
  // 获取固定薪酬项目
  getFixedItems: () => {
    return request.get('/salarystrandary/fixed-items');
  },
  getUnFixedItems: () => {
    return request.get('/salarystrandary/unfixed-items');
  },
  // 新增薪酬标准
  addSalaryStandard: (data) => {
    return request.post('/salarystrandary/add', data);
  },

  // 更新薪酬标准（包含所有信息）
  updateStandard: (standardId, data) => {
    return request.put(`/salarystrandary/update/${standardId}`, data);
  },

  // 更新薪酬标准中的项目信息
  updateStandardItem: (standardId, itemId, data) => {
    return request.put(`/salarystrandary/${standardId}/items/${itemId}`, data);
  }
};

// 薪资项目相关接口
export const salaryItemsAPI = {
  // 获取所有薪资项目
  getItems: () => {
    return request.get('/ssitems');
  },
  
  // 添加薪资项目
  addItem: (data) => {
    return request.post('/ssitems', data);
  },
  
  // 更新薪资项目
  updateItem: (id, data) => {
    return request.put(`/ssitems/${id}`, data);
  },
  
  // 删除薪资项目
  deleteItem: (id) => {
    return request.delete(`/ssitems/${id}`);
  }
};

// 薪酬发放相关接口
export const salaryPaymentAPI = {
  // 获取薪酬发放列表
  getPaymentList: (params) => {
    return request.get('/salarypayment/search', { params });
  }
};