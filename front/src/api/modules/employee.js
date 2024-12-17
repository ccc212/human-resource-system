import request from '../request';

export const employeeAPI = {
  fetchEmployees: (params) => request.get('/hr-record/search', { params }),
  getEmployeeById: (id) => request.get(`/hr-record/${id}`),
  deleteEmployee: (id) => request.delete(`/hr-record/${id}`),
  addEmployee: (data) => request.post('/hr-record/log', data),
  reviewEmployee: (id) => request.put('/hr-record/update', { recordId: id, status: '1' }),
  updateEmployee: (data) => request.put('/hr-record/update', data),
}; 