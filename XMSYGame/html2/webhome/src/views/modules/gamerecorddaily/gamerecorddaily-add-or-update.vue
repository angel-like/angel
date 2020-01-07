<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="有效下注" prop="validBet">
      <el-input v-model="dataForm.validBet" placeholder="有效下注"></el-input>
    </el-form-item>
    <el-form-item label="中奖金币" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="中奖金币"></el-input>
    </el-form-item>
    <el-form-item label="抽水金额" prop="waterProfit">
      <el-input v-model="dataForm.waterProfit" placeholder="抽水金额"></el-input>
    </el-form-item>
    <el-form-item label="代理商盈利金币" prop="profitCoins">
      <el-input v-model="dataForm.profitCoins" placeholder="代理商盈利金币"></el-input>
    </el-form-item>
    <el-form-item label="结算日期" prop="countDay">
      <el-input v-model="dataForm.countDay" placeholder="结算日期"></el-input>
    </el-form-item>
    <el-form-item label="会员返水" prop="userWaterProfit">
      <el-input v-model="dataForm.userWaterProfit" placeholder="会员返水"></el-input>
    </el-form-item>
    <el-form-item label="会员返水比例" prop="userWaterRate">
      <el-input v-model="dataForm.userWaterRate" placeholder="会员返水比例"></el-input>
    </el-form-item>
    <el-form-item label="是否计算返水" prop="userReturn">
      <el-input v-model="dataForm.userReturn" placeholder="是否计算返水"></el-input>
    </el-form-item>
    <el-form-item label="代理商是否返佣" prop="agentReturn">
      <el-input v-model="dataForm.agentReturn" placeholder="代理商是否返佣"></el-input>
    </el-form-item>
    <el-form-item label="用户返水是否异常" prop="abnormal">
      <el-input v-model="dataForm.abnormal" placeholder="用户返水是否异常"></el-input>
    </el-form-item>
    <el-form-item label="用户返水异常内容" prop="abnormalContent">
      <el-input v-model="dataForm.abnormalContent" placeholder="用户返水异常内容"></el-input>
    </el-form-item>
    <el-form-item label="代理商返佣是否异常" prop="agentAbnormal">
      <el-input v-model="dataForm.agentAbnormal" placeholder="代理商返佣是否异常"></el-input>
    </el-form-item>
    <el-form-item label="代理商返佣异常内容" prop="agentAbnormalContent">
      <el-input v-model="dataForm.agentAbnormalContent" placeholder="代理商返佣异常内容"></el-input>
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
          validBet: '',
          prizeCoins: '',
          waterProfit: '',
          profitCoins: '',
          countDay: '',
          userWaterProfit: '',
          userWaterRate: '',
          userReturn: '',
          agentReturn: '',
          abnormal: '',
          abnormalContent: '',
          agentAbnormal: '',
          agentAbnormalContent: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          validBet: [
            { required: true, message: '有效下注不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '中奖金币不能为空', trigger: 'blur' }
          ],
          waterProfit: [
            { required: true, message: '抽水金额不能为空', trigger: 'blur' }
          ],
          profitCoins: [
            { required: true, message: '代理商盈利金币不能为空', trigger: 'blur' }
          ],
          countDay: [
            { required: true, message: '结算日期不能为空', trigger: 'blur' }
          ],
          userWaterProfit: [
            { required: true, message: '会员返水不能为空', trigger: 'blur' }
          ],
          userWaterRate: [
            { required: true, message: '会员返水比例不能为空', trigger: 'blur' }
          ],
          userReturn: [
            { required: true, message: '是否计算返水不能为空', trigger: 'blur' }
          ],
          agentReturn: [
            { required: true, message: '代理商是否返佣不能为空', trigger: 'blur' }
          ],
          abnormal: [
            { required: true, message: '用户返水是否异常不能为空', trigger: 'blur' }
          ],
          abnormalContent: [
            { required: true, message: '用户返水异常内容不能为空', trigger: 'blur' }
          ],
          agentAbnormal: [
            { required: true, message: '代理商返佣是否异常不能为空', trigger: 'blur' }
          ],
          agentAbnormalContent: [
            { required: true, message: '代理商返佣异常内容不能为空', trigger: 'blur' }
          ],
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
              url: this.$http.adornUrl(`/gamerecorddaily/gamerecorddaily/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecorddaily.userId
                this.dataForm.userAccount = data.gamerecorddaily.userAccount
                this.dataForm.validBet = data.gamerecorddaily.validBet
                this.dataForm.prizeCoins = data.gamerecorddaily.prizeCoins
                this.dataForm.waterProfit = data.gamerecorddaily.waterProfit
                this.dataForm.profitCoins = data.gamerecorddaily.profitCoins
                this.dataForm.countDay = data.gamerecorddaily.countDay
                this.dataForm.userWaterProfit = data.gamerecorddaily.userWaterProfit
                this.dataForm.userWaterRate = data.gamerecorddaily.userWaterRate
                this.dataForm.userReturn = data.gamerecorddaily.userReturn
                this.dataForm.agentReturn = data.gamerecorddaily.agentReturn
                this.dataForm.abnormal = data.gamerecorddaily.abnormal
                this.dataForm.abnormalContent = data.gamerecorddaily.abnormalContent
                this.dataForm.agentAbnormal = data.gamerecorddaily.agentAbnormal
                this.dataForm.agentAbnormalContent = data.gamerecorddaily.agentAbnormalContent
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
              url: this.$http.adornUrl(`/gamerecorddaily/gamerecorddaily/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'validBet': this.dataForm.validBet ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'waterProfit': this.dataForm.waterProfit ,

          'profitCoins': this.dataForm.profitCoins ,

          'countDay': this.dataForm.countDay ,

          'userWaterProfit': this.dataForm.userWaterProfit ,

          'userWaterRate': this.dataForm.userWaterRate ,

          'userReturn': this.dataForm.userReturn ,

          'agentReturn': this.dataForm.agentReturn ,

          'abnormal': this.dataForm.abnormal ,

          'abnormalContent': this.dataForm.abnormalContent ,

          'agentAbnormal': this.dataForm.agentAbnormal ,

          'agentAbnormalContent': this.dataForm.agentAbnormalContent ,

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
