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
  message,
  DatePicker
} from 'antd';
import { 
  SearchOutlined, 
  EditOutlined, 
  DeleteOutlined,
  EyeOutlined,
  ReloadOutlined
} from '@ant-design/icons';
import { employeeAPI } from '../../../../api/modules/employee';
import { organizationAPI, positionCategorieAPI, positionAPI } from '../../../../api/modules/system'; 
const { RangePicker } = DatePicker;
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
  const [orgOptions, setOrgOptions] = useState({ level1: [], level2: [], level3: [] });
  const [positionCategories, setPositionCategories] = useState([]);
  const [positions, setPositions] = useState([]);

  // 获取一级机构
  useEffect(() => {
    organizationAPI.list({ level: 1 }).then(response => {
      setOrgOptions(prev => ({ ...prev, level1: response.data.records }));
    });
  }, []);

  // 获取职位分类
  useEffect(() => {
    positionCategorieAPI.list().then(response => {
      setPositionCategories(response.data.records);
    });
  }, []);

  // 处理机构选择变化
  const handleOrgChange = (level, value) => {
    if (level === 1) {
      searchForm.setFieldsValue({ orgId2: undefined, orgId3: undefined });
      if (value) {
        organizationAPI.list({ level: 2, parentId: value }).then(response => {
          setOrgOptions(prev => ({ ...prev, level2: response.data.records }));
        });
      } else {
        setOrgOptions(prev => ({ ...prev, level2: [], level3: [] }));
      }
    } else if (level === 2) {
      searchForm.setFieldsValue({ orgId3: undefined });
      if (value) {
        organizationAPI.list({ level: 3, parentId: value }).then(response => {
          setOrgOptions(prev => ({ ...prev, level3: response.data.records }));
        });
      } else {
        setOrgOptions(prev => ({ ...prev, level3: [] }));
      }
    }
  };

  // 处理职位分类选择变化
  const handleCategoryChange = (value) => {
    searchForm.setFieldsValue({ positionId: undefined });
    if (value) {
      positionAPI.list({ categoryId: value }).then(response => {
        setPositions(response.data.records);
      });
    } else {
      setPositions([]);
    }
  };

  // 先定义所有处理函数
  const fetchData = useCallback((params = {}) => {
    setLoading(true);
    employeeAPI.fetchEmployees({
      ...params,
      current: pagination.current,
      pageSize: pagination.pageSize,
    }).then(response => {
      setData(response.data.map(item => ({
        id: item.recordId,
        name: item.name,
        gender: item.gender === '0' ? '男' : '女',
        orgName1: item.orgName1,
        orgName2: item.orgName2,
        orgName3: item.orgName3,
        position: item.positionName,
      })));
      setPagination(prev => ({
        ...prev,
        total: response.data.length,
      }));
      setLoading(false);
    }).catch(() => {
      setLoading(false);
    });
  }, [pagination.current, pagination.pageSize]);

  const handleSearch = () => {
    const values = searchForm.getFieldsValue();
    const { begin } = values;
  if (begin) {
    values.begin = begin[0].format('YYYY-MM-DD');
    values.end = begin[1].format('YYYY-MM-DD');
    }
    console.log('搜索条件:', values);
    fetchData(values);
  };

  const handleReset = () => {
    searchForm.resetFields();
    fetchData();
  };

  // 查看详情
const handleView = (record) => {
  Modal.info({
    title: '员工详细信息',
    content: (
      <div>
        <p>姓名: {record.name}</p>
        <p>性别: {record.gender}</p>
        <p>一级机构: {record.orgName1}</p>
        <p>二级机构: {record.orgName2}</p>
        <p>三级机构: {record.orgName3}</p>
        <p>职位名称: {record.positionName}</p>
        <p>职称: {record.titleName}</p>
        <p>邮箱: {record.email}</p>
        <p>电话: {record.phone}</p>
        <p>登记人: {record.registrar}</p>
        <p>登记时间: {record.registrationTime}</p>
        {/* 添加其他字段 */}
      </div>
    ),
    onOk() {},
  });
};

  const handleEdit = (record) => {
    console.log('编辑记录:', record);
    // 实现编辑逻辑
  };

  // 删除
  const handleDelete = (id) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这条记录吗？',
      onOk: () => {
        employeeAPI.deleteEmployee(id).then(() => {
          message.success('删除成功');
          fetchData(); // 重新加载数据
        });
      },
    });
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
    },
    {
      title: '一级机构',
      dataIndex: 'orgName1',
      key: 'orgName1',
      width: 120,
    },
    {
      title: '二级机构',
      dataIndex: 'orgName2',
      key: 'orgName2',
      width: 120,
    },
    {
      title: '三级机构',
      dataIndex: 'orgName3',
      key: 'orgName3',
      width: 120,
    },
    {
      title: '职位',
      dataIndex: 'position',
      key: 'position',
      width: 120,
    },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space size="middle">
          <Button icon={<EyeOutlined />} onClick={() => handleView(record)}>查看</Button>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)}>编辑</Button>
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger>删除</Button>
        </Space>
      ),
    },
  ];

  // 搜索表单
  const searchFormItems = (
    <Form form={searchForm} layout="vertical">
      <Row gutter={16}>
        <Col span={6}>
          <Form.Item name="orgId1" label="一级机构">
            <Select placeholder="请选择一级机构" allowClear onChange={(value) => handleOrgChange(1, value)}>
              {orgOptions.level1.map(org => <Option key={org.orgId} value={org.orgId}>{org.orgName}</Option>)}
            </Select>
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="orgId2" label="二级机构">
          <Select placeholder="请选择二级机构" allowClear onChange={(value) => handleOrgChange(2, value)} disabled={!searchForm.getFieldValue('orgId1')}>
              {orgOptions.level2.map(org => <Option key={org.orgId} value={org.orgId}>{org.orgName}</Option>)}
            </Select>
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="orgId3" label="三级机构">
          <Select placeholder="请选择三级机构" allowClear disabled={!searchForm.getFieldValue('orgId2')}>
              {orgOptions.level3.map(org => <Option key={org.orgId} value={org.orgId}>{org.orgName}</Option>)}
            </Select>
          </Form.Item>
        </Col>
         <Col span={6}>
          <Form.Item name="categoryId" label="职位分类">
            <Select placeholder="请选择职位分类" allowClear onChange={handleCategoryChange}>
              {positionCategories.map(category => <Option key={category.categoryId} value={category.categoryId}>{category.categoryName}</Option>)}
            </Select>
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="positionId" label="职位名称">
            <Select placeholder="请选择职位名称" allowClear disabled={!searchForm.getFieldValue('categoryId')}>
              {positions.map(position => <Option key={position.positionId} value={position.positionId}>{position.positionName}</Option>)}
            </Select>
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item name="begin" label="建档时间">
            <RangePicker allowClear format="YYYY-MM-DD" />
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