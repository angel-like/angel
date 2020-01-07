<template>
  <el-dialog
    :title="'查看' "
    :close-on-click-modal="false"
    :visible.sync="visible">
		<el-table
			:data="dataList"
			border
			v-loading="dataListLoading"
			style="width: 100%;">
			<!-- <el-table-column
				type="selection"
				header-align="center"
				align="center"
				width="50">
			</el-table-column> -->
		<el-table-column
      type="index"
			label="序号"
      width="50">
			</el-table-column>
			<el-table-column
				prop="orderNo"
				header-align="center"
				align="center"
				label="订单号">
			</el-table-column>
			<el-table-column
				prop="adminOrderNo"
				header-align="center"
				align="center"
				label="人工存取款主订单号">
			</el-table-column>
			<el-table-column
				prop="amount"
				header-align="center"
				align="center"
				v-if="dataForm.operationType==0"
				label="金额">
			</el-table-column>
			<el-table-column
				prop="depositDate"
				header-align="center"
				align="center"
				v-if="dataForm.operationType==0"
				label="操作时间">
			</el-table-column>
			<el-table-column
				prop="takeAmount"
				header-align="center"
				align="center"
				v-if="dataForm.operationType==1"
				label="金额">
			</el-table-column>
			<el-table-column
				prop="createTime"
				header-align="center"
				align="center"
				v-if="dataForm.operationType==1"
				label="操作时间">
			</el-table-column>
			<el-table-column
				prop="userAccount"
				header-align="center"
				align="center"
				label="会员账号">
			</el-table-column>
			<el-table-column
				prop="status"
				header-align="center"
				align="center"
				label="订单状态">
				<template slot-scope="scope">
						<div v-if="scope.row.status==2">
							订单完成
						</div>
						<div v-if="scope.row.status==3">
							订单撤销
						</div>
				</template>
			</el-table-column>
			<el-table-column
				prop="remark"
				header-align="center"
				align="center"
				label="订单备注">
			</el-table-column>
		</el-table>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
				dataList: [],
				dataListLoading: false,
        dataForm: {
          orderNo: '',
					operationType:0,
          userAccount: null
        }
      }
    },
    methods: {
      init (orderNo,operationType) {
        this.dataForm.orderNo = orderNo
        this.dataForm.operationType = operationType
        this.visible = true
        this.$nextTick(() => {
					this.dataListLoading = true
          this.getRechargeList();
        })
      },
			getRechargeList(){
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/orderadministratorrecharge/orderadministratorrecharge/getRechargeList'),
					method: 'get',
					params: this.$http.adornParams({
						'adminOrderNo': this.dataForm.orderNo,
						'operationType': this.dataForm.operationType,
						'userAccount': this.dataForm.userAccount
					})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.dataList = data.rechargeList
					} else {
						this.dataList = []
					}
					this.dataListLoading = false
				})
			},
      // 撤销
      revoke (rechargeData) {
            this.$confirm(`确定对[充值账号=${rechargeData.userAccount}]订单进行撤销操作?`, '提示', {
            	confirmButtonText: '确定',
            	cancelButtonText: '取消',
            	type: 'warning'
            }).then(() => {
							var userOperater={};
							userOperater.memberId=rechargeData.userId;
							userOperater.memberAccount=rechargeData.userAccount ;
							userOperater.remark="撤销充值订单号【"+rechargeData.orderNo+"】充值金额为【"+rechargeData.amount+"】";
									this.$http({
										url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/revoke`),
										method: 'post',
										data: this.$http.adornData({
											'id': rechargeData.id,
											'userOperater':userOperater
										})
									}).then(({data}) => {
										if (data && data.code === 200) {
											this.$message({
												message: '操作成功',
												type: 'success',
												duration: 1500,
												onClose: () => {
													this.getRechargeList();
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
