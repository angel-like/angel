<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :clearable=false :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userId" placeholder="会员id" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.gameRoundNo" placeholder="游戏局号" clearable style="width:250px;"></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.gameId" clearable placeholder="请选择游戏">
					<el-option v-for="item in gameList" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.gradeId" clearable placeholder="请选择游戏场次">
					<el-option v-for="item in gradeList" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="clearButton()">清空</el-button>
				<el-button @click="getDataList('today')">今天</el-button>
				<el-button @click="getDataList('yesterday')">昨天</el-button>
				<el-button @click="getDataList('week')">近7天</el-button>
				<el-button @click="exportCSV()">下载Excel</el-button>
				<!-- <el-button v-if="isAuth('gamerecord:gamerecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
			</el-form-item>
			<el-form-item>
				<div style="margin-bottom: 20px;font-size: 16px; color: red;">
					总有效投注：<span>{{totalValidBet/100}}</span>
					盈亏小计：<span>{{subTotalPrizeCoins/100}}</span>
					盈亏总计：<span>{{totalPrizeCoins/100}}</span> </div>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="userId" header-align="center" align="center" label="会员ID">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="会员账号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="游戏时间">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名称">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" label="场次名称">
			</el-table-column>
			<el-table-column prop="roomName" header-align="center" align="center" label="房间名称">
			</el-table-column>
			<el-table-column prop="gameRoundNo" header-align="center" align="center" label="游戏局号">
			</el-table-column>
			<el-table-column prop="round" header-align="center" align="center" label="游戏局数">
			</el-table-column>
			<!-- <el-table-column prop="money" header-align="center" align="center" label="会员余额">
			</el-table-column>
			<el-table-column prop="coin" header-align="center" align="center" label="金币余额">
			</el-table-column> -->
			<!-- 	<el-table-column prop="betCoins" header-align="center" align="center" label="下注总金币">
			</el-table-column> -->
			<el-table-column prop="validBet" header-align="center" align="center" label="有效投注金币">
				<template slot-scope="scope">
					<div>
						{{scope.row.validBet/100}}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="prizeCoins" header-align="center" align="center" label="中奖金币">
				<template slot-scope="scope">
					<div>
						{{scope.row.prizeCoins/100}}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="mini" header-align="center" align="center" label="是否小游戏">
				<template slot-scope="scope">
					<div v-if="scope.row.mini">
						是
					</div>
					<div v-else>
						否
					</div>
				</template>
			</el-table-column>
			<!-- <el-table-column prop="profitCoins" header-align="center" align="center" label="代理商盈利金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.profitCoins/100}}
						</div>
				</template>
			</el-table-column> -->
			<el-table-column prop="waterProfit" header-align="center" align="center" label="抽水金币">
				<template slot-scope="scope">
					<div>
						{{scope.row.waterProfit/100}}
					</div>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button v-if="scope.row.gameId != 510" type="text" size="small" @click="showRoundHandle(scope.row.gameRoundNo,scope.row.round,scope.row.gameId,scope.row.roomId)">详情</el-button>
				</template>
			</el-table-column>
			<!-- <el-table-column prop="exchangeRate" header-align="center" align="center" label="房卡转金币的汇率">
			</el-table-column> -->

		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>

		<!-- 弹窗, 新增 / 修改 -->
		<showRound v-if="showRoundVisible" ref="showRound"></showRound>
	</div>
</template>

<script>
	import moment from 'moment';
	import ShowRound from './usergamerecord-show';
	import dateutil from '@/utils/datechonse'
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
          gameRoundNo: '',
					gameId: '',
					gradeId: '',
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString())
						.getTime() + 24 * 60 * 60 * 1000 - 1)]
				},
				totalPrizeCoins: 0,
				subTotalPrizeCoins: 0,
				totalValidBet: 0,
				gameList: [],
				gradeList: [],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				showRoundVisible: false,

			}
		},
		components: {
			ShowRound
		},
		activated() {
			this.getSelectList()
			this.getDataList()
		},
		//绑定回车事件
		created() {
			this.keyupSubmit()
		},
		methods: {

			// 获取下拉数据源
			getSelectList() {
				this.dataForm.userAccount = this.$route.query.account
				this.$http({
					url: this.$http.adornUrl('/user/gemerecord/selectList'),
					method: 'get',
					params: this.$http.adornParams({})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gameList = data.gameList
						this.gradeList = data.gradeList
					} else {
						this.gameList = []
						this.gradeList = []
					}
				})
			},
			getDataList(time) {
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if ('today' == time) {
					timeArr = [];
					startTime = moment().locale('zh-cn').format('YYYY-MM-DD 00:00:00');
					endTime = moment().locale('zh-cn').format('YYYY-MM-DD HH:mm:ss');
					timeArr.push(startTime);
					timeArr.push(endTime);
					this.dataForm.queryTime = timeArr;
				} else if ('yesterday' == time) {
					timeArr = [];
					startTime = moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 00:00:00');
					endTime = moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 23:59:59');
					timeArr.push(startTime);
					timeArr.push(endTime);
					this.dataForm.queryTime = timeArr;
				} else if ('week' == time) {
					timeArr = [];
					startTime = moment().locale('zh-cn').subtract(7, 'days').format('YYYY-MM-DD 00:00:00');
					endTime = moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 23:59:59');
					timeArr.push(startTime);
					timeArr.push(endTime);
					this.dataForm.queryTime = timeArr;
				} else {
					if (timeArr != null && timeArr.length == 2) {
						startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
						if (timeArr.length > 1) {
							endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
						}
						if (!this.isAdmin()) {
							// if(this.dateMinus(startTime,endTime)>30){
							// 	this.$message.error("输入查询时间范围不能超出30天")
							// 	return ;
							// }
						}
					} else {
						this.$message.error("请输入查询时间范围")
						return;
					}
				}

				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/user/gemerecord/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'userId': this.dataForm.userId,
						'gradeId': this.dataForm.gradeId,
						'gameId': this.dataForm.gameId,
            'gameRoundNo': this.dataForm.gameRoundNo,
						'startTime': startTime,
						'endTime': endTime
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.subTotalPrizeCoins = data.subTotalPrizeCoins
						this.totalPrizeCoins = data.totalPrizeCoins
						if (data.totalValidBet) {
							this.totalValidBet = data.totalValidBet
						} else {
							this.totalValidBet = 0
						}
						/*this.totalValidBet = data.totalValidBet===null?0:data.totalValidBet*/
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
					} else {
						this.dataList = []
						this.totalPage = 0
						this.subTotalPrizeCoins = 0
						this.totalPrizeCoins = 0
					}
					this.dataListLoading = false
				})
			},
			exportCSV() {
				this.downLoadMix("会员注单.csv");
			},
			downLoadMix(title) {
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				this.$http({
					url: this.$http.adornUrl('/user/user/userGemeRecord'),
					method: 'get',
					responseType: 'arraybuffer',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'userId': this.dataForm.userId,
						'gradeId': this.dataForm.gradeId,
						'gameId': this.dataForm.gameId,
						'startTime': startTime,
						'endTime': endTime
					})
				}).then(({
					data
				}) => {
					let blob = new Blob([data],
					{
						type: 'text/csv,charset=UTF-8、'
					});
					let link = document.createElement('a');
					link.href = window.URL.createObjectURL(blob);
					link.download = title;
					link.click();
					URL.revokeObjectURL(blob);
				})
			},
			//绑定回车键
			keyupSubmit() {
				document.onkeydown = e => {
					let _key = window.event.keyCode;
					if (_key === 13) {
						this.getDataListQuery()
					}
				}
			},
			clearButton() {
				this.dataForm.queryTime = [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date()
					.toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1)];
				this.dataForm.userAccount = '';
				this.dataForm.gameId = '';
				this.dataForm.gradeId = '';
				this.dataForm.userId = '';
				this.getDataList();
			},
			//查询按钮事件
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
			showRoundHandle(gameRoundNo, round, gameId, roomId) {
				this.showRoundVisible = true
				this.$nextTick(() => {
					this.$refs.showRound.init(gameRoundNo, round, gameId, roomId)
				})
			},
			isAdmin() {
				return this.$store.state != null && this.$store.state.user.id == 1;
			},
			dateMinus(startDate, endDate) {
				var days = (new Date(endDate)) - (new Date(startDate)).getTime();
				var day = parseInt(days / (1000 * 60 * 60 * 24));
				return day;
			}

		}
	}
</script>
