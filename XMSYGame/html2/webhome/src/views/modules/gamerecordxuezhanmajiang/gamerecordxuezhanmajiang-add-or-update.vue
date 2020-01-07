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
    <el-form-item label="底分" prop="baseScore">
      <el-input v-model="dataForm.baseScore" placeholder="底分"></el-input>
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
    <el-form-item label="结算牌型字符串" prop="settlementCardStr">
      <el-input v-model="dataForm.settlementCardStr" placeholder="结算牌型字符串"></el-input>
    </el-form-item>
    <el-form-item label="结算牌型" prop="settlementCard">
      <el-input v-model="dataForm.settlementCard" placeholder="结算牌型"></el-input>
    </el-form-item>
    <el-form-item label="总盈亏" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="总盈亏"></el-input>
    </el-form-item>
    <el-form-item label="胡牌倍数" prop="cardMultiple">
      <el-input v-model="dataForm.cardMultiple" placeholder="胡牌倍数"></el-input>
    </el-form-item>
    <el-form-item label="是否认输" prop="defeat">
      <el-input v-model="dataForm.defeat" placeholder="是否认输"></el-input>
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
          settlementCardStr: '',
          settlementCard: '',
          prizeCoins: '',
          cardMultiple: '',
          defeat: '',
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
            { required: true, message: '底分不能为空', trigger: 'blur' }
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
          settlementCardStr: [
            { required: true, message: '结算牌型字符串不能为空', trigger: 'blur' }
          ],
          settlementCard: [
            { required: true, message: '结算牌型不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '总盈亏不能为空', trigger: 'blur' }
          ],
          cardMultiple: [
            { required: true, message: '胡牌倍数不能为空', trigger: 'blur' }
          ],
          defeat: [
            { required: true, message: '是否认输不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordxuezhanmajiang/gamerecordxuezhanmajiang/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordxuezhanmajiang.userId
                this.dataForm.userAccount = data.gamerecordxuezhanmajiang.userAccount
                this.dataForm.gameId = data.gamerecordxuezhanmajiang.gameId
                this.dataForm.gameName = data.gamerecordxuezhanmajiang.gameName
                this.dataForm.gradeId = data.gamerecordxuezhanmajiang.gradeId
                this.dataForm.gradeName = data.gamerecordxuezhanmajiang.gradeName
                this.dataForm.gradeNumber = data.gamerecordxuezhanmajiang.gradeNumber
                this.dataForm.roomId = data.gamerecordxuezhanmajiang.roomId
                this.dataForm.roomName = data.gamerecordxuezhanmajiang.roomName
                this.dataForm.baseScore = data.gamerecordxuezhanmajiang.baseScore
                this.dataForm.coinsBefore = data.gamerecordxuezhanmajiang.coinsBefore
                this.dataForm.betCoins = data.gamerecordxuezhanmajiang.betCoins
                this.dataForm.coins = data.gamerecordxuezhanmajiang.coins
                this.dataForm.coinsAfter = data.gamerecordxuezhanmajiang.coinsAfter
                this.dataForm.settlementCardStr = data.gamerecordxuezhanmajiang.settlementCardStr
                this.dataForm.settlementCard = data.gamerecordxuezhanmajiang.settlementCard
                this.dataForm.prizeCoins = data.gamerecordxuezhanmajiang.prizeCoins
                this.dataForm.cardMultiple = data.gamerecordxuezhanmajiang.cardMultiple
                this.dataForm.defeat = data.gamerecordxuezhanmajiang.defeat
                this.dataForm.robot = data.gamerecordxuezhanmajiang.robot
                this.dataForm.banker = data.gamerecordxuezhanmajiang.banker
                this.dataForm.gameRoundNo = data.gamerecordxuezhanmajiang.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordxuezhanmajiang/gamerecordxuezhanmajiang/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'settlementCardStr': this.dataForm.settlementCardStr ,

          'settlementCard': this.dataForm.settlementCard ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'cardMultiple': this.dataForm.cardMultiple ,

          'defeat': this.dataForm.defeat ,

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
