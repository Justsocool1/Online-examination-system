<template>
  <a-form
    ref="formRef"
    :model="formState"
    :rules="rules"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
    v-bind="layout"
    @finish="handleFinish"
    @validate="handleValidate"
    @finishFailed="handleFinishFailed"
  >
    <a-form-item ref="teacherId" label="工号" name="teacherId">
      <a-input v-model:value="formState.teacherId" />
    </a-form-item>
    <a-form-item ref="teacherName" label="姓名" name="teacherName">
      <a-input v-model:value="formState.teacherName" />
    </a-form-item>
    <a-form-item label="性别" name="sex">
    <a-radio-group name="radioGroup" v-model:value="formState.sex">
        <a-radio value="1">男</a-radio>
        <a-radio value="2">女</a-radio>
    </a-radio-group>
    </a-form-item>
    <a-form-item label="身份证" name="cardId">
      <a-input v-model:value="formState.cardId" />
    </a-form-item>
    <a-form-item has-feedback label="密码" name="pwd">
      <a-input-password
        v-model:value="formState.pwd"
        type="password"
        autocomplete="off"
      />
    </a-form-item>
    <a-form-item has-feedback label="确认密码" name="checkPass">
      <a-input-password
        v-model:value="formState.checkPass"
        type="password"
        autocomplete="off"
      />
    </a-form-item>
    <a-form-item label="学院" name="institute">
      <a-select v-model:value="formState.instituteId" placeholder="请选择您所在学院" @click="getInstitutes" @select="getMajorsByInstituteId"> 
        <a-select-option  v-for="item in items" :key="item.instituteId" :value="item.instituteId">{{item.institute}}</a-select-option>     
        <!-- <a-select-option value="网络空间安全学院">网络空间安全学院</a-select-option>
        <a-select-option value="大气科学学院">大气科学学院</a-select-option>
        <a-select-option value="通信工程学院">通信工程学院</a-select-option>
        <a-select-option value="商学院">商学院</a-select-option>
        <a-select-option value="文化艺术学院">文化艺术学院</a-select-option> dropdownVisibleChange -->
      </a-select>      
    </a-form-item>
    <a-form-item label="电话" name="tel">
      <a-input v-model:value="formState.tel" />
    </a-form-item>
    <a-form-item label="邮箱" name="email">
      <a-input v-model:value="formState.email" />
    </a-form-item>
    <a-form-item label="职称" name="type">
      <a-input v-model:value="formState.type" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" html-type="submit">添加</a-button>
      <a-button style="margin-left: 10px" @click="resetForm">清空</a-button>
    </a-form-item>
  </a-form>
</template>
<script>
import { defineComponent, reactive, ref,h } from "vue";
import { Modal } from 'ant-design-vue';
import { request } from '@/utils/request'
import router from '@/router'
export default defineComponent({
  setup() {
    const formRef = ref();
    const formState = reactive({
      // teacherId: "2018666666",
      // teacherName: "张三",
      // cardId: "566966666606066666",
      // pwd: "123456",
      // checkPass: "123456",
      // sex: "1",
      // instituteId: undefined,
      // type: "",
      // tel: "17666667777",
      // email: "zhangsan@qq.com",
        teacherName: "",
        teacherId: "",
        instituteId: undefined,
        tel: "",
        email: "",
        pwd: "",
        checkPass: "",
        cardId: "",
        sex: null,
        type: "",
        role: 1
    });
     var items = ref([]);
    let validatePass = async (rule, value) => {
      if (value === "") {
        return Promise.reject("请输入密码");
      } else {
        if (formState.checkPass !== "") {
          formRef.value.validateFields("checkPass");
        }

        return Promise.resolve();
      }
    };
    let validatePass2 = async (rule, value) => {
      if (value === "") {
        return Promise.reject("请再次输入密码");
      } else if (value !== formState.pass) {
        return Promise.reject("两次输入的密码不一致!");
      } else {
        return Promise.resolve();
      }
    };
    let validateTeacherId = async (rule,value) =>{
    console.log("value",value);
    let success = await checkSameid(value);
    console.log("success",success);
      console.log("value.length",value.length)
      if(value === ""){
          console.log("工号为空")
          return Promise.reject("请输入您的工号");
        } else if(value.length < 8 || value.length >10){
          console.log("不符号长度")
          return Promise.reject("工号位数应该为 8 到 10");
        } else if(!(new RegExp(/^[1-9]\d*$/, "g").test(value))){
          console.log("不符合规范");
          return Promise.reject("工号必须为数字");
        } else if(!success){
          console.log("存在相同工号");
          return Promise.reject("该工号已注册过账号!")
        } else {
          console.log("通过校验")
          return Promise.resolve();
        }
      
    }

    const rules = {
      teacherId: [
        {
          required: true,
          validator: validateTeacherId,
          trigger: "change",
        },
      ],
      teacherName: [
        {
          required: true,
          message: "请输入姓名",
          trigger: "change",
        },
      ],
      cardId: [
        {
          required: true,
          message: "请输入您的身份证",
          trigger: "change",
        },
        {
          message: "请输入正确的身份证号码",
          trigger: "change",
          pattern:  new RegExp(/\d{18}$/),
          
        }
      ],
      pwd: [
        {
          required: true,
          validator: validatePass,
          trigger: "change",
        },
      ],
      checkPass: [
        {
          validator: validatePass2,
          trigger: "change",
        },
      ],
      sex: [
        {
          message: "必须选择您的性别",
          required: true,
          trigger: "change",
        },
      ],
      institute: [
        {
          required: true,
          message: "请选择您的学院",
          trigger: "change",
        },
      ],
      tel: [
        {
          required: true,
          message: "请输入您的电话号码",
          trigger: "change",
        },
        { 
          trigger: "change", 
          message: "请输入正确的电话号码",
          pattern: new RegExp(/^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/),
          }
      ],
      email: [
        {
          required: true,
          message: "请输入您的邮箱地址",
          trigger: "change",
        },
        { 

          trigger: "change", 
          message: "请输入正确的邮箱地址",
          pattern: new RegExp(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/),
          }
        ],
    };

      let checkSameid = async (value) => {
        console.log("value1",value)
        let data;
       await request({
        url: '/user/getSameId',
        method: 'get',
        params: {
          userId: value,
          role: 1,
        }
      }).then((res) => {
        console.log("res.data.success",res.data.success)
        data = res.data.success;
      }).catch((err) => {
        
      });
      console.log("data",data);
      return data;
    
    };
    const getInstitutes = () => {
        request({
          url: "/user/getInstitutes",
          method: "get",
          params: {
            
          }
        }).then((res) => {  
          console.log("初始items")
         console.log(items.value)
          let resData = res.data.data;
          console.log(resData);
          if(resData != null){
            // resData.forEach(element => {
            // items.push(element);              
            // });
            for (let index = 0; index < resData.length; index++) {
              items.value[index] = resData[index];
            }
            
            console.log("ins")
            console.log(items.value)


          } else {
           console.log("获取学院失败");
           items.value = [];
          }
        })
        
    };
    const handleFinish = values => {
      console.log(values, formState);
        request({
          url: "/user/register",
          method: "post",
          data: {
            teacherId:  formState.teacherId,
            teacherName:  formState.teacherName,
            instituteId: formState.institute,
            tel: formState.tel,
            email: formState.email,
            pwd: formState.pwd,
            cardId: formState.cardId,
            sex: formState.sex,
            role: 1
          }
        }).then((res) => { 
          if(res.data.success){
            Modal.success({
            title: '注册成功',
            content: h('div', {}, [h('p', '请重新登录')]),
            });
            // console.log("注册成功!")
            // alert("注册成功，点击确定返回到登录页")
            router.push({path: "/"})
          } else{
              console.log(res.data.message) 
          }
        }).catch((err) => {
            
        });

    };  



    const handleFinishFailed = errors => {
      console.log("错误")


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
      formRef,
      labelCol: {
        span: 4,
      },
      wrapperCol: {
        span: 14,
      },
      formState,
      rules,
      items,
      resetForm,
      getInstitutes,
      handleFinishFailed,
      handleValidate,
      handleFinish,
      validateTeacherId,
      checkSameid
    };
  },
});

</script>