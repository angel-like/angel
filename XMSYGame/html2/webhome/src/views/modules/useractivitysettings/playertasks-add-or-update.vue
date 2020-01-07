<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="玩家的任务类型" placement="top-start">
				<el-form-item label="任务类型" prop="type">
					<el-select v-model="dataForm.type" placeholder="任务类型 " @change="getTaskName()" clearable :disabled="disabledEnable">
						<el-option v-for="item in options" :key="item.code" :label="item.name" :value="item.code">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="福缘任务类型" placement="top-start">
				<el-form-item label="福缘任务类型" v-if="dataForm.type==3" prop="functionalType">
					<el-select v-model="dataForm.functionalType" placeholder="福缘任务类型 " @change="getTaskName()" clearable :disabled="disabledEnable">
						<el-option v-for="item in functionalTypeOptions" :key="item.code" :label="item.name" :value="item.code">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="什么类型的游戏房间" placement="top-start">
				<el-form-item label="房间类型" prop="roomId" v-if="dataForm.type!=3">
					<el-select v-model="dataForm.roomId" placeholder="房间类型 " @change="getGameSelect()" clearable :disabled="disabledEnable">
						<el-option v-for="item in roomOptions" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="房间类型下对应的游戏" placement="top-start">
				<el-form-item label="游戏类型" prop="gameId" v-if="dataForm.type!=3">
					<el-select v-model="dataForm.gameId" placeholder="游戏类型 " @change="getGradeSelect()" clearable :disabled="disabledEnable">
						<el-option v-for="item in gameOptions" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="游戏类型下对应的场次" placement="top-start">
				<el-form-item label="游戏场次" prop="gradeId" v-if="dataForm.type!=3">
					<el-select v-model="dataForm.gradeId" placeholder="游戏场次 " @change="getTaskName()" clearable :disabled="disabledEnable">
						<el-option v-for="item in gradeOptions" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="每个任务可完成次数" placement="top-start">
				<el-form-item label="任务次数" prop="taskNum">
					<el-input-number v-model="dataForm.taskNum" controls-position="right" :min="1" placeholder="任务次数"></el-input-number>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="任务标题" placement="top-start">
				<el-form-item label="标题" prop="title">
					<el-input v-model="dataForm.title" placeholder="标题"></el-input>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="获取的道具名称" placement="top-start">
				<el-form-item label="道具名称" prop="propId">
					<el-select v-model="dataForm.propId" placeholder="道具名称 " clearable>
						<el-option v-for="item in propOptions" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="获取的道具数量" placement="top-start">
				<el-form-item label="道具数量" prop="propNum">
					<el-input v-model="dataForm.propNum" placeholder="道具数量"></el-input>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="满足什么样的条件才能获取奖励" placement="top-start">
				<el-form-item label="条件" prop="condition">
					<el-input v-model="dataForm.condition" placeholder="条件"></el-input>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="满足什么样的对局要求" placement="top-start">
				<el-form-item label="对局要求" v-if="dataForm.type==1" prop="confrontationRequirement">
					<el-select v-model="dataForm.confrontationRequirement" placeholder="对局要求 "  @change="getTaskName()" clearable>
						<el-option v-for="item in confrontationOptions" :key="item.code" :label="item.name" :value="item.code">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<!--<el-tooltip class="item" effect="dark" content="周期(以多少天为一个周期)" placement="top-start">
				<el-form-item label="周期(天)" prop="cycle">
					<el-input-number v-model="dataForm.cycle" controls-position="right" :min="1" placeholder="周期(天)" :disabled="true"></el-input-number>
				</el-form-item>
			</el-tooltip>-->
			<el-tooltip class="item" effect="dark" content="为当前的房间设置一个序号" placement="top-start">
				<el-form-item label="排序" prop="sorts">
					<el-input-number v-model="dataForm.sorts" controls-position="right" :min="0" label="排序号"></el-input-number>
				</el-form-item>
			</el-tooltip>
			<!--<el-tooltip class="item" effect="dark" content="生效的时间" placement="top-start">
				<el-form-item label="生效时间" prop="effectTime">
					<el-date-picker v-model="dataForm.effectTime" type="datetime" :editable="false" format="yyyy-MM-dd HH:mm:ss"
					 value-format="yyyy-MM-dd HH:mm:ss" placeholder="生效时间" default-time="00:00:00" :disabled="true">
						  </el-date-picker>
				</el-form-item>
			</el-tooltip>-->
			<!--
       <el-tooltip class="item" effect="dark" content="失效的时间" placement="top-start">
      	<el-form-item label="失效时间" prop="failureTime">
      			<el-date-picker v-model="dataForm.failureTime" type="datetime"  :editable="false"
      			format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
      			placeholder="失效时间" default-time="00:00:00">
      	    </el-date-picker>
      	  </el-form-item>
      </el-tooltip>-->
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
			var checkFailureTime = (rule, value, callback) => {
				if (value && this.dataForm.effectTime) {
					if (value <= this.dataForm.effectTime) {
						callback(new Error('失效时间不能早于生效时间'));
					} else {
						callback();
					}

				} else {
					callback();
				}

			};

			//验证正整数
			var validateInteger = (rule, value, callback) => {
				var res = /^[0-9]*[1-9][0-9]*$/;
				if (value === '') {
					callback();
				} else {
					if (!res.test(value)) {
						callback(new Error('请输入正整数！'));
					} else {
						callback();
					}
				}
			};
			return {
				functionalTypeOptions: [],
				dataForm: {
					key: ''
				},
				confrontationOptions: [],
				options: [],
				optionsAll: [{
					"id": 0,
					"name": "全部"
				}],
				disabledEnable:false,
				gameOptions: [],
				gradeOptions:[],
				//roomOptions: [],
				roomOptions: [{
					id: 1,
					name: '匹配房间'
				}, {
					id: 3,
					name: '百人房间'
				}, {
					id: 4,
					name: '拉霸房间'
				}],
				propOptions: [],
				rate: 100,
				visible: false,
				dataForm: {
					id: 0,
					roomId: '',
					gameId: '',
					gradeId:'',
					title: '',
					type: '',
					propId: '',
					propNum: '',
					taskNum: 1,
					functionalType: '',
					condition: '',
					confrontationRequirement: '',
					cycle: 1,
					sorts: '',
					effectTime: this.defaultEffectTime(),
					failureTime: this.defaultFailureTime(),
				},
				dataRule: {
					roomId: [{
						required: true,
						message: '房间类型不能为空',
						trigger: 'blur'
					}],
					gameId: [{
						required: true,
						message: '游戏类型不能为空',
						trigger: 'blur'
					}],
					gradeId: [{
						required: true,
						message: '游戏厅次不能为空',
						trigger: 'blur'
					}],
					title: [{
						required: true,
						message: '标题不能为空',
						trigger: 'blur'
					}],
					type: [{
						required: true,
						message: '任务类型不能为空',
						trigger: 'blur'
					}],
					taskNum: [{
						required: true,
						message: '任务次数不能为空',
						trigger: 'blur'
					}],
					propId: [{
						required: true,
						message: '道具id不能为空',
						trigger: 'blur'
					}],
					propNum: [{
							required: true,
							message: '道具数量不能为空',
							trigger: 'blur'
						},
						{
							validator: validateInteger,
							trigger: 'blur'
						}
					],
					condition: [{
							required: true,
							message: '条件不能为空',
							trigger: 'blur'
						},
						{
							validator: validateInteger,
							trigger: 'blur'
						}
					],
					confrontationRequirement: [{
						required: true,
						message: '对局条件不能为空',
						trigger: 'blur'
					}],
					functionalType: [{
						required: true,
						message: '福缘任务类型不能为空',
						trigger: 'blur'
					}],
					cycle: [{
							required: true,
							message: '周期不能为空',
							trigger: 'blur'
						},
						{
							validator: validateInteger,
							trigger: 'blur'
						}
					],
					sorts: [{
						required: true,
						message: '排序不能为空',
						trigger: 'blur'
					}],
					effectTime: [{
						required: true,
						message: '生效时间不能为空',
						trigger: 'blur'
					}],
					failureTime: [{
							required: true,
							message: '失效时间不能为空',
							trigger: 'blur'
						},
						{
							validator: checkFailureTime,
							trigger: 'blur'
						}
					]
				}
			}
		},
		methods: {
			defaultEffectTime() {
				let date = new Date()
				let y = date.getFullYear()
				let m = date.getMonth() + 1
				let d = date.getDate()
				if (m < 10) {
					m = '0' + m
				}
				if (d < 10) {
					d = '0' + d
				}

				let time = y + '-' + m + '-' + d
				return time + " 00:00:00";
			},
			defaultFailureTime() {
				let date = new Date()
				let y = date.getFullYear()
				let m = date.getMonth() + 2
				let d = date.getDate()
				if (m < 10) {
					m = '0' + m
				}
				if (d < 10) {
					d = '0' + d
				}

				let time = y + '-' + m + '-' + d
				return time + " 00:00:00";
			},
			//房间下对应的游戏
			getGameSelect() {
				this.dataForm.gameId=null
				this.dataForm.gradeId=null
				this.gradeOptions=this.optionsAll
				if (this.dataForm.roomId == 0) {
					this.gameOptions = []//this.optionsAll
					return;
				}
				this.dataForm.gameId = null;
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelectForTask`),
					method: 'get',
					params: this.$http.adornParams({
						'roomId': this.dataForm.roomId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gameOptions = data.data
						//this.gameOptions =this.optionsAll.concat(data.data)
					} else {
						//this.gameOptions = this.optionsAll
					}
				});
			},
			//游戏下对应的不同厅次
			getGradeSelect() {
				this.dataForm.gradeId=null
				if (this.dataForm.gameId == 0) {
					this.gradeOptions = this.optionsAll
					return;
				}
				this.dataForm.gradeId = null;
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gradeSelectForTask`),
					method: 'get',
					params: this.$http.adornParams({
						'gameId': this.dataForm.gameId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gradeOptions = data.data
						this.gradeOptions =this.optionsAll.concat(data.data)
					} else{
						this.gradeOptions =this.optionsAll
					}
				});
			},
			//选择场次时  默认调用名字
			getTaskName(){
				return;//先不开启
				if(this.dataForm.type==3){//其他任务
					for(var i=0;i<this.functionalTypeOptions.length;i++){
						if(this.functionalTypeOptions[i].code==this.dataForm.functionalType){
							this.dataForm.title=this.functionalTypeOptions[i].name
						}
					}
				}
				if(this.dataForm.gameId==null || this.gameOptions.length<1){
					return
				}
				for(var i=0;i<this.gameOptions.length;i++){
					if(this.gameOptions[i].id==this.dataForm.gameId){
						//console.log(this.gameOptions[i].name)
						this.dataForm.title=this.gameOptions[i].name
					}
				}
				for(var i=0;i<this.gradeOptions.length;i++){
					if(this.gradeOptions[i].id==this.dataForm.gradeId){
						//console.log(this.gameOptions[i].name)
						this.dataForm.title=this.dataForm.title+"-"+this.gradeOptions[i].name
					}
				}
				if(this.dataForm.type==1){//福缘任务
					for(var i=0;i<this.confrontationOptions.length;i++){
						if(this.confrontationOptions[i].code==this.dataForm.confrontationRequirement){
							this.dataForm.title=this.confrontationOptions[i].name+":"+this.dataForm.title
						}
					}
				}else if(this.dataForm.type==2){//打码任务
					this.dataForm.title="打码:"+this.dataForm.title
				}else if(this.dataForm.type==3){//其他任务
					for(var i=0;i<this.functionalTypeOptions.length;i++){
						if(this.functionalTypeOptions[i].code==this.dataForm.functionalType){
							this.dataForm.title=this.functionalTypeOptions[i].name
						}
					}
				}
			},
			init(id) {
				this.dataForm.id = id || 0
				this.functionalType = 0
				this.visible = true
				this.dataForm.roomId=null
				this.gameOptions = []
				this.dataForm.gameId=null
				this.gradeOptions=this.optionsAll
				this.dataForm.gradeId=null
				this.disabledEnable=false
				this.dataForm.confrontationRequirement=null
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					//为任务下拉获取数据
					this.$http({
						url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "TaskType"),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.options = data.data
						}
					});
					//为对局下拉获取数据
					this.$http({
						url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "ConfrontationType"),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.confrontationOptions = data.data
						}
					});
					//为福缘任务下拉获取数据
					this.$http({
						url: this.$http.adornUrl(`/sysdictionary/sysdictionary/otherTaskTypeSelect/` + "transferFunction"),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.functionalTypeOptions = data.data
						}
					});
					//道具选择
					this.$http({
						url: this.$http.adornUrl(`/sysprop/sysprop/getProp`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.propOptions = data.propList
						}
					});

					/*this.$http({
						url: this.$http.adornUrl(`/gameinfo/gameinfo/roomSelect`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.roomOptions =this.optionsAll.concat(data.data)
						}else{
							this.roomOptions = this.optionsAll
						}
					});*/
					if (this.dataForm.id) {
						this.disabledEnable=true
						this.$http({
							url: this.$http.adornUrl(`/playertasks/playertasks/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.roomId = data.playertasks.roomId
								this.getGameSelect();
								this.dataForm.gameId = data.playertasks.gameId
								this.getGradeSelect();
								this.dataForm.gradeId = data.playertasks.gradeId
								this.dataForm.title = data.playertasks.title
								this.dataForm.type = data.playertasks.type.toString()
								this.dataForm.taskNum = data.playertasks.taskNum
								this.dataForm.functionalType = data.playertasks.functionalType
								this.dataForm.propId = data.playertasks.propId
								if (this.dataForm.propId == 1) {
									this.dataForm.propNum = data.playertasks.propNum / this.rate
								} else {
									this.dataForm.propNum = data.playertasks.propNum
								}
								if (this.dataForm.type == 2) { //富豪任务  打码量条件  也要/100
									this.dataForm.condition = Math.round(data.playertasks.condition / this.rate)
								} else {
									this.dataForm.condition = data.playertasks.condition
								}
								this.dataForm.confrontationRequirement = data.playertasks.confrontationRequirement.toString()
								this.dataForm.cycle = data.playertasks.cycle
								this.dataForm.sorts = data.playertasks.sorts
								this.dataForm.effectTime = data.playertasks.effectTime
								this.dataForm.failureTime = data.playertasks.failureTime
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				var ss = this.dataForm.propNum;
				if (this.dataForm.propId == 1) {
					ss = this.dataForm.propNum * this.rate
				}
				if (this.dataForm.type == 3) {
					this.dataForm.roomId = 0;
					this.dataForm.gameId = 0;
					this.dataForm.gradeId = 0;
					this.dataForm.confrontationRequirement=null
				}
				var conditionNum;
				if (this.dataForm.type == 2) { //富豪任务  打码量条件 提交时 要*100
					conditionNum = Math.round(this.dataForm.condition * this.rate)
					this.dataForm.confrontationRequirement=null
				} else {
					conditionNum = this.dataForm.condition
				}
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/playertasks/playertasks/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'roomId': this.dataForm.roomId,
								'gameId': this.dataForm.gameId,
								'gradeId': this.dataForm.gradeId,
								'title': this.dataForm.title,
								'type': this.dataForm.type,
								'taskNum': this.dataForm.taskNum,
								'functionalType': this.dataForm.functionalType,
								'propId': this.dataForm.propId,
								'propNum': ss,
								'condition': conditionNum,
								'confrontationRequirement': this.dataForm.confrontationRequirement,
								'cycle': this.dataForm.cycle,
								'sorts': this.dataForm.sorts,
								'effectTime': this.dataForm.effectTime,
								'failureTime': this.dataForm.failureTime,
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
