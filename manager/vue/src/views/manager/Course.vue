<template>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click=choiceCourse(scope.row) size="mini" v-if="user.role === 'STUDENT'" :disabled="scope.row.status !== '未开课'">删除</el-button>
          </template>
        </el-table-column>


</template>

