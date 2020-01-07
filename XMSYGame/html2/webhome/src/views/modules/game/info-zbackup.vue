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
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="gameId" header-align="center" align="center" label="游戏id">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名字">
			</el-table-column>
			<el-table-column prop="roomId" header-align="center" align="center" label="房间id">
			</el-table-column>
			<el-table-column prop="roomName" header-align="center" align="center" label="房间名">
			</el-table-column>
			<el-table-column prop="gradeId" header-align="center" align="center" label="场次id">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" label="游戏场次">
			</el-table-column>
			<el-table-column prop="enable" header-align="center" align="center" label="启用">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.enable" size="small" >是</el-tag>
					<el-tag v-else size="small" type="danger">否</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="display" header-align="center" align="center" label="显示">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.display" size="small" >是</el-tag>
					<el-tag v-else size="small" type="danger">否</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="sence" header-align="center" align="center" label="显示的scene页面">
			</el-table-column>
			<el-table-column prop="rate" header-align="center" :formatter="formatterRate" align="center" label="抽水比例">
			</el-table-column>
			<el-table-column prop="limitLower" header-align="center" align="center" label="限踢">
			</el-table-column>
			<el-table-column prop="limitHigh" header-align="center" align="center" label="限高">
			</el-table-column>
			<el-table-column prop="bscore" header-align="center" align="center" label="游戏底分">
			</el-table-column>
			<el-table-column prop="restrict" header-align="center" align="center" label="入场限制">
			</el-table-column>
			<el-table-column prop="maxTimes" header-align="center" align="center" label="最高倍率">
			</el-table-column>
			<el-table-column prop="maxLines" header-align="center" align="center" label="线数">
			</el-table-column>
			<el-table-column prop="onlineMin" header-align="center" align="center" label="最小在线人数">
			</el-table-column>
			<el-table-column prop="onlineMax" header-align="center" align="center" label="最大在线人数">
			</el-table-column>
			<el-table-column prop="maintenance" header-align="center" align="center" label="维护">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.maintenance" size="small" >否</el-tag>
					<el-tag v-else size="small" type="danger">是</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="updateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
					<!--<el-button v-if="isAuth('gameconfig:gameconfig:save')" type="text" size="small" @click="addOrUpdateAllHandle(scope.row.id)">配置信息</el-button>-->
					<el-button v-if="isAuth('gameconfig:gameconfig:save')" type="text" size="small" @click="addOrUpdatePerfectHandle(scope.row.id)">配置信息</el-button>
					<el-button v-if="isAuth('gameconfig:gameconfig:save')" type="text" size="small" @click="addOrUpdateIntervalGameRate(scope.row.id)">库存区间游戏概率</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add v-if="addVisible" ref="add" @refreshDataList="getDataList"></add>
		<update v-if="updateVisible" ref="update" @refreshDataList="getDataList"></update>
		<!-- 弹窗, 新增 / 修改 所有属性 -->
		<add-or-update-all v-if="addOrUpdateAllVisible" ref="addOrUpdateAll" @refreshDataList="getDataList"></add-or-update-all>
		<!-- 弹窗, 新增 / 修改 区间概率 -->
		<interval-game-rate v-if="intervalGameRateVisible" ref="intervalGameRate" @refreshDataList="getDataList"></interval-game-rate>
		<!-- 弹窗, 新增 / 修改 所有属性 (完美)-->
		<add-or-update-perfect v-if="addOrUpdatePerfectVisible" ref="addOrUpdatePerfect" @refreshDataList="getDataList"></add-or-update-perfect>
	</div>
</template>

<script>
	import Add from './info-add'
	import Update from './info-update'
	import addOrUpdateAll from './gameconfig-add-or-update-all'
	import intervalGameRate from './gameconfig-add-or-update-interval'
	import addOrUpdatePerfect from './gameconfig-add-or-update-perfect'
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
				addOrUpdateAllVisible:false,
				intervalGameRateVisible:false,
				addOrUpdatePerfectVisible:false
			}
		},
		components: {
			Add,
			Update,
			addOrUpdateAll,
			intervalGameRate,
			addOrUpdatePerfect
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/info/info/list'),
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
			formatterRate(row){
				return (row.rate*100)+"%";
				//return (row.rate);
			},
			//修改
			updateHandle(id) {
				this.updateVisible = true
				this.$nextTick(() => {
					this.$refs.update.init(id)
				})
			},
			// 新增 / 修改配置信息
			addOrUpdateAllHandle(gameId) {
				this.addOrUpdateAllVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdateAll.init(gameId,'')
				})
			},
			// 新增 / 修改配置信息（完美）
			addOrUpdatePerfectHandle(id) {
				this.addOrUpdatePerfectVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdatePerfect.init(id,'')
				})
			},
			// 新增 / 修改库存区间游戏概率
			addOrUpdateIntervalGameRate(gameId){
				this.intervalGameRateVisible=true
				this.$nextTick(() => {
					this.$refs.intervalGameRate.init(gameId,'')
				})
			},
			//导出sql语句
			exportSql(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? 'sql语句导出' : 'sql语句导出'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/info/info/exportSql'),
						method: 'post',
						responseType: 'arraybuffer',
						data: this.$http.adornData(ids, false)
					}).then(({
						data
					}) => {
						let blob = new Blob([data], 
						{
							type: 'application/octet-stream,charset=UTF-8'
						});
						let link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = "游戏信息.sql";
						link.click();
						URL.revokeObjectURL(blob);
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
						url: this.$http.adornUrl('/info/info/delete'),
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
