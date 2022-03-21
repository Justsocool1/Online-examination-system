<!-- 用户登录界面 -->
<template>
  <div id="login">
    <div class="bg"></div>
    <el-row class="main-container">
      <el-col :lg="8" :xs="16" :md="10" :span="10">
        <div class="top">
          <icon-font id="Icon" class="title" type="icon-zhihuikaoshi-copy" :style="{fontSize: '65px'}"/>
          <span id="systemName" class="title">在线考试系统</span>
        </div>
        <div class="bottom">
          <div class="container">
            <p class="title">账号登录</p>
            <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign" :rules="rules" ref="formLabelAlign">
              <el-form-item label="账号" prop="userId">
                <el-input v-model.number="formLabelAlign.userId" placeholder="请输入学号或工号"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="formLabelAlign.password" placeholder="请输入密码" type='password'></el-input>
              </el-form-item>
              <el-form-item label="角色" prop="role">  
              <a-radio-group v-model:value="formLabelAlign.role" name="role">
                <a-radio value="0">管理员</a-radio>
                <a-radio value="1">教师</a-radio>
                <a-radio value="2">学生</a-radio>
              </a-radio-group>
              </el-form-item>
              <div class="submit">
                <el-button type="primary" class="row-login" @click="submitForm('formLabelAlign')">登录</el-button>
              </div>
              <div class="options">
                <p class="find" ><a @click="forgetPwd">找回密码</a></p>
                <div class="register">
                  <span>没有账号?</span>
                  <span><a @click="register">去注册</a></span>
                </div>
              </div>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row class="footer">
      <el-col>
        <p class="msg2" hidden>版权所有 ©2019 <!--重庆文理学院计科2班余晓江--> 保留所有权利  <a href="http://beian.miit.gov.cn/" target="_blank">渝ICP备19001371号</a></p>
      </el-col>
    </el-row>
    <section class="remind">
      <span>管理员账号：9527</span>
      <span>教师账号：20081001</span>
      <span>密码都是：123456</span>
    </section>
  </div>
</template>

<script>
import store from "@/store";
import {mapState} from 'vuex'
import {request} from '@/utils/request'
import {ElMessage} from 'element-plus';
import { createFromIconfontCN } from '@ant-design/icons-vue';
const IconFont = createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/font_3260262_zldbsmw393.js',
});
export default {
  store,
  name: "login",
  data() {
    return {
      labelPosition: 'left',
      formLabelAlign: {
        userId: '20154084',
        password: '123456',
        role: '2'
      },
      ruleForm: {
          userId: '',
          pwd: '',
          role: '',
        },
      rules: {
        userId: [
        {
          required: true,
          message: "请输入学号/工号",
          trigger: "change",
          },
        ],
        password: [
        {
          required: true,
          message: "请输入密码",
          trigger: "change",
        },
        ],
        role: [
        {
          message: "请选择您的身份",
          trigger: "change",
          required: true
          },
        ],
      },
    }
  },
  components: {
    IconFont
  },
  methods: {
    //用户登录请求后台处理
    login() {
      console.log("登录操作执行-------");
      request({
        url: '/user/login',
        method: 'post',
        data: {
          ...this.formLabelAlign
        }
      }).then(res =>{
        let resData = res.data.data["userInfo"];
        let tokenData = res.data.data["token"];
        console.log("token",tokenData);
        console.log("resData",resData)
        if(res.data.success == true){
        console.log(resData)
        switch(resData.role){
           case "0":
             this.$cookies.set("cname",resData.adminName)
             this.$cookies.set("cid",resData.adminId)
             this.$cookies.set("role",resData.role)
             localStorage.setItem("token",tokenData);
             this.$router.push({path: "/index"})
             break;
          case "1":
             this.$cookies.set("cname",resData.teacherName)
             this.$cookies.set("cid",resData.teacherId)
             this.$cookies.set("role",resData.role)
             localStorage.setItem("token",tokenData);
             this.$router.push({path: "/index"})
             break;
          case "2":
             this.$cookies.set("cname",resData.studentName)
             this.$cookies.set("cid",resData.studentId)
             this.$cookies.set("role",resData.role)
             localStorage.setItem("token",tokenData);
             this.$router.push({path: "/student"})
             break;
        }
        } else {
          console.log("登陆失败!");
          ElMessage.error('工号/学号 或者 密码 错误');
        }
      });

    },
    clickTag(key) {
      this.role = key
    },
    forgetPwd(){
      alert("请联系管理员或任课老师")
    },
    register(){
     this.$router.push({path: "/register"})
    },
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // alert('submit!');
            this.login();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
  },
  computed: mapState(["userInfo"]),
  
}
</script>

<style lang="scss" scoped>

.remind {
  border-radius: 4px;
  padding: 10px 20px;
  display: flex;
  position: fixed;
  right: 20px;
  bottom: 50%;
  flex-direction: column;
  color: #606266;
  background-color: #fff;
  border-left: 4px solid #409eff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)
}
.container {
  margin-bottom: 32px;
}
.container .el-radio-group {
  margin: 30px 0px;
}
a:link {
  color:#ff962a;
  text-decoration:none;
}
#login {
  font-size: 14px;
  color: #000;
  background-color: #fff;
}
#login .bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  overflow-y: auto;
  height: 100%;
  background: url('../../assets/img/loginbg.png')center top / cover no-repeat;
  background-color: #b6bccdd1 !important;
}
#login .main-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
#login .main-container .top {
  margin-top: 100px;
  font-size: 30px;
  color: #ff962a;
  display: flex;
  justify-content: center;
}

#login .top  {
  font-size: 20px;
  overflow: hidden;
  
}
#login .top .title {
  margin-bottom: 20px;
  float: left;
}
#login .top .title {
  float: left;
  margin-bottom: 20px;
  margin-left: 10px;
}
#login .bottom {
  display:flex;
  justify-content: center;
  background-color:#fff;
  border-radius: 5px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
#login .bottom .title {
  text-align: center;
  font-size: 30px;
}
.bottom .container .title {
  margin: 30px 0px;;
}
.bottom .submit .row-login {
  width: 100%;
  background-color: #04468b;
  border-color: #04468b;
  margin: 20px 0px 10px 0px;
  padding: 15px 20px;
}
.bottom .submit {
  display: flex;
  justify-content: center;
}
.footer {
  margin-top: 50px;
  text-align: center;
}
.footer .msg1 {
  font-size: 18px;
  color: #fff;
  margin-bottom: 15px;
}
.footer .msg2 {
  font-size: 14px;
  color: #e3e3e3;
  margin-top: 70px;
}
.bottom .options {
  margin-bottom: 40px;
  color: #ff962a;
  display: flex;
  justify-content: space-between;
}
.bottom .options > a {
  color: #ff962a;
}
.bottom .options .register span:nth-child(1) {
  color: #8C8C8C;
}
</style>
