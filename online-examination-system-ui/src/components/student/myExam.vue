// 我的试卷页面
<template>
  <div id="myExam">
    <div class="title"><icon-font type="icon-shijuanguanli" :style="{fontSize: '23px', color: 'white'}"/>我的试卷</div>
    <div class="wrapper">
      <ul class="top">
        <li class="order">试卷列表</li>
        <li class="search-li"><div class="icon"><input type="text" placeholder="试卷名称" class="search" v-model="key" @keydown.enter="getExamInfo()"><i class="el-icon-search"></i></div></li>
        <li><el-button type="primary"  @click="getExamInfo()"><search-outlined/>搜索试卷</el-button></li>
      </ul>
      <ul class="paper" v-loading="loading">
        <li class="item" v-for="(item,index) in pagination.records" :key="index">
          <h4 @click="toExamMsg(item.examCode)">{{item.source}}</h4>
          <p class="name">{{item.source}}-{{item.description}}</p>
          <div class="info">
            <setting-filled spin/><span>{{item.examDate.substr(0,10)}}</span>
            <icon-font type="icon-shijian"/><span v-if="item.totalTime != null">限时{{item.totalTime}}分钟</span>
            <icon-font type="icon-shijuan-xianxing"/><span>满分{{item.totalScore}}分</span>
          </div>
        </li>
      </ul>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[6, 10, 20, 40]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { request } from '@/utils/request'
import { SearchOutlined } from '@ant-design/icons-vue';
import { createFromIconfontCN,SettingFilled } from '@ant-design/icons-vue';
const IconFont = createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/font_3260262_ees17zskgzm.js',
});
export default {
  // name: 'myExam'
  data() {
    return {
      loading: false,
      key: null, //搜索关键字
      allExam: null, //所有考试信息
      pagination: { //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      }
    }
  },
  components: {
    IconFont,
    SearchOutlined,
    SettingFilled
},
  created() {
    this.getExamInfo()
    this.loading = true
  },
  // watch: {
    
  // },
  methods: {
    //获取当前所有考试信息或搜索试卷
    getExamInfo() {
      if(this.key != null && this.key.length == 0){
           this.key = null;
          }
      console.log("获取当前所有考试信息")
      request({
        url: `/exam/getExams/${this.pagination.current}/${this.pagination.size}/${this.key}`,
        method: 'get',
        params: {
          
        }
      }).then(res => {
      console.log("搜索结果",res)
        if(res.data.success){
          this.pagination = res.data.data
          this.loading = false;
          console.log("搜索成功",this.pagination.records);
        } else{
          this.pagination.records = res.data.data
          this.loading = false;
          console.log(res.data.message)
          console.log(`没有查询到 ${this.key} 相关试卷` );
          
        }
      }).catch(error => {
        console.log(error);
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
    //跳转到试卷详情页
    toExamMsg(examCode) {
      this.$router.push({path: '/student/examInfo', query: {examCode: examCode}})
      console.log(examCode)
    }
  }
}
</script>

<style lang="scss" scoped>
.pagination {
  padding: 20px 0px 30px 0px;
  .el-pagination {
    display: flex;
    justify-content: center;
  }
}
.paper {
  h4 {
    cursor: pointer;
  }
}
.paper .item a {
  color: #000;
}
.wrapper .top .order {
  cursor: pointer;
}
.wrapper .top .order:hover {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}
.wrapper .top .order:visited {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}
.item .info i {
  margin-right: 5px;
  color: #0195ff;
}
.item .info span {
  margin-right: 14px;
}
.paper .item {
  width: 380px;
  border-radius: 4px;
  padding: 20px 30px;
  border: 1px solid #eee;
  box-shadow: 0 0 4px 2px rgba(217,222,234,0.3);
  transition: all 0.6s ease;
}
.paper .item:hover {
  box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
  transform: scale(1.03);
}
.paper .item .info {
  font-size: 14px;
  color: #88949b;
}
.paper .item .name {
  font-size: 14px;
  color: #88949b;
}
.paper * {
  margin: 20px 0;
}
.wrapper .paper {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.top .el-icon-search {
  position: absolute;
  right: 10px;
  top: 10px;
}
.top .icon {
  position: relative;
}
.wrapper .top {
  border-bottom: 1px solid #eee;
  margin-bottom: 20px;
}
#myExam .search-li {
  margin-left: auto;
}
.top .search-li {
  margin-left: auto;
}
.top li {
  display: flex;
  align-items: center;
}
.top .search {
  margin-left: auto;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #eee;
  box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
  transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
}
.top .search:hover {
  color: #0195ff;
  border-color: #0195ff;
}
.wrapper .top {
  display: flex;
}
.wrapper .top li {
  margin: 20px;
}
#myExam {
  width: 980px;
  margin: 0 auto;
}
#myExam .title {
  margin: 20px;
}
#myExam .wrapper {
  background-color: #fff;
}
</style>
