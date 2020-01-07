<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="vip等级id" prop="vipId">
      <el-input v-model="dataForm.vipId" placeholder="vip等级id"></el-input>
    </el-form-item>
    <el-form-item label="已经充值" prop="rechargeAmount">
      <el-input v-model="dataForm.rechargeAmount" placeholder="已经充值"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="用户名称" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户名称"></el-input>
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
          vipId: '',
          rechargeAmount: '',
          userId: '',
          userAccount: '',
        },
        dataRule: {
          vipId: [
            { required: true, message: 'vip等级id不能为空', trigger: 'blur' }
          ],
          rechargeAmount: [
            { required: true, message: '已经充值不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户名称不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userviprecord/userviprecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.vipId = data.userviprecord.vipId
                this.dataForm.rechargeAmount = data.userviprecord.rechargeAmount
                this.dataForm.userId = data.userviprecord.userId
                this.dataForm.userAccount = data.userviprecord.userAccount
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
              url: this.$http.adornUrl(`/userviprecord/userviprecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'vipId': this.dataForm.vipId ,

          'rechargeAmount': this.dataForm.rechargeAmount ,

          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

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
