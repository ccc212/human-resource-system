import React, { useState, useEffect, useCallback } from 'react';
import { Table, Button, Modal, Form, Input, Space, message, Switch, InputNumber } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { salaryItemsAPI } from '../../../../api';
import debounce from 'lodash/debounce';

const SalaryItems = () => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [form] = Form.useForm();
  const [editingId, setEditingId] = useState(null);

  // 获取薪资项目列表
  const fetchItems = async () => {
    try {
      setLoading(true);
      const data = await salaryItemsAPI.getItems();
      setItems(Array.isArray(data) ? data : []);
    } catch (error) {
      message.error('获取薪资项目列表失败');
      setItems([]);
    } finally {
      setLoading(false);
    }
  };

  // 使用useCallback和debounce创建防抖的fetchItems
  const debouncedFetchItems = useCallback(
    debounce(() => {
      fetchItems();
    }, 500),
    []
  );

  useEffect(() => {
    fetchItems();
    return () => {
      debouncedFetchItems.cancel(); // 清理防抖
    };
  }, []);

  // 处理添加
  const handleAdd = () => {
    setEditingId(null);
    form.resetFields();
    setIsModalVisible(true);
  };

  // 处理编辑
  const handleEdit = (record) => {
    setEditingId(record.itemId);
    form.setFieldsValue({
      itemName: record.itemName,
      isFixed: record.isFixed,
      rate: record.rate
    });
    setIsModalVisible(true);
  };

  // 处理删除
  const handleDelete = async (id) => {
    try {
      const success = await salaryItemsAPI.deleteItem(id);
      if (success) {
        message.success('删除成功');
        // 乐观更新：立即从列表中移除该项
        setItems(prevItems => prevItems.filter(item => item.itemId !== id));
        // 防抖更新
        debouncedFetchItems();
      } else {
        message.error('删除失败');
      }
    } catch (error) {
      message.error('删除失败');
    }
  };

  // 处理模态框确认
  const handleModalOk = async () => {
    try {
      const values = await form.validateFields();
      if (editingId) {
        const updatedItem = await salaryItemsAPI.updateItem(editingId, values);
        if (updatedItem) {
          message.success('更新成功');
          setIsModalVisible(false);
          // 乐观更新：立即更新列表中的项
          setItems(prevItems => 
            prevItems.map(item => 
              item.itemId === editingId ? { ...item, ...values } : item
            )
          );
          // 防抖更新
          debouncedFetchItems();
        } else {
          message.error('更新失败');
        }
      } else {
        const newItem = await salaryItemsAPI.addItem(values);
        if (newItem) {
          message.success('添加成功');
          setIsModalVisible(false);
          // 乐观更新：立即添加到列表
          setItems(prevItems => [...prevItems, newItem]);
          // 防抖更新
          debouncedFetchItems();
        } else {
          message.error('添加失败');
        }
      }
    } catch (error) {
      message.error(editingId ? '更新失败' : '添加失败');
    }
  };

  // 列配置
  const columns = [
    {
      title: '项目名称',
      dataIndex: 'itemName',
      key: 'itemName',
    },
    {
      title: '是否固定项',
      dataIndex: 'isFixed',
      key: 'isFixed',
      render: (isFixed) => (
        <Switch checked={isFixed} disabled />
      )
    },
    {
      title: '比率',
      dataIndex: 'rate',
      key: 'rate',
      render: (rate) => `${rate * 100}%`
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          >
            编辑
          </Button>
          <Button 
            type="link" 
            danger 
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record.itemId)}
          >
            删除
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div style={{ padding: '24px' }}>
      <div style={{ marginBottom: 16 }}>
        <Button 
          type="primary" 
          icon={<PlusOutlined />} 
          onClick={handleAdd}
        >
          添加薪资项目
        </Button>
      </div>
      
      <Table 
        columns={columns} 
        dataSource={items} 
        rowKey="itemId" 
        loading={loading}
      />

      <Modal
        title={editingId ? '编辑薪资项目' : '添加薪资项目'}
        open={isModalVisible}
        onOk={handleModalOk}
        onCancel={() => setIsModalVisible(false)}
      >
        <Form
          form={form}
          layout="vertical"
          initialValues={{
            isFixed: false,
            rate: 1
          }}
        >
          <Form.Item
            name="itemName"
            label="项目名称"
            rules={[{ required: true, message: '请输入项目名称' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="isFixed"
            label="是否固定项"
            valuePropName="checked"
          >
            <Switch />
          </Form.Item>
          <Form.Item
            name="rate"
            label="比率"
            rules={[{ required: true, message: '请输入比率' }]}
          >
            <InputNumber 
              min={0} 
              max={2} 
              step={0.01} 
              formatter={value => `${value * 100}%`}
              parser={value => value.replace('%', '') / 100}
              style={{ width: '100%' }}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default SalaryItems;