<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="方案名称" prop="productName">
      <el-input v-model="dataForm.productName" placeholder="方案名称"></el-input>
    </el-form-item>
    <el-form-item label="结算类型（0:循环结算，1本金结算）" prop="settlementType">
      <el-input v-model="dataForm.settlementType" placeholder="结算类型（0:循环结算，1本金结算）"></el-input>
    </el-form-item>
    <el-form-item label="最低金额" prop="minMoney">
      <el-input v-model="dataForm.minMoney" placeholder="最低金额"></el-input>
    </el-form-item>
    <el-form-item label="最高金额" prop="maxMoney">
      <el-input v-model="dataForm.maxMoney" placeholder="最高金额"></el-input>
    </el-form-item>
    <el-form-item label="利率" prop="rate">
      <el-input v-model="dataForm.rate" placeholder="利率"></el-input>
    </el-form-item>
    <el-form-item label="利息上限" prop="rateMoneyMax">
      <el-input v-model="dataForm.rateMoneyMax" placeholder="利息上限"></el-input>
    </el-form-item>
    <el-form-item label="剩余可买份数" prop="remaindBuyNum">
      <el-input v-model="dataForm.remaindBuyNum" placeholder="剩余可买份数"></el-input>
    </el-form-item>
    <el-form-item label="打码倍数" prop="betMultiple">
      <el-input v-model="dataForm.betMultiple" placeholder="打码倍数"></el-input>
    </el-form-item>
    <el-form-item label="会员当天可购买次数" prop="userTodayBuyNum">
      <el-input v-model="dataForm.userTodayBuyNum" placeholder="会员当天可购买次数"></el-input>
    </el-form-item>
    <el-form-item label="发行份数" prop="issueNum">
      <el-input v-model="dataForm.issueNum" placeholder="发行份数"></el-input>
    </el-form-item>
    <el-form-item label="发行时间" prop="issueTime">
      <el-input v-model="dataForm.issueTime" placeholder="发行时间"></el-input>
    </el-form-item>
    <el-form-item label="状态(0:禁用，1:启用)" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="状态(0:禁用，1:启用)"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="orderNum">
      <el-input v-model="dataForm.orderNum" placeholder="排序"></el-input>
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
          productName: '',
          settlementType: '',
          minMoney: '',
          maxMoney: '',
          rate: '',
          rateMoneyMax: '',
          remaindBuyNum: '',
          betMultiple: '',
          userTodayBuyNum: '',
          issueNum: '',
          issueTime: '',
          enable: '',
          orderNum: '',
        },
        dataRule: {
          productName: [
            { required: true, message: '方案名称不能为空', trigger: 'blur' }
          ],
          settlementType: [
            { required: true, message: '结算类型（0:循环结算，1本金结算）不能为空', trigger: 'blur' }
          ],
          minMoney: [
            { required: true, message: '最低金额不能为空', trigger: 'blur' }
          ],
          maxMoney: [
            { required: true, message: '最高金额不能为空', trigger: 'blur' }
          ],
          rate: [
            { required: true, message: '利率不能为空', trigger: 'blur' }
          ],
          rateMoneyMax: [
            { required: true, message: '利息上限不能为空', trigger: 'blur' }
          ],
          remaindBuyNum: [
            { required: true, message: '剩余可买份数不能为空', trigger: 'blur' }
          ],
          betMultiple: [
            { required: true, message: '打码倍数不能为空', trigger: 'blur' }
          ],
          userTodayBuyNum: [
            { required: true, message: '会员当天可购买次数不能为空', trigger: 'blur' }
          ],
          issueNum: [
            { required: true, message: '发行份数不能为空', trigger: 'blur' }
          ],
          issueTime: [
            { required: true, message: '发行时间不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态(0:禁用，1:启用)不能为空', trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userbalanceproduct/userbalanceproduct/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.productName = data.userbalanceproduct.productName
                this.dataForm.settlementType = data.userbalanceproduct.settlementType
                this.dataForm.minMoney = data.userbalanceproduct.minMoney
                this.dataForm.maxMoney = data.userbalanceproduct.maxMoney
                this.dataForm.rate = data.userbalanceproduct.rate
                this.dataForm.rateMoneyMax = data.userbalanceproduct.rateMoneyMax
                this.dataForm.remaindBuyNum = data.userbalanceproduct.remaindBuyNum
                this.dataForm.betMultiple = data.userbalanceproduct.betMultiple
                this.dataForm.userTodayBuyNum = data.userbalanceproduct.userTodayBuyNum
                this.dataForm.issueNum = data.userbalanceproduct.issueNum
                this.dataForm.issueTime = data.userbalanceproduct.issueTime
                this.dataForm.enable = data.userbalanceproduct.enable
                this.dataForm.orderNum = data.userbalanceproduct.orderNum
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
              url: this.$http.adornUrl(`/userbalanceproduct/userbalanceproduct/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'productName': this.dataForm.productName ,

          'settlementType': this.dataForm.settlementType ,

          'minMoney': this.dataForm.minMoney ,

          'maxMoney': this.dataForm.maxMoney ,

          'rate': this.dataForm.rate ,

          'rateMoneyMax': this.dataForm.rateMoneyMax ,

          'remaindBuyNum': this.dataForm.remaindBuyNum ,

          'betMultiple': this.dataForm.betMultiple ,

          'userTodayBuyNum': this.dataForm.userTodayBuyNum ,

          'issueNum': this.dataForm.issueNum ,

          'issueTime': this.dataForm.issueTime ,

          'enable': this.dataForm.enable ,

          'orderNum': this.dataForm.orderNum ,

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
