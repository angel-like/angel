<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userid">
      <el-input v-model="dataForm.userid" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="代理商id" prop="agentId">
      <el-input v-model="dataForm.agentId" placeholder="代理商id"></el-input>
    </el-form-item>
    <el-form-item label="代理商名称" prop="agentName">
      <el-input v-model="dataForm.agentName" placeholder="代理商名称"></el-input>
    </el-form-item>
    <el-form-item label="" prop="gameId">
      <el-input v-model="dataForm.gameId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="游戏名称" prop="gameName">
      <el-input v-model="dataForm.gameName" placeholder="游戏名称"></el-input>
    </el-form-item>
    <el-form-item label="场次id" prop="gradeId">
      <el-input v-model="dataForm.gradeId" placeholder="场次id"></el-input>
    </el-form-item>
    <el-form-item label="场次名称" prop="gradeName">
      <el-input v-model="dataForm.gradeName" placeholder="场次名称"></el-input>
    </el-form-item>
    <el-form-item label="下注总金币" prop="betCoins">
      <el-input v-model="dataForm.betCoins" placeholder="下注总金币"></el-input>
    </el-form-item>
    <el-form-item label="下注总钻石" prop="betGems">
      <el-input v-model="dataForm.betGems" placeholder="下注总钻石"></el-input>
    </el-form-item>
    <el-form-item label="中奖金币" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="中奖金币"></el-input>
    </el-form-item>
    <el-form-item label="中奖钻石" prop="prizeGems">
      <el-input v-model="dataForm.prizeGems" placeholder="中奖钻石"></el-input>
    </el-form-item>
    <el-form-item label="用户类型" prop="userType">
      <el-input v-model="dataForm.userType" placeholder="用户类型"></el-input>
    </el-form-item>
    <el-form-item label="倍率（拉霸类游戏）" prop="ratio">
      <el-input v-model="dataForm.ratio" placeholder="倍率（拉霸类游戏）"></el-input>
    </el-form-item>
    <el-form-item label="是否小游戏" prop="mini">
      <el-input v-model="dataForm.mini" placeholder="是否小游戏"></el-input>
    </el-form-item>
    <el-form-item label="代理商盈利金币" prop="profitCoins">
      <el-input v-model="dataForm.profitCoins" placeholder="代理商盈利金币"></el-input>
    </el-form-item>
    <el-form-item label="代理商盈利钻石" prop="profitGems">
      <el-input v-model="dataForm.profitGems" placeholder="代理商盈利钻石"></el-input>
    </el-form-item>
    <el-form-item label="抽水金额" prop="waterProfit">
      <el-input v-model="dataForm.waterProfit" placeholder="抽水金额"></el-input>
    </el-form-item>
    <el-form-item label="钻石转金币的汇率" prop="gemsRate">
      <el-input v-model="dataForm.gemsRate" placeholder="钻石转金币的汇率"></el-input>
    </el-form-item>
    <el-form-item label="代理商游戏返佣比例" prop="maidRate">
      <el-input v-model="dataForm.maidRate" placeholder="代理商游戏返佣比例"></el-input>
    </el-form-item>
    <el-form-item label="游戏局号" prop="gameRoundNo">
      <el-input v-model="dataForm.gameRoundNo" placeholder="游戏局号"></el-input>
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
          userid: '',
          userAccount: '',
          agentId: '',
          agentName: '',
          gameId: '',
          gameName: '',
          gradeId: '',
          gradeName: '',
          betCoins: '',
          betGems: '',
          prizeCoins: '',
          prizeGems: '',
          userType: '',
          ratio: '',
          mini: '',
          profitCoins: '',
          profitGems: '',
          waterProfit: '',
          gemsRate: '',
          maidRate: '',
          gameRoundNo: '',
        },
        dataRule: {
          userid: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          agentId: [
            { required: true, message: '代理商id不能为空', trigger: 'blur' }
          ],
          agentName: [
            { required: true, message: '代理商名称不能为空', trigger: 'blur' }
          ],
          gameId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          gameName: [
            { required: true, message: '游戏名称不能为空', trigger: 'blur' }
          ],
          gradeId: [
            { required: true, message: '场次id不能为空', trigger: 'blur' }
          ],
          gradeName: [
            { required: true, message: '场次名称不能为空', trigger: 'blur' }
          ],
          betCoins: [
            { required: true, message: '下注总金币不能为空', trigger: 'blur' }
          ],
          betGems: [
            { required: true, message: '下注总钻石不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '中奖金币不能为空', trigger: 'blur' }
          ],
          prizeGems: [
            { required: true, message: '中奖钻石不能为空', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '用户类型不能为空', trigger: 'blur' }
          ],
          ratio: [
            { required: true, message: '倍率（拉霸类游戏）不能为空', trigger: 'blur' }
          ],
          mini: [
            { required: true, message: '是否小游戏不能为空', trigger: 'blur' }
          ],
          profitCoins: [
            { required: true, message: '代理商盈利金币不能为空', trigger: 'blur' }
          ],
          profitGems: [
            { required: true, message: '代理商盈利钻石不能为空', trigger: 'blur' }
          ],
          waterProfit: [
            { required: true, message: '抽水金额不能为空', trigger: 'blur' }
          ],
          gemsRate: [
            { required: true, message: '钻石转金币的汇率不能为空', trigger: 'blur' }
          ],
          maidRate: [
            { required: true, message: '代理商游戏返佣比例不能为空', trigger: 'blur' }
          ],
          gameRoundNo: [
            { required: true, message: '游戏局号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecord/gamerecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userid = data.gamerecord.userid
                this.dataForm.userAccount = data.gamerecord.userAccount
                this.dataForm.agentId = data.gamerecord.agentId
                this.dataForm.agentName = data.gamerecord.agentName
                this.dataForm.gameId = data.gamerecord.gameId
                this.dataForm.gameName = data.gamerecord.gameName
                this.dataForm.gradeId = data.gamerecord.gradeId
                this.dataForm.gradeName = data.gamerecord.gradeName
                this.dataForm.betCoins = data.gamerecord.betCoins
                this.dataForm.betGems = data.gamerecord.betGems
                this.dataForm.prizeCoins = data.gamerecord.prizeCoins
                this.dataForm.prizeGems = data.gamerecord.prizeGems
                this.dataForm.userType = data.gamerecord.userType
                this.dataForm.ratio = data.gamerecord.ratio
                this.dataForm.mini = data.gamerecord.mini
                this.dataForm.profitCoins = data.gamerecord.profitCoins
                this.dataForm.profitGems = data.gamerecord.profitGems
                this.dataForm.waterProfit = data.gamerecord.waterProfit
                this.dataForm.gemsRate = data.gamerecord.gemsRate
                this.dataForm.maidRate = data.gamerecord.maidRate
                this.dataForm.gameRoundNo = data.gamerecord.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecord/gamerecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userid': this.dataForm.userid ,

          'userAccount': this.dataForm.userAccount ,

          'agentId': this.dataForm.agentId ,

          'agentName': this.dataForm.agentName ,

          'gameId': this.dataForm.gameId ,

          'gameName': this.dataForm.gameName ,

          'gradeId': this.dataForm.gradeId ,

          'gradeName': this.dataForm.gradeName ,

          'betCoins': this.dataForm.betCoins ,

          'betGems': this.dataForm.betGems ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'prizeGems': this.dataForm.prizeGems ,

          'userType': this.dataForm.userType ,

          'ratio': this.dataForm.ratio ,

          'mini': this.dataForm.mini ,

          'profitCoins': this.dataForm.profitCoins ,

          'profitGems': this.dataForm.profitGems ,

          'waterProfit': this.dataForm.waterProfit ,

          'gemsRate': this.dataForm.gemsRate ,

          'maidRate': this.dataForm.maidRate ,

          'gameRoundNo': this.dataForm.gameRoundNo ,

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
