<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <div style="margin: -64px 60px 40px 60px;">
			<el-tooltip class="item" effect="dark" content="首先扫描二维码下载谷歌身份验证器，然后绑定自己的ip，绑定后验证码隔几秒钟机会刷新一次，如果下载下来没有绑定也可以用OTP秘钥绑定" placement="top">
					<i class="el-icon-question" style="color:blue" ></i>
			</el-tooltip>
		</div>
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-tooltip class="item" effect="dark" content="otp的身份标识" placement="top-start">
				<el-form-item label="otp秘钥" prop="otpSecret">
					<el-input v-model="dataForm.otpSecret" placeholder="otp密匙" readonly></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="用户名可以自己设置" placement="top-start">
      	<el-form-item label="用户名" prop="username">
      		<el-input v-model="dataForm.username" placeholder="用户名"></el-input>
      	</el-form-item>
        </el-tooltip>
      <el-tooltip class="item" effect="dark" content="绑定自己的ip地址(ip只能绑定一次!!!)" placement="top-start">
      	<el-form-item label="otp绑定的ip" prop="bindIp">
      		<el-input v-model="dataForm.bindIp" placeholder="otp绑定的ip"></el-input>
      	</el-form-item>
      </el-tooltip>
      		<el-form-item label="otp二维码" v-show="isShow">
      		      <img v-bind:src="src">
      	</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	import { isIp } from '@/utils/validate'
	export default {
		data() {
			var validateIp = (rule, value, callback) => {
				var ipArray=value.split(",");
				for(var j = 0,len = ipArray.length; j < len; j++){
					if (!isIp(ipArray[j])) {
						callback(new Error('ip格式错误'))
					}
        }
				callback()
			}
			return {
				visible: false,
				isShow: false,
				src: '',
				dataForm: {
					id: 0,
					otpSecret: '',
					username: '',
					bindIp: '',
				},
				dataRule: {
					otpSecret: [{
						required: true,
						message: 'otp秘钥不能为空',
						trigger: 'blur'
					}],
					username: [{
						required: true,
						message: '用户名不能为空',
						trigger: 'blur'
					}],
					bindIp: [{
						required: true,
						message: 'otp绑定的ip格式不正确',
						trigger: 'blur'
					},{ validator: validateIp, trigger: 'blur' }]
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
							url: this.$http.adornUrl(`/userotp/userotp/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.isShow = false
								this.dataForm.otpSecret = data.userotp.otpSecret
								this.dataForm.username = data.userotp.username
								this.dataForm.bindIp = data.userotp.bindIp
							}
						})
					} else {
						this.$http({
							url: this.$http.adornUrl(`/userotp/userotp/info/otp/`+this.$store.state.user.id),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.isShow = true
								this.dataForm.otpSecret = data.userotp.secrets
								this.dataForm.username = data.userotp.username
								this.src = data.userotp.qrcode
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
							url: this.$http.adornUrl(`/userotp/userotp/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'otpSecret': this.dataForm.otpSecret,

								'username': this.dataForm.username,

								'bindIp': this.dataForm.bindIp,

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
