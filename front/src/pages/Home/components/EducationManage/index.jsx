import React, { useState, useEffect } from 'react';
import { educationAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Space, message } from 'antd';

const EducationManage = () => {
  const [educations, setEducations] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentEducation, setCurrentEducation] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchEducations(pagination.current, pagination.pageSize);
  }, []);

  const fetchEducations = async (page, pageSize) => {
    try {
      const response = await educationAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setEducations(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取学历列表失败');
      }
    } catch (error) {
      message.error('获取学历列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentEducation(null);
    setIsModalVisible(true);
  };

  const handleEdit = (education) => {
    setCurrentEducation(education);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await educationAPI.delete(id);
      message.success('删除成功');
      fetchEducations(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentEducation) {
        await educationAPI.update({ educationId: currentEducation.educationId, ...values });
        message.success('更新成功');
      } else {
        await educationAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchEducations(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchEducations(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '学历名称', dataIndex: 'educationName', key: 'educationName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.educationId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加学历</Button>
      <Table
        columns={columns}
        dataSource={educations}
        rowKey="educationId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentEducation ? '编辑学历' : '添加学历'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentEducation}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="educationName"
            label="学历名称"
            rules={[{ required: true, message: '请输入学历名称' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
              保存
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default EducationManage;