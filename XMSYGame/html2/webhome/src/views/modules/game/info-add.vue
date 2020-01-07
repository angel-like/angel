<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="游戏id" prop="gameId">
				<el-input v-model="dataForm.gameId" placeholder="游戏id"></el-input>
			</el-form-item>
			<el-form-item label="游戏名称" prop="gameName">
				<el-input v-model="dataForm.gameName" placeholder="游戏名称"></el-input>
			</el-form-item>
			<el-form-item label="游戏房间" prop="roomId">
				<template>
					<el-select v-model="dataForm.roomId" placeholder="请选择">
						<el-option v-for="item in roomList" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</template>
			</el-form-item>
			<el-form-item label="游戏渠道" prop="provider">
				<el-select v-model="dataForm.provider" placeholder="请选择">
					<el-option v-for="item in providerOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
		</el-form>
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
			var checkAmount = (rule, value, callback) => {
				var retgex = /^\+?[1-9][0-9]*$/;
				if (!retgex.test(value)) {
					callback(new Error('请输入非零的正整数'));
				} else {
					callback();
				}
			};
			return {
				providerOptions:[{
					id:0,
					name:"本地游戏"
				},{
					id:1,
					name:"开源游戏"
				}],
				loadPicture:false,
				
				visible: false,
				roomList: [],
				gradeList: [],
				dataForm: {
					id: 0,
					gameId: '',
					gameName: '',
					provider:0,
					roomId: ''
				},
				dataRule: {
					gameId: [{
							required: true,
							message: '游戏id不能为空',
							trigger: 'blur'
						},
						{
							validator: checkAmount,
							trigger: 'blur'
						}
					],
					gameName: [{
						required: true,
						message: '游戏名称不能为空',
						trigger: 'blur'
					}],
					roomId: [{
						required: true,
						message: '房间不能为空',
						trigger: 'blur'
					}],
					provider: [{
						required: true,
						message: '游戏渠道不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.loadPicture=false
				this.dataForm.provider=0
				this.visible = true
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
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.loadPicture=true
						this.$http({
							url: this.$http.adornUrl(`/info/info/saveNew`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'gameId': this.dataForm.gameId,
								'gameName': this.dataForm.gameName,
								'roomId': this.dataForm.roomId,
								'provider': this.dataForm.provider,
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
							this.loadPicture=false
						})
					}
				})
			}
		}
	}
</script>
