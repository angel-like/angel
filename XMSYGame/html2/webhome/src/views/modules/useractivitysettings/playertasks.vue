<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.title" placeholder="标题" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.roomId" placeholder="房间类型 " @change="getGameSelect()" clearable>
					<el-option v-for="item in roomOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-tooltip class="item" effect="dark" content="房间类型下对应的游戏" placement="top-start">
				<el-form-item>
					<el-select v-model="dataForm.gameId" placeholder="游戏类型 " clearable>
						<el-option v-for="item in gameOptions" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-form-item>
				<el-select v-model="dataForm.type" placeholder="任务类型 " clearable>
					<el-option v-for="item in options" :key="item.code" :label="item.name" :value="item.code">
					</el-option>
				</el-select>
			</el-form-item>
				<el-form-item>
				<el-select v-model="dataForm.enable" placeholder="是否启用 " clearable>
					<el-option v-for="item in enableOptions" :key="item.code" :label="item.name" :value="item.code">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('playertasks:playertasks:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!--<el-button v-if="isAuth('playertasks:playertasks:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<!--<el-table-column
      	type="index"
      	width="120"
      	header-align="center"
      	align="center"
      	label="序号">
      </el-table-column>-->
			<el-table-column prop="taskTypeName" header-align="center" align="center" label="任务类型">
			</el-table-column>
			<el-table-column prop="roomName" header-align="center" align="center" label="房间类型">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏类型">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" label="游戏场次">
			</el-table-column>
			<el-table-column prop="title" header-align="center" align="center" label="标题">
			</el-table-column>
			<el-table-column prop="taskNum" header-align="center" align="center" label="任务次数">
			</el-table-column>
			<el-table-column prop="functionalTypeName" header-align="center" align="center" label="福缘任务类型">
			</el-table-column>
			<el-table-column prop="propName" header-align="center" align="center" label="道具">
			</el-table-column>
			<el-table-column prop="propNum" header-align="center" align="center" label="道具数量">
				<template slot-scope="scope">
					<div v-if="scope.row.propId==1">{{scope.row.propNum/100}}</div>
					<div v-if="scope.row.propId==2">{{scope.row.propNum}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="condition" header-align="center" align="center" label="条件">
				<template slot-scope="scope">
					<div v-if="scope.row.type==2">{{Math.round(scope.row.condition/100)}}</div>
					<div v-else>{{scope.row.condition}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="confrontationTypeName" header-align="center" align="center" label="对局要求">
				<template slot-scope="scope">
					<div v-if="scope.row.confrontationRequirement==0">无</div>
					<div else>{{scope.row.confrontationTypeName}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="sorts" header-align="center" align="center" label="排序">
			</el-table-column>
			<el-table-column prop="enable" header-align="center" align="center" label="是否启用">
				<template slot-scope="scope">
					<el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ff4949" @change="updateEnable(scope.row.id,scope.row.enable)">
					</el-switch>
				</template>
			</el-table-column>
			<!--<el-table-column prop="cycle" header-align="center" align="center" label="周期(天)">
			</el-table-column>
			<el-table-column prop="effectTime" header-align="center" align="center" label="生效时间">
			</el-table-column>
			<el-table-column prop="failureTime" header-align="center" align="center" label="失效时间">
				<template slot-scope="scope">
					永久
				</template>
			</el-table-column>-->
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<!--<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
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
	import AddOrUpdate from './playertasks-add-or-update'
	export default {
		data() {
			return {
				enableOptions:[{
					"code": true,
					"name": "开启"
				},{
					"code": false,
					"name": "关闭"
				}],
				optionsAll: [],
				opAll: [{
					"id": 0,
					"name": "全部"
				}],
				options: [],
				gameOptions: [],
				//roomOptions: [],
				roomOptions: [{
					id: 1,
					name: '匹配房间'
				}, {
					id: 3,
					name: '百人房间'
				}, {
					id: 4,
					name: '拉霸房间'
				}],
				dataForm: {
					title: '',
					roomId: '',
					gameId: '',
					type: ''
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
		created() {
			this.keyupSubmit()
		},
		methods: {

			getGameSelect() {

				if (this.dataForm.roomId === 0) {
					this.gameOptions = this.opAll
					return;
				}
				if (this.dataForm.roomId === null || this.dataForm.roomId === '') {
					this.gameOptions = []
					return;
				}
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelectForTask`),
					method: 'get',
					params: this.$http.adornParams({
						'roomId': this.dataForm.roomId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gameOptions = this.opAll.concat(data.data)
					} else {
						this.gameOptions = this.opAll
					}
				});
			},
			// 获取数据列表
			getDataList() {
				//为任务下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "TaskType"),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.options = data.data
					}
				});

				/*this.$http({
				  url: this.$http.adornUrl(`/gameinfo/gameinfo/roomSelect`),
				  method: 'get',
				  params: this.$http.adornParams()
				}).then(({data}) => {
				  if (data && data.code === 200) {
				    this.roomOptions =this.opAll.concat(data.data)
				  }else{
				    this.roomOptions = this.opAll
				  }
				});*/
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/playertasks/playertasks/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'title': this.dataForm.title,
						'roomId': this.dataForm.roomId,
						'gameId': this.dataForm.gameId,
						'type': this.dataForm.type,
						'enable': this.dataForm.enable,
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
			keyupSubmit() {
				document.onkeydown = e => {
					let _key = window.event.keyCode;
					if (_key === 13) {
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery() {
				this.pageIndex = 1;
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
			//修改状态滑块触发事件
			updateEnable(id,enable) {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl(`/playertasks/playertasks/taskEnable`),
					method: 'post',
					data: this.$http.adornData({
						'id': id ,
						'enable': enable
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
								this.$emit('refreshDataList')
							}
						})
					} else {
						this.$message.error(data.msg)
					}
					this.dataListLoading = false
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
						url: this.$http.adornUrl('/playertasks/playertasks/delete'),
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
