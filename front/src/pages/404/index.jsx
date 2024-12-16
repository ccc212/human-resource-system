import { Button, Result, Space } from 'antd';
import { useNavigate } from 'react-router-dom';

const NotFound = () => {
  const navigate = useNavigate();

  return (
    <div style={{
      width: '100%',
      height: '100vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      background: '#f5f5f5'
    }}>
      <Result
        status="404"
        title="404"
        subTitle="抱歉，该功能正在开发中..."
        extra={
          <Space size="middle">
            <Button size="large" onClick={() => navigate(-1)}>
              返回上一页
            </Button>
            <Button size="large" type="primary" onClick={() => navigate('/')}>
              返回首页
            </Button>
          </Space>
        }
        style={{
          padding: '48px',
          background: '#fff',
          borderRadius: '8px',
          boxShadow: '0 2px 8px rgba(0,0,0,0.15)'
        }}
      />
    </div>
  );
};

export default NotFound; 