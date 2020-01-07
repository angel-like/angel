<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="会员账号" prop="userAccount">
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" @change="findUserName()" :disabled="disabled"></el-input>
			</el-form-item>
			<el-form-item label="旧名称" prop="userOldName">
				<!--<el-input v-model="dataForm.userOldName" placeholder="修改前姓名"></el-input>-->
				<div>{{dataForm.userOldName}}</div>
			</el-form-item>
			<el-form-item label="新名称" prop="userNewName">
				<el-input v-model="dataForm.userNewName" placeholder="修改后姓名" :disabled="disabled"></el-input>
			</el-form-item>
			<el-form-item label="备注" prop="remake">
				<el-input type="textarea" v-model="dataForm.remake" placeholder="备注"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture" v-if="determine">确定</el-button>
			<el-button type="primary" @click="updateStatusSuccess2()"  v-if="updateStatusSuccess">确认</el-button>
			<el-button type="primary" @click="updateStatusFail2()"  v-if="updateStatusFail">驳回</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			return {
				loadPicture:false,
				visible: false,
				determine:true,//控制确定按钮隐藏
				updateStatusSuccess:false,//与确定按钮相反
				updateStatusFail:false,//与确定按钮相反
				disabled:false,//控制文本输入框不可用
				dataForm: {
					id: 0,
					userId: '',
					userAccount: '',
					userOldName: '',
					userNewName: '',
					applicantAccount: '',
					status: '',
					checkerAccount: '',
					remake: '',
				},
				dataRule: {
					userAccount: [{
						required: true,
						message: '会员账号不能为空',
						trigger: 'blur'
					}],
					/*userOldName: [{
						required: true,
						message: '修改前姓名不能为空',
						trigger: 'blur'
					}],*/
					userNewName: [{
						required: true,
						message: '修改后姓名不能为空',
						trigger: 'blur'
					}],

				}
			}
		},
		methods: {
			init(id,i) {
				this.dataForm.id = id || 0
				this.visible = true
				this.disabled=false//文本输入框可用
				this.determine=true//确定按钮显示
				this.updateStatusSuccess=false//确认按钮
				this.updateStatusFail=false//驳回按钮
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/usernamemodify/usernamemodify/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.userId = data.usernamemodify.userId
								this.dataForm.userAccount = data.usernamemodify.userAccount
								this.dataForm.userOldName = data.usernamemodify.userOldName
								this.dataForm.userNewName = data.usernamemodify.userNewName
								this.dataForm.applicantAccount = data.usernamemodify.applicantAccount
								this.dataForm.status = data.usernamemodify.status
								this.dataForm.checkerAccount = data.usernamemodify.checkerAccount
								this.dataForm.remake = data.usernamemodify.remake
								this.disabled=true//文本输入框不可用
								this.determine=false//确定按钮隐藏
								if(i==1){
									this.updateStatusSuccess=true//确认按钮
								}else if(i==2){
									this.updateStatusFail=true//驳回按钮
								}
							}
						})
					}
				})
			},
			findUserName() {
				//console.log(this.dataForm.userAccount);
				this.$http({
					url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/getUser`),
					method: 'get',
					params: this.$http.adornParams({
						'account': this.dataForm.userAccount
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataForm.userId = data.data.userId;
						this.dataForm.userOldName = data.data.userName;
						this.loadPicture=false;
					}else{
						this.loadPicture=true;
						this.dataForm.userOldName = '';
						this.$message.error(data.msg)
					}
				})
			},
			//更新状态为成功
			updateStatusSuccess2(){
				this.$http({
					url: this.$http.adornUrl(`/usernamemodify/usernamemodify/updateStatusSuccessTwo`),
					method: 'post',
					data: this.$http.adornData({
						'id': this.dataForm.id || undefined,
						'userId': this.dataForm.userId,
				
						'userAccount': this.dataForm.userAccount,
				
						'userOldName': this.dataForm.userOldName,
				
						'userNewName': this.dataForm.userNewName,
				
						'checkerAccount': this.dataForm.checkerAccount,
				
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
				})
			},
			//更新状态为驳回
			updateStatusFail2(){
				this.$http({
					url: this.$http.adornUrl(`/usernamemodify/usernamemodify/updateStatusFailTwo`),
					method: 'post',
					data: this.$http.adornData({
						'id': this.dataForm.id || undefined,
						'userId': this.dataForm.userId,
				
						'userAccount': this.dataForm.userAccount,
				
						'userOldName': this.dataForm.userOldName,
				
						'userNewName': this.dataForm.userNewName,
				
						'checkerAccount': this.dataForm.checkerAccount,
				
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
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/usernamemodify/usernamemodify/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'userId': this.dataForm.userId,

								'userAccount': this.dataForm.userAccount,

								'userOldName': this.dataForm.userOldName,

								'userNewName': this.dataForm.userNewName,

								'applicantAccount': this.dataForm.applicantAccount,

								'status': this.dataForm.status,

								'checkerAccount': this.dataForm.checkerAccount,

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
						})
					}
				})
			}
		}
	}
</script>
