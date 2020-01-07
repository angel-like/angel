<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="标题" prop="title">
				<el-input v-model="dataForm.title" placeholder="标题"></el-input>
			</el-form-item>
			<el-form-item label="类别" prop="category">
				<el-select v-model="dataForm.category" placeholder="请选择类别">
					<el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="素材类型" prop="type">
				<el-select v-model="dataForm.type" placeholder="素材类型">
					<el-option v-for="item in typeOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="内容" prop="content" v-show="dataForm.type==0||dataForm.type==2">
				<editor v-model="dataForm.content" api-key="3ftge632o0domdn4vu0wda20yzdlfbeq658w1s8h5h7ewlos" :init="editorInit"></editor>
			</el-form-item>
			<el-form-item label="图片路径" prop="imageUrl" v-show="dataForm.type==1||dataForm.type==2">
				<el-input v-model="dataForm.imageUrl" placeholder="图片url，素材为图片非富文本的场景"></el-input>
			</el-form-item>
			<el-form-item label="跳转路径" prop="jumpUrl" v-show="dataForm.type==1||dataForm.type==2">
				<el-input v-model="dataForm.jumpUrl" placeholder="跳转url，素材为图片的时候可选"></el-input>
			</el-form-item>
		</el-form>
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		 :file-list="fileList" :data="form" style="text-align: center;" v-show="dataForm.type==1||dataForm.type==2">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import Editor from '@tinymce/tinymce-vue';
	export default {
		data() {
			var validateContent = (rule, value, callback) => {
				if (this.dataForm.type == 0) {
					if (value === '') {
						console.log("请输入文本内容")
						callback(new Error('请输入文本内容'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			var validateImageUrl = (rule, value, callback) => {
				if (this.dataForm.type == 1||this.dataForm.type == 2) {
					if (value === '') {
						callback(new Error('请上传图片'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			return {
				visible: false,
				fileList: [],
				num: 0,
				show: false,
				form: null,
				successNum: 0,
				editorInit: {
					language_url: '/static/tinymce/zh_CN.js',
					language: 'zh_CN',
					height: 300
				},
				dataForm: {
					id: 0,
					title: '',
					content: '',
					type: 0,
					category: 1,
					imageUrl: '',
					jumpUrl: '',
				},
				categoryOptions: [{
						id: 1,
						name: "新闻"
					},
					{
						id: 2,
						name: "特色"
					},
					{
						id: 3,
						name: "活动"
					},
					{
						id: 4,
						name: "维护"
					},
					{
						id: 5,
						name: "防盗"
					},
					{
						id: 6,
						name: "游戏截图"
					},
					{
						id: 7,
						name: "新手入门"
					},
					{
						id: 8,
						name: "充值帮助"
					},
					{
						id: 9,
						name: "玩家福利"
					},
					{
						id: 10,
						name: "取款规则"
					},
					{
						id: 11,
						name: "背景轮播"
					},
					{
						id: 12,
						name: "VIP段位介绍"
					},
					{
						id: 13,
						name: "最新消息"
					}
				],
				typeOptions: [{
						id: 0,
						name: "富文本"
					},
					{
						id: 1,
						name: "图片/视频"
					},
					{
						id: 2,
						name: "图片+富文本"
					}
				],
				dataRule: {
					title: [{
						required: true,
						message: '标题不能为空',
						trigger: 'blur'
					}],
					content: [{
						message: '内容，不能为空',
						validator: validateContent,
						trigger: 'blur'
					}],
					type: [{
						required: true,
						message: '素材类型不能为空',
						trigger: 'blur'
					}],
					category: [{
						required: true,
						message: '类别不能为空',
						trigger: 'blur'
					}],
					imageUrl: [{
						validator: validateImageUrl,
						message: '图片url不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		components: {
			'editor': Editor
		},
		methods: {
			init(id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/immaterial/immaterial/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.title = data.immaterial.title
								this.dataForm.content = data.immaterial.content
								this.dataForm.type = data.immaterial.type
								this.dataForm.category = data.immaterial.category
								this.dataForm.imageUrl = data.immaterial.imageUrl
								this.dataForm.jumpUrl = data.immaterial.jumpUrl
							}
						})
					}
				})
			},
			getUrl() {
				this.$emit('getUrl', this.dataForm.imageUrl)
			},
			UploadUrl: function() {
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;
			},
			// 照片上传之前
			beforeUploadHandle(file) {
				this.num++
			},
			mounted() {
				tinymce.init({
					selector: '#mytextarea'
				});
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
					this.dataForm.imageUrl = response.url
					this.dataForm.iconMd5 = response.Md5Val
					this.show = true
				} else {
					this.$message.error(response.msg)
				}
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						console.log("this.dataForm.type==" + this.dataForm.type)
						this.$http({
							url: this.$http.adornUrl(`/immaterial/immaterial/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'content': this.dataForm.content,
								'title': this.dataForm.title,
								'type': this.dataForm.type,
								'category': this.dataForm.category,
								'imageUrl': this.dataForm.imageUrl,
								'jumpUrl': this.dataForm.jumpUrl
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
