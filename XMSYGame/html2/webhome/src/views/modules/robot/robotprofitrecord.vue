<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item label="机器人">
				<el-input v-model="dataForm.userAccount" placeholder="机器人" clearable></el-input>
			</el-form-item>
			<el-form-item label="游戏">
				<el-select v-model="dataForm.gameId" placeholder="请选择游戏">
					<el-option v-for="item in gameOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="场次">
				<el-select v-model="dataForm.gradeId" placeholder="请选择场次" clearable>
					<el-option v-for="item in gradeOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="统计日期">
				<el-date-picker v-model="dataForm.timeArr" type="daterange" range-separator="至" start-placeholder="开始日期"
				 end-placeholder="结束日期">
				</el-date-picker>
			</el-form-item>

			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('robotprofitrecord:robotprofitrecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!-- <el-button v-if="isAuth('robotprofitrecord:robotprofitrecord:delete')" type="danger" @click="deleteHandle()"
				 :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
			</el-form-item>
		</el-form>
    <div style="color: red;">
    	总盈利(元):{{countProfit/1000000}}万
    </div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<!-- <el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column> -->
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" label="游戏场次">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="机器人">
			</el-table-column>
			<el-table-column prop="profitCoin" header-align="center" align="center" label="盈利额(元)">
				<template slot-scope="scope">
					{{scope.row.profitCoin/100}}
				</template>
			</el-table-column>
			<el-table-column prop="recordTime" header-align="center" align="center" label="统计日期">
			</el-table-column>
			<!--
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>-->
		</el-table>
		
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import AddOrUpdate from './robotprofitrecord-add-or-update'
	import moment from 'moment';
	export default {
		data() {
			return {
				dataForm: {
					userAccount: '',
					timeArr: '',
					gameId: '',
					gradeId: ''
				},
				gameOptions: [],
				gradeOptions: [{
						"id": 1,
						"name": "初级场"
					}, {
						"id": 2,
						"name": "中级场"
					},
					{
						"id": 3,
						"name": "高级场"
					}
				],
				countProfit: 0,
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
			AddOrUpdate
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				// this.dataListLoading = true
				//验证时间是否为空
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.timeArr;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD");
					}
				}
				//为游戏下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gameOptions = data.data
					}
				});
				this.$http({
					url: this.$http.adornUrl('/robotprofitrecord/robotprofitrecord/list'),
					method: 'post',
					data: this.$http.adornData({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'startTime': startTime,
						'endTime': endTime,
						'gameId': this.dataForm.gameId,
						'gradeId': this.dataForm.gradeId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						this.countProfit = data.countProfit
					} else {
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
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
						url: this.$http.adornUrl('/robotprofitrecord/robotprofitrecord/delete'),
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


<style>
	.el-date-editor .el-range-separator {
    padding: 0 0px;
}
</style>
