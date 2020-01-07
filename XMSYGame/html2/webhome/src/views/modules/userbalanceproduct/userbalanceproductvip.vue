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
<!--			<el-form-item>-->
<!--					<el-option v-for="item in enableOptions" :key="item.name" :label="item.label" :value="item.name">-->
<!--					</el-option>-->
<!--				</el-select>-->
<!--			</el-form-item>-->
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

<!--      <el-table-column prop="user_id" header-align="center" align="center" label="id">-->
<!--      </el-table-column>-->


<!--      			<el-table-column prop="orderNum" header-align="center" align="center" label="id">-->
<!--      			</el-table-column>-->

      <el-table-column prop="userName" header-align="center" align="center" label="会员名称">
      </el-table-column>
      <el-table-column prop="account" header-align="center" align="center" label="会员帐号">
      </el-table-column>
			<el-table-column prop="productName" header-align="center" align="center" label="方案名称">
			</el-table-column>
			<el-table-column prop="settlementType" header-align="center" align="center" label="结算类型">
				<template slot-scope="scope">
					<div v-if="scope.row.settlementType==0">循环结算</div>
					<div v-if="scope.row.settlementType==1">本金结算</div>
				</template>
			</el-table-column>
<!--			<el-table-column prop="minMoney" header-align="center" align="center" label="最低金额">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="maxMoney" header-align="center" align="center" label="最高金额">-->
<!--			</el-table-column>	-->
      <el-table-column prop="money" header-align="center" align="center" label="金额">
			</el-table-column>
<!--			<el-table-column prop="rate" header-align="center" align="center" label="利率">-->
<!--				<template slot-scope="scope">-->
<!--					<div v-if="scope.row.id==1">{{scope.row.rate*10000}}%（万元化）</div>-->
<!--					<div v-else>{{scope.row.rate*100}}%</div>-->
<!--				</template>-->
<!--			</el-table-column>-->

      <el-table-column prop="rate" header-align="center" align="center" label="利率">
      </el-table-column>
<!--			<el-table-column prop="rateMoneyMax" header-align="center" align="center" label="利息上限">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="remaindBuyNum" header-align="center" align="center" label="剩余可买份数">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="betMultiple" header-align="center" align="center" label="打码倍数">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="userTodayBuyNum" header-align="center" align="center" label="会员当天可购买次数">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="issueNum" header-align="center" align="center" label="发行份数">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="createTime" header-align="center" align="center" label="发行时间">-->
<!--			</el-table-column>-->
<!--			<el-table-column prop="settlementCycle" header-align="center" align="center" label="结算周期">-->
<!--			</el-table-column>-->

<!--			<el-table-column prop="issueTime" header-align="center" align="center" label="结算时间">-->
<!--				<template slot-scope="scope">-->
<!--					<div v-if="scope.row.settlementType==0">每天结算一次</div>-->
<!--					<div v-if="scope.row.settlementType==1">{{scope.row.issueTime}}</div>-->
<!--				</template>-->
<!--			</el-table-column>-->
<!--      <el-table-column prop="" header-align="center" align="center" label="收益">-->
<!--        <template slot-scope="scope">-->
<!--          <div>-->
<!--            {{scope.row.money*scope.row.rate}}-->
<!--          </div>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="profit" header-align="center" align="center" label="收益">
      </el-table-column>

      <el-table-column prop="issueTime" header-align="center" align="center" label="结算时间">
      </el-table-column>

<!--			<el-table-column prop="enable" header-align="center" align="center" label="状态">-->
<!--				<template slot-scope="scope">-->
<!--					<div v-if="scope.row.enable==0">关闭</div>-->
<!--					<div v-if="scope.row.enable==1">开启</div>-->
<!--				</template>-->
<!--			</el-table-column>-->

<!--			<el-table-column prop="orderNum" header-align="center" align="center" label="排序">-->
      <!--			</el-table-column>-->

<!--			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">-->
<!--				<template slot-scope="scope">-->
<!--&lt;!&ndash;					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>&ndash;&gt;-->
<!--					&lt;!&ndash;-->
<!--					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>&ndash;&gt;-->
<!--				</template>-->
<!--			</el-table-column>-->
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
<!--		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
	</div>
</template>

<script>
	/*import AddOrUpdate from './userbalanceproductvip-add-or-update'*/
	export default {
		data() {
			return {
				options: [{
					name: 0,
					label: '循环结算'
				}, {
					name: 1,
					label: '本金结算'
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
          user_name:'',
          profit:'',
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
        data() {
        },
        dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false
			}
		},
		components: {
		/*	AddOrUpdate*/
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/userbalanceproduct/userbalanceproduct/vipProductss'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'settlementType': this.dataForm.settlementType,
						'productName':this.dataForm.productName,
						'enable':this.dataForm.enable,
            'userName':this.dataForm.userName,
            'account':this.dataForm.account,
            'money':this.dataForm.money,
            'profit':this.dataForm.profit
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
				});
			}
		}
	}
</script>
