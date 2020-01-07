<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="取款用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="取款用户id"></el-input>
    </el-form-item>
    <el-form-item label="取款账号" prop="account">
      <el-input v-model="dataForm.account" placeholder="取款账号"></el-input>
    </el-form-item>
    <el-form-item label="取款金额" prop="takeAmount">
      <el-input v-model="dataForm.takeAmount" placeholder="取款金额"></el-input>
    </el-form-item>
    <el-form-item label="手续费" prop="poundage">
      <el-input v-model="dataForm.poundage" placeholder="手续费"></el-input>
    </el-form-item>
    <el-form-item label="实际得到的金额" prop="obtainAmount">
      <el-input v-model="dataForm.obtainAmount" placeholder="实际得到的金额"></el-input>
    </el-form-item>
    <el-form-item label="收入账号" prop="incomeNo">
      <el-input v-model="dataForm.incomeNo" placeholder="收入账号"></el-input>
    </el-form-item>
    <el-form-item label="银行名称" prop="bankName">
      <el-input v-model="dataForm.bankName" placeholder="银行名称"></el-input>
    </el-form-item>
    <el-form-item label="开户人" prop="accountName">
      <el-input v-model="dataForm.accountName" placeholder="开户人"></el-input>
    </el-form-item>
    <el-form-item label="状态(0:未审核，1：通过，2.取消)" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态(0:未审核，1：通过，2.取消)"></el-input>
    </el-form-item>
    <el-form-item label="取款审核ID" prop="takeMoneyId">
      <el-input v-model="dataForm.takeMoneyId" placeholder="取款审核ID"></el-input>
    </el-form-item>
    <el-form-item label="取款类型(1:银行转账，2：支付宝转账，3：其它)" prop="accountType">
      <el-input v-model="dataForm.accountType" placeholder="取款类型(1:银行转账，2：支付宝转账，3：其它)"></el-input>
    </el-form-item>
    <el-form-item label="操作管理员id" prop="sysUserId">
      <el-input v-model="dataForm.sysUserId" placeholder="操作管理员id"></el-input>
    </el-form-item>
    <el-form-item label="操作用户账号" prop="sysUserAccount">
      <el-input v-model="dataForm.sysUserAccount" placeholder="操作用户账号"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="0:账号取现。1：佣金取现" prop="type">
      <el-input v-model="dataForm.type" placeholder="0:账号取现。1：佣金取现"></el-input>
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
          takeAmount: '',
          poundage: '',
          obtainAmount: '',
          incomeNo: '',
          bankName: '',
          accountName: '',
          status: '',
          takeMoneyId: '',
          accountType: '',
          sysUserId: '',
          sysUserAccount: '',
          remark: '',
          type: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '取款用户id不能为空', trigger: 'blur' }
          ],
          account: [
            { required: true, message: '取款账号不能为空', trigger: 'blur' }
          ],
          takeAmount: [
            { required: true, message: '取款金额不能为空', trigger: 'blur' }
          ],
          poundage: [
            { required: true, message: '手续费不能为空', trigger: 'blur' }
          ],
          obtainAmount: [
            { required: true, message: '实际得到的金额不能为空', trigger: 'blur' }
          ],
          incomeNo: [
            { required: true, message: '收入账号不能为空', trigger: 'blur' }
          ],
          bankName: [
            { required: true, message: '银行名称不能为空', trigger: 'blur' }
          ],
          accountName: [
            { required: true, message: '开户人不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态(0:未审核，1：通过，2.取消)不能为空', trigger: 'blur' }
          ],
          takeMoneyId: [
            { required: true, message: '取款审核ID不能为空', trigger: 'blur' }
          ],
          accountType: [
            { required: true, message: '取款类型(1:银行转账，2：支付宝转账，3：其它)不能为空', trigger: 'blur' }
          ],
          sysUserId: [
            { required: true, message: '操作管理员id不能为空', trigger: 'blur' }
          ],
          sysUserAccount: [
            { required: true, message: '操作用户账号不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '0:账号取现。1：佣金取现不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/ordertakemoney/ordertakemoney/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.ordertakemoney.userId
                this.dataForm.account = data.ordertakemoney.account
                this.dataForm.takeAmount = data.ordertakemoney.takeAmount
                this.dataForm.poundage = data.ordertakemoney.poundage
                this.dataForm.obtainAmount = data.ordertakemoney.obtainAmount
                this.dataForm.incomeNo = data.ordertakemoney.incomeNo
                this.dataForm.bankName = data.ordertakemoney.bankName
                this.dataForm.accountName = data.ordertakemoney.accountName
                this.dataForm.status = data.ordertakemoney.status
                this.dataForm.takeMoneyId = data.ordertakemoney.takeMoneyId
                this.dataForm.accountType = data.ordertakemoney.accountType
                this.dataForm.sysUserId = data.ordertakemoney.sysUserId
                this.dataForm.sysUserAccount = data.ordertakemoney.sysUserAccount
                this.dataForm.remark = data.ordertakemoney.remark
                this.dataForm.type = data.ordertakemoney.type
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
              url: this.$http.adornUrl(`/ordertakemoney/ordertakemoney/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'account': this.dataForm.account ,

          'takeAmount': this.dataForm.takeAmount ,

          'poundage': this.dataForm.poundage ,

          'obtainAmount': this.dataForm.obtainAmount ,

          'incomeNo': this.dataForm.incomeNo ,

          'bankName': this.dataForm.bankName ,

          'accountName': this.dataForm.accountName ,

          'status': this.dataForm.status ,

          'takeMoneyId': this.dataForm.takeMoneyId ,

          'accountType': this.dataForm.accountType ,

          'sysUserId': this.dataForm.sysUserId ,

          'sysUserAccount': this.dataForm.sysUserAccount ,

          'remark': this.dataForm.remark ,

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
