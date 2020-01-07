<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<!--
			<el-form-item label="代理编号" prop="proxyNo">
				<el-input v-model="dataForm.proxyNo" placeholder="代理编号"></el-input>
			</el-form-item>
			<el-form-item label="代理别名" prop="proxyAlias">
				<el-input v-model="dataForm.proxyAlias" placeholder="代理别名"></el-input>
			</el-form-item>-->
			<el-form-item label="渠道信息" prop="channelCode">
				<el-select v-model="dataForm.channelCode" clearable placeholder="渠道信息">
					<el-option v-for="channelItem in channelConfigOptions" :key="channelItem.channelCode" :label="channelItem.channelCode+'-'+channelItem.channelName" :value="channelItem.channelCode">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="苹果签名" prop="iosName">
				<el-select v-model="dataForm.iosName" clearable placeholder="苹果签名">
					<el-option v-for="item in iosNameOptions" :key="item.certificateName" :label="item.certificateName" :value="item.certificateName">
					</el-option>
				</el-select>
			</el-form-item>
			<!--
			<el-form-item label="过期时间" prop="expireTime">
				<el-date-picker type="datetime" v-model="dataForm.expireTime" placeholder="过期时间"></el-date-picker>
			</el-form-item>-->
			<el-form-item label="更新地址" prop="updateUrl">
				<el-input v-model="dataForm.updateUrl" placeholder="更新地址"></el-input>
			</el-form-item>
			<el-form-item label="备注" prop="remark">
				<el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
				iosNameOptions:[],
				channelConfigOptions:[],
				dataForm: {
					id: 0,
					proxyNo: '',
					proxyAlias: '',
					channelCode:'',
					iosName: '',
					expireTime: '',
					updateUrl: '',
					remark: '',
				},
				dataRule: {
					/*proxyNo: [{
						required: true,
						message: '代理编号不能为空',
						trigger: 'blur'
					}],
					proxyAlias: [{
						required: true,
						message: '代理别名不能为空',
						trigger: 'blur'
					}],
					iosName: [{
						required: true,
						message: '苹果签名不能为空',
						trigger: 'blur'
					}],*/
					updateUrl: [{
						required: true,
						message: '更新地址不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				//获取苹果签名(证书别名)下拉选择
				this.$http({
					url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/selectIosName`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.iosNameOptions = data.iosNameList
					}
				});
				//获取渠道配置下拉选择
				this.$http({
					url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/selectChannelConfig`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.channelConfigOptions = data.channelConfigList
					}
				});
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.proxyNo = data.webhomefilepackage.proxyNo
								this.dataForm.proxyAlias = data.webhomefilepackage.proxyAlias
								this.dataForm.channelCode = data.webhomefilepackage.channelCode
								this.dataForm.channelName = data.webhomefilepackage.channelName
								this.dataForm.iosName = data.webhomefilepackage.iosName
								this.dataForm.expireTime = data.webhomefilepackage.expireTime
								this.dataForm.updateUrl = data.webhomefilepackage.updateUrl
								this.dataForm.remark = data.webhomefilepackage.remark
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				//console.log("---"+this.dataForm.channelCode);
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'proxyNo': this.dataForm.proxyNo,

								'proxyAlias': this.dataForm.proxyAlias,
								
								'channelCode': this.dataForm.channelCode,

								'iosName': this.dataForm.iosName,

								'expireTime': this.dataForm.expireTime,

								'updateUrl': this.dataForm.updateUrl,

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
