<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="支付人账号" prop="account">
      <el-input v-model="dataForm.account" placeholder="支付人账号"></el-input>
    </el-form-item>
    <el-form-item label="支付人Id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="支付人Id"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="" prop="remake">
      <el-input v-model="dataForm.remake" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="订单状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="订单状态"></el-input>
    </el-form-item>
    <el-form-item label="订单ID" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单ID"></el-input>
    </el-form-item>
    <el-form-item label="支付方订单号" prop="merchantOrderId">
      <el-input v-model="dataForm.merchantOrderId" placeholder="支付方订单号"></el-input>
    </el-form-item>
    <el-form-item label="支付平台" prop="platform">
      <el-input v-model="dataForm.platform" placeholder="支付平台"></el-input>
    </el-form-item>
    <el-form-item label="属性(app,web)" prop="type">
      <el-input v-model="dataForm.type" placeholder="属性(app,web)"></el-input>
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
          account: '',
          userId: '',
          amount: '',
          remake: '',
          status: '',
          orderId: '',
          merchantOrderId: '',
          platform: '',
          type: '',
        },
        dataRule: {
          account: [
            { required: true, message: '支付人账号不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '支付人Id不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          remake: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '订单状态不能为空', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: '订单ID不能为空', trigger: 'blur' }
          ],
          merchantOrderId: [
            { required: true, message: '支付方订单号不能为空', trigger: 'blur' }
          ],
          platform: [
            { required: true, message: '支付平台不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '属性(app,web)不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/rechargepayorder/rechargepayorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.account = data.rechargepayorder.account
                this.dataForm.userId = data.rechargepayorder.userId
                this.dataForm.amount = data.rechargepayorder.amount
                this.dataForm.remake = data.rechargepayorder.remake
                this.dataForm.status = data.rechargepayorder.status
                this.dataForm.orderId = data.rechargepayorder.orderId
                this.dataForm.merchantOrderId = data.rechargepayorder.merchantOrderId
                this.dataForm.platform = data.rechargepayorder.platform
                this.dataForm.type = data.rechargepayorder.type
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
              url: this.$http.adornUrl(`/rechargepayorder/rechargepayorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'account': this.dataForm.account ,

          'userId': this.dataForm.userId ,

          'amount': this.dataForm.amount ,

          'remake': this.dataForm.remake ,

          'status': this.dataForm.status ,

          'orderId': this.dataForm.orderId ,

          'merchantOrderId': this.dataForm.merchantOrderId ,

          'platform': this.dataForm.platform ,

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
