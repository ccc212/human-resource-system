import request from '../request';

export const employeeAPI = {
  // 获取员工列表
  getList: (params) => {
    return request.get('/employees', { params });
  },
  
  // 获取员工详情
  getDetail: (id) => {
    return request.get(`/employees/${id}`);
  },
  
  // 添加员工
  create: (data) => {
    return request.post('/employees', data);
  },
  
  // 更新员工信息
  update: (id, data) => {
    return request.put(`/employees/${id}`, data);
  },
  
  // 删除员工
  delete: (id) => {
    return request.delete(`/employees/${id}`);
  }
}; 