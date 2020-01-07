<template>
	<el-dialog :title="!dataForm.id ? '新增' : '上分'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="会员账号" prop="account">
				<el-input v-model="dataForm.account" placeholder="会员账号" readOnly></el-input>
			</el-form-item>
			<el-form-item label="会员名称" prop="userName">
				<el-input v-model="dataForm.userName" placeholder="会员名称" readOnly></el-input>
			</el-form-item>
			<el-form-item label="上分金币" prop="coin">
				<el-input-number v-model="dataForm.coin" :min="0" controls-position="right" label="上分金币" ></el-input-number>
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
					account: '',
					userName: '',
					coin:0
				},
				dataRule: {}
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
							url: this.$http.adornUrl(`/sys/proxy/userinfo/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.account = data.user.account
								this.dataForm.userName = data.user.userName
								
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
							url: this.$http.adornUrl(`/sys/proxy/${!this.dataForm.id ? 'save' : 'transfer'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'account': this.dataForm.account,
								'userName': this.dataForm.userName,
								'coin': this.dataForm.coin*100,
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
