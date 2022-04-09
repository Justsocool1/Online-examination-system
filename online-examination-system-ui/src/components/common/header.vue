<!-- 顶部信息栏 -->
<template>
  <header id="topbar">
    <el-row>
      <el-col :span="4" class="topbar-left">
        <div class="title1">
        <icon-font id="examIcon" type="icon-zhihuikaoshi" :style="{fontSize: '65px'}"/>
        <span id="systemName" @click="index()">ONLINE EXAM SYSTEM</span>
        </div>
        
      </el-col>
      <el-col :span="20" class="topbar-right">
        <i class="el-icon-menu" @click="toggle()"></i>
        <div class="user">
          <span>{{user.userName}}</span>
          <a-dropdown :placement="placement">
              <img src="@/assets/img/avatar.png" class="user-img" ref="img"/>
              <template #overlay>
                  <a-menu>
                  <a-menu-item v-if="flag">
                    <a href="javascript:;" @click="manage()">修改密码</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a href="javascript:;" @click="exit()">退出登录</a>
                  </a-menu-item>
                </a-menu>
              </template>
      </a-dropdown>
        </div>
      </el-col>
    </el-row>
  </header>
</template>

<script>
import store from "@/store";
import {mapState,mapMutations} from 'vuex'
import { createFromIconfontCN } from '@ant-design/icons-vue';
const IconFont = createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/font_3260262_ji93tnzull.js',
});
export default {
  data() {
    return {
      user: { //用户信息
        userName: null,
        userId: null,
        role: null,
      },
      flag: true, 
    }
  },
  components: {
    IconFont
},
  created() {
    this.getUserInfo()
    
  },
  computed: mapState(["flag","menu"]),
  methods: {
    //左侧栏放大缩小
    ...mapMutations(["toggle"]),
    getUserInfo() { //获取用户信息
      let userName = this.$cookies.get("cname")
      let userId = this.$cookies.get("cid")
      let role = this.$cookies.get("role");
      this.user.userName = userName
      this.user.userId = userId
      this.user.role = role;
      role == 0 ? this.flag = false : this.flag = true;
    },
    index() {
      this.$router.push({path: '/index'})
    },
    exit() {
      console.log("退出登录清除token ---header");
      localStorage.removeItem("token");
      let role = this.$cookies.get("role")
      this.$router.push({path:"/"}) //跳转到登录页面
      this.$cookies.remove("cname") //清除cookie
      this.$cookies.remove("cid")
      this.$cookies.remove("role")
      if(role == 0) {
        this.menu.pop()
      }
    },
    manage() {
      // 跳转到修改密码页面
        this.$router.push({path: '/index/manager',query: {studentId: this.$cookies.get("cid"),role: this.$cookies.get("role")}})
    },
  },
  store
}
</script>

<style scoped>
.title1{
  overflow: hidden;
  font-size: 20px;
  display: inline-block;
  cursor: pointer;
}
#examIcon{
  float: left;
  /* margin-left: 20px; */
}
#examName{
  float: left;
  /* margin-left: 50px; */
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
#topbar {
  position: relative;
  z-index: 10;
  background-color: #124280;
  height: 80px;
  line-height: 80px;
  color: #fff;
  box-shadow: 5px 0px 10px rgba(0, 0, 0, 0.5);
}
#topbar .topbar-left {
  height: 80px;
  display: flex;
  justify-content: center;
  background: rgba(0, 0, 0, 0.05);
  overflow: hidden;
}
.topbar-left .icon-kaoshi {
  font-size: 60px;
}
.topbar-left .title {
  font-size: 20px;
  display: inline-block;
  cursor: pointer;
}
.topbar-right {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.topbar-right .user-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.topbar-right .el-icon-menu {
  font-size: 30px;
  margin-left: 20px;
}
.topbar-right .user {
  position: relative;
  margin-right: 40px;
  display: flex;
}
.topbar-right .user .user-img {
  margin-top: 15px;
  margin-left: 10px;
  cursor: pointer;
}
.user .out {
  font-size: 14px;
  position: absolute;
  top: 80px;
  right: 0px;
  background-color: #fff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding: 18px;
}
.user .out ul {
  list-style: none;
}
.user .out ul > li {
  height: 26px;
  line-height: 26px;
}
.out a {
  text-decoration: none;
  color: #000;
}
.out .exit {
  margin-top: 4px;
  padding-top: 4px;
  border-top: 1px solid #ccc;
}
</style>
