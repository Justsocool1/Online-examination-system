<template>
  <section class="index">
    <div class="hello">
      <heart-two-tone two-tone-color="red" :style="{fontSize: '30px'}"/>&nbsp<span>很高兴遇见你,{{user.userName}}老师。</span>
    </div>
    <div class="msg">
      <p class="title">教务公告：</p>
      <ul>
        <li @click="this.$alert('这是一个公告','清明节放假通知',{confirmButtonText: '确定'})"><tag-two-tone />清明节放假通知</li>
        <li @click="this.$alert('这是一个公告','工作报告',{confirmButtonText: '确定'})"><tag-two-tone />下周例行工作报告会</li>
        <li @click="this.$alert('这是一个公告','毕业生答辩具体流程',{confirmButtonText: '确定'})"><tag-two-tone />关于毕业生答辩具体流程</li>
      </ul>
    </div>
  </section>
</template>

<script>
import { HeartTwoTone,TagTwoTone } from '@ant-design/icons-vue';
export default {
  data() {
    return {
      user: { //用户信息
        userName: null,
        userId: null
      } 
    }
  },
  components:{
    HeartTwoTone,
    TagTwoTone
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() { //获取用户信息
      let userName = this.$cookies.get("cname")
      let userId = this.$cookies.get("cid")
      this.user.userName = userName
      this.user.userId = userId
    },
  }
}
</script>


<style lang="scss" scoped>
.index {
  margin-left: 70px;
  .hello {
    font-size: 20px;
    color: #726f70;
    .icon-xihuan {
      font-size: 30px;
      color: #dd6572;
    }
  }
  .msg {
    .title {
      font-size: 16px;
      color: #000;
      margin-top: 20px;
      margin-left: 10px;
    }
    ul {
      display: flex;
      flex-direction: column;
      width: 200px;
      overflow: hidden;
    }
    li {
      margin-top: 10px;
      font-size: 14px;
      color: lightcoral;
      cursor: pointer;
      display: inline-block;
    }
  }
}
</style>

