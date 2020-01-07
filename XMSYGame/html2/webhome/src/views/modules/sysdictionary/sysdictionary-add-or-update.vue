<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="上级" prop="parentCode">
			<el-select v-model="dataForm.parentCode" placeholder="请选择上级">
				<el-option
					v-for="item in parentList"
					:key="item.code"
					:label="item.name"
					:value="item.code">
				</el-option>
			</el-select>
    </el-form-item>
		<el-form-item label="代码" prop="code">
      <el-input v-model="dataForm.code" placeholder="代码"></el-input>
    </el-form-item>
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="是否可用" prop="enable">
			<el-radio-group v-model="dataForm.enable">
				<el-radio :label="true">是</el-radio>
				<el-radio :label="false">否</el-radio>
			</el-radio-group>
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
				parentList:[],
        dataForm: {
          id: 0,
          code: '',
          name: '',
          parentCode: '',
          enable: true
        },
        dataRule: {
          code: [
            { required: true, message: 'code不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          parentCode: [
            { required: true, message: '上级不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '是否可用不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id,parentCode) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.dataForm.parentCode = parentCode
					this.$http({
						url: this.$http.adornUrl(`/sysdictionary/sysdictionary/getParentList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.parentList = data.dictionaryList
						}
					})
					
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/sysdictionary/sysdictionary/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.code = data.sysdictionary.code
                this.dataForm.name = data.sysdictionary.name
                this.dataForm.parentCode = data.sysdictionary.parentCode
                this.dataForm.enable = data.sysdictionary.enable
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
              url: this.$http.adornUrl(`/sysdictionary/sysdictionary/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'code': this.dataForm.code ,
								'name': this.dataForm.name ,
								'parentCode': this.dataForm.parentCode ,
								'enable': this.dataForm.enable 
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
