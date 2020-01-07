<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.name" placeholder="游戏名字" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('info:info:save')" type="primary" @click="addHandle()">新增</el-button>
				<el-button v-if="isAuth('info:info:save')" type="primary" @click="exportSql()" :disabled="dataListSelections.length <= 0">导出SQL</el-button>
			</el-form-item>
		</el-form>
		<div style="color: red;padding-bottom: 10px;">是否开放（是：不在维护中，否：维护中）</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<!--
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>-->
			<el-table-column prop="gameId" header-align="center" align="center" label="游戏id">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名字">
			</el-table-column>
			<el-table-column prop="roomName" header-align="center" align="center" label="房间名">
			</el-table-column>
			<el-table-column
			  prop="finished"
			  header-align="center"
			  align="center"
			  label="是否完成">
			  <template slot-scope="scope">
			    <el-switch v-model="scope.row.finished" active-color="#13ce66" inactive-color="#ff4949"
			               @change="updateEnable(scope.row.gameId,scope.row.finished)">
			    </el-switch>
			  </template>
			</el-table-column>
			<el-table-column
			  prop="maintenance"
			  header-align="center"
			  align="center"
			  label="是否开放">
			  <template slot-scope="scope">
			    <el-switch v-model="scope.row.maintenance" active-color="#13ce66" inactive-color="#ff4949"
			                @change="updateMaintenance(scope.row.gameId,scope.row.maintenance)">
			    </el-switch>
			  </template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="updateHandle(scope.row.gameId)">配置信息</el-button>
					<!--<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
					<el-button v-if="isAuth('gameconfig:gameconfig:save')" type="text" size="small" @click="addOrUpdatePerfectHandle(scope.row.id)">配置信息</el-button>
					<el-button v-if="isAuth('gameconfig:gameconfig:save')" type="text" size="small" @click="addOrUpdateIntervalGameRate(scope.row.id)">库存区间游戏概率</el-button>-->
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add v-if="addVisible" ref="add" @refreshDataList="getDataList"></add>
		<update v-if="updateVisible" ref="update" @refreshDataList="getDataList"></update>
		<!-- 弹窗, 新增 / 修改 所有属性 
		<add-or-update-all v-if="addOrUpdateAllVisible" ref="addOrUpdateAll" @refreshDataList="getDataList"></add-or-update-all>-->
		<!-- 弹窗, 新增 / 修改 区间概率 
		<interval-game-rate v-if="intervalGameRateVisible" ref="intervalGameRate" @refreshDataList="getDataList"></interval-game-rate>-->
		<!-- 弹窗, 新增 / 修改 所有属性 (完美)
		<add-or-update-perfect v-if="addOrUpdatePerfectVisible" ref="addOrUpdatePerfect" @refreshDataList="getDataList"></add-or-update-perfect>-->
	</div>
</template>

<script>
	import Add from './info-add'
	import Update from './info-update'
	/*import addOrUpdateAll from './gameconfig-add-or-update-all'
	import intervalGameRate from './gameconfig-add-or-update-interval'
	import addOrUpdatePerfect from './gameconfig-add-or-update-perfect'*/
	export default {
		data() {
			return {
				dataForm: {
					name: ''
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addVisible: false,
				updateVisible: false,
				addOrUpdateAllVisible: false,
				intervalGameRateVisible: false,
				addOrUpdatePerfectVisible: false
			}
		},
		components: {
			Add,
			Update,
			//addOrUpdateAll,
			//intervalGameRate,
			//addOrUpdatePerfect
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/info/info/listNew'),
					method: 'post',
					data: this.$http.adornData({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'name': this.dataForm.name
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
			//修改状态滑块触发事件
			updateEnable(id, finished) {
			  this.$http({
			    url: this.$http.adornUrl('/info/info/updateEnable'),
			    method: 'get',
			    params: this.$http.adornParams({
			      'id': id,
			      'finished': finished
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
			//修改状态滑块触发事件
			updateMaintenance(id, maintenance) {
			  this.$http({
			    url: this.$http.adornUrl('/info/info/updateMaintenance'),
			    method: 'get',
			    params: this.$http.adornParams({
			      'id': id,
			      'maintenance': maintenance
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
			// 多选
			selectionChangeHandle(val) {
				this.dataListSelections = val
			},
			// 新增
			addHandle(id) {
				this.addVisible = true
				this.$nextTick(() => {
					this.$refs.add.init(id)
				})
			},
			formatterRate(row) {
				return (row.rate * 100) + "%";
				//return (row.rate);
			},
			//修改
			updateHandle(gameId) {
				this.updateVisible = true
				this.$nextTick(() => {
					this.$refs.update.init(gameId)
				})
			},
			// 新增 / 修改配置信息
			addOrUpdateAllHandle(gameId) {
				this.addOrUpdateAllVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdateAll.init(gameId, '')
				})
			},
			// 新增 / 修改配置信息（完美）
			addOrUpdatePerfectHandle(id) {
				this.addOrUpdatePerfectVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdatePerfect.init(id, '')
				})
			},
			// 新增 / 修改库存区间游戏概率
			addOrUpdateIntervalGameRate(gameId) {
				this.intervalGameRateVisible = true
				this.$nextTick(() => {
					this.$refs.intervalGameRate.init(gameId, '')
				})
			},
			//导出sql语句
			exportSql(gameId) {
				var ids = gameId ? [gameId] : this.dataListSelections.map(item => {
					return item.gameId
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${gameId ? 'sql语句导出' : 'sql语句导出'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					// location.href = "info/info/exportSql?ids=" + ids;
					this.$http({
						url: this.$http.adornUrl('/info/info/exportSqlNew'),
						method: 'post',
						responseType: 'arraybuffer',
						data: this.$http.adornData(ids, false)
					}).then(({
						data
					}) => {
						/*console.log(11111)
						console.log(data)
						console.log(123456789)*/
						let blob = new Blob([data], {
							type: 'application/octet-stream,charset=UTF-8'
						});
						let link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = "游戏信息.sql";
						link.click();
						URL.revokeObjectURL(blob);
						/*if (data && data.code === 200) {
							let blob = new Blob([data], 
							{
								type: 'application/octet-stream; charset=UTF-8'
							});
							let link = document.createElement('a');
							link.href = window.URL.createObjectURL(blob);
							link.download = title;
							link.click();
							URL.revokeObjectURL(blob);
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
						}*/
					})
				})
			},
			// 删除
			deleteHandle(id) {
				this.$confirm(`确定对[id=${id}]进行删除操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/info/info/delete'),
						method: 'post',
						data: this.$http.adornData(id, false)
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
