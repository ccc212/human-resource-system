import React, { useState, useEffect } from 'react';
import { positionAPI, positionCategorieAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Select, Space, message } from 'antd';

const { Option } = Select;

const PositionManage = () => {
  const [positions, setPositions] = useState([]);
  const [categories, setCategories] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentPosition, setCurrentPosition] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchPositions(pagination.current, pagination.pageSize);
    fetchCategories();
  }, []);

  const fetchPositions = async (page, pageSize) => {
    try {
      const response = await positionAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setPositions(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取职位列表失败');
      }
    } catch (error) {
      message.error('获取职位列表失败');
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await positionCategorieAPI.list();
      if (response.code === 200) {
        setCategories(response.data.records);
      } else {
        message.error('获取职位分类列表失败');
      }
    } catch (error) {
      message.error('获取职位分类列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentPosition(null);
    setIsModalVisible(true);
  };

  const handleEdit = (position) => {
    setCurrentPosition(position);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await positionAPI.delete(id);
      message.success('删除成功');
      fetchPositions(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentPosition) {
        await positionAPI.update({ positionId: currentPosition.positionId, ...values });
        message.success('更新成功');
      } else {
        await positionAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchPositions(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchPositions(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '职位名称', dataIndex: 'positionName', key: 'positionName' },
    { title: '职位分类', dataIndex: 'categoryName', key: 'categoryName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.positionId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加职位</Button>
      <Table
        columns={columns}
        dataSource={positions}
        rowKey="positionId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentPosition ? '编辑职位' : '添加职位'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentPosition}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="positionName"
            label="职位名称"
            rules={[{ required: true, message: '请输入职位名称' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="categoryId"
            label="职位分类"
            rules={[{ required: true, message: '请选择职位分类' }]}
          >
            <Select placeholder="请选择职位分类">
              {categories.map(category => (
                <Option key={category.categoryId} value={category.categoryId}>
                  {category.categoryName}
                </Option>
              ))}
            </Select>
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

export default PositionManage;