<template>
	<el-dialog
		:title="'详情'"
		:close-on-click-modal="false"
		:visible.sync="visible">
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
			  <el-input v-model="dataForm.userId" placeholder="玩家Id" clearable></el-input>
			</el-form-item>
			<el-form-item>
			  <el-input v-model="dataForm.userAccount" placeholder="玩家账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="id" header-align="center" align="center" label="序号">
			</el-table-column>
			<el-table-column prop="createTime" :formatter="dateFormat" header-align="center" align="center" sortable label="日期">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="玩家账号">
			</el-table-column>
			<el-table-column prop="userId" header-align="center" align="center" label="账户ID">
			</el-table-column>
			<el-table-column prop="investmentTotal" header-align="center" align="center" sortable label="今日总投入">
				<template slot-scope="scope">
					<span v-if="scope.row.investmentTotal != null">{{scope.row.investmentTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotal" header-align="center" align="center" sortable label="今日总产出">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotal != null">{{scope.row.outputTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="winTotal" header-align="center" align="center" sortable label="今日总输赢">
				<template slot-scope="scope">
					<span v-if="scope.row.winTotal != null">{{scope.row.winTotal/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="investmentTotalSum" header-align="center" align="center" sortable label="游戏总投入">
				<template slot-scope="scope">
					<span v-if="scope.row.investmentTotalSum != null">{{scope.row.investmentTotalSum/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="outputTotalSum" header-align="center" align="center" sortable label="游戏总产出">
				<template slot-scope="scope">
					<span v-if="scope.row.outputTotalSum != null">{{scope.row.outputTotalSum/100}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="winTotalSum" header-align="center" align="center" sortable label="总输赢">
				<template slot-scope="scope">
					<span v-if="scope.row.winTotalSum != null">{{scope.row.winTotalSum/100}}</span>
				</template>
			</el-table-column>
			<el-table-column
				header-align="center"
				align="center"
				width="150"
				label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="pageFourDateMethod(scope.row.countDay,scope.row.userAccount,scope.row.gameId,scope.row.gradeId,scope.row.roomId)">查看详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
	</div>
	</el-dialog>
</template>

<script>
	import moment from 'moment';
	export default {
		
		data() {
			return {
				dataForm: {
					userAccount:'',
					userId:'',
					countDay:'',
					gameId:null,
					gradeId:null,
					roomId:null
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
		created(){
		 this.keyupSubmit()
		},
// 		activated() {
// 			if(this.$route.query.countDay!=null){
// 				this.dataForm.countDay=this.$route.query.countDay;
// 			}
// 			if(this.$route.query.gameId!=null){
// 				this.dataForm.gameId=this.$route.query.gameId;
// 			}
// 			if(this.$route.query.gradeId!=null){
// 				this.dataForm.gradeId=this.$route.query.gradeId;
// 			}
// 			if(this.$route.query.roomId!=null){
// 				this.dataForm.roomId=this.$route.query.roomId;
// 			}
// 			this.getDataList();
// 		},
		methods: {
			getDataList(time) {
				this.$http({
					url: this.$http.adornUrl('/reportplayergamedaily/reportplayergamedaily/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'countDay':this.dataForm.countDay,
						'gameId':this.dataForm.gameId,
						'gradeId':this.dataForm.gradeId,
						'roomId':this.dataForm.roomId,
						'userAccount': this.dataForm.userAccount,
						'userId': this.dataForm.userId
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
			init(countDay,gameId,gradeId,roomId){
				this.visible = true
				if(countDay!=null){
					this.dataForm.countDay=countDay;
				}
				if(gameId!=null){
					this.dataForm.gameId=gameId;
				}
				if(gradeId!=null){
					this.dataForm.gradeId=gradeId;
				}
				if(roomId!=null){
					this.dataForm.roomId=roomId;
				}
				this.getDataList();
			},
			// 查看详情
			pageFourDateMethod (countDay,userAccount,gameId,gradeId,roomId) {
				this.visible = false
				this.$router.push({
					name:'platformdatareporting-userfielddetails',
					params:{
						countDay: moment(countDay).format("YYYY-MM-DD"),
						userAccount: userAccount , 
						gameId:gameId, 
						gradeId:gradeId, 
						roomId:roomId,
						},
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
