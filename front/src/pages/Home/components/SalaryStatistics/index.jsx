import { Card, Row, Col, DatePicker, Button, Select, Table, Space } from 'antd';
import { Column } from '@ant-design/plots';
import { useState } from 'react';

const { RangePicker } = DatePicker;
const { Option } = Select;

const SalaryStatistics = () => {
  const [activeTab, setActiveTab] = useState('1');

  // 图表数据
  const data = [
    { department: '研发部', salary: 450000 },
    { department: '销售部', salary: 380000 },
    { department: '人力资源部', salary: 280000 },
    { department: '财务部', salary: 300000 },
  ];

  const config = {
    data,
    xField: 'department',
    yField: 'salary',
    label: {
      position: 'top',
      style: {
        fill: '#000000',
        opacity: 0.6,
      },
    },
    meta: {
      salary: {
        alias: '薪资总额',
      },
    },
  };

  return (
    <Card>
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={8}>
          <RangePicker style={{ width: '100%' }} />
        </Col>
        <Col span={8}>
          <Select placeholder="请选择统计维度" style={{ width: '100%' }}>
            <Option value="department">按部门统计</Option>
            <Option value="position">按职位统计</Option>
            <Option value="level">按职级统计</Option>
          </Select>
        </Col>
        <Col span={8} style={{ textAlign: 'right' }}>
          <Space>
            <Button>导出报表</Button>
            <Button type="primary">统计</Button>
          </Space>
        </Col>
      </Row>
      
      <Card title="薪资分布图" style={{ marginBottom: 24 }}>
        <Column {...config} />
      </Card>
      
      <Card title="薪资统计明细">
        <Table 
          columns={[
            { title: '统计维度', dataIndex: 'dimension', key: 'dimension' },
            { title: '人数', dataIndex: 'count', key: 'count' },
            { title: '薪资总额', dataIndex: 'totalSalary', key: 'totalSalary' },
            { title: '平均薪资', dataIndex: 'avgSalary', key: 'avgSalary' },
            { title: '最高薪资', dataIndex: 'maxSalary', key: 'maxSalary' },
            { title: '最低薪资', dataIndex: 'minSalary', key: 'minSalary' },
          ]}
          scroll={{ x: 800 }}
        />
      </Card>
    </Card>
  );
};

export default SalaryStatistics; 