<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="代理名称" prop="userName">
				<el-input v-model="dataForm.userName" placeholder="登录帐号"></el-input>
			</el-form-item>
			<el-form-item label="代理账户" prop="mobile">
				<el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="password" :class="{ 'is-required': !dataForm.id }">
				<el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
			</el-form-item>
			<el-form-item label="确认密码" prop="comfirmPassword" :class="{ 'is-required': !dataForm.id }">
				<el-input v-model="dataForm.comfirmPassword" type="password" placeholder="确认密码"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	import {
		isEmail,
		isMobile
	} from '@/utils/validate'
	export default {
		data() {
			var validatePassword = (rule, value, callback) => {
				if (!this.dataForm.id && !/\S/.test(value)) {
					callback(new Error('密码不能为空'))
				} else {
					callback()
				}
			}
			var validateComfirmPassword = (rule, value, callback) => {
				if (!this.dataForm.id && !/\S/.test(value)) {
					callback(new Error('确认密码不能为空'))
				} else if (this.dataForm.password !== value) {
					callback(new Error('确认密码与密码输入不一致'))
				} else {
					callback()
				}
			}
			var validateEmail = (rule, value, callback) => {
				if (!isEmail(value)) {
					callback(new Error('邮箱格式错误'))
				} else {
					callback()
				}
			}
			var validateMobile = (rule, value, callback) => {
				if (!isMobile(value)) {
					callback(new Error('手机号格式错误'))
				} else {
					callback()
				}
			}
			return {
				visible: false,
				roleList: [],
				dataForm: {
					id: 0,
					userName: '',
					password: '',
					comfirmPassword: '',
					salt: '',
					email: '',
					mobile: '',
					otpEnable: true,
					roleIdList: [],
					status: 1
				},
				dataRule: {
					userName: [{
						required: true,
						message: '代理名称不能为空',
						trigger: 'blur'
					}],
					password: [{
						validator: validatePassword,
						trigger: 'blur'
					}],
					comfirmPassword: [{
						validator: validateComfirmPassword,
						trigger: 'blur'
					}],
					/*email: [{
							required: true,
							message: '邮箱不能为空',
							trigger: 'blur'
						},
						{
							validator: validateEmail,
							trigger: 'blur'
						}
					],*/
					mobile: [{
							required: true,
							message: '代理账户不能为空',
							trigger: 'blur'
						},
						{
							validator: validateMobile,
							trigger: 'blur'
						}
					]
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
							url: this.$http.adornUrl(`/sys/user/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.userName = data.user.username
								this.dataForm.salt = data.user.salt
								this.dataForm.email = data.user.email
								this.dataForm.mobile = data.user.mobile
								this.dataForm.status = data.user.status
								this.dataForm.otpEnable = data.user.otpEnable

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
							url: this.$http.adornUrl(`/sys/user/${!this.dataForm.id ? 'saveproxy' : 'updateproxy'}`),
							method: 'post',
							data: this.$http.adornData({
								'userId': this.dataForm.id || undefined,
								'username': this.dataForm.userName,
								'password': this.dataForm.password,
								'mobile': this.dataForm.mobile,
								'status': this.dataForm.status,
							//'roleIdList': this.dataForm.roleIdList,
								'otpEnable': this.dataForm.otpEnable
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
