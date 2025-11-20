# 用户管理系统 (User Management System)

一个基于Spring Boot和MyBatis-Plus的用户管理系统，提供了用户注册、登录以及管理员功能。
前端仓库链接//

## 技术栈

- Spring Boot 3.5.7
- MyBatis-Plus 3.5.14
- MySQL 8.0+
- Java 17
- Maven

## 功能特性

1. 用户注册
2. 用户登录
3. 用户搜索（仅管理员）
4. 删除用户（仅管理员）

## 数据库设计

系统使用MySQL数据库，包含一个用户表(users)：

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    avatarUrl VARCHAR(1024),
    gender INT DEFAULT 0,
    password VARCHAR(512) NOT NULL,
    email VARCHAR(255),
    isVaild INT DEFAULT 0,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    userStatus INT DEFAULT 0,
    userRole INT DEFAULT 0,
    isDelete INT DEFAULT 0
);
```

## 配置说明

在 `src/main/resources/application.yml` 文件中配置数据库连接：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/yurijundatabase
    username: yuri
    password: 09830983a
```

请根据实际环境修改数据库连接参数。

## API接口

### 用户注册
```
POST /user/register
```
请求体：
```json
{
  "userAccount": "用户名",
  "userPassword": "密码",
  "checkPassword": "确认密码"
}
```

### 用户登录
```
POST /user/login
```
请求体：
```json
{
  "userAccount": "用户名",
  "userPassword": "密码"
}
```

### 搜索用户（仅管理员）
```
GET /user/search?username={用户名}
```

### 删除用户（仅管理员）
```
POST /user/delete
```
请求体：
```json
{
  "id": 1
}
```

## 权限控制

- 普通用户：只能进行注册和登录操作
- 管理员用户：除了普通用户权限外，还可以搜索和删除用户
- 管理员权限通过[userRole=1]标识

## 运行项目

1. 克隆项目到本地
2. 修改数据库配置
3. 创建数据库和表
4. 使用Maven编译并运行项目：

```bash
mvn clean install
mvn spring-boot:run
```

或者打包运行：

```bash
mvn clean package
java -jar target/usermanage-0.0.1-SNAPSHOT.jar
```

## 注意事项

1. 确保MySQL数据库服务正在运行
2. 确保数据库连接配置正确
3. 管理员功能需要用户具有管理员权限(userRole=1)
