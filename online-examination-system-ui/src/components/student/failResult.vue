<template>
  <a-result
    status="error"
    title="提交试卷错误!"
    sub-title="请来联系任课老师或管理员老师！"
  >
    <template #extra>
      <a-button @click="getBack()" type="primary">返回首页</a-button>
    </template>
  </a-result>
</template>
<script>
export default {
    methods: {
      getBack(){
          this.$router.push({path: "/student/"});
      }
    },
}
</script>