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
    <el-form-item label="庄家牌型" prop="bankerCard">
      <el-input v-model="dataForm.bankerCard" placeholder="庄家牌型"></el-input>
    </el-form-item>
    <el-form-item label="庄家牌型字符" prop="bankerCardStr">
      <el-input v-model="dataForm.bankerCardStr" placeholder="庄家牌型字符"></el-input>
    </el-form-item>
    <el-form-item label="闲家牌型" prop="idleCard">
      <el-input v-model="dataForm.idleCard" placeholder="闲家牌型"></el-input>
    </el-form-item>
    <el-form-item label="闲家牌型字符" prop="idleCardStr">
      <el-input v-model="dataForm.idleCardStr" placeholder="闲家牌型字符"></el-input>
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
          bankerCard: '',
          bankerCardStr: '',
          idleCard: '',
          idleCardStr: '',
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
          bankerCard: [
            { required: true, message: '庄家牌型不能为空', trigger: 'blur' }
          ],
          bankerCardStr: [
            { required: true, message: '庄家牌型字符不能为空', trigger: 'blur' }
          ],
          idleCard: [
            { required: true, message: '闲家牌型不能为空', trigger: 'blur' }
          ],
          idleCardStr: [
            { required: true, message: '闲家牌型字符不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordbairenpaijiu/gamerecordbairenpaijiu/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordbairenpaijiu.userId
                this.dataForm.userAccount = data.gamerecordbairenpaijiu.userAccount
                this.dataForm.gameId = data.gamerecordbairenpaijiu.gameId
                this.dataForm.gameName = data.gamerecordbairenpaijiu.gameName
                this.dataForm.gradeId = data.gamerecordbairenpaijiu.gradeId
                this.dataForm.gradeName = data.gamerecordbairenpaijiu.gradeName
                this.dataForm.roomId = data.gamerecordbairenpaijiu.roomId
                this.dataForm.roomName = data.gamerecordbairenpaijiu.roomName
                this.dataForm.betArea = data.gamerecordbairenpaijiu.betArea
                this.dataForm.betAreaStr = data.gamerecordbairenpaijiu.betAreaStr
                this.dataForm.betCoins = data.gamerecordbairenpaijiu.betCoins
                this.dataForm.prizeCoins = data.gamerecordbairenpaijiu.prizeCoins
                this.dataForm.bankerCard = data.gamerecordbairenpaijiu.bankerCard
                this.dataForm.bankerCardStr = data.gamerecordbairenpaijiu.bankerCardStr
                this.dataForm.idleCard = data.gamerecordbairenpaijiu.idleCard
                this.dataForm.idleCardStr = data.gamerecordbairenpaijiu.idleCardStr
                this.dataForm.coinsBefore = data.gamerecordbairenpaijiu.coinsBefore
                this.dataForm.coinsAfter = data.gamerecordbairenpaijiu.coinsAfter
                this.dataForm.robot = data.gamerecordbairenpaijiu.robot
                this.dataForm.gameRoundNo = data.gamerecordbairenpaijiu.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordbairenpaijiu/gamerecordbairenpaijiu/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'bankerCard': this.dataForm.bankerCard ,

          'bankerCardStr': this.dataForm.bankerCardStr ,

          'idleCard': this.dataForm.idleCard ,

          'idleCardStr': this.dataForm.idleCardStr ,

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
