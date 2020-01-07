<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="友情链接ID" prop="friendshipId" v-if="false">
      <el-input v-model="dataForm.friendshipId" placeholder="友情链接ID"></el-input>
    </el-form-item>
    <el-form-item label="内容" prop="content">
			<el-input
				type="textarea"
				:rows="8"
				placeholder="请输入内容"
				v-model="dataForm.content">
			</el-input>
    </el-form-item>
		<el-form-item label="图标ID"  v-if="show">
			<el-input v-model="dataForm.icon" placeholder="图标ID"   style="width: 200px;"  readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览</el-button>
		</el-form-item>
    </el-form>
			<el-tooltip class="item" effect="dark" content="友情链接图片" placement="top">
			<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
			:file-list="fileList" :data="form" style="text-align: center;">
				<i class="el-icon-upload"></i>
				<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
				<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
			</el-upload>
			</el-tooltip>
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
        dataForm: {
          id: 0,
          friendshipId: '',
          content: '',
          icon: '',
        },
				fileList: [],
				num: 0,
				show: false,
				form:null,
				successNum: 0,
        dataRule: {
          friendshipId: [
            { required: true, message: '友情链接ID不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '图标ID不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id,friendshipId) {
				this.show=false
				this.dataForm.enclosureId=''
				this.visible = true
				this.fileList = []
				this.dataForm.friendshipId = friendshipId
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomefriendshiptemplate/webhomefriendshiptemplate/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.friendshipId = data.webhomefriendshiptemplate.friendshipId
                this.dataForm.content = data.webhomefriendshiptemplate.content
                this.dataForm.icon = data.webhomefriendshiptemplate.icon
								if(data.webhomefriendshiptemplate.icon!=null&&data.webhomefriendship.icon!=0){
									this.show=true
								}
              }
            })
          }
        })
      },
			getUrl(){
							this.$emit('getUrl',this.dataForm.icon)
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
								this.dataForm.icon = response.id
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
              url: this.$http.adornUrl(`/webhomefriendshiptemplate/webhomefriendshiptemplate/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'friendshipId': this.dataForm.friendshipId ,

          'content': this.dataForm.content ,

          'icon': this.dataForm.icon ,

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
