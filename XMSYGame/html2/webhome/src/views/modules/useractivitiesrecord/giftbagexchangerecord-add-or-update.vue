<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="兑换用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="兑换用户id"></el-input>
    </el-form-item>
    <el-form-item label="用户账户" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户账户"></el-input>
    </el-form-item>
    <el-form-item label="用户名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户名称"></el-input>
    </el-form-item>
    <el-form-item label="兑换码" prop="exchangeCode">
      <el-input v-model="dataForm.exchangeCode" placeholder="兑换码"></el-input>
    </el-form-item>
    <el-form-item label="兑换时间" prop="exchangeTime">
      <el-input v-model="dataForm.exchangeTime" placeholder="兑换时间"></el-input>
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
          userName: '',
          exchangeCode: '',
          exchangeTime: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '兑换用户id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户账户不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '用户名称不能为空', trigger: 'blur' }
          ],
          exchangeCode: [
            { required: true, message: '兑换码不能为空', trigger: 'blur' }
          ],
          exchangeTime: [
            { required: true, message: '兑换时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/giftbagexchangerecord/giftbagexchangerecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.giftbagexchangerecord.userId
                this.dataForm.userAccount = data.giftbagexchangerecord.userAccount
                this.dataForm.userName = data.giftbagexchangerecord.userName
                this.dataForm.exchangeCode = data.giftbagexchangerecord.exchangeCode
                this.dataForm.exchangeTime = data.giftbagexchangerecord.exchangeTime
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
              url: this.$http.adornUrl(`/giftbagexchangerecord/giftbagexchangerecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'userName': this.dataForm.userName ,

          'exchangeCode': this.dataForm.exchangeCode ,

          'exchangeTime': this.dataForm.exchangeTime ,

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
