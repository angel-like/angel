<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()" style="margin-top: 10px;">
			<el-form-item label="任务数量">
				<el-select v-model="dataForm.num" clearable placeholder="请选择领取数量">
					<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="recieveTheTask()">领取任务</el-button>
				<el-button type="primary" @click="processedOrder()">已处理</el-button>
				<el-badge :value="waitNum" class="item">
					<el-button type="primary" @click="waitOrder()">待处理</el-button>
				</el-badge>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="取款时间">
			</el-table-column>
			<el-table-column prop="updateTime" header-align="center" align="center" label="操作时间">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="取款用户账号">
			</el-table-column>
			<el-table-column prop="takeAmount" header-align="center" align="center" label="取款金额">
				<template slot-scope="scope">
				  <span  style="color: red;">{{scope.row.takeAmount}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="userNeedBet" header-align="center" align="center" label="取款需要打码">
			</el-table-column>
			<el-table-column prop="userValidBet" header-align="center" align="center" label="用户有效打码">
			</el-table-column>
			<el-table-column prop="poundage" header-align="center" align="center" label="手续费">
			</el-table-column>
			<el-table-column prop="incomeNo" header-align="center" align="center" label="入款账号">
			</el-table-column>
			<el-table-column prop="bankName" header-align="center" align="center" label="银行名称">
			</el-table-column>
			<el-table-column prop="accountName" header-align="center" align="center" label="开户名">
			</el-table-column>
			<el-table-column prop="status" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
					<el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
					<el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
					<el-tag v-if="scope.row.status==4" size="small" type="success">已锁定</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="accountType" header-align="center" align="center" label="取款类型">
				<template slot-scope="scope">
					<div v-if="scope.row.accountType==0">
						支付宝
					</div>
					<div v-if="scope.row.accountType==1">
						银行
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作管理员">
			</el-table-column>
			<el-table-column prop="betCancel" header-align="center" align="center" label="是否取消打码">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.betCancel" size="small" type="info">是</el-tag>
					<el-tag v-if="!scope.row.betCancel" size="small" type="info">否</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="200" label="操作">
				<template slot-scope="scope">
					<div v-show="scope.row.status=='4'">
						<el-button v-if="isAuth('ordertakemoney:ordertakemoney:dealWithConfirmed')" type="text" size="small" @click="confirmedHandle(scope.row.id)">确认订单</el-button>
						<el-button v-if="isAuth('ordertakemoney:ordertakemoney:dealWithCanceled')" type="text" size="small" @click="canceledHandle(scope.row.id)">取消订单</el-button>
						<el-button v-if="isAuth('ordertakemoney:ordertakemoney:dealWithBetCanceled')" type="text" size="small" @click="cancelBetHandle(scope.row.id)">强制确认</el-button>
						<el-button v-if="isAuth('ordertakemoney:ordertakemoney:dealWithEmancipateLocking')" type="text" size="small"
						 @click="emancipateLockingHandle(scope.row.id)">解除锁定</el-button>
						<el-button v-else type="info" size="small">无</el-button>
					</div>
					<div v-show="scope.row.status=='2'|| scope.row.status=='3'">
						<el-button type="info" size="small">已处理</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<Ordertakemoneyedit v-if="ordertakemoneyeditVisible" ref="ordertakemoneyedit" @refreshDataList="getDataList"></Ordertakemoneyedit>
	</div>
</template>

<script>
	import moment from 'moment';
	import Ordertakemoneyedit from './ordertakemoneyedit';
	export default {
		data() {
			return {
				dataForm: {
					num: 3,
					status: 4
				},
				waitNum:0,
				options: [{
						value: 1,
						label: '1条'
					},
					{
						value: 3,
						label: '3条'
					},
					{
						value: 5,
						label: '5条'
					},
					{
						value: 10,
						label: '10条'
					}
				],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				ordertakemoneyeditVisible: false
			}
		},
		components: {
			Ordertakemoneyedit
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'type': 0,
						'status': this.dataForm.status,
						'sysUserId': this.$store.state.user.id
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
				})
				this.$http({
					url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/totalNumber'),
					method: 'get',
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.waitNum=data.num
					} 
					
				})
				this.dataListLoading = false
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
			//已处理弹框
			processedOrder() {
				this.ordertakemoneyeditVisible=true
				this.$nextTick(() => {
					this.$refs.ordertakemoneyedit.init(2)
				})
			},
			//待处理弹框
			waitOrder() {
				this.ordertakemoneyeditVisible=true
				this.$nextTick(() => {
					this.$refs.ordertakemoneyedit.init(0)
				})
			},
			//批量锁定订单
			recieveTheTask() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl(`/ordertakemoney/ordertakemoney/batchLocking`),
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
			emancipateLockingHandle(id) {
				this.$confirm(`确定要解除该订单锁定状态?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/emancipateLocking/' + id),
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
						url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/confirmed/' + id),
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
					this.$emit('refreshDataList')
				})
			},
      // 取消订单
      canceledHandle (id) {
        this.$prompt(`确定取消订单操作?(可在下框中输入备注)`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^.{0,200}$/,
          inputErrorMessage: '备注输入不得超过200个字'
        }).then(({ value }) => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/canceled'),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'remark': value
            })
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
      },
			// 取消订单
			// canceledHandle(id) {
			// 	this.$confirm(`确定取消订单操作?`, '提示', {
			// 		confirmButtonText: '确定',
			// 		cancelButtonText: '取消',
			// 		type: 'warning'
			// 	}).then(() => {
			// 		this.dataListLoading = true
			// 		this.$http({
			// 			url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/canceled/' + id),
			// 			method: 'post'
			// 		}).then(({
			// 			data
			// 		}) => {
			// 			if (data && data.code === 200) {
			// 				this.$message({
			// 					message: '操作成功',
			// 					type: 'success',
			// 					duration: 1500,
			// 					onClose: () => {
			// 						this.getDataList()
			// 					}
			// 				})
			// 			} else {
			// 				this.$message.error(data.msg)
			// 				this.getDataList()
			// 			}
			// 		})
			// 		this.dataListLoading = false
			// 		this.$emit('refreshDataList')
			// 	})
			// },
			// 取消打码订单
			cancelBetHandle(id) {
				this.$confirm(`确定取消订单操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/bet/canceled/' + id),
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
					this.$emit('refreshDataList')
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
						url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/delete'),
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
							this.getDataList()
						}
					})
				})
			}
		}
	}
</script>
