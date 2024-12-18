import { Layout, Menu, theme, Avatar, Dropdown, Tabs } from 'antd';
import { useState, useEffect } from 'react';
import { 
  UserOutlined, 
  HomeOutlined,
  FileOutlined,
  TeamOutlined,
  SettingOutlined,
  LogoutOutlined,
  FormOutlined,
  PayCircleOutlined,
  ProfileOutlined,
  AccountBookOutlined,
  BarChartOutlined,
  AppstoreOutlined,
  AuditOutlined,
} from '@ant-design/icons';
import { useNavigate, Outlet } from 'react-router-dom';
import EmployeeForm from './components/EmployeeForm/EmployeeForm';
import EmployeeList from './components/EmployeeList/EmployeeList';
import SalaryStandard from './components/SalaryStandard';
import SalaryPayment from './components/SalaryPayment';
import SalaryStatistics from './components/SalaryStatistics';
import SalaryItems from './components/SalaryItems';
import SalaryStandardReview from './components/SalaryStandard/Review';
import RoleManage from './components/RoleManage';
import OrganizationManage from './components/OrganizationManage';
import PositionCategoryManage from './components/PositionCategoryManage';
import PositionManage from './components/PositionManage';
import TitleManage from './components/TitleManage';
import EducationManage from './components/EducationManage';
import EthnicityManage from './components/EthnicityManage';
import './style.css';

const { Header, Sider, Content } = Layout;

const Home = () => {
  const navigate = useNavigate();
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const [activeTab, setActiveTab] = useState('employee-add');
  const [username, setUsername] = useState('');

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    if (storedUsername) {
      setUsername(storedUsername);
    }
  }, []);

  const userMenuItems = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: '个人信息',
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: '设置',
    },
    {
      type: 'divider',
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      onClick: () => navigate('/login'),
    },
  ];

  const menuItems = [
    {
      key: 'dashboard',
      icon: <HomeOutlined />,
      label: '首页概览',
      onClick: () => navigate('/dashboard'),
    },
    {
      key: 'employee',
      icon: <TeamOutlined />,
      label: '人力资源',
      children: [
        {
          key: 'employee-add',
          icon: <FormOutlined />,
          label: '员工录入',
          onClick: () => setActiveTab('employee-add'),
        },
        {
          key: 'employee-list',
          icon: <FileOutlined />,
          label: '员工列表',
          onClick: () => setActiveTab('employee-list'),
        },
      ],
    },
    {
      key: 'salary',
      icon: <PayCircleOutlined />,
      label: '薪酬管理',
      children: [
        {
          key: 'salary-items',
          icon: <AppstoreOutlined />,
          label: '薪��项目',
          onClick: () => setActiveTab('salary-items'),
        },
        {
          key: 'salary-standard',
          icon: <ProfileOutlined />,
          label: '薪酬标准',
          onClick: () => setActiveTab('salary-standard'),
        },
        {
          key: 'salary-payment',
          icon: <AccountBookOutlined />,
          label: '薪酬发放',
          onClick: () => setActiveTab('salary-payment'),
        },
        {
          key: 'salary-statistics',
          icon: <BarChartOutlined />,
          label: '薪酬统计',
          onClick: () => setActiveTab('salary-statistics'),
        },
        {
          key: 'salary-review',
          icon: <AuditOutlined />,
          label: '薪酬审核',
          onClick: () => setActiveTab('salary-review'),
        },
      ],
    },
    {
      key: 'system',
      icon: <SettingOutlined />,
      label: '系统管理',
      children: [
        {
          key: 'role-manage',
          icon: <AppstoreOutlined />,
          label: '角色管理',
          onClick: () => setActiveTab('role-manage'),
        },
        {
          key: 'organization-manage',
          icon: <AppstoreOutlined />,
          label: '组织机构管理',
          onClick: () => setActiveTab('organization-manage'),
        },
        {
          key: 'position-category-manage',
          icon: <AppstoreOutlined />,
          label: '职位分类管理',
          onClick: () => setActiveTab('position-category-manage'),
        },
        {
          key: 'position-manage',
          icon: <AppstoreOutlined />,
          label: '职位管理',
          onClick: () => setActiveTab('position-manage'),
        },
        {
          key: 'title-manage',
          icon: <AppstoreOutlined />,
          label: '职称管理',
          onClick: () => setActiveTab('title-manage'),
        },
        {
          key: 'education-manage',
          icon: <AppstoreOutlined />,
          label: '学历管理',
          onClick: () => setActiveTab('education-manage'),
        },
        {
          key: 'ethnicities-manage',
          icon: <AppstoreOutlined />,
          label: '民族管理',
          onClick: () => setActiveTab('ethnicities-manage'),
        }
      ],
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: '系统设置',
      onClick: () => navigate('/settings'),
    },
  ];

  const tabItems = [
    {
      key: 'employee-add',
      label: '员工信息录入',
      children: <EmployeeForm />
    },
    {
      key: 'employee-list',
      label: '员工列表',
      children: <EmployeeList />
    },
    {
      key: 'salary-standard',
      label: '薪酬标准',
      children: <SalaryStandard />
    },
    {
      key: 'salary-payment',
      label: '薪酬发放',
      children: <SalaryPayment />
    },
    {
      key: 'salary-statistics',
      label: '薪酬统计',
      children: <SalaryStatistics />
    },
    {
      key: 'salary-items',
      label: '薪资项目管理',
      children: <SalaryItems />
    },
    {
      key: 'salary-review',
      label: '薪酬标准审核',
      children: <SalaryStandardReview />
    },
    {
      key: 'role-manage',
      label: '角色管理',
      children: <RoleManage />
    },
    {
      key: 'organization-manage',
      label: '组织机构管理',
      children: <OrganizationManage />
    },
    {
      key: 'position-category-manage',
      label: '职位分类管理',
      children: <PositionCategoryManage />
    },
    {
      key: 'position-manage',
      label: '职位管理',
      children: <PositionManage />
    },
    {
      key: 'title-manage',
      label: '职称管理',
      children: <TitleManage />
    },
    {
      key: 'education-manage',
      label: '学历管理',
      children: <EducationManage />
    },
    {
      key: 'ethnicities-manage',
      label: '民族管理',
      children: <EthnicityManage />
    }
  ].map(item => ({
    ...item,
    label: typeof item.label === 'object' ? item.label.name || item.label : item.label
  }));

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider
        breakpoint="lg"
        collapsedWidth="0"
        className="site-sider"
      >
        <div className="logo">人力资源管理系统</div>
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={['employee-add']}
          defaultOpenKeys={['employee']}
          items={menuItems}
        />
      </Sider>
      <Layout>
        <Header className="site-header" style={{ background: colorBgContainer }}>
          <div className="header-content">
            <span className="welcome-text">欢迎回来，{username}</span>
            <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
              <Avatar icon={<UserOutlined />} style={{ cursor: 'pointer' }} />
            </Dropdown>
          </div>
        </Header>
        <Content className="site-content">
          <Tabs 
            activeKey={activeTab} 
            onChange={setActiveTab} 
            type="card"
            destroyInactiveTabPane
            items={tabItems}
          />
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  );
};

export default Home; 