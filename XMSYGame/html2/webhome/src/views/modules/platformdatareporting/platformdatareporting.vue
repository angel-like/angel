<template>
	<div class="mod-configg">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="daterange" range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :clearable=false :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="getDataList()">刷新</el-button>
			</el-form-item>
		</el-form>
		<el-table  
		  :data="dataList"
		  border
		  v-loading="dataListLoading"
		  @selection-change="selectionChangeHandle"
          style="width: 100%;">
			<el-table-column prop="id" header-align="center" align="center" sortable label="序号" width="80">
			</el-table-column>
			<el-table-column prop="countDay" :formatter="dateFormat" header-align="center" align="center" sortable label="日期">
			</el-table-column>
			<el-table-column prop="rechargeTotal" header-align="center" align="center" sortable label="当前充值总额">
			</el-table-column>
			<el-table-column prop="rechargeNum" header-align="center" align="center" sortable label="当前充值人数">
			</el-table-column>
			<el-table-column prop="registereNum" header-align="center" align="center" sortable label="当前注册人数">
			</el-table-column>
			<el-table-column prop="activeNum" header-align="center" align="center" sortable label="当前活跃人数">
			</el-table-column>
			<el-table-column prop="winNum" header-align="center" align="center" sortable label="当前赢钱人数">
			</el-table-column>
			<el-table-column prop="loseNum" header-align="center" align="center" sortable label="当前输钱人数">
			</el-table-column>
			<el-table-column prop="investmentTotal" header-align="center" align="center" sortable label="当前总投入">
				<template slot-scope="scope">
					<span v-if="scope.row.investmentTotal != null">{{scope.row.investmentTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotal" header-align="center" align="center" sortable label="当前总产出">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null">{{scope.row.outputTotal/100}}</span>
				</template>
			</el-table-column> 
			<el-table-column prop="winTotal" header-align="center" align="center" sortable label="当前总输赢">
				<template slot-scope="scope">
					<span v-if="scope.row.winTotal != null">{{scope.row.winTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="ARPPU" header-align="center" align="center" sortable label="ARPPU值">
				<template slot-scope="scope">
					<span v-if="scope.row.rechargeTotal != null && scope.row.rechargeNum != null && scope.row.rechargeNum != 0">
					{{scope.row.rechargeTotal/scope.row.rechargeNum}}</span>
					<span v-else>0</span>
				</template>
			</el-table-column>
			<el-table-column prop="ARPU" header-align="center" align="center" sortable label="ARPU值">
				<template slot-scope="scope">
					<span v-if="scope.row.rechargeTotal != null && scope.row.activeNum != null && scope.row.activeNum != 0">
					{{scope.row.rechargeTotal/scope.row.activeNum}}
					</span>
					<span v-else>0</span>
				</template>
			</el-table-column>
			<el-table-column
			  fixed="right"
			  header-align="center"
			  align="center"
			  width="150"
			  label="操作">
			  <template slot-scope="scope">
				<el-button type="text" size="small" @click="pageOneDateMethod(scope.row.countDay)">查看详情</el-button>
			  </template>
			</el-table-column>
		</el-table>
		<el-pagination
		  @size-change="sizeChangeHandle"
		  @current-change="currentChangeHandle"
		  :current-page="pageIndex"
		  :page-sizes="[10, 20, 50, 100]"
		  :page-size="pageSize"
		  :total="totalPage"
		  layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> ARPU值：当前总充值/当前登陆用户数量</div>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> ARPPU值：当前总充值/当前充值用户数量</div>
		<!-- 弹窗,查看详情 -->
		<modularrevenuedata v-if="modularrevenuedataVisible"  ref="modularrevenuedata" ></modularrevenuedata>
	</div>
</template>

<script>
	import moment from 'moment';
	import Modularrevenuedata from './modularrevenuedata';
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
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date()]
				},
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
				modularrevenuedataVisible:false
			}
		},
		components: {
			Modularrevenuedata
		},
		activated() {
			this.getDataList();
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			getDataList(time) {
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
					url: this.$http.adornUrl('/reportdatadaily/reportdatadaily/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'sort': 'count_day',
						'direction': false,
						'startTime': startTime,
						'endTime': endTime
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list,
						this.totalPage = data.page.totalCount
					} else {
						this.dataList = [],
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			 //时间格式化
            dateFormat:function(row, column) {
               var date = row[column.property];
	           if (date == undefined) {
	             return "";
	           }
	           return moment(date).format("YYYY-MM-DD");
            },
			// 查看详情
			pageOneDateMethod (countDay) {
				// this.$router.push({path:'/platformdatareporting-modularrevenuedata',query:{countDay: moment(countDay).format("YYYY-MM-DD")}}),
				
				this.modularrevenuedataVisible = true
				this.$nextTick(() => {
					this.$refs.modularrevenuedata.init(moment(countDay).format("YYYY-MM-DD"))
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


<style>
	.el-date-editor .el-range-separator {
    padding: 0 0px;
}
</style>
