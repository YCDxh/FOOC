<!-- ElemMenu.vue -->
<template>
  <el-container class="main-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '200px'" class="side-menu">
      <div class="logo-area">
        <span v-show="!isCollapse">在线教育平台</span>
      </div>
      <el-menu :collapse="isCollapse" :collapse-transition="false" background-color="#304156" text-color="#bfcbd9"
        active-text-color="#409EFF" unique-opened>
        <el-submenu v-for="item in menuItems" :key="item.title" :index="item.title">
          <template #title>
            <i :class="['menu-icon', item.icon]"></i>
            <span>{{ item.title }}</span>
          </template>
          <el-menu-item v-for="sub in item.children" :key="sub" :index="sub">
            {{ sub }}
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <!-- 顶部导航 -->
      <el-header class="main-header">
        <div class="header-left">
          <el-button type="text" @click="toggleCollapse" class="collapse-btn">
            <i class="el-icon-s-fold"></i>
          </el-button>
          <span class="header-title">首页</span>
        </div>
        <div class="user-info">
          <span>用户名：周同学</span>
          <span class="identity">身份：【学生】</span>
        </div>
      </el-header>

      <!-- 内容区域 -->
      <el-main>
        <el-card class="content-card">
          <!-- 这里放置具体页面内容 -->
          <el-table :data="courseData" stripe border style="width: 100%; margin-top: 20px;">
            <el-table-column type="index" label="序号" align="center" />
            <el-table-column prop="courseName" label="课程名称" />
            <el-table-column prop="category" label="分类">
              <template #default="scope">
                <el-tag type="info">{{ scope.row.category }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="lecturer" label="讲师" />

            <el-table-column prop="status" label="状态">
              <template #default="scope">
                <el-tag :type="scope.row.status === 'published' ? 'success' : 'danger'" effect="dark">
                  {{ scope.row.status === 'published' ? '已发布' : '未发布' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right">
              <template #default>
                <el-button size="mini" type="text">查看</el-button>
                <el-button size="mini" type="text" icon="el-icon-edit">编辑</el-button>
                <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="block">
            <span class="demonstration"></span>
            <el-pagination small layout="prev, pager, next" :total="50">
            </el-pagination>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: false,
      menuItems: [
        {
          title: '内容管理',
          icon: 'el-icon-notebook-2',
          children: ['所有课程', '我的课程', '课程收藏']
        },
        {
          title: '用户管理',
          icon: 'el-icon-user',
          children: ['学习记录', '学习报告']
        },
        {
          title: '系统设置',
          icon: 'el-icon-setting',
          children: ['个人信息', '账号安全', '消息通知']
        }
      ],
      courseData: [
        {
          courseName: 'Vue3开发实战',
          category: '前端开发',
          lecturer: '王老师',

          status: 'published',
        },
        {
          courseName: 'Java核心编程',
          category: '后端开发',
          lecturer: '李老师',

          status: 'published',
        },
        {
          courseName: 'Python数据分析',
          category: '数据科学',
          lecturer: '张老师',

          status: 'draft',
        },
        {
          courseName: 'UI设计基础',
          category: '设计艺术',
          lecturer: '陈老师',

          status: 'published',
        }
      ]
    }
  },
  methods: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    }
  }
}
</script>

<style scoped>
.main-container {
  height: 100vh;

  .side-menu {
    background: rgba(48, 65, 86, 0.9);
    /* 原#304156改为90%透明 */
    transition: width 0.3s;

    .logo-area {
      height: 60px;
      line-height: 60px;
      color: #fff;
      text-align: center;
      background: #2b2f3a;
      font-size: 18px;
    }

    .el-menu {
      border-right: none;

      .menu-icon {
        display: inline-block;
        width: 24px;
        height: 24px;
        background: rgb(251, 252, 253, 0.9);
        /* 原#304156改为90%透明 */
        border-radius: 50%;
        margin-right: 8px;

        &::before {
          display: block;
          text-align: center;
          line-height: 24px;
        }
      }
    }
  }

  .main-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: rgb(251, 252, 253, 0.9);
    /* 原#304156改为90%透明 */

    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .header-left {
      display: flex;
      align-items: center;

      .collapse-btn {
        font-size: 20px;
        color: #333;
      }

      .header-title {
        margin-left: 10px;
        font-size: 16px;
      }
    }

    .user-info {
      .identity {
        margin-left: 15px;
        color: #409EFF;
      }
    }
  }

  .content-card {
    background: rgba(255, 255, 255, 0.8);
    /* 原#fff */
    min-height: calc(100vh - 100px);
    border-radius: 4px;

    .dashboard-content {
      padding: 20px;

      h3 {
        color: #304156;
        margin-bottom: 15px;
      }
    }
  }
}

.el-submenu__title,
.el-menu-item {
  font-size: 14px;
}
</style>