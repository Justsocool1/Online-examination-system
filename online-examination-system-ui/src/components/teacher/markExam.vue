<template>
  <!-- <div v-for="(item,index) in essayQuestions" :key="index">
    <p>{{index + 1}}.{{item.question}}</p>
    <div style="margin: 24px 0" />
    <a-textarea
      :value="item.stuAnswer"
      readonly="true"
      :auto-size="{ minRows: 2, maxRows: 5 }"
    />
    得分:<a-input-number id="score" v-model="score[index]" :min="0" :max="50" />
  </div>
  <a-button type="primary" @click="commit()">提交</a-button>
  <a-button @click="cancle()">取消</a-button> -->
  <a-form
    v-bind="layout"
    name="nest-messages"
    :validate-messages="validateMessages"
    @finish="onFinish"
  >
    <a-descriptions title="阅卷">
    <a-descriptions-item label="学生姓名">{{studentName}}</a-descriptions-item>
    <a-descriptions-item label="学号">{{studentId}}</a-descriptions-item>
    <a-descriptions-item label="考试科目">{{subject}}</a-descriptions-item>
  </a-descriptions>
  <a-divider />
    <template v-for="(item,index) in essayQuestions" :key="index">
      <a-form-item label="题目">
        <p>{{ index + 1 }}.{{ item.question }}</p>
      </a-form-item>
      <a-form-item label="考生答案">
        <a-textarea v-model:value="item.stuAnswer" :readonly="true"/>
      </a-form-item>
      <a-form-item label="分数">
        <a-input-number v-model:value="score[index]" precision="1" min="0" :max="item.score"/>
      </a-form-item>
    </template>
    <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 8 }">
      <a-button type="primary" @click="commit()">提交</a-button>
      <a-button type="default" @click="cancle()">取消</a-button>
    </a-form-item>
  </a-form>
</template>
<script>
import { ElMessageBox } from 'element-plus';
import { request } from "@/utils/request";
export default {
  data() {
    return {
      studentId: "",
      studentName: "",
      subject: "",
      essayQuestions: [],
      score: [],
      layout: {
        labelCol: {
          span: 8,
        },
        wrapperCol: {
          span: 16,
        },
      },
    };
  },
  created() {
    this.getEssay();
  },
  methods: {
    getEssay() {
      let id = this.$route.query.id;
      let teqId = this.$route.query.teqId;
      this.studentId = this.$route.query.studentId;
      this.studentName = this.$route.query.studentName;
      this.subject = this.$route.query.subject;
      console.log("teqId", teqId);
      console.log("studentId",  this.studentId);
      console.log("studentName", this.studentName);
      console.log("subject", this.subject);
      request({
        url: "/exam/getEssays",
        method: "get",
        params: {
          teqId: teqId,
        },
      }).then((res) => {
        let resData = res.data.data;
        console.log("数据", resData);
        resData.forEach((element) => {
          this.essayQuestions.push(element);
        });
        console.log("问答题",this.essayQuestions)
      });
    },
    commit() {
      let allScore = this.$route.query.noEssayScore;
      allScore = Number(allScore)
      console.log("统计分数中...",this.score);
      
      this.score.forEach(e =>{
        allScore += Number(e);       
      })
       console.log("输出",allScore);
       ElMessageBox.confirm("确定提交吗？","提交提示",{
        confirmButtonText: '确定',
        cancelButtonText: '算了',
        type: 'danger'
      }).then(()=> { //确认提交
      console.log("提交中...")
      let examCode =  this.$route.query.examCode;
      let id = this.$route.query.id;
      let teqId = this.$route.query.teqId;
      let studentId = this.$route.query.studentId;
      let subject = this.$route.query.subject;
      let answerDate = this.$route.query.answerDate;
      request({
        url: '/score/commitScore',
        method: 'post',
        data: {
          id: id,
          teqId: teqId,
          studentId: studentId,
          examCode: examCode,
          subject: subject,
          allScore: allScore,
          answerDate: answerDate
        }
      }).then(res => {
        this.$router.push({path: "/index/markExamPapers"})
      })
      }).catch(() => {

      })
    },
    cancle(){
       ElMessageBox.confirm("还未提交分数,确定要退出吗？","提示",{
        confirmButtonText: '确定',
        cancelButtonText: '算了',
        type: 'danger'
      }).then(() =>{
        this.$router.push({path: "/index/markExamPapers"});
      })
    }
  },
};
</script>