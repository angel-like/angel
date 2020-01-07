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
    <el-form-item label="游戏id" prop="gameId">
      <el-input v-model="dataForm.gameId" placeholder="游戏id"></el-input>
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
    <el-form-item label="场次编号" prop="gradeNumber">
      <el-input v-model="dataForm.gradeNumber" placeholder="场次编号"></el-input>
    </el-form-item>
    <el-form-item label="房间id" prop="roomId">
      <el-input v-model="dataForm.roomId" placeholder="房间id"></el-input>
    </el-form-item>
    <el-form-item label="房间名称" prop="roomName">
      <el-input v-model="dataForm.roomName" placeholder="房间名称"></el-input>
    </el-form-item>
    <el-form-item label="底注" prop="baseScore">
      <el-input v-model="dataForm.baseScore" placeholder="底注"></el-input>
    </el-form-item>
    <el-form-item label="额外加注" prop="additionalScore">
      <el-input v-model="dataForm.additionalScore" placeholder="额外加注"></el-input>
    </el-form-item>
    <el-form-item label="牌型盈亏" prop="cardsPrize">
      <el-input v-model="dataForm.cardsPrize" placeholder="牌型盈亏"></el-input>
    </el-form-item>
    <el-form-item label="保险盈亏" prop="insurancePrize">
      <el-input v-model="dataForm.insurancePrize" placeholder="保险盈亏"></el-input>
    </el-form-item>
    <el-form-item label="总盈亏" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="总盈亏"></el-input>
    </el-form-item>
    <el-form-item label="玩家牌型" prop="playerCard">
      <el-input v-model="dataForm.playerCard" placeholder="玩家牌型"></el-input>
    </el-form-item>
    <el-form-item label="玩家牌型字符" prop="playerCardStr">
      <el-input v-model="dataForm.playerCardStr" placeholder="玩家牌型字符"></el-input>
    </el-form-item>
    <el-form-item label="庄家牌型" prop="bankerCard">
      <el-input v-model="dataForm.bankerCard" placeholder="庄家牌型"></el-input>
    </el-form-item>
    <el-form-item label="庄家牌型字符" prop="bankerCardStr">
      <el-input v-model="dataForm.bankerCardStr" placeholder="庄家牌型字符"></el-input>
    </el-form-item>
    <el-form-item label="下注前金币 " prop="coinsBefore">
      <el-input v-model="dataForm.coinsBefore" placeholder="下注前金币 "></el-input>
    </el-form-item>
    <el-form-item label="下注金币" prop="betCoins">
      <el-input v-model="dataForm.betCoins" placeholder="下注金币"></el-input>
    </el-form-item>
    <el-form-item label="赢输金币" prop="coins">
      <el-input v-model="dataForm.coins" placeholder="赢输金币"></el-input>
    </el-form-item>
    <el-form-item label="结束后金币" prop="coinsAfter">
      <el-input v-model="dataForm.coinsAfter" placeholder="结束后金币"></el-input>
    </el-form-item>
    <el-form-item label="是否机器人" prop="robot">
      <el-input v-model="dataForm.robot" placeholder="是否机器人"></el-input>
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
          userId: '',
          userAccount: '',
          gameId: '',
          gameName: '',
          gradeId: '',
          gradeName: '',
          gradeNumber: '',
          roomId: '',
          roomName: '',
          baseScore: '',
          additionalScore: '',
          cardsPrize: '',
          insurancePrize: '',
          prizeCoins: '',
          playerCard: '',
          playerCardStr: '',
          bankerCard: '',
          bankerCardStr: '',
          coinsBefore: '',
          betCoins: '',
          coins: '',
          coinsAfter: '',
          robot: '',
          gameRoundNo: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          gameId: [
            { required: true, message: '游戏id不能为空', trigger: 'blur' }
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
          gradeNumber: [
            { required: true, message: '场次编号不能为空', trigger: 'blur' }
          ],
          roomId: [
            { required: true, message: '房间id不能为空', trigger: 'blur' }
          ],
          roomName: [
            { required: true, message: '房间名称不能为空', trigger: 'blur' }
          ],
          baseScore: [
            { required: true, message: '底注不能为空', trigger: 'blur' }
          ],
          additionalScore: [
            { required: true, message: '额外加注不能为空', trigger: 'blur' }
          ],
          cardsPrize: [
            { required: true, message: '牌型盈亏不能为空', trigger: 'blur' }
          ],
          insurancePrize: [
            { required: true, message: '保险盈亏不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '总盈亏不能为空', trigger: 'blur' }
          ],
          playerCard: [
            { required: true, message: '玩家牌型不能为空', trigger: 'blur' }
          ],
          playerCardStr: [
            { required: true, message: '玩家牌型字符不能为空', trigger: 'blur' }
          ],
          bankerCard: [
            { required: true, message: '庄家牌型不能为空', trigger: 'blur' }
          ],
          bankerCardStr: [
            { required: true, message: '庄家牌型字符不能为空', trigger: 'blur' }
          ],
          coinsBefore: [
            { required: true, message: '下注前金币 不能为空', trigger: 'blur' }
          ],
          betCoins: [
            { required: true, message: '下注金币不能为空', trigger: 'blur' }
          ],
          coins: [
            { required: true, message: '赢输金币不能为空', trigger: 'blur' }
          ],
          coinsAfter: [
            { required: true, message: '结束后金币不能为空', trigger: 'blur' }
          ],
          robot: [
            { required: true, message: '是否机器人不能为空', trigger: 'blur' }
          ],
          gameRoundNo: [
            { required: true, message: '游戏局号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordtwentyonespot/gamerecordtwentyonespot/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordtwentyonespot.userId
                this.dataForm.userAccount = data.gamerecordtwentyonespot.userAccount
                this.dataForm.gameId = data.gamerecordtwentyonespot.gameId
                this.dataForm.gameName = data.gamerecordtwentyonespot.gameName
                this.dataForm.gradeId = data.gamerecordtwentyonespot.gradeId
                this.dataForm.gradeName = data.gamerecordtwentyonespot.gradeName
                this.dataForm.gradeNumber = data.gamerecordtwentyonespot.gradeNumber
                this.dataForm.roomId = data.gamerecordtwentyonespot.roomId
                this.dataForm.roomName = data.gamerecordtwentyonespot.roomName
                this.dataForm.baseScore = data.gamerecordtwentyonespot.baseScore
                this.dataForm.additionalScore = data.gamerecordtwentyonespot.additionalScore
                this.dataForm.cardsPrize = data.gamerecordtwentyonespot.cardsPrize
                this.dataForm.insurancePrize = data.gamerecordtwentyonespot.insurancePrize
                this.dataForm.prizeCoins = data.gamerecordtwentyonespot.prizeCoins
                this.dataForm.playerCard = data.gamerecordtwentyonespot.playerCard
                this.dataForm.playerCardStr = data.gamerecordtwentyonespot.playerCardStr
                this.dataForm.bankerCard = data.gamerecordtwentyonespot.bankerCard
                this.dataForm.bankerCardStr = data.gamerecordtwentyonespot.bankerCardStr
                this.dataForm.coinsBefore = data.gamerecordtwentyonespot.coinsBefore
                this.dataForm.betCoins = data.gamerecordtwentyonespot.betCoins
                this.dataForm.coins = data.gamerecordtwentyonespot.coins
                this.dataForm.coinsAfter = data.gamerecordtwentyonespot.coinsAfter
                this.dataForm.robot = data.gamerecordtwentyonespot.robot
                this.dataForm.gameRoundNo = data.gamerecordtwentyonespot.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordtwentyonespot/gamerecordtwentyonespot/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'gameId': this.dataForm.gameId ,

          'gameName': this.dataForm.gameName ,

          'gradeId': this.dataForm.gradeId ,

          'gradeName': this.dataForm.gradeName ,

          'gradeNumber': this.dataForm.gradeNumber ,

          'roomId': this.dataForm.roomId ,

          'roomName': this.dataForm.roomName ,

          'baseScore': this.dataForm.baseScore ,

          'additionalScore': this.dataForm.additionalScore ,

          'cardsPrize': this.dataForm.cardsPrize ,

          'insurancePrize': this.dataForm.insurancePrize ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'playerCard': this.dataForm.playerCard ,

          'playerCardStr': this.dataForm.playerCardStr ,

          'bankerCard': this.dataForm.bankerCard ,

          'bankerCardStr': this.dataForm.bankerCardStr ,

          'coinsBefore': this.dataForm.coinsBefore ,

          'betCoins': this.dataForm.betCoins ,

          'coins': this.dataForm.coins ,

          'coinsAfter': this.dataForm.coinsAfter ,

          'robot': this.dataForm.robot ,

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
