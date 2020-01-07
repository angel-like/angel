<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="存款人" prop="name">
      <el-input v-model="dataForm.name" placeholder="存款人"></el-input>
    </el-form-item>
    <el-form-item label="存款时间" prop="depositDate">
      <el-input v-model="dataForm.depositDate" placeholder="存款时间"></el-input>
    </el-form-item>
    <el-form-item label="存款金额" prop="depositAmount">
      <el-input v-model="dataForm.depositAmount" placeholder="存款金额"></el-input>
    </el-form-item>
    <el-form-item label="存款银行" prop="depositBlank">
      <el-input v-model="dataForm.depositBlank" placeholder="存款银行"></el-input>
    </el-form-item>
    <el-form-item label="收款银行" prop="incomeBank">
      <el-input v-model="dataForm.incomeBank" placeholder="收款银行"></el-input>
    </el-form-item>
    <el-form-item label="收款账号" prop="bankAccount">
      <el-input v-model="dataForm.bankAccount" placeholder="收款账号"></el-input>
    </el-form-item>
    <el-form-item label="收款人" prop="payee">
      <el-input v-model="dataForm.payee" placeholder="收款人"></el-input>
    </el-form-item>
    <el-form-item label="开户网点" prop="openBank">
      <el-input v-model="dataForm.openBank" placeholder="开户网点"></el-input>
    </el-form-item>
    <el-form-item label="订单状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="订单状态"></el-input>
    </el-form-item>
    <el-form-item label="存款方式" prop="depositType">
      <el-input v-model="dataForm.depositType" placeholder="存款方式"></el-input>
    </el-form-item>
    <el-form-item label="" prop="userId">
      <el-input v-model="dataForm.userId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="优惠金额" prop="discount">
      <el-input v-model="dataForm.discount" placeholder="优惠金额"></el-input>
    </el-form-item>
    <el-form-item label="是否审核" prop="examine">
      <el-input v-model="dataForm.examine" placeholder="是否审核"></el-input>
    </el-form-item>
    <el-form-item label="充值时间" prop="rechargeDate">
      <el-input v-model="dataForm.rechargeDate" placeholder="充值时间"></el-input>
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
          name: '',
          depositDate: '',
          depositAmount: '',
          depositBlank: '',
          incomeBank: '',
          bankAccount: '',
          payee: '',
          openBank: '',
          status: '',
          depositType: '',
          userId: '',
          discount: '',
          examine: '',
          rechargeDate: '',
        },
        dataRule: {
          name: [
            { required: true, message: '存款人不能为空', trigger: 'blur' }
          ],
          depositDate: [
            { required: true, message: '存款时间不能为空', trigger: 'blur' }
          ],
          depositAmount: [
            { required: true, message: '存款金额不能为空', trigger: 'blur' }
          ],
          depositBlank: [
            { required: true, message: '存款银行不能为空', trigger: 'blur' }
          ],
          incomeBank: [
            { required: true, message: '收款银行不能为空', trigger: 'blur' }
          ],
          bankAccount: [
            { required: true, message: '收款账号不能为空', trigger: 'blur' }
          ],
          payee: [
            { required: true, message: '收款人不能为空', trigger: 'blur' }
          ],
          openBank: [
            { required: true, message: '开户网点不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '订单状态不能为空', trigger: 'blur' }
          ],
          depositType: [
            { required: true, message: '存款方式不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          discount: [
            { required: true, message: '优惠金额不能为空', trigger: 'blur' }
          ],
          examine: [
            { required: true, message: '是否审核不能为空', trigger: 'blur' }
          ],
          rechargeDate: [
            { required: true, message: '充值时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/orderbankrecharge/orderbankrecharge/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.orderbankrecharge.name
                this.dataForm.depositDate = data.orderbankrecharge.depositDate
                this.dataForm.depositAmount = data.orderbankrecharge.depositAmount
                this.dataForm.depositBlank = data.orderbankrecharge.depositBlank
                this.dataForm.incomeBank = data.orderbankrecharge.incomeBank
                this.dataForm.bankAccount = data.orderbankrecharge.bankAccount
                this.dataForm.payee = data.orderbankrecharge.payee
                this.dataForm.openBank = data.orderbankrecharge.openBank
                this.dataForm.status = data.orderbankrecharge.status
                this.dataForm.depositType = data.orderbankrecharge.depositType
                this.dataForm.userId = data.orderbankrecharge.userId
                this.dataForm.discount = data.orderbankrecharge.discount
                this.dataForm.examine = data.orderbankrecharge.examine
                this.dataForm.rechargeDate = data.orderbankrecharge.rechargeDate
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
              url: this.$http.adornUrl(`/orderbankrecharge/orderbankrecharge/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'depositDate': this.dataForm.depositDate ,

          'depositAmount': this.dataForm.depositAmount ,

          'depositBlank': this.dataForm.depositBlank ,

          'incomeBank': this.dataForm.incomeBank ,

          'bankAccount': this.dataForm.bankAccount ,

          'payee': this.dataForm.payee ,

          'openBank': this.dataForm.openBank ,

          'status': this.dataForm.status ,

          'depositType': this.dataForm.depositType ,

          'userId': this.dataForm.userId ,

          'discount': this.dataForm.discount ,

          'examine': this.dataForm.examine ,

          'rechargeDate': this.dataForm.rechargeDate ,

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
