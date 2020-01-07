<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userId" placeholder="用户ID" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('gamerecord:gamerecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!-- <el-button v-if="isAuth('gamerecord:gamerecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>

			<el-table-column prop="userId" header-align="center" align="center" label="用户ID">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="用户账号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="游戏时间">
			</el-table-column>
			<!-- <el-table-column prop="robot" header-align="center" align="center" label="是否机器人">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.robot" size="small" type="danger">是</el-tag>
					<el-tag v-else size="small">否</el-tag>
				</template>
			</el-table-column> -->
			<el-table-column prop="gameName" header-align="center" align="center" label="游戏名称">
			</el-table-column>
			<el-table-column prop="gradeName" header-align="center" align="center" label="场次名称">
			</el-table-column>
			<el-table-column prop="roomName" header-align="center" align="center" label="房间名称">
			</el-table-column>
			<!-- <el-table-column prop="betCoins" header-align="center" align="center" label="下注总金币">
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
			<!-- <el-table-column prop="userType" header-align="center" align="center" label="用户类型">
			</el-table-column> -->
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
			<el-table-column prop="profitCoins" header-align="center" align="center" label="代理商盈利金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.profitCoins/100}}
						</div>
				</template>
			</el-table-column>
			<el-table-column prop="waterProfit" header-align="center" align="center" label="抽水金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.waterProfit/100}}
						</div>
				</template>
			</el-table-column>
			<!-- <el-table-column prop="exchangeRate" header-align="center" align="center" label="房卡转金币的汇率">
			</el-table-column> -->
			<el-table-column prop="gameRoundNo" header-align="center" align="center" label="游戏局号">
			</el-table-column>
			<!--    <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column> -->
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import AddOrUpdate from './gamerecord-add-or-update'
	import moment from 'moment';
	export default {
		data() {
			return {
				pickerOptions2: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				dataForm: {
					userid: '',
					queryTime: [new Date(), new Date()]
				},
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
				this.dataListLoading = true
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				// 				console.log("startDate："+startDate)
				// 				console.log("endDate："+endDate)
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/gamerecord/gamerecord/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userId': this.dataForm.userId,
						'startTime': startTime,
						'endTime': endTime
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
						url: this.$http.adornUrl('/gamerecord/gamerecord/delete'),
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
