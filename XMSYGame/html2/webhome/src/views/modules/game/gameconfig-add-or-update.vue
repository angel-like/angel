<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">

			<el-form-item label="游戏id" prop="gameId">
				<el-select v-model="dataForm.gameId" :disabled="isEdit" placeholder="请选择">
					<el-option v-for="item in gameList" :key="item.id" :label="item.gameName+'-'+item.gradeName" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="属性名称" prop="name">
				<el-select v-model="dataForm.name" :disabled="isEdit" @change="selectName" placeholder="请选择" style="width:300px">
					<el-option v-for="item in dictionaryList" :key="item.code" :label="item.code+'-'+item.name" :value="item.code">
					</el-option>
				</el-select>
				<!-- <el-input v-model="dataForm.name" :readOnly="isEdit" placeholder="属性名称"></el-input> -->
			</el-form-item>
			<el-form-item label="库存开始值" prop="startVal" v-if="dataForm.name=='intervalGameRate'">
				<el-input v-model="dataForm.startVal" placeholder="开始值有两位小数的数值"></el-input>
			</el-form-item>
			<el-form-item label="库存结束值" prop="endVal" v-if="dataForm.name=='intervalGameRate'">
				<el-input v-model="dataForm.endVal" placeholder="结束值有两位小数的数值"></el-input>
			</el-form-item>
			<el-form-item label="属性值" prop="val" v-if="dataForm.name!='differentBetArea'">
				<el-input v-model="dataForm.val" placeholder="属性值是整数或者有两位小数的数值或者包含逗号的整数"></el-input>
			</el-form-item>
			<el-form-item label="区域大小" prop="valArray" v-if="dataForm.name=='differentBetArea'">
				 <el-input-number  v-model="areaSize" controls-position="right" :min="1" :max="15" @change="updateValArray()"></el-input-number>
				 <span  v-for="item in areaSize" style="width: 20px;">
					 <el-row :gutter="5">
					 	<el-col :span="5">
							<el-input v-model="valArray[item-1]" placeholder="区域属性值" ></el-input>
						</el-col>
					</el-row>
				 </span>
			</el-form-item>
			<el-form-item label="属性描述" prop="description">
				<el-input v-model="dataForm.description" placeholder="属性描述"></el-input>
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
			/*var validateVal = (rule, value, callback) => {
				var res = /^(-?\d+)+(.[0-9]{0,2})?$/;
				if (value === '') {
					callback(new Error('不可为空'));
				} else {
					if (!res.test(value)) {
						callback(new Error('格式有误'));
					}
					callback();
				}
			}; */
			//验证正整数
			var validateVal = (rule, value, callback) => {
				if(this.dataForm.name=="tenChips"){
					var res = /^\+?[1-9][0-9]*$/;
					var resValue = value;
					while(resValue.indexOf(",") != -1){
						resValue = resValue.replace(",", "");
					}
					if (!res.test(resValue)) {
						callback(new Error('格式有误'));
					}
					callback();
				}else {
					//console.log(this.dataForm.name+"其他")
					var res = /^(-?\d+)(\.\d+)?$/;
					if (value === '') {
						callback(new Error('不可为空'));
					} else {
						if (!res.test(value)) {
							callback(new Error('格式有误'));
						}
						callback();
					}
				}

			};
			//验证可输入2位小数的整实数
			var validateRate = (rule, value, callback) => {
				var res = /^(-?\d+)+(.[0-9]{0,2})?$/;
				if (value === '') {
					callback(new Error('不可为空'));
				} else {
					if (!res.test(value)) {
						callback(new Error('格式有误'));
					}
					callback();
				}
			};
			return {
				visible: false,
				gameList: [],
				dictionaryList: [],
				isEdit: false,
				isInterval: false,
				moneyRate: 100,
				rate: 100,
				areaSize:"",
				valArray:[],
				valArrayback:[],
				dataForm: {
					id: 0,
					gameId: '',
					name: '',
					startVal: '',
					endVal: '',
					val: '',
					gameArray: '',
					description: '',
				},
				dataRule: {
					gameId: [{
						required: true,
						message: '游戏id不能为空',
						trigger: 'blur'
					}],
					name: [{
						required: true,
						message: '属性名称不能为空',
						trigger: 'blur'
					}],
					startVal: [{
						required: true,
						validator: validateRate,
						message: '请输入有两位小数的数值',
						trigger: 'blur'
					}, ],
					endVal: [{
						required: true,
						validator: validateRate,
						message: '请输入有两位小数的数值',
						trigger: 'blur'
					}, ],
					val: [{
						required: true,
						validator: validateVal,
						message: '属性值是整数或者有两位小数的数值或者包含逗号的整数',
						trigger: 'blur'
					}, ],
					description: [{
						required: true,
						message: '属性描述不能为空',
						trigger: 'blur'
					}],
				}
			}
		},
		// 		watch:{
		// 			visible(vla,old){
		// 				
		// 			},
		// 			"dataForm.val": {
		//         handler(vla,old){   //特别注意，不能用箭头函数，箭头函数，this指向全局
		// 				if(val!==''){
		// 					alert('++++++++++++++'+,old	)
		// 				}
		// 					console.log('-------------------'+vla)
		// 					console.log('+++++++++++++++++++++++++'+old)
		// 
		//         },
		//         deep: true    //深度监听
		// 			}
		// 
		// 		},
		methods: {
			init(id, name) {
				this.areaSize=1,
				this.valArray=[],
				this.dataForm.id = id || 0
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
					if (name != null && name != '') {
						this.dataForm.name = name;
						this.isInterval = true;
						this.dataForm.startVal = 0;
						this.dataForm.endVal = 0;
					} else {
						this.isInterval = false;
					}
					this.$http({
						url: this.$http.adornUrl(`/gameconfig/gameconfig/select/${this.isInterval}`),
						method: 'post',
						data: this.$http.adornData()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.dictionaryList = data.data;
						}
						if (this.isInterval) {
							this.selectName(name);
						}
					})

					if (this.dataForm.id) {
						this.isEdit = true;
						this.$http({
							url: this.$http.adornUrl(`/gameconfig/gameconfig/info/${this.dataForm.id}`),
							method: 'post',
							data: this.$http.adornData()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.gameId = parseInt(data.gameconfig.gameId)
								this.dataForm.name = data.gameconfig.name
								if (data.gameconfig.name == "currentStock" || data.gameconfig.name == "goalStock" ||
									data.gameconfig.name == "initialStock" || data.gameconfig.name == "cumulativeStock") {
									this.dataForm.val = Number((data.gameconfig.val / this.moneyRate).toFixed(2))
								} else if (data.gameconfig.name == "intervalGameRate" || data.gameconfig.name == "miniRate") {
									this.dataForm.val = Number((data.gameconfig.val * this.rate).toFixed(2))
								}else if(data.gameconfig.name =="differentBetArea"){
									this.dataForm.val=data.gameconfig.val
									this.valArray=data.gameconfig.val.split(",")//字符串转数组
									this.areaSize=this.valArray.length//数组长度
								} else {
									this.dataForm.val = data.gameconfig.val
								}
								this.dataForm.startVal = data.gameconfig.startVal / this.moneyRate
								this.dataForm.endVal = data.gameconfig.endVal / this.moneyRate
								this.dataForm.description = data.gameconfig.description
							}
						})
					} else {
						this.isEdit = false;
					}



				})
			},
			updateValArray(){//重新给数组赋值
				this.valArrayback=[];
				//console.log(this.areaSize)
				for(var i=0;i<this.areaSize;i++){
					this.valArrayback[i]=this.valArray[i]
				}
				this.valArray=this.valArrayback
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
			// 表单提交
			dataFormSubmit() {
				this.valArrayback=[];
				for(var i=0;i<this.areaSize;i++){
					this.valArrayback[i]=this.valArray[i]
				}
				this.valArray=this.valArrayback//这里重新赋值在于每次变更时，this.areaSize总会比操作的少一步，这里再做一步就正常
				//console.log(this.valArray+"数组")
				var valString=this.valArray.join(",");//数组转为字符串
				//console.log(valString+"字符串")
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						var val = 0;
						if (this.dataForm.name == "currentStock" || this.dataForm.name == "goalStock" ||
							this.dataForm.name == "initialStock" || this.dataForm.name == "cumulativeStock") {
							val = this.dataForm.val * this.moneyRate
						} else if (this.dataForm.name == "intervalGameRate" || this.dataForm.name == "miniRate") {
							val = this.dataForm.val / this.rate
						}else if (this.dataForm.name=="differentBetArea"){
							val=valString
							console.log(val)
						} else {
							val = this.dataForm.val
						}
						this.$http({
							url: this.$http.adornUrl(`/gameconfig/gameconfig/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'gameId': this.dataForm.gameId,
								'name': this.dataForm.name,
								'startVal': this.dataForm.startVal * this.moneyRate,
								'endVal': this.dataForm.endVal * this.moneyRate,
								'val': val,
								'gameArray': this.dataForm.gameArray,
								'description': this.dataForm.description,
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
