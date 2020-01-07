<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.productName" placeholder="方案名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.settlementType" placeholder="结算类型" clearable>
					<el-option v-for="item in options" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.enable" placeholder="状态" clearable>
					<el-option v-for="item in enableOptions" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
				<el-button v-if="isAuth('userbalanceproduct:userbalanceproduct:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!--<el-button v-if="isAuth('userbalanceproduct:userbalanceproduct:delete')" type="danger" @click="deleteHandle()"
				 :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<!--<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>-->
			<el-table-column prop="productName" header-align="center" align="center" label="方案名称">
			</el-table-column>
			<el-table-column prop="settlementType" header-align="center" align="center" label="结算类型">
				<template slot-scope="scope">
					<div v-if="scope.row.settlementType==0">循环结算</div>
					<div v-if="scope.row.settlementType==1">一次结算</div>
				</template>
			</el-table-column>
			<el-table-column prop="minMoney" header-align="center" align="center" label="最低金额">
				<template slot-scope="scope">
					{{Math.round(scope.row.minMoney/100)}}
				</template>
			</el-table-column>
			<el-table-column prop="maxMoney" header-align="center" align="center" label="最高金额">
				<template slot-scope="scope">
					{{Math.round(scope.row.maxMoney/100)}}
				</template>
			</el-table-column>
			<el-table-column prop="rate" header-align="center" align="center" label="利率(万元化)">
				<template slot-scope="scope">
					{{scope.row.rate*10000}}‱
				</template>
			</el-table-column>
			<el-table-column prop="rateMoneyMax" header-align="center" align="center" label="利息上限">
				<template slot-scope="scope">
					{{Math.round(scope.row.rateMoneyMax/100)}}
				</template>
			</el-table-column>
			<el-table-column prop="remaindBuyNum" header-align="center" align="center" label="剩余可买金额">
				<template slot-scope="scope">
					{{Math.round(scope.row.remaindBuyNum/100)}}
				</template>
			</el-table-column>
			<el-table-column prop="betMultiple" header-align="center" align="center" label="打码倍数">
			</el-table-column>
			<el-table-column prop="userTodayBuyNum" header-align="center" align="center" label="会员当天可购买次数">
			</el-table-column>
			<el-table-column prop="issueNum" header-align="center" align="center" label="发行金额">
				<template slot-scope="scope">
					{{Math.round(scope.row.issueNum/100)}}
				</template>
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="发行时间">
			</el-table-column>
			<el-table-column prop="settlementCycle" header-align="center" align="center" label="结算周期">
			</el-table-column>
			<!--
			<el-table-column prop="issueTime" header-align="center" align="center" label="结算时间">
				<template slot-scope="scope">
					<div v-if="scope.row.settlementType==0">每天结算一次</div>
					<div v-if="scope.row.settlementType==1">{{scope.row.issueTime}}</div>
				</template>
			</el-table-column>-->
			<el-table-column prop="enable" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<div v-if="scope.row.enable==0">关闭</div>
					<div v-if="scope.row.enable==1">开启</div>
				</template>
			</el-table-column>
			<el-table-column prop="orderNum" header-align="center" align="center" label="排序">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<!--
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>-->
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import AddOrUpdate from './userbalanceproduct-add-or-update'
	export default {
		data() {
			return {
				options: [{
					name: 0,
					label: '循环结算'
				}, {
					name: 1,
					label: '一次结算'
				}],
				enableOptions: [{
					name: 1,
					label: '开启'
				}, {
					name: 0,
					label: '关闭'
				}],
				dataForm: {
					productName: '',
					settlementType:'',
					enable:'',
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
				this.$http({
					url: this.$http.adornUrl('/userbalanceproduct/userbalanceproduct/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'settlementType': this.dataForm.settlementType,
						'productName':this.dataForm.productName,
						'enable':this.dataForm.enable,
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
						url: this.$http.adornUrl('/userbalanceproduct/userbalanceproduct/delete'),
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
