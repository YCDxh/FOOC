<template>
  <div class="course-publish-container">
    <el-card class="form-card">
      <!-- 表单标题 -->
      <div class="form-header">
        <h2>课程发布</h2>
        <el-divider></el-divider>
      </div>

      <!-- 表单主体 -->
      <el-form label-width="300px" class="publish-form">
        <!-- 基础信息 -->
        <div class="form-section">
          <h3 class="section-title">基础信息</h3>
          <el-form-item label="课程标题" required>
            <el-input placeholder="请输入课程标题"></el-input>
          </el-form-item>

          <el-form-item label="课程分类" required>
            <el-select placeholder="请选择分类">
              <el-option label="人文社科" value="1"></el-option>
              <el-option label="理工技术" value="2"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="课程封面" required>
            <el-upload
              action="#"
              list-type="picture-card"
              :auto-upload="false"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </div>

        <!-- 课程详情 -->
        <div class="form-section">
          <h3 class="section-title">课程详情</h3>
          <el-form-item label="课程简介" required>
            <el-input
              type="textarea"
              :rows="3"
              placeholder="请输入课程简介"
            ></el-input>
          </el-form-item>

          <el-form-item label="课程详情" required>
            <!-- 需要引入富文本编辑器 -->
            <el-input
              type="textarea"
              :rows="9"
              placeholder="请输入课程详情"
            ></el-input>
          </el-form-item>
        </div>

        <!-- 其他信息 -->
        <div class="form-section">
          <h3 class="section-title">其他信息</h3>
          <el-row :gutter="15">
            <el-col :span="12">
              <el-form-item label="开课时间">
                <el-date-picker
                  type="date"
                  placeholder="选择日期"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="适用对象">
                <el-select multiple placeholder="请选择">
                  <el-option label="本科生" value="1"></el-option>
                  <el-option label="研究生" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="课时安排">
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请输入课时安排（每行一个课时）"
            ></el-input>
          </el-form-item>
        </div>

        <!-- 操作按钮 -->
        <div class="form-actions">
          <el-button type="primary" size="large">提交审核</el-button>
          <el-button size="large">重置表单</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.course-publish-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}

.form-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.form-header {
  margin-bottom: 30px;
  h2 {
    color: #303133;
    margin-bottom: 60px;
  }
}

.publish-form {
  padding: 0 30px;
}

.form-section {
  margin-bottom: 40px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 6px;
  
  .section-title {
    color: #409EFF;
    margin-bottom: 25px;
    padding-left: 10px;
    border-left: 6px solid #409EFF;
  }
}

::v-deep .el-form-item__label {
  font-weight: 500;
  color: #606266;
}

.editor-placeholder {
  width: 100%;
  height: 400px;
  border: 1px solid #DCDFE6;
  border-radius: 4px;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
}

.form-actions {
  text-align: right;
  padding: 30px 0;
  
  .el-button {
    width: 120px;
    margin-left: 20px;
  }
}

.el-divider {
  margin: 20px 0;
}
</style>