// 所有学生
<template>
  <div class="all">
       <div class="search">
      <div class="search">
        <span>学号&nbsp&nbsp</span>
        <el-input
          v-model="studentId"
          class="w-50 m-2"
          size="large"
          placeholder="Please Input1"
          :suffix-icon="Search"
          @keydown.enter="search()"
        />
      </div>
      <div class="search">
        <span >姓名&nbsp&nbsp</span>
        <el-input
          v-model="studentName"
          class="w-50 m-2"
          size="large"
          placeholder="Please Input2"
          :suffix-icon="Search"
          @keydown.enter="search()"
        />
      </div>
      <div class="search">
        <a-button type="primary" @click="search()">
          <template #icon><SearchOutlined /></template>
          搜索
        </a-button>
      </div>
    </div>
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="studentId" label="学号" width="180"></el-table-column>
      <el-table-column  prop="studentName" label="姓名" width="180"></el-table-column>
      <el-table-column prop="instituteId" label="学院" width="200"></el-table-column>
      <el-table-column prop="majorId" label="专业" width="200"></el-table-column>
      <el-table-column prop="grade" label="年级" width="200"></el-table-column>
      <el-table-column prop="clazzId" label="班级" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="120"></el-table-column>
      <el-table-column fixed="right" label="查看成绩" width="150">
        <template #default="scope">
          <el-button @click="checkGrade(scope.row.studentId)" type="primary" size="small">查看成绩</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page"
    ></el-pagination>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from "element-plus";
import { SearchOutlined } from '@ant-design/icons-vue';
import { request } from "@/utils/request";
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      studentId: null,
      studentName: null,
    };
  },
    components: {
    SearchOutlined
  },
  created() {
    this.getStudentInfo();
  },
  methods: {
    async getStudentInfo() {
      //分页查询所有学生信息
      if(this.studentId != null && this.studentId.length == 0){
           this.studentId = null;
        }

      if(this.studentName != null && this.studentName.length == 0){
           this.studentName = null;
          }
      await request({
        url: `/user/getStudentInfos/${this.pagination.current}/${this.pagination.size}/${this.studentId}/${this.studentName}`,
        method: "get",
        params: {},
      }).then((res) => {
          console.log("学生数据",res.data.data)
        this.pagination = res.data.data;
      });

    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getAnswerInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getAnswerInfo();
    },
    checkGrade(studentId) {
      this.$router.push({ path: "/index/grade", query: { studentId: studentId } });
    }
  }
};
</script>
<style lang="scss" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
.search {
  padding: 0px 40px;
  margin-left: 100px;
  margin-bottom: 20px;
  display: flex;

}
</style>
