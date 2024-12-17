import request from '../request';

export const organizationAPI = {
  list: (params) => request.get('/organization/list', { params }),
};

export const positionCategorieAPI = {
  list: () => request.get('/position-categorie/list'),
};

export const positionAPI = {
  list: (params) => request.get('/position/list', { params }),
};

export const titleAPI = {
  list: (params) => request.get('/title/list', { params }),
};

export const educationAPI = {
  list: (params) => request.get('/education/list', { params }),
};

export const ethnicitiesAPI = {
  list: (params) => request.get('/ethnicitie/list', { params }),
};
