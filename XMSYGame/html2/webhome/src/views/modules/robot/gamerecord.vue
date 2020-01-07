<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="账号" clearable></el-input>
			</el-form-item>
			<el-form-item label="游戏">
				<el-select v-model="dataForm.gameId" placeholder="请选择游戏" clearable>
					<el-option v-for="item in gameOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="场次">
				<el-select v-model="dataForm.gradeId" placeholder="请选择场次" clearable>
					<el-option v-for="item in gradeOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="日期">
				<el-date-picker v-model="dataForm.queryTime" type="date" placeholder="选择日期">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('gamerecord:gamerecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('gamerecord:gamerecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
		</el-form>
		<div style="color: red;">
			总盈利(元):{{-countProfit/1000000}}万
		</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			
      <el-table-column type="index" width="120" header-align="center" align="center" label="序号">
      </el-table-column>
      <el-table-column prop="userAccount" header-align="center" align="center" label="用户账号">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名称">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" label="场次名称">
			</el-table-column>
			<el-table-column prop="betCoins" header-align="center" align="center" label="下注总金币">
				<template slot-scope="scope">
					{{-scope.row.betCoins/100}}
				</template>
			</el-table-column>
			<el-table-column prop="profitCoins" header-align="center" align="center" label="盈利金币">
				<template slot-scope="scope">
					{{-scope.row.profitCoins/100}}
				</template>
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="游戏时间">
			</el-table-column>
		</el-table>

		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
	</div>
</template>

<script>
	import moment from 'moment';
	export default {
		data() {
			return {
				dataForm: {
					userAccount: '',
					gameId: null,
					gradeId: null,
					queryTime: new Date()
				},
				gameOptions: [],
				gradeOptions: [{
						"id": 1,
						"name": "初级场"
					}, {
						"id": 2,
						"name": "中级场"
					},
					{
						"id": 3,
						"name": "高级场"
					}
				],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				countProfit: 0,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false
			}
		},
		components: {},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				//验证时间是否为空
				var time;
				var result = true;
				if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
					time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
				}
				this.dataListLoading = true
				//为游戏下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gameOptions = data.data
					}
				});
				this.$http({
					url: this.$http.adornUrl('/robotgamerecord/robotgamerecord/list'),
					method: 'post',
					data: this.$http.adornData({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'gameId': this.dataForm.gameId,
						'queryTime': time,
						'gradeId': this.dataForm.gradeId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						this.countProfit = data.countProfit
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
					this.$refs.addOrUpdate.init(id)
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
						url: this.$http.adornUrl('/gamerecord/gamerecord/delete'),
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
