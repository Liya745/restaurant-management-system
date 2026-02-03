# 餐厅服务管理系统 API 接口文档 (v2.1)

## 1. 项目概述
本项目是餐厅服务管理系统的后端 API，基于 Spring Boot 框架开发。文档旨在为前端开发人员提供清晰、准确的接口调用说明，同时包含系统的过滤器、拦截器、AOP等技术组件的详细信息。

### 基础信息
- **Base URL**: `http://localhost:8080` (本地开发环境)
- **Content-Type**: `application/json` (除特殊说明外，POST 请求均使用 JSON 格式)
- **响应格式**: 统一返回 `Result` 对象。

---

## 2. 全局通用结构

### 2.1 统一响应 (Result)
所有接口执行完毕后都会返回该对象：
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `code` | Integer | 状态码：1 表示成功，0 表示失败 |
| `msg` | String | 提示信息或错误消息 |
| `data` | Object | 业务数据 (可能为数组、对象或 null) |

### 2.2 JWT 令牌认证
系统使用JWT (JSON Web Token) 进行身份验证：
- 登录成功后，服务器会返回包含用户信息的JWT令牌
- 后续请求需要在请求头中添加 `Authorization` 字段，值为 `Bearer {token}`
- JWT令牌有效期为2小时，过期后需要重新登录
- 前端应妥善保管JWT令牌，建议存储在localStorage或sessionStorage中


### 2.3 分页数据 (PageResult)
列表分页查询时，`Result.data` 中会包含此对象：
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `total` | Long | 符合条件的记录总数 |
| `rows` | List | 当前页的数据列表 |

---

## 3. 数据模型 (POJO)

### 3.1 用户 (RstUser)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `id` | Integer | 用户唯一 ID |
| `username` | String | 用户名 |
| `age` | Integer | 年龄 |
| `gender` | Integer | 性别 (1:男, 2:女, 0:未知) |
| `phone` | String | 手机号 |
| `password` | String | 登录密码 |
| `avatar` | String | 用户头像 URL 链接地址 |
| `level` | Integer | 会员等级 |
| `signInCount`| Integer | 签到次数 |

### 3.2 登录信息 (LoginInfo)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `id` | Integer | 用户ID |
| `username` | String | 用户名 |
| `token` | String | 登录JWT令牌 |


### 3.3 菜品 (Dish)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `id` | Integer | 菜品 ID |
| `dishname` | String | 菜品名称 |
| `dishimage` | Integer | 菜品图片标识 |
| `price` | double | 菜品单价 |
| `remain` | int | 库存剩余数量 |
| `intro` | String | 菜品简介 |

### 3.4 订单 (Order)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `orderId` | Integer | 订单 ID |
| `rstuserId` | Integer | 用户 ID |
| `dishId` | Integer | 菜品 ID |
| `orderTime` | DateTime | 下单时间 |
| `quantity` | Integer | 购买数量 |
| `totalPrice` | double | 总计金额 |
| `userName` | String | 用户名 (冗余显示) |
| `dishName` | String | 菜品名 (冗余显示) |

---

## 4. API 接口详情

### 4.1 用户管理 (RstUserController)

#### 4.1.1 用户登录 (新 - 带JWT令牌)
- **URL**: `/login`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Body**: `RstUser` 对象 (包含 `username` 和 `password`)
- **说明**: 登录成功后会生成JWT令牌(token)，用于后续身份验证。
- **响应**: 
  - 成功: 返回 `LoginInfo` 对象，包含用户ID、用户名和JWT令牌
  - 失败: 返回错误信息 "用户名或密码错误"

#### 4.1.2 用户登录 (旧 - 已弃用)
- **URL**: `/RstUserSignIn`
- **Method**: `POST`
- **Params (Query)**:
  - `rstUserName`: 用户名
  - `password`: 密码
- **说明**: 此接口已弃用，不再推荐使用。验证成功返回用户信息，但不包含JWT令牌。
- **响应**: 
  - 成功: 返回 `RstUser` 对象
  - 失败: 返回错误信息 "此用户不存在" 或 "密码错误"
- **注意**: 建议使用新的 `/login` 接口，该接口支持JWT令牌认证。

#### 4.1.3 获取所有用户
- **URL**: `/RstUsers`
- **Method**: `GET`
- **说明**: 返回所有用户的完整列表.

#### 4.1.4 新增用户
- **URL**: `/addRstUser`
- **Method**: `POST`
- **Body**: `RstUser` 对象

#### 4.1.5 分页/条件查询用户
- **URL**: `/conditionQueryRstUser`
- **Method**: `POST`
- **Body**: `RstUserQueryPara` (支持 page, pageSize, username, gender, phone 等模糊查询)

---

### 4.2 菜品管理 (DishController)

#### 4.2.1 获取菜品列表
- **URL**: `/getDishes`
- **Method**: `GET`
- **说明**: 返回所有菜品，通常用于首页展示。

#### 4.2.2 分页查询菜品
- **URL**: `/paginationQueryDishes`
- **Method**: `POST`
- **Params (Query)**: `page` (默认1), `pageSize` (默认5)

#### 4.2.3 条件查询菜品 (搜索)
- **URL**: `/conditionQueryDish`
- **Method**: `POST`
- **Body**: `DishQueryPara` (包含 dishName 模糊匹配)

#### 4.2.4 新增/更新菜品
- **新增**: `/addDish` (POST, Body: Dish)
- **更新**: `/updateDishById` (POST, Body: Dish)

---

### 4.3 订单管理 (OrderController)

#### 4.3.1 下单 (添加订单)
- **URL**: `/addOrder`
- **Method**: `POST`
- **Body**: `Order` 对象 (需提供 rstuserId, dishId, quantity)
- **说明**: 后端会自动校验库存、余额并计算总价。

#### 4.3.2 查询用户订单
- **URL**: `/getOrdersByRstuserId`
- **Method**: `POST`
- **Params (Query)**: `rstuserId`

#### 4.3.3 修改订单
- **URL**: `/updateOrderById`
- **Method**: `POST`
- **Params (Query)**: `originalOrderId` (原订单ID)
- **Body**: `Order` (新订单数据)

---

### 4.4 评论管理 (DishCommentsController)

#### 4.4.1 添加评论
- **URL**: `/addDishComments`
- **Method**: `POST`
- **Params (Query)**: `rstuserId`, `dishId`, `userComment`

#### 4.4.2 获取菜品详情评论
- **URL**: `/getDishCommentsByDishId`
- **Method**: `POST`
- **Params (Query)**: `dishId`
- **返回**: `List<OneDishComments>` (包含用户名和内容)

#### 4.4.3 获取我的评论
- **URL**: `/getDishCommentsByRstuserId`
- **Method**: `POST`
- **Params (Query)**: `rstuserId`

---

### 4.5 收藏与余额 (RstUserCollection / RstUserMsg)

#### 4.5.1 收藏管理
- **添加收藏**: `/addRstUserCollection` (POST, Params: rstuserId, dishId)
- **取消收藏**: `/deleteRstUserCollectionByRstuserId` (POST, Params: rstuserId, dishId)
- **查询用户所有收藏**: 
  - `/queryRstUserAllCollectionsByRstuserId` (POST, Params: rstuserId)
  - `/queryRstUserCollectionByUserId` (POST, Params: userId)
- **获取收藏菜品详情**: `/getRstUserAllCollectionsDishByRstuserId` (POST, Params: rstuserId)

#### 4.5.2 查询余额与充值
- **查询余额**: `/queryRstUserMsgBalanceById` (POST, Params: id)
- **充值**: `/RstUserRechargeBalance` (POST, Params: id, rechargeAmount)

---

### 4.6 文件上传 (UploadController)

#### 4.6.1 上传文件到云端并更新头像 (OSS)
- **URL**: `/upload`
- **Method**: `POST`
- **Content-Type**: `multipart/form-data`
- **请求参数**:
  - `file` (必填): 要上传的文件
  - `userId` (可选): 用户ID，如果传入则自动更新该用户的头像
- **响应**:
  - 成功: 返回文件的完整 URL 地址
  - 失败: 如果 userId 不存在，返回错误信息 "用户不存在"
- **说明**: 
  - 仅上传文件时，不传 userId 参数
  - 上传并保存头像时，必须传入 userId

---

## 5. 前端调用建议 (Tips)

1. **参数传递**:
   - `POST` 方法中，如果后端使用 `@RequestBody`，前端必须发送 JSON 字符串，并设置 `Content-Type: application/json`。
   - 如果后端使用 `@RequestParam`，前端应使用 Query String 或 `application/x-www-form-urlencoded`。
2. **错误处理**: 始终检查响应中的 `code`。若为 `0`，请在界面弹出 `msg` 提示。
3. **数据一致性**: 下单前建议先调用菜品详情接口确认 `remain` (库存) 是否充足。
4. **JWT令牌认证**:
   - 登录成功后，将返回的JWT令牌存储在localStorage或sessionStorage中
   - 后续受保护的接口调用时，需要在请求头中添加 `Authorization: Bearer {token}`
   - 监听登录状态，当令牌过期或无效时，引导用户重新登录

---

## 6. 系统技术组件

### 6.1 过滤器 (Filter)

#### 6.1.1 实现原理
过滤器是 Servlet 规范的一部分，在请求进入 Servlet 容器后、到达控制器之前执行。本系统实现了以下过滤器：

| 过滤器名称 | 状态 | 功能描述 | 拦截路径 |
| :--- | :--- | :--- | :--- |
| `DemoFilter` | 未启用 | 演示过滤器基本功能 | `/*` (已注释) |
| `TokenFilter` | 未启用 | JWT令牌验证过滤器 | `/*` (已注释) |

#### 6.1.2 技术特点
- 基于 `jakarta.servlet.Filter` 接口实现
- 使用 `@WebFilter` 注解配置拦截路径（当前均已注释）
- 过滤器链执行机制，支持多个过滤器顺序执行
- 可用于请求参数预处理、编码转换、安全验证等场景

### 6.2 拦截器 (Interceptor)

#### 6.2.1 实现原理
拦截器是 Spring MVC 提供的组件，在控制器方法执行前后执行。本系统实现了以下拦截器：

| 拦截器名称 | 状态 | 功能描述 | 拦截路径 | 排除路径 |
| :--- | :--- | :--- | :--- | :--- |
| `DemoInterceptor` | 未启用 | 演示拦截器基本功能 | - | - |
| `TokenInterceptor` | 已启用 | JWT令牌验证拦截器 | `/**` | `/login` |

#### 6.2.2 核心功能
- **`TokenInterceptor`**: 实现了JWT令牌验证逻辑
  - 跳过登录接口的验证
  - 从请求头获取token并验证有效性
  - 验证失败返回401状态码
  - 验证成功则放行请求

#### 6.2.3 技术特点
- 基于 `HandlerInterceptor` 接口实现
- 使用 `@Component` 注解注册到Spring容器
- 在 `WebConfig` 中配置拦截规则
- 支持 `preHandle`、`postHandle`、`afterCompletion` 三个生命周期方法
- 更适合处理与业务逻辑相关的横切关注点

### 6.3 面向切面编程 (AOP)

#### 6.3.1 实现原理
AOP是Spring框架的核心特性之一，用于实现横切关注点的模块化。本系统实现了以下切面：

| 切面名称 | 状态 | 功能描述 | 切入点 |
| :--- | :--- | :--- | :--- |
| `TestAspect` | 未启用 | 演示AOP各种通知类型 | `execution(* com.itheima.service.impl.*.*(..))` |
| `RecordTimeAspect` | 已启用 | 记录方法执行时间 | `execution(* com.itheima.service.impl.*.*(..))` |

#### 6.3.2 核心功能
- **`RecordTimeAspect`**: 实现了方法执行时间监控
  - 记录服务实现类所有方法的执行开始时间
  - 执行原始方法
  - 记录方法执行结束时间并计算耗时
  - 打印方法执行耗时日志

#### 6.3.3 技术特点
- 使用 `@Aspect` 注解标记切面类
- 使用 `@Component` 注解注册到Spring容器
- 使用 `@Pointcut` 定义切入点表达式
- 使用 `@Around` 实现环绕通知
- 支持多种通知类型：`@Before`、`@After`、`@AfterReturning`、`@AfterThrowing`
- 更适合处理日志记录、性能监控、事务管理等横切关注点

### 6.4 组件执行顺序

当一个请求到达系统时，各组件的执行顺序如下：
1. **过滤器 (Filter)** → 最先执行，在Servlet容器层面处理
2. **拦截器 (Interceptor)** → 其次执行，在Spring MVC层面处理
3. **控制器 (Controller)** → 核心业务逻辑处理
4. **AOP切面** → 包裹服务层方法执行
5. **服务层 (Service)** → 业务逻辑实现
6. **数据访问层 (Mapper)** → 数据操作
7. **AOP切面** → 服务方法执行后处理
8. **拦截器 (Interceptor)** → 控制器方法执行后处理
9. **过滤器 (Filter)** → 最后执行，响应返回前处理

### 6.5 安全认证流程

系统的安全认证主要通过以下流程实现：
1. 用户通过 `/login` 接口登录，提供用户名和密码
2. 后端验证用户信息，生成JWT令牌
3. 前端存储JWT令牌
4. 后续请求在请求头中携带令牌
5. **`TokenInterceptor`** 拦截请求并验证令牌
   - 验证成功：放行请求
   - 验证失败：返回401状态码
6. 控制器处理业务逻辑
7. **`RecordTimeAspect`** 监控服务方法执行性能
8. 返回响应给前端
