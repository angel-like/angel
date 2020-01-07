<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
   <el-tooltip class="item" effect="dark" content="0为基层,不可设置" placement="top-start">
				<el-form-item label="上级" prop="parentId" >
					<el-select v-model="dataForm.parentId" placeholder="请选择上级" disabled>
						<el-option
							v-for="item in parentList"
							:key="item.id"
							:label="item.paramKey"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="添加第三方软件名称" placement="top-start">
				<el-form-item label="名称" prop="paramKey">
				  <el-input v-model="dataForm.paramKey" placeholder="key"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="与第三方软件对应的值" placement="top-start">
				<el-form-item label="值" prop="paramValue" >
				  <el-input v-model="dataForm.paramValue" placeholder="value" :disabled="valueType"></el-input>
				</el-form-item>
			</el-tooltip>
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
        dataForm: {
          id: 0,
          paramKey: '',
          paramValue: '',
          parentId: 0,
        },
				valueType:false,
				parentList:[],
        dataRule: {
          paramKey: [
            { required: true, message: 'key不能为空', trigger: 'blur' }
          ],
          paramValue: [
            { required: true, message: 'value不能为空', trigger: 'blur' }
          ],
          parentId: [
            { required: true, message: '备注0为最上级不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id,parentId) {

        this.dataForm.id = id || 0
        this.visible = true
				this.valueType=false
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.dataForm.parentId=parentId
					this.$http({
						url: this.$http.adornUrl(`/sysconfig/sysconfig/select`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.parentList = data.select
						}
					})
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/sysconfig/sysconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
								if(this.dataForm.parentId==0){
									this.valueType=true
								}
                this.dataForm.paramKey = data.sysconfig.paramKey
                this.dataForm.paramValue = data.sysconfig.paramValue
                this.dataForm.parentId = data.sysconfig.parentId
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
              url: this.$http.adornUrl(`/sysconfig/sysconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'paramKey': this.dataForm.paramKey ,

          'paramValue': this.dataForm.paramValue ,

          'parentId': this.dataForm.parentId ,

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
