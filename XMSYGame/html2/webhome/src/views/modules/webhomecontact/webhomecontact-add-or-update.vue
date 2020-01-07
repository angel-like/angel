<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">

		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="例如:微信代理" placement="top-start">
			<el-form-item label="名称" prop="name">
				<el-input v-model="dataForm.name" placeholder="名称"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="例如:介绍微信代理怎么样" placement="top-start">
			<el-form-item label="内容" prop="content">
				<el-input v-model="dataForm.content" placeholder="内容"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="例如:http://www.baidu.com" placement="top-start">
			<el-form-item label="跳转路径" prop="url">
				<el-input v-model="dataForm.url" placeholder="跳转路径"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-form-item label="类型" prop="type">
				<el-select v-model="dataForm.type" clearable placeholder="请选择类型">
					<el-option v-for="item in options" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-tooltip class="item" effect="dark" content="是否展示给用户看" placement="top-start">
			<el-form-item label="是否展示" prop="enable">
				<el-radio-group v-model="dataForm.enable">
					<el-radio :label="'1'">是</el-radio>
					<el-radio :label="'0'">否</el-radio>
				</el-radio-group>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="存放图片" placement="top-start">
			<el-form-item label="附件ID" prop="enclosureId" v-if="show">
				<el-input v-model="dataForm.enclosureId" placeholder="附件ID" style="width: 200px;" readOnly></el-input>
				<el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
			</el-form-item>
			</el-tooltip>
		</el-form>
		<el-tooltip class="item" effect="dark" content="信息展示图片" placement="top">
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		:file-list="fileList" :data="form" style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<div style="color: red;">{{pictureSize}}</div>
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
				show: false,
				options: [{
					name: 'contact',
					label: '联系信息'
				}, {
					name: 'website',
					label: '网站信息'
				}],
				pictureSize: '',
				form: null,
				fileList: [],
				num: 0,
				successNum: 0,
				visible: false,
				dataForm: {
					id: 0,
					enclosureId: '',
					name: '',
					content: '',
					type: '',
					url:'',
					enable: 1
				},
				dataRule: {
					content: [{
						required: true,
						message: '内容不能为空',
						trigger: 'blur'
					}],
					type: [{
						required: true,
						message: '类型不能为空',
						trigger: 'blur'
					}],
					enable: [{
						required: true,
						message: '是否展示不能为空',
						trigger: 'blur'
					}]

				}
			}
		},
		methods: {
			init(id) {
				 //下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/16`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
				this.show = false
				this.dataForm.enclosureId = ''
				this.visible = true
				this.fileList = []
				this.dataForm.id = id || 0

				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/webhomecontact/webhomecontact/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.enclosureId = data.contactManagement.enclosureId
								this.dataForm.name = data.contactManagement.name
								this.dataForm.content = data.contactManagement.content
								this.dataForm.type = data.contactManagement.type
								this.dataForm.enable = data.contactManagement.enable
								this.dataForm.url = data.contactManagement.url

								if (data.contactManagement.enclosureId != null) {
									this.show = true
								}
								if (data.contactManagement.enable) {
									this.dataForm.enable = "1"
								} else {
									this.dataForm.enable = "0"
								}
							}
						})
					}
				})
			},
			UploadUrl: function() {
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;
			},

			getUrl(){
				this.$emit('getUrl',this.dataForm.enclosureId)
			},


			// 照片上传之前
			beforeUploadHandle(file) {
				this.num++
			},
			// 照片上传成功
			successHandle(response, file, fileList, type) {
				this.type = type
				this.fileList = fileList
				this.successNum++
				if (response && response.code === 200) {
					if (this.num === this.successNum) {
						this.$message({
							message: '操作成功',
							type: 'success',
							duration: 1500,
						})
					}
					this.dataForm.enclosureId = response.id
					this.show=true
				} else {
					this.$message.error(response.msg)
				}
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/webhomecontact/webhomecontact/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'enclosureId': this.dataForm.enclosureId,
								'name': this.dataForm.name,
								'content': this.dataForm.content,
								'type': this.dataForm.type,
								'enable': this.dataForm.enable,
								'url': this.dataForm.url
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
