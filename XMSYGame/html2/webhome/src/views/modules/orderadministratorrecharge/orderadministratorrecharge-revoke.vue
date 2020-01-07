<template>
  <el-dialog
    :title="'充值明细' "
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :inline="true" :model="dataForm"  ref="dataForm" @keyup.enter.native="dataFormSubmit()" >
    <el-form-item label="充值账号" prop="userAccount">
      <el-input size="small " v-model="dataForm.userAccount" clearable placeholder="充值账号"></el-input>
		</el-form-item>
		<el-form-item>
			<el-button @click="getRechargeList()">查询</el-button>
		</el-form-item>
    </el-form>
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
				label="人工充值主订单号">
			</el-table-column>
			<el-table-column
				prop="amount"
				header-align="center"
				align="center"
				label="金额">
			</el-table-column>
			<el-table-column
				prop="depositDate"
				header-align="center"
				align="center"
				label="存款时间">
			</el-table-column>
			<el-table-column
				prop="userAccount"
				header-align="center"
				align="center"
				label="充值账号">
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
				label="备注">
			</el-table-column>
			<el-table-column
				fixed="right"
				header-align="center"
				align="center"
				width="50"
				label="操作">
				<template slot-scope="scope">
					<el-button v-if="scope.row.status==2" type="text" size="small" @click="revoke(scope.row)">撤销</el-button>
				</template>
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
          operationType: 0,
          userAccount: null
        }
      }
    },
    methods: {
      init (orderNo) {
        this.dataForm.orderNo = orderNo
        this.visible = true
        this.$nextTick(() => {
					this.dataListLoading = true
          this.$refs['dataForm'].resetFields()
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
