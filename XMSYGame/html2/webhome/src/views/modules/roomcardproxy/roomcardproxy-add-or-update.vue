<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="显示在按钮上的名称" placement="top-start">
			<el-form-item label="按钮名称" prop="name">
				<el-input v-model="dataForm.name" placeholder="按钮名称"></el-input>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="请选择展示的类型" placement="top-start">
			<el-form-item label="类型" prop="type">
				<el-select v-model="dataForm.type" clearable placeholder="请选择类型"  @change="showContent()">
					<el-option v-for="item in options"
							:key="item.name"
							:label="item.label"
							:value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" v-if="dataForm.type==1"  content="文本内容" placement="top-start">
					<el-form-item label="文本内容" prop="content">
							<el-input v-model="dataForm.content" placeholder="文字"></el-input>
					</el-form-item>
			</el-tooltip>
			</el-form-item>
			<el-form-item label="排序" prop="sort">
				<el-input-number v-model="dataForm.sort" controls-position="right" :min="0" placeholder="排序"></el-input-number>
			</el-form-item>
			 <el-form-item label="图片id" prop="enclosureId" v-if="show">
				<el-input v-model="dataForm.enclosureId" placeholder="图片id"  style="width: 200px;" readOnly></el-input>
				<el-button size="mini" type="primary"  title="查看" @click="getUrl()">预览图片</el-button>
			</el-form-item>
    </el-form>
		<el-tooltip class="item" effect="dark" v-if="dataForm.type==0"  content="房卡代理图片" placement="top">
		<el-upload   drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
			 :file-list="fileList" :data="form" style="text-align: center;" >
			<i class="el-icon-upload"></i>
			<div class="el-upload__text" >将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
			<!-- <div style="color: red;">上传的图片大小为：690*450</div> -->
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
				options:[{
					name:0,
					label:'图片'
				},{
					name:1,
					label:'文字'
				}],
				show:false,
        visible: false,
				fileList: [],
				num: 0,
				form:null,
				successNum: 0,
        dataForm: {
          id: 0,
          name: '',
          enclosureId: '',
          content: '',
          type: 0,
          sort: '',
        },
        dataRule: {
          name: [
            { required: true, message: '按钮名称不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/1/41`),
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
              url: this.$http.adornUrl(`/roomcardproxy/roomcardproxy/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.roomcardproxy.name
                this.dataForm.enclosureId = data.roomcardproxy.enclosureId
                this.dataForm.content = data.roomcardproxy.content
                this.dataForm.type = data.roomcardproxy.type
                this.dataForm.sort = data.roomcardproxy.sort
								if (data.roomcardproxy.enclosureId != null) {
									this.show = true
								}
              }
            })
          }
        })
      },
			//由选择框触发显示哪部分
			showContent(){
				if(this.dataForm.type==0){//如果选择图片，把图片数据显示
					this.show=true;
					this.dataForm.content='';//文本内容设为''
				}
				if(this.dataForm.type==1){//如果选择文本，把图片数据隐藏
					this.show=false;
					this.dataForm.enclosureId='';//图片id设为''
				}
			},
			//加载图片
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
              url: this.$http.adornUrl(`/roomcardproxy/roomcardproxy/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'enclosureId': this.dataForm.enclosureId ,

          'content': this.dataForm.content ,

          'type': this.dataForm.type ,

          'sort': this.dataForm.sort ,

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
