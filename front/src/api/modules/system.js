import request from '../request';

const ROLE_URL = '/role';
const ORGANIZATION_URL = '/organization';
const POSITION_URL = '/position';
const POSITION_CATEGORIE_URL = '/position-categorie';
const TITLE_URL = '/title';
const EDUCATION_URL = '/education';
const ETHNICITY_URL = '/ethnicitie';

export const roleAPI = {
  add: (role) => request.post(ROLE_URL, role),
  delete: (id) => request.delete(`${ROLE_URL}/${id}`),
  update: (role) => request.put(ROLE_URL, role),
  list: (params) => request.get(ROLE_URL + '/list', { params }),
};

export const organizationAPI = {
  add: (organization) => request.post(ORGANIZATION_URL, organization),
  delete: (id) => request.delete(`${ORGANIZATION_URL}/${id}`),
  update: (organization) => request.put(ORGANIZATION_URL, organization),
  list: (params) => request.get(ORGANIZATION_URL + '/list', { params }),
};

export const positionCategorieAPI = {
  add: (positionCategorie) => request.post(POSITION_CATEGORIE_URL, positionCategorie),
  delete: (id) => request.delete(`${POSITION_CATEGORIE_URL}/${id}`),
  update: (positionCategorie) => request.put(POSITION_CATEGORIE_URL, positionCategorie),
  list: (params) => request.get(POSITION_CATEGORIE_URL + '/list', { params }),
};

export const positionAPI = {
  add: (position) => request.post(POSITION_URL, position),
  delete: (id) => request.delete(`${POSITION_URL}/${id}`),
  update: (position) => request.put(POSITION_URL, position),
  list: (params) => request.get(POSITION_URL + '/list', { params }),
};

export const titleAPI = {
  add: (title) => request.post(TITLE_URL, title),
  delete: (id) => request.delete(`${TITLE_URL}/${id}`),
  update: (title) => request.put(TITLE_URL, title),
  list: (params) => request.get(TITLE_URL + '/list', { params }),
};

export const educationAPI = {
  add: (education) => request.post(EDUCATION_URL, education),
  delete: (id) => request.delete(`${EDUCATION_URL}/${id}`),
  update: (education) => request.put(EDUCATION_URL, education),
  list: (params) => request.get(EDUCATION_URL + '/list', { params }),
};

export const ethnicitiesAPI = {
  add: (ethnicities) => request.post(ETHNICITY_URL, ethnicities),
  delete: (id) => request.delete(`${ETHNICITY_URL}/${id}`),
  update: (ethnicities) => request.put(ETHNICITY_URL, ethnicities),
  list: (params) => request.get(ETHNICITY_URL + '/list', { params }),
};
