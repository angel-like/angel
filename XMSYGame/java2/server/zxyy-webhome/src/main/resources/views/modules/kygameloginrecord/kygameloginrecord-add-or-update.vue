<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="用户账号" prop="account">
      <el-input v-model="dataForm.account" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="订单号" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单号"></el-input>
    </el-form-item>
    <el-form-item label="响应体" prop="responseBody">
      <el-input v-model="dataForm.responseBody" placeholder="响应体"></el-input>
    </el-form-item>
    <el-form-item label="游戏id" prop="gameId">
      <el-input v-model="dataForm.gameId" placeholder="游戏id"></el-input>
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
          account: '',
          orderId: '',
          responseBody: '',
          gameId: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          account: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: '订单号不能为空', trigger: 'blur' }
          ],
          responseBody: [
            { required: true, message: '响应体不能为空', trigger: 'blur' }
          ],
          gameId: [
            { required: true, message: '游戏id不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/kygameloginrecord/kygameloginrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.kygameloginrecord.userId
                this.dataForm.account = data.kygameloginrecord.account
                this.dataForm.orderId = data.kygameloginrecord.orderId
                this.dataForm.responseBody = data.kygameloginrecord.responseBody
                this.dataForm.gameId = data.kygameloginrecord.gameId
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
              url: this.$http.adornUrl(`/kygameloginrecord/kygameloginrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'account': this.dataForm.account ,

          'orderId': this.dataForm.orderId ,

          'responseBody': this.dataForm.responseBody ,

          'gameId': this.dataForm.gameId ,

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
