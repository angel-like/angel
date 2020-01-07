<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
			<el-form-item label="发布地址" prop="publishUrl">
				<el-input v-model="dataForm.publishUrl" placeholder="发布地址"></el-input>
				<div><font color="red">http://或者 https://开头</font></div>
			</el-form-item>
			<el-form-item label="微信分享图片url" prop="shareWeixinUrl">
				<el-input v-model="dataForm.shareWeixinUrl" placeholder="微信分享图片url"></el-input>
				<div><font color="red">http://或者 https://开头,(宽640)</font></div>
			</el-form-item>
			<el-form-item label="苹果链接地址" prop="iosUrl">
				<el-input v-model="dataForm.iosUrl" placeholder="苹果链接地址"></el-input>
				<div><font color="red">http://或者 https://开头</font></div>
			</el-form-item>
			<el-form-item label="安卓链接地址" prop="androidUrl">
				<el-input v-model="dataForm.androidUrl" placeholder="安卓链接地址"></el-input>
				<div><font color="red">http://或者 https://开头</font></div>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">下载</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			return {
				loadPicture:false,
				visible: false,
				dataForm: {
					id: 0,
					publishUrl: '',
					shareWeixinUrl: '',
					iosUrl: '',
					androidUrl: ''

				},
				dataRule: {
					publishUrl: [{
						required: true,
						message: '发布地址不能为空',
						trigger: 'blur'
					}],
					shareWeixinUrl: [{
						required: true,
						message: '微信分享图片url不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id,publishUrl,iosUrl,androidUrl) {
				this.loadPicture=false
				this.dataForm.id = id || 0
				//console.log(this.dataForm.id)
				this.visible = true
				this.dataForm.publishUrl= publishUrl || ''//数据置空
				this.dataForm.shareWeixinUrl=''//数据置空
				this.dataForm.iosUrl= iosUrl || ''//数据置空
				this.dataForm.androidUrl= androidUrl || ''//数据置空
			},
			// 表单提交
			dataFormSubmit() {
				this.loadPicture=true
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/adchannelconfig/adchannelconfig/shareWeixin`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'publishUrl': this.dataForm.publishUrl,
								'shareWeixinUrl': this.dataForm.shareWeixinUrl,
								'androidUrl': this.dataForm.androidUrl,
								'iosUrl': this.dataForm.iosUrl,

							})
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								let a = document.createElement('a');
								a.setAttribute('href', data.urlDownload);
								a.setAttribute('target', '_blank');
								document.body.appendChild(a);
								//console.log(a)
								a.click();
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
