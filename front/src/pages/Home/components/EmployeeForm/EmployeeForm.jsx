import { useState, useEffect } from 'react';
import { Form, Input, Select, DatePicker, Button, Row, Col, Card, message } from 'antd';
import { employeeAPI } from '../../../../api/modules/employee';
import { organizationAPI, positionCategorieAPI, positionAPI, titleAPI, educationAPI, ethnicitiesAPI } from '../../../../api/modules/system';
import { salaryItemsAPI } from '../../../../api/modules/salary';
import moment from 'moment';

const { Option } = Select;

const EmployeeForm = () => {
  const [form] = Form.useForm();
  const [orgOptions, setOrgOptions] = useState({ level1: [], level2: [], level3: [] });
  const [positionCategories, setPositionCategories] = useState([]);
  const [positions, setPositions] = useState([]);
  const [titles, setTitles] = useState([]);
  const [educations, setEducations] = useState([]);
  const [ethnicities, setEthnicities] = useState([]);
  const [salaryItems, setSalaryItems] = useState([]);

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

  // 获取职称
  useEffect(() => {
    titleAPI.list().then(response => {
      setTitles(response.data.records);
    });
  }, []);

  // 获取学历
  useEffect(() => {
    educationAPI.list().then(response => {
      setEducations(response.data.records);
    });
  }, []);

  // 获取民族
  useEffect(() => {
    ethnicitiesAPI.list().then(response => {
      setEthnicities(response.data.records);
    });
  }, []);

  // 获取薪资项目
  useEffect(() => {
    // salaryItemsAPI.getItems().then(response => {
    //   setSalaryItems(response.data.records);
    // });
  }, []);

  // 处理机构选择变化
  const handleOrgChange = (level, value) => {
    if (level === 1) {
      form.setFieldsValue({ orgId2: undefined, orgId3: undefined });
      if (value) {
        organizationAPI.list({ level: 2, parentId: value }).then(response => {
          setOrgOptions(prev => ({ ...prev, level2: response.data.records }));
        });
      } else {
        setOrgOptions(prev => ({ ...prev, level2: [], level3: [] }));
      }
    } else if (level === 2) {
      form.setFieldsValue({ orgId3: undefined });
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
    form.setFieldsValue({ positionId: undefined });
    if (value) {
      positionAPI.list({ categoryId: value }).then(response => {
        setPositions(response.data.records);
      });
    } else {
      setPositions([]);
    }
  };

  const onFinish = (values) => {
    const employeeData = {
      ...values,
      registrar: '当前登录用户', // 应从登录状态获取
      registrationTime: moment(),
    };
    employeeAPI.addEmployee(employeeData).then(() => {
      message.success('添加成功');
      form.resetFields();
    }).catch(() => {
      message.error('添加失败');
    });
  };

  return (
    <Card title="员工信息录入" bordered={false}>
      <Form
        form={form}
        layout="vertical"
        onFinish={onFinish}
        initialValues={{
          registrar: '当前登录用户',
          registrationTime: moment(),
        }}
      >
        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="orgId1" label="I级机构" rules={[{ required: true, message: '请选择I级机构' }]}>
              <Select placeholder="请选择I级机构" onChange={(value) => handleOrgChange(1, value)}>
                {orgOptions.level1.map(org => <Option key={org.orgId} value={org.orgId}>{org.orgName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="orgId2" label="II级机构" rules={[{ required: true, message: '请选择II级机构' }]}>
              <Select placeholder="请选择II级机构" onChange={(value) => handleOrgChange(2, value)} disabled={!form.getFieldValue('orgId1')}>
                {orgOptions.level2.map(org => <Option key={org.orgId} value={org.orgId}>{org.orgName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="orgId3" label="III级机构" rules={[{ required: true, message: '请选择III级机构' }]}>
              <Select placeholder="请选择III级机构" disabled={!form.getFieldValue('orgId2')}>
                {orgOptions.level3.map(org => <Option key={org.orgId} value={org.orgId}>{org.orgName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="categoryId" label="职位分类" rules={[{ required: true }]}>
              <Select placeholder="请选择职位分类" onChange={handleCategoryChange}>
                {positionCategories.map(category => <Option key={category.categoryId} value={category.categoryId}>{category.categoryName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="positionId" label="职位名称" rules={[{ required: true }]}>
              <Select placeholder="请选择职位名称" disabled={!form.getFieldValue('categoryId')}>
                {positions.map(position => <Option key={position.positionId} value={position.positionId}>{position.positionName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="titleId" label="职称" rules={[{ required: true }]}>
              <Select placeholder="请选择职称">
                {titles.map(title => <Option key={title.titleId} value={title.titleId}>{title.titleName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="name" label="姓名" rules={[{ required: true }]}>
              <Input placeholder="请输入姓名" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="gender" label="性别" rules={[{ required: true }]}>
              <Select placeholder="请选择性别">
                <Option value="0">男</Option>
                <Option value="1">女</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="birthdate" label="出生日期" rules={[{ required: true }]}>
              <DatePicker style={{ width: '100%' }} />
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col span={8}>
            <Form.Item 
              name="email" 
              label="Email"
              rules={[{ type: 'email' }]}
            >
              <Input placeholder="请输入邮箱" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="phone" label="电话">
              <Input placeholder="请输入电话" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="mobile" label="手机">
              <Input placeholder="请输入手机号" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="qq" label="QQ">
              <Input placeholder="请输入QQ" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="postalCode" label="邮编">
              <Input placeholder="请输入邮编" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="nationality" label="国籍">
              <Input placeholder="请输入国籍" defaultValue="中国" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="birthplace" label="出生地">
              <Input placeholder="请输入出生地" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="ethnicityId" label="民族" rules={[{ required: true }]}>
              <Select placeholder="请选择民族">
                {ethnicities.map(ethnicity => <Option key={ethnicity.ethnicityId} value={ethnicity.ethnicityId}>{ethnicity.ethnicityName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="religion" label="宗教信仰">
              <Input placeholder="请输入宗教信仰" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="politicalAffiliation" label="政治面貌">
              <Input placeholder="请输入政治面貌" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item 
              name="idNumber" 
              label="身份证号码"
              rules={[{ required: true }]}
            >
              <Input placeholder="请输入身份证号码" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="socialSecurityNumber" label="社会保障号码">
              <Input placeholder="请输入社会保障号码" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
        <Col span={8}>
            <Form.Item name="educationId" label="学历" rules={[{ required: true }]}>
              <Select placeholder="请选择学历">
                {educations.map(education => <Option key={education.educationId} value={education.educationId}>{education.educationName}</Option>)}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="major" label="学历专业">
              <Input placeholder="请输入专业" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="salaryStandardId" label="薪酬标准">
                <Select placeholder="请选择薪酬标准">
                  {salaryItems.map(item => <Option key={item.id} value={item.id}>{item.name}</Option>)}
                </Select>
              </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={12}>
            <Form.Item name="bankName" label="开户行">
              <Input placeholder="请输入开户行" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="accountNumber" label="账号">
              <Input placeholder="请输入银行账号" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={24}>
            <Form.Item name="address" label="住址">
              <Input.TextArea rows={2} placeholder="请输入详细住址" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={12}>
            <Form.Item name="skills" label="特长">
              <Input.TextArea rows={2} placeholder="请输入特长" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="hobbies" label="爱好">
              <Input.TextArea rows={2} placeholder="请输入爱好" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={24}>
            <Form.Item name="personalHistory" label="个人履历">
              <Input.TextArea rows={4} placeholder="请输入个人履历" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={24}>
            <Form.Item name="familyInfo" label="家庭关系信息">
              <Input.TextArea rows={4} placeholder="请输入家庭关系信息" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={24}>
            <Form.Item name="remarks" label="备注">
              <Input.TextArea rows={3} placeholder="请输入备注信息" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={12}>
            <Form.Item name="registrar" label="登记人">
              <Input disabled />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="registrationTime" label="登记时间">
              <DatePicker 
                showTime 
                style={{ width: '100%' }} 
                disabled 
              />
            </Form.Item>
          </Col>
        </Row>
        <Form.Item>
          <Button type="primary" htmlType="submit">提交</Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default EmployeeForm;