<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item label="房间">
				<el-select v-model="dataForm.roomId" placeholder="请选择房间" clearable>
					<el-option v-for="item in roomOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('gameinfo:gameinfo:close')" type="primary" @click="colseRobot()">重置人数配置</el-button>
				<el-button v-if="isAuth('gameinfo:gameinfo:forceTaskRobot')" type="danger" @click="forceTaskRobot()" :disabled="dataListSelections.length <= 0">强制回收机器人</el-button>
				<el-button v-if="isAuth('gameinfo:gameinfo:forceOnTaskRobot')" type="primary" @click="forceOnTaskRobot()" :disabled="dataListSelections.length <= 0">还原机器人</el-button>
			</el-form-item>
		</el-form>
    <div style="color: red;">
    	总盈利:{{profit}}(万)
    </div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="name" header-align="center" align="center" label="游戏名称">
			</el-table-column>
			<el-table-column prop="num" header-align="center" align="center" label="机器人数量">
			</el-table-column>
			<el-table-column prop="profitCoin" header-align="center" align="center" label="总盈利(元)">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="selectInfo(scope.row.id)">{{scope.row.profitCoin/10000}}</el-button>
				</template>
			</el-table-column>
			<el-table-column header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<!-- <el-button type="text" size="small" @click="selectInfo(scope.row.id)">盈利详情</el-button> -->
					<el-button type="text" size="small" v-if="isAuth('gameinfo:gameinfo:robotGameConfig')" @click="addOrUpdateHandle(scope.row.id)">修改配置</el-button>
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
	import AddOrUpdate from './gameinfo-add-or-update'
	export default {
		data() {
			return {
				dataForm: {
					roomId: ''
				},
				roomOptions: [],
				dataList: [],
				pageIndex: 1,
				profit: '',
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
				//为房间下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/roomSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.roomOptions = data.data
					}
				});
				this.$http({
					url: this.$http.adornUrl('/gameinfo/gameinfo/robotGameList'),
					method: 'post',
					data: this.$http.adornData({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'roomId': this.dataForm.roomId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.list
						this.totalPage = data.count
						this.profit = ((data.profit) / 1000000).toLocaleString()
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
			//关闭游戏机器人----重置人数配置
			colseRobot() {
				this.$confirm(`确定要关闭所有游戏中机器人?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl(`/gameinfo/gameinfo/close`),
						method: 'post',
						data: this.$http.adornData({})
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

				this.dataListLoading = false
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
			// c查看
			selectInfo(gameId) {
				this.$router.push({
					path: '/gradeinfo',
					query: {
						gameId: gameId
					}
				})
			},
			// 新增 / 修改
			addOrUpdateHandle(gameId) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(gameId)
				})
			},
			// 强制回收机器人
			forceTaskRobot(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对当前页所选游戏进行强制回收操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/gameinfo/gameinfo/forceTaskRobot'),
						method: 'post',
						data: this.$http.adornData(
							ids, false
						)
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
			// 还原机器人
			forceOnTaskRobot(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对当前页所选游戏进行还原机器人操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/gameinfo/gameinfo/forceOnTaskRobot'),
						method: 'post',
						data: this.$http.adornData(
							ids, false
						)
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
