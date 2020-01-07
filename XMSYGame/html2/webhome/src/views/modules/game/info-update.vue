<template>
	<el-dialog title="修改" :close-on-click-modal="false" :visible.sync="visible">
		<div style="overflow: hidden;">
			<el-card class="box-card">
				<div style="text-align: center;">
					<el-tag>游戏名称:{{showGameName}}</el-tag>
					<el-tag>游戏id:{{dataForm.gameId}}</el-tag>
					<el-tag>房间:{{showRoomName}}</el-tag>
				</div>
				<hr />
				<div>
					<div style="text-align: left;width:12%;float:left;height:40px;">
						<el-tag>属性</el-tag>
					</div>
					<div v-for="(name,i) in showGameGrade" style="text-align: center;width:22%;float:left;height:40px;">
						<el-tag>{{showGameGrade[i]}}</el-tag>
					</div>
				</div>
			</el-card>
			<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
				<!--1.属性-->
				<div style="width:12%;float:left; background:white;">
					<!--1.1游戏信息的属性-->
					<!-- <div style="text-align: center;height:60px;">启用</div>
					<div style="text-align: center;height:60px;">显示</div>
					<div style="text-align: center;height:60px;">维护中</div> -->
					<!-- <div style="text-align: center;height:60px;">scene页面</div> -->
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1 && dataForm.gameId!=23">游戏底分</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1">入场限制</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1 || dataForm.roomId==3">限高</div>
					<!-- <div style="text-align: center;height:60px;">限踢</div> -->
					<div style="text-align: center;height:60px;"  v-if="dataForm.roomId==1 && dataForm.gameId!=23">最高倍率</div>
					<div style="text-align: center;height:60px;"  v-if="dataForm.roomId==4" >线数</div>
					<div style="text-align: center;height:60px;"  v-if="dataForm.roomId==3" >最低金额</div>
					<div style="text-align: center;height:60px;">最小在线人数</div>
					<div style="text-align: center;height:60px;">最大在线人数</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1 && dataForm.gameId!=23" >抽水比率</div>
					<!--1.2游戏配置的属性-->
					<div v-for="item in sysDictionaryList" style="text-align: center;height:60px;">
						{{item.name}}
					</div>
				</div>
				<!--1.游戏信息-->
				<div v-for="gameInfo in gameInfoList" style="width:20%;float:left; background:white;margin-left:8px">
					<!-- <div style="text-align: center;height:60px;">
						<el-switch v-model="gameInfo.enable" active-color="#13ce66" inactive-color="#ff4949" active-text="是"
						 inactive-text="否"></el-switch>
					</div>
					<div style="text-align: center;height:60px;">
						<el-switch v-model="gameInfo.display" active-color="#13ce66" inactive-color="#ff4949" active-text="是"
						 inactive-text="否"></el-switch><br />
					</div>
					<div style="text-align: center;height:60px;">
						<el-switch v-model="gameInfo.maintenance" active-color="#13ce66" inactive-color="#ff4949" active-text="否"
						 inactive-text="是"></el-switch><br />
					</div> -->
					<!-- <div style="text-align: center;height:60px;">
						<el-input v-model="gameInfo.sence" placeholder="显示的scene页面"></el-input>
					</div> -->
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1 && dataForm.gameId!=23">
						<el-input v-model="gameInfo.bscore" placeholder="游戏底分" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1" >
						<el-input v-model="gameInfo.restrict" placeholder="入场限制" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1 || dataForm.roomId==3">
						<el-input v-model="gameInfo.limitHigh" placeholder="限高" type="number"></el-input>
					</div>
				<!-- 	<div style="text-align: center;height:60px;">
						<el-input v-model="gameInfo.limitLower" placeholder="限踢" type="number"></el-input>
					</div> -->
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==1 && dataForm.gameId!=23">
						<el-input v-model="gameInfo.maxTimes" placeholder="最高倍率" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==4">
						<el-input v-model="gameInfo.maxLines" placeholder="线数" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;" v-if="dataForm.roomId==3">
						<el-input v-model="gameInfo.maxLines" placeholder="最低金额" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;">
						<el-input v-model="gameInfo.onlineMin" placeholder="最小在线人数" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;">
						<el-input v-model="gameInfo.onlineMax" placeholder="最大在线人数" type="number"></el-input>
					</div>
					<div style="text-align: center;height:60px;"  v-if="dataForm.roomId==1 && dataForm.gameId!=23">
						<!-- <el-input v-model="gameInfo.rate" placeholder="抽水比率" type="number" min="0" max="5"></el-input> -->
						<el-input-number v-model="gameInfo.rate" :min="0" :max="5" label="抽水比率" style="width: 184px;"></el-input-number>
					</div>
				</div>
				<!-- <div style="text-align: center;background:white;height:780px;"></div> -->
				<!--上面属于div浮动不占用空间，所以这边把上面浮动覆盖掉-->
				<!--2.<游戏信息的   游戏配置-->
				<div v-for="sysDictionaryList in sysDictionaryListList" style="width:20%;float:left; background:white;margin-left:8px">
					<div v-for="item in sysDictionaryList" style="text-align: center;height:60px;">
						<el-input v-model="item.val" v-if="item.code=='chipMax' || item.code=='chipMin'" disabled ></el-input>
						<el-input v-model="item.val" v-if="item.code!='chipMax' && item.code!='chipMin'"  ></el-input>
					</div>
				</div>
			</el-form>
		</div>

		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			//验证正整数
			var validateRate = (rule, value, callback) => {
				var res = /^[0-9]*$/;
				if (value === '') {
					callback(new Error('不可为空'));
				} else {
					if (!res.test(value)) {
						callback(new Error('格式有误'));
					} else {
						if (rule.field == "rate" && value >= 100) {
							callback(new Error('格式有误'));
						} else {
							callback();
						}
					}
				}
			};
			return {
				loadPicture: false,
				visible: false,
				roomList: [],
				gradeList: [],
				gameInfoList: [],
				showGameId: '',
				showGameName: '',
				showRoomName: '',
				showGameGrade: [],
				sysDictionaryListList: [], //数据字典集合的集合
				sysDictionaryList: [], //数据字典集合--为了显示属性名称
				moneyRate: 100, //乘除100用的
				dataForm: {
					gameId: '',
					gameName: '',
					roomId: '',
					gradeId: '',
					enable: true,
					display: true,
					sence: '',
					bscore: '',
					restrict: '',
					maintenance: false,
					maxTimes: '',
					maxLines: '',
					onlineMin: '',
					onlineMax: '',
					roomMin: '',
					roomMax: '',
					limitHigh: '',
					limitLower: '',
					rate: 0,
				},
				dataRule: {
				}
			}
		},
		methods: {
			init(gameId) {
				this.loadPicture = false
				this.dataForm.gameId = gameId
				this.dataForm.roomId = 0;
				//console.log("新增页面游戏ID:" + this.dataForm.gameId)
				this.showGameGrade = [];
				this.gameInfoList = []; //初始化游戏信息数据
				this.sysDictionaryListList = []; //初始化游戏配置信息的数据
				this.visible = true
				var moneyRate = 100;
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					this.$http({
						url: this.$http.adornUrl(`/room/room/RoomList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.roomList = data.roomList; //获取房间名称		
						}
					})
					this.$http({
						url: this.$http.adornUrl(`/gamegrade/gamegrade/GradeList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.gradeList = data.gradeList; //获取房间名称		
						}
					})
					this.$http({
						url: this.$http.adornUrl(`/info/info/infoNew/${this.dataForm.gameId}`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.gameInfoList = data.gameInfoList
							//单个显示的信息
							this.showGameName = this.gameInfoList[0].gameName
							this.dataForm.roomId = this.gameInfoList[0].roomId
							for (var i = 0; i < this.roomList.length; i++) { //为了获取房间名称
								if (this.gameInfoList[0].roomId == this.roomList[i].id) {
									this.showRoomName = this.roomList[i].name
								}
							}
							var k = 0;
							for (var j = 0; j < this.gameInfoList.length; j++) {
								//显示的各个字段   游戏低分、入场限制、限高、限踢  要/100      抽水比例*100
								this.gameInfoList[j].bscore = this.gameInfoList[j].bscore / moneyRate
								this.gameInfoList[j].restrict = this.gameInfoList[j].restrict / moneyRate
								this.gameInfoList[j].limitHigh = this.gameInfoList[j].limitHigh / moneyRate
								this.gameInfoList[j].limitLower = this.gameInfoList[j].limitLower / moneyRate
								this.gameInfoList[j].rate = Math.round(this.gameInfoList[j].rate * moneyRate)
								if(this.gameInfoList[j].roomId==3){
									this.gameInfoList[j].maxLines = this.gameInfoList[j].maxLines / moneyRate
								}
								for (var i = 0; i < this.gradeList.length; i++) { //为了获取各个厅名称
									if (this.gameInfoList[j].gradeId == this.gradeList[i].id) {
										this.showGameGrade[k] = this.gradeList[i].name
										k++
									}
								}
							}
							//游戏信息  对应 游戏配置表的数据
							this.sysDictionaryListList = data.sysDictionaryListList
							this.sysDictionaryList = this.sysDictionaryListList[0]
							for (var j = 0; j < this.sysDictionaryListList.length; j++) {
								for (var i = 0; i < this.sysDictionaryListList[j].length; i++) {
									//四个库存要 除以 100   +限红最大值  筹码最小值  筹码最大值    vip限红 最小携带筹码值 最大携带筹码值
									if (this.sysDictionaryListList[j][i].code == "currentStock" || this.sysDictionaryListList[j][i].code ==
										"goalStock" ||
										this.sysDictionaryListList[j][i].code == "initialStock" || this.sysDictionaryListList[j][i].code ==
										"cumulativeStock" ||
										this.sysDictionaryListList[j][i].code == "chipMin" || this.sysDictionaryListList[j][i].code == "chipMax"
										|| this.sysDictionaryListList[j][i].code == "limitRedMax"|| this.sysDictionaryListList[j][i].code == "vipLimitRed" 
										|| this.sysDictionaryListList[j][i].code == "roomMin"|| this.sysDictionaryListList[j][i].code == "roomMax") {
										if (this.sysDictionaryListList[j][i].val != null && this.sysDictionaryListList[j][i].val != '') {
											this.sysDictionaryListList[j][i].val = this.sysDictionaryListList[j][i].val / moneyRate
										}
									}
									//小游戏胜率*100
									if (this.sysDictionaryListList[j][i].code == "miniRate") {
										if (this.sysDictionaryListList[j][i].val != null && this.sysDictionaryListList[j][i].val != '') {
											this.sysDictionaryListList[j][i].val = Math.round(this.sysDictionaryListList[j][i].val * moneyRate)
										}
									}

								}
							}
						}
					})
				})
			},
			// 表单提交
			dataFormSubmit() {
				//数据校验  重新赋值
				var moneyRate = 100;
				var ss = this.gameInfoList;
				var gameInfoListJSONObject = [];
				for (var i = 0; i < ss.length; i++) {
					gameInfoListJSONObject[i] = ss[i]
					gameInfoListJSONObject[i].bscore = gameInfoListJSONObject[i].bscore * moneyRate
					gameInfoListJSONObject[i].restrict = gameInfoListJSONObject[i].restrict * moneyRate
					gameInfoListJSONObject[i].limitHigh = gameInfoListJSONObject[i].limitHigh * moneyRate
					gameInfoListJSONObject[i].limitLower = gameInfoListJSONObject[i].limitLower * moneyRate
					gameInfoListJSONObject[i].rate = gameInfoListJSONObject[i].rate / moneyRate
					if(gameInfoListJSONObject[i].roomId==3){
						gameInfoListJSONObject[i].maxLines = gameInfoListJSONObject[i].maxLines * moneyRate
					}
					
				}
				/*for (var j = 0; j < gameInfoListJSONObject.length; j++) {
					//显示的各个字段   游戏底分、入场限制、限高、限踢  要*100      抽水比例/100
					gameInfoListJSONObject[j].bscore = gameInfoListJSONObject[j].bscore * this.moneyRate
					gameInfoListJSONObject[j].restrict = gameInfoListJSONObject[j].restrict * this.moneyRate
					gameInfoListJSONObject[j].limitHigh = gameInfoListJSONObject[j].limitHigh * this.moneyRate
					gameInfoListJSONObject[j].limitLower = gameInfoListJSONObject[j].limitLower * this.moneyRate
					gameInfoListJSONObject[j].rate = gameInfoListJSONObject[j].rate / this.moneyRate
				}*/
				//游戏信息表  对应的游戏配置的信息 数据校验 后台做
				for (var j = 0; j < this.sysDictionaryListList.length; j++) {
					for (var i = 0; i < this.sysDictionaryListList[j].length; i++) {
						//四个库存要 * 100   +限红最大值 筹码最小值  筹码最大值
						if (this.sysDictionaryListList[j][i].code == "roomMin"|| this.sysDictionaryListList[j][i].code == "roomMax") {
							if (this.sysDictionaryListList[j][i].val != null && this.sysDictionaryListList[j][i].val != '') {
								this.sysDictionaryListList[j][i].val = this.sysDictionaryListList[j][i].val * this.moneyRate
							}
						}
						//小游戏胜率/100
// 						if (this.sysDictionaryListList[j][i].code == "miniRate") {
// 							if (this.sysDictionaryListList[j][i].val != null && this.sysDictionaryListList[j][i].val != '') {
// 								this.sysDictionaryListList[j][i].val = this.sysDictionaryListList[j][i].val / this.moneyRate
// 							}
// 						}
// 						
						
				
					}
				}
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.loadPicture = true
						console.log("this.sysDictionaryListList"+this.sysDictionaryListList);
						//1.游戏信息表  对应的游戏配置的信息
						this.$http({
							url: this.$http.adornUrl(`/info/info/updateNewGameConfig`),
							method: 'post',
							data: this.$http.adornData({
								'sysDictionaryListListJSONObject':this.sysDictionaryListList
							})
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								
								//2.保存游戏信息表
								this.$http({
									url: this.$http.adornUrl(`/info/info/updateNew`),
									method: 'post',
									data: this.$http.adornData({
										'gameInfoListJSONObject': gameInfoListJSONObject,
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
								
							} else {
								this.$message.error(data.msg)
							}
							this.loadPicture = false
						})
						
					}
				})
			}
		}
	}
</script>
