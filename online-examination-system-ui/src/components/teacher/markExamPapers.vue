<template>
<!-- 查询所有提交过来的试卷 -->
  <div class="exam">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="studentId" label="学号" width="180"></el-table-column>
      <el-table-column fixed="left" prop="studentName" label="学生姓名" width="180"></el-table-column>
      <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="description" label="介绍" width="200"></el-table-column>
      <el-table-column prop="institute" label="所属学院" width="120"></el-table-column>
      <el-table-column prop="major" label="所属专业" width="200"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="answerDate" label="考试日期" width="120"></el-table-column>
      <el-table-column prop="type" label="试卷类型" width="120"></el-table-column>
      <el-table-column prop="noEssayScore" label="非问答题总分" width="120"></el-table-column>
      <el-table-column prop="id" v-if="false"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150"> 
        <template #default="scope">
          <el-button @click="Review(scope.row.id)" type="primary" size="small">批阅</el-button>
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
  </div>
</template>

<script>
import { request } from '@/utils/request'
export default {
  data() {
    return {
      form: {}, //保存点击以后当前试卷的信息
      pagination: { //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
      resData: [],
      
    }
  },
  created() {
    this.getStudentExamInfo()
  },
  methods: {
    Review(id) { //编辑试卷
      console.log("id",id);
      this.resData.forEach(e => {
        if(e.id == id){
          console.log("e.id",e.id)
        this.$router.push({path: "/index/markExam",query:{id: e.id,
                                                          teqId: e.teqId,
                                                          examCode: e.examCode,
                                                          noEssayScore: e.noEssayScore,
                                                          studentId: e.studentId,
                                                          studentName: e.studentName,
                                                          subject: e.subject,
                                                          startTime: e.statTime,
                                                          endTime: e.endTime,
                                                          answerDate: e.answerDate}})
        }
        })

    //   this.$axios(`/api/exam/${examCode}`).then(res => { //根据试卷id请求后台
    //     if(res.data.code == 200) {
    //       this.form = res.data.data
    //     }
    //   })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    submit() { //提交修改后的试卷信息
      this.dialogVisible = false
    //   this.$axios({
    //     url: '/api/exam',
    //     method: 'put',
    //     data: {
    //       ...this.form
    //     }
    //   }).then(res => {
    //     if(res.data.code == 200) {
    //       this.$message({ //成功修改提示
    //         message: '更新成功',
    //         type: 'success'
    //       })
    //     }
    //     this.getExamInfo()
    //   })
    },
    deleteRecord(id) {

      // ElMessageBox.confirm("确定删除该记录吗,该操作不可逆！！！","删除提示",{
      //   confirmButtonText: '确定删除',
      //   cancelButtonText: '算了,留着',
      //   type: 'danger'
      // }).then(()=> { //确认删除
      //   // this.$axios({
      //   //   url: `/api/exam/${examCode}`,
      //   //   method: 'delete',
      //   // }).then(res => {
      //   //   this.getExamInfo()
      //   // })
      // }).catch(() => {

      // })
    },
    getStudentExamInfo() { //分页查询所有试卷信息
    console.log("开始获取数据")
    request({
        url: `/exam/getNoEssayScoreExamInfo/${this.pagination.current}/${this.pagination.size}`,
        method: 'get',
        params: {

        }
    }).then(res =>{
      console.log("获取的数据",res.data.data.records)
        this.pagination = res.data.data;
        let data = res.data.data.records;
        data.forEach(element => {
          this.resData.push(element)
        });
        // this.resData = res.data.data.records
        console.log("数据",this.resData)
    })
    //   this.$axios(`/api/exams/${this.pagination.current}/${this.pagination.size}`).then(res => {
    //     this.pagination = res.data.data
    //   }).catch(error => {
    //   })
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
