<template>
	<el-dialog :title=" '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<div style="text-align:center">产品编码</div>
			<el-form-item label="alipay" prop="alipay">
				<el-input v-model="dataForm.alipay" placeholder="alipay"></el-input>
			</el-form-item>
			<el-form-item label="weixin" prop="weixin">
				<el-input v-model="dataForm.weixin" placeholder="weixin"></el-input>
			</el-form-item>
			<el-form-item label="unionpay" prop="unionpay">
				<el-input v-model="dataForm.unionpay" placeholder="unionpay"></el-input>
			</el-form-item>
			<el-form-item label="quickpay" prop="quickpay">
				<el-input v-model="dataForm.quickpay" placeholder="quickpay"></el-input>
			</el-form-item>
			<el-form-item label="qqpay" prop="qqpay">
				<el-input v-model="dataForm.qqpay" placeholder="qqpay"></el-input>
			</el-form-item>
			<el-form-item label="jindongpay" prop="jindongpay">
				<el-input v-model="dataForm.jindongpay" placeholder="jindongpay"></el-input>
			</el-form-item>
			<div style="text-align:center">密钥</div>
			<el-form-item label="uid" prop="uid">
				<el-input v-model="dataForm.uid" placeholder="uid"></el-input>
			</el-form-item>
			<el-form-item label="secret" prop="secret">
				<el-input v-model="dataForm.secret" placeholder="secret"></el-input>
			</el-form-item>
			<el-form-item label="publicKey" prop="publicKey">
				<el-input v-model="dataForm.publicKey" placeholder="publicKey"></el-input>
			</el-form-item>
			<el-form-item label="privateKey" prop="privateKey">
				<el-input v-model="dataForm.privateKey" placeholder="privateKey"></el-input>
			</el-form-item>
			<!--
			<el-form-item label="回调地址" prop="callbackUrl">
				<el-input v-model="dataForm.callbackUrl" placeholder="回调地址"></el-input>
			</el-form-item>-->
			<el-form-item label="支付地址" prop="payUrl">
				<el-input v-model="dataForm.payUrl" placeholder="支付地址"></el-input>
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
				gameOptions: [],
				dataForm: {
					name:'',
					aliasName: '',
					alipay: '', //6个产品编码字段
					weixin: '',
					unionpay: '',
					quickpay: '',
					qqpay: '',
					jindongpay: '',
					uid: '', //4个秘钥
					secret: '',
					publicKey: '',
					privateKey: '',
					callbackUrl: '', //回调地址
					payUrl: '', //支付地址

					alipayId: '', //6个产品编码字段Id
					weixinId: '',
					unionpayId: '',
					quickpayId: '',
					qqpayId: '',
					jindongpayId: '',
					uidId: '', //4个秘钥Id
					secretId: '',
					publicKeyId: '',
					privateKeyId: '',
					callbackUrlId: '', //回调地址Id
					payUrlId: '', //支付地址Id

				},
				dataRule: {
					/* gameId: [
            { required: true, message: '游戏ID不能为空', trigger: 'blur' }
          ],
					 sort: [
					  { required: true, message: '排序不能为空', trigger: 'blur' },
					]*/
				}
			}
		},
		methods: {
			init(aliasName,name) {
				this.dataForm.name = name
				this.dataForm.aliasName = aliasName
				console.log("init::::" + aliasName+"===公司名称:::"+name)
				this.visible = true
				//为游戏下拉获取数据

				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.aliasName) {
						this.$http({
							url: this.$http.adornUrl(`/sysconfig/sysconfig/sysConfigParam/${this.dataForm.aliasName}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.alipay = data.sysConfigPayParam.alipay
								this.dataForm.weixin = data.sysConfigPayParam.weixin
								this.dataForm.unionpay = data.sysConfigPayParam.unionpay
								this.dataForm.quickpay = data.sysConfigPayParam.quickpay
								this.dataForm.qqpay = data.sysConfigPayParam.qqpay
								this.dataForm.jindongpay = data.sysConfigPayParam.jindongpay
								this.dataForm.uid = data.sysConfigPayParam.uid
								this.dataForm.secret = data.sysConfigPayParam.secret
								this.dataForm.publicKey = data.sysConfigPayParam.publicKey
								this.dataForm.privateKey = data.sysConfigPayParam.privateKey
								this.dataForm.callbackUrl = data.sysConfigPayParam.callbackUrl
								this.dataForm.payUrl = data.sysConfigPayParam.payUrl

								if (data.sysConfigPayParam.alipayId != null) {
									this.dataForm.alipayId = data.sysConfigPayParam.alipayId
								} else {
									this.dataForm.alipayId = '';
								}
								if (data.sysConfigPayParam.weixinId != null) {
									this.dataForm.weixinId = data.sysConfigPayParam.weixinId
								} else {
									this.dataForm.weixinId = '';
								}
								if (data.sysConfigPayParam.unionpayId != null) {
									this.dataForm.unionpayId = data.sysConfigPayParam.unionpayId
								} else {
									this.dataForm.unionpayId = '';
								}
								if (data.sysConfigPayParam.quickpayId != null) {
									this.dataForm.quickpayId = data.sysConfigPayParam.quickpayId
								} else {
									this.dataForm.quickpayId = '';
								}
								if (data.sysConfigPayParam.qqpayId != null) {
									this.dataForm.qqpayId = data.sysConfigPayParam.qqpayId
								} else {
									this.dataForm.qqpayId = '';
								}
								if (data.sysConfigPayParam.jindongpayId != null) {
									this.dataForm.jindongpayId = data.sysConfigPayParam.jindongpayId
								} else {
									this.dataForm.jindongpayId = '';
								}

								if (data.sysConfigPayParam.uidId != null) {
									this.dataForm.uidId = data.sysConfigPayParam.uidId
								} else {
									this.dataForm.uidId = '';
								}
								if (data.sysConfigPayParam.secretId != null) {
									this.dataForm.secretId = data.sysConfigPayParam.secretId
								} else {
									this.dataForm.secretId = '';
								}
								if (data.sysConfigPayParam.publicKeyId != null) {
									this.dataForm.publicKeyId = data.sysConfigPayParam.publicKeyId
								} else {
									this.dataForm.publicKeyId = '';
								}
								if (data.sysConfigPayParam.privateKeyId != null) {
									this.dataForm.privateKeyId = data.sysConfigPayParam.privateKeyId
								} else {
									this.dataForm.privateKeyId = '';
								}

								if (data.sysConfigPayParam.callbackUrlId != null) {
									this.dataForm.callbackUrlId = data.sysConfigPayParam.callbackUrlId
								} else {
									this.dataForm.callbackUrlId = '';
								}
								if (data.sysConfigPayParam.payUrlId != null) {
									this.dataForm.payUrlId = data.sysConfigPayParam.payUrlId
								} else {
									this.dataForm.payUrlId = '';
								}
								/*console.log("alipayId:" + this.dataForm.alipayId + "--weixinId:" + this.dataForm.weixinId + "--unionpayId:" +
									this.dataForm.unionpayId + "--quickpayId:" + this.dataForm.quickpayId + "--qqpayId:" +
									this.dataForm.qqpayId + "--jindongpayId:" + this.dataForm.jindongpayId);
								console.log("uidId:" + this.dataForm.uidId + "--secretId:" + this.dataForm.secretId + "--publicKeyId:" +
									this.dataForm.publicKeyId + "--privateKeyId:" + this.dataForm.privateKeyId);
								console.log("callbackUrlId:" + this.dataForm.callbackUrlId + "--payUrlId:" + this.dataForm.payUrlId);*/
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					console.log("name:"+this.dataForm.name)
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/sysconfig/sysconfig/updateOrSaveSysConfigParam`),
							method: 'post',
							data: this.$http.adornData({
								"name": this.dataForm.name,//公司名称
								"aliasName": this.dataForm.aliasName,//别名
								"alipay": this.dataForm.alipay,
								"weixin": this.dataForm.weixin,
								"unionpay": this.dataForm.unionpay,
								"quickpay": this.dataForm.quickpay,
								"qqpay": this.dataForm.qqpay,
								"jindongpay": this.dataForm.jindongpay,
								"uid": this.dataForm.uid,
								"secret": this.dataForm.secret,
								"publicKey": this.dataForm.publicKey,
								"privateKey": this.dataForm.privateKey,
								"callbackUrl": this.dataForm.callbackUrl,
								"payUrl": this.dataForm.payUrl,
								"alipayId": this.dataForm.alipayId,
								"weixinId": this.dataForm.weixinId,
								"unionpayId": this.dataForm.unionpayId,
								"quickpayId": this.dataForm.quickpayId,
								"qqpayId": this.dataForm.qqpayId,
								"jindongpayId": this.dataForm.jindongpayId,
								"uidId": this.dataForm.uidId,
								"secretId": this.dataForm.secretId,
								"publicKeyId": this.dataForm.publicKeyId,
								"privateKeyId": this.dataForm.privateKeyId,
								"callbackUrlId": this.dataForm.callbackUrlId,
								"payUrlId": this.dataForm.payUrlId,

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
