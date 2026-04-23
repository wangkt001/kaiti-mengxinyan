# 校园二手交易系统

基于 Spring Boot + Vue 3 的校园二手交易平台，支持商品发布、购买、订单管理、评价等功能。

## 技术栈

**后端**
- Spring Boot 4.0.3
- Java 17
- MyBatis
- MySQL

**前端**
- Vue 3
- Element Plus
- Vue Router
- Pinia
- TypeScript

## 项目结构

```
├── second-hand-trade-backend/    # 后端项目
│   ├── src/main/java/
│   │   └── com/campus/secondhand/
│   │       ├── config/           # 配置类
│   │       ├── controller/       # 控制器
│   │       ├── dao/              # 数据访问层
│   │       ├── model/            # 实体类
│   │       └── service/          # 业务逻辑层
│   └── src/main/resources/
│       ├── application.properties # 配置文件
│       └── schema.sql             # 数据库初始化脚本
├── second-hand-trade-frontend/   # 前端项目
│   └── src/
│       ├── api/                  # API 接口
│       ├── components/           # 公共组件
│       ├── router/               # 路由配置
│       ├── store/                # 状态管理
│       └── views/                # 页面组件
└── README.md
```

## 功能模块

- **用户管理**：注册、登录、个人信息编辑、头像上传
- **商品管理**：发布、编辑、删除（逻辑删除）、商品列表、商品详情
- **交易管理**：下单、订单状态流转（待发货/运输中/已收货）、库存管理
- **订单管理**：买家订单、卖家订单、商品图片展示
- **系统管理**：用户管理、商品管理、订单管理（管理员）

## 快速开始

### 环境要求

- JDK 17+
- Node.js 16+
- MySQL 5.7+

### 后端启动

```bash
cd second-hand-trade-backend
mvn spring-boot:run
```

后端默认运行在 `http://localhost:8080`

### 前端启动

```bash
cd second-hand-trade-frontend
npm install
npm run dev
```

前端默认运行在 `http://localhost:8081`

### 数据库配置

默认使用的数据库名为 `employment_mengxinyan`，应用首次启动时会自动创建该数据库，并执行 `schema.sql` 完成表结构初始化。

如需修改数据库连接，可调整 `second-hand-trade-backend/src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
```

数据库表会在应用启动时自动通过 `schema.sql` 初始化。

## 主要 API

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/user/login` | POST | 用户登录 |
| `/api/user/register` | POST | 用户注册 |
| `/api/user/upload-avatar` | POST | 上传头像 |
| `/api/goods/list` | GET | 商品列表 |
| `/api/goods/add` | POST | 发布商品 |
| `/api/goods/update` | PUT | 编辑商品 |
| `/api/goods/delete/{id}` | DELETE | 删除商品 |
| `/api/order/create` | POST | 创建订单 |
| `/api/order/update-status` | PUT | 更新订单状态 |
