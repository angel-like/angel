<template>
  <div class="mod-menu">

    <el-form :inline="true" :model="dataForm">
      <el-form-item>
        <el-button v-if="isAuth('sys:column:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      style="width: 100%;">
      <el-table-column
        prop="menuId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <table-tree-column
        prop="name"
        header-align="center"
        treeKey="menuId"
        width="150"
        label="名称">
      </table-tree-column>
      <el-table-column
        prop="parentName"
        header-align="center"
        align="center"
        width="120"
        label="上级栏目">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="图标">
        <template slot-scope="scope">
          <icon-svg :name="scope.row.icon || ''"></icon-svg>
        </template>
      </el-table-column>

      <el-table-column
        prop="url"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="链接地址">
      </el-table-column>
      <el-table-column
        prop="orderNum"
        header-align="center"
        align="center"
        label="排序号">
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="status"-->
        <!--header-align="center"-->
        <!--align="center"-->
        <!--label="状态">-->
        <!--<template slot-scope="scope">-->
          <!--<el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949"-->
                     <!--@change="updateEnable(scope.row.menuId)">-->
          <!--</el-switch>-->
        <!--</template>-->
        <!--&lt;!&ndash;<template slot-scope="scope">&ndash;&gt;-->
        <!--&lt;!&ndash;<el-tag v-if="scope.row.status === 1" size="small">开</el-tag>&ndash;&gt;-->
        <!--&lt;!&ndash;<el-tag v-else-if="scope.row.status === 0" size="small" type="success">关</el-tag>&ndash;&gt;-->
        <!--&lt;!&ndash;</template>&ndash;&gt;-->
      <!--</el-table-column>-->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:column:update')" type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.menuId)">修改
          </el-button>
          <el-button v-if="isAuth('sys:column:delete')" type="text" size="small"
                     @click="deleteHandle(scope.row.menuId)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import AddOrUpdate from './column-add-or-update'
  import {treeDataTranslate} from '@/utils'

  export default {
    data() {
      return {
        activeName: 'second',
        dataForm: {},
        dataList: [],
        dataListLoading: false,
        addOrUpdateVisible: false
      }
    },
    components: {
      TableTreeColumn,
      AddOrUpdate
    },
    activated() {
      this.getDataList()
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sys/column/list'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = treeDataTranslate(data, 'menuId')
          console.log(this.dataList)
          this.dataListLoading = false
        })
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // //修改状态滑块触发事件
      // updateEnable(id) {
      //   this.dataForm.menuId = id
      //   this.$http({
      //     url: this.$http.adornUrl(`/sys/column/updateStatus/${this.dataForm.menuId}`),
      //     method: 'get',
      //     params: this.$http.adornParams()
      //   }).then(({
      //              data
      //            }) => {
      //     if (data && data.code === 200) {
      //       this.$message({
      //         message: '操作成功',
      //         type: 'success',
      //         duration: 1500,
      //         onClose: () => {
      //           this.visible = false
      //
      //           this.$emit('refreshDataList')
      //         }
      //       })
      //     } else {
      //       this.$message.error(data.msg)
      //     }
      //   })
      // },

      // 删除
      deleteHandle(id) {
        this.$confirm(`确定对[id=${id}]进行[删除]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/sys/column/delete/${id}`),
            method: 'post',
            data: this.$http.adornData()
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      }
    }
  }
</script>
