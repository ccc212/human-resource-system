import { Form, Input, Button, Card, Space, Divider } from 'antd';
import { UserOutlined, LockOutlined, WechatOutlined, QqOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import './style.css';

const Login = () => {
  const navigate = useNavigate();

  const handleLogin = () => {
    navigate('/home');
  };

  return (
    <div className="login-container">
      <Card className="login-card" bordered={false}>
        <h1>登录到我的应用</h1>
        
        <Form layout="vertical">
          <Form.Item name="username">
            <Input 
              prefix={<UserOutlined />} 
              placeholder="请输入用户名"
              size="large"
            />
          </Form.Item>
          
          <Form.Item name="password">
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="请输入密码"
              size="large"
            />
          </Form.Item>
          
          <Form.Item>
            <Button type="primary" block size="large" onClick={handleLogin}>
              登录
            </Button>
          </Form.Item>
          
          <Divider>或</Divider>
          
          <Space direction="horizontal" size="middle" style={{ width: '100%', justifyContent: 'center' }}>
            <Button shape="circle" icon={<WechatOutlined />} size="large" className="social-button" />
            <Button shape="circle" icon={<QqOutlined />} size="large" className="social-button" />
          </Space>
        </Form>
      </Card>
    </div>
  );
};

export default Login; 