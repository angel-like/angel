<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
			<el-form-item label="游戏id" prop="gameId">
				<el-select v-model="dataForm.gameId" :disabled="isEdit" placeholder="请选择" @change="selectDataPerfectByGameId()">
					<el-option v-for="item in gameList" :key="item.id" :label="item.gameName+'-'+item.gradeName" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<div v-for="item in sysDictionaryList">
				<el-form-item :label="item.name" :prop="item.code" v-if="item.code!='differentBetArea'&&item.code!='intervalGameRate'">
					<el-input v-model="item.val"></el-input>
				</el-form-item>
			</div>
			<el-form-item label="下注区域倍数" prop="valArray" v-if='dataForm.roomId==3'>
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
				if (this.dataForm.tenChips != '') {
					var res = /^\+?[1-9][0-9]*$/;
					var resValue = value;
					if(resValue!=null&&resValue!=''){
						while (resValue.indexOf(",") != -1) {
							resValue = resValue.replace(",", "");
						}
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
				sysDictionaryList: [],
				rate: 100,
				areaSize: "",
				valArray: [],
				valArrayback: [],
				dataForm: {
					id: 0,
					gameId: '',
					tenChips: '',
					miniRate: 50,
					roomId:'',
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
					}/*, {
						required: true,
						validator: checkInt,
						message: '请输入正整数(50代表百分50)',
						trigger: 'blur'
					}*/],
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
					tenChips: [{
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
				//游戏列表
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
				if (this.dataForm.gameId) {
					this.isEdit=true,
					this.$http({
						url: this.$http.adornUrl(`/gameconfig/gameconfig/selectDataPerfectByGameId/${this.dataForm.gameId}`),
						method: 'post',
						data: this.$http.adornData()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.dataForm.gameId = parseInt(data.gameId) //必须要有
							this.dataForm.roomId=data.roomId//房间号
							this.sysDictionaryList = data.list
							
							var flag = true; //用来判断如果集合中不存在下注区域，就置空
							for (var i = 0; i < this.sysDictionaryList.length; i++) {
								//不同下注区域要转化为数组显示
								if (this.sysDictionaryList[i].code == "differentBetArea" && this.sysDictionaryList[i].val != null) {
									flag = false;
									this.valArray = this.sysDictionaryList[i].val.split(",") //字符串转数组
									this.areaSize = this.valArray.length //数组长度
									break;
								}
								//四个库存要 除以 100
								if (this.sysDictionaryList[i].code == "currentStock" || this.sysDictionaryList[i].code == "goalStock" ||
									this.sysDictionaryList[i].code == "initialStock" || this.sysDictionaryList[i].code == "cumulativeStock") {
									if (this.sysDictionaryList[i].val!=null && this.sysDictionaryList[i].val!='') {
										this.sysDictionaryList[i].val = this.sysDictionaryList[i].val / this.moneyRate
									}
								}
								//小游戏胜率*100
								if (this.sysDictionaryList[i].code == "miniRate") {
									if (this.sysDictionaryList[i].val!=null && this.sysDictionaryList[i].val!='') {
										this.sysDictionaryList[i].val = this.sysDictionaryList[i].val * this.moneyRate
									}
								}
								
							}
							if (flag) {
								this.areaSize = 1
								this.valArray = []
							}

						}
					})
				} else {
					/*//数据字典字段列表
					this.$nextTick(() => {
						this.$refs['dataForm'].resetFields()
						this.$http({
							url: this.$http.adornUrl(`/gameconfig/gameconfig/selectSysDictionary`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.sysDictionaryList = data.list
							}
						})
					})*/
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
			selectDataPerfectByGameId() { //去查询当前选中gameId的所有数据并赋值		
				//console.log(this.dataForm.gameId);
				this.$http({
					url: this.$http.adornUrl(`/gameconfig/gameconfig/selectDataPerfectByGameId/${this.dataForm.gameId}`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataForm.gameId = parseInt(data.gameId) //必须要有
						this.dataForm.roomId=data.roomId//房间号
						this.sysDictionaryList = data.list
						//不同下注区域要转化为数组显示
						var flag = true; //用来判断如果集合中不存在下注区域，就置空
						for (var i = 0; i < this.sysDictionaryList.length; i++) {
							if (this.sysDictionaryList[i].code == "differentBetArea" && this.sysDictionaryList[i].val != null) {
								flag = false;
								this.valArray = this.sysDictionaryList[i].val.split(",") //字符串转数组
								this.areaSize = this.valArray.length //数组长度
								break;
							}
							//四个库存要 除以 100
							if (this.sysDictionaryList[i].code == "currentStock" || this.sysDictionaryList[i].code == "goalStock" ||
								this.sysDictionaryList[i].code == "initialStock" || this.sysDictionaryList[i].code == "cumulativeStock") {
								if (this.sysDictionaryList[i].val!=null && this.sysDictionaryList[i].val!='') {
									this.sysDictionaryList[i].val = this.sysDictionaryList[i].val / this.moneyRate
								}
							}
							//小游戏胜率*100
							if (this.sysDictionaryList[i].code == "miniRate") {
								if (this.sysDictionaryList[i].val!=null && this.sysDictionaryList[i].val!='') {
									this.sysDictionaryList[i].val = this.sysDictionaryList[i].val * this.moneyRate
								}
							}
						}
						if (flag) {
							this.areaSize = 1
							this.valArray = []
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
				for (var i = 0; i < this.sysDictionaryList.length; i++) { //赋值到sysDictionaryList里
					if (this.sysDictionaryList[i].code == "differentBetArea") {
						this.sysDictionaryList[i].val = valString
						break;
					}
					//四个库存要*100  
					/*if (this.sysDictionaryList[i].code == "currentStock" || this.sysDictionaryList[i].code == "goalStock" ||
						this.sysDictionaryList[i].code == "initialStock" || this.sysDictionaryList[i].code == "cumulativeStock") {
						if (this.sysDictionaryList[i].val!=null && this.sysDictionaryList[i].val!='') {
							this.sysDictionaryList[i].val = this.sysDictionaryList[i].val * this.moneyRate
						}
					}*/
					//小游戏胜率除100  后端做校验
					/*if (this.sysDictionaryList[i].code == "miniRate") {
						if (this.sysDictionaryList[i].val!=null && this.sysDictionaryList[i].val!='') {
							this.sysDictionaryList[i].val = this.sysDictionaryList[i].val / this.moneyRate
						}
					}*/

				}
				console.log(this.dataForm.gameId)

				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/gameconfig/gameconfig/saveOrUpdatePerfect`),
							method: 'post',
							data: this.$http.adornData({
								'sysDictionaryListObject': this.sysDictionaryList
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
