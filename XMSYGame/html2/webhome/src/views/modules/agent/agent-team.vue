<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.account" placeholder="账号名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="account" header-align="center" align="center" label="账号名称">
			</el-table-column>
			<el-table-column prop="recommendationCode" header-align="center" align="center" label="邀请码">
			</el-table-column>
			<el-table-column prop="directNum" header-align="center" align="center" label="直属下线">
				<template slot-scope="scope">
					<div v-if="scope.row.directNum>0">
						<a type="text" size="small" style="cursor:pointer "  @click="getSubordinateList(scope.row.id)"><u>{{scope.row.directNum}}</u></a>
					</div>
					<div v-if="scope.row.directNum==0">
						0
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="directValidBet" header-align="center" align="center" label="直属打码">
				<template slot-scope="scope">
					{{scope.row.directValidBet/100}}
				</template>
			</el-table-column>
			<el-table-column prop="sumNum" header-align="center" align="center" label="总人数">
			</el-table-column>
			<el-table-column prop="sumValidBet" header-align="center" align="center" label="总打码">
				<template slot-scope="scope">
					{{scope.row.sumValidBet/100}}
				</template>
			</el-table-column>
			<el-table-column prop="agentCommission" header-align="center" align="center" label="盈利佣金">
			</el-table-column>
			<el-table-column prop="agentCoin" header-align="center" align="center" label="盈利金币">
				<template slot-scope="scope">
					{{scope.row.agentCoin/100}}
				</template>
			</el-table-column>
			<el-table-column prop="agentHierarchyName" header-align="center" align="center" label="代理层级">
			</el-table-column>
			<el-table-column prop="recommendationHierarchyName" header-align="center" align="center" label="邀请等级">
				<template slot-scope="scope">
					<div v-if="scope.row.recommendationHierarchyName!=''&&scope.row.recommendationHierarchyName!=null">
						{{scope.row.recommendationHierarchyName}}
					</div>
					<div v-else>
						无
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="teamEnable" header-align="center" align="center" label="团队权限">
				<template slot-scope="scope">
					<el-switch v-model="scope.row.teamEnable" active-color="#13ce66" inactive-color="#ff4949" @change="updateTeamEnable(scope.row.id,scope.row.teamEnable)">
					</el-switch>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="cancelAgencyAuthority(scope.row.id)">取消代理</el-button>
					<el-button type="text" size="small" @click="updateAgenHierarchy(scope.row.id)">修改代理层级</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 下级代理 -->
		<subordinate v-if="subordinateVisible" ref="subordinate" @refreshDataList="getDataList"></subordinate>
		<!-- 获取url图片的弹框 -->
		<el-dialog title="代理层级" :close-on-click-modal="false" :visible.sync="visible">
			<el-input v-model="userId" v-if="false"></el-input>
			<el-select v-model="agentId" placeholder="代理层级" clearable>
				<el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
				</el-option>
			</el-select>
			<span slot="footer" class="dialog-footer">
				<el-button @click="visible = false">取消</el-button>
				<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	import Subordinate from './agent-subordinate'
	export default {
		data() {
			return {
				dataForm: {
					account: ''
				},
				visible: false,
				options: [],
				agentId: '',
				userId: '',
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				subordinateVisible: false
			}
		},
		components: {
			Subordinate
		},
		activated() {
			this.getDataList()
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl(`/useragenthierarchy/useragenthierarchy/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.options = data.list
					}
				});

				this.$http({
					url: this.$http.adornUrl('/agent/agent/teamList'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'account': this.dataForm.account
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
			//修改团队权限滑块触发事件
			updateTeamEnable(userId, teamEnable) {
				this.$http({
					url: this.$http.adornUrl('/agent/agent/updateTeamEnable'),
					method: 'get',
					params: this.$http.adornParams({
						'userId': userId,
						'teamEnable': teamEnable
					})
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
			},
			//取消代理
			cancelAgencyAuthority(id) {
				this.$http({
					url: this.$http.adornUrl(`/agent/agent/AgencyAuthority/` + id),
					method: 'post'
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
			},
			//修改代理代理
			updateAgenHierarchy(id) {
				this.userId = id
				this.visible = true
			},
			//保存
			dataFormSubmit() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/agent/agent/save'),
					method: 'get',
					params: this.$http.adornParams({
						'userId': this.userId,
						'agentId': this.agentId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.$message({
							message: '操作成功',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.visible = false
								this.getDataList()
							}
						})
					} else {
						this.$message.error(data.msg)
					}
					this.dataListLoading = false

				})
			},
			// 获取下线列表
			getSubordinateList(id) {
				this.subordinateVisible = true
				this.$nextTick(() => {
					this.$refs.subordinate.init(id)
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
						url: this.$http.adornUrl('/user/user/delete'),
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
