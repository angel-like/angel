<template>
	<el-dialog title="下线提供佣金详情" :close-on-click-modal="false" :visible.sync="visible" width="75%">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.time" type="daterange" range-separator="至" start-placeholder="开始日期"
				 end-placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userId" placeholder="会员账号" v-if="false" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" style="width: 100%;">
			<el-table-column prop="recordDate" header-align="center" align="center" label="记录日期">
			</el-table-column>
			<el-table-column prop="num" header-align="center" align="center" label="统计人数">
			</el-table-column>
			<el-table-column prop="commission" header-align="center" align="center" label="佣金">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="recordHandle(scope.row.recordDate)">详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<div style="color: red;">
		总盈利(元):{{sumCommission}}
		</div>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
	</el-dialog>
</template>

<script>
	import moment from 'moment';
	export default {
		data() {
			return {
				dataForm: {
					time: '',
					userId: null,
				},
				sumCommission:0,
				dataList: [],
				visible: false,
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
			}
		},
		components: {},
		methods: {
			recordHandle(date) {
				this.$parent.recordHandle(date, this.dataForm.userId)
			},
			init(id) {
				this.dataForm.userId = id
				this.getDataList()
			},
			// 获取数据列表
			getDataList() {
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.time;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				this.visible = true
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/dateList'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userId': this.dataForm.userId,
						'startDate': startTime,
						'endDate': endTime
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						this.sumCommission=data.commission
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
						url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/delete'),
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

<style>
	.el-date-editor .el-range-separator {
    padding: 0 0px;
}
</style>
