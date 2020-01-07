<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()" style="margin-top: 10px;">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item label="游戏">
				<el-select v-model="dataForm.gameId" clearable placeholder="请选择游戏">
					<el-option v-for="item in gameOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="场次">
				<el-select v-model="dataForm.gradeId" clearable placeholder="请选择场次">
					<el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="玩家账号">
					<el-input v-model="dataForm.userAccount" placeholder="玩家账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="getDataListQuery()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="id" header-align="center" align="center" label="序号">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" sortable label="会员账号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" sortable label="时间">
			</el-table-column>
			<el-table-column prop="gameRoundNo" header-align="center" align="center" sortable label="游戏局号">
			</el-table-column>
			<el-table-column v-show="" prop="round" header-align="center" align="center" sortable label="游戏局数">
			</el-table-column>
			<el-table-column prop="betCoins" header-align="center" align="center" sortable label="当局投入金额">
				<template slot-scope="scope">
					<span v-if="scope.row.prizeCoins < 0">
					{{-(scope.row.prizeCoins/100)}}
					</span>
					<span v-else>
					   0
					</span>
				</template>
			</el-table-column>
			<el-table-column prop="produceCoins" header-align="center" align="center" sortable label="当局产出金额">
				<template slot-scope="scope">
					<span v-if="scope.row.prizeCoins > 0">
					{{scope.row.prizeCoins/100}}
					</span>
					<span v-else>
						0
					</span>
				</template>
			</el-table-column>
			<!-- <el-table-column prop="roomName" header-align="center" align="center" label="押注区域">
			</el-table-column>
			<el-table-column prop="validBet" header-align="center" align="center" label="开奖区域">
			</el-table-column> -->
			<el-table-column prop="prizeCoins" header-align="center" align="center" sortable label="中奖金额">
				<template slot-scope="scope">
					<span v-if="scope.row.prizeCoins != null">
					{{scope.row.prizeCoins/100}}
					</span>
				</template>
			</el-table-column>
			<el-table-column prop="userIdPrizeCoins" header-align="center" align="center" label="同桌玩家ID及输赢">
			</el-table-column>
			<el-table-column
				fixed="right"
				header-align="center"
				align="center"
				width="150"
				label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="showRoundHandle(scope.row.gameRoundNo,scope.row.round,scope.row.gameId,scope.row.roomId)">查看详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<showRound v-if="showRoundVisible" ref="showRound" ></showRound>
	</div>
</template>

<script>
	import moment from 'moment';
	import ShowRound from './userfielddetails-show';
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
					userId:null,
					countDay:'',
					gameId:null,
					gradeId:null,
					roomId:null,
					userAccount:'',
					queryTime:[],
				},
				options:[{
					id: 1,
					name: '荣耀厅'
				}, {
					id: 2,
					name: '王牌厅'
				},
				{
					id: 3,
					name: '战神厅'
				}],
				gameOptions:[],
				totalPrizeCoins:0,
				subTotalPrizeCoins:0,
				gameList:[],
				gradeList:[],
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
		created(){
		 this.keyupSubmit()
		},
		activated() {
			if(this.$route.params.countDay!=null){
				this.dataForm.countDay=this.$route.params.countDay;
			}
			if(this.$route.params.gameId!=null){
				this.dataForm.gameId=this.$route.params.gameId;
			}
			if(this.$route.params.gradeId!=null){
				this.dataForm.gradeId=this.$route.params.gradeId;
			}
			if(this.$route.params.roomId!=null){
				this.dataForm.roomId=this.$route.params.roomId;
			}
			if(this.$route.params.userAccount!=null){
				this.dataForm.userAccount=this.$route.params.userAccount;
			}
			this.getDataList();
		},
		methods: {
			getDataList(time) {
				// var countTime=''; 
				var startDate='';
				var endDate='';
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				// if(this.dataForm.queryTime!=null && this.dataForm.queryTime!=''){
				// 	countTime=moment(this.dataForm.queryTime[0]).format("YYYY-MM-DD HH:mm:ss")
				// }
				
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.gameOptions = data.data
					}
				});
				this.$http({
					url: this.$http.adornUrl('/reportplayergamedaily/reportplayergamedaily/sceneList'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userId':this.dataForm.userId,
						'gameId':this.dataForm.gameId,
						'gradeId':this.dataForm.gradeId,
						'roomId':this.dataForm.roomId,
						// 'countDay':countTime,
						'startTime': startDate,
						'endTime': endDate,
						'userAccount':this.dataForm.userAccount
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						
					} else {
						this.dataList = [],
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			// 查看详情
 			showRoundHandle (gameRoundNo,round,gameId,roomId) {
				this.showRoundVisible = true
				this.$nextTick(() => {
					this.$refs.showRound.init(gameRoundNo,round,gameId,roomId)
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
			},
			dateMinus(startDate,endDate){ 
			　　var days = endDate.getTime() - startDate.getTime(); 
			　　var day = parseInt(days / (1000 * 60 * 60 * 24)); 
			　　return day; 
			}
			
		}
	}
</script>
