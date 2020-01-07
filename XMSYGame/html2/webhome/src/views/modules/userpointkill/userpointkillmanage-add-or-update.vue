<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<!--
			<el-form-item label="会员ID" prop="userId">
				<el-input v-model="dataForm.userId" placeholder="会员ID"></el-input>
			</el-form-item>-->
			<el-form-item label="会员账号" prop="userAccount">
				{{this.dataForm.userAccount}}
			</el-form-item>
			<el-form-item label="回收收益额度(退出点杀)" prop="removeAmount">
				<!--<el-input type="number" v-model="dataForm.removeAmount" placeholder="解除退出金额"></el-input>-->
				<el-input-number v-model="dataForm.removeAmount" controls-position="right" :min="1" label="回收收益额度(退出点杀)" ></el-input-number>
				<div><font color="red">进入点杀开始计算收益，输给平台这个额度，就退出点杀</font></div>
			</el-form-item>
			<el-form-item label="平台胜率" prop="pointKillRate">
				<!--<el-input v-model="dataForm.pointKillRate" placeholder="平台胜率"></el-input>-->
				<el-select v-model="dataForm.pointKillRate" clearable placeholder="平台胜率">
					<el-option v-for="item in options" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="点杀原因" prop="pointKillRemake">
				<el-input type="textarea" v-model="dataForm.pointKillRemake" placeholder="点杀原因"></el-input>
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
					userId:0,
					userAccount: '',
					removeAmount: '',
					pointKillRate: '',
					pointKillRemake:'',
				},
				options: [{
					name: 0.6,
					label: '60%'
				}, {
					name: 0.7,
					label: '70%'
				}, {
					name: 0.8,
					label: '80%'
				}, {
					name: 0.9,
					label: '90%'
				}, {
					name: 1,
					label: '100%'
				}],
				dataRule: {
					userAccount: [{
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
					}],
					/*pointKillRemake: [{
						required: true,
						message: '点杀原因不能为空',
						trigger: 'blur'
					}]*/
				}
			}
		},
		methods: {
			init(userId,userAccount) {
				this.dataForm.userId=userId||0
				this.dataForm.userAccount = userAccount || ''
				this.dataForm.removeAmount=''
				this.dataForm.pointKillRate=0.8
				this.dataForm.pointKillRemake=''
				this.visible = true
				this.loadPicture=false
				//console.log(this.dataForm.userId+">>>>"+this.dataForm.userAccount)
				/*this.$nextTick(() => {
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
								this.dataForm.pointKillRate = data.userpointkill.pointKillRate
								this.dataForm.removeAmount = data.userpointkill.removeAmount
								this.dataForm.sysUserAccount = data.userpointkill.sysUserAccount
								this.dataForm.remake = data.userpointkill.remake
							}
						})
					}
				})*/
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.loadPicture=true
						this.$http({
							url: this.$http.adornUrl(`/userpointkill/userpointkill/makeuserpointkill`),
							method: 'post',
							data: this.$http.adornData({
								'userId': this.dataForm.userId,
								
								'userAccount': this.dataForm.userAccount,

								'pointKillRate': this.dataForm.pointKillRate,

								'removeAmount': this.dataForm.removeAmount*100,
								
								'pointKillRemake': this.dataForm.pointKillRemake,
								

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
