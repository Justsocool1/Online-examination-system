// 学生管理页面
<template>
  <div class="all">
    <div class="search">
      <div class="search">
        <span>学号&nbsp&nbsp</span>
        <el-input
          v-model="studentId"
          class="w-50 m-2"
          size="large"
          placeholder=""
          :suffix-icon="Search"
          @keydown.enter="search()"
        />
      </div>
      <div class="search">
        <span >姓名&nbsp&nbsp</span>
        <el-input
          v-model="studentName"
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
    <el-table :data="pagination.records" border>
        <el-table-column
        v-if="false"
        prop="stuId"
        label="ID"
        width="180"
      ></el-table-column>
      <el-table-column
        fixed="left"
        prop="studentId"
        label="学号"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="studentName"
        label="姓名"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="instituteId"
        label="学院"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="majorId"
        label="专业"
        width="200"
      ></el-table-column>
      <el-table-column prop="grade" label="年级" width="200"></el-table-column>
      <el-table-column
        prop="clazzId"
        label="班级"
        width="100"
      ></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column
        prop="tel"
        label="联系方式"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="email"
        label="邮箱地址"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="role"
        label="角色"
        width="120"
        v-if ="false"
      ></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button
            @click="resetPassword(scope.row.stuId,scope.row.role)"
            type="primary"
            size="small"
            >重置密码</el-button
          >
          <el-button @click="editStudent(scope.row)" type="primary" size="small"
            >编辑</el-button
          >
          <el-button
            @click="deleteById(scope.row.studentId)"
            type="danger"
            size="small"
            >删除</el-button
          >
        </template>
      </el-table-column>
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
    >
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑试卷信息"
      v-model="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="ID" v-if="false">
            <el-input v-model="form.stuId"></el-input>
          </el-form-item>
            <el-form-item label="学号">
            <el-input v-model="form.studentId"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.studentName"></el-input>
          </el-form-item>
          <el-form-item label="所属学院">
            <el-select placeholder="请选择学院" v-model="form.instituteId">
              <el-option
                v-for="item in institutes"
                :key="item.instituteId"
                :value="item.instituteId"
                :label="item.institute"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属专业">
            <el-select
              @click="getMajors()"
              placeholder="请选择专业"
              v-model="form.majorId"
            >
              <el-option
                v-for="item in majors"
                :key="index"
                :value="item.majorId"
                :label="item.major"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="年级">
        <el-select v-model="form.grade" placeholder="请选择年级">
        <el-option v-for="(item,index) in grades" :key="index" :value="item" >{{item}}</el-option>
        </el-select>
      </el-form-item>
          <el-form-item label="班级">
            <el-select placeholder="请选择班级" v-model="form.clazzId">
              <el-option
                v-for="item in clazzes"
                :key="item.clazzId"
                :value="item.clazzId"
                :label="item.clazzName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="性别">
              <a-radio-group  v-model:value="form.sex">
                <a-radio value="男">男</a-radio>
                <a-radio value="女">女</a-radio>
               </a-radio-group>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="form.tel"></el-input>
          </el-form-item>
          <el-form-item label="邮箱地址">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
        </el-form>
      </section>   
      <template v-slot:footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from "element-plus";
import { SearchOutlined } from '@ant-design/icons-vue';
import { request } from "@/utils/request";
export default {
  data() {
    return {
      grades: [2022,2021,2020,2019,2018],
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogVisible: false, //对话框
      form: {
        stuId: null,
        studentId: null,
        studentName: null,
        instituteId: null,
        majorId: null,
        grade: null,
        clazzId: null,
        sex: null,
        tel: null,
        email: null,
        role: null
      },
      studentId: null,
      studentName: null,
      institutes: [],
      majors: [],
      clazzes: [],
    };
  },
  components: {
    SearchOutlined
  },

  created() {
    this.getStudentInfo();
  },
  methods: {
    async getStudentInfo() {
      //分页查询所有学生信息
      if(this.studentId != null && this.studentId.length == 0){
           this.studentId = null;
        }

      if(this.studentName != null && this.studentName.length == 0){
           this.studentName = null;
          }
      await request({
        url: `/user/getStudentInfos/${this.pagination.current}/${this.pagination.size}/${this.studentId}/${this.studentName}`,
        method: "get",
        params: {},
      }).then((res) => {
          console.log("学生数据",res.data.data)
        this.pagination = res.data.data;
      });

    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getStudentInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getStudentInfo();
    },
   async editStudent(studentInfo) {
      //修改学生信息
      this.dialogVisible = true;
      this.form.stuId = studentInfo.stuId;
      console.log("id",this.form.stuId);
      this.form.studentId = studentInfo.studentId;
      this.form.studentName = studentInfo.studentName;
      this.form.instituteId = studentInfo.instituteId;
      this.form.majorId = studentInfo.majorId;
      this.form.grade = studentInfo.grade;
      this.form.clazzId = studentInfo.clazzId;
      this.form.sex = studentInfo.sex;
      this.form.tel = studentInfo.tel;
      this.form.email = studentInfo.email;
      this.form.role = studentInfo.role;

   await this.getInstitutes();
    this.institutes.forEach(e=>{
        if(studentInfo.instituteId == e.institute){
            this.form.instituteId = e.instituteId;
        }
    })
    console.log("ins",this.form.instituteId)
   await this.getMajors();
    this.majors.forEach(e=>{
        if(studentInfo.majorId == e.major){
            this.form.majorId = e.majorId;
        }
    })

   await this.getClazzes();
    this.clazzes.forEach(e=>{
        if(studentInfo.clazzId == e.clazzName){
            this.form.clazzId = e.clazzId;
        }
    })

    },
    deleteById(studentId) {
      console.log("被删下学生学号",studentId)
      //删除当前学生
      ElMessageBox.confirm("确定删除当前学生吗？删除后无法恢复","警告",{
         confirmButtonText: '确定删除',
          cancelButtonText: '算了,留着吧',
          type: 'danger'
      }).then(()=>{
          request({
              url: '/user/deleteStudentByStudentId',
              method: 'delete',
              data: {
                  studentId
              }
          }).then(res => {
            if(res.data.success){
                ElMessage.success("删除成功！");
                this.getStudentInfo();
            }
          })
      })
    },
    submit() {
      //提交更改
      this.dialogVisible = false;
      request({
        url: "/user/updateStudentInfo",
        method: 'put',
        data: {
            ...this.form
        }
      }).then((res) => {
          if(res.data.success){
            ElMessage.success("更新成功！");
            this.getStudentInfo();
          }
      });

    },
    search() {
      console.log("搜索中...");
     this.getStudentInfo();
      console.log("搜索完成！");
    },
    //重置密码
    resetPassword(stuId,role) {
      console.log("stuId",stuId)
      console.log("role",role)
        ElMessageBox.confirm("确认将密码重置为333333 ?","提示",{
            confirmButtonText: '确认重置',
            cancelButtonText: '算了',
            type: 'warning'
        }).then(()=>{
            request({
                url: `/user/resetPassword`,
                method: 'put',
                data: {
                    id: stuId,
                    role: role
                }
             }).then(res => {
                 if(res.data.success){
                 ElMessage.success("密码重置成功！");
                 }
            })
            })
        

    },

    handleClose(done) {
      //关闭提醒
    ElMessageBox.confirm("确认关闭？").then(() =>{
        done();
    })

    },
    //获取学院
    async getInstitutes() {
      await request({
        url: "/user/getInstitutes",
        method: "get",
        params: {},
      }).then((res) => {
        let resData = res.data.data;
        if (resData != null) {
          this.institutes = [];
          resData.forEach((e) => {
            this.institutes.push(e);
          });
        } else {
          console.log("获取学院失败");
          
        }
      });
    },
    //根据学院获取专业
    async getMajors() {
      await request({
        url: "/user/getMajorsByInstituteId",
        method: "get",
        params: {
          instituteId: this.form.instituteId,
        },
      }).then((res) => {
        let resData = res.data.data;
        if (resData != null) {
          this.majors = [];
          resData.forEach((e) => {
           this.majors.push(e);
          });
        } else {
          console.log("获取专业失败");
          
        }
      });
    },
    async getClazzes() {
      await request({
        url: "/user/getClassesByMajorId",
        method: "get",
        params: {
          majorId: this.form.majorId,
        },
      }).then((res) => {
        let resData = res.data.data;
        console.log(resData);
        this.clazzes = [];
        if (resData != null) {
          resData.forEach((e) => {
            this.clazzes.push(e);
          });
        } else {
          console.log("获取班级失败");
          
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.all {
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
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}

.search {
  padding: 0px 40px;
  margin-left: 100px;
  margin-bottom: 20px;
  display: flex;

}
</style>
