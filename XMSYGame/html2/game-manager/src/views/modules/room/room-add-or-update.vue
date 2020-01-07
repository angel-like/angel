<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
			<el-form-item label="房间名称" prop="name">
				<el-input v-model="dataForm.name" placeholder="房间名称"></el-input>
			</el-form-item>
			<el-form-item label="是否可用" prop="enable">
				<el-switch v-model="dataForm.enable" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否"></el-switch>
			</el-form-item>
			<el-form-item label="是否显示" prop="display">
				<el-switch v-model="dataForm.display" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否"></el-switch>
			</el-form-item>

			<el-form-item label="游戏集合" prop="gameIds">
				<template>
					<el-select v-model="dataForm.gameIds" multiple placeholder="请选择">
						<el-option v-for="item in gameList" :key="item.id" :label="item.gameName+'-'+item.gradeName" :value="item.id">
							<span style="float: left">{{ item.gameName }}</span>
							<span style="float: right; color: #8492a6;  font-size: 13px">{{ item.gradeName }}</span>
						</el-option>
					</el-select>
				</template>
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
				gameList: [],
				dataForm: {
					id: 0,
					name: '',
					enable: true,
					display: true,
					gameIds: '',
					roomset: '',
				},
				dataRule: {
					name: [{
						required: true,
						message: '房间名称不能为空',
						trigger: 'blur'
					}],
					enable: [{
						required: true,
						message: '是否可用不能为空',
						trigger: 'blur'
					}],
					display: [{
						required: true,
						message: '是否显示不能为空',
						trigger: 'blur'
					}],
					// 					gameIds: [{
					// 						required: true,
					// 						message: '游戏id数组不能为空',
					// 						trigger: 'blur'
					// 					}],
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()

					this.$http({
						url: this.$http.adornUrl(`/info/info/GameList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.gameList = data.gameList;
							this.gameList.sort(function(a,b){
								return a.gameId-b.gameId
								}
							);
							if (this.dataForm.id) {
								this.$http({
									url: this.$http.adornUrl(`/room/room/info/${this.dataForm.id}`),
									method: 'get',
									params: this.$http.adornParams()
								}).then(({
									data
								}) => {
									if (data && data.code === 200) {
										this.dataForm.name = data.room.name
										this.dataForm.enable = data.room.enable
										this.dataForm.display = data.room.display
										var gameIds = data.room.gameIds.split(',');
										if (data.room.gameIds != '') {
											//console.log("gameIds"+gameIds);
											var gameIdArray = [];
											for (var i = 0; i < gameIds.length; i++) {
												gameIdArray.push(Number(gameIds[i]));
											}
											this.dataForm.gameIds = gameIdArray
// 												console.log('++++++++++++++++++++'+gameIds)
// 												console.log('----------------'+gameIdArray)
										}
									}
								})
							}
						}
					})


				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/room/room/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'name': this.dataForm.name,
								'gameIds': this.dataForm.gameIds.join(","),
								'gameArray': this.dataForm.gameArray,
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
