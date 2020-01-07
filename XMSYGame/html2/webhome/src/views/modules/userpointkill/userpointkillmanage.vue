<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.pointKillEnable" clearable placeholder="状态">
					<el-option v-for="item in options" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<div><font color="red">总资产=理财金币+金币+提现&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				总投入=会员查询时间区间内在游戏中输的金币累加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</font></div>
		<div><font color="red">
				总收益 = 总产出-总投入&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				总产出=会员查询时间区间内在游戏中赢的金币累加
		</font></div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="userId" header-align="center" align="center" label="会员ID">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="会员账号">
			</el-table-column>
			<el-table-column prop="totalCoin" header-align="center" align="center" label="总资产">
				<template slot-scope="scope">
					<div>{{scope.row.totalCoin/100}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="totalInput" header-align="center" align="center" label="总投入">
				<template slot-scope="scope">
					<div>{{scope.row.totalInput/100}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="totalOutput" header-align="center" align="center" label="总产出">
				<template slot-scope="scope">
					<div>{{scope.row.totalOutput/100}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="profit" header-align="center" align="center" label="总收益">
				<template slot-scope="scope">
					<div>{{scope.row.profit/100}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="pointKillEnable" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<div v-if="scope.row.pointKillEnable">点杀</div>
					<div v-else>正常</div>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.userId,scope.row.userAccount)" :disabled="scope.row.pointKillEnable">设置点杀</el-button>
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
	import AddOrUpdate from './userpointkillmanage-add-or-update'
	import dateutil from '@/utils/datechonse'
	import moment from 'moment'
	export default {
		data() {
			return {
				options: [{
					name: 1,
					label: '点杀'
				}, {
					name: 0,
					label: '正常'
				}],
				pickerOptions2: {
					shortcuts: [{
						text: '今天',
						onClick(picker) {
							const end = dateutil.getToday().endtime;
							const start = dateutil.getToday().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '昨天',
						onClick(picker) {
							const end = dateutil.getYesterday().endtime;
							const start = dateutil.getYesterday().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '本周',
						onClick(picker) {
							const end = dateutil.getCurrWeekDays().endtime;
							const start = dateutil.getCurrWeekDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '上周',
						onClick(picker) {
							const end = dateutil.getLastWeekDays().endtime;
							const start = dateutil.getLastWeekDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '本月',
						onClick(picker) {
							const end = dateutil.getCurrMonthDays().endtime;
							const start = dateutil.getCurrMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '上月',
						onClick(picker) {
							const end = dateutil.getLastMonthDays().endtime;
							const start = dateutil.getLastMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去7天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去30天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去二月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去三月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				dataForm: {
					userAccount: '',
					pointKillEnable:'',
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString())
						.getTime() + 24 * 60 * 60 * 1000 - 1)]
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
		methods: {
			// 获取数据列表
			getDataList() {
				//console.log(this.dataForm.pointKillEnable)
				this.dataListLoading = true
				var startDate = null;
				var endDate = null;
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				this.$http({
					url: this.$http.adornUrl('/userpointkillmanager/userpointkillmanager/listNew'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'pointKillEnable':this.dataForm.pointKillEnable,
						'startTime': startDate,
						'endTime': endDate,
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
			addOrUpdateHandle(userId,userAccount) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(userId,userAccount)
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
						url: this.$http.adornUrl('/userpointkillmanager/userpointkillmanager/delete'),
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
