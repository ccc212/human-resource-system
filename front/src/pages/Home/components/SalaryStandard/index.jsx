import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, Space, DatePicker, message, InputNumber, Spin, Divider, Select } from 'antd';
import moment from 'moment';
import { salaryStandardAPI } from '../../../../api/modules/salary';

// const MOCK_DATA = [
//   {
//     standardId: "266611412174897152",
//     salaryStandardName: "测试薪酬标准1",
//     creator: "张三",
//     registrar: "李四",
//     reviewer: "王五",
//     registrationTime: [2024, 12, 17, 0, 0],
//     reviewStatus: "待审核",
//     items: [
//       {
//         itemID: "266611412174897155",
//         standardId: 266611412174897152,
//         name: "基本工资",
//         account: 5000.00
//       },
//       {
//         itemID: "266611412174897154",
//         standardId: 266611412174897152,
//         name: "绩效奖金",
//         account: 2000.00
//       },
//       {
//         itemID: "266611412174897153",
//         standardId: 266611412174897152,
//         name: "交通补贴",
//         account: 500.00
//       }
//     ]
//   },
//   {
//     standardId: "266611484412346368",
//     salaryStandardName: "测试薪酬标准2",
//     creator: "赵六",
//     registrar: "钱七",
//     reviewer: "孙八",
//     registrationTime: [2024, 12, 17, 0, 0],
//     reviewStatus: "已审核",
//     items: [
//       {
//         itemID: "266611484412346371",
//         standardId: 266611484412346368,
//         name: "基本工资",
//         account: 6000.00
//       },
//       {
//         itemID: "266611484412346370",
//         standardId: 266611484412346368,
//         name: "绩效奖金",
//         account: 3000.00
//       }
//     ]
//   }
// ];

const SalaryStandard = () => {
  // 状态定义
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isItemModalVisible, setIsItemModalVisible] = useState(false);
  const [editingRecord, setEditingRecord] = useState(null);
  const [editingItem, setEditingItem] = useState(null);
  const [selectedStandard, setSelectedStandard] = useState(null);
  const [form] = Form.useForm();
  const [itemForm] = Form.useForm();
  const [activeTab, setActiveTab] = useState('salary-standard');
  const[fixed_items,setFixed_items]=useState([])
  const[unfixed_items,setUFixed_items]=useState([])
  const [standardItems, setStandardItems] = useState([]); // 用于存储当前编辑的标准项目
  
  // 获取数据的方法
  const fetchSalaryStandards = async () => {
    try {
      setLoading(true);
      // 调用API获取数据
      const response = await salaryStandardAPI.getAllStandards();
      
      // 确保返回的数据是数组
      if (response && Array.isArray(response)) {
        setData(response);
      } else {
        throw new Error('返回数据格式错误');
      }
    } catch (error) {
      message.error('获取薪酬标准列表失败：' + error.message);
      setData([]); // 出错时设置为空数组
    } finally {
      setLoading(false);
    }
  };

  // 在组件加载时获取数据
  useEffect(() => {
    fetchSalaryStandards();
  }, []);

  // 初始化加载数据
  useEffect(() => {
    if (activeTab === 'salary-standard') {
      fetchData();
      getFixed_items()
      getUnFixed_items()
    }
  }, [activeTab]);
  const getFixed_items=async()=>{
    const response=await salaryStandardAPI.getFixedItems()
    if(response&&Array.isArray(response)){
      setFixed_items(response)
      console.log(fixed_items)
    }
  }
  const getUnFixed_items=async()=>{
    const response=await salaryStandardAPI.getUnFixedItems()
    if(response&&Array.isArray(response)){
      setUFixed_items(response)
      console.log(unfixed_items)
    }
  }
  // 获取数据列表
  const fetchData = async () => {
    try {
      setLoading(true);
      const response = await salaryStandardAPI.getAllStandards();
   
      if (response && Array.isArray(response)) {
        setData(response);
     
      } else {
        throw new Error('数据格式错误');
      }
    } catch (error) {
      message.error('获取数据失败: ' + error.message);
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  // 表格列定义
  const columns = [
    {
      title: '标准编号',
      dataIndex: 'standardId',
      key: 'standardId',
    },
    {
      title: '标准名',
      dataIndex: 'salaryStandardName',
      key: 'salaryStandardName',
    },
    {
      title: '创建人',
      dataIndex: 'creator',
      key: 'creator',
    },
    {
      title: '状态',
      dataIndex: 'reviewStatus',
      key: 'reviewStatus',
    },
    {
      title: '项目总额',
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
          <Button onClick={() => handleEdit(record)}>编辑</Button>
          <Button danger onClick={() => handleDelete(record.standardId)}>删除</Button>
          <Button onClick={() => handleViewItems(record)}>查看项目</Button>
        </Space>
      ),
    },
  ];

  // 处理新增
  const handleAdd = () => {
    setEditingRecord(null);
    form.resetFields();
    // 根据新的数据格式初始化固定项目
    setStandardItems(fixed_items.map(item => ({
      ...item,
      itemID: item.itemId,
      name: item.itemName,
      account: 0
    })));
    setIsModalVisible(true);
  };

  // 处理编辑
  const handleEdit = (record) => {
    setEditingRecord(record);
    form.setFieldsValue({
      ...record,
      registrationTime: moment(record.registrationTime.join('-'))
    });
    setIsModalVisible(true);
  };

  // 处理删除
  const handleDelete = (standardId) => {
    Modal.confirm({
      title: '确认删除？',
      onOk: async () => {
        try {
          setLoading(true);
          // TODO: 实现删除API
          await fetchData();
          message.success('删除成功');
        } catch (error) {
          message.error('删除失败');
        }
      },
    });
  };

  // 处理查看项目
  const handleViewItems = (record) => {
    setSelectedStandard(record);
    setIsItemModalVisible(true);
  };

  // 处理项目编辑
  const handleEditItem = (item) => {
    setEditingItem(item);
    itemForm.setFieldsValue(item);
  };

  // 处理项目删除
  const handleDeleteItem = (itemId) => {
    const newItems = selectedStandard.items.filter(item => item.itemID !== itemId);
    setSelectedStandard({
      ...selectedStandard,
      items: newItems
    });
    message.success('删除成功');
  };

  // 处理项目提交
  const handleItemSubmit = async () => {
    try {
      const values = await itemForm.validateFields();
      const newItem = {
        ...values,
        itemID: editingItem ? editingItem.itemID : Date.now().toString(),
        standardId: selectedStandard.standardId
      };

      const updatedItems = editingItem
        ? selectedStandard.items.map(item => 
            item.itemID === editingItem.itemID ? newItem : item
          )
        : [...selectedStandard.items, newItem];

      setSelectedStandard({
        ...selectedStandard,
        items: updatedItems
      });

      setEditingItem(null);
      itemForm.resetFields();
      message.success('操作成功');
    } catch (error) {
      message.error('操作失败');
    }
  };

  // 添加一个计算固定项目金额的函数
  const calculateFixedItemAmount = (baseAmount, rate) => {
    return baseAmount * rate;
  };

  // 表单提交
  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      
      // 验证基本工资是否已填写
      const baseItem = standardItems.find(item => item.name === '基本工资');
      if (!baseItem?.account) {
        message.error('请填写基本工资');
        return;
      }

      const formData = {
        ...values,
        registrationTime: values.registrationTime.format('YYYY-MM-DD').split('-').map(Number),
        items: standardItems.map(item => {
          if (item.isFixed && item.name !== '基本工资') {
            // 固定项目根据基本工资和比率计算金额
            return {
              itemId: item.itemID,
              itemName: item.name,
              account: calculateFixedItemAmount(baseItem.account, item.rate),
              isFixed: item.isFixed,
              rate: item.rate
            };
          }
          return {
            itemId: item.itemID,
            itemName: item.name,
            account: item.account,
            isFixed: item.isFixed,
            rate: item.rate
          };
        }),
        reviewStatus: 'PENDING'
      };

      setLoading(true);
      if (editingRecord) {
        formData.standardId = editingRecord.standardId;
        // 更新操作
      } else {
        // 新增操作
        await salaryStandardAPI.addSalaryStandard(formData);
      }
      
      setIsModalVisible(false);
      await fetchData();
      message.success(editingRecord ? '更新成功' : '添加成功');
    } catch (error) {
      message.error('操作失败: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  // 添加项目相关函数
  const handleAddItem = async () => {
    try {
      const values = await itemForm.validateFields();
      console.log('添加浮动项目的值:', values);
      
      const selectedItem = unfixed_items.find(item => item.itemId === values.itemID);
      if (!selectedItem) {
        throw new Error('未找到选择的项目');
      }

      // 检查是否已添加
      if (standardItems.some(item => item.itemID === selectedItem.itemId)) {
        message.error('该项目已添加');
        return;
      }

      const newItem = {
        itemID: selectedItem.itemId,
        name: selectedItem.itemName,
        account: values.account,
        isFixed: false,
        rate: selectedItem.rate || 0
      };

      console.log('新添加的浮动项目:', newItem);
      setStandardItems(prev => [...prev, newItem]);
      itemForm.resetFields();
      message.success('添加项目成功');
    } catch (error) {
      console.error('添加浮动项目失败:', error);
      message.error('添加项目失败: ' + error.message);
    }
  };

  // 项目表格列定义
  const itemColumns = [
    {
      title: '项目名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '金额',
      dataIndex: 'account',
      key: 'account',
    },
    {
      title: '操作',
      key: 'action',
      render: (_, record) => (
        <Space>
          <Button onClick={() => handleEditItem(record)}>编辑</Button>
          <Button danger onClick={() => handleDeleteItem(record.itemID)}>删除</Button>
        </Space>
      ),
    }
  ];

  useEffect(() => {
    const fetchAllData = async () => {
      setLoading(true);
      try {
        await Promise.all([
          fetchData(),
          getFixed_items(),
          getUnFixed_items()
        ]);
      } catch (error) {
        console.error('获取数据失败:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchAllData();
  }, []);

  return (
    <Spin spinning={loading}>
      <div>
        <Button type="primary" onClick={handleAdd} style={{ marginBottom: 16 }}>
          新增标准
        </Button>
        
        <Table 
          columns={columns}
          dataSource={data}
          rowKey="standardId"
        />

        <Modal
          title={editingRecord ? '编辑标准' : '新增标准'}
          visible={isModalVisible}
          onOk={handleSubmit}
          onCancel={() => setIsModalVisible(false)}
          width={800}
        >
          <Form form={form} layout="vertical">
            <Form.Item name="salaryStandardName" label="标准名称" rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item name="creator" label="创建人" rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item name="registrationTime" label="登记时间" rules={[{ required: true }]}>
              <DatePicker />
            </Form.Item>

            {/* 薪酬项目部分 */}
            <div style={{ marginTop: 24 }}>
              <h3>固定薪酬项目</h3>
              <Table
                columns={[
                  {
                    title: '项目名称',
                    dataIndex: 'name',
                    key: 'name',
                  },
                  {
                    title: '比率',
                    dataIndex: 'rate',
                    key: 'rate',
                    render: (rate) => `${(rate * 100).toFixed(0)}%`
                  },
                  {
                    title: '金额',
                    key: 'account',
                    render: (_, record, index) => {
                      if (record.name === '基本工资') {
                        // 基本工资可以直接输入
                        return (
                          <InputNumber
                            placeholder="请输入基本工资"
                            value={record.account}
                            onChange={(value) => {
                              const newItems = [...standardItems];
                              // 更新基本工资
                              newItems[index] = { ...newItems[index], account: value };
                              
                              // 更新其他固定项目的金额
                              newItems.forEach((item, idx) => {
                                if (idx !== index && item.isFixed) {
                                  item.account = calculateFixedItemAmount(value, item.rate);
                                }
                              });
                              
                              setStandardItems(newItems);
                            }}
                            style={{ width: '150px' }}
                          />
                        );
                      } else {
                        // 其他固定项目显示计算后的金额
                        const baseItem = standardItems.find(item => item.name === '基本工资');
                        const baseAmount = baseItem?.account || 0;
                        const amount = calculateFixedItemAmount(baseAmount, record.rate);
                        return `¥${amount.toFixed(2)}`;
                      }
                    },
                  }
                ]}
                dataSource={standardItems.filter(item => 
                  fixed_items.some(fixed => fixed.itemId === item.itemID)
                )}
                rowKey="itemID"
                pagination={false}
              />

              <Divider />
              
              <h3>添加浮动薪酬项目</h3>
              <Space align="baseline">
                <Form.Item 
                  name="tempItemID" 
                  rules={[{ required: false }]}
                >
                  <Select
                    placeholder="选择项目"
                    style={{ width: 200 }}
                    options={unfixed_items
                      .filter(item => 
                        // 确保项目不是固定项目
                        !item.isFixed &&
                        // 确保项目还未被添加
                        !standardItems.some(si => si.itemID === item.itemId)
                      )
                      .map(item => ({
                        label: item.itemName,
                        value: item.itemId,
                        item: item
                      }))}
                    onChange={(value, option) => {
                      console.log('选择的浮动项目:', option);
                      form.setFieldsValue({ tempItemID: value });
                    }}
                  />
                </Form.Item>
                <Form.Item 
                  name="tempAmount" 
                  rules={[{ required: false }]}
                >
                  <InputNumber 
                    placeholder="金额" 
                    style={{ width: 150 }} 
                    min={0}
                    formatter={value => `¥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                    parser={value => value.replace(/\¥\s?|(,*)/g, '')}
                  />
                </Form.Item>
                <Button 
                  type="primary"
                  onClick={() => {
                    const itemID = form.getFieldValue('tempItemID');
                    const amount = form.getFieldValue('tempAmount');
                    
                    if (!itemID || !amount) {
                      message.error('请选择项目并输入金额');
                      return;
                    }

                    const selectedItem = unfixed_items.find(item => item.itemId === itemID);
                    if (!selectedItem) {
                      message.error('未找到选择的项目');
                      return;
                    }

                    if (standardItems.some(item => item.itemID === selectedItem.itemId)) {
                      message.error('该项目已添加');
                      return;
                    }

                    const newItem = {
                      itemID: selectedItem.itemId,
                      name: selectedItem.itemName,
                      account: amount,
                      isFixed: false,
                      rate: selectedItem.rate || 0
                    };

                    setStandardItems(prev => [...prev, newItem]);
                    form.setFieldsValue({ tempItemID: undefined, tempAmount: undefined });
                    message.success('添加项目成功');
                  }}
                >
                  添加项目
                </Button>
              </Space>

              <Table
                style={{ marginTop: 16 }}
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
                  },
                  {
                    title: '操作',
                    key: 'action',
                    render: (_, record) => (
                      <Button 
                        danger 
                        onClick={() => {
                          const newItems = standardItems.filter(item => 
                            item.itemID !== record.itemID
                          );
                          setStandardItems(newItems);
                          message.success('删除成功');
                        }}
                      >
                        删除
                      </Button>
                    ),
                  }
                ]}
                dataSource={standardItems.filter(item => 
                  // 只显示非固定项目
                  !item.isFixed &&
                  // 确保项目存在于浮动项目列表中
                  unfixed_items.some(unfixed => unfixed.itemId === item.itemID)
                )}
                rowKey="itemID"
                pagination={false}
                locale={{ emptyText: '暂无浮动项目' }}
              />
            </div>
          </Form>
        </Modal>
        
        <Modal
          title="薪酬项目管理"
          visible={isItemModalVisible}
          onCancel={() => {
            setIsItemModalVisible(false);
            setSelectedStandard(null);
            setEditingItem(null);
            itemForm.resetFields();
          }}
          footer={null}
        >
          <Form form={itemForm} layout="inline" onFinish={handleItemSubmit}>
            <Form.Item name="name" rules={[{ required: true }]}>
              <Input placeholder="项目名称" />
            </Form.Item>
            <Form.Item name="account" rules={[{ required: true }]}>
              <InputNumber placeholder="金额" />
            </Form.Item>
            <Button type="primary" htmlType="submit">
              {editingItem ? '更新' : '添加'}
            </Button>
          </Form>
          
          <Table
            columns={itemColumns}
            dataSource={selectedStandard?.items || []}
            rowKey="itemID"
            style={{ marginTop: 16 }}
          />
        </Modal>
      </div>
    </Spin>
  );
};

export default SalaryStandard;