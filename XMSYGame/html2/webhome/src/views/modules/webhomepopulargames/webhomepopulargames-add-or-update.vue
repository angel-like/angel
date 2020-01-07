<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="游戏/菜单名称" placement="top-start">
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="简单的介绍游戏有多么多么的火爆" placement="top-start">
    <el-form-item label="介绍" prop="introduction">
      <el-input v-model="dataForm.introduction" placeholder="介绍"></el-input>
    </el-form-item>
		</el-tooltip>
    <el-form-item label="跳转类型" prop="type">
      <el-select v-model="dataForm.type" placeholder="请选择跳转类型" clearable>
      	<el-option
      		v-for="item in typeOptions"
      		:key="item.id"
      		:label="item.name"
      		:value="item.id">
      	</el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="游戏/菜单" prop="typeId" v-if="showEnable">
			<el-select v-model="dataForm.typeId" placeholder="请选择" clearable>
				<el-option
					v-for="item in options"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
    </el-form-item>
		<el-tooltip class="item" effect="dark" content="点击图片,跳转路径" placement="top-start">
    <el-form-item label="路径" prop="url">
      <el-input v-model="dataForm.url" placeholder="路径"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-form-item label="状态" prop="enable">
			<el-radio-group v-model="dataForm.enable">
				<el-radio :label="true">展示</el-radio>
				<el-radio :label="false">不展示</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="排序号" prop="orderNum">
			<el-input-number v-model="dataForm.orderNum"  :min="1"  label="排序号"></el-input-number>
		</el-form-item>
		<el-form-item label="附件ID" prop="enclosureId" v-if="show">
			<el-input v-model="dataForm.enclosureId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
		</el-form-item>
    </el-form>
			<el-tooltip class="item" effect="dark" content="游戏图片" placement="top">
			<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
			:file-list="fileList" :data="form" style="text-align: center;">
				<i class="el-icon-upload"></i>
				<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
				<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
			</el-upload>
			</el-tooltip>
			<!-- <div style="color: red;">上传的图片尺寸为：208*276</div> -->
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
				showEnable:false,
				fileList: [],
				num: 0,
				form:null,
				pictureSize: '',
				show: false,
				successNum: 0,
				typeOptions:[{
          id: 'menu',
          name: '菜单'
        }, {
          id: 'game',
          name: '游戏'
        }],
				options:[],
        dataForm: {
          id: 0,
          name: '',
          enclosureId: '',
          introduction: '',
          type: '',
          typeId: '',
          url: '',
          enable: '',
          orderNum: '',
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          introduction: [
            { required: true, message: '介绍不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型(menu，game）不能为空', trigger: 'blur' }
          ],
          typeId: [
            { required: true, message: '类型Id不能为空', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '路径不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '排序号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
		watch:{
			//监听
			dataForm:{
				handler(val,oldval){
					if(val.type!=''){
						this.getSelectForTypeId(val.type)
						this.showEnable=true
					}
					if(val.type==''){
						this.dataForm.typeId=''
						this.showEnable=false
					}
				},
				deep:true
			}
		},
    methods: {
      init (id) {
				 //下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/13`),
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
              url: this.$http.adornUrl(`/webhomepopulargames/webhomepopulargames/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.webhomepopulargames.name
                this.dataForm.enclosureId = data.webhomepopulargames.enclosureId
								if(data.webhomepopulargames.enclosureId!=null){
									this.show=true
								}
                this.dataForm.introduction = data.webhomepopulargames.introduction
                this.dataForm.type = data.webhomepopulargames.type
                this.dataForm.typeId = data.webhomepopulargames.typeId
                this.dataForm.url = data.webhomepopulargames.url
                this.dataForm.enable = data.webhomepopulargames.enable
                this.dataForm.orderNum = data.webhomepopulargames.orderNum
              }
            })
          }
        })
      },
			getUrl(){
				this.$emit('getUrl',this.dataForm.enclosureId)
			},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomepopulargames/webhomepopulargames/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'enclosureId': this.dataForm.enclosureId ,

          'introduction': this.dataForm.introduction ,

          'type': this.dataForm.type ,

          'typeId': this.dataForm.typeId ,

          'url': this.dataForm.url ,

          'enable': this.dataForm.enable ,

          'orderNum': this.dataForm.orderNum ,

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
      },
			//下拉
			getSelectForTypeId (type) {
				if(type=='menu'){
						this.$http({
							url: this.$http.adornUrl(`/webhomemenu/webhomemenu/getMenuListForSelect`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({data}) => {
							if (data && data.code === 200) {
								this.options = data.list
							}
						});
				}
				if(type=='game'){
						this.$http({
							url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({data}) => {
							if (data && data.code === 200) {
								this.options = data.data
							}
						});
				}
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
    }
  }
</script>
