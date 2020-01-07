<template>
	<div class="mod-user">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.mobile" placeholder="代理账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userName" placeholder="代理名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!--        <el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
 -->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="mobile" header-align="center" align="center" label="代理账号">
			</el-table-column>
			<el-table-column prop="username" header-align="center" align="center" label="代理名称">
			</el-table-column>
			
			<el-table-column prop="proxyBalance" header-align="center" align="center" width="180" label="余额">
				<template slot-scope="scope">
					<div>{{scope.row.proxyBalance/100}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="proxySaleAmount" header-align="center" align="center" width="180" label="售卖金额">
				<template slot-scope="scope">
					<div>{{scope.row.proxySaleAmount/100}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" width="180" label="创建时间">
			</el-table-column>
			<el-table-column prop="status" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.status === 0" size="small" type="danger">禁用</el-tag>
					<el-tag v-else size="small">正常</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button v-if="isAuth('sys:user:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.userId)">修改</el-button>
					<el-button type="text" size="small" v-if="!scope.row.status" @click="openEnable(scope.row.userId)">启用</el-button>
					<el-button type="text" size="small" v-if="scope.row.status" @click="closeEnable(scope.row.userId)">禁用</el-button>
					<el-button v-if="isAuth('sys:user:delete')" type="text" size="small" @click="deleteHandle(scope.row.userId)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import AddOrUpdate from './proxy-agent-add-or-update'
	export default {
		data() {
			return {
				dataForm: {
					userName: ''
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
		activated() {
			this.getDataList()
		},
		//绑定回车事件
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/sys/user/listProxyAgent'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'username': this.dataForm.userName,
						'mobile':this.dataForm.mobile
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.dataForm.roles = data.roles
						this.totalPage = data.page.totalCount
					} else {
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			//绑定回车键
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询按钮事件
			getDataListQuery(){
				this.pageIndex=1;
				this.getDataList();
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val
				this.pageIndex = 1
				this.getDataList()
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val
				this.getDataList()
			},
			// 多选
			selectionChangeHandle(val) {
				this.dataListSelections = val
			},
			// 新增 / 修改
			addOrUpdateHandle(id) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id)
				})
			},
			//状态为启用
			openEnable (id) {
				this.$confirm(`确定将[id=${id}]状态改成启用?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/sys/user/openEnable/${id}`),
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
			//状态为禁用
			closeEnable (id) {
				this.$confirm(`确定将[id=${id}]的状态改成禁用?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/sys/user/closeEnable/${id}`),
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
			deleteHandle(id) {
				var userIds = id ? [id] : this.dataListSelections.map(item => {
					return item.userId
				})
				this.$confirm(`确定对[id=${userIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/sys/user/delete'),
						method: 'post',
						data: this.$http.adornData(userIds, false)
					}).then(({
						data
					}) => {
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
				}).catch(() => {})
			}
		}
	}
</script>
