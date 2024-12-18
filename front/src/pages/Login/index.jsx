import { Form, Input, Button, Card, Space, Divider, message } from 'antd';
import { UserOutlined, LockOutlined, WechatOutlined, QqOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './style.css';

const Login = () => {
  const navigate = useNavigate();

  const handleLogin = async (values) => {
    try {
      const response = await axios.post('/user/login', values);
      if (response.data.code === 200) {
        const { token, userId } = response.data.data;
        localStorage.setItem('token', token);
        localStorage.setItem('userId', userId);
        localStorage.setItem('username', values.username);
        message.success('登录成功');
        navigate('/home');
      } else {
        message.error(response.data.msg || '登录失败');
      }
    } catch (error) {
      message.error('登录失败，请检查您的用户名和密码');
    }
  };

  const handleRegister = () => {
    navigate('/register');
  };

  return (
    <div className="login-container">
      <Card className="login-card" bordered={false}>
        <h1>登录到我的应用</h1>
        
        <Form layout="vertical" onFinish={handleLogin}>
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input 
              prefix={<UserOutlined />} 
              placeholder="请输入用户名"
              size="large"
            />
          </Form.Item>
          
          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="请输入密码"
              size="large"
            />
          </Form.Item>
          
          <Form.Item>
            <Button type="primary" block size="large" htmlType="submit">
              登录
            </Button>
          </Form.Item>

          <Form.Item>
            <Button type="default" block size="large" onClick={handleRegister}>
              注册
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