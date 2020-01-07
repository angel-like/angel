<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="daterange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.key" placeholder="游戏ID或名称" clearable></el-input>
			</el-form-item>
			<el-form-item label="统计分类">
				<el-select v-model="dataForm.robot" placeholder="请选择">
					<el-option label="--全部--" :value="null">
					</el-option>
					<el-option label="用户" :value="false">
					</el-option>
					<el-option label="机器人" :value="true">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="gameId" header-align="center" align="center" label="游戏id">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名称">
			</el-table-column>
			<el-table-column prop="userBet" header-align="center" align="center" label="下注金币">
			</el-table-column>
			<el-table-column prop="userProfit" header-align="center" align="center" label="用户盈利">
			</el-table-column>
			<el-table-column prop="waterProfit" header-align="center" align="center" label="抽水盈利">
			</el-table-column>
			<el-table-column prop="waterRate" header-align="center" align="center" label="抽水比例">
			</el-table-column>
			<el-table-column prop="platformProfit" header-align="center" align="center" label="平台抽水">
			</el-table-column>
			<el-table-column prop="platformRate" header-align="center" align="center" label="平台抽水比例">
			</el-table-column>
			<el-table-column prop="time" :formatter="dateFormat" header-align="center" align="center" label="时间">
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
				pickerOptions2: {
					shortcuts: [{
						text: '上一周',
						onClick(picker) {
							const end = moment(new Date()).add(-1, 'days');
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 8);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '上个月',
						onClick(picker) {
							const end = moment(new Date()).add(-1, 'days');
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 31);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = moment(new Date()).add(-1, 'days');
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 91);
							picker.$emit('pick', [start, end]);
						}
					}],
					disabledDate(time) {
						return time.getTime() > moment(new Date()).add(-1, 'days');
					}
				},
				dataForm: {
					gameId: '',
					robot: null,
					queryTime: [moment(new Date()).add(-1, 'days'), moment(new Date()).add(-1, 'days')]
				},
				// 				startDay: {
				// 					disabledDate(time) {
				// 						let curDate = (new Date()).getTime();
				// 						let three = 90 * 24 * 3600 * 1000;
				// 						let threeMonths = curDate - three;
				// 						return time.getTime() > moment(new Date()).add(-1, 'days') || time.getTime() < threeMonths;
				// 					}
				// 				},
				// 				endDay: {
				// 					disabledDate(time) {
				// 						let curDate = (new Date()).getTime();
				// 						let three = 90 * 24 * 3600 * 1000;
				// 						let threeMonths = curDate - three;
				// 						return time.getTime() > moment(new Date()).add(-1, 'days') || time.getTime() < threeMonths;
				// 					}
				// 				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false
			}
		},

		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				var startDate = "";
				var endDate = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = moment(timeArr[0]).format("YYYY-MM-DD");
					if (timeArr.length > 1) {
						endDate = moment(timeArr[1]).format("YYYY-MM-DD");
					}
				}
				// 				console.log("startDate："+startDate)
				// 				console.log("endDate："+endDate)
				this.$http({
					url: this.$http.adornUrl('/gameanalysis/gameanalysis/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'key': this.dataForm.key,
						'startDate': startDate,
						'endDate': endDate,
						'robot': this.dataForm.robot
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
			dateFormat: function(row, column) {
				var date = row[column.property];
				if (date == undefined) {
					return "";
				}
				return moment(date).format("YYYY-MM-DD");
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
			}
		}
	}
</script>


<style>
	.el-date-editor .el-range-separator {
    padding: 0 0px;
}
</style>
