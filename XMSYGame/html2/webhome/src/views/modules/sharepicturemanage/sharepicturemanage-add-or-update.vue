<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <!-- 隐藏修改中：分享标题、分享url、分享内容-->
			<!-- <el-tooltip class="item" effect="dark" content="分享图片的标题" placement="top-start">
			<el-form-item label="分享标题" prop="shareTitle">
				<el-input v-model="dataForm.shareTitle" placeholder="分享标题"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="点击标题后跳转的路径" placement="top-start">
			<el-form-item label="分享url" prop="shareUrl">
				<el-input v-model="dataForm.shareUrl" placeholder="分享url"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="分享图片的内容" placement="top-start">
			<el-form-item label="分享内容" prop="shareContent">
				<el-input v-model="dataForm.shareContent" placeholder="分享内容"></el-input>
			</el-form-item>
			</el-tooltip> -->
			<el-form-item label="是否启用" prop="enable">
				<el-radio-group v-model="dataForm.enable">
					<el-radio :label="true">是</el-radio>
					<el-radio :label="false">否</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="图片ID" prop="enclosureId" v-if="show">
				<el-input v-model="dataForm.enclosureId" placeholder="图片ID" style="width: 200px;" readOnly></el-input>
				<el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
			</el-form-item>
    </el-form>
		<!--图片上传-->
		<el-tooltip class="item" effect="dark" content="分享的图片" placement="top">
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		:file-list="fileList" :data="form" style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<!-- <div style="color: red;">上传的图片大小为：405*720</div> -->
		<div style="color: red;">{{pictureSize}}</div>
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
				pictureSize:'',
				show: false,
        visible: false,
				fileList: [],
				num: 0,
				form:null,
				successNum: 0,
        dataForm: {
          id: 0,
          enclosureId: '',
          shareTitle: '',
          shareUrl: '',
          shareContent: '',
          enable: true,
        },
        dataRule: {
          enclosureId: [
            { required: true, message: '图片ID不能为空', trigger: 'blur' }
          ],
         /* shareTitle: [
            { required: true, message: '分享标题不能为空', trigger: 'blur' }
          ],
          shareUrl: [
            { required: true, message: '分享url不能为空', trigger: 'blur' }
          ],
          shareContent: [
            { required: true, message: '分享内容不能为空', trigger: 'blur' }
          ],*/
          enable: [
            { required: true, message: '是否启用不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/1/42`),
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
              url: this.$http.adornUrl(`/sharepicturemanage/sharepicturemanage/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.enclosureId = data.sharepicturemanage.enclosureId
                this.dataForm.shareTitle = data.sharepicturemanage.shareTitle
                this.dataForm.shareUrl = data.sharepicturemanage.shareUrl
                this.dataForm.shareContent = data.sharepicturemanage.shareContent
                this.dataForm.enable = data.sharepicturemanage.enable
								if (data.sharepicturemanage.enclosureId != null) {
									this.show = true
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
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/sharepicturemanage/sharepicturemanage/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'enclosureId': this.dataForm.enclosureId ,
							'shareTitle': this.dataForm.shareTitle ,
							'shareUrl': this.dataForm.shareUrl ,
							'shareContent': this.dataForm.shareContent ,
							'enable': this.dataForm.enable ,
					
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
