<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="方案名称" prop="productName">
				<el-input v-model="dataForm.productName" placeholder="方案名称"></el-input>
			</el-form-item>
			<el-form-item label="结算类型" prop="settlementType">
				<el-select v-model="dataForm.settlementType" placeholder="结算类型" clearable>
					<el-option v-for="item in options" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="最低金额" prop="minMoney">
				<el-input-number v-model="dataForm.minMoney" :min="1" placeholder="最低金额"></el-input-number>
			</el-form-item>
			<el-form-item label="最高金额" prop="maxMoney">
				<el-input-number v-model="dataForm.maxMoney" :min="1" placeholder="最高金额"></el-input-number>
			</el-form-item>
			<el-form-item label="利率" prop="rate">
				<el-input-number v-model="dataForm.rate" :min="0" :max="1" placeholder="利率"></el-input-number>
			</el-form-item>
			<el-form-item label="利息上限" prop="rateMoneyMax">
				<el-input-number v-model="dataForm.rateMoneyMax" :min="1" placeholder="利息上限"></el-input-number>
			</el-form-item>
			<!--
			<el-form-item label="剩余可买金额" prop="remaindBuyNum">
				<el-input-number v-model="dataForm.remaindBuyNum" :min="1" placeholder="剩余可买金额"></el-input-number>
			</el-form-item>-->
			<el-form-item label="打码倍数" prop="betMultiple">
				<el-input-number v-model="dataForm.betMultiple" :min="0" placeholder="打码倍数"></el-input-number>
			</el-form-item>
			<el-form-item label="会员当天可购买次数" prop="userTodayBuyNum">
				<el-input-number v-model="dataForm.userTodayBuyNum" :min="0" placeholder="会员当天可购买次数"></el-input-number>
			</el-form-item>
			<el-form-item label="发行金额" prop="issueNum">
				<el-input-number v-model="dataForm.issueNum" :min="0" placeholder="发行金额"  :disabled="issueFlag"></el-input-number>
			</el-form-item>
			<el-form-item label="结算周期" prop="settlementCycle">
				<el-input-number v-model="dataForm.settlementCycle" :min="1" placeholder="结算周期" :disabled="true"></el-input-number>
			</el-form-item>
			<!--
			<el-form-item label="结算时间" prop="issueTime" v-if="this.dataForm.settlementType==1">
				<el-date-picker v-model="dataForm.issueTime" type="date" placeholder="选择日期">
				</el-date-picker>
			</el-form-item>-->
			<el-form-item label="状态" prop="enable">
				<el-switch v-model="dataForm.enable" active-color="#13ce66" inactive-color="#ff4949">
				</el-switch>
			</el-form-item>
			<el-form-item label="排序" prop="orderNum">
				<el-input-number v-model="dataForm.orderNum" :min="0" placeholder="排序"></el-input-number>
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
			var checkAmount = (rule, value, callback) => {
				if (value) {
					if (value.length>8) {
						callback(new Error('限制输入最多8个汉字或字符'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			return {
				options: [{
					name: 0,
					label: '循环结算'
				}, {
					name: 1,
					label: '一次结算'
				}],
				visible: false,
				issueFlag:false,
				dataForm: {
					id: 0,
					productName: '',
					settlementType: '',
					minMoney: '',
					maxMoney: '',
					rate: '',
					rateMoneyMax: '',
					remaindBuyNum: '',
					betMultiple: '',
					userTodayBuyNum: '',
					issueNum: '',
					issueTime: '',
					settlementCycle:1,
					enable: true,
					orderNum: 0,
				},
				dataRule: {
					productName: [{
						required: true,
						message: '方案名称不能为空',
						trigger: 'blur'
					},
					{
						validator: checkAmount,
						trigger: 'blur'
					}],
					settlementType: [{
						required: true,
						message: '结算类型（0:循环结算，1本金结算）不能为空',
						trigger: 'blur'
					}],
					minMoney: [{
						required: true,
						message: '最低金额不能为空',
						trigger: 'blur'
					}],
					maxMoney: [{
						required: true,
						message: '最高金额不能为空',
						trigger: 'blur'
					}],
					rate: [{
						required: true,
						message: '利率不能为空',
						trigger: 'blur'
					}],
					rateMoneyMax: [{
						required: true,
						message: '利息上限不能为空',
						trigger: 'blur'
					}],
					/*remaindBuyNum: [{
						required: true,
						message: '剩余可买金额不能为空',
						trigger: 'blur'
					}],*/
					betMultiple: [{
						required: true,
						message: '打码倍数不能为空',
						trigger: 'blur'
					}],
					userTodayBuyNum: [{
						required: true,
						message: '会员当天可购买次数不能为空',
						trigger: 'blur'
					}],
					issueNum: [{
						required: true,
						message: '发行金额不能为空',
						trigger: 'blur'
					}],
					settlementCycle: [{
						required: true,
						message: '结算周期不能为空',
						trigger: 'blur'
					}],
					/*issueTime: [{
						required: true,
						message: '结算时间不能为空',
						trigger: 'blur'
					}],*/
					enable: [{
						required: true,
						message: '状态(0:禁用，1:启用)不能为空',
						trigger: 'blur'
					}],
					orderNum: [{
						required: true,
						message: '排序不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.issueFlag=false
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.issueFlag=true
						this.$http({
							url: this.$http.adornUrl(`/userbalanceproduct/userbalanceproduct/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.productName = data.userbalanceproduct.productName
								this.dataForm.settlementType = data.userbalanceproduct.settlementType
								this.dataForm.minMoney = data.userbalanceproduct.minMoney/100
								this.dataForm.maxMoney = data.userbalanceproduct.maxMoney/100
								this.dataForm.rate = data.userbalanceproduct.rate
								this.dataForm.rateMoneyMax = data.userbalanceproduct.rateMoneyMax/100
								this.dataForm.remaindBuyNum = data.userbalanceproduct.remaindBuyNum/100
								this.dataForm.betMultiple = data.userbalanceproduct.betMultiple
								this.dataForm.userTodayBuyNum = data.userbalanceproduct.userTodayBuyNum
								this.dataForm.issueNum = data.userbalanceproduct.issueNum/100
								this.dataForm.issueTime = data.userbalanceproduct.issueTime
								this.dataForm.settlementCycle = data.userbalanceproduct.settlementCycle
								this.dataForm.enable = data.userbalanceproduct.enable
								this.dataForm.orderNum = data.userbalanceproduct.orderNum
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
							url: this.$http.adornUrl(`/userbalanceproduct/userbalanceproduct/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'productName': this.dataForm.productName,

								'settlementType': this.dataForm.settlementType,

								'minMoney': this.dataForm.minMoney*100,

								'maxMoney': this.dataForm.maxMoney*100,

								'rate': this.dataForm.rate,

								'rateMoneyMax': this.dataForm.rateMoneyMax*100,

								'remaindBuyNum': this.dataForm.remaindBuyNum*100,

								'betMultiple': this.dataForm.betMultiple,

								'userTodayBuyNum': this.dataForm.userTodayBuyNum,

								'issueNum': this.dataForm.issueNum*100,

								'issueTime': this.dataForm.issueTime,
								
								'settlementCycle': this.dataForm.settlementCycle,
								
								'enable': this.dataForm.enable,

								'orderNum': this.dataForm.orderNum,

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
