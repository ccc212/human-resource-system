import React, { useState, useEffect } from 'react';
import { ethnicitiesAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Space, message } from 'antd';

const EthnicityManage = () => {
  const [ethnicities, setEthnicities] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentEthnicity, setCurrentEthnicity] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchEthnicities(pagination.current, pagination.pageSize);
  }, []);

  const fetchEthnicities = async (page, pageSize) => {
    try {
      const response = await ethnicitiesAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setEthnicities(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取民族列表失败');
      }
    } catch (error) {
      message.error('获取民族列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentEthnicity(null);
    setIsModalVisible(true);
  };

  const handleEdit = (ethnicity) => {
    setCurrentEthnicity(ethnicity);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await ethnicitiesAPI.delete(id);
      message.success('删除成功');
      fetchEthnicities(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentEthnicity) {
        await ethnicitiesAPI.update({ ethnicityId: currentEthnicity.ethnicityId, ...values });
        message.success('更新成功');
      } else {
        await ethnicitiesAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchEthnicities(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchEthnicities(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '民族名称', dataIndex: 'ethnicityName', key: 'ethnicityName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.ethnicityId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加民族</Button>
      <Table
        columns={columns}
        dataSource={ethnicities}
        rowKey="ethnicityId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentEthnicity ? '编辑民族' : '添加民族'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentEthnicity}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="ethnicityName"
            label="民族名称"
            rules={[{ required: true, message: '请输入民族名称' }]}
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

export default EthnicityManage;