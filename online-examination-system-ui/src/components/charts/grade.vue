// 成绩统计页面
<template>
  <div id="grade">
    <div id="echarts"  class="box"></div>
    <div class="notFound" v-if="isNull">
      <i class="iconfont icon-LC_icon_tips_fill"></i><span>该考生未参加考试</span>
    </div>
  </div>
</template>

<script>
import { request } from "@/utils/request";
export default {
  name: "grade",
  data() {
    return {
      isNull: false, //原始数据
      tableDataX: [], //x轴数据 保存次数
      tableDataY: [], //y轴数据 保存分数
    }
  },
  mounted() {
    this.score();
  },
  methods: {
    score() {
      let studentId = this.$route.query.studentId
      request({
          url: '/score/getStudentScoreByStudentId',
          method: 'get',
          params: {
              studentId: studentId
          }
      }).then((res) => {
          console.log(res.data.data)
          let resData = res.data.data;
          if(res.data.success){
            resData.forEach((e,i) => {
                this.tableDataX.push(`第${i + 1}次`)
                this.tableDataY.push(e.etScore);
            });
            let scoreCharts = this.$echarts.init(document.getElementById("echarts"));
            let option = {
            xAxis: {
                type: 'category',
                data: this.tableDataX
            },
            yAxis: {
                type: 'value'
            },
             series: [
                {
                data: this.tableDataY,
                type: 'line',
                itemStyle: { normal: { label: { show: true } } }
                }
            ]
            };
           scoreCharts.setOption(option);
           scoreCharts.on("mouseover",params =>{
               console.log(params.value);
           });
          } else{
              console.log("没有参加过考试")
              this.isNull = true;
          }
      }).catch((err) => {
          
      });
    }
  }
};
</script>

<style lang="scss" scoped>
#grade {
  position: relative;
  .box{
    width: 800px;
    height: 500px;
  }
  .notFound {
    position: absolute;
    top: 0px;
    left: 0px;
  }
}
</style>
