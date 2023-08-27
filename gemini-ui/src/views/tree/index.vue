<template>
  <div class="app-container">
    <div class="search-container">
      <el-input v-model="traceCode" placeholder="输入链路追踪码" class="search-input" />
      <el-button type="primary" @click="search" class="search-button">搜索</el-button>
    </div>

    <el-tree
      ref="tree2"
      :data="treeData"
      :props="defaultProps"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
export default {

  data() {
    return {
      traceCode: '', // 输入的链路追踪码
      treeData: [], // 树状结构展示的数据
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },

  methods: {
    async fetchTreeData() {
      try {
        const response = await axios.get(`/api/treeData?traceCode=${this.traceCode}`) // 替换为实际的后端 API 地址
        this.treeData = response.data // 将获取到的数据赋值给 treeData 属性
      } catch (error) {
        console.error(error)
      }
    },
    search() {
      this.fetchTreeData() // 执行搜索操作，获取并展示链路追踪信息
    }
  }
}
</script>

<style>
.search-container {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.search-input {
  flex: 1;
  margin-right: 10px;
}

.search-button {
  width: 80px;
}
</style>
