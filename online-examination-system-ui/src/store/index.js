import { createStore } from 'vuex'

export default createStore({
  state: {
  isPractice: false, //练习模式标志
  flag: false, //菜单栏左右滑动标志
  userInfo: null,
  menu: [{
    index: '1',
    title: '考试管理',
    content:[{item1:'考试查询',path:'/index/selectExam'},{item2:'添加考试',path:'/index/addExam'},{item3: '批阅试卷',path: '/index/markExamPapers'}],
  },
  {
    index: '2',
    title: '题库管理',
    content:[{item1:'所有题库',path:'/index/selectAnswer'},{item2:'增加题库',path:'/index/addAnswer'},{path: '/index/addAnswerChildren'}],
  },
  {
    index: '3',
    title: '成绩查询',
    content:[{item1:'学生成绩查询',path:'/index/allStudentsGrade'},{path: '/index/grade'},{item2: '成绩分段查询',path: '/index/selectExamToPart'},{path: '/index/scorePart'}],
  },
  {
    index: '4',
    title: '学生管理',
    content:[{item1:'学生管理',path:'/index/studentManage'},
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
