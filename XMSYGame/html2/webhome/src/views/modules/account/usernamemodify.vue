<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.applicantAccount" placeholder="申请人操作账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.status" clearable placeholder="状态">
					<el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userOldName" placeholder="修改前姓名" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('usernamemodify:usernamemodify:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!--
				<el-button v-if="isAuth('usernamemodify:usernamemodify:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<!-- <el-table-column prop="userId" header-align="center" align="center" label="会员id">
			</el-table-column> -->
			<el-table-column prop="userAccount" header-align="center" align="center" label="会员账号">
			</el-table-column>
			<el-table-column prop="userOldName" header-align="center" align="center" label="修改前姓名">
			</el-table-column>
			<el-table-column prop="userNewName" header-align="center" align="center" label="修改后姓名">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="申请时间">
			</el-table-column>
			<el-table-column prop="applicantAccount" header-align="center" align="center" label="申请人操作账号">
			</el-table-column>
			<el-table-column prop="status" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<div v-if="scope.row.status==0">未审核</div>
					<div v-if="scope.row.status==1">驳回</div>
					<div v-if="scope.row.status==2">成功</div>
				</template>
			</el-table-column>
			<el-table-column prop="checkerAccount" header-align="center" align="center" label="审核人账号">
			</el-table-column>
			<el-table-column prop="remake" header-align="center" align="center" label="备注">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="updateStatusSuccess2(scope.row.id)" :disabled="scope.row.status!=0">确认</el-button>
					<el-button type="text" size="small" @click="updateStatusFail2(scope.row.id)" :disabled="scope.row.status!=0">驳回</el-button>
					<!--<el-button type="text" size="small" @click="updateStatusSuccess(scope.row.id)" :disabled="scope.row.status!=0">确认</el-button>
					<el-button type="text" size="small" @click="updateStatusFail(scope.row.id)" :disabled="scope.row.status!=0">驳回</el-button>
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					 <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
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
	import AddOrUpdate from './usernamemodify-add-or-update'
	export default {
		data() {
			return {
				options:[
					{
						id:0,
						name:"未审核"
					},
					{
						id:1,
						name:"驳回"
					},
					{
						id:2,
						name:"成功"
					}
				],
				dataForm: {
					userAccount: '',
					applicantAccount:'',
					status:'',
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
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/usernamemodify/usernamemodify/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'applicantAccount': this.dataForm.applicantAccount,
						'status': this.dataForm.status,
						'userOldName': this.dataForm.userOldName,
					})
				}).then(({
					data
				}) => {
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
					this.$refs.addOrUpdate.init(id,0)
				})
			},
			// 确认修改会员名称 2
			updateStatusSuccess2(id) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,1)
				})
			},
			// 驳回修改会员名称 2
			updateStatusFail2(id) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,2)
				})
			},
			//确认修改会员名称--暂时不用  不能删掉
			updateStatusSuccess(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '修改会员名称' : '批量修改会员名称'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/usernamemodify/usernamemodify/updateStatusSuccess'),
						method: 'post',
						data: this.$http.adornData(ids, false)
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
				})
			},
			//驳回修改会员名称--暂时不用  不能删掉
			updateStatusFail(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '驳回修改会员名称' : '批量驳回修改会员名称'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/usernamemodify/usernamemodify/updateStatusFail'),
						method: 'post',
						data: this.$http.adornData(ids, false)
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
				})
			},
			// 删除
			deleteHandle(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/usernamemodify/usernamemodify/delete'),
						method: 'post',
						data: this.$http.adornData(ids, false)
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
				})
			}
		}
	}
</script>
