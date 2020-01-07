<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="添加域名(域名可以修改，删除)" placement="top-start">
				<el-form-item label="域名" prop="domain">
				  <el-input v-model="dataForm.domain" placeholder="域名"></el-input>
				</el-form-item>
			</el-tooltip>
     <el-tooltip class="item" effect="dark" content="选择想要绑定的类型" placement="top-start">
    <el-form-item label="类型" prop="type">
			<el-select v-model="dataForm.type" prop="type">
      <el-option v-for="item in options" :key="item.value" :value="item.value" :label="item.label"></el-option>
			</el-select>
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
		   options: [{
        value:  1,
        label: '官网'
      }, {
        value:  2,
        label: '管理后台'
      }, {
        value:  3,
        label: '推广链接'
      }],
        visible: false,
        dataForm: {
          id: 0,
          domain: '',
          type: '',
        },
        dataRule: {
          domain: [
            { required: true, message: '域名不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '1:官网,2:管理后台不能为空,3:推广链接', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/domainmanagement/domainmanagement/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.domain = data.domainmanagement.domain
                this.dataForm.type = data.domainmanagement.type
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
              url: this.$http.adornUrl(`/domainmanagement/domainmanagement/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'domain': this.dataForm.domain ,

          'type': this.dataForm.type ,

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
