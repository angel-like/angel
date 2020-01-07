<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="盈利金币额" prop="profitCoin">
				<el-input v-model="dataForm.profitCoin" placeholder="盈利金币额"></el-input>
			</el-form-item>
			<el-form-item label="机器人账户名" prop="userAccount">
				<el-input v-model="dataForm.userAccount" placeholder="机器人账户名"></el-input>
			</el-form-item>
			<el-form-item label="机器人ID" prop="userId">
				<el-input v-model="dataForm.userId" placeholder="机器人ID"></el-input>
			</el-form-item>
			<el-form-item label="统计日期" prop="recordTime">
				<el-input v-model="dataForm.recordTime" placeholder="统计日期"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			return {
				visible: false,
				dataForm: {
					id: 0,
					profitCoin: '',
					userAccount: '',
					userId: '',
					recordTime: '',
				},
				dataRule: {
					profitCoin: [{
						required: true,
						message: '盈利金币额不能为空',
						trigger: 'blur'
					}],
					userAccount: [{
						required: true,
						message: '机器人账户名不能为空',
						trigger: 'blur'
					}],
					userId: [{
						required: true,
						message: '机器人ID不能为空',
						trigger: 'blur'
					}],
					recordTime: [{
						required: true,
						message: '统计日期不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/robotprofitrecord/robotprofitrecord/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.profitCoin = data.robotprofitrecord.profitCoin
								this.dataForm.userAccount = data.robotprofitrecord.userAccount
								this.dataForm.userId = data.robotprofitrecord.userId
								this.dataForm.recordTime = data.robotprofitrecord.recordTime
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/robotprofitrecord/robotprofitrecord/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'profitCoin': this.dataForm.profitCoin,

								'userAccount': this.dataForm.userAccount,

								'userId': this.dataForm.userId,

								'recordTime': this.dataForm.recordTime,

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
										this.visible = false
										this.$emit('refreshDataList')
									}
								})
							} else {
								this.$message.error(data.msg)
							}
						})
					}
				})
			}
		}
	}
</script>
