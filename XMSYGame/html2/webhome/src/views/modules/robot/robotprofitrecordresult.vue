<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item label="日期">
				<el-date-picker v-model="dataForm.queryTime" type="date" placeholder="选择日期">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('robotprofitrecordresult:robotprofitrecordresult:sumrobotRecord')" type="primary" @click="sumrobotRecord()">重新统计</el-button>
			</el-form-item>
		</el-form>
    <div style="color: red;">
    	空闲队列机器人:{{idleRobotNum}}
    	待修改机器人:{{modifyRobotNum}}
    </div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="index" width="120" header-align="center" align="center" label="序号">
      </el-table-column>
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="统计时间">
			</el-table-column>
			<el-table-column prop="resultEnable" header-align="center" align="center" label="统计结果">
				<template slot-scope="scope">
					<div v-if="scope.row.resultEnable=='1'">
						成功
					</div>
					<div v-if="scope.row.resultEnable=='0'" style="color: red;">
						失败
					</div>
				</template>
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
					queryTime: new Date()
				},
				dataList: [],
				pageIndex: 1,
				idleRobotNum: 0,
				modifyRobotNum: 0,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false
			}
		},
		components: {
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				//验证时间是否为空
				var time;
				var result = true;
				if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
					time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
				}
				this.$http({
					url: this.$http.adornUrl('/robotprofitrecordresult/robotprofitrecordresult/list'),
					method: 'post',
					data: this.$http.adornData({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'queryTime': time
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						this.idleRobotNum = data.idleRobotNum
						this.modifyRobotNum = data.modifyRobotNum
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
						url: this.$http.adornUrl('/robotprofitrecordresult/robotprofitrecordresult/delete'),
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
			//重新统计
			sumrobotRecord() {
				this.$http({
					url: this.$http.adornUrl('/robotprofitrecordresult/robotprofitrecordresult/sumrobotRecord'),
					method: 'get',
					params: this.$http.adornParams()
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
						this.getDataList()
					}
				})
			}
		}
	}
</script>
