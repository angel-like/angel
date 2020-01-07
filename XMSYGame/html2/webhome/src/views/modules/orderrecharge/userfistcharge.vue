<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>



			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
        <el-button @click="getDataList('today')">今天</el-button>
        <el-button @click="getDataList('week')">近7天</el-button>
        <el-button @click="getDataList('nearlyThirtyDays')">近30天</el-button>
				<el-button @click="exportCSV()">下载Excel</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column
        type="index"
        width="120"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>
			<!-- <el-table-column prop="userId" header-align="center" align="center" label="会员id">
			</el-table-column> -->
			<el-table-column prop="userName" header-align="center" align="center" label="会员名称">
			</el-table-column>
      <el-table-column prop="userAccount" header-align="center" align="center" label="会员账号">
      </el-table-column>
      <el-table-column prop="superiorsName" header-align="center" align="center" label="直属上级"> <!-- 先展示本身ID-->
        <template slot-scope="scope">
            <span v-if="scope.row.superiorsName===undefined||scope.row.superiorsName===''||scope.row.superiorsName===null">暂无</span>
            <span v-else>{{scope.row.superiorsName}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="hierarchyName" header-align="center" align="center" label="所属层级">
      </el-table-column>

			<el-table-column prop="rechargeType" header-align="center" align="center" label="充值类型">
				<template slot-scope="scope">
					<div v-if="scope.row.rechargeType==1">后台人工充值</div>
					<div v-if="scope.row.rechargeType==2">第三方支付({{scope.row.rechargePlatformName}})</div>
					<div v-if="scope.row.rechargeType==3">线下银行卡充值</div>
				</template>
			</el-table-column>
			<!--
			<el-table-column prop="depositType" header-align="center" align="center" label="具体类型">
			</el-table-column>-->
			<el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
			</el-table-column>
			<el-table-column prop="amount" header-align="center" align="center" label="资金变动">
				<template slot-scope="scope">
					{{scope.row.amount}}
				</template>
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="首充时间">
			</el-table-column>
		<!--	<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>-->
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
	</div>
</template>

<script>
	//import AddOrUpdate from './adchannelconfig-add-or-update'
	import dateutil from '@/utils/datechonse'
	import moment from 'moment'
	export default {
		data() {
			return {
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
					},{
						text: '本月',
						onClick(picker) {
							const end = dateutil.getCurrMonthDays().endtime;
							const start = dateutil.getCurrMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '上月',
						onClick(picker) {
							const end = dateutil.getLastMonthDays().endtime;
							const start = dateutil.getLastMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去7天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去30天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去二月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
							picker.$emit('pick', [start, end]);
						}
					},{
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
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)],
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
			// AddOrUpdate
		},
		activated() {
			this.getDataList()
		},
		created() {
			this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList(time) {
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
        timeArr = this.time;
        if ('today' == time) {
        	timeArr=[];
        	startDate=moment().locale('zh-cn').format('YYYY-MM-DD 00:00:00');
        	endDate=moment().locale('zh-cn').format('YYYY-MM-DD HH:mm:ss');
        	timeArr.push(startDate);
        	timeArr.push(endDate);
        	this.time=timeArr;
        }else if ('week' == time) {
					timeArr=[];
					startDate=moment().locale('zh-cn').subtract(7, 'days').format('YYYY-MM-DD 00:00:00');
					endDate=moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 23:59:59');
					timeArr.push(startDate);
					timeArr.push(endDate);
					this.time=timeArr;
				}else if ('nearlyThirtyDays' == time) {
					timeArr=[];
					startDate=moment().locale('zh-cn').subtract(30, 'days').format('YYYY-MM-DD 00:00:00');
					endDate=moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 23:59:59');
					timeArr.push(startDate);
					timeArr.push(endDate);
					this.time=timeArr;
				}
				this.$http({
					url: this.$http.adornUrl('/user/user/UserFisterCharge'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
            'superiorsName': this.dataForm.superiorsName,
            'hierarchyName': this.dataForm.hierarchyName,
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
			exportCSV() {
				this.downLoadMix("会员首充.csv");
			},
			downLoadMix(title) {
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
					url: this.$http.adornUrl('/user/user/exportUserFisterChargeCSVData'),
					method: 'get',
					responseType: 'arraybuffer',
					params: this.$http.adornParams({
						'userAccount': this.dataForm.userAccount,
						'startTime': startDate,
						'endTime': endDate,
					})
				}).then(({
					data
				}) => {
					let blob = new Blob([data], {
						type: 'application/csv;charset=UTF-8'
					});
					let link = document.createElement('a');
					link.href = window.URL.createObjectURL(blob);
					link.download = title;
					link.click();
					URL.revokeObjectURL(blob);
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
						url: this.$http.adornUrl('/adchannelconfig/adchannelconfig/delete'),
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

