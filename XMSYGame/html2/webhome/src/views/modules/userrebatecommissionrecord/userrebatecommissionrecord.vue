<template><el-dialog
    title="下线提供佣金详情"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="75%">
    <el-table
    	:data="dataList"
    	border
    	v-loading="dataListLoading"
    	style="width: 100%;">
    	<el-table-column
    		prop="provideUserId"
    		header-align="center"
    		align="center"
    		label="供应佣金的用户ID">
    	</el-table-column>
    	<el-table-column
    		prop="provideUserAccount"
    		header-align="center"
    		align="center"
    		label="供应佣金的用户账号">
    	</el-table-column>
			<el-table-column
				prop="commission"
				header-align="center"
				align="center"
				label="佣金">
			</el-table-column>
    	<el-table-column
    		prop="recordDate"
    		header-align="center"
    		align="center"
    		label="记录日期">
    	</el-table-column>
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
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
				visible:false,
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
    },
//     activated () {
//       this.getDataList()
//     },
    methods: {
      // 获取数据列表
      getDataList(date,userId) {
				this.visible=true
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/detailList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userId': userId,
						'queryDate':date
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
            url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/delete'),
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
