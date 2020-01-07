<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.channelName" placeholder="渠道名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2"  :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="index" width="120" header-align="center" align="center" label="序号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="创建时间">
			</el-table-column>
			<el-table-column prop="channelName" header-align="center" align="center" label="渠道名称">
			</el-table-column>
			<el-table-column prop="channelCode" header-align="center" align="center" label="渠道码">
			</el-table-column>
			<el-table-column prop="userNum" header-align="center" align="center" label="会员数量">
				<template slot-scope="scope">
					<div>{{scope.row.userNum}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="validBet" header-align="center" align="center" label="打码量">
					<template slot-scope="scope">
						<div else>
            <a @click="readUserValidBet(scope.row.channelCode,scope.row.startTime,scope.row.endTime)" title="查看详情" style="cursor:pointer ">{{scope.row.validBet/100}}</a>
            </div>
					</template>
			</el-table-column>
			<el-table-column prop="amount" header-align="center" align="center" label="充值总额">
				<template slot-scope="scope">
					<div else>
						<a @click="readAmount(scope.row.channelCode,scope.row.startTime,scope.row.endTime)" title="查看详情" style="cursor:pointer ">{{scope.row.amount}}</a>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="takeAmount" header-align="center" align="center" label="取款总额">
			</el-table-column>
			<!--通过  渠道名称  去 查询渠道地址-->
			<!-- <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="readAdChannelConfig(scope.row.channelName)">详情</el-button>
				</template>
			</el-table-column> -->
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
		<!-- <add-or-update > </add-or-update> -->
		<ad-channel-config v-if="readAdChannelConfigVisbel" ref="adChannelConfig" @refreshDataList="getDataList"></ad-channel-config>
		<valid-bet v-if="validBetVisible" ref="validBet" @refreshDataList="getDataList"></valid-bet>
    <amount v-if="amountVisible" ref="amount" @refreshDataList="getDataList"></amount>
	</div>
</template>
<script>
	//import AddOrUpdate from './adchannelconfig-add-or-update'
	import adChannelConfig from './read_adchannelconfig'
	import validBet from './read_usernum'
  import amount from './read_amount'
	import dateutil from '@/utils/datechonse'
	import moment from 'moment'
	export default {
		data() {
			return {
				dataForm: {
					channelName: '',
					queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime],
				},
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
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				readAdChannelConfigVisbel: false,
				validBetVisible:false,
        amountVisible:false,
			}
		},
		components: {
			//AddOrUpdate
			adChannelConfig,
			validBet,
      amount
		},
		activated() {
			this.getDataList()
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
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
					url: this.$http.adornUrl('/channelstatistics/channelstatistics/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'channelName': this.dataForm.channelName,
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
			//弹窗出渠道配置表
			readAdChannelConfig(channelName) {
				this.readAdChannelConfigVisbel = true
				this.$nextTick(() => {
					this.$refs.adChannelConfig.init(channelName)
				})
			},
			//通过渠道代码读取会员数量
			readUserValidBet(channelCode,startTime,endTime){
				this.validBetVisible = true
				this.$nextTick(() => {
					this.$refs.validBet.init(channelCode,startTime,endTime)
				})
			},
      //通过渠道代码读取会员充值金额
      readAmount(channelCode,startTime,endTime){
      	this.amountVisible = true
      	this.$nextTick(() => {
      		this.$refs.amount.init(channelCode,startTime,endTime)
      	})
      },
				//绑定回车事件
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询
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
			}
		}
	}
</script>
