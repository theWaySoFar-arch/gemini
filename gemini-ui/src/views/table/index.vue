<template>
  <div class="app-container">
    <div class="search-bar">
      <div class="search-row">
        <div class="search-inputs">
          <div class="search-label">服务器名称:</div>
          <el-input v-model="searchServer" placeholder="搜索服务器名称" size="small" clearable></el-input>
        </div>
        <div class="search-inputs">
          <div class="search-label">类名:</div>
          <el-input v-model="searchClassName" placeholder="搜索类名" size="small" clearable></el-input>
        </div>
      </div>
      <div class="search-row">
        <div class="search-inputs">
          <div class="search-label">链路追踪码:</div>
          <el-input v-model="searchTraceCode" placeholder="搜索链路追踪码" size="small" clearable></el-input>
        </div>
        <div class="search-inputs">
          <div class="search-label">应用环境:</div>
          <el-input v-model="searchAppEnv" placeholder="搜索应用环境" size="small" clearable></el-input>
        </div>
        <div class="search-inputs">
          <div class="search-label">时间范围:</div>
          <el-date-picker v-model="searchDate" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期" size="small" :picker-options="pickerOptions" style="width: 180px;"></el-date-picker>
        </div>
        <div class="search-inputs">
          <div class="search-label">内容搜索:</div>
          <el-input v-model="searchContent" placeholder="搜索内容" size="small" clearable style="width: 220px;"></el-input>
        </div>
        <el-button type="primary" size="small" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <el-table
      v-loading="listLoading"
      :data="currentPageData"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="内容">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="应用名称" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="链路追踪码" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.pageviews }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="日志等级" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="日志时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.display_time }}</span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="currentPage"
      :page-sizes="[10, 15, 20, 30]"
      :page-size="pageSize"
      layout="sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { getList } from '@/api/table'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        draft: 'success',
        warning: 'warning',
        error: 'danger',
        debug: 'info'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      searchTitle: '',
      searchAuthor: '',
      searchDate: null,
      pickerOptions: {
      selectableRange: '00:00:00 - 23:59:59'
    },
    searchServer: '',
    searchClassName: '',
    searchTraceCode: '',
    searchContent: ''// 添加这个搜索字段
    }
  },
  computed: {
    currentPageData() {
      const startIndex = (this.currentPage - 1) * this.pageSize
      const endIndex = startIndex + this.pageSize
      return this.list.slice(startIndex, endIndex)
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getList().then(response => {
        this.list = response.data.items
        this.total = this.list.length // 更新总条数
        this.listLoading = false
      })
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize
      this.currentPage = 1
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage
    },
    handleDateChange() {
      // 保存其他搜索框的值
      this.savedSearchText.searchServer = this.searchServer;
      this.savedSearchText.searchClassName = this.searchClassName;
      this.savedSearchText.searchTraceCode = this.searchTraceCode;
      this.savedSearchText.searchAppEnv = this.searchAppEnv;
      this.savedSearchText.searchContent = this.searchContent;
    },
    handleSearch() {
      const params = {
        server: this.searchServer,
        className: this.searchClassName,
        traceCode: this.searchTraceCode,
        appEnv: this.searchAppEnv,
        startDate: this.searchDate ? this.searchDate[0] : null,
        endDate: this.searchDate ? this.searchDate[1] : null,
        content: this.searchContent
      }

      getList(params)
        .then(response => {
          // 更新页面展示的数据
          this.list = response.data.items;
          this.total = response.data.total;
          this.currentPageData = this.list.slice(0, this.pageSize);
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    }
  }
}
</script>

<style>
.search-bar {
  margin-bottom: 20px;
}

.search-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.search-inputs {
  display: flex;
  align-items: center;
  gap: 8px;
}

.search-label {
  width: 80px;
  font-family: '黑体', sans-serif;
}
</style>
