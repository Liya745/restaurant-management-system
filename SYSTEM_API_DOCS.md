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
