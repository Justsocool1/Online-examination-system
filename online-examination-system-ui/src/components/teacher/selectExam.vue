//查询所有考试
<template>
  <div class="exam">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="source" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="description" label="介绍" width="200"></el-table-column>
      <el-table-column prop="instituteId" label="所属学院" width="120"></el-table-column>
      <el-table-column prop="majorId" label="所属专业" width="200"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="examDate" label="考试日期" width="120"></el-table-column>
      <el-table-column prop="totalTime" label="持续时间" width="120"></el-table-column>
      <el-table-column prop="totalScore" label="总分" width="120"></el-table-column>
      <el-table-column prop="type" label="试卷类型" width="120"></el-table-column>
      <el-table-column prop="tips" label="考生提示" width="400"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button @click="edit(scope.row)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteRecord(scope.row.examCode)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10, 20]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑试卷信息"
      v-model="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
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
        </el-form>
      </section>
      <template v-slot:footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span> 
      </template>
      
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus';
import { request } from '@/utils/request'
import { ref,toRaw } from 'vue' 
export default {
  data() {
    return {
      grades: [2022,2021,2020,2019,2018],
      types: ["测验","周考","月考","期中考试","期末考试"],
      institutes: [],
      majors: [],
      examCode: null,
      form: {
        examCode: null,
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
      }, //保存点击以后当前试卷的信息
      pagination: { //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
      dialogVisible: false,
      test: null,
    }
  },
  created() {
    this.getExamInfo();
  },
  methods: {
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
   async edit(examInfo) { //编辑试卷
    console.log("选中数据",examInfo)
      this.dialogVisible = true;
      this.examCode = examInfo.examCode;
      
     await this.getInstitutes();
      
      console.log("ins",this.institutes);

      this.institutes.forEach(e=>{
        if(e.institute == examInfo.instituteId){
        this.form.instituteId = e.instituteId;
        }
      })
      console.log("id",this.form.instituteId)

     await this.getMajors();
      this.majors.forEach(e => {
        if(e.major == examInfo.majorId){
           this.form.majorId = e.majorId;
          }
        });
       console.log("mid",this.form.majorId)



 
    
      this.form.source = examInfo.source;
      this.form.description = examInfo.description; 
      this.form.grade = examInfo.grade;
      this.form.examDate = examInfo.examDate;
      this.form.examStartTime = examInfo.examStartTime;
      this.form.totalTime = examInfo.totalTime;
      this.form.totalScore = examInfo.totalScore;
      this.form.type = examInfo.type;
      this.form.tips = examInfo.tips;

    },
    parseDate(time){
      return new Date(Date.parse(time.replace(/-/g,"/")));
      
      
    },
    async getInstitutes(){
      console.log("执行getInstitutes")
        await request({
        url: '/exam/getInstitutes',
        method: 'get',
        params: {

        }
      }).then(res => {
        if(res.data.success){
          let instituteList = res.data.data; 
          for (let index = 0; index < instituteList.length; index++) {
            this.institutes[index] = instituteList[index];
            
          }
          console.log("学院",this.institutes);
        }
      });
    },
    async getMajors(){
      console.log("执行getMajors")
      console.log("instituteId",this.form.instituteId)
      await request({
        url: '/exam/getMajorsByInstituteId',
        method: 'get',
        params: {
          instituteId: this.form.instituteId
        }
      }).then(res =>{
        if(res.data.success){
          let majorList = res.data.data;
          for (let index = 0; index < majorList.length; index++) {
            this.majors[index] = majorList[index];
            
          }
          console.log("专业",this.majors);
        }
      })
    },
    handleClose(done) { //关闭提醒
      ElMessageBox.confirm("确认关闭?","提示",{
        confirmButtonText: '确认',
        cancelButtonText: '算了',
        type: 'warning'
      }).then(() =>{
        done();
      });
    },
    submit() { //提交修改后的试卷信息
      this.dialogVisible = false
      this.form.examCode = this.examCode;
      console.log("开始时间",this.form.examStartTime)
      this.form.examStartTime = this.parseDate(this.form.examStartTime);
      console.log("开始时间",this.form.examStartTime)
      if(this.form.examStartTime != null){
      this.form.examDate = this.formatTime(this.form.examStartTime).substr(0,10);
      this.form.examStartTime = this.formatTime(this.form.examStartTime)
      }
      console.log("开始时间",this.form.examStartTime)
      console.log("更新的数据",this.form)
      request({
        url: '/exam/updateExam',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.success){
          ElMessage.success("更新成功");
        }
        this.getExamInfo();
      })
    },
    deleteRecord(examCode) {
      console.log(examCode)
      ElMessageBox.confirm("确定删除该记录吗,该操作不可逆！！！","删除提示",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着',
        type: 'danger'
      }).then(res =>{
        request({
          url: '/exam/deleteExam',
          method: 'delete',
          data: {
            examCode: examCode
          }
        }).then(res =>{
          console.log("删除之后重新刷新数据")
          this.getExamInfo();
          console.log("刷新完毕")
          
        })
      })
    },
   async getExamInfo() { //分页查询所有试卷信息
    console.log("展示数据")
    let examName = null;
     await request({
        url: `/exam/getExams/${this.pagination.current}/${this.pagination.size}/${examName}`
      }).then(res => {
        this.pagination = res.data.data;
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val
      this.getExamInfo()
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getExamInfo()
    },
  },
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit{
    margin-left: 20px;
  }
}
</style>
