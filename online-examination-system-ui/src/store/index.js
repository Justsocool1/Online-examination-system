import { createStore } from 'vuex'

export default createStore({
  state: {
  isPractice: false, //练习模式标志
  flag: false, //菜单栏左右滑动标志
  userInfo: null,
  menu: [{
    index: '1',
    title: '考试管理',
    type: "icon-kaoshi",
    content:[{item1:'考试查询',type: "icon-kaoshichaxun1",path:'/index/selectExam'},{item2:'添加考试',type: "icon-tianjiakaoshi1",path:'/index/addExam'},{item3: '批阅试卷',type: "icon-piyue",path: '/index/markExamPapers'}],
  },
  {
    index: '2',
    title: '题库管理',
    type: "icon-exam_item_bank",
    content:[{item1:'所有题库',type: "icon-biaotiku",path:'/index/selectAnswer'},{item2:'增加题库',type: "icon-tianjiakaoshi",path:'/index/addAnswer'},{path: '/index/addAnswerChildren'}],
  },
  {
    index: '3',
    title: '成绩查询',
    type: "icon-chengji",
    content:[{item1:'学生成绩查询',type: "icon-chengji1",path:'/index/allStudentsGrade'},{path: '/index/grade'},{item2: '成绩分段查询',type: "icon-xueshengchengji",path: '/index/selectExamToPart'},{path: '/index/scorePart'}],
  },
  {
    index: '4',
    title: '学生管理',
    type: "icon-yewutubiao_xueshengchaxun",
    content:[{item1:'学生管理',type: "icon-_xuesheng",path:'/index/studentManage'},
    // {item2: '添加学生',path: '/index/addStudent'}
  ],
  },
  // {
  //   index: '5',
  //   title: '教师管理',
  //   icon: 'icon-Userselect',
  //   content:[{item1:'教师管理',path:'/teacherManage'},{item2: '添加教师',path: '/addTeacher'}],
  // },
  // {
  //   index: '7',
  //   title: '模块管理',
  //   icon: 'icon-module4mokuai',
  //   content:[{item1:'模块操作',path:'/module'}],
  // }
],
  },
  mutations: {
    practice(state,status) {
      state.isPractice = status
    },
    toggle(state) {
      state.flag = !state.flag
    },
    changeUserInfo(state,info) {
      state.userInfo = info
    }
  },
  actions: {
    getUserInfo(context,info) {
      context.commit('changeUserInfo',info)
    },
    getPractice(context,status) {
      context.commit('practice',status)
    }
  },
  modules: {
  }
})
