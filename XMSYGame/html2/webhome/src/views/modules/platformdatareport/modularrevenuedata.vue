<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="daterange" range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :clearable=false :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
			  <el-input v-model="dataForm.gameName" placeholder="游戏名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="getDataList()">刷新</el-button>
			</el-form-item>
		</el-form>
		<div style="color: red;padding-bottom: 10px;">
			<span>当前页汇总&nbsp;&nbsp;&nbsp;</span>    
			<span>总参与人数 ：{{participateNum}}</span>    
			<span>总投入：{{investmentTotal/100}}</span>   
			<span>总产出：{{outputTotal/100}}</span>   
			<span v-if="this.investmentTotal!=0&&this.outputTotal!=0">赔率：{{outputTotal/investmentTotal| rounding}}</span>
			<span v-if="this.investmentTotal==0||this.outputTotal==0">赔率：0</span>   
			<span>总输赢：{{winTotal/100}}</span>  
			<span v-if="this.investmentTotal!=0&&this.outputTotal!=0">输赢占比：{{investmentTotal/outputTotal| percentage}}%</span>
			<span v-if="this.investmentTotal==0||this.outputTotal==0">输赢占比：0%</span>
		</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="index" width="120" header-align="center" align="center" label="序号">
			</el-table-column>
			<el-table-column prop="countDay" :formatter="dateFormat" header-align="center" align="center" label="日期">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="模块名称">
			</el-table-column>
			<el-table-column prop="participateNum" header-align="center" align="center" label="参与人数">
			</el-table-column>
			<el-table-column prop="investmentTotal" header-align="center" align="center" label="总投入">
				<template slot-scope="scope">
					<span v-if="scope.row.investmentTotal != null">{{scope.row.investmentTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotal" header-align="center" align="center" label="总产出">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null">{{scope.row.outputTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="odds" header-align="center" align="center" label="赔率">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null && scope.row.investmentTotal != null && scope.row.investmentTotal>0">
					{{scope.row.outputTotal/scope.row.investmentTotal| rounding}}
					</span>
					<span v-else>0</span>
				</template>
			</el-table-column>
			<el-table-column prop="winTotal" header-align="center" align="center" label="总输赢">
				<template slot-scope="scope">
					<span v-if="scope.row.winTotal != null">{{scope.row.winTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotal" header-align="center" align="center" label="输赢占比">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null && scope.row.investmentTotal != null  && scope.row.outputTotal>0">
					{{scope.row.investmentTotal/scope.row.outputTotal| percentage}}%
					</span>
					<span v-else>0</span>
				</template>
			</el-table-column>
			<!-- <el-table-column
				fixed="right"
				header-align="center"
				align="center"
				width="150"
				label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="pageTwoDateMethod(scope.row.countDay,scope.row.gameId)">查看详情</el-button>
				</template>
			</el-table-column> -->
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- <div style="margin-top: 10px;font-size: 14px; color: red;"> 输赢占比：玩家总投入/玩家总产出</div>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> 赔率：玩家总产出/玩家总投入*100</div>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> 玩家总输赢：玩家总投入-玩家总产出</div> -->
	</div>
</template>

<script>
	import moment from 'moment';
	import dateutil from '@/utils/datechonse'
	export default {
		data() {
			return {
				pickerOptions2: {
					shortcuts: [{
					  text: '今天',
					  onClick(picker) {
						const end = new Date();
						const start = new Date();
					    picker.$emit('pick', [start, end]);
					  }
					}, {
					  text: '昨天',
					  onClick(picker) {
						const end = new Date();
						const start = new Date();
						end.setTime(end.getTime() - 3600 * 1000 * 24);
					    start.setTime(start.getTime() - 3600 * 1000 * 24);
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
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date()],
					gameName:'',
					
				},
				participateNum:'',
				investmentTotal:'',
				outputTotal:'',
				winTotal:'',
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
				visible: false,
				showRoundVisible: false,
				
			}
		},
		components: {
		},
		filters: {
		 rounding (value) {
		 return value.toFixed(2)
		 },
		 percentage(value){
			return (value*100).toFixed(2)
		 }
		},
		activated() {
			this.getDataList();
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			getDataList() {
				this.dataListLoading = true
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD");
					}
				}
				this.$http({
					url: this.$http.adornUrl('/reportgamedaily/reportgamedaily/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'sort': 'count_day,game_id',
						'direction': false,
						'startTime': startTime,
						'endTime': endTime,
						'gameName': this.dataForm.gameName,
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						this.winTotal = data.winTotal
						this.participateNum = data.participateNum
						this.investmentTotal = data.investmentTotal
						this.outputTotal = data.outputTotal
						
					} else {
						this.dataList = [],
						this.totalPage = 0
					}
					this.dataListLoading = false
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
			 //时间格式化
			dateFormat:function(row, column) {
			   var date = row[column.property];
	           if (date == undefined) {
	             return "";
	           }
	           return moment(date).format("YYYY-MM-DD");
			},
			 // 每页数
			sizeChangeHandle (val) {
			  this.pageSize = val
			  this.pageIndex = 1
			  this.getDataList()
			},
			// 当前页
			currentChangeHandle (val) {
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
