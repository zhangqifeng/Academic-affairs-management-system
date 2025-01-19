<template>
    <div>
        <div class="table">
            <el-table :data="tableData" strip @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" v-if="user.role ==='ADMIN'"></el-table-column>
                <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
                <el-table-column prop="name" label="课程名称" show-overflow-tooltip></el-table-column>
                <el-table-column prop="type" label="课程类型" show-overflow-tooltip></el-table-column>
                <el-table-column prop="teacherName" label="授课教师" show-overflow-tooltip></el-table-column>
                <el-table-column prop="score" label="学分" show-overflow-tooltip></el-table-column>
                <el-table-column prop="num" label="上课人数" show-overflow-tooltip></el-table-column>
                <el-table-column prop="room" label="上课教室" show-overflow-tooltip></el-table-column>
                <el-table-column prop="week" label="周几" show-overflow-tooltip></el-table-column>
                <el-table-column prop="segment" label="第几大节" show-overflow-tooltip></el-table-column>
                <el-table-column prop="status" label="上课状态" show-overflow-tooltip></el-table-column>
                <el-table-column prop="studentName" label="选课学生" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" align="center" width="180">
                    <template v-slot="scope">
                        <el-button size="mini" type="danger" plain @click="del(scope.row.id)" v-if="user.role ==='STUDENT'">取消选课</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination
                        background
                        @current-change="handleCurrentChange"
                        :current-page="pageNum"
                        :page-sizes="[5, 10, 20]"
                        :page-size="pageSize"
                        layout="total, prev, pager, next"
                        :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        type: "Choice",
        data() {
            return {
                tableData: [],  // 所有的数据
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                rules: {},
                ids: [],
            }
        },
        created() {
            this.load(1)
            this.loadteacher()
        },
        methods: {
            choiceCourse(row) {
                let data = {
                    studentId:this.user.id,
                    teacherId:row.teacherId,
                    courseId:row.id
                }
            },
            del(id) {   // 单个删除
                this.$confirm('您确定取消吗？', '灵魂拷问', {type: "warning"}).then(response => {
                    this.$request.delete('/choice/delete/' + id).then(res => {
                        if (res.code === '200') {   // 表示操作成功
                            this.$message.success('操作成功')
                            this.load(1)
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }).catch(() => {
                })
            },
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/choice/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name,
                    }
                }).then(res => {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                })
            },
            reset() {
                this.name = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
            handleAvatarSuccess(response, file, fileList) {
                // 把头像属性换成上传的图片的链接
                this.form.avatar = response.data
            },
        }
    }
</script>

<style scoped>

</style>