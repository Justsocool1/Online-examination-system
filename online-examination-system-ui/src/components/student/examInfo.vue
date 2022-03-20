// 点击试卷后的缩略信息
<template>
  <div id="msg">
    <div class="title">
      <span>试卷列表</span>
      <span>/  {{examData.source}}</span>
    </div>
    <div class="wrapper">
      <ul class="top">
        <li class="example">{{examData.source}}</li>
        <li><i class="iconfont icon-pen-"></i></li>
        <li><i class="iconfont icon-share"></i></li>
        <li class="right">
          <div>
            <span class="count">总分</span>
            <span class="score">{{score[0]+score[1]+score[2]+score[3]}}</span>
          </div>
        </li>
      </ul>
      <ul class="bottom">
        <li>更新于{{examData.examDate}}</li>
        <li>来自 {{examData.institute}}</li>
        <li class="btn">{{examData.type}}</li>
        <li class="right">
          <!-- <el-button @click="toAnswer(examData.examCode)">开始答题</el-button>  -->
          <a-button type="primary" @click="toAnswer(examData.examCode)" v-if="examStartFlag">开始答题</a-button>
          <a-popover title="提示: " trigger="hover" v-else>
          <template #content>
          <p>目前未在允许进入考试时间范围内</p>
          <!-- <p>Content</p> -->
          </template>
          <a-button>开始答题</a-button>
          </a-popover>
          </li>
      </ul>
      <ul class="info">
        <li @click="dialogVisible = true"><a href="javascript:;"><i class="iconfont icon-info"></i>考生须知</a></li>
      </ul>
    </div>
    <div class="content">
      <a-collapse v-model:activeKey="activeKey">
    <a-collapse-panel key="1" header="考试详情">
      <div class="title">
              <span>考试科目: {{examData.source}}</span><i class="header-icon el-icon-info"></i>
              <span class="time">总分：{{examData.totalScore}}分  考试时间: {{examData.totalTime}}分钟 </span>
            </div>

<div class="titlei">考试时间：{{this.examStartTime}} - {{getEndTime(this.examStartTime)}}</div>
<div class="titlei">选择题 (共{{topicCount[0]}}题 共计{{score[0]}}分)</div>
   <div class="titlei">判断题 (共{{topicCount[2]}}题 共计{{score[2]}}分)</div>
   <div class="titlei">填空题 (共{{topicCount[1]}}题  共计{{score[1]}}分)</div>
<div class="titlei">问答题 (共{{topicCount[3]}}题 共计{{score[3]}}分)</div>
    </a-collapse-panel>


  </a-collapse>
    </div>
<!-- ================================================================= -->
    <!--考生须知对话框-->
    <el-dialog
      title="考生须知"
      v-model="dialogVisible"
      width="30%">
      <span>{{examData.tips}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">知道了</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { request } from '@/utils/request'
export default {
  data() {
    return {
      activeKey: true ,
      dialogVisible: false, //对话框属性
      activeName: '0',  //默认打开序号
      topicCount: [],//每种类型题目的总数
      score: [],  //每种类型分数的总数
      examData: { //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {  //试卷信息

      },
      examStartTime: "", //考试开始时间
      examStartFlag: false,
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    //初始化页面数据
    init() {
      let examCode = this.$route.query.examCode //获取路由传递过来的试卷编号
      request({
          url: '/exam/getExamInfoByExamCode',
          method: 'get',
          params: {
            examCode: examCode
          }
      }).then((res) => {
          let resData = res.data.data[0];
          console.log(resData)
          this.examStartTime = String(resData.examStartTime);
          console.log("开始时间",this.examStartTime)


          let startTime = this.parseDate(this.examStartTime);
          // startTime = this.parseDate("2019/3/21 16:00:00")
          let nowTime = new Date();
          nowTime = this.parseDate("2019/3/21 16:01:00");
          this.examStartFlag = this.comparingDate(startTime,nowTime)

        //   let testTime = this.parseDate("2022/3/13 16:30:00");
        //   console.log("测试时间",testTime);
        //  let changeTestTime = testTime.setHours(testTime.getHours() + 1);
        //   console.log("测试时间",testTime);
        //   console.log("改变测试时间",new Date(changeTestTime));


          console.log("时间",this.examStartTime)
          resData.examDate = resData.examDate.substr(0,10);
          console.log("examDate",resData.examDate)
          this.examData = {...resData}
          console.log("试卷数据",this.examData)
          console.log("总分",this.examData.totalScore)
          let paperId = this.examData.paperId
          console.log("papaerId",paperId)
          request({
           url: '/paper/getPaperById',
           method: 'get',
           params: {
               paperId: paperId
           }
          }).then(res => {
             this.topic = {...res.data.data}
             console.log("试卷信息topic",this.topic)
             let keys = Object.keys(this.topic) //对象转数组
             console.log("key",keys)
             keys.forEach(element => {
                 console.log(element)
                 let data = this.topic[element]
                 console.log("data",data)
                 console.log("data长度",data.length)
                 this.topicCount.push(data.length)
                 console.log("topicCount",this.topicCount)
                 let currentScore = 0;
                 for (let index = 0; index < data.length; index++) {
                   
                     currentScore += Number(data[index].score);
                     
                 }
                 this.score.push(currentScore);
                 console.log("分数",currentScore)
                 console.log("题目数",this.topicCount);
                 console.log("填空题数",this.topicCount[1]);
                 
             })
          })
      }).catch((err) => {
          
      });
    },
    toAnswer(id) {
      this.$router.push({path:"/answer",query:{examCode: id}})
    },
    getMyTime(date) { //日期格式化
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    comparingDate(startTime,nowTime){
     let dif = (nowTime - startTime)/3600/1000
     console.log("相差",dif)
    //  let deadline = 1.8e6
     let deadline = 0.5
     console.log("三十分钟",deadline)
      if(dif >= 0 && dif < deadline){
        console.log("允许进入考试")
        return true;
      } else {
        console.log("禁止进入考试")
        return false;
      }
    },
    parseDate(time){
      return new Date(Date.parse(time.replace(/-/g,"/")));
    },
    getEndTime(time){
       let endTime = this.parseDate(time);
      let totalTime = this.examData.totalTime;
      endTime.setMinutes(endTime.getMinutes() + totalTime);

      return this.getMyTime(endTime);
      
    }
  }
}
</script>

<style lang="scss" scoped>
.bottom {
  .right{
    .el-button{
      color: #409EFF;
      border-color: #c6e2ff;
      background-color: #ecf5ff;
    }
  }
}
.right {
  margin-left: auto;
}
.inner .contenti .question {
  margin-left: 40px;
  color: #9a9a9a;
  font-size: 14px;
}
.content .inner .titlei {
  margin-left: 20px;
  font-size: 16px;
  color: #88949b;
  font-weight: bold;
}
.titlei {
  margin-left: 20px;
  font-size: 16px;
  color: #88949b;
  font-weight: bold;
}
.title {
  font-size: 16px;
  margin-left: 420px;
  color: #999;
}
.content .title .time {
  font-size: 16px;
  margin-left: 420px;
  color: #999;
}
.content .stitle {
  background-color: #0195ff;
}
.content .title span {
  margin-right: 10px;
}
#msg .content .title {
  font-size: 20px;
  margin: 0px;
  display: flex;
  align-items: center;
}
.content {
  margin-top: 20px;
  background-color: #fff;
}
.content .header {
  padding: 10px 30px;
}
.wrapper .info {
  margin: 20px 0px 0px 20px;
  border-top: 1px solid #eee;
  padding: 20px 0px 10px 0px;
}
.wrapper .info a {
  color: #88949b;
  font-size: 14px;
}
.wrapper .info a:hover {
  color: #0195ff;
}
.wrapper .bottom .btn {
  cursor: pointer;
  padding: 5px 10px;
  border: 1px solid #88949b;
  border-radius: 4px;
} 
.wrapper .bottom {
  display: flex;
  margin-left: 20px;
  color: #999;
  font-size: 14px;
  align-items: center;
}
.wrapper .bottom li {
  margin-right: 14px;
}
#msg {
  background-color: #eee;
  width: 980px;
  margin: 0 auto;
}
#msg .title {
  margin: 20px;
}
#msg .wrapper {
  background-color: #fff;
  padding: 10px;
}
.wrapper .top {
  display: flex;
  margin: 20px;
  align-items: center;
}
.wrapper .top .right {
  margin-left: auto;
}
.wrapper .top .example {
  color: #333;
  font-size: 22px;
  font-weight: 700;
}
.wrapper .top li i {
  margin-left: 20px;
  color: #88949b;
}
.wrapper .right .count {
  margin-right: 60px;
  color: #fff;
  padding: 4px 10px;
  background-color: #88949b;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  border: 1px solid #88949b;
}
.wrapper .right .score {
  position: absolute;
  left: 53px;
  top: -5px;
  padding: 1px 12px;
  font-size: 20px;
  color: #88949b;
  border: 1px dashed #88949b;
  border-left: none;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  font-weight: bold;
}
.wrapper .right div {
  position: relative;
}
</style>
