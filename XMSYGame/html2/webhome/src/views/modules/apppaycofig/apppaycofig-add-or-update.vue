<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="充值通道名称" placement="top-start">
    <el-form-item label="名称" prop="name">
			<el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="选择充值通道类型" placement="top-start">
		<el-form-item label="通道类型" prop="type">
			<el-select v-model="dataForm.type" placeholder="请选择通道类型">
				<el-option
					v-for="item in options"
					:key="item.value"
					:label="item.label"
					:value="item.value">
				</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="充值客服号" placement="top-start">
    <el-form-item label="客服号" prop="typeNum">
      <el-input v-model="dataForm.typeNum" placeholder="客服号"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-form-item label="状态" prop="enable">
			<el-radio-group v-model="dataForm.enable">
				<el-radio :label="true">启用</el-radio>
				<el-radio :label="false">禁用</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-tooltip class="item" effect="dark" content="提示语内容,例如:复制QQ成功，请到QQ添加好友" placement="top-start">
		<el-form-item label="提示语" prop="tips">
			<el-input v-model="dataForm.tips" placeholder="提示语"></el-input>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="图标路径已加密" placement="top-start">
		<el-form-item label="图标MD值" v-if="show" readOnly>
			<el-input v-model="dataForm.iconMd5" placeholder="图标MD值"></el-input>
		</el-form-item>
		</el-tooltip>
		<el-form-item label="图标ID" prop="icon" v-if="show">
			<el-input v-model="dataForm.icon" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
		</el-form-item>
    </el-form>
		<el-tooltip class="item" effect="dark" content="充值通道用于显示的图片" placement="top">
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
				fileList: [],
				num: 0,
				show: false,
				form:null,
				successNum: 0,
				pictureSize:'',
				options:[{
          value: 1,
          label: '微信通道'
        }, {
          value: 2,
          label: 'QQ通道'
        }, {
          value: 3,
          label: '在线通道'
        }],
        dataForm: {
          id: 0,
          name: '',
          typeNum: '',
					type:'',
					tips:'',
					iconMd5:'',
					enable:true
        },
        dataRule: {
          name: [
            { required: true, message: '客服类型不能为空', trigger: 'blur' }
          ],
          typeNum: [
            { required: true, message: '客服号不能为空', trigger: 'blur' }
          ],
					type: [
						{ required: true, message: '通道类型不能为空', trigger: 'blur' }
					]
        }
      }
    },
    methods: {
      init (id) {
				//下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/1/36`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
				this.show=false
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/apppaycofig/apppaycofig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.apppaycofig.name
                this.dataForm.typeNum = data.apppaycofig.typeNum
								this.dataForm.type = data.apppaycofig.type
								this.dataForm.enable = data.apppaycofig.enable
								this.dataForm.icon = data.apppaycofig.icon
								if(data.apppaycofig.icon!=null){
									this.show=true
								}
								this.dataForm.tips = data.apppaycofig.tips
								this.dataForm.iconMd5 = data.apppaycofig.iconMd5
								
								
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
								this.dataForm.iconMd5 = response.Md5Val
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
              url: this.$http.adornUrl(`/apppaycofig/apppaycofig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,
          'typeNum': this.dataForm.typeNum ,
					'enable': this.dataForm.enable ,
					'type': this.dataForm.type ,
					'icon': this.dataForm.icon ,
					'tips': this.dataForm.tips,
					'iconMd5': this.dataForm.iconMd5,
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
