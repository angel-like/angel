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
    <el-form-item label="房间底分" prop="baseScore">
      <el-input v-model="dataForm.baseScore" placeholder="房间底分"></el-input>
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
    <el-form-item label="玩家牌型字符串" prop="playerCardStr">
      <el-input v-model="dataForm.playerCardStr" placeholder="玩家牌型字符串"></el-input>
    </el-form-item>
    <el-form-item label="玩家牌型" prop="playerCard">
      <el-input v-model="dataForm.playerCard" placeholder="玩家牌型"></el-input>
    </el-form-item>
    <el-form-item label="结算牌型字符串" prop="settlementCardStr">
      <el-input v-model="dataForm.settlementCardStr" placeholder="结算牌型字符串"></el-input>
    </el-form-item>
    <el-form-item label="结算牌型" prop="settlementCard">
      <el-input v-model="dataForm.settlementCard" placeholder="结算牌型"></el-input>
    </el-form-item>
    <el-form-item label="总盈亏" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="总盈亏"></el-input>
    </el-form-item>
    <el-form-item label="下注倍数" prop="betMultiple">
      <el-input v-model="dataForm.betMultiple" placeholder="下注倍数"></el-input>
    </el-form-item>
    <el-form-item label="是否机器人" prop="robot">
      <el-input v-model="dataForm.robot" placeholder="是否机器人"></el-input>
    </el-form-item>
    <el-form-item label="是否庄家" prop="banker">
      <el-input v-model="dataForm.banker" placeholder="是否庄家"></el-input>
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
          coinsBefore: '',
          betCoins: '',
          coins: '',
          coinsAfter: '',
          playerCardStr: '',
          playerCard: '',
          settlementCardStr: '',
          settlementCard: '',
          prizeCoins: '',
          betMultiple: '',
          robot: '',
          banker: '',
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
            { required: true, message: '房间底分不能为空', trigger: 'blur' }
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
          playerCardStr: [
            { required: true, message: '玩家牌型字符串不能为空', trigger: 'blur' }
          ],
          playerCard: [
            { required: true, message: '玩家牌型不能为空', trigger: 'blur' }
          ],
          settlementCardStr: [
            { required: true, message: '结算牌型字符串不能为空', trigger: 'blur' }
          ],
          settlementCard: [
            { required: true, message: '结算牌型不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '总盈亏不能为空', trigger: 'blur' }
          ],
          betMultiple: [
            { required: true, message: '下注倍数不能为空', trigger: 'blur' }
          ],
          robot: [
            { required: true, message: '是否机器人不能为空', trigger: 'blur' }
          ],
          banker: [
            { required: true, message: '是否庄家不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordsangongqiangzhuang/gamerecordsangongqiangzhuang/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordsangongqiangzhuang.userId
                this.dataForm.userAccount = data.gamerecordsangongqiangzhuang.userAccount
                this.dataForm.gameId = data.gamerecordsangongqiangzhuang.gameId
                this.dataForm.gameName = data.gamerecordsangongqiangzhuang.gameName
                this.dataForm.gradeId = data.gamerecordsangongqiangzhuang.gradeId
                this.dataForm.gradeName = data.gamerecordsangongqiangzhuang.gradeName
                this.dataForm.gradeNumber = data.gamerecordsangongqiangzhuang.gradeNumber
                this.dataForm.roomId = data.gamerecordsangongqiangzhuang.roomId
                this.dataForm.roomName = data.gamerecordsangongqiangzhuang.roomName
                this.dataForm.baseScore = data.gamerecordsangongqiangzhuang.baseScore
                this.dataForm.coinsBefore = data.gamerecordsangongqiangzhuang.coinsBefore
                this.dataForm.betCoins = data.gamerecordsangongqiangzhuang.betCoins
                this.dataForm.coins = data.gamerecordsangongqiangzhuang.coins
                this.dataForm.coinsAfter = data.gamerecordsangongqiangzhuang.coinsAfter
                this.dataForm.playerCardStr = data.gamerecordsangongqiangzhuang.playerCardStr
                this.dataForm.playerCard = data.gamerecordsangongqiangzhuang.playerCard
                this.dataForm.settlementCardStr = data.gamerecordsangongqiangzhuang.settlementCardStr
                this.dataForm.settlementCard = data.gamerecordsangongqiangzhuang.settlementCard
                this.dataForm.prizeCoins = data.gamerecordsangongqiangzhuang.prizeCoins
                this.dataForm.betMultiple = data.gamerecordsangongqiangzhuang.betMultiple
                this.dataForm.robot = data.gamerecordsangongqiangzhuang.robot
                this.dataForm.banker = data.gamerecordsangongqiangzhuang.banker
                this.dataForm.gameRoundNo = data.gamerecordsangongqiangzhuang.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordsangongqiangzhuang/gamerecordsangongqiangzhuang/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'coinsBefore': this.dataForm.coinsBefore ,

          'betCoins': this.dataForm.betCoins ,

          'coins': this.dataForm.coins ,

          'coinsAfter': this.dataForm.coinsAfter ,

          'playerCardStr': this.dataForm.playerCardStr ,

          'playerCard': this.dataForm.playerCard ,

          'settlementCardStr': this.dataForm.settlementCardStr ,

          'settlementCard': this.dataForm.settlementCard ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'betMultiple': this.dataForm.betMultiple ,

          'robot': this.dataForm.robot ,

          'banker': this.dataForm.banker ,

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
