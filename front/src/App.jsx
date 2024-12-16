import { BrowserRouter } from 'react-router-dom';
import { Suspense } from 'react';
import { Spin } from 'antd';
import { RouterElement } from './router';

const App = () => {
  return (
    <BrowserRouter>
      <Suspense 
        fallback={
          <div style={{ 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'center', 
            height: '100vh' 
          }}>
            <Spin size="large" />
          </div>
        }
      >
        <RouterElement />
      </Suspense>
    </BrowserRouter>
  );
};

export default App; 