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
  const [editingRecord, setEditingRecord] = useState(null);
  const [editForm] = Form.useForm();

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
      setData(response.data.records.map(item => ({
        id: item.recordId,
        name: item.name,
        gender: item.gender === '0' ? '男' : '女',
        orgName1: item.orgName1,
        orgName2: item.orgName2,
        orgName3: item.orgName3,
        position: item.positionName,
        status: item.status,
        categoryName: item.categoryName,
        titleName: item.titleName,
        email: item.email,
        phone: item.phone,
        qq: item.qq,
        mobile: item.mobile,
        address: item.address,
        postalCode: item.postalCode,
        nationality: item.nationality,
        birthplace: item.birthplace,
        birthdate: item.birthdate,
        ethnicityName: item.ethnicityName,
        religion: item.religion,
        politicalAffiliation: item.politicalAffiliation,
        idNumber: item.idNumber,
        socialSecurityNumber: item.socialSecurityNumber,
        age: item.age,
        educationName: item.educationName,
        major: item.major,
        salaryStandardName: item.salaryStandardName,
        bankName: item.bankName,
        accountNumber: item.accountNumber,
        skills: item.skills,
        hobbies: item.hobbies,
        personalHistory: item.personalHistory,
        familyInfo: item.familyInfo,
        remarks: item.remarks,
        photoUrl: item.photoUrl,
        registrar: item.registrar,
        registrationTime: item.registrationTime,
      })));
      setPagination(prev => ({
        ...prev,
        total: response.data.total,
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
  employeeAPI.getEmployeeById(record.id).then(response => {
    const detail = response.data;
    Modal.info({
      title: '员工详细信息',
      width: 1200,
      content: (
        <div>
          <Row gutter={16}>
            <Col span={8}>
              <p><strong>档案编号:</strong> {detail.recordId}</p>
              <p><strong>姓名:</strong> {detail.name}</p>
              <p><strong>性别:</strong> {detail.gender === '0' ? '男' : '女'}</p>
              <p><strong>一级机构:</strong> {detail.orgName1}</p>
              <p><strong>二级机构:</strong> {detail.orgName2}</p>
              <p><strong>三级机构:</strong> {detail.orgName3}</p>
              <p><strong>职位名称:</strong> {detail.positionName}</p>
              <p><strong>职位分类:</strong> {detail.categoryName}</p>
              <p><strong>职称:</strong> {detail.titleName}</p>
              <p><strong>邮箱:</strong> {detail.email}</p>
              <p><strong>电话:</strong> {detail.phone}</p>
              <p><strong>QQ:</strong> {detail.qq}</p>
              <p><strong>手机:</strong> {detail.mobile}</p>
            </Col>
            <Col span={8}>
              <p><strong>住址:</strong> {detail.address}</p>
              <p><strong>邮政编码:</strong> {detail.postalCode}</p>
              <p><strong>国籍:</strong> {detail.nationality}</p>
              <p><strong>出生地:</strong> {detail.birthplace}</p>
              <p><strong>出生日期:</strong> {detail.birthdate}</p>
              <p><strong>民族:</strong> {detail.ethnicityName}</p>
              <p><strong>宗教信仰:</strong> {detail.religion}</p>
              <p><strong>政治面貌:</strong> {detail.politicalAffiliation}</p>
              <p><strong>身份证号码:</strong> {detail.idNumber}</p>
              <p><strong>社会保障号码:</strong> {detail.socialSecurityNumber}</p>
              <p><strong>年龄:</strong> {detail.age}</p>
              <p><strong>学历:</strong> {detail.educationName}</p>
              <p><strong>专业:</strong> {detail.major}</p>
            </Col>
            <Col span={8}>
              <p><strong>薪酬标准:</strong> {detail.salaryStandardName}</p>
              <p><strong>开户行:</strong> {detail.bankName}</p>
              <p><strong>账号:</strong> {detail.accountNumber}</p>
              <p><strong>特长:</strong> {detail.skills}</p>
              <p><strong>爱好:</strong> {detail.hobbies}</p>
              <p><strong>个人履历:</strong> {detail.personalHistory}</p>
              <p><strong>家庭关系信息:</strong> {detail.familyInfo}</p>
              <p><strong>备注:</strong> {detail.remarks}</p>
              <p><strong>员工相片:</strong> <img src={detail.photoUrl} alt="员工相片" style={{ width: '100px' }} /></p>
              <p><strong>登记人:</strong> {detail.registrar}</p>
              <p><strong>登记时间:</strong> {detail.registrationTime}</p>
            </Col>
          </Row>
        </div>
      ),
      onOk() {},
      footer: (
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button onClick={() => Modal.destroyAll()}>返回</Button>
          <Button type="primary" onClick={() => handleReview(record.id)}>
            复核
          </Button>
        </div>
      ),
    });
  }).catch(() => {
    message.error('获取详细信息失败');
  });
};

const handleReview = (id) => {
  employeeAPI.reviewEmployee(id).then(() => {
    message.success('复核成功');
    fetchData(); // 重新加载数据
  }).catch(() => {
    message.error('复核失败');
  });
};

  const handleEdit = (record) => {
    setEditingRecord(record);
    editForm.setFieldsValue(record);
    Modal.confirm({
      title: '编辑员工信息',
      width: 800,
      content: (
        <Form form={editForm} layout="vertical">
          <Row gutter={16}>
            <Col span={8}>
              <Form.Item name="name" label="姓名" rules={[{ required: true, message: '请输入姓名' }]}>
                <Input />
              </Form.Item>
              <Form.Item name="gender" label="性别" rules={[{ required: true, message: '请选择性别' }]}>
                <Select>
                  <Option value="0">男</Option>
                  <Option value="1">女</Option>
                </Select>
              </Form.Item>
              <Form.Item name="orgName1" label="一级机构">
                <Input />
              </Form.Item>
              <Form.Item name="orgName2" label="二级机构">
                <Input />
              </Form.Item>
              <Form.Item name="orgName3" label="三级机构">
                <Input />
              </Form.Item>
              <Form.Item name="position" label="职位名称">
                <Input />
              </Form.Item>
              <Form.Item name="categoryName" label="职位分类">
                <Input />
              </Form.Item>
              <Form.Item name="titleName" label="职称">
                <Input />
              </Form.Item>
              <Form.Item name="email" label="邮箱">
                <Input />
              </Form.Item>
              <Form.Item name="phone" label="电话">
                <Input />
              </Form.Item>
              <Form.Item name="qq" label="QQ">
                <Input />
              </Form.Item>
              <Form.Item name="mobile" label="手机">
                <Input />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item name="address" label="住址">
                <Input />
              </Form.Item>
              <Form.Item name="postalCode" label="邮政编码">
                <Input />
              </Form.Item>
              <Form.Item name="nationality" label="国籍">
                <Input />
              </Form.Item>
              <Form.Item name="birthplace" label="出生地">
                <Input />
              </Form.Item>
              <Form.Item name="birthdate" label="出生日期">
                <DatePicker style={{ width: '100%' }} />
              </Form.Item>
              <Form.Item name="ethnicityName" label="民族">
                <Input />
              </Form.Item>
              <Form.Item name="religion" label="宗教信仰">
                <Input />
              </Form.Item>
              <Form.Item name="politicalAffiliation" label="政治面貌">
                <Input />
              </Form.Item>
              <Form.Item name="idNumber" label="身份证号码">
                <Input />
              </Form.Item>
              <Form.Item name="socialSecurityNumber" label="社会保障号码">
                <Input />
              </Form.Item>
              <Form.Item name="age" label="年龄">
                <Input />
              </Form.Item>
              <Form.Item name="educationName" label="学历">
                <Input />
              </Form.Item>
            </Col>
            <Col span={8}>
              <Form.Item name="major" label="专业">
                <Input />
              </Form.Item>
              <Form.Item name="salaryStandardName" label="薪酬标准">
                <Input />
              </Form.Item>
              <Form.Item name="bankName" label="开户行">
                <Input />
              </Form.Item>
              <Form.Item name="accountNumber" label="账号">
                <Input />
              </Form.Item>
              <Form.Item name="skills" label="特长">
                <Input />
              </Form.Item>
              <Form.Item name="hobbies" label="爱好">
                <Input />
              </Form.Item>
              <Form.Item name="personalHistory" label="个人履历">
                <Input />
              </Form.Item>
              <Form.Item name="familyInfo" label="家庭关系信息">
                <Input />
              </Form.Item>
              <Form.Item name="remarks" label="备注">
                <Input />
              </Form.Item>
              <Form.Item name="photoUrl" label="员工相片">
                <Input />
              </Form.Item>
              <Form.Item name="registrar" label="登记人">
                <Input disabled />
              </Form.Item>
              <Form.Item name="registrationTime" label="登记时间">
                <DatePicker showTime style={{ width: '100%' }} disabled />
              </Form.Item>
            </Col>
          </Row>
        </Form>
      ),
      onOk: () => {
        editForm.validateFields().then(values => {
          employeeAPI.updateEmployee({ recordId: editingRecord.id, ...values }).then(() => {
            message.success('更新成功');
            fetchData();
          }).catch(() => {
            message.error('更新失败');
          });
        }).catch(() => {
          message.error('请检查输入');
        });
      },
    });
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
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 100,
      render: (status) => {
        switch (status) {
          case '0':
            return <Tag color="blue">未复核</Tag>;
          case '1':
            return <Tag color="green">已复核</Tag>;
          case '2':
            return <Tag color="red">已删除</Tag>;
          default:
            return <Tag>未知</Tag>;
        }
      },
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