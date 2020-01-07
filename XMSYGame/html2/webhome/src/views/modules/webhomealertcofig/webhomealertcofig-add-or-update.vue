<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="弹窗广告介绍" placement="top-start">
    <el-form-item label="介绍" prop="introduction">
      <el-input v-model="dataForm.introduction" placeholder="介绍"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="输入跳转的路径" placement="top-start">
		<el-form-item label="附件ID"  v-if="show">
			<el-input v-model="dataForm.enclosureId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="点击图片,跳转路径" placement="top-start">
    <el-form-item label="跳转路径" prop="url">
      <el-input v-model="dataForm.url" placeholder="跳转路径"></el-input>
    </el-form-item>
		</el-tooltip>
    <el-form-item label="排序号" prop="num">
      <el-input-number v-model="dataForm.num"  :min="1"  label="排序号"></el-input-number>
    </el-form-item>
    </el-form>
		<el-tooltip class="item" effect="dark" content="弹窗广告图片" placement="top">
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
    data () {
      return {
        visible: false,
				pictureSize: '',
        dataForm: {
          id: 0,
          introduction: '',
          enclosureId: '',
          url: '',
          num: '',
        },
				fileList: [],
				num: 0,
				show: false,
				form:null,
				successNum: 0,
        dataRule: {
          introduction: [
            { required: true, message: '介绍不能为空', trigger: 'blur' }
          ],
          enclosureId: [
            { required: true, message: '附件ID不能为空', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '跳转路径不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '排序号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				 //下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/14`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
        this.dataForm.id = id || 0
       this.show=false
       this.dataForm.enclosureId=''
       this.visible = true
       this.fileList = []
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomealertcofig/webhomealertcofig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.introduction = data.webhomealertcofig.introduction
                this.dataForm.enclosureId = data.webhomealertcofig.enclosureId
								if(data.webhomealertcofig.enclosureId!=null&&data.webhomealertcofig.enclosureId!=0){
									this.show=true
								}
                this.dataForm.url = data.webhomealertcofig.url
                this.dataForm.num = data.webhomealertcofig.num
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
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomealertcofig/webhomealertcofig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'introduction': this.dataForm.introduction ,

          'enclosureId': this.dataForm.enclosureId ,

          'url': this.dataForm.url ,

          'num': this.dataForm.num ,

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
