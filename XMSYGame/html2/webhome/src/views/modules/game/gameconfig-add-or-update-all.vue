<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
			<el-form-item label="游戏id" prop="gameId">
				<el-select v-model="dataForm.gameId" :disabled="isEdit" placeholder="请选择" @change="selectDataByGameId()">
					<el-option v-for="item in gameList" :key="item.id" :label="item.gameName+'-'+item.gradeName" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="初始库存" prop="initialStock">
				<el-input v-model="dataForm.initialStock" placeholder="初始库存"></el-input>
			</el-form-item>
			<el-form-item label="当前库存" prop="currentStock">
				<el-input v-model="dataForm.currentStock" placeholder="当前库存"></el-input>
			</el-form-item>
			<el-form-item label="目标库存" prop="goalStock">
				<el-input v-model="dataForm.goalStock" placeholder="目标库存"></el-input>
			</el-form-item>
			<el-form-item label="最多机器人数量" prop="maxRobot">
				<el-input v-model="dataForm.maxRobot" placeholder="最多机器人数量"></el-input>
			</el-form-item>
			<el-form-item label="小游戏胜率" prop="miniRate">
				<el-input v-model="dataForm.miniRate" placeholder="小游戏胜率"></el-input>
			</el-form-item>
			<el-form-item label="机器人等待时间" prop="robotWait">
				<el-input v-model="dataForm.robotWait" placeholder="机器人等待时间"></el-input>
			</el-form-item>
			<el-form-item label="累计库存" prop="cumulativeStock">
				<el-input v-model="dataForm.cumulativeStock" placeholder="累计库存"></el-input>
			</el-form-item>
			<el-form-item label="盈利值" prop="profitValue">
				<el-input v-model="dataForm.profitValue" placeholder="盈利值"></el-input>
			</el-form-item>
			<el-form-item label="限红最大值" prop="limitRedMax">
				<el-input v-model="dataForm.limitRedMax" placeholder="限红最大值"></el-input>
			</el-form-item>
			<el-form-item label="vip限红" prop="vipLimitRed">
				<el-input v-model="dataForm.vipLimitRed" placeholder="vip限红"></el-input>
			</el-form-item>
			<el-form-item label="筹码最小值" prop="chipMin">
				<el-input v-model="dataForm.chipMin" placeholder="筹码最小值"></el-input>
			</el-form-item>
			<el-form-item label="筹码最大值" prop="chipMax">
				<el-input v-model="dataForm.chipMax" placeholder="筹码最大值"></el-input>
			</el-form-item>
			<el-form-item label="十个筹码值" prop="tenChips">
				<el-input v-model="dataForm.tenChips" placeholder="十个筹码值"></el-input>
			</el-form-item>
			<el-form-item label="区域大小" prop="valArray">
				<el-input-number v-model="areaSize" controls-position="right" :min="1" :max="15" @change="updateValArray()"></el-input-number>
				<span v-for="item in areaSize" style="width: 20px;">
					<el-row :gutter="5">
						<el-col :span="5">
							<el-input v-model="valArray[item-1]" placeholder="区域属性值"></el-input>
						</el-col>
					</el-row>
				</span>
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
			//验证正整数
			var validateVal = (rule, value, callback) => {
				if(this.dataForm.tenChips!=''){
					var res = /^\+?[1-9][0-9]*$/;
					var resValue = value;
					while (resValue.indexOf(",") != -1) {
						resValue = resValue.replace(",", "");
					}
					if (!res.test(resValue)) {
						callback(new Error('格式有误'));
					}
				}
				callback();
			};
			//验证可输入2位小数的整实数
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
			//正整数
			var checkInt = (rule, value, callback) => {
				if (value) {
					var retgex = /^\+?[1-9][0-9]*$/;
					if (!retgex.test(value)) {
						callback(new Error('请输入非零的正整数'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			return {
				visible: false,
				gameList: [],
				isEdit: false,
				isInterval: false,
				moneyRate: 100,
				rate: 100,
				areaSize: "",
				valArray: [],
				valArrayback: [],
				dataForm: {
					id: 0,
					gameId: '',
					tenChips:'',
					miniRate:50
				},
				dataRule: {
					gameId: [{
						required: true,
						message: '游戏id不能为空',
						trigger: 'blur'
					}],
					initialStock: [{
						required: true,
						validator: checkAmount,
						message: '最多两位数的正数',
						trigger: 'blur'
					}],
					currentStock: [{
						required: true,
						validator: checkAmount,
						message: '最多两位数的正数',
						trigger: 'blur'
					}],
					goalStock: [{
						required: true,
						validator: checkAmount,
						message: '最多两位数的正数',
						trigger: 'blur'
					}],
					cumulativeStock: [{
						required: true,
						validator: checkAmount,
						message: '最多两位数的正数',
						trigger: 'blur'
					}],
					maxRobot: [{
						required: true,
						validator: checkInt,
						message: '请输入正整数',
						trigger: 'blur'
					}],
					robotWait: [{
						required: true,
						validator: checkAmount,
						message: '最多两位数的正数',
						trigger: 'blur'
					}],
					miniRate: [{
						required: true,
						message: '小游戏胜率不能为空（50代表百分50）',
						trigger: 'blur'
					},{
						required: true,
						validator: checkInt,
						message: '请输入正整数(50代表百分50)',
						trigger: 'blur'
					}],
					profitValue: [{
						required: true,
						validator: checkAmount,
						message: '最多两位数的正数',
						trigger: 'blur'
					}],
					limitRedMax: [{
						required: true,
						validator: checkInt,
						message: '请输入正整数',
						trigger: 'blur'
					}],
					vipLimitRed: [{
						required: true,
						validator: checkInt,
						message: '请输入正整数',
						trigger: 'blur'
					}],
					chipMin: [{
						required: true,
						validator: checkInt,
						message: '请输入正整数',
						trigger: 'blur'
					}],
					chipMax: [{
						required: true,
						validator: checkInt,
						message: '请输入正整数',
						trigger: 'blur'
					}],
					tenChips:[{
						required: true,
						validator: validateVal,
						message: '请输入正整数或者包含,分隔符的正整数',
						trigger: 'blur'
					}],
				}
			}
		},
		methods: {
			init(gameId, name) {
				this.areaSize = 1,
					this.valArray = [],
					this.dataForm.gameId = gameId || ''
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					this.$http({
						url: this.$http.adornUrl(`/info/info/GameList`),
						method: 'post',
						data: this.$http.adornData()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.gameList = data.gameList
						}
					})
				})
				//有gameId的话
				if(this.dataForm.gameId){
					this.$http({
						url: this.$http.adornUrl(`/gameconfig/gameconfig/selectDataByGameId/${this.dataForm.gameId}`),
						method: 'post',
						data: this.$http.adornData()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.dataForm.gameId = parseInt(data.map.gameId)//必须要有
							//这四个库存要除以100显示
							if (data.map.currentStock != null) {
								this.dataForm.currentStock = Number((data.map.currentStock / this.moneyRate).toFixed(2))
							} else {
								this.dataForm.currentStock = ''
							}
							if (data.map.goalStock != null) {
								this.dataForm.goalStock = Number((data.map.goalStock / this.moneyRate).toFixed(2))
							} else {
								this.dataForm.goalStock = ''
							}
							if (data.map.initialStock != null) {
								this.dataForm.initialStock = Number((data.map.initialStock / this.moneyRate).toFixed(2))
							} else {
								this.dataForm.initialStock = ''
							}
							if (data.map.cumulativeStock != null) {
								this.dataForm.cumulativeStock = Number((data.map.cumulativeStock / this.moneyRate).toFixed(2))
							} else {
								this.dataForm.cumulativeStock = ''
							}
							//小游戏胜率要*100
							if (data.map.miniRate != null) {
								this.dataForm.miniRate = Number((data.map.miniRate * this.rate).toFixed(2))
							} else {
								this.dataForm.miniRate = 50
							}
							//不同下注区域要转化为数组显示
							if (data.map.differentBetArea != null) {
								this.valArray = data.map.differentBetArea.split(",") //字符串转数组
								this.areaSize = this.valArray.length //数组长度
							} else {
								this.areaSize = 1
								this.valArray = []
							}
							this.dataForm.maxRobot = data.map.maxRobot
							this.dataForm.robotWait = data.map.robotWait
							this.dataForm.profitValue = data.map.profitValue
							this.dataForm.limitRedMax = data.map.limitRedMax
							this.dataForm.vipLimitRed = data.map.vipLimitRed
							this.dataForm.chipMin = data.map.chipMin
							this.dataForm.chipMax = data.map.chipMax
							if(data.map.tenChips!=null){
								this.dataForm.tenChips = data.map.tenChips
							}else{
								this.dataForm.tenChips=''
							}
							
					
						}
					})
				}
				
			},
			updateValArray() { //重新给数组赋值
				this.valArrayback = [];
				//console.log(this.areaSize)
				for (var i = 0; i < this.areaSize; i++) {
					this.valArrayback[i] = this.valArray[i]
				}
				this.valArray = this.valArrayback
			},
			selectName(val) { //change 触发事件
				if (!this.isEdit) {
					for (var i = 0; i < this.dictionaryList.length; i++) {
						if (val == this.dictionaryList[i].code) {
							this.dataForm.description = this.dictionaryList[i].name;
							break;
						}
					}
				}
			},
			selectDataByGameId() { //去查询当前选中gameId的所有数据并赋值
				//console.log(this.dataForm.gameId);
				this.$http({
					url: this.$http.adornUrl(`/gameconfig/gameconfig/selectDataByGameId/${this.dataForm.gameId}`),
					method: 'post',
					 data: this.$http.adornData()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataForm.gameId = parseInt(data.map.gameId)//必须要有
						//这四个库存要除以100显示
						if (data.map.currentStock != null) {
							this.dataForm.currentStock = Number((data.map.currentStock / this.moneyRate).toFixed(2))
						} else {
							this.dataForm.currentStock = ''
						}
						if (data.map.goalStock != null) {
							this.dataForm.goalStock = Number((data.map.goalStock / this.moneyRate).toFixed(2))
						} else {
							this.dataForm.goalStock = ''
						}
						if (data.map.initialStock != null) {
							this.dataForm.initialStock = Number((data.map.initialStock / this.moneyRate).toFixed(2))
						} else {
							this.dataForm.initialStock = ''
						}
						if (data.map.cumulativeStock != null) {
							this.dataForm.cumulativeStock = Number((data.map.cumulativeStock / this.moneyRate).toFixed(2))
						} else {
							this.dataForm.cumulativeStock = ''
						}
						//小游戏胜率要*100
						if (data.map.miniRate != null) {
							this.dataForm.miniRate = Number((data.map.miniRate * this.rate).toFixed(2))
						} else {
							this.dataForm.miniRate = 50
						}
						//不同下注区域要转化为数组显示
						if (data.map.differentBetArea != null) {
							this.valArray = data.map.differentBetArea.split(",") //字符串转数组
							this.areaSize = this.valArray.length //数组长度
						} else {
							this.areaSize = 1
							this.valArray = []
						}
						this.dataForm.maxRobot = data.map.maxRobot
						this.dataForm.robotWait = data.map.robotWait
						this.dataForm.profitValue = data.map.profitValue
						this.dataForm.limitRedMax = data.map.limitRedMax
						this.dataForm.vipLimitRed = data.map.vipLimitRed
						this.dataForm.chipMin = data.map.chipMin
						this.dataForm.chipMax = data.map.chipMax
						if(data.map.tenChips!=null){
							this.dataForm.tenChips = data.map.tenChips
						}else{
							this.dataForm.tenChips=''
						}
						

					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.valArrayback = [];
				for (var i = 0; i < this.areaSize; i++) {
					this.valArrayback[i] = this.valArray[i]
				}
				this.valArray = this.valArrayback //这里重新赋值在于每次变更时，this.areaSize总会比操作的少一步，这里再做一步就正常
				//console.log(this.valArray+"数组")
				var valString = this.valArray.join(","); //数组转为字符串
				//console.log(valString+"字符串")

				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						var val = 0;
						if (this.dataForm.name == "currentStock" || this.dataForm.name == "goalStock" ||
							this.dataForm.name == "initialStock" || this.dataForm.name == "cumulativeStock") {
							val = this.dataForm.val * this.moneyRate
						} else if (this.dataForm.name == "intervalGameRate" || this.dataForm.name == "miniRate") {
							val = this.dataForm.val / this.rate
						} else if (this.dataForm.name == "differentBetArea") {
							val = valString
							console.log(val)
						} else {
							val = this.dataForm.val
						}
						this.$http({
							url: this.$http.adornUrl(`/gameconfig/gameconfig/saveOrUpdateAll`),
							method: 'post',
							data: this.$http.adornData({
								'gameId': this.dataForm.gameId,
								'initialStock':this.dataForm.initialStock * this.moneyRate,
								'currentStock':this.dataForm.currentStock * this.moneyRate,
								'goalStock':this.dataForm.goalStock * this.moneyRate ,
								'maxRobot':this.dataForm.maxRobot ,
								'miniRate':this.dataForm.miniRate / this.rate,
								'robotWait':this.dataForm.robotWait ,
								'cumulativeStock':this.dataForm.cumulativeStock * this.moneyRate,
								'profitValue':this.dataForm.profitValue ,
								'limitRedMax':this.dataForm.limitRedMax ,
								'vipLimitRed':this.dataForm.vipLimitRed ,
								'chipMin':this.dataForm.chipMin ,
								'chipMax':this.dataForm.chipMax ,
								'differentBetArea':valString ,
								'tenChips':this.dataForm.tenChips ,
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
