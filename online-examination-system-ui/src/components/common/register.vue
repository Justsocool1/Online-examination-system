<template>
<a-page-header
    style="border: 1px solid rgb(235, 237, 240)"
    title="返回"
    sub-title="返回到登录页"
    @back="() => $router.go(-1)"
  />
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
    <a-form-item ref="studentId" label="学号" name="studentId">
      <a-input v-model:value="formState.studentId" />
    </a-form-item>
    <a-form-item ref="studentName" label="姓名" name="studentName">
      <a-input v-model:value="formState.studentName" />
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
    <a-form-item has-feedback label="密码" name="pass">
      <a-input-password
        v-model:value="formState.pass"
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
    <a-form-item label="年级" name="grade">
      <a-select v-model:value="formState.grade" placeholder="请选择您所在年级">
        <a-select-option value="2022">2022</a-select-option>
        <a-select-option value="2021">2021</a-select-option>
        <a-select-option value="2020">2020</a-select-option>
        <a-select-option value="2019">2019</a-select-option>
        <a-select-option value="2018">2018</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="学院" name="institute">
      <a-select v-model:value="formState.institute" placeholder="请选择您所在学院" @click="getInstitutes" @select="getMajorsByInstituteId"> 
        <a-select-option  v-for="item in items" :key="item.instituteId" :value="item.instituteId">{{item.institute}}</a-select-option>     
        <!-- <a-select-option value="网络空间安全学院">网络空间安全学院</a-select-option>
        <a-select-option value="大气科学学院">大气科学学院</a-select-option>
        <a-select-option value="通信工程学院">通信工程学院</a-select-option>
        <a-select-option value="商学院">商学院</a-select-option>
        <a-select-option value="文化艺术学院">文化艺术学院</a-select-option> dropdownVisibleChange -->
      </a-select>
      
    </a-form-item>
    <a-form-item label="专业" name="major">
      <a-select v-model:value="formState.major" placeholder="请选择您所在专业" @select="getClassesByMajorId" :default-value="majors[0]" >
        <a-select-option  v-for="item in majors" :key="item.majorId" :value="item.majorId">{{item.major}}</a-select-option>
      <!-- <a-select-option value="">网络工程专业</a-select-option> -->
      <!-- <a-select-option value="网络工程专业">网络工程专业</a-select-option> -->
      </a-select>
    </a-form-item>
   <a-form-item label="班级" name="clazz">
      <a-select v-model:value="formState.clazz" placeholder="请选择您所在班级" >
        <a-select-option  v-for="item in clazzes" :key="item.clazzId" :value="item.clazzId">{{item.clazzName}}</a-select-option>
      <!-- <a-select-option value="181">181</a-select-option> -->
      </a-select>
    </a-form-item> 
    <a-form-item label="电话" name="tel">
      <a-input v-model:value="formState.tel" />
    </a-form-item>
    <a-form-item label="邮箱" name="email">
      <a-input v-model:value="formState.email" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" html-type="submit">注册</a-button>
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
      studentId: "",
      studentName: "",
      cardId: "",
      pass: "",
      checkPass: "",
      sex: "",
      grade: undefined,
      instituteId: undefined,
      majorId: undefined,
      clazzId: undefined,
      tel: "",
      email: "",

    });
    // const formState = reactive({
    //   studentId: "2018666666",
    //   studentName: "张三",
    //   cardId: "566966666606066666",
    //   pass: "123456",
    //   checkPass: "123456",
    //   sex: "1",
    //   grade: undefined,
    //   instituteId: undefined,
    //   majorId: undefined,
    //   clazzId: undefined,
    //   tel: "17666667777",
    //   email: "zhangsan@qq.com",

    // });
     var items = ref([]);
     var majors = ref([]);
     var clazzes = ref([]);
    //  var clazzes = ref([{clazzId: 181,clazzName: "181班"}]);
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
    let validateStudentId = async (rule,value) =>{
    console.log("value",value);
    let success = await checkSameid(value);
    console.log("success",success);
    

      console.log("value.length",value.length)

      if(value === ""){
          console.log("学号为空")
          return Promise.reject("请输入您的学号");
        } else if(value.length < 8 || value.length >10){
          console.log("不符号长度")
          return Promise.reject("学号位数应该为 8 到 10");
        } else if(!(new RegExp(/^[1-9]\d*$/, "g").test(value))){
          console.log("不符合规范");
          return Promise.reject("学号必须为数字");
        } else if(!success){
          console.log("存在相同学号");
          return Promise.reject("该学号已注册过账号!")
        } else {
          console.log("通过校验")
          return Promise.resolve();
        }
      
    }

    const rules = {
      studentId: [
        {
          required: true,
          validator: validateStudentId,
          trigger: "change",
        },
      ],
      studentName: [
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
      pass: [
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
          required: true,
          trigger: "change",
          message: "请选择您的性别"
        },
      ],
      grade: [
        {
          required: true,
          message: "请选择您的年级",
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
      major: [
        {
          required: true,
          message: "请选择您的专业",
          trigger: "change",
        },
      ],
      clazz: [
        {
          required: true,
          message: "请选择您的班级",
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
        let data;
       await request({
        url: '/user/getSameId',
        method: 'get',
        params: {
          userId: value,
          role: 2
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
          formState.major = undefined;
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
    const getMajorsByInstituteId = () => {
        request({
          url: "/user/getMajorsByInstituteId",
          method: "get",
          params: {
           instituteId: formState.institute
          }
        }).then((res) => {  
          console.log("初始majors")
         console.log(majors.value)
          let resData = res.data.data;
          console.log(resData);
          formState.clazz = undefined;
          if(resData != null){
            for (let index = 0; index < resData.length; index++) {
              majors.value[index] = resData[index];
            }
           
            console.log("majors")
            console.log(majors.value)


          } else {
           console.log("获取专业失败");
           majors.value = [];
          }
        })
    }

    const getClassesByMajorId = () => {
      console.log(formState.major)
        request({
          url: "/user/getClassesByMajorId",
          method: "get",
          params: {
           majorId: formState.major
          }
        }).then((res) => {  
          console.log("初始class")
         console.log(majors.value)
          let resData = res.data.data;
          console.log(resData);
          if(resData != null){
            for (let index = 0; index < resData.length; index++) {
              clazzes.value[index] = resData[index];
            }
            console.log("class")
            console.log(clazzes.value)

          } else {
           console.log("获取班级失败");
           clazzes.value = [];
          }
        })
    };

    const handleFinish = values => {
      console.log(values, formState);
        request({
          url: "/user/register",
          method: "post",
          data: {
            studentId: formState.studentId,
            studentName: formState.studentName,
            grade: formState.grade,
            majorId: formState.major,
            clazzId: formState.clazz,
            instituteId: formState.institute,
            tel: formState.tel,
            email: formState.email,
            pwd: formState.pass,
            cardId: formState.cardId,
            sex: formState.sex,
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
      other: "",
      formState,
      rules,
      items,
      majors,
      clazzes,
      resetForm,
      getInstitutes,
      getMajorsByInstituteId,
      getClassesByMajorId,
      handleFinishFailed,
      handleValidate,
      handleFinish,
      validateStudentId,
      checkSameid
    };
  },
});

</script>