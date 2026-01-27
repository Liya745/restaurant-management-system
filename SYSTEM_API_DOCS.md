# 餐厅服务管理系统 API 接口文档 (v2.0)

## 1. 项目概述
本项目是餐厅服务管理系统的后端 API，基于 Spring Boot 框架开发。文档旨在为前端开发人员提供清晰、准确的接口调用说明。

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

### 2.2 分页数据 (PageResult)
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

### 3.2 菜品 (Dish)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `id` | Integer | 菜品 ID |
| `dishname` | String | 菜品名称 |
| `dishimage` | Integer | 菜品图片标识 |
| `price` | double | 菜品单价 |
| `remain` | int | 库存剩余数量 |
| `intro` | String | 菜品简介 |

### 3.3 订单 (Order)
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

#### 4.1.1 用户登录
- **URL**: `/RstUserSignIn`
- **Method**: `POST`
- **Params (Query)**:
  - `rstUserName`: 用户名
  - `password`: 密码
- **说明**: 验证成功返回用户信息。

#### 4.1.2 获取所有用户
- **URL**: `/RstUsers`
- **Method**: `GET`
- **说明**: 返回所有用户的完整列表。

#### 4.1.3 新增用户
- **URL**: `/addRstUser`
- **Method**: `POST`
- **Body**: `RstUser` 对象

#### 4.1.4 分页/条件查询用户
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

#### 4.6.1 上传文件到云端 (OSS)
- **URL**: `/upload`
- **Method**: `POST`
- **Body**: `multipart/form-data` (file 字段)
- **返回**: 成功后返回文件的绝对 URL 地址。

---

## 5. 前端调用建议 (Tips)

1. **参数传递**:
   - `POST` 方法中，如果后端使用 `@RequestBody`，前端必须发送 JSON 字符串，并设置 `Content-Type: application/json`。
   - 如果后端使用 `@RequestParam`，前端应使用 Query String 或 `application/x-www-form-urlencoded`。
2. **错误处理**: 始终检查响应中的 `code`。若为 `0`，请在界面弹出 `msg` 提示。
3. **数据一致性**: 下单前建议先调用菜品详情接口确认 `remain` (库存) 是否充足。
