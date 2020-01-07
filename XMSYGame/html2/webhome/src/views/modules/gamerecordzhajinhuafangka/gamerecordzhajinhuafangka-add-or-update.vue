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
    <el-form-item label="房间id" prop="roomId">
      <el-input v-model="dataForm.roomId" placeholder="房间id"></el-input>
    </el-form-item>
    <el-form-item label="房间名称" prop="roomName">
      <el-input v-model="dataForm.roomName" placeholder="房间名称"></el-input>
    </el-form-item>
    <el-form-item label="游戏模式（1.积分模式2.金币模式）" prop="gameModule">
      <el-input v-model="dataForm.gameModule" placeholder="游戏模式（1.积分模式2.金币模式）"></el-input>
    </el-form-item>
    <el-form-item label="玩法模式（1.抢庄模式、2.通比模式）" prop="gameType">
      <el-input v-model="dataForm.gameType" placeholder="玩法模式（1.抢庄模式、2.通比模式）"></el-input>
    </el-form-item>
    <el-form-item label="支付类型（1.房主支付、2.AA支付）" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="支付类型（1.房主支付、2.AA支付）"></el-input>
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
    <el-form-item label="牌型" prop="cardsInt">
      <el-input v-model="dataForm.cardsInt" placeholder="牌型"></el-input>
    </el-form-item>
    <el-form-item label="牌型" prop="cardsStr">
      <el-input v-model="dataForm.cardsStr" placeholder="牌型"></el-input>
    </el-form-item>
    <el-form-item label="下注倍数" prop="betMultiple">
      <el-input v-model="dataForm.betMultiple" placeholder="下注倍数"></el-input>
    </el-form-item>
    <el-form-item label="牌倍数" prop="brandMultiple">
      <el-input v-model="dataForm.brandMultiple" placeholder="牌倍数"></el-input>
    </el-form-item>
    <el-form-item label="倍数" prop="multiple">
      <el-input v-model="dataForm.multiple" placeholder="倍数"></el-input>
    </el-form-item>
    <el-form-item label="是否机器人" prop="robot">
      <el-input v-model="dataForm.robot" placeholder="是否机器人"></el-input>
    </el-form-item>
    <el-form-item label="游戏局号" prop="gameRoundNo">
      <el-input v-model="dataForm.gameRoundNo" placeholder="游戏局号"></el-input>
    </el-form-item>
    <el-form-item label="局数" prop="round">
      <el-input v-model="dataForm.round" placeholder="局数"></el-input>
    </el-form-item>
    <el-form-item label="房间号" prop="roomNo">
      <el-input v-model="dataForm.roomNo" placeholder="房间号"></el-input>
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
          roomId: '',
          roomName: '',
          gameModule: '',
          gameType: '',
          payType: '',
          baseScore: '',
          coinsBefore: '',
          betCoins: '',
          coins: '',
          coinsAfter: '',
          cardsInt: '',
          cardsStr: '',
          betMultiple: '',
          brandMultiple: '',
          multiple: '',
          robot: '',
          gameRoundNo: '',
          round: '',
          roomNo: '',
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
            { required: true, message: '玩法模式（1.抢庄模式、2.通比模式）不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '支付类型（1.房主支付、2.AA支付）不能为空', trigger: 'blur' }
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
          cardsInt: [
            { required: true, message: '牌型不能为空', trigger: 'blur' }
          ],
          cardsStr: [
            { required: true, message: '牌型不能为空', trigger: 'blur' }
          ],
          betMultiple: [
            { required: true, message: '下注倍数不能为空', trigger: 'blur' }
          ],
          brandMultiple: [
            { required: true, message: '牌倍数不能为空', trigger: 'blur' }
          ],
          multiple: [
            { required: true, message: '倍数不能为空', trigger: 'blur' }
          ],
          robot: [
            { required: true, message: '是否机器人不能为空', trigger: 'blur' }
          ],
          gameRoundNo: [
            { required: true, message: '游戏局号不能为空', trigger: 'blur' }
          ],
          round: [
            { required: true, message: '局数不能为空', trigger: 'blur' }
          ],
          roomNo: [
            { required: true, message: '房间号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordzhajinhuafangka/gamerecordzhajinhuafangka/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordzhajinhuafangka.userId
                this.dataForm.userAccount = data.gamerecordzhajinhuafangka.userAccount
                this.dataForm.gameId = data.gamerecordzhajinhuafangka.gameId
                this.dataForm.gameName = data.gamerecordzhajinhuafangka.gameName
                this.dataForm.gradeId = data.gamerecordzhajinhuafangka.gradeId
                this.dataForm.gradeName = data.gamerecordzhajinhuafangka.gradeName
                this.dataForm.roomId = data.gamerecordzhajinhuafangka.roomId
                this.dataForm.roomName = data.gamerecordzhajinhuafangka.roomName
                this.dataForm.gameModule = data.gamerecordzhajinhuafangka.gameModule
                this.dataForm.gameType = data.gamerecordzhajinhuafangka.gameType
                this.dataForm.payType = data.gamerecordzhajinhuafangka.payType
                this.dataForm.baseScore = data.gamerecordzhajinhuafangka.baseScore
                this.dataForm.coinsBefore = data.gamerecordzhajinhuafangka.coinsBefore
                this.dataForm.betCoins = data.gamerecordzhajinhuafangka.betCoins
                this.dataForm.coins = data.gamerecordzhajinhuafangka.coins
                this.dataForm.coinsAfter = data.gamerecordzhajinhuafangka.coinsAfter
                this.dataForm.cardsInt = data.gamerecordzhajinhuafangka.cardsInt
                this.dataForm.cardsStr = data.gamerecordzhajinhuafangka.cardsStr
                this.dataForm.betMultiple = data.gamerecordzhajinhuafangka.betMultiple
                this.dataForm.brandMultiple = data.gamerecordzhajinhuafangka.brandMultiple
                this.dataForm.multiple = data.gamerecordzhajinhuafangka.multiple
                this.dataForm.robot = data.gamerecordzhajinhuafangka.robot
                this.dataForm.gameRoundNo = data.gamerecordzhajinhuafangka.gameRoundNo
                this.dataForm.round = data.gamerecordzhajinhuafangka.round
                this.dataForm.roomNo = data.gamerecordzhajinhuafangka.roomNo
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
              url: this.$http.adornUrl(`/gamerecordzhajinhuafangka/gamerecordzhajinhuafangka/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'gameId': this.dataForm.gameId ,

          'gameName': this.dataForm.gameName ,

          'gradeId': this.dataForm.gradeId ,

          'gradeName': this.dataForm.gradeName ,

          'roomId': this.dataForm.roomId ,

          'roomName': this.dataForm.roomName ,

          'gameModule': this.dataForm.gameModule ,

          'gameType': this.dataForm.gameType ,

          'payType': this.dataForm.payType ,

          'baseScore': this.dataForm.baseScore ,

          'coinsBefore': this.dataForm.coinsBefore ,

          'betCoins': this.dataForm.betCoins ,

          'coins': this.dataForm.coins ,

          'coinsAfter': this.dataForm.coinsAfter ,

          'cardsInt': this.dataForm.cardsInt ,

          'cardsStr': this.dataForm.cardsStr ,

          'betMultiple': this.dataForm.betMultiple ,

          'brandMultiple': this.dataForm.brandMultiple ,

          'multiple': this.dataForm.multiple ,

          'robot': this.dataForm.robot ,

          'gameRoundNo': this.dataForm.gameRoundNo ,

          'round': this.dataForm.round ,

          'roomNo': this.dataForm.roomNo ,

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
