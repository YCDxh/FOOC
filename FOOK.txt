create database FOOK 

-- ---------------------------
-- 1. 用户与权限模块
-- ---------------------------
CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户唯一标识符',
  username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户登录名（唯一）',
  password_hash CHAR(60) NOT NULL COMMENT 'BCrypt加密后的密码哈希值',
  email VARCHAR(100) UNIQUE NOT NULL COMMENT '用户电子邮箱（唯一）',
  avatar_url VARCHAR(255) COMMENT '用户头像的URL地址',
  role ENUM('student', 'teacher', 'admin') NOT NULL DEFAULT 'student' COMMENT '用户角色：学员/教师/管理员',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建时间'
) COMMENT='平台用户基本信息表';

CREATE TABLE user_profile (
  user_id INT PRIMARY KEY COMMENT '关联用户ID',
  real_name VARCHAR(50) COMMENT '用户真实姓名',
  gender ENUM('male', 'female', 'other') COMMENT '用户性别',
  bio TEXT COMMENT '用户个人简介',
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT='用户扩展信息表';

-- ---------------------------
-- 2. 课程核心模块
-- ---------------------------
CREATE TABLE courses (
  course_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '课程唯一标识符',
  title VARCHAR(200) NOT NULL COMMENT '课程标题',
  description TEXT COMMENT '课程详细描述',
  teacher_id INT NOT NULL COMMENT '授课教师ID（关联users.user_id）',
  cover_image_url VARCHAR(255) COMMENT '课程封面图URL',
  price DECIMAL(10,2) DEFAULT 0.00 COMMENT '课程价格（单位：元）',
  status ENUM('draft', 'published', 'closed') DEFAULT 'draft' COMMENT '课程状态：草稿/已发布/已关闭',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '课程创建时间',
  FOREIGN KEY (teacher_id) REFERENCES users(user_id)
) COMMENT='课程主信息表';

CREATE TABLE chapters (
  chapter_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '章节唯一标识符',
  course_id INT NOT NULL COMMENT '所属课程ID（关联courses.course_id）',
  chapter_title VARCHAR(200) NOT NULL COMMENT '章节标题',
  sort_order INT DEFAULT 0 COMMENT '章节排序序号（从0开始）',
  FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
) COMMENT='课程章节结构表';

CREATE TABLE chapter_contents (
  content_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '教学内容唯一标识符',
  chapter_id INT NOT NULL COMMENT '所属章节ID（关联chapters.chapter_id）',
  content_type ENUM('video', 'document', 'quiz') NOT NULL COMMENT '内容类型：视频/文档/测验',
  resource_url VARCHAR(255) NOT NULL COMMENT '教学资源存储路径（如MinIO地址）',
  duration INT COMMENT '视频时长（单位：秒，仅视频类型有效）',
  quiz_id INT COMMENT '关联测验ID（需扩展测验模块时使用）',
  sort_order INT DEFAULT 0 COMMENT '内容排序序号（从0开始）',
  FOREIGN KEY (chapter_id) REFERENCES chapters(chapter_id) ON DELETE CASCADE
) COMMENT='章节详细教学内容表';

-- ---------------------------
-- 3. 学习行为模块
-- ---------------------------
CREATE TABLE enrollments (
  enrollment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '选课记录唯一标识符',
  user_id INT NOT NULL COMMENT '学员ID（关联users.user_id）',
  course_id INT NOT NULL COMMENT '课程ID（关联courses.course_id）',
  enrolled_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
  completion_status ENUM('in_progress', 'completed') DEFAULT 'in_progress' COMMENT '课程完成状态：进行中/已完成',
  UNIQUE KEY (user_id, course_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id)
) COMMENT='课程选课记录表';

CREATE TABLE study_progress (
  progress_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '学习进度唯一标识符',
  user_id INT NOT NULL COMMENT '学员ID（关联users.user_id）',
  content_id INT NOT NULL COMMENT '教学内容ID（关联chapter_contents.content_id）',
  last_studied_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后学习时间',
  progress_percent DECIMAL(5,2) DEFAULT 0.00 COMMENT '学习进度百分比（0.00-100.00）',
  is_completed BOOLEAN DEFAULT FALSE COMMENT '是否完成学习（true/false）',
  UNIQUE KEY (user_id, content_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (content_id) REFERENCES chapter_contents(content_id)
) COMMENT='用户学习进度跟踪表';

-- ---------------------------
-- 4. 互动与评价模块
-- ---------------------------
CREATE TABLE discussions (
  discussion_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '讨论记录唯一标识符',
  course_id INT NOT NULL COMMENT '所属课程ID（关联courses.course_id）',
  user_id INT NOT NULL COMMENT '发帖用户ID（关联users.user_id）',
  content TEXT NOT NULL COMMENT '讨论内容（支持富文本）',
  parent_id INT DEFAULT NULL COMMENT '父评论ID（用于回复功能）',
  upvote_count INT DEFAULT 0 COMMENT '点赞数量',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (course_id) REFERENCES courses(course_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
) COMMENT='课程讨论区表';

CREATE TABLE course_ratings (
  rating_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '评分记录唯一标识符',
  course_id INT NOT NULL COMMENT '被评课程ID（关联courses.course_id）',
  user_id INT NOT NULL COMMENT '评分用户ID（关联users.user_id）',
  score TINYINT CHECK (score BETWEEN 1 AND 5) COMMENT '评分值（1-5星）',
  comment TEXT COMMENT '评分文字评论',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评分时间',
  UNIQUE KEY (course_id, user_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
) COMMENT='课程评分评价表';

-- ---------------------------
-- 5. 订单与支付模块
-- ---------------------------
CREATE TABLE orders (
  order_id CHAR(20) PRIMARY KEY COMMENT '订单号（生成规则：年月日时分秒+6位随机数，示例：20230809123456789012）',
  user_id INT NOT NULL COMMENT '下单用户ID（关联users.user_id）',
  course_id INT NOT NULL COMMENT '购买课程ID（关联courses.course_id）',
  amount DECIMAL(10,2) NOT NULL COMMENT '订单金额（单位：元）',
  status ENUM('pending', 'paid', 'canceled') DEFAULT 'pending' COMMENT '订单状态：待支付/已支付/已取消',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id)
) COMMENT='订单主表';

CREATE TABLE payments (
  payment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '支付记录唯一标识符',
  order_id CHAR(20) NOT NULL COMMENT '关联订单号（关联orders.order_id）',
  transaction_id VARCHAR(100) COMMENT '第三方支付平台交易号（如支付宝交易号）',
  payment_method ENUM('alipay', 'wechat', 'credit_card') COMMENT '支付方式：支付宝/微信/信用卡',
  paid_at DATETIME COMMENT '支付成功时间',
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
) COMMENT='支付流水记录表';

-- ---------------------------
-- 6. 系统管理模块
-- ---------------------------
CREATE TABLE operation_logs (
  log_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '日志记录唯一标识符',
  user_id INT COMMENT '操作用户ID（关联users.user_id，可为空表示系统操作）',
  action_type VARCHAR(50) NOT NULL COMMENT '操作类型（如：user_login/course_publish）',
  target_id INT COMMENT '操作对象ID（如被修改的课程ID）',
  details TEXT COMMENT '操作详情（JSON格式存储详细信息）',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  FOREIGN KEY (user_id) REFERENCES users(user_id)
) COMMENT='系统操作日志表';