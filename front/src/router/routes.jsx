import { lazy } from 'react';
import { Navigate } from 'react-router-dom';

// 使用懒加载导入页面组件
const Login = lazy(() => import('../pages/Login'));
const Home = lazy(() => import('../pages/Home'));
const NotFound = lazy(() => import('../pages/404'));


const routes = [
  {
    path: '/login',
    element: <Login />
  },
  {
    path: '/home',
    element: <Home />,
    children: [
      {

      }
    ]
  },
  {
    path: '/',
    element: <Navigate to="/home" />
  },
  {
    path: '*',
    element: <NotFound />
  }
];

export default routes; 