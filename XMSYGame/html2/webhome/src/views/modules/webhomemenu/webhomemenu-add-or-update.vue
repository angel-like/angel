<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
		<div style="margin: -64px 60px 40px 60px;">
<el-tooltip class="item" effect="dark" content="(一级菜单管理)可以点击列表中的菜单名称,进入下一级菜单" placement="top">
<i class="el-icon-question" style="color:blue" ></i>
</el-tooltip>
</div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="上级菜单为系统管理时,级别不可修改" placement="top-start">
		<el-form-item label="级别类型" prop="type">
		<el-select v-model="dataForm.type" placeholder="请选择菜单级别" disabled>
			<el-option
				v-for="item in typeOptions"
				:key="item.id"
				:label="item.name"
				:value="item.id">
			</el-option>
		</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="上级菜单" placement="top-start">
		<el-form-item label="父菜单" prop="parentId" >
			<el-select v-model="dataForm.parentId" placeholder="请选择父菜单 " disabled>
				<el-option
					v-for="item in menuOptions"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="菜单的名称" placement="top-start">
    <el-form-item label="菜单名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="菜单名称"></el-input>
    </el-form-item>
	</el-tooltip>
	<el-tooltip class="item" effect="dark" content="菜单的图标" placement="top-start">
    <el-form-item label="菜单图标" prop="icon" v-if="iconShow" >
      <el-input v-model="dataForm.icon" placeholder="菜单图标" readOnly style="width: 200px;" ></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
    </el-form-item>
	</el-tooltip>
	<el-tooltip class="item" effect="dark" content="用英文名或十六位字符编码表示.如:red,#160000" placement="top-start">
		<el-form-item label="颜色" prop="menuColor">
			<el-input v-model="dataForm.menuColor" placeholder="颜色"></el-input>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="菜单的英文名称" placement="top-start">
		<el-form-item label="英文名称" prop="englishName">
			<el-input v-model="dataForm.englishName" placeholder="英文名称"></el-input>
		</el-form-item>
		</el-tooltip>
		<el-form-item label="排序" prop="orderNum">
			<el-input-number v-model="dataForm.orderNum"  :min="1"  label="排序号"></el-input-number>
		</el-form-item>
		
    </el-form>
		<el-tooltip class="item" effect="dark" content="菜单图片" placement="top">
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
				typeOptions: [{
					id: 0,
					name: '一级菜单'
				}, {
					id: 1,
					name: '二级菜单'
					
				}, {
					id: 2,
					name: '三级菜单'
				}],
				menuOptions:[{
					id: 0,
					name: '系统管理'
				}],
				pictureSize: '',
				form:null,
				fileList: [],
				iconShow:false,
				num: 0,
				successNum: 0,
        visible: false,
        dataForm: {
          id: 0,
          parentId: 0,          name: '',         type: 0,          orderNum: '',   englishName:''  ,   menuType: '' , menuColor: '' , icon:''    },
        dataRule: {
          parentId: [
            { required: true, message: '父菜单ID，一级菜单从0开始不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '菜单名称不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          menuType: [
            { required: true, message: '菜单类型不能为空', trigger: 'blur' }
          ]
        }
      }
    },
		//监听
		watch:{
			//监听
			dataForm:{
				handler(val,oldval){
					if(val.icon!=''){
						this.iconShow=true
					}
					if(val.icon==''){
						this.iconShow=false
					}
					
				},
				deep:true
			}
		},
    methods: {
      init (id) {
		   //下拉获取图片尺寸
		this.$http({
			url: this.$http.adornUrl(`/picturesize/picturesize/select/0/17`),
			method: 'get',
			params: this.$http.adornParams()
		  }).then(({data}) => {
			if (data && data.code === 200) {
				this.pictureSize = data.pictureSize
			}
		});
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomemenu/webhomemenu/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.parentId = data.webhomeMenu.parentId
                this.dataForm.name = data.webhomeMenu.name
                this.dataForm.englishName = data.webhomeMenu.englishName
                this.dataForm.type = data.webhomeMenu.type
                this.dataForm.icon = data.webhomeMenu.icon
                this.dataForm.orderNum = data.webhomeMenu.orderNum
								this.dataForm.menuColor = data.webhomeMenu.menuColor
              }
            })
          }
        })
      },
			getUrl(){
				this.$emit('getUrl',this.dataForm.icon)
			},
			UploadUrl:function(){
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;     
			},  
				// 照片上传之前
				beforeUploadHandle (file) {
					
					this.num++
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
						this.dataForm.icon=response.id
					} else {
						this.$message.error(response.msg)
					}
				},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomemenu/webhomemenu/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'parentId': this.dataForm.parentId ,
          'name': this.dataForm.name ,
          'englishName': this.dataForm.englishName ,
          'type': this.dataForm.type, 
          'icon': this.dataForm.icon, 
          'orderNum': this.dataForm.orderNum, 
          'menuType': this.dataForm.menuType,
					'menuColor': this.dataForm.menuColor 
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
