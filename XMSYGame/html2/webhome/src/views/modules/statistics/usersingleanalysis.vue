<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.account" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-date-picker
					v-model="dataForm.time"
					type="datetimerange"
					range-separator="至"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					:picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="clearButton()">清空</el-button>
			</el-form-item>
		</el-form>
		<div style="font-size: 18px;margin-bottom: 10px;">会员信息汇总</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
					<el-table-column prop="userType" header-align="center" align="center" label="会员类型">
						<template slot-scope="scope">
							<div v-if="scope.row.userType=='TRIAL'">试玩账号</div>
							<div v-else-if="scope.row.userType=='USER'">普通会员</div>
							<div v-else-if="scope.row.userType=='VIP'">VIP</div>
						</template>
					</el-table-column>
					<el-table-column prop="createTime" header-align="center" min-width="140" align="center" label="创建时间">
					</el-table-column>
					<el-table-column prop="hierarchyName" header-align="center" align="center" label="层级名称">
					</el-table-column>
					<el-table-column prop="userName" header-align="center" align="center" label="真实姓名">
					</el-table-column>
					<el-table-column prop="enable" header-align="center" align="center" label="账号状态">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.forbiddenEnable=='0' && scope.row.nobetEnable=='0' && scope.row.frozenEnable=='0' && scope.row.abnormalEnable=='0' ">正常</el-tag>
							<el-tag v-if="scope.row.forbiddenEnable=='1'" type="danger">已拉黑</el-tag>
							<el-tag v-if="scope.row.nobetEnable=='1'" type="danger">已停押</el-tag>
							<el-tag v-if="scope.row.frozenEnable=='1'" type="danger">已冻结</el-tag>
							<el-tag v-if="scope.row.abnormalEnable=='1'" type="danger">风控</el-tag>
						</template>
					</el-table-column>
					<el-table-column prop="remark" header-align="center" align="center" min-width="128px" label="登录状态">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.online" type="success">({{scope.row.deviceType}})游戏中</el-tag>
							<el-tag v-else type="info">离线</el-tag>
						</template>
					</el-table-column>
					<el-table-column prop="remark" header-align="center" align="left" min-width="200" label="详情">
						<template slot-scope="scope">
							<div>当前登录ip:{{scope.row.loginIp}}</div>
							<div>最后登录ip:{{scope.row.lastLoginIp}}</div>
							<div>当前登录时间:{{scope.row.loginTime}}</div>
							<div>最后登录时间:{{scope.row.lastLoginTime}}</div>
						</template>
					</el-table-column>
					<el-table-column prop="money" header-align="center" align="center" label="余额">
						<template slot-scope="scope">
							<div v-if="scope.row.money!=null">{{scope.row.money/100}}</div>
							<div v-else>{{scope.row.money}}</div>
						</template>
					</el-table-column>
					<el-table-column prop="userNeedBet" header-align="center" align="left" min-width="120" label="打码量">
						<template slot-scope="scope">
							<div>要求:{{scope.row.userNeedBet}}</div>
							<div>有效量:{{scope.row.userValidBet}}</div>
							<div v-if="scope.row.userValidBet-scope.row.userNeedBet>=0">(已满足)</div>
							<div v-else>(未满足)</div>
						</template>
					</el-table-column>
		</el-table>
		<div style="font-size: 18px;margin-bottom: 10px;margin-top: 20px;">个人报表</div>
		<div style="color: red;margin: 10px 0px;">
			<span>充值总额：{{this.dataForm.orderRechargeSum}}</span>
			<span>提款总额：{{this.dataForm.takeMoneySum}}</span>
			<span>投注总额：{{-this.dataForm.betCoins}}</span>
			<span>中奖总额：{{this.dataForm.prizeCoins}}</span>
			<span>佣金总额：{{this.dataForm.commissionSum}}</span>
			<span>佣金转入总额：{{this.dataForm.commissionEnterSum}}</span>
			<span>佣金转出总额：{{this.dataForm.commissionOutSum}}</span>
		</div>
		<el-table :data="dataReportList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
					<el-table-column prop="createTime" header-align="center" :formatter="dateFormat" align="center" label="报表时间">
					</el-table-column>
					<el-table-column prop="orderRechargeSum" header-align="center" align="center" label="充值">
					</el-table-column>
					<el-table-column prop="takeMoneySum" header-align="center" align="center" label="提款">
					</el-table-column>
					<el-table-column prop="betCoins" header-align="center" align="center" label="投注">
						<template slot-scope="scope">
						 <div>{{-scope.row.betCoins}}</div>
						</template>
					</el-table-column>
					<el-table-column prop="prizeCoins" header-align="center" align="center" label="中奖">
					</el-table-column>
					<!-- <el-table-column prop="remark" header-align="center" align="left" label="返点">
					</el-table-column> -->
					<el-table-column prop="commissionSum" header-align="center" align="center" label="佣金">
					</el-table-column>
					<el-table-column prop="commissionEnterSum" header-align="center" align="left" label="佣金转入">
					</el-table-column>
					<el-table-column prop="commissionOutSum" header-align="center" align="left" label="佣金转出">
					</el-table-column>
					<el-table-column prop="prizeCoins" header-align="center" align="left" label="投注盈亏">
						<template slot-scope="scope">
						 <div>{{scope.row.prizeCoins+scope.row.betCoins}}</div>
						</template>
					</el-table-column>
					<el-table-column prop="takeMoneySum" header-align="center" align="left" label="账变金额">
						<template slot-scope="scope">
							 <div>{{scope.row.orderRechargeSum-scope.row.takeMoneySum}}</div>
						</template>
					</el-table-column>
		</el-table>
		<div style="color: red;">投注盈亏 = 中奖金额 - 投注金额</div>
		<div style="color: red;">账变金额 = 充值金额 - 提款金额</div>
		<div style="font-size: 18px;margin-bottom: 10px;margin-top: 20px;">入款记录</div>
		<el-table :data="datadepositRecordList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
					<el-table-column prop="createTime" header-align="center" align="center" label="时间">
					</el-table-column>
					<el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
					</el-table-column>
					<el-table-column prop="id" header-align="center" align="center" label="交易Id">
					</el-table-column>
					<el-table-column prop="amount" header-align="center" align="center" label="入款金额">
					</el-table-column>
					<el-table-column prop="finalMoney" header-align="center" align="center" label="最终金额">
					</el-table-column>
					<el-table-column prop="rechargeType" header-align="center" align="left" label="类型">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.rechargeType==1" size="small" type="danger">后台人工充值</el-tag>
							<el-tag v-if="scope.row.rechargeType==2" size="small" type="info">第三方支付</el-tag>
							<el-tag v-if="scope.row.rechargeType==3" size="small" type="success">线下银行卡打款</el-tag>
						</template>
					</el-table-column>
					<el-table-column prop="status" header-align="center" align="center" label="状态">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
							<el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
							<el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
							<el-tag v-if="scope.row.status==4" size="small" type="danger">已锁定</el-tag>
						</template>
					</el-table-column>
		</el-table>
		<div style="font-size: 18px;margin-bottom: 10px;margin-top: 20px;">出款记录</div>
		<el-table :data="dataWithdrawalRecordList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
					<el-table-column prop="createTime" header-align="center" align="center" label="创建时间">
					</el-table-column>
					<el-table-column prop="updateTime" header-align="center" align="center" label="更新时间">
					</el-table-column>
					<el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
					</el-table-column>
					<el-table-column prop="id" header-align="center" align="center" label="交易Id">
					</el-table-column>
					<el-table-column prop="takeAmount" header-align="center" align="center" label="取款金额">
					</el-table-column>
					<el-table-column prop="poundage" header-align="center" align="center" label="取款手续费">
					</el-table-column>
					<el-table-column prop="poundage" header-align="center" align="left" label="扣除金额">
					</el-table-column>
					<el-table-column prop="status" header-align="center" align="center" label="状态">
						<template slot-scope="scope">
							<el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
							<el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
							<el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
							<el-tag v-if="scope.row.status==4" size="small" type="success">已锁定</el-tag>
						</template>
					</el-table-column>
		</el-table>
		<div style="font-size: 18px;margin-bottom: 10px;margin-top: 20px;">登录日志</div>
		<el-table :data="dataLogonLogList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
					<el-table-column prop="account" header-align="center" align="center" label="会员账号">
					</el-table-column>
					<el-table-column prop="loginStatus" header-align="center" align="center" label="登录状态">
						<template slot-scope="scope">
							<div v-if="scope.row.loginStatus=='success'">登录成功</div>
							<div v-else-if="scope.row.loginStatus=='fail'">登录失败</div>
						</template>
					</el-table-column>
					<!-- <el-table-column prop="token" header-align="center" align="center" label="设备token">
					</el-table-column> -->
					<el-table-column prop="lastLoginTime" header-align="center" min-width="140" align="center" label="登录时间">
					</el-table-column>
					<el-table-column prop="lastIp" header-align="center" align="center" label="ip">
					</el-table-column>
					<el-table-column prop="region" header-align="center" align="center" label="地区">
					</el-table-column>
					<el-table-column prop="nation" header-align="center" align="left"  label="国家">
					</el-table-column>
					<el-table-column prop="ipAddress" header-align="center" align="center" label="ip地理位置">
					</el-table-column>
					<!-- <el-table-column prop="domain" header-align="center" align="left" label="域名">
					</el-table-column> -->
					<el-table-column prop="deviceCode" header-align="center" align="left" label="来源设备">
					</el-table-column>
					<!-- <el-table-column prop="browser" header-align="center" align="left" label="软件版本">
					</el-table-column> -->
					<el-table-column prop="deviceType" header-align="center" align="left" label="浏览器类别">
					</el-table-column>
		</el-table>
		<!--<div style="font-size: 18px;margin-bottom: 10px;margin-top: 20px;">同ip登录人数</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
					<el-table-column prop="createTime" header-align="center" align="center" label="注册ip">
					</el-table-column>
					<el-table-column prop="hierarchyName" header-align="center" align="center" label="注册ip登录人数">
					</el-table-column>
					<el-table-column prop="userName" header-align="center" align="center" label="登录ip">
					</el-table-column>
					<el-table-column prop="enable" header-align="center" align="center" label="登录ip登录人数">
					</el-table-column>
					<el-table-column prop="remark" header-align="center" align="center" min-width="128px" label="最后登录ip">
					</el-table-column>
					<el-table-column prop="remark" header-align="center" align="left" min-width="250" label="最后登录ip人数">
					</el-table-column>
		</el-table> -->
		
		<!-- 弹窗, 新增 / 修改 -->
		<!-- <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update> -->
	</div>
</template>

<script>
	// import AddOrUpdate from './usersingleanalysis-add-or-update'
	import moment from 'moment';
	import dateutil from '@/utils/datechonse';
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
					}],
					onPick: ({ maxDate, minDate }) => {
						this.pickerMinDate = minDate.getTime()
						if (maxDate) {
						  this.pickerMinDate = ''
						}
					  },
					  disabledDate: (time) => {
						if (this.pickerMinDate !== '') {
						  const day30 = (7 - 1) * 24 * 3600 * 1000
						  let maxTime = this.pickerMinDate + day30
						  let minTime = this.pickerMinDate - day30
						  if (maxTime > new Date()) {
							maxTime = new Date()
						  }
						  return time.getTime() > maxTime ||  time.getTime() < minTime
						}
						return time.getTime() > Date.now()
					  }
				},
				dataForm: {
					account: '',
					time: [dateutil.getToday().starttime,dateutil.getToday().endtime],
					takeMoneySum : null,
					orderRechargeSum : null,
					commissionSum : null,
					commissionOutSum : null,
					commissionEnterSum : null,
					betCoins : 0,
					prizeCoins : 0,
				},
				typeOptions: [],
				detailTypeOptions: [],
				datadepositRecordList: [],
				fristrechargeOptions:[
					{
					"code":true,
					"name":"是"
					},
					{
					"code":false,
					"name":"否"
					}],
				dataList: [],
				dataWithdrawalRecordList: [],
				dataLogonLogList: [],
				dataReportList: [],
				reportParamAll: [],
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
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
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
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/user/usersingle/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'account': this.dataForm.account,
						'startTime':startTime,
						'endTime':endTime,
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.userInfo;
						this.dataWithdrawalRecordList = data.withdrawalRecord;
						this.datadepositRecordList = data.depositRecord;
						this.dataLogonLogList = data.logonLog;
						this.dataReportList = data.mapList;
						this.reportParamAll = data.reportParamAll;
						this.dataForm.takeMoneySum = data.reportParamAll.takeMoneySum;
						this.dataForm.orderRechargeSum = data.reportParamAll.orderRechargeSum;
						this.dataForm.commissionSum = data.reportParamAll.commissionSum;
						this.dataForm.commissionOutSum = data.reportParamAll.commissionOutSum;
						this.dataForm.commissionEnterSum = data.reportParamAll.commissionEnterSum;
						this.dataForm.betCoins = data.reportParamAll.betCoins;
						this.dataForm.prizeCoins = data.reportParamAll.prizeCoins;
						
					} else if(data && data.code === 100){
						this.$message.error(data.msg)
					}else{
						this.dataList = [];
						this.dataWithdrawalRecordList = [];
						this.datadepositRecordList = [];
						this.dataLogonLogList = [];
						this.dataReportList = [];
						this.reportParamAll = [];
					}
					this.dataListLoading = false
				})
			},
			clearButton(){
				this.dataForm.account= '';
				this.dataForm.time= [];
				this.getDataList();
			},
			//时间格式化
			dateFormat:function(row,column){        
			    var date = row[column.property];        
				if(date == undefined){
					return ''
				};        
				 return moment(date).format("YYYY-MM-DD")   
			},

			//搜索框类型加载数据
			selectList() {
				this.dataListLoading = true
				// 交易类型下拉
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + '003'),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.typeOptions = data.data
						if(this.dataForm.userAccount!=null&&this.dataForm.userAccount!=''){
							this.getDataList()
						}
					}
				})
				
				// 操作类型下拉
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + '004'),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.detailTypeOptions = data.data
					}
				})
				this.dataListLoading = false
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
						url: this.$http.adornUrl('/usertransactionrecord/usertransactionrecord/delete'),
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
