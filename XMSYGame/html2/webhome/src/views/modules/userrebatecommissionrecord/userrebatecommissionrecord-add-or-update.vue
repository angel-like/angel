<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="用户账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="佣金" prop="commission">
      <el-input v-model="dataForm.commission" placeholder="佣金"></el-input>
    </el-form-item>
    <el-form-item label="供应佣金的用户ID" prop="provideUserId">
      <el-input v-model="dataForm.provideUserId" placeholder="供应佣金的用户ID"></el-input>
    </el-form-item>
    <el-form-item label="供应佣金的用户账号" prop="provideUserAccount">
      <el-input v-model="dataForm.provideUserAccount" placeholder="供应佣金的用户账号"></el-input>
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
        dataForm: {
          id: 0,
          userId: '',
          userAccount: '',
          commission: '',
          provideUserId: '',
          provideUserAccount: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          commission: [
            { required: true, message: '佣金不能为空', trigger: 'blur' }
          ],
          provideUserId: [
            { required: true, message: '供应佣金的用户ID不能为空', trigger: 'blur' }
          ],
          provideUserAccount: [
            { required: true, message: '供应佣金的用户账号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userrebatecommissionrecord/userrebatecommissionrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.userrebatecommissionrecord.userId
                this.dataForm.userAccount = data.userrebatecommissionrecord.userAccount
                this.dataForm.commission = data.userrebatecommissionrecord.commission
                this.dataForm.provideUserId = data.userrebatecommissionrecord.provideUserId
                this.dataForm.provideUserAccount = data.userrebatecommissionrecord.provideUserAccount
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
              url: this.$http.adornUrl(`/userrebatecommissionrecord/userrebatecommissionrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'commission': this.dataForm.commission ,

          'provideUserId': this.dataForm.provideUserId ,

          'provideUserAccount': this.dataForm.provideUserAccount ,

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
