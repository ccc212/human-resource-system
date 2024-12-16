import { Form, Input, Button, Select, DatePicker, Space } from 'antd';

const EmployeeForm = () => {
  const [form] = Form.useForm();

  const onFinish = (values) => {
    console.log('Success:', values);
  };

  return (
    <Form
      form={form}
      layout="vertical"
      initialValues={{
        name: '',
        gender: 'male',
        phone: '',
        email: '',
        department: 'none',
        position: 'none',
      }}
      onFinish={onFinish}
    >
      <Form.Item
        name="name"
        label="姓名"
        rules={[{ required: true, message: '请输入姓名' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="gender"
        label="性别"
        rules={[{ required: true, message: '请选择性别' }]}
      >
        <Select>
          <Select.Option value="male">男</Select.Option>
          <Select.Option value="female">女</Select.Option>
        </Select>
      </Form.Item>

      <Form.Item
        name="phone"
        label="手机号"
        rules={[{ required: true, message: '请输入手机号' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="email"
        label="邮箱"
        rules={[
          { required: true, message: '请输入邮箱' },
          { type: 'email', message: '请输入有效的邮箱地址' }
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="department"
        label="部门"
        rules={[{ required: true, message: '请选择部门' }]}
      >
        <Select>
          <Select.Option value="none">无</Select.Option>
          <Select.Option value="tech">技术部</Select.Option>
          <Select.Option value="hr">人力资源部</Select.Option>
          <Select.Option value="finance">财务部</Select.Option>
        </Select>
      </Form.Item>

      <Form.Item
        name="position"
        label="职位"
        rules={[{ required: true, message: '请选择职位' }]}
      >
        <Select>
          <Select.Option value="none">无</Select.Option>
          <Select.Option value="developer">开发工程师</Select.Option>
          <Select.Option value="designer">设计师</Select.Option>
          <Select.Option value="manager">经理</Select.Option>
        </Select>
      </Form.Item>

      <Form.Item>
        <Space>
          <Button type="primary" htmlType="submit">
            提交
          </Button>
          <Button onClick={() => form.resetFields()}>
            重置
          </Button>
        </Space>
      </Form.Item>
    </Form>
  );
};

export default EmployeeForm; 