<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.projectName" placeholder="项目名" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.type" clearable placeholder="类型">
					<el-option 
					v-for="item in options" 
					:key="item.name"
					:label="item.label"
					:value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
			 start-placeholder="完成前时间" end-placeholder="完成后时间" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
			</el-date-picker>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('updatelog:updatelog:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('updatelog:updatelog:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="projectName" header-align="center" align="center" label="项目名">
			</el-table-column>
			<el-table-column prop="title" header-align="center" align="center" label="标题">
			</el-table-column>
			<el-table-column prop="content" header-align="center" align="center" label="内容">
			</el-table-column>
			
			<el-table-column prop="type" header-align="center" align="center" label="类型">
				<template slot-scope="scope">
					<div v-if="scope.row.type==0">后台</div>
					<div v-if="scope.row.type==1">前台</div>
					<div v-if="scope.row.type==2">游戏管理服</div>
				</template>
			</el-table-column>
			<el-table-column prop="expectUpdateTime" header-align="center" align="center" label="预计更新时间">
			</el-table-column>
			<el-table-column prop="updateStatus" header-align="center" align="center" label="是否更新">
				<template slot-scope="scope">
					<div v-if="scope.row.updateStatus">是</div>
					<div v-else>否</div>
				</template>
			</el-table-column>
			<el-table-column prop="finishTime" header-align="center" align="center" label="完成时间">
				<template slot-scope="scope">
					<div v-if="scope.row.updateStatus">{{scope.row.finishTime}}</div>
					<div v-else></div>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="UpdateStatusHandle(scope.row.id)" :disabled="scope.row.updateStatus">更新</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
	import AddOrUpdate from './updatelog-add-or-update'
	export default {
		data() {
			return {
				options:[{
					name:0,
					label:'后台'
				},{
					name:1,
					label:'前台'
				},{
					name:2,
					label:'游戏管理服台'
				}],
				pickerOptions2: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				dataForm: {
					queryTime: [],
					key: ''
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
				var startDate = null;
				var endDate = null;
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = timeArr[0];
					//console.log(startDate);
					if (timeArr.length > 1) {
						endDate = timeArr[1];
						//console.log(endDate);
					}
				}
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/updatelog/updatelog/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'type': this.dataForm.type,
						'projectName': this.dataForm.projectName,
						'startTime': startDate,
						'endTime': endDate
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
			//更新操作
			UpdateStatusHandle(id){
				this.$confirm(`确定对[id=${id}]进行更新操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/updatelog/updatelog/updateStatus'),
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
						url: this.$http.adornUrl('/updatelog/updatelog/delete'),
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
