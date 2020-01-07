<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="兑换码兑换的金额单位:元" placement="top-start">
				<el-form-item label="兑换额度" prop="acountMoney">
					<el-input v-model="dataForm.acountMoney" placeholder="兑换额度"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="给会员兑换的凭证" placement="top-start">
				<el-form-item label="兑换码" prop="exchangeCode">
					<el-input v-model="dataForm.exchangeCode" placeholder="兑换码" ></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="密码有值时,会员兑换时需要密码;否则不需要" placement="top-start">
				<el-form-item label="密码" prop="password">
					<el-input v-model="dataForm.password" placeholder="密码"></el-input>
				</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="兑换码总的兑换次数" placement="top-start">
				<el-form-item label="兑换次数" prop="exchangeNum">
					<el-input-number v-model="dataForm.exchangeNum" controls-position="right" :min="1" label="兑换次数" ></el-input-number>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="指定兑换者账号,多个则用','隔开,如果没有指定账号,则对所有账号开放" placement="top-start">
				<el-form-item label="指定兑换者账号" prop="exchangeAccount">
					<el-input v-model="dataForm.exchangeAccount" placeholder="兑换者账号"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="单次就是只能兑换一次;多次就是根据一个周期内设置的兑换次数来兑换" placement="top-start">
				<el-form-item label="单个用户限制次数" v-if="dataForm.exchangeNum==1" prop="limitTimesUser">
					<el-radio-group v-model="dataForm.limitTimesUser"  @change="showContent()">
							<el-radio :label="false">单次</el-radio>
							<el-radio :label="true" disabled>多次</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="单次就是只能兑换一次;多次就是根据一个周期内设置的兑换次数来兑换" placement="top-start">
				<el-form-item label="单个用户限制次数" v-if="dataForm.exchangeNum!=1" prop="limitTimesUser">
					<el-radio-group v-model="dataForm.limitTimesUser"  @change="showContent()">
						<el-radio :label="false">单次</el-radio>
						<el-radio :label="true">多次</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="以多少天为一个周期" v-if="show" placement="top-start">
      	<el-form-item label="周期(天)" prop="period"  >
      			<el-input-number v-model="dataForm.period" :min="1" controls-position="right" label="周期(天)"></el-input-number>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="兑换码在一个周期内可以兑换多少次" v-if="show" placement="top-start">
      	<el-form-item label="可以领取次数" prop="receiveTimes" >
      		<el-input-number v-model="dataForm.receiveTimes" :min="1" controls-position="right" label="可以领取次数" ></el-input-number>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="兑换码什么时间生效" placement="top-start">
      	<el-form-item label="生效时间" prop="startTime">
      		<el-date-picker v-model="dataForm.startTime" type="datetime" placeholder="生效时间"></el-date-picker>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="兑换码什么时间失效" placement="top-start">
      	<el-form-item label="结束时间" prop="endTime">
      		<el-date-picker v-model="dataForm.endTime" type="datetime" placeholder="结束时间"></el-date-picker>
      	</el-form-item>
      </el-tooltip>

      <!--<el-form-item label="是否兑换" prop="exchange">
				<el-radio-group v-model="dataForm.exchange">
					<el-radio :label="true">是</el-radio>
					<el-radio :label="false">否</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="兑换者id" prop="exchangeId">
				<el-input v-model="dataForm.exchangeId" placeholder="兑换者id"></el-input>
			</el-form-item>

			<el-table-column prop="exchangeTotalNum" label="总兑换次数">
				<el-input-number v-model="dataForm.exchangeTotalNum" controls-position="right" :min="1" label="总兑换次数" ></el-input-number>
			</el-table-column>-->
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
					//var retgex=/^(1|1\.[0]*|0?\.(?!0+$)[\d]+)$/im;//限制0到1之间
					//var retgex = /^(\d(\.\d{1,2})?|10)$/; //限制0到10之间.最多4位小数
					var retgex = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^\d\.\d{1,2}$)/;
					if (!retgex.test(value)) {
						callback(new Error('请输入最多包含两位小数的正数'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			var checkPassword = (rule, value, callback) => {
				if (value) {
					var retgex = /^[a-zA-Z\d]{6,20}$/;
					if (!retgex.test(value)) {
						callback(new Error('请输入6到20位的数字或字母'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			var checkExchangeCode = (rule, value, callback) => {
				if (value) {
					var retgex = /^[0-9a-zA-Z]{10}$/; //输入数字及字母，且输入的字符的个数为10个字符
					if (!retgex.test(value)) {
						callback(new Error('输入数字及字母，且输入的字符的个数为10个字符'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			return {
				multiple:100,
				show:false,
				visible: false,
				dataForm: {
					id: 0,
					acountMoney: '',
					exchangeCode: '',
					password: '',
					//exchange: '',
					exchangeTotalNum:'',
					exchangeNum: '',
					exchangeAccount:'',
					limitTimesUser:false,
					period:1,
					receiveTimes:1,
					startTime: '',
					endTime: '',
				},
				dataRule: {
					acountMoney: [{
							required: true,
							message: '金额不能为空',
							trigger: 'blur'
						},
						{
							validator: checkAmount,
							trigger: 'blur'
						}
					],
					exchangeCode: [{
							required: true,
							message: '兑换码不能为空',
							trigger: 'blur'
						},
						{
							validator: checkExchangeCode,
							trigger: 'blur'
						}
					],
					password: [{
						validator: checkPassword,
						trigger: 'blur'
					}],
					/*exchange: [{
						required: true,
						message: '是否兑换不能为空',
						trigger: 'blur'
					}],
					exchangeTotalNum:[{
						required: true,
						message: '总兑换次数不能为空',
						trigger: 'blur'
					}],*/
					exchangeNum: [{
						required: true,
						message: '剩余兑换次数不能为空',
						trigger: 'blur'
					}],
					startTime: [{
						required: true,
						message: '生效时间不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				this.show = false
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/giftbagconfig/giftbagconfig/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.acountMoney = data.giftbagconfig.acountMoney/this.multiple
								this.dataForm.exchangeCode = data.giftbagconfig.exchangeCode
								this.dataForm.password = data.giftbagconfig.password
								this.dataForm.codeCapacity=data.giftbagconfig.codeCapacity
								this.dataForm.exchange = data.giftbagconfig.exchange
								//this.dataForm.exchangeTotalNum = data.giftbagconfig.exchangeTotalNum
								this.dataForm.exchangeNum = data.giftbagconfig.exchangeNum
								this.dataForm.exchangeAccount = data.giftbagconfig.exchangeAccount
								this.dataForm.limitTimesUser = data.giftbagconfig.limitTimesUser
								this.dataForm.period = data.giftbagconfig.period
								this.dataForm.receiveTimes = data.giftbagconfig.receiveTimes
								this.dataForm.startTime = data.giftbagconfig.startTime
								this.dataForm.endTime = data.giftbagconfig.endTime
								if (data.giftbagconfig.limitTimesUser==true) {
									this.show = true
								}
							}
						})
					}
				})
			},
			//由选择框触发显示哪部分
			showContent(){
				if(this.dataForm.limitTimesUser==false){//如果为单次
					this.show=false;
					this.dataForm.period=null;//把周期以及领取次数设为空
					this.dataForm.receiveTimes=null;
				}
				if(this.dataForm.limitTimesUser==true){
					this.show=true;
				}
			},
			// 表单提交
			dataFormSubmit() {

				this.$refs['dataForm'].validate((valid) => {

					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/giftbagconfig/giftbagconfig/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'acountMoney': this.dataForm.acountMoney*this.multiple,

								'exchangeCode': this.dataForm.exchangeCode,

								'password': this.dataForm.password,

								'codeCapacity':this.dataForm.codeCapacity,

								'exchange': this.dataForm.exchange,

								//'exchangeTotalNum': this.dataForm.exchangeTotalNum,

								'exchangeNum': this.dataForm.exchangeNum,

								'limitTimesUser':this.dataForm.limitTimesUser,

								'period': this.dataForm.period,

								'receiveTimes': this.dataForm.receiveTimes,

								'exchangeAccount': this.dataForm.exchangeAccount,

								'startTime': this.dataForm.startTime,

								'endTime': this.dataForm.endTime,

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
