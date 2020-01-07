<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="代理账户" prop="proxyAccount">
      <el-input v-model="dataForm.proxyAccount" placeholder="代理账户"></el-input>
    </el-form-item>
    <el-form-item label="交易类型" prop="type">
      <el-input v-model="dataForm.type" placeholder="交易类型"></el-input>
    </el-form-item>
    <el-form-item label="订单编号" prop="orderNo">
      <el-input v-model="dataForm.orderNo" placeholder="订单编号"></el-input>
    </el-form-item>
    <el-form-item label="交易额" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="交易额"></el-input>
    </el-form-item>
    <el-form-item label="代理商余额" prop="proxyBalance">
      <el-input v-model="dataForm.proxyBalance" placeholder="代理商余额"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remake">
      <el-input v-model="dataForm.remake" placeholder="备注"></el-input>
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
          proxyAccount: '',
          type: '',
          orderNo: '',
          amount: '',
          proxyBalance: '',
          remake: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          proxyAccount: [
            { required: true, message: '代理账户不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '交易类型不能为空', trigger: 'blur' }
          ],
          orderNo: [
            { required: true, message: '订单编号不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '交易额不能为空', trigger: 'blur' }
          ],
          proxyBalance: [
            { required: true, message: '代理商余额不能为空', trigger: 'blur' }
          ],
          remake: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/proxyordertransactionrecord/proxyordertransactionrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.proxyordertransactionrecord.userId
                this.dataForm.proxyAccount = data.proxyordertransactionrecord.proxyAccount
                this.dataForm.type = data.proxyordertransactionrecord.type
                this.dataForm.orderNo = data.proxyordertransactionrecord.orderNo
                this.dataForm.amount = data.proxyordertransactionrecord.amount
                this.dataForm.proxyBalance = data.proxyordertransactionrecord.proxyBalance
                this.dataForm.remake = data.proxyordertransactionrecord.remake
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
              url: this.$http.adornUrl(`/proxyordertransactionrecord/proxyordertransactionrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'proxyAccount': this.dataForm.proxyAccount ,

          'type': this.dataForm.type ,

          'orderNo': this.dataForm.orderNo ,

          'amount': this.dataForm.amount ,

          'proxyBalance': this.dataForm.proxyBalance ,

          'remake': this.dataForm.remake ,

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
