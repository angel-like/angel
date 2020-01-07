<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单ID" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单ID"></el-input>
    </el-form-item>
    <el-form-item label="交易流水号" prop="transactionNo">
      <el-input v-model="dataForm.transactionNo" placeholder="交易流水号"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="商户APPID" prop="appId">
      <el-input v-model="dataForm.appId" placeholder="商户APPID"></el-input>
    </el-form-item>
    <el-form-item label="支付平台订单号" prop="billno">
      <el-input v-model="dataForm.billno" placeholder="支付平台订单号"></el-input>
    </el-form-item>
    <el-form-item label="签名" prop="sign">
      <el-input v-model="dataForm.sign" placeholder="签名"></el-input>
    </el-form-item>
    <el-form-item label="附属" prop="attach">
      <el-input v-model="dataForm.attach" placeholder="附属"></el-input>
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
          orderId: '',
          transactionNo: '',
          amount: '',
          appId: '',
          billno: '',
          sign: '',
          attach: '',
        },
        dataRule: {
          orderId: [
            { required: true, message: '订单ID不能为空', trigger: 'blur' }
          ],
          transactionNo: [
            { required: true, message: '交易流水号不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          appId: [
            { required: true, message: '商户APPID不能为空', trigger: 'blur' }
          ],
          billno: [
            { required: true, message: '支付平台订单号不能为空', trigger: 'blur' }
          ],
          sign: [
            { required: true, message: '签名不能为空', trigger: 'blur' }
          ],
          attach: [
            { required: true, message: '附属不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/rechargecallbackorder/rechargecallbackorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.orderId = data.rechargecallbackorder.orderId
                this.dataForm.transactionNo = data.rechargecallbackorder.transactionNo
                this.dataForm.amount = data.rechargecallbackorder.amount
                this.dataForm.appId = data.rechargecallbackorder.appId
                this.dataForm.billno = data.rechargecallbackorder.billno
                this.dataForm.sign = data.rechargecallbackorder.sign
                this.dataForm.attach = data.rechargecallbackorder.attach
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
              url: this.$http.adornUrl(`/rechargecallbackorder/rechargecallbackorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'orderId': this.dataForm.orderId ,

          'transactionNo': this.dataForm.transactionNo ,

          'amount': this.dataForm.amount ,

          'appId': this.dataForm.appId ,

          'billno': this.dataForm.billno ,

          'sign': this.dataForm.sign ,

          'attach': this.dataForm.attach ,

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
