// 教师管理页面
<template>
  <div class="all">
    <div class="search">
      <div class="search">
        <span>工号&nbsp&nbsp</span>
        <el-input
          v-model="teacherId"
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
          v-model="teacherName"
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
      <el-table-column  prop="id" label="ID" width="180" v-if="false"></el-table-column>
      <el-table-column fixed="left" prop="teacherId" label="姓名" width="180"></el-table-column>
      <el-table-column  prop="teacherName" label="姓名" width="180"></el-table-column>
      <el-table-column prop="instituteId" label="学院" width="200"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱地址" width="120"></el-table-column>
      <el-table-column prop="cardId" label="身份证号" width="120"></el-table-column>
      <el-table-column prop="type" label="职称" width="120"></el-table-column>
      <el-table-column prop="role" label="角色" width="120" v-if="false"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button @click="resetPassword(scope.row.id,scope.row.role)" type="primary" size="small">重置密码</el-button>
          <el-button @click="editTeacher(scope.row)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.tId)" type="danger" size="small">删除</el-button>
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
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑教师信息"
      v-model="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="工号">
            <el-input v-model="form.teacherId"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.teacherName"></el-input>
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
          <el-form-item label="身份证号">
            <el-input v-model="form.cardId"></el-input>
          </el-form-item>
          <el-form-item label="职称">
            <el-input v-model="form.type"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
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
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogVisible: false, //对话框
      form: {
        id: null,
        teacherId : null,
        teacherName : null,
        instituteId : null,
        sex : null,
        tel : null,
        pwd : null,
        cardId : null,
        type: null,
        role: null,
        
      }, 
      teacherId: null,
      teacherName: null,
      institutes: [],
    };
  },
  components:{
    SearchOutlined
  },
  created() {
    this.getTeacherInfo();
  },
  methods: {
    getTeacherInfo() {
      //分页查询所有试卷信息
      if(this.teacherId != null && this.teacherId.length == 0){
           this.teacherId = null;
        }

      if(this.teacherName != null && this.teacherName.length == 0){
           this.teacherName = null;
          }
      request({
          url: `/user/getTeachers/${this.pagination.current}/${this.pagination.size}/${this.teacherId}/${this.teacherName}`,
          method: 'get',
          params: {

          }
      }).then(res =>{
        console.log("数据",res.data.data)
        this.pagination = res.data.data;
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getTeacherInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getTeacherInfo();
    },
   async editTeacher(teacherInfo) { //修改教师信息
        this.dialogVisible = true
        this.form.id = teacherInfo.id;
        this.form.teacherId = teacherInfo.teacherId;
        this.form.teacherName = teacherInfo.teacherName;
        this.form.sex = teacherInfo.sex;
        this.form.tel = teacherInfo.tel;
        this.form.email = teacherInfo.email;
        this.form.cardId = teacherInfo.cardId;
        this.form.type= teacherInfo.type;
        this.form.role= teacherInfo.role;

        console.log("tId,",this.form.id);

       await this.getInstitutes();
        console.log("学院",this.institutes)
        this.institutes.forEach(e => {
          if(teacherInfo.instituteId == e.institute){
            this.form.instituteId = e.instituteId;
          }
        });
        console.log("ins",this.form.instituteId);
    },
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
    deleteById(teacherId) { //删除当前学生
      ElMessageBox.confirm("确定删除当前教师吗？删除后无法恢复","Warning",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(()=> { //确认删除
        request({
          url: '/user/deleteTeacherById',
          method: 'delete',
          data: {
            tId: this.form.tId,
          }
        }).then((res) => {
          if(res.data.success){
          ElMessage.success("删除成功!");
          this.getTeacherInfo();
          }
        }).catch((err) => {
          
        });
      }).catch(() => {

      })
    },
    submit() { //提交更改
      this.dialogVisible = false
      
      request({
        url: '/user/updateTeacher',
        method: 'put',
        data: {
          ...this.form
        }
      }).then((res) => {
        if(res.data.success){
          ElMessage.success("更新成功!");
          this.getTeacherInfo();
        }
      }).catch((err) => {
        
      });
    },
    search(){
      this.getTeacherInfo();
    },
    resetPassword(tId,role){
      console.log("tId",tId)
      console.log("接收的角色",role);
      ElMessageBox.confirm("确定将密码重置为333333 ?","警告",{
        confirmButtonText: '确定重置',
        cancelButtonText: '算了',
        type: 'warning'
      }).then(() => {
        request({
          url: '/user/resetPassword',
          method: 'put',
          data: {
            id: tId,
            role: role,
          }

        }).then((res) => {
          if(res.data.success){
          ElMessage.success("重置密码成功!");
          }
        }).catch((err) => {
          
        });
      })
    },
    handleClose(done) { //关闭提醒
      ElMessageBox.confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
  }
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
