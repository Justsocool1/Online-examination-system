//查询所有题库
<template>
  <div class="exam">
    <div class="search">
      
    <div class="search">
      <span>试卷名称&nbsp&nbsp</span>
    <el-input
      v-model="examName"
      class="w-50 m-2"
      size="large"
      placeholder=""
      :suffix-icon="Search"
      @keydown.enter="search()"
    />
  </div>
  <div class="search">
      <span>所属章节&nbsp&nbsp</span>
    <el-input
      v-model="section"
      class="w-50 m-2"
      size="large"
      placeholder=""
      :suffix-icon="Search"
      @keydown.enter="search()"
    />
  </div>
  <div class="search">
      <span>题目类型&nbsp&nbsp</span>
    <el-input
      v-model="questionType"
      class="w-50 m-2"
      size="large"
      placeholder=""
      :suffix-icon="Search"
      @keydown.enter="search()"
    />
  </div>
  <div class="search">
      <span>难度等级&nbsp&nbsp</span>
    <el-input
      v-model="level"
      class="w-50 m-2"
      size="large"
      placeholder=""
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
  
    
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="question" label="题目信息" width="490"></el-table-column>
      <el-table-column prop="section" label="所属章节" width="200"></el-table-column>
      <el-table-column prop="type" label="题目类型" width="200"></el-table-column>
      <el-table-column prop="score" label="试题分数" width="150"></el-table-column>
      <el-table-column prop="level" label="难度等级" width="133"></el-table-column>
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
import { ElMessage, ElMessageBox } from 'element-plus';
import { request } from '@/utils/request'
import { SearchOutlined } from '@ant-design/icons-vue';
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      examName: null,
      section: null,
      questionType: null,
      level: null,
    }
  },
  components:{
    SearchOutlined
  },
  created() {
    this.getAnswerInfo();
  },
  methods: {
   async getAnswerInfo() {
     // 分页查询所有试卷信息
     if(this.examName != null && this.examName.length == 0){
           this.examName = null;
          }
      if(this.section != null && this.section.length == 0){
           this.section = null;
          }
      if(this.type != null && this.type.length == 0){
           this.type = null;
          }
      if(this.level != null && this.level.length == 0){
           this.level = null;
          }
     let type = null;
     if(this.questionType != null){
      switch(this.questionType){
        case "选择题":
          type = 1;
          break;
        case "填空题":
          type = 2;
          break;
        case "判断题":
          type = 3;
          break;
        case "问答题":
          type = 4;
          break;
      }
     }
      console.log("问题类型",this.questionType)
      console.log("试卷名称",this.examName)
      console.log("等级",this.level)
      console.log("章节",this.section)
      await request({
        url: `/paper/getPapers/${this.pagination.current}/${this.pagination.size}/${this.examName}/${this.section}/${type}/${this.level}`,
        method: 'get',
        params: {

        }
      }).then((result) => {
          this.pagination = result.data.data;
      }).catch((err) => {
          
      });

    },
    search(){
      console.log("搜索中");
      this.getAnswerInfo();
      console.log("搜索完成");

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
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    }
  }
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
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #DD5862 !important;
  }
}
  .el-table .warning-row {
    background: #000 !important;
    
  }

  .el-table .success-row {
    background: #DD5862;
  }

  .search {
    padding: 0px 40px;
    margin-left: 40px;
    margin-bottom: 20px;
    display: inline-flex;
    // justify-content: center;
    // align-items: center;

  }


</style>
