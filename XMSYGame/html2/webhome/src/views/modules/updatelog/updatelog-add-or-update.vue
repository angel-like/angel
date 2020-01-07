<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="标题" prop="title">
				<el-input v-model="dataForm.title" placeholder="标题"></el-input>
			</el-form-item>
			<el-form-item label="内容" type="textarea" prop="content">
				<el-input v-model="dataForm.content" placeholder="内容"></el-input>
			</el-form-item>
			<el-form-item label="项目名" prop="projectName">
				<el-input v-model="dataForm.projectName" placeholder="项目名"></el-input>
			</el-form-item>
			<el-form-item label="类型" prop="type">
				<el-select v-model="dataForm.type" clearable placeholder="类型">
					<el-option 
					v-for="item in options" 
					:key="item.name"
					:label="item.label"
					:value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="预计更新时间" prop="expectUpdateTime">
				<el-date-picker v-model="dataForm.expectUpdateTime" type="datetime" placeholder="预计更新时间"></el-date-picker>
			</el-form-item>
			<!--
			<el-form-item label="是否更新" prop="updateStatus">
				<el-radio-group v-model="dataForm.updateStatus">
					<el-radio :label="false">否</el-radio>
					<el-radio :label="true">是</el-radio>
				</el-radio-group>
			</el-form-item>
			
			<el-form-item label="完成时间" prop="finishTime">
				<el-date-picker v-model="dataForm.finishTime" type="datetime" placeholder="完成时间"></el-date-picker>
			</el-form-item>
			* -->
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
				options:[{
					name:0,
					label:'后台'
				},{
					name:1,
					label:'前台'
				},{
					name:2,
					label:'游戏管理服台'
				}],
				dataForm: {
					id: 0,
					title: '',
					content: '',
					projectName: '',
					type: '',
					expectUpdateTime: '',
					updateStatus: false,
					finishTime: '',
				},
				dataRule: {
					title: [{
						required: true,
						message: '标题不能为空',
						trigger: 'blur'
					}],
					/*content: [{
						required: true,
						message: '内容不能为空',
						trigger: 'blur'
					}],*/
					projectName: [{
						required: true,
						message: '项目名不能为空',
						trigger: 'blur'
					}],
					type: [{
						required: true,
						message: '类型(0:后台 1:前台 2：游戏管理服）不能为空',
						trigger: 'blur'
					}],
					expectUpdateTime: [{
						required: true,
						message: '预计更新时间不能为空',
						trigger: 'blur'
					}],
					updateStatus: [{
						required: true,
						message: '是否更新(0:否，1：是)不能为空',
						trigger: 'blur'
					}]/*,
					finishTime: [{
						required: true,
						message: '完成后更新时间不能为空',
						trigger: 'blur'
					}]*/
				}
			}
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/updatelog/updatelog/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.title = data.updatelog.title
								this.dataForm.content = data.updatelog.content
								this.dataForm.projectName = data.updatelog.projectName
								this.dataForm.type = data.updatelog.type
								this.dataForm.expectUpdateTime = data.updatelog.expectUpdateTime
								this.dataForm.updateStatus = data.updatelog.updateStatus
								this.dataForm.finishTime = data.updatelog.finishTime
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/updatelog/updatelog/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'title': this.dataForm.title,

								'content': this.dataForm.content,

								'projectName': this.dataForm.projectName,

								'type': this.dataForm.type,

								'expectUpdateTime': this.dataForm.expectUpdateTime,

								'updateStatus': this.dataForm.updateStatus,

								'finishTime': this.dataForm.finishTime,

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
