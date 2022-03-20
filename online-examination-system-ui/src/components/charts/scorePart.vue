<template>
  <div class="part" >
    <div class="box" id="echarts"></div>
    <div v-if="isNull">
      <span>该门考试还没人参考哦,请提醒学生参加考试。</span>
    </div>
  </div>
</template>

<script>
import { request } from "@/utils/request";
export default {
  data() {
    return {
      isNull: false, //是否有成绩标志位
      name: null,
      category: { //保存分数段
        '90分及以上': 0,
        '80-89分': 0,
        '70-79分': 0,
        '60-69分': 0,
        '60分以下': 0,
      }
    }
  },
  created() {
    this.getScoreInfo()

  },
  methods: {
    getScoreInfo() {
      let examCode = this.$route.query.examCode
      this.name = this.$route.query.source
      request({
        url: '/score/getExamsScoreByExamCode',
        method: 'get',
        params: {
          examCode
        }
      }).then((res) => {
        let resData = res.data.data
        console.log("数据",resData.length);
        if( resData.length > 0){
          console.log("制图")
          let scoreCharts =  this.$echarts.init(document.getElementById("echarts"));
          resData.forEach(element => {
            switch(element.etScore / 10) {
              case 10:
              case 9:
                this.category["90分及以上"]++
                break
              case 8:
                this.category['80-89分']++
                break
              case 7:
                this.category["70-79分"]++
                break
              case 6:
                this.category['60-69分']++
                break
              default:
                this.category['60分以下']++
            }
          });

          let option = {
                title: {
                  text: `${this.name}分数段图`,
                  subtext: '分数段饼图',
                  left: 'center'
                },
                tooltip: {
                  trigger: 'item',
                  formatter: "{a}：{b} <br/> {c}人 ({d}%)"
                },
                legend: {
                  orient: 'vertical',
                  left: 'left',
                  data: ['90分及以上','80-89分','70-79分','60-69分','60分以下']
                },
                series: [
                  {
                    name: '分数段',
                    type: 'pie',
                    radius: '50%',
                    data: [
                          {value:this.category['90分及以上'], name:'90分及以上'},
                          {value:this.category['80-89分'], name:'80-89分'},
                          {value:this.category['70-79分'], name:'70-79分'},
                          {value:this.category['60-69分'], name:'60-69分'},
                          {value:this.category['60分以下'], name:'60分以下'}
                    ],
                    emphasis: {
                      itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                      }
                    }
                  }
                ]
              };

             scoreCharts.setOption(option);
        } else{
          this.isNull = true;
        }
      }).catch((err) => {
        
      });
    }
  },

}
</script>

<style lang="scss" scoped>
.part {
  .box {
    width: 800px;
    height: 800px;
    margin-left: 40px;
  }
}
</style>


