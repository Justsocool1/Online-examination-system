<template>
  <div id="manager">
    <a-form
    ref="formRef"
    name="custom-validation"
    :model="formState"
    :rules="rules"
    v-bind="layout"
    @finish="handleFinish"
    @validate="handleValidate"
    @finishFailed="handleFinishFailed"
  >
    <h3 class="alter">修改你的密码</h3>
    <a-form-item has-feedback label="密码" name="pass">
      <a-input-password v-model:value="formState.pass" type="password" autocomplete="off" />
    </a-form-item>
    <a-form-item has-feedback label="确认密码" name="checkPass">
      <a-input-password v-model:value="formState.checkPass" type="password" autocomplete="off" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" html-type="submit">修改</a-button>
      <a-button style="margin-left: 10px" @click="resetForm">重置</a-button>
    </a-form-item>
  </a-form>
  </div>

  
</template>
<script>
import { request } from '@/utils/request'
import { ElMessage, ElMessageBox } from "element-plus";
import { useRouter, useRoute } from 'vue-router'
import { defineComponent, reactive, ref } from 'vue';
export default defineComponent({
  setup() {

    const router = useRouter()
    const route = useRoute()
    const formRef = ref();
    const formState = reactive({
      pass: '',
      checkPass: '',
    });

    let validatePass = async (_rule, value) => {
      if (value === '') {
        return Promise.reject('请输入密码.');
      } else {
        if (formState.checkPass !== '') {
          formRef.value.validateFields('checkPass');
        }

        return Promise.resolve();
      }
    };

    let validatePass2 = async (_rule, value) => {
      if (value === '') {
        return Promise.reject('请再次输入密码.');
      } else if (value !== formState.pass) {
        return Promise.reject("两次数入的密码不一致");
      } else {
        return Promise.resolve();
      }
    };

    const rules = {
      pass: [{
        required: true,
        validator: validatePass,
        trigger: 'change',
      }],
      checkPass: [{
        validator: validatePass2,
        trigger: 'change',
      }],
    };
    const layout = {
      labelCol: {
        span: 4,
      },
      wrapperCol: {
        span: 14,
      },
    };

    const handleFinish = (values) => {
      console.log(values, formState);
      console.log("id",route.query.studentId);
      console.log("pwd",formState.pass)
      request({
        url: '/user/updatePassword',
        method: 'put',
        data: {
         id: route.query.studentId,
         role: route.query.role,
         password: formState.pass
        }
      }).then((res) => {
        if(res.data.success){
          ElMessage.success("修改密码成功，请重新登录！")
          router.push({path: "/"})
        }
      }).catch((err) => {
        
      });
      console.log("修改成功并清除token");
      localStorage.removeItem("token");

    };

    const handleFinishFailed = errors => {
      console.log("错误")
      console.log("id",route.query.studentId);
      console.log("role",route.query.role);

      console.log(errors);
    };

    const resetForm = () => {
      console.log("重置表单")
      formRef.value.resetFields();
    };

    const handleValidate = (...args) => {
      console.log(args);
    };

    return {
      formState,
      formRef,
      rules,
      layout,
      handleFinishFailed,
      handleFinish,
      resetForm,
      handleValidate,
    };
  },

});
</script>
<style lang="scss" scoped>
#manager {
  width: 600px;
  margin: 0 auto;
  margin-top: 100px;
  text-align: center;
  margin-bottom: 300px;
}
#manager .alter {
  margin: 30px 0px;
}
</style>