<!--左边下拉导航栏-->
<template>
  <div id="left">
    <el-menu
      active-text-color="#dd5862" 
      text-color="#000" 
      :default-active="this.$route.path"
      class="el-menu-vertical-demo" 
      @open="handleOpen" 
      @close="handleClose" 
      :collapse="flag"
      background-color="#124280"  
      menu-trigger="click" router>
      <el-sub-menu v-for="(item,index) in menu" :index='item.index' :key="index">
            <template v-slot:title>
              <!-- <user-outlined v-if="item.title == '学生管理'"  :style="{fontSize: '23px', color: 'white'}"/> -->
              <icon-font :type="item.type" :style="{fontSize: '23px', color: 'white'}" />
              <span  class="title">{{item.title}}</span>
            </template>
            <el-menu-item-group v-for="(list,index1) in item.content" :key="index1">
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item1 != null"><icon-font :type="list.type" :style="{fontSize: '23px', color: 'white'}" /> &nbsp{{list.item1}}</el-menu-item>
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item2 != null"><icon-font :type="list.type" :style="{fontSize: '23px', color: 'white'}" /> &nbsp{{list.item2}}</el-menu-item>
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item3 != null"><icon-font :type="list.type" :style="{fontSize: '23px', color: 'white'}" /> &nbsp{{list.item3}}</el-menu-item>
            <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item4 != null"><icon-font :type="list.type" :style="{fontSize: '23px', color: 'white'}" /> &nbsp{{list.item4}}</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
import store from "@/store/index";
import {mapState} from 'vuex'
import bus from '@/utils/bus.js'
import { UserOutlined } from '@ant-design/icons-vue';
import { createFromIconfontCN } from '@ant-design/icons-vue';
const IconFont = createFromIconfontCN({
  scriptUrl: '//at.alicdn.com/t/font_3260262_ji93tnzull.js',
});
export default {
  name: "mainLeft",
  data() {
    return {
      
    }
  },
  components:{
    UserOutlined,
    IconFont
  },
  computed: mapState(["flag","menu"]),
  created() {
    console.log(this.menu)
    this.addData()
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    //点击标题传递参数给navigator组件
    handleTitle(index) {
      bus.emit('sendIndex',index)
    },
    addData() {
      let role = this.$cookies.get("role")
      if(role == 0) {
        this.menu.push({
          index: '5',
          title: '教师管理',
          type: "icon-jiaoshi",
          content:[{item1:'教师管理',type: "icon-jiaoshiguanli",path:'/index/teacherManage'},{item2: '添加教师',type: "icon-tianjiajiaoshi",path: '/index/addTeacher'}],
        })
      }
    }
  },
  store
}
</script>

<style>
.el-menu-vertical-demo .el-submenu__title {
  overflow: hidden;
}
.left-width .iconfont {
  font-size: 18px;
  color: #fff;
}
.left-width {
  width: 213px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  min-height: 900px;
}
#left {
  height: 900px;
  background-color: #124280;
  z-index: 0;
}
#left .el-menu-vertical-demo .title {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  margin-left: 14px;
}
.el-submenu {
  border-bottom: 1px solid #eeeeee0f !important;
}
.el-submenu__title:hover {
  background-color: #fff;
}
.el-submenu__title i {
    color: #fbfbfc !important;
}
</style>
