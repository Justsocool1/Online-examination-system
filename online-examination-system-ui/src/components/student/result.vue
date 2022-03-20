<template>
  <a-result
    status="success"
    title="成功提交试卷!"
    sub-title="最终成绩将在任课老师批改试卷之后显示在您的'我的分数'中"
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