<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="交易金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="交易金额"></el-input>
    </el-form-item>
    <el-form-item label="取出金额" prop="takeMoney">
      <el-input v-model="dataForm.takeMoney" placeholder="取出金额"></el-input>
    </el-form-item>
    <el-form-item label="是否生效" prop="effect">
      <el-input v-model="dataForm.effect" placeholder="是否生效"></el-input>
    </el-form-item>
    <el-form-item label="类型 0:存入1:取出" prop="type">
      <el-input v-model="dataForm.type" placeholder="类型 0:存入1:取出"></el-input>
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
          money: '',
          takeMoney: '',
          effect: '',
          type: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '交易金额不能为空', trigger: 'blur' }
          ],
          takeMoney: [
            { required: true, message: '取出金额不能为空', trigger: 'blur' }
          ],
          effect: [
            { required: true, message: '是否生效不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型 0:存入1:取出不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userbalancerecord/userbalancerecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.userbalancerecord.userId
                this.dataForm.money = data.userbalancerecord.money
                this.dataForm.takeMoney = data.userbalancerecord.takeMoney
                this.dataForm.effect = data.userbalancerecord.effect
                this.dataForm.type = data.userbalancerecord.type
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
              url: this.$http.adornUrl(`/userbalancerecord/userbalancerecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'money': this.dataForm.money ,

          'takeMoney': this.dataForm.takeMoney ,

          'effect': this.dataForm.effect ,

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
