<template>
  <div id="login-container">
    <div class="top">
      <span class="title">在线考试系统</span>
    </div>
    <div id="form">
      <a-form
        :model="formState"
        name="normal_login"
        class="login-form"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
      >
        <a-form-item
          label="用户名"
          name="username"
          :rules="[{ required: true, message: '请输入您的用户名!' }]"
        >
          <a-input v-model:value="formState.username" placeholder="userName">
            <template #prefix>
              <UserOutlined class="site-form-item-icon" />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: '请输入您的密码!' }]"
        >
          <a-input-password
            v-model:value="formState.password"
            placeholder="Password"
          >
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>

        <div class="login-form-wrap">
          <a-form-item name="remember" no-style>
            <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
          </a-form-item>
          <a class="login-form-forgot"  @click.prevent="forgetPwd" href="">忘记密码？</a>
        </div>

        <a-form-item>
          <a-button
            :disabled="disabled"
            type="primary"
            html-type="submit"
            class="login-form-button"
          >
            登录
          </a-button>
          Or
          <a href="register">注册</a>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>
<script>
import { defineComponent, reactive, computed } from "vue";
import { UserOutlined, LockOutlined } from "@ant-design/icons-vue";
export default defineComponent({
  components: {
    UserOutlined,
    LockOutlined,
  },
  setup() {
    const formState = reactive({
      username: "",
      password: "",
      remember: true,
    });

    const onFinish = (values) => {
      console.log("Success:", values);
    };

    const onFinishFailed = (errorInfo) => {
      console.log("Failed:", errorInfo);
    };

    const disabled = computed(() => {
      return !(formState.username && formState.password);
    });
    return {
      formState,
      onFinish,
      onFinishFailed,
      disabled,
    };
  },
  methods:{
      forgetPwd(){
          alert("请联系管理员或者任课老师...");
      }
  }
});
</script>
<style>
#login-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background: url(../../assets/beijing.png);
}
#login-container.top{}
#form {
  width: 30%;
  height: 30%;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid rgb(19, 18, 18);
  border-radius: 24px;
  background: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
#login-container .login-form {
  max-width: 300px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
#login-container .login-form-wrap {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
#login-container .login-form-forgot {
  margin-bottom: 24px;
}
#login-container .login-form-button {
  width: 100%;
}
</style>