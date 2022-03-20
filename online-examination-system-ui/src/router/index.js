import { createRouter, createWebHistory } from 'vue-router'


const routes = [
  {
      path: '/',
      name: 'login', //登录界面
      component: () => import('@/components/common/login')
    },
    {
      path: '/register',
      name: 'register', //注册界面
      component: () => import('@//components/common/register')
    },
    {
      path: '/index', //教师主页
      component: () => import('@/components/admin/index'),
      children: [
        {
          path: '/index/', //首页默认路由
          component: () => import('@/components/common/hello')
        },
        {
          path: '/index/manager',
          component: () => import ('@/components/teacher/manager')
        },
        {
          path: '/index/markExamPapers', //学生提交的试卷
          component: () => import('@/components/teacher/markExamPapers')
        },
        {
          path: '/index/markExam', //批改试卷
          component: () => import('@/components/teacher/markExam')
        },
        {
          path:'/index/grade', //学生成绩
          component: () => import('@/components/charts/grade')
        },
        {
          path: '/index/selectExamToPart', //学生分数段
          component: () => import('@/components/teacher/selectExamToPart')
        },
        {
          path: '/index/scorePart',
          component: () => import('@/components/charts/scorePart')
        },
        {
          path: '/index/allStudentsGrade', //所有学生成绩统计
          component: () => import('@/components/teacher/allStudentsGrade')
        },
        {
          path: '/index/selectExam', //查询所有考试
          component: () => import('@/components/teacher/selectExam')
        },
        {
          path: '/index/addExam', //添加考试
          component: () => import('@/components/teacher/addExam')
        },
        {
          path: '/index/selectAnswer', //查询所有题库
          component: () => import('@/components/teacher/selectAnswer')
        },
        {
          path: '/index/addAnswer', //增加题库主界面
          component: () => import('@/components/teacher/addAnswer')
        },
        {
          path: '/index/addAnswerChild', //点击试卷跳转到添加题库页面
          component: () => import('@/components/teacher/addAnswerChild')
        },
        {
          path: '/index/studentManage', //学生管理界面
          component: () => import('@/components/teacher/studentManage')
        },
        {
          path: '/index/teacherManage',
          component: () => import('@/components/admin/tacherManage')
        },
        {
          path: '/index/addTeacher',
          component: () => import ('@/components/admin/addTeacher')
        },
        
      ]
    },

    {
      path: '/student',
      component: () => import('@/components/student/index'),
      children: [
        {path: '/student/', component: () => import('@/components/student/myExam')},
        {path: '/student/examInfo', component: () => import('@/components/student/examInfo')},
        {path: '/student/scoreTable', component: () => import("@/components/student/scoreTable")},
        {path: '/student/manager',component: () => import("@/components/student/manager")}
      ]
    },
    {
      path: '/answer',component: () => import('@/components/student/answer')
    },
    {path: '/result', component: () => import('@/components/student/result')},
    {path: '/failresult', component: () => import('@/components/student/failResult')},
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
