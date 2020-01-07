<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="图片标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="图片标题"></el-input>
    </el-form-item>
		<el-tooltip class="item" effect="dark" content="选择在哪个游戏展示轮播图" placement="top-start">
   <el-form-item label="游戏" prop="gameId" >
   	<el-select v-model="dataForm.gameId" placeholder="请选择" clearable>
   		<el-option
   			v-for="item in options"
   			:key="item.id"
   			:label="item.name"
   			:value="item.id">
   		</el-option>
   	</el-select>
   </el-form-item>
	 </el-tooltip>
		<el-form-item label="附件ID"  v-if="show">
			<el-input v-model="dataForm.enclosureId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
		</el-form-item>
		<el-form-item label="是否启用" prop="enable">
				<el-switch
					v-model="dataForm.enable"
					active-color="#13ce66"
					inactive-color="#ff4949">
				</el-switch>
		</el-form-item>
		<el-form-item label="排序号" prop="orderNo">
			<el-input-number v-model="dataForm.orderNo"  :min="1"  label="排序号"></el-input-number>
		</el-form-item>
    </el-form>
		<el-tooltip class="item" effect="dark" content="轮播图图片" placement="top">
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		:file-list="fileList" :data="form" style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<div style="color: red;">{{pictureSize}}</div>
		<!-- <div style="color: red;">上传的轮播图尺寸为： 830*261    </div> -->
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
				options:[],
				pictureSize: '',
        dataForm: {
          id: 0,
          enclosureId: '',
          title: '',
          enable: true,
          orderNo: '',
          gameId: '',
        },
        dataRule: {
          enclosureId: [
            { required: true, message: '附件不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '图片标题不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态(启用，禁用)不能为空', trigger: 'blur' }
          ],
          orderNo: [
            { required: true, message: '排序号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.data
					}
				});
				 //下拉获取图片尺寸
				this.$http({
									url: this.$http.adornUrl(`/picturesize/picturesize/select/0/8`),
									method: 'get',
									params: this.$http.adornParams()
				}).then(({data}) => {
									if (data && data.code === 200) {
										this.pictureSize = data.pictureSize
									}
				});
        this.dataForm.id = id || 0
				this.fileList = []
        this.visible = true
				this.show=false
				this.dataForm.enclosureId=null
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/imshuffling/imshuffling/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.enclosureId = data.imshuffling.enclosureId
                this.dataForm.title = data.imshuffling.title
                this.dataForm.enable = data.imshuffling.enable
                this.dataForm.orderNo = data.imshuffling.orderNo
                this.dataForm.gameId = data.imshuffling.gameId
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
				if(this.dataForm.enclosureId==null||this.dataForm.enclosureId==0){
					this.$message.error("请上传附件")
					return;
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/imshuffling/imshuffling/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'enclosureId': this.dataForm.enclosureId ,

          'title': this.dataForm.title ,

          'enable': this.dataForm.enable ,

          'orderNo': this.dataForm.orderNo ,

          'gameId': this.dataForm.gameId ,

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
