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
    <el-form-item label="游戏模式（1.积分模式2.金币模式）" prop="gameModule">
      <el-input v-model="dataForm.gameModule" placeholder="游戏模式（1.积分模式2.金币模式）"></el-input>
    </el-form-item>
    <el-form-item label="玩法模式" prop="gameType">
      <el-input v-model="dataForm.gameType" placeholder="玩法模式"></el-input>
    </el-form-item>
    <el-form-item label="" prop="payType">
      <el-input v-model="dataForm.payType" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="底分" prop="baseScore">
      <el-input v-model="dataForm.baseScore" placeholder="底分"></el-input>
    </el-form-item>
    <el-form-item label="剩余张数" prop="surplusCardsNum">
      <el-input v-model="dataForm.surplusCardsNum" placeholder="剩余张数"></el-input>
    </el-form-item>
    <el-form-item label="被炸弹数" prop="coverBombNum">
      <el-input v-model="dataForm.coverBombNum" placeholder="被炸弹数"></el-input>
    </el-form-item>
    <el-form-item label="所出炸弹数" prop="bombNum">
      <el-input v-model="dataForm.bombNum" placeholder="所出炸弹数"></el-input>
    </el-form-item>
    <el-form-item label="包赔数" prop="compensateNum">
      <el-input v-model="dataForm.compensateNum" placeholder="包赔数"></el-input>
    </el-form-item>
    <el-form-item label="总盈亏" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="总盈亏"></el-input>
    </el-form-item>
    <el-form-item label="赢输金币" prop="coins">
      <el-input v-model="dataForm.coins" placeholder="赢输金币"></el-input>
    </el-form-item>
    <el-form-item label="下注金币" prop="betCoins">
      <el-input v-model="dataForm.betCoins" placeholder="下注金币"></el-input>
    </el-form-item>
    <el-form-item label="下注前金币" prop="coinsBefore">
      <el-input v-model="dataForm.coinsBefore" placeholder="下注前金币"></el-input>
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
          gameModule: '',
          gameType: '',
          payType: '',
          baseScore: '',
          surplusCardsNum: '',
          coverBombNum: '',
          bombNum: '',
          compensateNum: '',
          prizeCoins: '',
          coins: '',
          betCoins: '',
          coinsBefore: '',
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
          gameModule: [
            { required: true, message: '游戏模式（1.积分模式2.金币模式）不能为空', trigger: 'blur' }
          ],
          gameType: [
            { required: true, message: '玩法模式不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          baseScore: [
            { required: true, message: '底分不能为空', trigger: 'blur' }
          ],
          surplusCardsNum: [
            { required: true, message: '剩余张数不能为空', trigger: 'blur' }
          ],
          coverBombNum: [
            { required: true, message: '被炸弹数不能为空', trigger: 'blur' }
          ],
          bombNum: [
            { required: true, message: '所出炸弹数不能为空', trigger: 'blur' }
          ],
          compensateNum: [
            { required: true, message: '包赔数不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '总盈亏不能为空', trigger: 'blur' }
          ],
          coins: [
            { required: true, message: '赢输金币不能为空', trigger: 'blur' }
          ],
          betCoins: [
            { required: true, message: '下注金币不能为空', trigger: 'blur' }
          ],
          coinsBefore: [
            { required: true, message: '下注前金币不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordpaodekuaifangka/gamerecordpaodekuaifangka/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordpaodekuaifangka.userId
                this.dataForm.userAccount = data.gamerecordpaodekuaifangka.userAccount
                this.dataForm.gameId = data.gamerecordpaodekuaifangka.gameId
                this.dataForm.gameName = data.gamerecordpaodekuaifangka.gameName
                this.dataForm.gradeId = data.gamerecordpaodekuaifangka.gradeId
                this.dataForm.gradeName = data.gamerecordpaodekuaifangka.gradeName
                this.dataForm.gradeNumber = data.gamerecordpaodekuaifangka.gradeNumber
                this.dataForm.roomId = data.gamerecordpaodekuaifangka.roomId
                this.dataForm.roomName = data.gamerecordpaodekuaifangka.roomName
                this.dataForm.gameModule = data.gamerecordpaodekuaifangka.gameModule
                this.dataForm.gameType = data.gamerecordpaodekuaifangka.gameType
                this.dataForm.payType = data.gamerecordpaodekuaifangka.payType
                this.dataForm.baseScore = data.gamerecordpaodekuaifangka.baseScore
                this.dataForm.surplusCardsNum = data.gamerecordpaodekuaifangka.surplusCardsNum
                this.dataForm.coverBombNum = data.gamerecordpaodekuaifangka.coverBombNum
                this.dataForm.bombNum = data.gamerecordpaodekuaifangka.bombNum
                this.dataForm.compensateNum = data.gamerecordpaodekuaifangka.compensateNum
                this.dataForm.prizeCoins = data.gamerecordpaodekuaifangka.prizeCoins
                this.dataForm.coins = data.gamerecordpaodekuaifangka.coins
                this.dataForm.betCoins = data.gamerecordpaodekuaifangka.betCoins
                this.dataForm.coinsBefore = data.gamerecordpaodekuaifangka.coinsBefore
                this.dataForm.coinsAfter = data.gamerecordpaodekuaifangka.coinsAfter
                this.dataForm.robot = data.gamerecordpaodekuaifangka.robot
                this.dataForm.gameRoundNo = data.gamerecordpaodekuaifangka.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordpaodekuaifangka/gamerecordpaodekuaifangka/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'gameModule': this.dataForm.gameModule ,

          'gameType': this.dataForm.gameType ,

          'payType': this.dataForm.payType ,

          'baseScore': this.dataForm.baseScore ,

          'surplusCardsNum': this.dataForm.surplusCardsNum ,

          'coverBombNum': this.dataForm.coverBombNum ,

          'bombNum': this.dataForm.bombNum ,

          'compensateNum': this.dataForm.compensateNum ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'coins': this.dataForm.coins ,

          'betCoins': this.dataForm.betCoins ,

          'coinsBefore': this.dataForm.coinsBefore ,

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
