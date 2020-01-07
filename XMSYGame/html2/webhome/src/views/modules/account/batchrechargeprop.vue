<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.account" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('batchrechargeprop:batchrechargeprop:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <!-- <el-button v-if="isAuth('batchrechargeprop:batchrechargeprop:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
			<el-table-column
			  prop="createTime"
			  header-align="center"
			  align="center"
			  label="创建时间">
			</el-table-column>
      <el-table-column
        prop="orderNo"
        header-align="center"
        align="center"
        label="订单号">
      </el-table-column>
      <el-table-column
        prop="sysUserAccount"
        header-align="center"
        align="center"
        label="操作人用户名">
      </el-table-column>
      <!-- <el-table-column
        prop="sysUserId"
        header-align="center"
        align="center"
        label="操作人id"> -->
      </el-table-column>
			<el-table-column
			  prop="account"
			  header-align="center"
			  align="center"
			  label="会员账号">
			</el-table-column>
      <el-table-column
        prop="propName"
        header-align="center"
        align="center"
        label="道具名称">
      </el-table-column>
      <el-table-column
        prop="propNum"
        header-align="center"
        align="center"
        label="道具数量">
      </el-table-column>
     <!-- <el-table-column
        prop="hierarchyName"
        header-align="center"
        align="center"
        label="层级">
      </el-table-column>
      <el-table-column
        prop="vipName"
        header-align="center"
        align="center"
        label="vip名称">
      </el-table-column>
      <el-table-column
        prop="designated"
        header-align="center"
        align="center"
        label="是否指定">
				<template slot-scope="scope">
					<div v-if="scope.row.designated==0">是</div>
					<div v-if="scope.row.designated==1">否</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="sendMessage"
        header-align="center"
        align="center"
        label="是否发送站内信">
				<template slot-scope="scope">
					<div v-if="scope.row.sendMessage==1">是</div>
					<div v-if="scope.row.sendMessage==0">否</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="messageTitle"
        header-align="center"
        align="center"
        label="站内信标题">
      </el-table-column>
      <el-table-column
        prop="messageContent"
        header-align="center"
        align="center"
        label="站内信内容">
      </el-table-column>
      <el-table-column
        prop="effectiveDate"
        header-align="center"
        align="center"
        label="站内信有效期限">
      </el-table-column> -->
     <!-- <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './batchrechargeprop-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          account: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/batchrechargeprop/batchrechargeprop/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'account': this.dataForm.account
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/batchrechargeprop/batchrechargeprop/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
        })
      }
    }
  }
</script>
