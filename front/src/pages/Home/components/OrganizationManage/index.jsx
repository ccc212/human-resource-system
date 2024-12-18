import React, { useState, useEffect } from 'react';
import { organizationAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Space, message } from 'antd';

const OrganizationManage = () => {
  const [organizations, setOrganizations] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentOrganization, setCurrentOrganization] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchOrganizations(pagination.current, pagination.pageSize);
  }, []);

  const fetchOrganizations = async (page, pageSize) => {
    try {
      const response = await organizationAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setOrganizations(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取组织机构列表失败');
      }
    } catch (error) {
      message.error('获取组织机构列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentOrganization(null);
    setIsModalVisible(true);
  };

  const handleEdit = (organization) => {
    setCurrentOrganization(organization);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await organizationAPI.delete(id);
      message.success('删除成功');
      fetchOrganizations(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentOrganization) {
        await organizationAPI.update({ organizationId: currentOrganization.organizationId, ...values });
        message.success('更新成功');
      } else {
        await organizationAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchOrganizations(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchOrganizations(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '组织名称', dataIndex: 'organizationName', key: 'organizationName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.organizationId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加组织</Button>
      <Table
        columns={columns}
        dataSource={organizations}
        rowKey="organizationId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentOrganization ? '编辑组织' : '添加组织'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentOrganization}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="organizationName"
            label="组织名称"
            rules={[{ required: true, message: '请输入组织名称' }]}
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

export default OrganizationManage;