<template>
	<el-dialog
		:title="'详情'"
		:close-on-click-modal="false"
		:visible.sync="visible">
		<div class="mod-config">
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<!-- 	<el-form-item>
					<el-input v-model="dataForm.userAccount" placeholder="用户名称" clearable></el-input>
				</el-form-item> -->
				<el-form-item>
					<el-button @click="getDataList()">刷新</el-button>
					<!-- <el-button v-if="isAuth('userviprecord:userviprecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
					<el-button v-if="isAuth('userviprecord:userviprecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
				</el-form-item>
			</el-form>
			<el-table
				:data="dataList"
				border
				v-loading="dataListLoading"
				@selection-change="selectionChangeHandle"
				style="width: 100%;">
				<el-table-column
					type="selection"
					header-align="center"
					align="center"
					width="50">
				</el-table-column>
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
					prop="userId"
					header-align="center"
					align="center"
					label="用户id">
				</el-table-column>
				<el-table-column
					prop="userAccount"
					header-align="center"
					align="center"
					label="用户名称">
				</el-table-column>
				<el-table-column
					prop="vipName"
					header-align="center"
					align="center"
					label="vip等级">
				</el-table-column>
				<el-table-column
					prop="rechargeAmount"
					header-align="center"
					align="center"
					label="已经充值">
				</el-table-column>
			<!-- 	<el-table-column
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
	</el-dialog>
</template>

<script>
  //import AddOrUpdate from './userviprecord-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          userAccount: '',
					userId:'',
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
				visible: false,
      }
    },
    components: {
      //AddOrUpdate
    },
    activated () {
     // this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/userviprecord/userviprecord/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userAccount': this.dataForm.userAccount,
						'userId':this.dataForm.userId,
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
			init(userId){
				this.visible = true
				if(userId!=null){
					this.dataForm.userId=userId;
				}
				this.getDataList();
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
            url: this.$http.adornUrl('/userviprecord/userviprecord/delete'),
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
