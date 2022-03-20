<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="试卷名称">
        <el-input v-model="form.source"></el-input>
      </el-form-item>
      <el-form-item label="介绍">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="所属学院">
         <el-select placeholder="请选择学院" v-model="form.instituteId">
            <el-option v-for="item in institutes" :key="item.instituteId" :value="item.instituteId" :label="item.institute"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属专业" >
        <el-select @click="getMajors()" placeholder="请选择专业" v-model="form.majorId">
          <el-option  v-for="item in majors" :key="index" :value="item.majorId" :label="item.major"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="年级">
        <el-select v-model="form.grade" placeholder="请选择年级">
        <el-option v-for="(item,index) in grades" :key="index" :value="item" >{{item}}</el-option>
  </el-select>
      </el-form-item>
      <el-form-item label="考试日期">
        <el-col :span="11">
          <div class="block">
      <span class="demonstration"></span>
      <el-date-picker
        v-model="form.examStartTime"
        type="datetime"
        placeholder="请选择考试开始时间"
      />
    </div>
        </el-col>
      </el-form-item>
      <el-form-item label="持续时间">
        <el-input v-model="form.totalTime"></el-input>
      </el-form-item>
      <el-form-item label="总分">
        <el-input v-model="form.totalScore"></el-input>
      </el-form-item>
      <el-form-item label="考试类型" >
        <el-select v-model="form.type" placeholder="请选择考试类型">
        <el-option
        v-for="(item,index) in types"
      :key="index"
      :value="item"
    />
  </el-select>
      </el-form-item>
      <el-form-item label="考生提示">
        <el-input type="textarea" v-model="form.tips"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="text" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus';
import { request } from '@/utils/request'
export default {
  data() {
    return {
      form: { //表单数据初始化
        source: null,
        description: null,
        instituteId: null,
        majorId: null,
        grade: null,
        examDate: null,
        examStartTime: null,
        totalTime: null,
        totalScore: null,
        type: null,
        tips: null,
        paperId: null,
      },
      types: ["测验","周考","月考","期中考试","期末考试"],
      grades: [2022,2021,2020,2019,2018],
      institutes: [],
      majors: [],
    };
  },
  created() {
    this.init();
  },
  methods: {
    init(){
      console.log("初始化...")
      request({
        url: '/exam/getInstitutes',
        method: 'get',
        params: {

        }
      }).then(res => {
        if(res.data.success){
          let instituteList = res.data.data;
          instituteList.forEach(e => {
            this.institutes.push(e);
          })   
          console.log("学院",this.institutes);

          
        }
      })
    },
    getMajors(){
      console.log("instituteId",this.form.instituteId)
      request({
        url: '/exam/getMajorsByInstituteId',
        method: 'get',
        params: {
          instituteId: this.form.instituteId
        }
      }).then(res =>{
        if(res.data.success){
          let majorList = res.data.data;
          majorList.forEach(e => {
            this.majors.push(e);
          })
          console.log("专业",this.majors);
        }
      })
    },
    formatTime(date) { //日期格式化
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    onSubmit() {
      let examDate = this.formatTime(this.form.examStartTime)
      this.form.examDate = examDate.substr(0,10)
      this.form.examStartTime = this.formatTime(this.form.examStartTime);
      console.log("数据",this.form)
      request({
        url: '/exam/addExam',
        method: 'post',
        data: {
          description: this.form.description,
          source: this.form.source,
          examDate: this.form.examDate,
          examStartTime: this.form.examStartTime,
          totalTime: this.form.totalTime,
          grade: this.form.grade,
          majorId: this.form.majorId,
          instituteId: this.form.instituteId,
          totalScore: this.form.totalScore,
          type: this.form.type,
          tips: this.form.tips
        }
      }).then((res) => {
        if(res.data.success){
          this.form = {},
          ElMessage.success("试卷添加成功");

          // this.$router.push({path: '/index/selectExam'})
        }
      }).catch((err) => {
        
      });
    //   this.$axios(`/api/examManagePaperId`).then(res => {
    //     this.form.paperId = res.data.data.paperId + 1 //实现paperId自增1
    //     this.$axios({
    //       url: '/api/exam',
    //       method: 'post',
    //       data: {
    //         ...this.form
    //       }
    //     }).then(res => {
    //       if(res.data.code == 200) {
    //         this.$message({
    //           message: '数据添加成功',
    //           type: 'success'
    //         })
    //         this.$router.push({path: '/selectExam'})
    //       }
    //     })
    //   })
    },
    cancel() { //取消按钮
      this.form = {}
    },
    
  }
};
</script>
<style lang="scss" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

