<template>
	<el-dialog
		:title="'详情'"
		:close-on-click-modal="false"
		:visible.sync="visible">
	<div class="mod-config">
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="id" header-align="center" align="center" label="序号">
			</el-table-column>
			<el-table-column prop="countDay" :formatter="dateFormat" header-align="center" align="center" sortable label="日期">
			</el-table-column>
			<el-table-column prop="gameName" header-align="center" align="center" label="模块名称">
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
			<el-table-column prop="odds" header-align="center" align="center" sortable label="赔率">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null && scope.row.investmentTotal != null && scope.row.investmentTotal>0">
					{{scope.row.outputTotal/scope.row.investmentTotal| rounding}}
					</span>
					<span v-else>0</span>
				</template>
			</el-table-column>
			<el-table-column prop="winTotal" header-align="center" align="center" sortable label="平台总输赢">
				<template slot-scope="scope">
					<span v-if="scope.row.winTotal != null">{{scope.row.winTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotal" header-align="center" align="center" sortable label="输赢占比">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null && scope.row.investmentTotal != null  && scope.row.outputTotal>0">
					{{scope.row.investmentTotal/scope.row.outputTotal| percentage}}%
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
					<el-button type="text" size="small" @click="pageTwoDateMethod(scope.row.countDay,scope.row.gameId)">查看详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> 输赢占比：玩家总投入/玩家总产出</div>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> 赔率：玩家总产出/玩家总投入*100</div>
		<div style="margin-top: 10px;font-size: 14px; color: red;"> 玩家总输赢：玩家总投入-玩家总产出</div>
	</div>
	</el-dialog>
</template>

<script>
	import moment from 'moment';
	export default {
		data() {
			return {
				dataForm: {
					countDay:''
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
		
// 		activated() {
// 			if(this.$route.query.countDay!=null){
// 				this.dataForm.countDay=this.$route.query.countDay;
// 			}
// 			this.getDataList();
// 		},
		methods: {
			getDataList(time) {
				this.$http({
					url: this.$http.adornUrl('/reportgamedaily/reportgamedaily/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'sort': 'count_day,game_id',
						'direction': false,
						'countDay':this.dataForm.countDay
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
			init(countDay){
				this.visible = true
				if(countDay!=null){
					this.dataForm.countDay=countDay;
				}
				this.getDataList();
			},
			// 查看详情
			pageTwoDateMethod (countDay,gameId) {
				this.visible = false
				this.$router.push({name:'platformdatareporting-playgrounddetails',params:{countDay: moment(countDay).format("YYYY-MM-DD") , gameId:gameId}})
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
