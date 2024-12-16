import { Table, Form, Input, Button, Select, Card, Row, Col, Space, InputNumber, Modal, DatePicker } from 'antd';
import { SearchOutlined, ReloadOutlined, PlusOutlined } from '@ant-design/icons';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const { Option } = Select;

const SalaryStandard = () => {
  const [form] = Form.useForm();
  const [createForm] = Form.useForm();
  const [isModalVisible, setIsModalVisible] = useState(false);
  const navigate = useNavigate();
  
const mockData = [
    {
      id: 'SS001',
      positionType: '技术类',
      basicSalary: 8000,
      positionSalary: 3000,
      performanceSalary: 2000,
      insuranceBase: 10000,
      fundBase: 10000,
      creator: '张三',
      registrar: '李四',
      registerTime: '2024-03-20 14:30:00',
 },
    {
      id: 'SS002',
      name: '中级工程师薪资标准',
      positionType: '技术类',
      basicSalary: 12000,
      positionSalary: 4000,
      performanceSalary: 3000,
      insuranceBase: 15000,
      fundBase: 15000,
      creator: '张三',
      registrar: '李四',
      registerTime: '2024-03-20 14:30:00',
    },
    {
      id: 'SS003',
      name: '部门经理薪资标准',
      positionType: '管理类',
      basicSalary: 15000,
      positionSalary: 6000,
      performanceSalary: 5000,
      insuranceBase: 20000,
      fundBase: 20000,
      creator: '张三',
      registrar: '李四',
      registerTime: '2024-03-20 14:30:00',
    },
    {
      id: 'SS004',
      name: '运营专员薪资标准',
      positionType: '运营类',
      basicSalary: 7000,
      positionSalary: 2000,
      performanceSalary: 2000,
      insuranceBase: 8000,
      fundBase: 8000,
      creator: '张三',
      registrar: '李四',
      registerTime: '2024-03-20 14:30:00',
    },
  ];
  const columns = [
    {
      title: '标准编号',
      dataIndex: 'id',
      key: 'id',
      width: 120,
      render: (text) => (
        <a onClick={() => navigate(`/home/salary-standard/detail/${text}`)}>{text}</a>
      ),
    },
    {
      title: '标准名称',
      dataIndex: 'name',
      key: 'name',
      width: 150,
    },
    {
      title: '职位类别',
      dataIndex: 'positionType',
      key: 'positionType',
      width: 120,
    },
    {
      title: '基本工资',
      dataIndex: 'basicSalary',
      key: 'basicSalary',
      width: 120,
    },
    {
      title: '岗位工资',
      dataIndex: 'positionSalary',
      key: 'positionSalary',
      width: 120,
    },
    {
      title: '绩效工资',
      dataIndex: 'performanceSalary',
      key: 'performanceSalary',
      width: 120,
    },
    {
      title: '社保基数',
      dataIndex: 'insuranceBase',
      key: 'insuranceBase',
      width: 120,
    },
    {
      title: '公积金基数',
      dataIndex: 'fundBase',
      key: 'fundBase',
      width: 120,
    },
    {
      title: '制定人',
      dataIndex: 'creator',
      key: 'creator',
      width: 120,
    },
    {
      title: '登记人',
      dataIndex: 'registrar',
      key: 'registrar',
      width: 120,
    },
    {
      title: '登记时间',
      dataIndex: 'registerTime',
      key: 'registerTime',
      width: 150,
    },
  ];

  const handleCreate = async () => {
    try {
      const values = await createForm.validateFields();
      console.log('新建薪资标准:', values);
      // 这里添加创建逻辑
      createForm.resetFields();
      setIsModalVisible(false);
    } catch (error) {
      console.error('验证失败:', error);
    }
  };

  const calculateInsuranceAndFund = (basicSalary) => {
    const pensionInsurance = (basicSalary * 0.08).toFixed(2);
    const medicalInsurance = (basicSalary * 0.02 + 3).toFixed(2);
    const unemploymentInsurance = (basicSalary * 0.005).toFixed(2);
    const housingFund = (basicSalary * 0.08).toFixed(2);
    return { pensionInsurance, medicalInsurance, unemploymentInsurance, housingFund };
  };
  return (
    <Card>
      <Form form={form} layout="vertical">
        <Row gutter={16}>
          {/* ... existing search form items ... */}
          <Col span={12} style={{ textAlign: 'right', marginTop: 30 }}>
            <Space>
              <Button icon={<PlusOutlined />} type="primary" onClick={() => setIsModalVisible(true)}>
                新建标准
              </Button>
              <Button icon={<ReloadOutlined />}>重置</Button>
              <Button type="primary" icon={<SearchOutlined />}>搜索</Button>
            </Space>
          </Col>
        </Row>
      </Form>

      <Table 
        columns={columns}
        dataSource={mockData}
        rowKey="id"
        scroll={{ x: 1200 }}
      />

      <Modal
        title="新建薪资标准"
        open={isModalVisible}
        onOk={handleCreate}
        onCancel={() => {
          createForm.resetFields();
          setIsModalVisible(false);
        }}
        width={800}
      >
        <Form
          form={createForm}
          layout="vertical"
          onValuesChange={(changedValues, allValues) => {
            if (changedValues.basicSalary !== undefined) {
              const { pensionInsurance, medicalInsurance, unemploymentInsurance, housingFund } = calculateInsuranceAndFund(changedValues.basicSalary);
              createForm.setFieldsValue({
                pensionInsurance,
                medicalInsurance,
                unemploymentInsurance,
                housingFund,
              });
            }
          }}
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="name"
                label="标准名称"
                rules={[{ required: true, message: '请输入标准名称' }]}
              >
                <Input placeholder="请输入标准名称" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="positionType"
                label="职位类别"
                rules={[{ required: true, message: '请选择职位类别' }]}
              >
                <Select placeholder="请选择职位类别">
                  <Option value="技术类">技术类</Option>
                  <Option value="管理类">管理类</Option>
                  <Option value="运营类">运营类</Option>
                </Select>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="basicSalary"
                label="基本工资"
                rules={[{ required: true, message: '请输入基本工资' }]}
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="请输入基本工资"
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="positionSalary"
                label="岗位工资"
                rules={[{ required: true, message: '请输入岗位工资' }]}
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="请输入岗位工资"
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="performanceSalary"
                label="绩效工资"
                rules={[{ required: true, message: '请输入绩效工资' }]}
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="请输入绩效工资"
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="pensionInsurance"
                label="养老保险"
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="自动计算"
                  disabled
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="medicalInsurance"
                label="医疗保险"
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="自动计算"
                  disabled
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="unemploymentInsurance"
                label="失业保险"
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="自动计算"
                  disabled
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="housingFund"
                label="住房公积金"
              >
                <InputNumber
                  style={{ width: '100%' }}
                  min={0}
                  placeholder="自动计算"
                  disabled
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="creator"
                label="制定人"
                rules={[{ required: true, message: '请输入制定人' }]}
              >
                <Input placeholder="请输入制定人" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="registrar"
                label="登记人"
                rules={[{ required: true, message: '请输入登记人' }]}
              >
                <Input placeholder="请输入登记人" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="registerTime"
                label="登记时间"
                rules={[{ required: true, message: '请选择登记时间' }]}
              >
                <DatePicker 
                  style={{ width: '100%' }}
                  showTime
                  placeholder="请选择登记时间"
                />
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </Modal>
    </Card>
  );
};

export default SalaryStandard; 