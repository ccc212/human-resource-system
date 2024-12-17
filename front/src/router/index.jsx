import { useRoutes } from 'react-router-dom';
import routes from './routes';

// 路由渲染组件
export const RouterElement = () => {
  const element = useRoutes(routes);
  return element;
};