import React, { useState, useEffect } from 'react';
import { titleAPI } from '../../../../api/modules/system';
import { Table, Button, Modal, Form, Input, Space, message } from 'antd';

const TitleManage = () => {
  const [titles, setTitles] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentTitle, setCurrentTitle] = useState(null);
  const [pagination, setPagination] = useState({ current: 1, pageSize: 10, total: 0 });

  useEffect(() => {
    fetchTitles();
  }, []);

  const fetchTitles = async (page, pageSize) => {
    try {
      const response = await titleAPI.list({ current: page, size: pageSize });
      if (response.code === 200) {
        setTitles(response.data.records);
        setPagination({
          current: response.data.current,
          pageSize: response.data.size,
          total: response.data.total,
        });
      } else {
        message.error('获取职称列表失败');
      }
    } catch (error) {
      message.error('获取职称列表失败');
    }
  };

  const handleAdd = () => {
    setCurrentTitle(null);
    setIsModalVisible(true);
  };

  const handleEdit = (title) => {
    setCurrentTitle(title);
    setIsModalVisible(true);
  };

  const handleDelete = async (id) => {
    try {
      await titleAPI.delete(id);
      message.success('删除成功');
      fetchTitles(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('删除失败');
    }
  };

  const handleOk = async (values) => {
    try {
      if (currentTitle) {
        await titleAPI.update({ titleId: currentTitle.titleId, ...values });
        message.success('更新成功');
      } else {
        await titleAPI.add(values);
        message.success('添加成功');
      }
      setIsModalVisible(false);
      fetchTitles(pagination.current, pagination.pageSize);
    } catch (error) {
      message.error('操作失败');
    }
  };

  const handleTableChange = (pagination) => {
    fetchTitles(pagination.current, pagination.pageSize);
  };

  const columns = [
    { title: '职称名称', dataIndex: 'titleName', key: 'titleName' },
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.titleId)}>删除</Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>添加职称</Button>
      <Table
        columns={columns}
        dataSource={titles}
        rowKey="titleId"
        pagination={{
          current: pagination.current,
          pageSize: pagination.pageSize,
          total: pagination.total,
          showSizeChanger: true,
        }}
        onChange={handleTableChange}
      />
      <Modal
        title={currentTitle ? '编辑职称' : '添加职称'}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        footer={null}
      >
        <Form
          initialValues={currentTitle}
          onFinish={handleOk}
          layout="vertical"
        >
          <Form.Item
            name="titleName"
            label="职称名称"
            rules={[{ required: true, message: '请输入职称名称' }]}
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

export default TitleManage;