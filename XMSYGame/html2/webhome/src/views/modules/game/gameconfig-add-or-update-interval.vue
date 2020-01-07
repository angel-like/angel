<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">

			<el-form-item label="游戏id" prop="gameId">
				<el-select v-model="dataForm.gameId" :disabled="isEdit" placeholder="请选择" @change="selectIntervalByGameId()">
					<el-option v-for="item in gameList" :key="item.id" :label="item.gameName+'-'+item.gradeName" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="库存区间个数">
				<el-input-number v-model="areaSize" controls-position="right" :min="1" :max="15" @change="updateValArray()"></el-input-number>
				<div v-for="item in areaSize">
					<el-input v-model="startVal[item-1]" placeholder="开始值" style="width: 150px;"></el-input>
					~
					<el-input v-model="endVal[item-1]" placeholder="结束值" style="width: 150px;"></el-input>
					     游戏胜率：<el-input v-model="intervalGameRate[item-1]" placeholder="游戏胜率" style="width: 150px;"></el-input>
				</div>
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
				isEdit: false,
				isInterval: false,
				rate: 100,
				areaSize: 3,
				startVal: [],
				endVal: [],
				intervalGameRate: [],
				idArray:[],
				dataForm: {
					id: 0,
					gameId: '',
					name: '',
					gameArray: '',
					description: '',
				},
				dataRule: {
					gameId: [{
						required: true,
						message: '游戏id不能为空',
						trigger: 'blur'
					}]
				}
			}
		},

		methods: {
			init(gameId, name) {
				//console.log("游戏id:"+gameId)
				this.areaSize = 1,
				this.startVal = [],
				this.endVal = [],
				this.intervalGameRate = [],
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
					this.isEdit=true,
					this.$http({
						url: this.$http.adornUrl(`/gameconfig/gameconfig/selectIntervalByGameId/${this.dataForm.gameId}`),
						method: 'post',
						data: this.$http.adornData()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.dataForm.gameId = parseInt(data.gameId) //必须要有
							this.areaSize=1
							this.startVal=[]//数据全部先置空
							this.endVal=[]
							this.idArray=[]
							this.intervalGameRate=[]
							if(data.list!=null&&data.list.length!=0){
								this.areaSize=data.list.length
								for(var i=0;i<this.areaSize;i++){
									this.startVal[i]=data.list[i].startVal/this.rate//除以100显示
									this.endVal[i]=data.list[i].endVal/this.rate
									this.intervalGameRate[i]=Math.round(data.list[i].intervalGameRate*this.rate)//乘以100显示
									this.idArray[i]=data.list[i].id
									//console.log(data.list[i].id)
								}
							}else{
								this.areaSize=1
								this.startVal=[]
								this.endVal=[]
								this.idArray=[]
								this.intervalGameRate=[]
							}
						}
					})
				}
			},
			updateValArray() { //重新给数组赋值
				var startValback = [];
				//console.log(this.areaSize)
				for (var i = 0; i < this.areaSize; i++) {
					startValback[i] = this.startVal[i]
				}
				this.startVal = startValback

				var endValback = [];
				for (var i = 0; i < this.areaSize; i++) {
					endValback[i] = this.endVal[i]
				}
				this.endVal = endValback

				var intervalGameRateback = [];
				for (var i = 0; i < this.areaSize; i++) {
					intervalGameRateback[i] = this.intervalGameRate[i]
				}
				this.intervalGameRate = intervalGameRateback
				
				//console.log("id数组"+this.idArray)
				/*var idBack=[]
				for (var i = 0; i < this.areaSize; i++) {//原本就几个id就要保留几个
					idBack[i] = this.idArray[i]
				}
				this.idArray=idBack*/
			},
			selectIntervalByGameId() { //去查询当前选中gameId的所有数据并赋值
				//console.log(this.dataForm.gameId);
				this.$http({
					url: this.$http.adornUrl(`/gameconfig/gameconfig/selectIntervalByGameId/${this.dataForm.gameId}`),
					method: 'post',
					data: this.$http.adornData()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataForm.gameId = parseInt(data.gameId) //必须要有
						this.areaSize=1
						this.startVal=[]//数据全部先置空
						this.endVal=[]
						this.idArray=[]
						this.intervalGameRate=[]
						if(data.list!=null&&data.list.length!=0){
							this.areaSize=data.list.length
							for(var i=0;i<this.areaSize;i++){
								this.startVal[i]=data.list[i].startVal/this.rate//除以100显示
								this.endVal[i]=data.list[i].endVal/this.rate
								this.intervalGameRate[i]=data.list[i].intervalGameRate*this.rate//乘以100显示
								this.idArray[i]=data.list[i].id
								//console.log(data.list[i].id)//--------------------------------------------------------------id输出位置
							}
						}else{
							this.areaSize=1
							this.startVal=[]
							this.endVal=[]
							this.idArray=[]
							this.intervalGameRate=[]
						}
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				var startValback = [];
				//console.log(this.areaSize)
				for (var i = 0; i < this.areaSize; i++) {
					startValback[i] = this.startVal[i]*this.rate//乘以100提交
				}
				var startValString = startValback.join(","); //数组转为字符串
				
				
				var endValback = [];
				for (var i = 0; i < this.areaSize; i++) {
					endValback[i] = this.endVal[i]*this.rate//乘以100提交
				}
				var endValString = endValback.join(","); //数组转为字符串
				
				var intervalGameRateback = [];
				for (var i = 0; i < this.areaSize; i++) {
					intervalGameRateback[i] = this.intervalGameRate[i]/this.rate//除以100提交
				}
				var intervalGameRateString = intervalGameRateback.join(","); //数组转为字符串
				
				//把idArray里面的空字符去掉
				var idArrayback=[];
				//console.log(this.idArray.length)
				for (var i = 0; i < this.idArray.length; i++) {
					if(this.idArray[i] !=null){
						idArrayback[i]=this.idArray[i]
					}
				}
				this.idArray=idArrayback;
				
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/gameconfig/gameconfig/saveOrUpdateIntervalGameRate`),
							method: 'post',
							data: this.$http.adornData({
								'gameId': this.dataForm.gameId,
								'startVal': startValString,
								'endVal': endValString,
								'intervalGameRate': intervalGameRateString,
								'id':this.idArray,
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
