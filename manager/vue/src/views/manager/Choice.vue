<template>

  <el-table-column label="操作" width="180" align="center" v-if="user.role === 'STUDENT'">
    <template v-slot="scope">
      <el-button plain type="danger" size="mini" @click=del(scope.row.id) :disabled="scope.row.status !== '未开课'">取消选课</el-button>
      <el-button plain type="primary" size="mini" @click="initComment(scope.row)" :disabled="scope.row.status !=='已结课'">评教</el-button>
    </template>
  </el-table-column>


  <div>
    <div>
      <el-pagination>
        :page-sizes = "[5,10,20]"
      </el-pagination>
    </div>
  </div>
  <el-dialog title="请填写评教信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
    <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
      <el-form-item prop="content" label="评教内容">
        <el-input type="textarea" :rows="5" v-model="form.content" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="fromVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>


</template>

<script>
export default {
  name: "Choice",
  data() {
    return {
      rules: {
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'},
        ]
      },
      form:{},
      fromVisible: false
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    initComment(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.fromVisible = true
    },
    save(){
      let data = {
        name: this.form.name,
        teacher: this.form.teacherName,
        student: this.user.name,
        content: this.form.content
      }
      this.$request.post('/comment/add',data).then(res => {
        if (res.code ==='200'){
          this.$message.success('评教成功')
          this.fromVisible = false
        }else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
