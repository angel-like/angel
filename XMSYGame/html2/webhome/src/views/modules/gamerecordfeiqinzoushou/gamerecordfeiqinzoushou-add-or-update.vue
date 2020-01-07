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
    <el-form-item label="下注区域" prop="betArea">
      <el-input v-model="dataForm.betArea" placeholder="下注区域"></el-input>
    </el-form-item>
    <el-form-item label="下注区域字符" prop="betAreaStr">
      <el-input v-model="dataForm.betAreaStr" placeholder="下注区域字符"></el-input>
    </el-form-item>
    <el-form-item label="下注金币" prop="betCoins">
      <el-input v-model="dataForm.betCoins" placeholder="下注金币"></el-input>
    </el-form-item>
    <el-form-item label="输赢金币" prop="prizeCoins">
      <el-input v-model="dataForm.prizeCoins" placeholder="输赢金币"></el-input>
    </el-form-item>
    <el-form-item label="开奖牌型" prop="lotteryCard">
      <el-input v-model="dataForm.lotteryCard" placeholder="开奖牌型"></el-input>
    </el-form-item>
    <el-form-item label="开奖牌型字符" prop="lotteryCardStr">
      <el-input v-model="dataForm.lotteryCardStr" placeholder="开奖牌型字符"></el-input>
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
          betArea: '',
          betAreaStr: '',
          betCoins: '',
          prizeCoins: '',
          lotteryCard: '',
          lotteryCardStr: '',
          coinsBefore: '',
          coinsAfter: '',
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
          betArea: [
            { required: true, message: '下注区域不能为空', trigger: 'blur' }
          ],
          betAreaStr: [
            { required: true, message: '下注区域字符不能为空', trigger: 'blur' }
          ],
          betCoins: [
            { required: true, message: '下注金币不能为空', trigger: 'blur' }
          ],
          prizeCoins: [
            { required: true, message: '输赢金币不能为空', trigger: 'blur' }
          ],
          lotteryCard: [
            { required: true, message: '开奖牌型不能为空', trigger: 'blur' }
          ],
          lotteryCardStr: [
            { required: true, message: '开奖牌型字符不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordfeiqinzoushou/gamerecordfeiqinzoushou/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordfeiqinzoushou.userId
                this.dataForm.userAccount = data.gamerecordfeiqinzoushou.userAccount
                this.dataForm.gameId = data.gamerecordfeiqinzoushou.gameId
                this.dataForm.gameName = data.gamerecordfeiqinzoushou.gameName
                this.dataForm.gradeId = data.gamerecordfeiqinzoushou.gradeId
                this.dataForm.gradeName = data.gamerecordfeiqinzoushou.gradeName
                this.dataForm.gradeNumber = data.gamerecordfeiqinzoushou.gradeNumber
                this.dataForm.roomId = data.gamerecordfeiqinzoushou.roomId
                this.dataForm.roomName = data.gamerecordfeiqinzoushou.roomName
                this.dataForm.betArea = data.gamerecordfeiqinzoushou.betArea
                this.dataForm.betAreaStr = data.gamerecordfeiqinzoushou.betAreaStr
                this.dataForm.betCoins = data.gamerecordfeiqinzoushou.betCoins
                this.dataForm.prizeCoins = data.gamerecordfeiqinzoushou.prizeCoins
                this.dataForm.lotteryCard = data.gamerecordfeiqinzoushou.lotteryCard
                this.dataForm.lotteryCardStr = data.gamerecordfeiqinzoushou.lotteryCardStr
                this.dataForm.coinsBefore = data.gamerecordfeiqinzoushou.coinsBefore
                this.dataForm.coinsAfter = data.gamerecordfeiqinzoushou.coinsAfter
                this.dataForm.robot = data.gamerecordfeiqinzoushou.robot
                this.dataForm.banker = data.gamerecordfeiqinzoushou.banker
                this.dataForm.gameRoundNo = data.gamerecordfeiqinzoushou.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordfeiqinzoushou/gamerecordfeiqinzoushou/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'betArea': this.dataForm.betArea ,

          'betAreaStr': this.dataForm.betAreaStr ,

          'betCoins': this.dataForm.betCoins ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'lotteryCard': this.dataForm.lotteryCard ,

          'lotteryCardStr': this.dataForm.lotteryCardStr ,

          'coinsBefore': this.dataForm.coinsBefore ,

          'coinsAfter': this.dataForm.coinsAfter ,

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
