import { useState, useEffect, useCallback } from 'react';
import { 
  Table, 
  Card, 
  Input, 
  Button, 
  Space, 
  Form, 
  Select, 
  Row, 
  Col,
  Tag,
  Tooltip,
  Modal,
  message 
} from 'antd';
import { 
  SearchOutlined, 
  EditOutlined, 
  DeleteOutlined,
  EyeOutlined,
  ReloadOutlined,
  ExportOutlined 
} from '@ant-design/icons';

const { Option } = Select;

const EmployeeList = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });
  const [searchForm] = Form.useForm();

  // 先定义所有处理函数
  const fetchData = useCallback(() => {
    setLoading(true);
    // 模拟API请求
    setTimeout(() => {
      setData(mockEmployees);
      setPagination(prev => ({
        ...prev,
        total: mockEmployees.length,
      }));
      setLoading(false);
    }, 500);
  }, []);

  const handleSearch = () => {
    const values = searchForm.getFieldsValue();
    console.log('搜索条件:', values);
    fetchData(values);
  };

  const handleReset = () => {
    searchForm.resetFields();
    fetchData();
  };

  const handleView = (record) => {
    console.log('查看详情:', record);
    // 实现查看详情逻辑
  };

  const handleEdit = (record) => {
    console.log('编辑记录:', record);
    // 实现编辑逻辑
  };

  const handleDelete = (record) => {
    Modal.confirm({
      title: '确认删除',
      content: `确定要删除 ${record.name} 的档案吗？`,
      onOk() {
        console.log('删除记录:', record);
        message.success('删除成功');
        // 实现删除逻辑
      },
    });
  };

  const handleExport = () => {
    console.log('导出数据');
    // 实现导出逻辑
  };

  const handleTableChange = (pagination, filters, sorter) => {
    setPagination(pagination);
    fetchData({
      ...searchForm.getFieldsValue(),
      page: pagination.current,
      pageSize: pagination.pageSize,
      sortField: sorter.field,
      sortOrder: sorter.order,
    });
  };

  // 模拟数据
  const mockEmployees = [
    {
      id: '202401010101',
      name: '张三',
      gender: 'male',
      department: '研发部',
      position: '软件工程师',
      title: '高级工程师',
      phone: '13800138000',
      email: 'zhangsan@example.com',
      status: 'active',
      entryDate: '2024-01-01',
    },
    // ... 更多模拟数据
  ];

  // 表格列定义
  const columns = [
    {
      title: '档案编号',
      dataIndex: 'id',
      key: 'id',
      width: 140,
      fixed: 'left',
    },
    {
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
      width: 100,
      fixed: 'left',
    },
    {
      title: '性别',
      dataIndex: 'gender',
      key: 'gender',
      width: 80,
      render: (gender) => gender === 'male' ? '男' : '女',
    },
    {
      title: '部门',
      dataIndex: 'department',
      key: 'department',
      width: 120,
    },
    {
      title: '职位',
      dataIndex: 'position',
      key: 'position',
      width: 120,
    },
    {
      title: '职称',
      dataIndex: 'title',
      key: 'title',
      width: 120,
    },
    {
      title: '联系电话',
      dataIndex: 'phone',
      key: 'phone',
      width: 120,
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      key: 'email',
      width: 180,
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 100,
      render: (status) => (
        <Tag color={status === 'active' ? 'green' : 'red'}>
          {status === 'active' ? '在职' : '离职'}
        </Tag>
      ),
    },
    {
      title: '入职日期',
      dataIndex: 'entryDate',
      key: 'entryDate',
      width: 120,
    },
    {
      title: '操作',
      key: 'action',
      fixed: 'right',
      width: 180,
      render: (_, record) => (
        <Space>
          <Tooltip title="查看">
            <Button 
              type="link" 
              icon={<EyeOutlined />} 
              onClick={() => handleView(record)}
            />
          </Tooltip>
          <Tooltip title="编辑">
            <Button 
              type="link" 
              icon={<EditOutlined />} 
              onClick={() => handleEdit(record)}
            />
          </Tooltip>
          <Tooltip title="删除">
            <Button 
              type="link" 
              danger 
              icon={<DeleteOutlined />} 
              onClick={() => handleDelete(record)}
            />
          </Tooltip>
        </Space>
      ),
    },
  ];

  // 搜索表单
  const searchFormItems = (
    <Form form={searchForm} layout="vertical">
      <Row gutter={16}>
        <Col span={6}>
          <Form.Item name="name" label="姓名">
            <Input placeholder="请输入姓名" allowClear />
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="department" label="部门">
            <Select placeholder="请选择部门" allowClear>
              <Option value="研发部">研发部</Option>
              <Option value="市场部">市场部</Option>
              <Option value="运营部">运营部</Option>
            </Select>
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="position" label="职位">
            <Select placeholder="请选择职位" allowClear>
              <Option value="软件工程师">软件工程师</Option>
              <Option value="产品经理">产品经理</Option>
              <Option value="运营专员">运营专员</Option>
            </Select>
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="status" label="状态">
            <Select placeholder="请选择状态" allowClear>
              <Option value="active">在职</Option>
              <Option value="inactive">离职</Option>
            </Select>
          </Form.Item>
        </Col>
      </Row>
      <Row>
        <Col span={24} style={{ textAlign: 'right' }}>
          <Space>
            <Button icon={<ReloadOutlined />} onClick={handleReset}>
              重置
            </Button>
            <Button type="primary" icon={<SearchOutlined />} onClick={handleSearch}>
              搜索
            </Button>
            <Button type="primary" icon={<ExportOutlined />} onClick={handleExport}>
              导出
            </Button>
          </Space>
        </Col>
      </Row>
    </Form>
  );

  // 初始加载
  useEffect(() => {
    fetchData();
  }, [fetchData]);

  return (
    <Card>
      {searchFormItems}
      <Table
        columns={columns}
        dataSource={data}
        pagination={pagination}
        loading={loading}
        onChange={handleTableChange}
        scroll={{ x: 1500 }}
        rowKey="id"
      />
    </Card>
  );
};

export default EmployeeList; 