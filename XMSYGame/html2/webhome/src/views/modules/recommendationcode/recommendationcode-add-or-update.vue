<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item v-if="!dataForm.id" label="会员" prop="userId">
				<el-select v-model="dataForm.userId" @change="selectGet" placeholder="请选择会员">
					<el-option v-for="item in userList" :key="item.id" :label="item.account" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<!--
		<el-form-item v-else label="会员账号" prop="userAccount">
			<el-input v-model="dataForm.userAccount" readonly placeholder="会员账号"></el-input>
		</el-form-item>-->
			<el-form-item label="邀请码" prop="recommendationCode">
				<el-input v-model="dataForm.recommendationCode" placeholder="邀请码" style="width: 500px;" :disabled="true"></el-input>
			</el-form-item>
			<el-form-item label="状态" prop="agentEnable">
				<el-switch v-model="dataForm.agentEnable" active-color="#13ce66"  active-text="开"  inactive-text="关" >
				</el-switch>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<!--<el-button type="primary" @click="dataFormSubmit()">确定</el-button>-->
			<el-button type="primary" @click="dataFormSubmitStatus()">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			var checkCode = (rule, value, callback) => {
				var retgex = /^[A-Za-z0-9]+$/;
				if (!retgex.test(value)) {
					callback(new Error('只能输入数字和字母'));
				} else {
					this.cheeckCodeUK(callback);
				}
			};
			return {
				visible: false,
				
				userList: [],
				dataForm: {
					id: 0,
					userId: '',
					userAccount: '',
					recommendationCode: '',
					oldRecommendationCode: '',
					agentEnable:'',
				},
				dataRule: {
					userId: [{
						required: true,
						message: '请选择会员',
						trigger: 'blur'
					}],
					recommendationCode: [{
							required: true,
							message: '请选择会员',
							trigger: 'blur'
						},
						{
							validator: checkCode,
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
					/*//用就卡死
					this.$http({
						url: this.$http.adornUrl(`/recommendationcode/recommendationcode/getNotRecommendationCodeUser`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.userList = data.userList
						}
					})*/
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/recommendationcode/recommendationcode/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.userId = data.recommendationCode.userId
								this.dataForm.userAccount = data.recommendationCode.userAccount
								this.dataForm.recommendationCode = data.recommendationCode.recommendationCode
								this.dataForm.oldRecommendationCode = data.recommendationCode.recommendationCode
								this.dataForm.agentEnable= data.recommendationCode.agentEnable
							}
						})
					}
				})
			},
			//下拉框选中事件
			selectGet(vId) { //这个vId也就是value值
				let obj = {};
				obj = this.userList.find((item) => { //这里的userList就是上面遍历的数据源
					return item.id === vId; //筛选出匹配数据
				});
				this.dataForm.userAccount = obj.account;
			},
			//检查邀请码唯一
			cheeckCodeUK(callback) { //
				this.$http({
					url: this.$http.adornUrl(
						`/recommendationcode/recommendationcode/checkCode/${this.dataForm.recommendationCode}/${this.dataForm.id}`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					console.log("checkCodeUK", data)
					if (data && data.code === 200) {
						if (!data.isOK) {
							callback("邀请码已经存在");
						} else {
							callback();
						}
					} else {
						callback("网络异常，请稍后尝试");
					}

				})
			},
			//提交状态
			dataFormSubmitStatus(){
				var userOperater = {};
				userOperater.memberId = this.dataForm.userId;
				userOperater.memberAccount = this.dataForm.userAccount;
				if(this.dataForm.agentEnable){
					userOperater.remark = "邀请码状态变更为开启";
				}else{
					userOperater.remark = "邀请码状态变更为关闭";
				}
				this.$http({
					url: this.$http.adornUrl(`/recommendationcode/recommendationcode/AgencyAuthority`),
					method: 'post',
					data: this.$http.adornData({
						'id': this.dataForm.id || undefined,
						'userId': this.dataForm.userId,
						'agentEnable': this.dataForm.agentEnable,
						'userOperater': userOperater
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
						var userOperater = {};
						userOperater.memberId = this.dataForm.userId;
						userOperater.memberAccount = this.dataForm.userAccount;
						if (this.dataForm.id) {
							if (this.dataForm.oldRecommendationCode == this.dataForm.recommendationCode) {
								this.$message({
									message: '邀请码没有变更',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.visible = false
										this.$emit('refreshDataList')
									}
								})
								return;
							}
							userOperater.remark = "邀请码由【" + this.dataForm.oldRecommendationCode +
								"】修改为【" + this.dataForm.recommendationCode + "】";
						} else {
							userOperater.remark = "新增邀请码为:" + this.dataForm.recommendationCode;
						}
						this.$http({
							url: this.$http.adornUrl(`/recommendationcode/recommendationcode/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'userId': this.dataForm.userId,
								'userAccount': this.dataForm.userAccount,
								'recommendationCode': this.dataForm.recommendationCode,
								'userOperater': userOperater
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
