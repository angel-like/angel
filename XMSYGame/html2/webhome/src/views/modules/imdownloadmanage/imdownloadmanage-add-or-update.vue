<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="推荐下载游戏标题" placement="top-start">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="推荐游戏介绍" placement="top-start">
    <el-form-item label="内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="内容"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="推荐游戏下载链接" placement="top-start">
    <el-form-item label="下载地址" prop="downloadUrl">
      <el-input v-model="dataForm.downloadUrl" placeholder="下载地址"></el-input>
    </el-form-item>
		</el-tooltip>
    <el-form-item label="附件ID"  v-if="show">
    <el-input v-model="dataForm.enclosureId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
    <el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
    </el-form-item>
    <el-form-item label="启/禁用" prop="enable">
			<el-switch
				v-model="dataForm.enable"
				active-color="#13ce66"
				inactive-color="#ff4949">
			</el-switch>
    </el-form-item>
    <el-form-item label="排序号" prop="orderNo">
      <el-input-number v-model="dataForm.orderNo"  :min="1"  label="排序号"></el-input-number>
    </el-form-item>
		<!--因为pc类型隐藏掉  所以这个也先隐藏掉
		<el-form-item label="类型" prop="type">
			<el-radio-group v-model="dataForm.type" size="small">
      <el-radio label="PC" disabled>PC</el-radio>
      <el-radio label="APP"  disabled>APP</el-radio>
    </el-radio-group>-->
		</el-form-item>
		<el-tooltip class="item" effect="dark" content="请选择要下载到的平台" placement="top-start">
		<el-form-item label="平台" prop="platform">
			<el-radio-group v-model="dataForm.platform" size="small">
			<el-radio label="ios" >ios</el-radio>
			<el-radio label="android" >android</el-radio>
		</el-radio-group>
		</el-form-item>
		</el-tooltip>
    </el-form>
		<el-tooltip class="item" effect="dark" content="推荐下载图片" placement="top">
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		:file-list="fileList" :data="form" style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<div style="color: red;">{{pictureSize}}</div>
		<!-- <div style="color: red;">上传的图片尺寸为：130*136   </div> -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
				fileList: [],
				num: 0,
				pictureSize: '',
				show: false,
				form:null,
				successNum: 0,
        dataForm: {
          id: 0,
          title: '',
          content: '',
          downloadUrl: '',
					platform: 'ios',
          enclosureId: '',
          enable: true,
          orderNo: '',
					type:''
        },
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          downloadUrl: [
            { required: true, message: '下载地址不能为空', trigger: 'blur' }
          ],
          enclosureId: [
            { required: true, message: '附件ID不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态(启用，禁用)不能为空', trigger: 'blur' }
          ],
          orderNo: [
            { required: true, message: '排序号不能为空', trigger: 'blur' }
          ],
					type:[ { required: true, message: '状态不能为空', trigger: 'blur' }]
        }
      }
    },
    methods: {
      init (id,type) {
				 //下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/9`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
        this.dataForm.id = id || 0
				this.dataForm.type=type
        this.visible = true
				this.dataForm.enclosureId=null
				this.show=false
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/imdownloadmanage/imdownloadmanage/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.title = data.imdownloadmanage.title
                this.dataForm.content = data.imdownloadmanage.content
                this.dataForm.downloadUrl = data.imdownloadmanage.downloadUrl
                this.dataForm.enclosureId = data.imdownloadmanage.enclosureId
                this.dataForm.enable = data.imdownloadmanage.enable
                this.dataForm.orderNo = data.imdownloadmanage.orderNo
								this.dataForm.platform = data.imdownloadmanage.platform
								this.dataForm.type = data.imdownloadmanage.type
								if(this.dataForm.enclosureId!=null&&this.dataForm.enclosureId!=0){
									this.show=true
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
      dataFormSubmit () {
				if(this.dataForm.enclosureId==null || this.dataForm.enclosureId==0){
					this.$message.error("请上传附件")
					return;
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/imdownloadmanage/imdownloadmanage/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'title': this.dataForm.title ,

          'content': this.dataForm.content ,

          'downloadUrl': this.dataForm.downloadUrl ,

          'enclosureId': this.dataForm.enclosureId ,

          'enable': this.dataForm.enable ,

          'orderNo': this.dataForm.orderNo ,
					'platform': this.dataForm.platform ,
					
					'type': this.dataForm.type

              })
            }).then(({data}) => {
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
