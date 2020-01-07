<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-select v-model="dataForm.gameId"  filterable    clearable  placeholder="游戏">
					<el-option v-for="item in gameList" :key="item.id" :label="item.gameName+'-'+item.gradeName" :value="item.id">	</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('gameconfig:gameconfig:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!-- <el-button v-if="isAuth('gameconfig:gameconfig:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="gameArray" header-align="center" align="center" label="游戏名称">
			</el-table-column>
			<el-table-column prop="name" header-align="center" align="center" label="属性名称">
			</el-table-column>
			<el-table-column prop="startVal" :formatter="formaterStartVal" header-align="center" align="center" label="库存开始值(元)">
			</el-table-column>
			<el-table-column prop="endVal"  :formatter="formaterEndVal" header-align="center" align="center" label="库存结束值(元)">
			</el-table-column>
			<el-table-column prop="val" header-align="center" align="center" label="游戏属性值">
				<template slot-scope="scope">
					{{Math.round(scope.row.val*100)}}%
				</template>
			</el-table-column>
			<el-table-column prop="description" header-align="center" align="center" label="属性描述">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
					<el-button type="text" size="small" @click="addOrUpdateIntervalGameRate(scope.row.gameId)">批量修改</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		<!-- 弹窗, 新增 / 修改 所有属性 -->
		<interval-game-rate v-if="intervalGameRateVisible" ref="intervalGameRate" @refreshDataList="getDataList"></interval-game-rate>
	</div>
</template>

<script>
	import AddOrUpdate from './gameconfig-add-or-update'
	import intervalGameRate from './gameconfig-add-or-update-interval'
	export default {
		data() {
			
			return {
				gameList: [],
				dataForm: {
					id: 0,
					gameId: '',
					name: 'intervalGameRate',
					val: '',
					gameArray: '',
					description: '',
					gameArray: '',
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				intervalGameRateVisible:false
			}
		},
		components: {
			AddOrUpdate,
			intervalGameRate
		},
		activated() {
			this.getDataList()
		},
		methods: {
			formaterStartVal(row, column){
				return (row.startVal/100).toFixed(2);
			},
			formaterEndVal(row, column){
				return (row.endVal/100).toFixed(2);
			},
			// 获取数据列表
			getDataList() {
					this.$http({
						url: this.$http.adornUrl(`/info/info/GameList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.gameList = data.gameList
						}
					})
				
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/gameconfig/gameconfig/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'gameId': this.dataForm.gameId,
						'interval': true
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
					this.$refs.addOrUpdate.init(id,'intervalGameRate')
				})
			},
			// 新增 / 修改库存区间游戏概率
			addOrUpdateIntervalGameRate(gameId){
				this.intervalGameRateVisible=true
				this.$nextTick(() => {
					this.$refs.intervalGameRate.init(gameId,'')
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
						url: this.$http.adornUrl('/gameconfig/gameconfig/delete'),
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
