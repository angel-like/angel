<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
		<div style="margin: -64px 60px 40px 60px;">
					<el-tooltip class="item" effect="dark" content="在相对应的菜单下添加/修改游戏" placement="top">
						<i class="el-icon-question" style="color:blue" ></i>
					</el-tooltip>
		</div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="菜单ID" prop="menuId" v-if="false">
      <el-input v-model="dataForm.menuId" placeholder="菜单ID"></el-input>
    </el-form-item>
		<el-tooltip class="item" effect="dark" content="游戏名称" placement="top-start">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="对游戏的简单介绍" placement="top-start">
    <el-form-item label="简介" prop="synopsis">
      <el-input v-model="dataForm.synopsis" placeholder="简介"></el-input>
    </el-form-item>
	</el-tooltip>
		<el-form-item label="游戏列表" prop="gameId">
			<el-select v-model="dataForm.gameId" placeholder="请选择游戏 " clearable>
				<el-option
					v-for="item in option"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
		</el-form-item>
		<el-form-item label="排序" prop="orderNum">
			<el-input-number v-model="dataForm.orderNum"  :min="1"  label="排序号"></el-input-number>
		</el-form-item>
		<el-form-item label="图片ID" prop="enclosureId" v-if="enclosureShow">
			<el-input v-model="dataForm.enclosureId" placeholder="图片ID"  style="width: 200px;" ></el-input>
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
		<!-- <div style="color: red;">上传的图片尺寸为： 208*276</div> -->
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
				fileList: [],
				num: 0,
				option:[],
				enclosureShow:false,
				successNum: 0,
        visible: false,
				activeClass:0,
				form:null, 
				pictureSize: '',
        dataForm: {
          id: 0,
          menuId: '',          title: '',          synopsis: '' ,              enclosureId: '',          orderNum: '',          gameId: ''        },
        dataRule: {
          menuId: [
            { required: true, message: '菜单ID不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          synopsis: [
            { required: true, message: '简介不能为空', trigger: 'blur' }
          ],
          
          orderNum: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ]
          
        }
      }
    },
		watch:{
			//监听
			dataForm:{
				handler(val,oldval){
				if(val.enclosureId==null||val.enclosureId==''){
					this.enclosureShow=false
				}else{
					this.enclosureShow=true
				}
				},
				deep:true
			}
		},
    methods: {
			UploadUrl:function(){
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;     
			},  
				// 照片上传之前
				beforeUploadHandle (file) {
					if(this.fileList!=null&&this.fileList!=''){
						this.$message.error('一次只能上传一个附件！')
						return false
					}
					this.num++
				},
				getUrl(){
					this.$emit('getUrl',this.dataForm.enclosureId)
				},
				// 照片上传成功
				successHandle (response, file, fileList,type) {
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
						this.dataForm.enclosureId=response.id
					} else {
						this.$message.error(response.msg)
					}
				},
      init (id,menuId,activeClass) {
				//下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/6`),
					method: 'get',
					params: this.$http.adornParams()
				  }).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
				this.dataForm.menuId=menuId
				this.activeClass=activeClass
				//为游戏下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.option = data.data
					}
				});
				this.dataForm.menuId = menuId
        this.dataForm.id = id || 0
				this.fileList=[]
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomemenutemplate/webhomemenutemplate/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.menuId = data.webhomeMenuTemplate.menuId
                this.dataForm.title = data.webhomeMenuTemplate.title
                this.dataForm.synopsis = data.webhomeMenuTemplate.synopsis
                this.dataForm.enclosureId = data.webhomeMenuTemplate.enclosureId
                this.dataForm.orderNum = data.webhomeMenuTemplate.orderNum
								if(data.webhomeMenuTemplate.gameId!=0){
									this.dataForm.gameId = data.webhomeMenuTemplate.gameId
								}
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomemenutemplate/webhomemenutemplate/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'menuId': this.dataForm.menuId ,
          'title': this.dataForm.title ,
          'synopsis': this.dataForm.synopsis, 
          'enclosureId': this.dataForm.enclosureId, 
          'orderNum': this.dataForm.orderNum,
					'gameId': this.dataForm.gameId
              })
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
										this.$emit('refreshDataList',this.dataForm.menuId,this.activeClass)
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
