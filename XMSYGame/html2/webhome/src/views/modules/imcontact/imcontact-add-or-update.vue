<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<div style="margin: -64px 60px 40px 60px;">
					<el-tooltip class="item" effect="dark" content="联系方式管理" placement="top">
						<i class="el-icon-question" style="color:blue" ></i>
					</el-tooltip>
		</div>
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="例如:客服电话,游戏咨询等" placement="top-start">
			<el-form-item label="名称" prop="name">
				<el-input v-model="dataForm.name" placeholder="名称"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="例如:电话号码,在线客服" placement="top-start">
			<el-form-item label="内容" prop="content">
				<el-input v-model="dataForm.content" placeholder="内容"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="请选择显示类型" placement="top-start">
			<el-form-item label="文本类型" prop="contentType" :change="changeShow()">
				<el-select v-model="dataForm.contentType" clearable placeholder="请选择类型">
					<el-option v-for="item in contentTypeOptions" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
      </el-tooltip>
			<el-tooltip class="item" effect="dark" content="请选择信息类型" placement="top-start">
			<el-form-item label="类型" prop="type">
				<el-select v-model="dataForm.type" clearable placeholder="请选择类型">
					<el-option v-for="item in options" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			</el-tooltip>
			<el-form-item label="是否展示" prop="enable">
				<el-radio-group v-model="dataForm.enable">
					<el-radio :label="true">是</el-radio>
					<el-radio :label="false">否</el-radio>
				</el-radio-group>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="需要跳转到的类型" placement="top-start"  v-if="this.dataForm.contentType==1">
			<el-form-item label="跳转路径" prop="url">
				<el-input v-model="dataForm.url" placeholder="跳转路径"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-form-item label="排序号" prop="sort">
				<el-input-number v-model="dataForm.sort" controls-position="right" :min="0" label="排序号"></el-input-number>
			</el-form-item>
			<el-form-item label="备注" prop="remake">
				<el-input v-model="dataForm.remake" placeholder="备注"></el-input>
			</el-form-item>
			<el-form-item label="附件ID" prop="enclosureId" v-if="show">
				<el-input v-model="dataForm.enclosureId" placeholder="附件ID" style="width: 200px;" readOnly></el-input>
				<el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
			</el-form-item>
		</el-form>
		<!--图片上传-->
		<el-tooltip class="item"  v-if="show" effect="dark" content="联系方式图片类型" placement="top">
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
				visible: false,
				fileList: [],
				pictureSize: '',
				form:null,
				options: [{
					name: 0,
					label: '网站联系方式'
				}, {
					name: 1,
					label: '网站信息'
				}, {
					name: 2,
					label: 'h5联系方式'
				}, {
					name: 3,
					label: '官方自媒体'
				}, {
					name: 4,
					label: '合作媒体'
				}],
				contentTypeOptions: [{
					name: 0,
					label: '文本'
				}, {
					name: 1,
					label: '链接'
				}, {
					name: 2,
					label: '图片'
				}],
				dataForm: {
					id: 0,
					enclosureId: '',
					name: '',
					content: '',
					type: '',
					enable: true,
					remake: '',
					url: '',
					sort: 0,
					contentType: '',
				},
				dataRule: {
					name: [{
						required: true,
						message: '名称不能为空',
						trigger: 'blur'
					}],
					content: [{
						required: true,
						message: '内容不能为空',
						trigger: 'blur'
					}],
					type: [{
						required: true,
						message: '信息类型不能为空',
						trigger: 'blur'
					}],
					enable: [{
						required: true,
						message: '是否展示不能为空',
						trigger: 'blur'
					}],
					sort: [{
						required: true,
						message: '排序不能为空',
						trigger: 'blur'
					}],
					contentType: [{
						required: true,
						message: '文本类型不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				 //下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/12`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
				this.show = false
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/imcontact/imcontact/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.enclosureId = data.imcontact.enclosureId
								this.dataForm.name = data.imcontact.name
								this.dataForm.content = data.imcontact.content
								this.dataForm.type = data.imcontact.type
								this.dataForm.enable = data.imcontact.enable
								this.dataForm.remake = data.imcontact.remake
								this.dataForm.url = data.imcontact.url
								this.dataForm.sort = data.imcontact.sort
								this.dataForm.contentType = data.imcontact.contentType
								if (data.imcontact.enclosureId != null) {
									this.show = true
								}
							}
						})
					}
				})
			},
			changeShow() {
				if(this.dataForm.contentType==0){
					this.dataForm.url=null
					this.dataForm.enclosureId=null
				}
				if(this.dataForm.contentType==1){
					this.dataForm.enclosureId=null
				}
				if(this.dataForm.contentType==2){
					this.show=true
					this.dataForm.url=null
				}else{
					this.show=false
				}
			},
			UploadUrl: function() {
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;
			},

			getUrl() {
				this.$emit('getUrl', this.dataForm.enclosureId)
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
					this.show = true
				} else {
					this.$message.error(response.msg)
				}
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/imcontact/imcontact/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'enclosureId': this.dataForm.enclosureId,

								'name': this.dataForm.name,

								'content': this.dataForm.content,

								'type': this.dataForm.type,

								'enable': this.dataForm.enable,

								'remake': this.dataForm.remake,

								'url': this.dataForm.url,

								'sort': this.dataForm.sort,

								'contentType': this.dataForm.contentType,

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
