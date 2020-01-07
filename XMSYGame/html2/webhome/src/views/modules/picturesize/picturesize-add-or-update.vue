<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
   <el-tooltip class="item" effect="dark" content="现有WEB和App" placement="top-start">
   		<el-form-item label="类型" prop="typeId">
   			<el-select v-model="dataForm.typeId" placeholder="类型" clearable>
   				<el-option
   					v-for="item in options"
   					:key="item.id"
   					:label="item.name"
   					:value="item.id">
   				</el-option>
   			</el-select>
   		</el-form-item>
   	</el-tooltip>
		<el-form-item label="菜单" prop="menuId">
			<el-select v-model="dataForm.menuId" placeholder="菜单 " clearable>
				<el-option
					v-for="item in optionsMenu"
					:key="item.code"
					:label="item.name"
					:value="item.code">
				</el-option>
			</el-select>
		</el-form-item>
		<el-form-item label="提示语" prop="tips">
		  <el-input v-model="dataForm.tips" placeholder="提示语"></el-input>
		</el-form-item>
    </el-form>
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
				options: [{
						id:0,
						name:"WEB",
					},{
						id:1,
						name:"App"
					}
				],
				optionsMenu: [],
        dataForm: {
          id: 0,
          typeId: '',
          tips: '',
          menuId: '',
        },
        dataRule: {
          typeId: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          tips: [
            { required: true, message: '提示语不能为空', trigger: 'blur' }
          ],
          menuId: [
            { required: true, message: '菜单id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//为菜单下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/`+"PictureSizeMenu"),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.optionsMenu = data.data
					}
				});
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/picturesize/picturesize/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.typeId = data.picturesize.typeId
                this.dataForm.tips = data.picturesize.tips
                this.dataForm.menuId = data.picturesize.menuId.toString()
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
              url: this.$http.adornUrl(`/picturesize/picturesize/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'typeId': this.dataForm.typeId ,
								'tips': this.dataForm.tips ,
								'menuId': this.dataForm.menuId ,
						
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
