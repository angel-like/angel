<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="层级名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('userhierarchy:userhierarchy:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('userhierarchy:userhierarchy:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
				type="index"
				width="120"
				header-align="center"
				align="center"
				label="序号">
      </el-table-column>
      <!-- <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column> -->
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="层级名称">
      </el-table-column>
			<el-table-column
				prop="rechargeMultiple"
				header-align="center"
				align="center"
				label="充值打码倍数">
			</el-table-column>
			<el-table-column
				prop="discountMultiple"
				header-align="center"
				align="center"
				label="优惠打码倍数">
			</el-table-column>
			<el-table-column
				prop="administrativeRate"
				header-align="center"
				align="center"
				label="行政费比例">
					<template slot-scope="scope">
					{{scope.row.administrativeRate*100}}%
				</template>
			</el-table-column>
			<el-table-column
				prop="relaxationRate"
				header-align="center"
				align="center"
				label="打码放宽比例">
					<template slot-scope="scope">
					{{scope.row.relaxationRate*100}}%
				</template>
			</el-table-column>
			<el-table-column
				prop="betRate"
				header-align="center"
				align="center"
				label="下注阈值">
					<template slot-scope="scope">
					{{scope.row.betRate*100}}%
				</template>
			</el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="是否默认">
				<template slot-scope="scope">
						<el-tag v-if="scope.row.type" size="small" type="success">是</el-tag>
						<el-tag v-else size="small" type="info">否</el-tag>
				</template>
      </el-table-column>
			<!-- <el-table-column
				prop="vipEnable"
				header-align="center"
				align="center"
				label="是否VIP">
				<template slot-scope="scope">
						<el-tag v-if="scope.row.vipEnable" size="small" type="success">是</el-tag>
						<el-tag v-else size="small" type="info">否</el-tag>
				</template>
			</el-table-column> -->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
					<el-button  type="text" size="small" v-if="!scope.row.type" @click="setDefaultHandle(scope.row.id)">设置默认</el-button>
					<!-- <el-button  type="text" size="small" v-if="!scope.row.vipEnable" @click="openVipEnable(scope.row.id)">开启VIP权限</el-button> -->
					<!-- <el-button  type="text" size="small" v-if="scope.row.vipEnable"  @click="closeVipEnable(scope.row.id)">关闭VIP权限</el-button> -->
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="depositAuthority(scope.row.id)">存款权限</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		<add-deposit-authority v-if="addDepositAuthorityVisible" ref="addDepositAuthority" @refreshDataList="getDataList"></add-deposit-authority>
  </div>
</template>

<script>
  import AddOrUpdate from './userhierarchy-add-or-update'
	import AddDepositAuthority from './userhierarchy-add-deposit-authority'
  export default {
    data () {
      return {
        dataForm: {
          name: '',
					hierarchyType:0
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
				addDepositAuthorityVisible:false,
      }
    },
    components: {
      AddOrUpdate,
			AddDepositAuthority
    },
    activated () {
      this.getDataList()
    },
		created(){
		 this.keyupSubmit()
		},
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/userhierarchy/userhierarchy/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.dataForm.name,
            'hierarchyType': this.dataForm.hierarchyType
						
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
			//绑定回车事件
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery(){
				this.pageIndex=1;
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
          this.$refs.addOrUpdate.init(id,this.dataForm.hierarchyType)
        })
      },
			//存款权限
			depositAuthority(id) {
        this.addDepositAuthorityVisible = true
        this.$nextTick(() => {
          this.$refs.addDepositAuthority.init(id)
        })
      },
			//设置默认
			setDefaultHandle (id) {
				this.$confirm(`确定对[id=${id}]设置默认操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/userhierarchy/userhierarchy/setdefault/${id}`),
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
				})
				
			},
			//开启VIP权限
			openVipEnable (id) {
				this.$confirm(`确定对[id=${id}]开启vip权限?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/userhierarchy/userhierarchy/openVipEnable/${id}`),
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
				})
				
			},
			//关闭VIP权限
			closeVipEnable (id) {
				this.$confirm(`确定对[id=${id}]关闭vip权限?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/userhierarchy/userhierarchy/closeVipEnable/${id}`),
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
            url: this.$http.adornUrl('/userhierarchy/userhierarchy/delete'),
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
