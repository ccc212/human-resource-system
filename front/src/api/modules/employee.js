import request from '../request';

export const employeeAPI = {
  fetchEmployees: (params) => {
    return request({
      url: '/hr-record/search',
      method: 'get',
      params,
    });
  },
  fetchEmployees: (params) => request.get('/hr-record/search', { params }),
  deleteEmployee: (id) => request.delete(`/hr-record/${id}`),
  addEmployee: (data) => request.post('/hr-record/log', data),
}; 