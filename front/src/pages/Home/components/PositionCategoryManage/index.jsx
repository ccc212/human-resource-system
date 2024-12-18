import React, { useState, useEffect } from 'react';
import { positionCategorieAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Space, message } from 'antd';

const PositionCategorieManage = () => {
  const [categories, setCategories] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentCategory, setCurrentCategory] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchCategories(pagination.current, pagination.pageSize);
  }, []);

  const fetchCategories = async (page, pageSize) => {
    try {
      const response = await positionCategorieAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setCategories(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取职位分类列表失败');
      }
    } catch (error) {
      message.error('获取职位分类列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentCategory(null);
    setIsModalVisible(true);
  };

  const handleEdit = (category) => {
    setCurrentCategory(category);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await positionCategorieAPI.delete(id);
      message.success('删除成功');
      fetchCategories(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentCategory) {
        await positionCategorieAPI.update({ categoryId: currentCategory.categoryId, ...values });
        message.success('更新成功');
      } else {
        await positionCategorieAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchCategories(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchCategories(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '分类名称', dataIndex: 'categoryName', key: 'categoryName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.categoryId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加分类</Button>
      <Table
        columns={columns}
        dataSource={categories}
        rowKey="categoryId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentCategory ? '编辑分类' : '添加分类'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentCategory}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="categoryName"
            label="分类名称"
            rules={[{ required: true, message: '请输入分类名称' }]}
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

export default PositionCategorieManage;