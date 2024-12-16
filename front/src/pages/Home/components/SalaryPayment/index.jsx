import { Table, Form, DatePicker, Button, Select, Card, Row, Col, Space, Tag } from 'antd';
import { SearchOutlined, ReloadOutlined } from '@ant-design/icons';

const { Option } = Select;
const { RangePicker } = DatePicker;

const SalaryPayment = () => {
  const [form] = Form.useForm();

  const columns = [
    {
      title: '发放编号',
      dataIndex: 'id',
      key: 'id',
      width: 120,
    },
    {
      title: '员工姓名',
      dataIndex: 'employeeName',
      key: 'employeeName',
      width: 100,
    },
    {
      title: '部门',
      dataIndex: 'department',
      key: 'department',
      width: 120,
    },
    {
      title: '应发工资',
      dataIndex: 'grossSalary',
      key: 'grossSalary',
      width: 120,
    },
    {
      title: '实发工资',
      dataIndex: 'netSalary',
      key: 'netSalary',
      width: 120,
    },
    {
      title: '发放状态',
      dataIndex: 'status',
      key: 'status',
      width: 100,
      render: (status) => (
        <Tag color={status === 'paid' ? 'green' : 'gold'}>
          {status === 'paid' ? '已发放' : '待发放'}
        </Tag>
      ),
    },
    {
      title: '发放日期',
      dataIndex: 'paymentDate',
      key: 'paymentDate',
      width: 120,
    }
  ];

  return (
    <Card>
      <Form form={form} layout="vertical">
        <Row gutter={16}>
          <Col span={8}>
            <Form.Item name="dateRange" label="发放日期">
              <RangePicker style={{ width: '100%' }} />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="department" label="部门">
              <Select placeholder="请选择部门">
                <Option value="rd">研发部</Option>
                <Option value="sales">销售部</Option>
                <Option value="hr">人力资源部</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={8} style={{ textAlign: 'right', marginTop: 30 }}>
            <Space>
              <Button icon={<ReloadOutlined />}>重置</Button>
              <Button type="primary" icon={<SearchOutlined />}>搜索</Button>
            </Space>
          </Col>
        </Row>
      </Form>
      <Table 
        columns={columns}
        rowKey="id"
        scroll={{ x: 1000 }}
      />
    </Card>
  );
};

export default SalaryPayment; 