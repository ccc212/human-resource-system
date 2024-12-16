import { Card, Descriptions, Button, Space } from 'antd';
import { useNavigate } from 'react-router-dom';
import { ArrowLeftOutlined } from '@ant-design/icons';

const SalaryStandardDetail = () => {
  const navigate = useNavigate();

  // 模拟获取详情数据
  const detailData = {
    id: 'SS001',
    name: '初级工程师薪资标准',
    positionType: '技术类',
    basicSalary: 8000,
    positionSalary: 3000,
    performanceSalary: 2000,
    insuranceBase: 10000,
    fundBase: 10000,
    creator: '张三',
    registrar: '李四',
    registerTime: '2024-03-20 14:30:00',
    pensionInsurance: 640,
    medicalInsurance: 163,
    unemploymentInsurance: 40,
    housingFund: 640,
  };

  return (
    <Card
      title="薪资标准详情"
      extra={
        <Space>
          <Button 
            icon={<ArrowLeftOutlined />} 
            onClick={() => navigate('/home/salary-standard')}
          >
            返回列表
          </Button>
        </Space>
      }
    >
      <Descriptions bordered column={2}>
        <Descriptions.Item label="标准编号">{detailData.id}</Descriptions.Item>
        <Descriptions.Item label="标准名称">{detailData.name}</Descriptions.Item>
        <Descriptions.Item label="职位类别">{detailData.positionType}</Descriptions.Item>
        <Descriptions.Item label="基本工资">{detailData.basicSalary}</Descriptions.Item>
        <Descriptions.Item label="岗位工资">{detailData.positionSalary}</Descriptions.Item>
        <Descriptions.Item label="绩效工资">{detailData.performanceSalary}</Descriptions.Item>
        
        <Descriptions.Item label="养老保险" span={2}>
          基数：{detailData.insuranceBase} | 个人缴纳：{detailData.pensionInsurance}
        </Descriptions.Item>
        <Descriptions.Item label="医疗保险" span={2}>
          基数：{detailData.insuranceBase} | 个人缴纳：{detailData.medicalInsurance}
        </Descriptions.Item>
        <Descriptions.Item label="失业保险" span={2}>
          基数：{detailData.insuranceBase} | 个人缴纳：{detailData.unemploymentInsurance}
        </Descriptions.Item>
        <Descriptions.Item label="住房公积金" span={2}>
          基数：{detailData.fundBase} | 个人缴纳：{detailData.housingFund}
        </Descriptions.Item>

        <Descriptions.Item label="制定人">{detailData.creator}</Descriptions.Item>
        <Descriptions.Item label="登记人">{detailData.registrar}</Descriptions.Item>
        <Descriptions.Item label="登记时间" span={2}>{detailData.registerTime}</Descriptions.Item>
      </Descriptions>
    </Card>
  );
};

export default SalaryStandardDetail; 