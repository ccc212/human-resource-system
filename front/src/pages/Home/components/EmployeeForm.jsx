import { 
  Form, 
  Input, 
  Select, 
  DatePicker, 
  Upload, 
  Button, 
  Row, 
  Col, 
  Card,
  InputNumber 
} from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import moment from 'moment';

const { TextArea } = Input;
const { Option } = Select;

const EmployeeForm = () => {
  const [form] = Form.useForm();
  
  // 扩展模拟数据
  const mockData = {
    organizations: {
      level1: [{ id: '01', name: '总公司' }],
      level2: [{ id: '01', name: '研发部' }],
      level3: [{ id: '01', name: '开发组' }],
    },
    positions: [{ id: '01', name: '软件工程师' }],
    positionTypes: [{ id: '01', name: '技术类' }],
    nations: ['汉族', '满族', '蒙古族', '回族'],
    educations: ['博士', '硕士', '本科', '大专'],
    titles: ['初级', '中级', '高级', '专家'],
    religions: ['无', '佛教', '基督教', '伊斯兰教', '其他'],
    politicalStatus: ['群众', '共青团员', '中共党员'],
    banks: ['工商银行', '建设银行', '农业银行', '中国银行'],
  };

  // 生成员工编号的函数
  const generateEmployeeId = () => {
    const year = moment().format('YYYY');
    const level1 = form.getFieldValue('level1Org') || '01';
    const level2 = form.getFieldValue('level2Org') || '01';
    const level3 = form.getFieldValue('level3Org') || '01';
    const sequence = '01'; // 这里应该从后端获取序列号
    
    return `${year}${level1}${level2}${level3}${sequence}`;
  };

  const onFinish = (values) => {
    const employeeData = {
      ...values,
      registrar: '当前登录用户', // 应从登录状态获取
      registrationTime: moment().format('YYYY-MM-DD HH:mm:ss'),
      employeeId: generateEmployeeId(),
    };
    console.log('提交的数据:', employeeData);
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
            <Form.Item 
              name="level1Org" 
              label="I级机构"
              rules={[{ required: true, message: '请选择I级机构' }]}
            >
              <Select placeholder="请选择I级机构">
                {mockData.organizations.level1.map(org => (
                  <Option key={org.id} value={org.id}>{org.name}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          
          <Col span={8}>
            <Form.Item 
              name="level2Org" 
              label="II级机构"
              rules={[{ required: true, message: '请选择II级机构' }]}
            >
              <Select placeholder="请选择II级机构">
                {mockData.organizations.level2.map(org => (
                  <Option key={org.id} value={org.id}>{org.name}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>

          <Col span={8}>
            <Form.Item 
              name="level3Org" 
              label="III级机构"
              rules={[{ required: true, message: '请选择III级机构' }]}
            >
              <Select placeholder="请选择III级机构">
                {mockData.organizations.level3.map(org => (
                  <Option key={org.id} value={org.id}>{org.name}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item 
              name="positionType" 
              label="职位分类"
              rules={[{ required: true }]}
            >
              <Select placeholder="请选择职位分类">
                {mockData.positionTypes.map(type => (
                  <Option key={type} value={type}>{type}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item 
              name="position" 
              label="职位名称"
              rules={[{ required: true }]}
            >
              <Select placeholder="请选择职位名称">
                {mockData.positions.map(pos => (
                  <Option key={pos.id} value={pos.id}>{pos.name}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item 
              name="title" 
              label="职称"
              rules={[{ required: true }]}
            >
              <Select placeholder="请选择职称">
                {mockData.titles.map(title => (
                  <Option key={title} value={title}>{title}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item 
              name="name" 
              label="姓名"
              rules={[{ required: true }]}
            >
              <Input placeholder="请输入姓名" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item 
              name="gender" 
              label="性别"
              rules={[{ required: true }]}
            >
              <Select placeholder="请选择性别">
                <Option value="male">男</Option>
                <Option value="female">女</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item 
              name="birthDate" 
              label="出生日期"
              rules={[{ required: true }]}
            >
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
            <Form.Item name="postcode" label="邮编">
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
            <Form.Item 
              name="nation" 
              label="民族"
              rules={[{ required: true }]}
            >
              <Select placeholder="请选择民族">
                {mockData.nations.map(nation => (
                  <Option key={nation} value={nation}>{nation}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="religion" label="宗教信仰">
              <Select placeholder="请选择宗教信仰">
                {mockData.religions.map(religion => (
                  <Option key={religion} value={religion}>{religion}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item name="politicalStatus" label="政治面貌">
              <Select placeholder="请选择政治面貌">
                {mockData.politicalStatus.map(status => (
                  <Option key={status} value={status}>{status}</Option>
                ))}
              </Select>
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
            <Form.Item name="socialSecurity" label="社会保障号码">
              <Input placeholder="请输入社会保障号码" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={8}>
            <Form.Item 
              name="education" 
              label="学历"
              rules={[{ required: true }]}
            >
              <Select placeholder="请选择学历">
                {mockData.educations.map(edu => (
                  <Option key={edu} value={edu}>{edu}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="major" label="学历专业">
              <Input placeholder="请输入专业" />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name="salary" label="薪酬标准">
              <InputNumber 
                style={{ width: '100%' }}
                formatter={value => `￥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                parser={value => value.replace(/\￥\s?|(,*)/g, '')}
              />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={12}>
            <Form.Item name="bank" label="开户行">
              <Select placeholder="请选择开户行">
                {mockData.banks.map(bank => (
                  <Option key={bank} value={bank}>{bank}</Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="bankAccount" label="账号">
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
            <Form.Item name="speciality" label="特长">
              <Input.TextArea rows={2} placeholder="请输入特长" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="hobby" label="爱好">
              <Input.TextArea rows={2} placeholder="请输入爱好" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={24}>
          <Col span={24}>
            <Form.Item name="resume" label="个人履历">
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
          <Button type="primary" htmlType="submit">
            提交
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default EmployeeForm; 