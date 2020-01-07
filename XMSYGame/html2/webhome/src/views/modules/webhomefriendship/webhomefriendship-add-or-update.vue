<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="友情链接名称,该名称对应官网底下位置.例如名称为'关于我们',点击'关于我们',跳转到'关于我们'相关页面" placement="top-start">
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
	</el-tooltip>
     <el-tooltip class="item" effect="dark" content="用英文名称或十六进制编码,如:red,#160000" placement="top-start">
		<el-form-item label="颜色" prop="color">
			<el-input v-model="dataForm.color"   placeholder="颜色"></el-input>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="内容跳转属于内容讲解,路径跳转则跳转到相对应的路径" placement="top-start">
		<el-form-item label="跳转类型" prop="type">
			<el-select v-model="dataForm.type" clearable placeholder="请选择跳转类型">
				<el-option 
				v-for="item in options" 
				:key="item.id" 
				:label="item.type" 
				:value="item.id">
				</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="跳转的路径链接" placement="top-start">
		<el-form-item label="链接" v-if="urlShow">
			<el-input v-model="dataForm.url" placeholder="链接"></el-input>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="图标ID" placement="top-start">
		<el-form-item label="图标ID"  v-if="show">
			<el-input v-model="dataForm.icon" placeholder="图标ID"   style="width: 200px;"  readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览</el-button>
		</el-form-item>
		</el-tooltip>
		<el-form-item label="排序" prop="orderNum">
			<el-input-number v-model="dataForm.orderNum"  :min="1"  label="排序号"></el-input-number>
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
				urlShow:false,
				fileList: [],
				num: 0,
				show: false,
				form:null,
				successNum: 0,
				pictureSize: '',
        dataForm: {
          id: 0,
          name: '',
					orderNum:'',
					type:'',
					color:'',
					url:'',
					icon:''
        },
				options: [{
					id: 1,
					type: '路径跳转',
				}, {
					id: 2,
					type: '内容跳转'
				}],
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
					url:[
						{ required: true, message: '路径不能为空', trigger: 'blur' }
					],
					orderNum:[
						{ required: true, message: '排序不能为空', trigger: 'blur' }
					],
					type:[
						{ required: true, message: '跳转类型不能为空', trigger: 'blur' }
					]
					
        }
      }
    },
		watch:{
			//监听
			dataForm:{
				handler(val,oldval){
				if(val.type==1){
					this.urlShow=true
				}else{
					this.urlShow=false
				}
				},
				deep:true
			}
		},
    methods: {
      init (id) {
		   //下拉获取图片尺寸
		  this.$http({
		  	url: this.$http.adornUrl(`/picturesize/picturesize/select/0/20`),
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
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomefriendship/webhomefriendship/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.webhomefriendship.name
                this.dataForm.url = data.webhomefriendship.url
								this.dataForm.orderNum = data.webhomefriendship.orderNum
								this.dataForm.color = data.webhomefriendship.color
								this.dataForm.type = data.webhomefriendship.type
								this.dataForm.icon = data.webhomefriendship.icon
								if(data.webhomefriendship.icon!=null&&data.webhomefriendship.icon!=0){
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
				var jumpUrl;
				if(this.dataForm.type==1){
					jumpUrl=this.dataForm.type
				}else{
					jumpUrl=''
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomefriendship/webhomefriendship/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,
          'url': jumpUrl ,
					'orderNum': this.dataForm.orderNum ,
					'type': this.dataForm.type ,
					'color': this.dataForm.color ,
					'icon': this.dataForm.icon 

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
