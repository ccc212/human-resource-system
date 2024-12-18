import React, { useState, useEffect } from 'react';
import { roleAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Space, message } from 'antd';

const RoleManage = () => {
  const [roles, setRoles] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentRole, setCurrentRole] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchRoles(pagination.current, pagination.pageSize);
  }, []);

  const fetchRoles = async (page, pageSize) => {
    try {
      const response = await roleAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setRoles(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取角色列表失败');
      }
    } catch (error) {
      message.error('获取角色列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentRole(null);
    setIsModalVisible(true);
  };

  const handleEdit = (role) => {
    setCurrentRole(role);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await roleAPI.delete(id);
      message.success('删除成功');
      fetchRoles(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentRole) {
        await roleAPI.update({ roleId: currentRole.roleId, ...values });
        message.success('更新成功');
      } else {
        await roleAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchRoles(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchRoles(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '角色名称', dataIndex: 'roleName', key: 'roleName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.roleId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加角色</Button>
      <Table
        columns={columns}
        dataSource={roles}
        rowKey="roleId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentRole ? '编辑角色' : '添加角色'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentRole}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="roleName"
            label="角色名称"
            rules={[{ required: true, message: '请输入角色名称' }]}
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

export default RoleManage;