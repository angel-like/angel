<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="会员账号" prop="userAccount">
				{{this.dataForm.userAccount}}
			</el-form-item>
			<el-form-item label="总资产" prop="totalCoin">
				{{Math.round(dataForm.totalCoin/100)}}
			</el-form-item>
			<el-form-item label="平台胜率" prop="pointKillRate">
				{{Math.round(dataForm.pointKillRate*100)}}%
			</el-form-item>
			<el-form-item label="回收收益额度(退出点杀)" prop="removeAmount">
				{{this.dataForm.removeAmount/100}}
			</el-form-item>
			<el-form-item label="退出原因" prop="remake">
				<el-input type="textarea" v-model="dataForm.remake" placeholder="退出原因"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			return {
				visible: false,
				loadPicture:false,
				dataForm: {
					id: 0,
					userId: '',
					userAccount: '',
					totalCoin:0,
					pointKillRate: '',
					removeAmount: '',
					sysUserAccount: '',
					remake: '',
				},
				dataRule: {
					userId: [{
						required: true,
						message: '会员ID不能为空',
						trigger: 'blur'
					}],
					/*userAccount: [{
						required: true,
						message: '会员账号不能为空',
						trigger: 'blur'
					}],
					pointKillRate: [{
						required: true,
						message: '点杀概率不能为空',
						trigger: 'blur'
					}],
					removeAmount: [{
						required: true,
						message: '解除退出金额不能为空',
						trigger: 'blur'
					}],*/
					remake: [{
						required: true,
						message: '退出原因不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.loadPicture=false
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/userpointkill/userpointkill/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.userId = data.userpointkill.userId
								this.dataForm.userAccount = data.userpointkill.userAccount
								this.dataForm.totalCoin = data.userpointkill.totalCoin
								this.dataForm.pointKillRate = data.userpointkill.pointKillRate
								this.dataForm.removeAmount = data.userpointkill.removeAmount
								this.dataForm.sysUserAccount = data.userpointkill.sysUserAccount
								this.dataForm.remake = data.userpointkill.remake
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.loadPicture=true
						this.$http({
							url: this.$http.adornUrl(`/userpointkill/userpointkill/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'userId': this.dataForm.userId,

								'userAccount': this.dataForm.userAccount,

								'pointKillRate': this.dataForm.pointKillRate,

								'removeAmount': this.dataForm.removeAmount,

								'sysUserAccount': this.dataForm.sysUserAccount,

								'remake': this.dataForm.remake,

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
							this.loadPicture=false
						})
					}
				})
			}
		}
	}
</script>
