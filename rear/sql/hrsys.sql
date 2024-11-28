CREATE
    DATABASE IF NOT EXISTS hrsys;

USE hrsys;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    user_id  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) UNIQUE COMMENT '用户名',
    password VARCHAR(255) COMMENT '密码',
    role_id  BIGINT DEFAULT 6 COMMENT '角色ID'
    -- FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE SET DEFAULT
) COMMENT '用户表';

DROP TABLE IF EXISTS organization;
CREATE TABLE organization
(
    org_id    BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '机构ID',
    org_name  VARCHAR(50) COMMENT '机构名称',
    parent_id BIGINT DEFAULT 0
) COMMENT '组织机构表';

DROP TABLE IF EXISTS hr_record;
CREATE TABLE hr_record
(
    record_id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '档案ID',
    user_id                BIGINT COMMENT '用户ID',
    org_id1               BIGINT COMMENT 'I级机构ID',
    org_id2               BIGINT COMMENT 'II级机构ID',
    org_id3               BIGINT COMMENT 'III级机构ID',
    category_id            BIGINT COMMENT '职位分类ID',
    position_id            BIGINT COMMENT '职位ID',
    title_id               BIGINT COMMENT '职称ID',
    name                   VARCHAR(50) COMMENT '姓名',
    gender                 CHAR(1) COMMENT '性别（0：男，1：女）',
    email                  VARCHAR(100) COMMENT '电子邮件',
    phone                  VARCHAR(20) COMMENT '电话',
    qq                     VARCHAR(20) COMMENT 'QQ号码',
    mobile                 VARCHAR(20) COMMENT '手机',
    address                VARCHAR(255) COMMENT '住址',
    postal_code            VARCHAR(10) COMMENT '邮政编码',
    nationality            VARCHAR(50) COMMENT '国籍',
    birthplace             VARCHAR(50) COMMENT '出生地',
    birthdate              DATE COMMENT '出生日期',
    ethnicity_id           BIGINT COMMENT '民族ID',
    religion               VARCHAR(50) COMMENT '宗教信仰',
    political_affiliation  VARCHAR(50) COMMENT '政治面貌',
    id_number              VARCHAR(20) COMMENT '身份证号码',
    social_security_number VARCHAR(20) COMMENT '社会保障号码',
    age                    INT COMMENT '年龄',
    education_id           BIGINT COMMENT '学历ID',
    major                  VARCHAR(50) COMMENT '专业',
    salary_standard_id     BIGINT COMMENT '薪酬标准ID',
    bank_name              VARCHAR(100) COMMENT '开户行',
    account_number         VARCHAR(50) COMMENT '账号',
    skills                 TEXT COMMENT '特长',
    hobbies                TEXT COMMENT '爱好',
    personal_history       TEXT COMMENT '个人履历',
    family_info            TEXT COMMENT '家庭关系信息',
    remarks                TEXT COMMENT '备注',
    photo_url              VARCHAR(255) COMMENT '员工相片url',
    registrar              VARCHAR(50) NOT NULL DEFAULT '' COMMENT '登记人',
    registration_time      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记时间',
    status                 CHAR(1)              DEFAULT '0' COMMENT '状态（0：未复核，1：已复核，2：已删除）'
    -- FOREIGN KEY (user_id) REFERENCES user(user_id),
    -- FOREIGN KEY (salary_standard_id) REFERENCES salary_standards(standard_id),
    -- FOREIGN KEY (org_id) REFERENCES organizations(org_id),
    -- FOREIGN KEY (ethnicity_id) REFERENCES ethnicities(ethnicity_id),
    -- FOREIGN KEY (education_id) REFERENCES educations(education_id)
    -- FOREIGN KEY (category_id) REFERENCES position_categories(category_id),
    -- FOREIGN KEY (position_id) REFERENCES positions(position_id),
    -- FOREIGN KEY (title_id) REFERENCES titles(title_id)
) COMMENT '人力资源档案表';

-- 插入示例数据
INSERT INTO hr_record (record_id, user_id, org_id1, org_id2, org_id3, name, gender, email, phone, registration_time, registrar, position_id, title_id)
VALUES 
(202401010101, 1, 1, 1, 1, '张三', '0', 'zhangsan@example.com', '13800138001', '2024-01-01 09:00:00', 'admin', 1, 1),
(202402020202, 2, 2, 2, 2, '李四', '0', 'lisi@example.com', '13800138002', '2024-02-01 09:30:00', 'admin', 2, 2),
(202403030303, 3, 3, 3, 3, '王五', '1', 'wangwu@example.com', '13800138003', '2024-03-01 10:00:00', 'admin', 3, 1),
(202404040404, 4, 1, 2, 1, '赵六', '0', 'zhaoliu@example.com', '13800138004', '2024-04-01 10:30:00', 'admin', 1, 3),
(202405050505, 5, 2, 1, 2, '孙七', '1', 'sunqi@example.com', '13800138005', '2024-05-01 11:00:00', 'admin', 2, 2),
(202406060606, 6, 3, 2, 3, '周八', '0', 'zhouba@example.com', '13800138006', '2024-06-01 11:30:00', 'admin', 3, 1),
(202407070707, 7, 1, 3, 1, '吴九', '1', 'wujiu@example.com', '13800138007', '2024-07-01 12:00:00', 'admin', 1, 2),
(202408080808, 8, 2, 3, 2, '郑十', '0', 'zhengshi@example.com', '13800138008', '2024-08-01 12:30:00', 'admin', 2, 3),
(202409090909, 9, 3, 1, 3, '钱十一', '1', 'qianshiyi@example.com', '13800138009', '2024-09-01 13:00:00', 'admin', 3, 1),
(202410101010, 10, 1, 2, 2, '陈十二', '0', 'chenshier@example.com', '13800138010', '2024-10-01 13:30:00', 'admin', 1, 2);

DROP TABLE IF EXISTS salary_standard;
CREATE TABLE salary_standard
(
    standard_id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '薪酬标准编号',
    name                     VARCHAR(50) NOT NULL COMMENT '薪酬标准名称',
    creator                  VARCHAR(50) NOT NULL COMMENT '制定人',
    registrar                VARCHAR(50) NOT NULL DEFAULT '' COMMENT '登记人',
    registration_time        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记时间',
    base_salary              DECIMAL(10, 2)       DEFAULT 0.00 COMMENT '基本工资',
    transportation_allowance DECIMAL(10, 2)       DEFAULT 0.00 COMMENT '交通补助',
    lunch_allowance          DECIMAL(10, 2)       DEFAULT 0.00 COMMENT '午餐补助',
    communication_allowance  DECIMAL(10, 2)       DEFAULT 0.00 COMMENT '通信补助',
    pension_insurance        DECIMAL(10, 2) GENERATED ALWAYS AS (base_salary * 0.08) STORED COMMENT '养老保险',
    medical_insurance        DECIMAL(10, 2) GENERATED ALWAYS AS (base_salary * 0.02 + 3) STORED COMMENT '医疗保险',
    unemployment_insurance   DECIMAL(10, 2) GENERATED ALWAYS AS (base_salary * 0.005) STORED COMMENT '失业保险',
    housing_fund             DECIMAL(10, 2) GENERATED ALWAYS AS (base_salary * 0.08) STORED COMMENT '住房公积金',
    review_status            CHAR(1)              DEFAULT '0' COMMENT '复核状态（0：未复核，1：已复核）',
    review_comment           TEXT COMMENT '复核意见'
) COMMENT '薪酬标准表';

DROP TABLE IF EXISTS salary_payment;
CREATE TABLE salary_payment
(
    payment_id        BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '薪酬发放单ID',
    details_id        BIGINT COMMENT '明细ID',
    org_id1          BIGINT COMMENT 'I级机构ID',
    org_id2          BIGINT COMMENT 'II级机构ID',
    org_id3          BIGINT COMMENT 'III级机构ID',
    registrar         VARCHAR(50)    NOT NULL DEFAULT '' COMMENT '登记人',
    registration_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记时间',
    total_people      INT            NOT NULL DEFAULT 0 COMMENT '发放人数',
    total_base_salary DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '基本薪资总额',
    status            CHAR(1)                 DEFAULT '0' COMMENT '状态（0：未提交，1：已提交，2：已删除）'
    -- FOREIGN KEY (details_id) REFERENCES salary_details(details_id),
    -- FOREIGN KEY (org_id) REFERENCES organizations(org_id)
) COMMENT '薪酬发放登记表';

DROP TABLE IF EXISTS salary_detail;
CREATE TABLE salary_detail
(
    details_id   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '明细ID',
    user_id      BIGINT COMMENT '用户ID',
    standard_id  BIGINT COMMENT '薪酬标准ID',
    bonus        DECIMAL(10, 2) DEFAULT 0.00 COMMENT '奖励金额',
    deductions   DECIMAL(10, 2) DEFAULT 0.00 COMMENT '应扣奖金',
    total_amount DECIMAL(10, 2) COMMENT '薪酬总额'
    -- FOREIGN KEY (user_id) REFERENCES user(user_id),
    -- FOREIGN KEY (standard_id) REFERENCES salary_standards(standard_id)
) COMMENT '薪酬发放明细表';

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    role_id   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) UNIQUE COMMENT '角色名称'
) COMMENT '角色表';

INSERT INTO role (role_id, role_name)
VALUES (1, '管理员'),
       (2, '人事专员'),
       (3, '人事经理'),
       (4, '薪酬专员'),
       (5, '薪酬经理'),
       (6, '员工');

DROP TABLE IF EXISTS position_categorie;
CREATE TABLE position_categorie
(
    category_id   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职位分类ID',
    category_name VARCHAR(50) COMMENT '职位分类名称'
) COMMENT '职位分类表';

INSERT INTO position_categorie (category_id, category_name)
VALUES (1, '软件开发'),
       (2, '软件测试'),
       (3, '软件运维'),
       (4, '软件实施'),
       (5, '软件销售');

DROP TABLE IF EXISTS position;
CREATE TABLE position
(
    position_id   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职位ID',
    position_name VARCHAR(50) COMMENT '职位名称',
    category_id   BIGINT COMMENT '职位分类ID'
    -- FOREIGN KEY (category_id) REFERENCES position_categories(category_id)
) COMMENT '职位表';

INSERT INTO position (position_id, position_name, category_id)
VALUES (1, '初级开发工程师', 1),
       (2, '中级开发工程师', 1),
       (3, '高级开发工程师', 1),
       (4, '技术主管', 1),
       (5, '技术经理', 1),
       (6, '初级测试工程师', 2),
       (7, '中级测试工程师', 2),
       (8, '高级测试工程师', 2),
       (9, '测试主管', 2),
       (10, '测试经理', 2),
       (11, '初级运维工程师', 3),
       (12, '中级运维工程师', 3),
       (13, '高级运维工程师', 3),
       (14, '运维主管', 3),
       (15, '运维经理', 3),
       (16, '初级实施工程师', 4),
       (17, '中级实施工程师', 4),
       (18, '高级实施工程师', 4),
       (19, '实施主管', 4),
       (20, '实施经理', 4),
       (21, '初级销售代表', 5),
       (22, '中级销售代表', 5),
       (23, '高级销售代表', 5),
       (24, '销售主管', 5),
       (25, '销售经理', 5);

DROP TABLE IF EXISTS title;
CREATE TABLE title
(
    title_id   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职称ID',
    title_name VARCHAR(50) COMMENT '职称'
) COMMENT '职称表';

INSERT INTO title (title_id, title_name)
VALUES (1, '助理'),
       (2, '工程师'),
       (3, '高级工程师'),
       (4, '专家'),
       (5, '教授');

DROP TABLE IF EXISTS ethnicitie;
CREATE TABLE ethnicitie
(
    ethnicity_id   BIGINT PRIMARY KEY COMMENT '民族ID',
    ethnicity_name VARCHAR(50) COMMENT '民族名称'
) COMMENT '民族表';

INSERT INTO ethnicitie (ethnicity_id, ethnicity_name)
VALUES (1, '汉族'),
       (2, '蒙古族'),
       (3, '回族'),
       (4, '藏族'),
       (5, '维吾尔族'),
       (6, '苗族'),
       (7, '彝族'),
       (8, '壮族'),
       (9, '布依族'),
       (10, '朝鲜族'),
       (11, '满族'),
       (12, '侗族'),
       (13, '瑶族'),
       (14, '白族'),
       (15, '土家族'),
       (16, '哈尼族'),
       (17, '哈萨克族'),
       (18, '傣族'),
       (19, '黎族'),
       (20, '傈僳族'),
       (21, '佤族'),
       (22, '畲族'),
       (23, '高山族'),
       (24, '拉祜族'),
       (25, '水族'),
       (26, '东乡族'),
       (27, '纳西族'),
       (28, '景颇族'),
       (29, '柯尔克孜族'),
       (30, '土族'),
       (31, '达斡尔族'),
       (32, '仫佬族'),
       (33, '羌族'),
       (34, '布朗族'),
       (35, '撒拉族'),
       (36, '毛南族'),
       (37, '仡佬族'),
       (38, '锡伯族'),
       (39, '阿昌族'),
       (40, '普米族'),
       (41, '塔吉克族'),
       (42, '怒族'),
       (43, '乌孜别克族'),
       (44, '俄罗斯族'),
       (45, '鄂温克族'),
       (46, '德昂族'),
       (47, '保安族'),
       (48, '裕固族'),
       (49, '京族'),
       (50, '塔塔尔族'),
       (51, '独龙族'),
       (52, '鄂伦春族'),
       (53, '赫哲族'),
       (54, '门巴族'),
       (55, '珞巴族'),
       (56, '基诺族');

DROP TABLE IF EXISTS education;
CREATE TABLE education
(
    education_id   BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '学历ID',
    education_name VARCHAR(50) COMMENT '学历名称'
) COMMENT '学历表';

INSERT INTO education (education_name)
VALUES ('小学'),
       ('初中'),
       ('高中'),
       ('中专'),
       ('大专'),
       ('本科'),
       ('硕士'),
       ('博士'),
       ('博士后');