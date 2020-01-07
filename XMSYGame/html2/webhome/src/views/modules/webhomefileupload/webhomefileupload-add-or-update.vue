<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<!--
			<el-form-item label="平台" prop="platform">
				<el-radio v-model="dataForm.platform" label="android" border></el-radio>
				<el-radio v-model="dataForm.platform" label="ios" border></el-radio>
			</el-form-item>-->
			<el-form-item label="证书别名" prop="certificateName">
				<el-input v-model="dataForm.certificateName" placeholder="证书别名"></el-input>
			</el-form-item>
			<!--
			<el-form-item label="文件名称" prop="fileName">
				<el-input v-model="dataForm.fileName" placeholder="文件名称"></el-input>
			</el-form-item>-->
			<el-form-item label="p12文件" prop="p12Content">
				<!--<el-input v-model="dataForm.p12Content" placeholder="p12文件内容"  style="text-align: left;width:200px;"  readOnly></el-input>-->
				<el-upload class="upload-demo" :action="UploadUrlP12()" :limit="1" :before-upload="beforeUploadHandleP12"
				 :on-success="successHandleP12" multiple :file-list="fileListP12" :data="form" style="text-align: left;">
					<i class="el-icon-upload"></i>
					<el-button size="small" type="primary">点击上传</el-button>
					<!--<div slot="tip" class="el-upload__tip">只能文件，且不超过500kb</div>-->
				</el-upload>
			</el-form-item>
			<!--	
			<el-form-item label="打包时间" prop="filePackageTime">
				<el-date-picker v-model="dataForm.filePackageTime" placeholder="打包时间"></el-date-picker>
			</el-form-item>
			-->
			<el-form-item label="profile文件" prop="profileContent">
				<!--<el-input v-model="dataForm.profileContent" placeholder="profile文件内容" style="text-align: left;width:200px;" readOnly></el-input>-->
				<el-upload class="upload-demo" :action="UploadUrlProfile()" :limit="1" :before-upload="beforeUploadHandleProfile"
				 :on-success="successHandleProfile" multiple :file-list="fileListProfile" :data="form" style="text-align: left;">
					<i class="el-icon-upload"></i>
					<el-button size="small" type="primary">点击上传</el-button>
					<!--<div slot="tip" class="el-upload__tip">只能文件，且不超过500kb</div>-->
				</el-upload>
			</el-form-item>
			<el-form-item label="证书密码" prop="pwd">
				<el-input v-model="dataForm.pwd" placeholder="证书密码"></el-input>
			</el-form-item>
			<el-form-item label="到期时间" prop="expireTime">
				<el-date-picker type="datetime" v-model="dataForm.expireTime" placeholder="到期时间"></el-date-picker>
			</el-form-item>
			<!--
			<el-form-item label="打包的文件地址" prop="fileUrl">
				<el-input type="text" v-model="dataForm.fileUrl" placeholder="打包地址" :disabled="true"></el-input>
			</el-form-item>-->
			<el-form-item label="备注" prop="remark">
				<el-input v-model="dataForm.remark" placeholder="备注"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<!--
			<el-button @click="goPackage()">打包</el-button>-->
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
				fileListP12: [],
				fileListProfile: [],
				form: null,
				dataForm: {
					id: 0,
					platform: '',
					certificateName: '',
					fileName: '',
					expireTime: '',
					filePackageTime: '',
					p12Content: '',
					profileContent: '',
					pwd: '',
					fileUrl: '',
					remark: '',
				},
				dataRule: {
					expireTime: [{
						required: true,
						message: '到期时间不能为空',
						trigger: 'blur'
					}],
					certificateName: [{
						required: true,
						message: '证书别名不能为空',
						trigger: 'blur'
					}],
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				if (!this.dataForm.id) {
					this.dataForm.filePackageTime = null;
					this.dataForm.expireTime = null;
				}
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/webhomefileupload/webhomefileupload/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.platform = data.webhomefileupload.platform
								this.dataForm.certificateName = data.webhomefileupload.certificateName
								this.dataForm.fileName = data.webhomefileupload.fileName
								this.dataForm.filePackageTime = data.webhomefileupload.filePackageTime
								this.dataForm.expireTime = data.webhomefileupload.expireTime
								this.dataForm.p12Content = data.webhomefileupload.p12Content
								this.dataForm.profileContent = data.webhomefileupload.profileContent
								this.dataForm.pwd = data.webhomefileupload.pwd
								this.dataForm.fileUrl = data.webhomefileupload.fileUrl
								this.dataForm.remark = data.webhomefileupload.remark
							}
						})
					}
				})
			},
			//P12文件上传
			UploadUrlP12: function() {
				this.url = this.$http.adornUrl(
					`/webhomefileupload/webhomefileupload/uploadFileP12?token=${this.$cookie.get('token')}`)
				return this.url;
			},
			beforeUploadHandleP12(file) {
				const isP12 = file.type === 'application/x-pkcs12';
				//console.log("文件后缀名"+file.type);
				if (!isP12) {
					this.$message.error('上传文件只能是 x-pkcs12 类型!');
				}
				return isP12;
			},
			successHandleP12(response, file, fileList, type) {
				this.type = type
				this.fileList = fileList
				this.successNum++
				if (response && response.code === 200) {
					if (this.num === this.successNum) {
						this.$message({
							message: '操作成功',
							type: 'success',
							duration: 1500,
						})
					}
					this.dataForm.p12Content = response.p12Content
				} else {
					this.$message.error(response.msg)
				}
			},
			//Profile文件上传
			UploadUrlProfile: function() {
				this.url = this.$http.adornUrl(
					`/webhomefileupload/webhomefileupload/uploadFileProfile?token=${this.$cookie.get('token')}`)
				return this.url;
			},
			beforeUploadHandleProfile(file) {
				var nameArray=file.name.split(".")
				//console.log("文件"+nameArray[1]);
				const isProfile = nameArray[1] === 'mobileprovision';
				if (!isProfile) {
					this.$message.error('上传的文件只能是 mobileprovision 后缀名的文件!');
				}
				return isProfile;
			},
			successHandleProfile(response, file, fileList, type) {
				this.type = type
				this.fileList = fileList
				this.successNum++
				if (response && response.code === 200) {
					if (this.num === this.successNum) {
						this.$message({
							message: '操作成功',
							type: 'success',
							duration: 1500,
						})
					}
					this.dataForm.profileContent = response.profileContent
				} else {
					this.$message.error(response.msg)
				}
			},
			//调用第三方接口 获取文件打包地址
			goPackage() {
				//this.$refs['dataForm'].resetFields()//重置当前窗口的所有参数
				/*if (this.dataForm.id) {
					this.$http({
						url: this.$http.adornUrl(`/webhomefileupload/webhomefileupload/goPackage/${this.dataForm.id}`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.dataForm.platform = data.webhomefileupload.platform
							this.dataForm.fileName = data.webhomefileupload.fileName
							this.dataForm.filePackageTime = data.webhomefileupload.filePackageTime
							this.dataForm.p12Content = data.webhomefileupload.p12Content
							this.dataForm.profileContent = data.webhomefileupload.profileContent
							this.dataForm.pwd = data.webhomefileupload.pwd
							this.dataForm.fileUrl = data.webhomefileupload.fileUrl
						}
					})
				}*/
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/webhomefileupload/webhomefileupload/goPackage`),
							method: 'post',
							data: this.$http.adornData({
								'platform': this.dataForm.platform,
								'fileName': this.dataForm.fileName,
								'filePackageTime': this.dataForm.filePackageTime,
								'p12Content': this.dataForm.p12Content,
								'profileContent': this.dataForm.profileContent,
								'pwd': this.dataForm.pwd,
								'fileUrl': this.dataForm.fileUrl,
							})
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.platform = data.webhomefileupload.platform
								this.dataForm.fileName = data.webhomefileupload.fileName
								this.dataForm.filePackageTime = data.webhomefileupload.filePackageTime
								this.dataForm.p12Content = data.webhomefileupload.p12Content
								this.dataForm.profileContent = data.webhomefileupload.profileContent
								this.dataForm.pwd = data.webhomefileupload.pwd
								this.dataForm.fileUrl = data.webhomefileupload.fileUrl
							} else {
								this.$message.error(data.msg)
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
							url: this.$http.adornUrl(`/webhomefileupload/webhomefileupload/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'platform': this.dataForm.platform,

								'certificateName': this.dataForm.certificateName,

								'fileName': this.dataForm.fileName,

								'expireTime': this.dataForm.expireTime,

								'filePackageTime': this.dataForm.filePackageTime,

								'p12Content': this.dataForm.p12Content,

								'profileContent': this.dataForm.profileContent,

								'pwd': this.dataForm.pwd,

								'fileUrl': this.dataForm.fileUrl,

								'remark': this.dataForm.remark,
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
