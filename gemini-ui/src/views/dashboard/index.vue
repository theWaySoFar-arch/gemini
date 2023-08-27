<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
    <h1>分布式日志平台</h1>
    <a href="https://github.com/theWaySoFar-arch/gemini">Github地址</a>
    <br>
    <a href="https://gitee.com/LuffyIo/gemini">Gitee地址</a>
    <h1>欢迎PR</h1>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    //映射了一个名为name的getter
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
