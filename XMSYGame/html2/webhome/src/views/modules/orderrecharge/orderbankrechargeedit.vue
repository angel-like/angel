<template>
	<el-dialog
		:title="'查看'"
		:close-on-click-modal="false"
		:visible.sync="visible">
	<div class="mod-config">
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
			</el-table-column>
			<el-table-column prop="depositDate" header-align="center" align="center" label="提交时间">
			</el-table-column>
			<el-table-column prop="rechargeTime" header-align="center" align="center" label="充值时间">
			</el-table-column>
			<el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作人">
			</el-table-column>
			<el-table-column prop="amount" header-align="center" align="center" label="金额">
			</el-table-column>
			<el-table-column prop="depositName" header-align="center" align="center" label="存款人">
			</el-table-column>
			<el-table-column prop="depositType" header-align="center" align="center" label="存款方式">
				<template slot-scope="scope">
					<div v-if="scope.row.depositType==0">
						网银转账
					</div>
					<div v-if="scope.row.depositType==1">
						ATM转账
					</div>
					<div v-if="scope.row.depositType==2">
						中国银行
					</div>
					<div v-if="scope.row.depositType==3">
						招商银行
					</div>
					<div v-if="scope.row.depositType==4">
						建设银行
					</div>
					<div v-if="scope.row.depositType==5">
						交通银行
					</div>
					<div v-if="scope.row.depositType==6">
						农业银行
					</div>
					<div v-if="scope.row.depositType==7">
						兴业银行
					</div>
					<div v-if="scope.row.depositType==8">
						华夏银行
					</div>
					<div v-if="scope.row.depositType==9">
						邮政银行
					</div>
					<div v-if="scope.row.depositType==10">
						农村信用社
					</div>
					<div v-if="scope.row.depositType==11">
						中国民生银行
					</div>
					<div v-if="scope.row.depositType==12">
						支付宝收款
					</div>
					<div v-if="scope.row.depositType==13">
						微信收款
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="incomeBank" header-align="center" align="center" label="收款银行">
			</el-table-column>
			<el-table-column prop="incomeBankAccount" header-align="center" align="center" label="收款账号">
			</el-table-column>
			<el-table-column prop="payee" header-align="center" align="center" label="收款人">
			</el-table-column>
			<el-table-column prop="status" header-align="center" align="center" label="订单状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
					<el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
					<el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
					<el-tag v-if="scope.row.status==4" size="small" type="success">已锁定</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="支付人账号">
			</el-table-column>
			<el-table-column prop="fristrecharge" header-align="center" align="center" label="是否首充">
				<template slot-scope="scope">
					<div v-if="scope.row.fristrecharge==false">
						否
					</div>
					<div v-if="scope.row.fristrecharge==true">
						是
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="discountAmount" header-align="center" align="center" label="优惠金额">
			</el-table-column>
			<el-table-column prop="remark" header-align="center" align="center" label="备注">
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		</el-tabs>
	</div>
	</el-dialog>
</template>

<script>
	import moment from 'moment';
	export default {
		data() {
			return {
				visible: false,
				dataForm: {
					num: 3,
					status: 4,
					sysUserId:null
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
		activated() {
			this.getDataList()
		},
		methods: {
			init(status){
				this.dataForm.status=status
				if(status==2){
					this.dataForm.sysUserId=this.$store.state.user.id
				}else{
					this.dataForm.sysUserId=null
				}
				this.getDataList()
			},
			// 获取数据列表
			getDataList() {
				this.visible=true
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/orderrecharge/orderrecharge/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'rechargeType': 3,
						'status':this.dataForm.status,
						'sysUserId': this.dataForm.sysUserId

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
			//批量锁定订单
			recieveTheTask() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl(`/orderrecharge/orderrecharge/batchLocking`),
					method: 'post',
					data: this.$http.adornData({
						'num': this.dataForm.num,
						'sysUserAccount': this.$store.state.user.name,
						'sysUserId': this.$store.state.user.id
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.$message({
							message: data.result,
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getDataList()
							}
						})
					} else {
						this.$message.error(data.msg)
						this.getDataList()
					}
				})
				this.dataListLoading = false
				this.$emit('refreshDataList')
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
			//解除锁定
			emancipateLockingHandle(id) {
				this.$confirm(`确定要解除该订单锁定状态?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl('/orderrecharge/orderrecharge/dealWithEmancipateLocking/' + id),
						method: 'post'
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
							this.getDataList()
						}
					})
					this.dataListLoading = false
				})
				this.$emit('refreshDataList')
			},
			// 确认订单
			confirmedHandle(id) {
				this.$confirm(`确定完成订单操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl('/orderrecharge/orderrecharge/dealWithConfirmed/' + id),
						method: 'post'
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
							this.getDataList()
						}
					})
					this.dataListLoading = false
				})
				this.$emit('refreshDataList')
			},
			// 			 handleClick(tab, event) {
			//         this.getDataList()
			//       },
			// 取消订单
			canceledHandle(id) {
				this.$confirm(`确定取消订单操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl('/orderrecharge/orderrecharge/dealWithCanceled/' + id),
						method: 'post'
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
							this.getDataList()
						}
					})
					this.dataListLoading = false
				})
				this.$emit('refreshDataList')
			}
		}
	}
</script>
