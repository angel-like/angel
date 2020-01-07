<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="图片的名称" placement="top-start">
			<el-form-item label="图片名称" prop="name" >
				<el-input v-model="dataForm.name"    placeholder="图片名称"  ></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="选择是否可跳转" placement="top-start">
			<el-form-item label="图片类型" prop="type">
				<el-select v-model="dataForm.type" clearable placeholder="请选择图片类型">
					<el-option 
					v-for="item in pictureType" :key="item.id" :label="item.type" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			</el-tooltip>
			<el-form-item label="是否可用" prop="availability">
				<el-radio-group v-model="dataForm.availability">
					<el-radio :label="'1'">是</el-radio>
					<el-radio :label="'0'">否</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-tooltip class="item" effect="dark" content="点击图片,跳转路径" placement="top-start">
			<el-form-item label="跳转路径" prop="url" v-if="urlShow">
				<el-input v-model="dataForm.url" placeholder="跳转路径"  ></el-input>
			</el-form-item>
			</el-tooltip>
			<el-form-item label="附件ID" v-if="show" prop="enclosureId">
				<el-input v-model="dataForm.enclosureId" placeholder="附件ID" style="width: 200px;"  readOnly></el-input>
				<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
			</el-form-item>
		</el-form>
		<el-tooltip class="item" effect="dark" content="赚钱攻略图片" placement="top">
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		:file-list="fileList" :data="form" style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<!-- <div style="color: red;">上传的图片大小为：700*440</div> -->
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
				pictureSize:'',
				pictureType: [{
					id: 1,
					type: '不可跳转',
				}, {
					id: 2,
					type: '可跳转'
				}],
				visible: false,
				urlShow:false,
				fileList: [],
				show: false,
				form:null,
				num:0,
				successNum: 0,
				dataForm: {
					id: 0,
					availability: '1',
					enclosureId:'',
					type: '',
					name: '',
					url:''
				},
				dataRule: {
					type: [{
						required: true,
						message: '图片类型不能为空',
						trigger: 'blur'
					}],
					name: [{
						required: true,
						message: '图片名称不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		watch:{
			//监听
			dataForm:{
				handler(val,oldval){
					if(val.type==1){
						this.urlShow=false
					}else if(val.type==2){
						this.urlShow=true
					}
				},
				deep:true
			}
		},
		methods: {
			init(id) {
				//下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/1/40`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
				this.show=false
				this.dataForm.enclosureId=''
				this.visible = true
				this.fileList = []
				this.$nextTick(() => {
					this.dataForm.id = id || 0
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/appmoneystrategy/appmoneystrategy/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.type = data.appmoneystrategy.type
								if(this.dataForm.type==2){
									this.urlShow=true
									this.dataForm.url=data.appmoneystrategy.url
								}
								this.dataForm.name = data.appmoneystrategy.name
								this.dataForm.enclosureId = data.appmoneystrategy.enclosureId
								if(data.appmoneystrategy.enclosureId!=null){
									this.urlShow=true
								}
								this.dataForm.url = data.appmoneystrategy.url
								if(data.appmoneystrategy.enclosureId!=null&&data.appmoneystrategy.enclosureId!=0){
									this.show=true
								}
								if(data.appmoneystrategy.availability){
									this.dataForm.availability="1"
								}else{
									this.dataForm.availability="0"
								}
							}
						})
					}
				})
			},
			
			
			getUrl(){
				this.$emit('getUrl',this.dataForm.enclosureId)
			},
			
			UploadUrl: function() {
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;
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
				if(this.dataForm.enclosureId==null||this.dataForm.enclosureId ===''){
					this.$message.error("图片附件不能为空")
					return
				}
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/appmoneystrategy/appmoneystrategy/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'type': this.dataForm.type,
								'enclosureId': this.dataForm.enclosureId,
								'availability': this.dataForm.availability,
								'url': this.dataForm.url,
								'name': this.dataForm.name,
							})
						}).then(({data}) => {
							if (data && data.code === 200) {
								this.$message({
									message: '操作成功',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.visible = false
										this.fileList = []
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

