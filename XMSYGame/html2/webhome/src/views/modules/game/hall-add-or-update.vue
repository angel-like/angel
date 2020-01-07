<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="大厅名称" prop="name">
				<el-input v-model="dataForm.name" placeholder="大厅名称"></el-input>
			</el-form-item>

			<el-form-item label="房间集合" prop="roomIds">
				<el-select v-model="dataForm.roomIds" multiple placeholder="请选择">
					<el-option v-for="item in roomList" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
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
				value: '',
				roomList: [],
				visible: false,
				name: '',
				dataForm: {
					id: 0,
					name: '',
					roomIds: '',
					roomArray: '',
				},
				dataRule: {
					name: [{
						required: true,
						message: '大厅名称不能为空',
						trigger: 'blur'
					}],
					
// 					roomIds: [{
// 						required: true,
// 						message: '房间id数组不能为空',
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
						url: this.$http.adornUrl(`/room/room/RoomList`),
						method: 'post',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.roomList = data.roomList; //获取房间名称		
						}
					})
					if (this.dataForm.id) {
						console.log("this.dataForm.id"+this.dataForm.id);
						this.$http({
							url: this.$http.adornUrl(`/hall/hall/info/${this.dataForm.id}`),
							method: 'post',
							data: this.$http.adornData()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								
								var rooms=data.hall.roomIds.split(",");
								var roomIds=[];
								for(var i=0; i<rooms.length; i++){
									roomIds.push(parseInt(rooms[i]));
								}
								this.dataForm.name = data.hall.name
								//console.log("======================"+this.dataForm.name);
								this.dataForm.roomIds = roomIds
								
							}
						})
					}
				})
			},


			// 表单提交
						dataFormSubmit() {
							this.$refs['dataForm'].validate((valid) => {
								if (valid) {
									//this.getGameTypeName();
									this.$http({
										url: this.$http.adornUrl(`/hall/hall/${!this.dataForm.id ? 'save' : 'update'}`),
										method: 'post',
										data: this.$http.adornData({
											'id': this.dataForm.id || undefined,
											'name': this.dataForm.name,
											'roomIds': this.dataForm.roomIds.join(","),
											'roomArray': this.dataForm.roomArray,
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
