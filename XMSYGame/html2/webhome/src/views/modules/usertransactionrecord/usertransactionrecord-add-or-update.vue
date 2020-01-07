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
    <el-form-item label="交易类型(0：存款 1:取款 2:冲销 3:返利，4：派奖，5: 额度转换，6：资金归集)" prop="type">
      <el-input v-model="dataForm.type" placeholder="交易类型(0：存款 1:取款 2:冲销 3:返利，4：派奖，5: 额度转换，6：资金归集)"></el-input>
    </el-form-item>
    <el-form-item label="业务ID 订单编号" prop="orderNo">
      <el-input v-model="dataForm.orderNo" placeholder="业务ID 订单编号"></el-input>
    </el-form-item>
    <el-form-item label="交易额" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="交易额"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remake">
      <el-input v-model="dataForm.remake" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="是否是首充（0：不是，1：是）" prop="fristrecharge">
      <el-input v-model="dataForm.fristrecharge" placeholder="是否是首充（0：不是，1：是）"></el-input>
    </el-form-item>
    <el-form-item label="账户余额" prop="money">
      <el-input v-model="dataForm.money" placeholder="账户余额"></el-input>
    </el-form-item>
    <el-form-item label="金币" prop="coin">
      <el-input v-model="dataForm.coin" placeholder="金币"></el-input>
    </el-form-item>
    <el-form-item label="佣金" prop="commission">
      <el-input v-model="dataForm.commission" placeholder="佣金"></el-input>
    </el-form-item>
    <el-form-item label="具体的类型: 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利 19推荐返利)" prop="detailType">
      <el-input v-model="dataForm.detailType" placeholder="具体的类型: 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利 19推荐返利)"></el-input>
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
          type: '',
          orderNo: '',
          amount: '',
          remake: '',
          fristrecharge: '',
          money: '',
          coin: '',
          commission: '',
          detailType: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '交易类型(0：存款 1:取款 2:冲销 3:返利，4：派奖，5: 额度转换，6：资金归集)不能为空', trigger: 'blur' }
          ],
          orderNo: [
            { required: true, message: '业务ID 订单编号不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '交易额不能为空', trigger: 'blur' }
          ],
          remake: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          fristrecharge: [
            { required: true, message: '是否是首充（0：不是，1：是）不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '账户余额不能为空', trigger: 'blur' }
          ],
          coin: [
            { required: true, message: '金币不能为空', trigger: 'blur' }
          ],
          commission: [
            { required: true, message: '佣金不能为空', trigger: 'blur' }
          ],
          detailType: [
            { required: true, message: '具体的类型: 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利 19推荐返利)不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/usertransactionrecord/usertransactionrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.usertransactionrecord.userId
                this.dataForm.userAccount = data.usertransactionrecord.userAccount
                this.dataForm.type = data.usertransactionrecord.type
                this.dataForm.orderNo = data.usertransactionrecord.orderNo
                this.dataForm.amount = data.usertransactionrecord.amount
                this.dataForm.remake = data.usertransactionrecord.remake
                this.dataForm.fristrecharge = data.usertransactionrecord.fristrecharge
                this.dataForm.money = data.usertransactionrecord.money
                this.dataForm.coin = data.usertransactionrecord.coin
                this.dataForm.commission = data.usertransactionrecord.commission
                this.dataForm.detailType = data.usertransactionrecord.detailType
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
              url: this.$http.adornUrl(`/usertransactionrecord/usertransactionrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'type': this.dataForm.type ,

          'orderNo': this.dataForm.orderNo ,

          'amount': this.dataForm.amount ,

          'remake': this.dataForm.remake ,

          'fristrecharge': this.dataForm.fristrecharge ,

          'money': this.dataForm.money ,

          'coin': this.dataForm.coin ,

          'commission': this.dataForm.commission ,

          'detailType': this.dataForm.detailType ,

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
