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
			<el-form-item>
				<el-button type="primary" @click="getDataListQuery()">查询</el-button>
			</el-form-item>
		</el-form>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> 平台总输赢：玩家总投入-玩家总产出</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="id" header-align="center" align="center" label="序号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" sortable label="游戏时间">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" sortable label="游戏">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" sortable label="游戏场">
			</el-table-column>
			<el-table-column prop="participateNum" header-align="center" align="center" sortable label="参与人数">
			</el-table-column>
			<el-table-column prop="investmentTotal" header-align="center" align="center" sortable label="玩家总投入">
				<template slot-scope="scope">
					<span v-if="scope.row.investmentTotal != null">{{scope.row.investmentTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotal" header-align="center" align="center" sortable label="玩家总产出">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null">{{scope.row.outputTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="winTotal" header-align="center" align="center" sortable label="平台总输赢">
				<template slot-scope="scope">
					<span v-if="scope.row.winTotal != null">{{scope.row.winTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column
				fixed="right"
				header-align="center"
				align="center"
				width="150"
				label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="pageThreeDateMethod(scope.row.countDay,scope.row.gameId,scope.row.gradeId,scope.row.roomId)">查看详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		
		<!-- 弹窗,查看详情 -->
		<gameuserdata v-if="gameuserdataVisible"  ref="gameuserdata" ></gameuserdata>
	</div>
</template>

<script>
	import moment from 'moment';
	import Gameuserdata from './gameuserdata';
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
					gameId:null,
					gradeId:null,
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
				gameuserdataVisible: false,
				showRoundVisible: false,
				
			}
		},
		components: {
			Gameuserdata
		},
		activated() {
			if(this.$route.params.countDay!=null){
				this.dataForm.countDay=this.$route.params.countDay;
			}
			if(this.$route.params.gameId!=null){
				this.dataForm.gameId=this.$route.params.gameId;
			}
			this.getDataList();

		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			getDataList(time) {
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
				// 	queryTime=moment(this.dataForm.queryTime).format("YYYY-MM-DD")
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
					url: this.$http.adornUrl('/reportgamegradedaily/reportgamegradedaily/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'sort': 'game_id,grade_id',
						'direction': true,
						'startTime': startDate,
						'endTime': endDate,
						'gameId':this.dataForm.gameId,
						'gradeId':this.dataForm.gradeId
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
			 //时间格式化
			dateFormat:function(row, column) {
			   var date = row[column.property];
	           if (date == undefined) {
	             return "";
	           }
	           return moment(date).format("YYYY-MM-DD");
			},
			// 查看详情
			pageThreeDateMethod (countDay,gameId,gradeId,roomId) {
				this.gameuserdataVisible = true
				this.$nextTick(() => {
					this.$refs.gameuserdata.init(moment(countDay).format("YYYY-MM-DD"),gameId,gradeId,roomId)
				})
				// this.$router.push({path:'/platformdatareporting-gameuserdata',query:{countDay: moment(countDay).format("YYYY-MM-DD") , gameId:gameId, gradeId:gradeId, roomId:roomId}})
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
