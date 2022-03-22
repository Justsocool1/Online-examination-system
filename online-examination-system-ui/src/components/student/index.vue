<!--学生考试首页-->
<template>
  <div id="student">
    <el-row class="padding-50">
      <el-col :span="24">
        <ul class="list">
          
          <li class="logo" >
            <icon-font id="examIcon" type="icon-zhihuikaoshi" :style="{fontSize: '40px'}"/><span id="examName">ONLINE-EXAM-SYSTEM</span>
          </li>
          <li><icon-font type="icon-shijuanguanli" /><a href="javascript:;" @click="exam()">我的试卷</a></li>
          <!-- <li><a href="javascript:;" @click="practice()">我的练习</a></li> -->
          <li><icon-font type="icon-shijuanguanlix"/><router-link to="/student/scoreTable">我的分数</router-link></li>
          <li
            class="right"
            @mouseenter="flag = !flag"
            @mouseleave="flag = !flag"
          >
            <a href="javascript:;">
              <!-- <i class="iconfont icon-Userselect icon"></i> -->
              <a-avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
              {{ user.userName }}</a
            >
            <div class="msg" v-if="flag">
              <p @click="manage()">管理中心</p>
              <p class="exit" @click="exit()">退出</p>
            </div>
          </li>
        </ul>
      </el-col>
    </el-row>
    <!--路由区域-->
    <div class="main">
      <router-view></router-view>
    </div>
    <v-footer></v-footer>
  </div>
</template>

<script>
import myFooter from "@/components/student/myFooter";
import { ContainerOutlined } from '@ant-design/icons-vue';
import { createFromIconfontCN } from '@ant-design/icons-vue';
import store from "@/store";
import { mapState } from "vuex";
const IconFont = createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/font_3260262_hljikdspxpw.js',
});
export default {
  store,
  components: {
    "v-footer": myFooter,
    
  },
  data() {
    return {
      flag: false,
      user: {},
      
    };
  },
  components: {
    ContainerOutlined,
    IconFont
  },
  created() {
    this.userInfo();
  },
  methods: {
    exit() {
      //退出登录
      this.$router.push({ path: "/" }); //跳转到登录页面
      this.$cookies.remove("cname"); //清除cookie
      this.$cookies.remove("cid");
    },
    manage() {
      // 跳转到修改密码页面
        this.$router.push({path: '/student/manager',query: {studentId: this.$cookies.get("cid"),role: this.$cookies.get("role")}})
    },
    userInfo() {
      let studentName = this.$cookies.get("cname");
      let studentId = this.$cookies.get("cid");
      console.log(`studentId${studentId}`);
      console.log(`studentName ${studentName}`);
      this.user.userName = studentName;
      this.user.studentId = studentId;
    },
    // practice() {
    //   //跳转练习模式
    //   let isPractice = true;
    //   this.$store.commit("practice", isPractice);
    //   //   this.$router.push({path:'/startExam'})
    // },
    exam() {
      //跳转考试模式
      let isPractice = false;
      console.log("exam1");
      this.$store.commit("practice", isPractice);
      console.log("exam2");
      this.$router.push({ path: "/student" });
      console.log("exam3")
    },
  },
  computed: mapState(["isPractice"]),
};
</script>

<style lang="scss" scoped>

.right .icon {
  margin-right: 6px;
}
#student .padding-50 {
  margin: 0 auto;
  padding: 0 50px;
  box-shadow: 0 0 10px 4px rgba(1, 149, 255, 0.1);
  background-color: #fff;
}
.list a {
  text-decoration: none;
  color: #334046;
}
li {
  list-style: none;
  height: 60px;
  line-height: 60px;
}
#student .list {
  display: flex;
}
#student .list li {
  padding: 0 20px;
  cursor: pointer;
}
#student .list li:hover {
  background-color: #0195ff;
  transition: all 2s ease;
}
#student .list li:hover a {
  color: #fff;
}
#student .list .right {
  margin-left: auto;
  position: relative;
}
#student .list li.right :hover a {
  color: #000;
}
#student .list .logo {
  overflow: hidden;
  float: left;
  display: flex;
  font-weight: bold;
  color: #2f6c9f;
}
#student .list .logo i {
  font-size: 50px;
}
.right .msg {
  text-align: center;
  position: absolute;
  top: 60px;
  left: 0px;
  display: flex;
  flex-direction: column;
  border-radius: 2px;
  border-bottom: 3px solid #0195ff;
  background-color: #fff;
}
.right .msg p {
  height: 40px;
  line-height: 40px;
  width: 105px;
}
.right .msg p:hover {
  background-color: #0195ff;
}
</style>
