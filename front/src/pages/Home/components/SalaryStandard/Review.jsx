import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, Space, message, Spin, Radio } from 'antd';
import { salaryStandardAPI } from '../../../../api/modules/salary';
import moment from 'moment';

const SalaryStandardReview = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedStandard, setSelectedStandard] = useState(null);
  const [form] = Form.useForm();

  // 获取待审核列表
  const fetchPendingStandards = async () => {
    try {
      setLoading(true);
      const response = await salaryStandardAPI.getPendingStandards();
      if (Array.isArray(response)) {
        setData(response);
      }
    } catch (error) {
      message.error('获取待审核列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPendingStandards();
  }, []);

  // 表格列定义
  const columns = [
    {
      title: '标准编号',
      dataIndex: 'standardId',
      key: 'standardId',
    },
    {
      title: '标准名称',
      dataIndex: 'salaryStandardName',
      key: 'salaryStandardName',
    },
    {
      title: '创建人',
      dataIndex: 'creator',
      key: 'creator',
    },
    {
      title: '登记人',
      dataIndex: 'registrar',
      key: 'registrar',
    },
    {
      title: '项目��额',
      key: 'total',
      render: (_, record) => {
        const total = record.items.reduce((sum, item) => sum + item.account, 0);
        return `¥${total.toFixed(2)}`;
      }
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space>
          <Button type="primary" onClick={() => handleReview(record)}>
            审核
          </Button>
          <Button onClick={() => handleViewDetail(record)}>
            查看详情
          </Button>
        </Space>
      ),
    },
  ];

  // 处理审核
  const handleReview = (record) => {
    setSelectedStandard(record);
    form.resetFields();
    setIsModalVisible(true);
  };

  // 处理查看详情
  const handleViewDetail = (record) => {
    Modal.info({
      title: '薪酬标准详情',
      width: 800,
      content: (
        <div>
          <p>标准名称：{record.salaryStandardName}</p>
          <p>创建人：{record.creator}</p>
          <p>登记人：{record.registrar}</p>
          <h4>薪酬项目</h4>
          <Table
            columns={[
              {
                title: '项目名称',
                dataIndex: 'name',
                key: 'name',
              },
              {
                title: '金额',
                dataIndex: 'account',
                key: 'account',
                render: (account) => `¥${Number(account).toFixed(2)}`
              }
            ]}
            dataSource={record.items}
            rowKey="itemID"
            pagination={false}
          />
        </div>
      ),
    });
  };

  // 处理审核提交
  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      
      const reviewData = {
        salaryStandardId: selectedStandard.standardId,
        reviewId: Number(localStorage.getItem('userId')), // 假设从localStorage获取当前用户ID
        reviewMessage: values.reviewMessage,
        reviewTime: moment().format('YYYY-MM-DD HH:mm:ss'),
        reviewStatus: values.reviewStatus
      };

      await salaryStandardAPI.reviewStandard(reviewData);
      message.success('审核成功');
      setIsModalVisible(false);
      fetchPendingStandards(); // 刷新列表
    } catch (error) {
      message.error('审核失败');
    }
  };

  return (
    <Spin spinning={loading}>
      <div style={{ padding: '24px' }}>
        <Table 
          columns={columns}
          dataSource={data}
          rowKey="standardId"
        />

        <Modal
          title="薪酬标准审核"
          visible={isModalVisible}
          onOk={handleSubmit}
          onCancel={() => setIsModalVisible(false)}
          width={600}
        >
          <Form form={form} layout="vertical">
            <Form.Item
              name="reviewStatus"
              label="审核结果"
              rules={[{ required: true, message: '请选择审核结果' }]}
            >
              <Radio.Group>
                <Radio value="APPROVED">通过</Radio>
                <Radio value="REJECTED">不通过</Radio>
              </Radio.Group>
            </Form.Item>

            <Form.Item
              name="reviewMessage"
              label="审核意见"
              rules={[{ required: true, message: '请输入审核意见' }]}
            >
              <Input.TextArea rows={4} />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    </Spin>
  );
};

export default SalaryStandardReview; 