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
    <el-form-item label="" prop="betArea">
      <el-input v-model="dataForm.betArea" placeholder=""></el-input>
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
    <el-form-item label="丢弃的牌" prop="openCard">
      <el-input v-model="dataForm.openCard" placeholder="丢弃的牌"></el-input>
    </el-form-item>
    <el-form-item label="丢弃的牌字符" prop="openCardStr">
      <el-input v-model="dataForm.openCardStr" placeholder="丢弃的牌字符"></el-input>
    </el-form-item>
    <el-form-item label="龙牌型 " prop="dragonCard">
      <el-input v-model="dataForm.dragonCard" placeholder="龙牌型 "></el-input>
    </el-form-item>
    <el-form-item label="龙牌型字符" prop="dragonCardStr">
      <el-input v-model="dataForm.dragonCardStr" placeholder="龙牌型字符"></el-input>
    </el-form-item>
    <el-form-item label="龙牌型" prop="tigerCard">
      <el-input v-model="dataForm.tigerCard" placeholder="龙牌型"></el-input>
    </el-form-item>
    <el-form-item label="龙牌型字符" prop="tigerCardStr">
      <el-input v-model="dataForm.tigerCardStr" placeholder="龙牌型字符"></el-input>
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
          roomId: '',
          roomName: '',
          betArea: '',
          betAreaStr: '',
          betCoins: '',
          prizeCoins: '',
          openCard: '',
          openCardStr: '',
          dragonCard: '',
          dragonCardStr: '',
          tigerCard: '',
          tigerCardStr: '',
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
          roomId: [
            { required: true, message: '房间id不能为空', trigger: 'blur' }
          ],
          roomName: [
            { required: true, message: '房间名称不能为空', trigger: 'blur' }
          ],
          betArea: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
          openCard: [
            { required: true, message: '丢弃的牌不能为空', trigger: 'blur' }
          ],
          openCardStr: [
            { required: true, message: '丢弃的牌字符不能为空', trigger: 'blur' }
          ],
          dragonCard: [
            { required: true, message: '龙牌型 不能为空', trigger: 'blur' }
          ],
          dragonCardStr: [
            { required: true, message: '龙牌型字符不能为空', trigger: 'blur' }
          ],
          tigerCard: [
            { required: true, message: '龙牌型不能为空', trigger: 'blur' }
          ],
          tigerCardStr: [
            { required: true, message: '龙牌型字符不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordlonghu/gamerecordlonghu/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordlonghu.userId
                this.dataForm.userAccount = data.gamerecordlonghu.userAccount
                this.dataForm.gameId = data.gamerecordlonghu.gameId
                this.dataForm.gameName = data.gamerecordlonghu.gameName
                this.dataForm.gradeId = data.gamerecordlonghu.gradeId
                this.dataForm.gradeName = data.gamerecordlonghu.gradeName
                this.dataForm.roomId = data.gamerecordlonghu.roomId
                this.dataForm.roomName = data.gamerecordlonghu.roomName
                this.dataForm.betArea = data.gamerecordlonghu.betArea
                this.dataForm.betAreaStr = data.gamerecordlonghu.betAreaStr
                this.dataForm.betCoins = data.gamerecordlonghu.betCoins
                this.dataForm.prizeCoins = data.gamerecordlonghu.prizeCoins
                this.dataForm.openCard = data.gamerecordlonghu.openCard
                this.dataForm.openCardStr = data.gamerecordlonghu.openCardStr
                this.dataForm.dragonCard = data.gamerecordlonghu.dragonCard
                this.dataForm.dragonCardStr = data.gamerecordlonghu.dragonCardStr
                this.dataForm.tigerCard = data.gamerecordlonghu.tigerCard
                this.dataForm.tigerCardStr = data.gamerecordlonghu.tigerCardStr
                this.dataForm.coinsBefore = data.gamerecordlonghu.coinsBefore
                this.dataForm.coinsAfter = data.gamerecordlonghu.coinsAfter
                this.dataForm.robot = data.gamerecordlonghu.robot
                this.dataForm.gameRoundNo = data.gamerecordlonghu.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordlonghu/gamerecordlonghu/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'betArea': this.dataForm.betArea ,

          'betAreaStr': this.dataForm.betAreaStr ,

          'betCoins': this.dataForm.betCoins ,

          'prizeCoins': this.dataForm.prizeCoins ,

          'openCard': this.dataForm.openCard ,

          'openCardStr': this.dataForm.openCardStr ,

          'dragonCard': this.dataForm.dragonCard ,

          'dragonCardStr': this.dataForm.dragonCardStr ,

          'tigerCard': this.dataForm.tigerCard ,

          'tigerCardStr': this.dataForm.tigerCardStr ,

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
