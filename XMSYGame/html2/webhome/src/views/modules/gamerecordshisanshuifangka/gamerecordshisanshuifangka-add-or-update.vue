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
    <el-form-item label="牌型" prop="headCardsInt">
      <el-input v-model="dataForm.headCardsInt" placeholder="牌型"></el-input>
    </el-form-item>
    <el-form-item label="牌型" prop="headCardsStr">
      <el-input v-model="dataForm.headCardsStr" placeholder="牌型"></el-input>
    </el-form-item>
    <el-form-item label="中墩牌型" prop="middleCardsInt">
      <el-input v-model="dataForm.middleCardsInt" placeholder="中墩牌型"></el-input>
    </el-form-item>
    <el-form-item label="中墩牌型" prop="middleCardsStr">
      <el-input v-model="dataForm.middleCardsStr" placeholder="中墩牌型"></el-input>
    </el-form-item>
    <el-form-item label="尾墩牌型" prop="bottomCardsInt">
      <el-input v-model="dataForm.bottomCardsInt" placeholder="尾墩牌型"></el-input>
    </el-form-item>
    <el-form-item label="尾墩牌型" prop="bottomCardsStr">
      <el-input v-model="dataForm.bottomCardsStr" placeholder="尾墩牌型"></el-input>
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
          baseScore: '',
          coinsBefore: '',
          betCoins: '',
          coins: '',
          coinsAfter: '',
          headCardsInt: '',
          headCardsStr: '',
          middleCardsInt: '',
          middleCardsStr: '',
          bottomCardsInt: '',
          bottomCardsStr: '',
          multiple: '',
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
          headCardsInt: [
            { required: true, message: '牌型不能为空', trigger: 'blur' }
          ],
          headCardsStr: [
            { required: true, message: '牌型不能为空', trigger: 'blur' }
          ],
          middleCardsInt: [
            { required: true, message: '中墩牌型不能为空', trigger: 'blur' }
          ],
          middleCardsStr: [
            { required: true, message: '中墩牌型不能为空', trigger: 'blur' }
          ],
          bottomCardsInt: [
            { required: true, message: '尾墩牌型不能为空', trigger: 'blur' }
          ],
          bottomCardsStr: [
            { required: true, message: '尾墩牌型不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordshisanshuifangka/gamerecordshisanshuifangka/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.gamerecordshisanshuifangka.userId
                this.dataForm.userAccount = data.gamerecordshisanshuifangka.userAccount
                this.dataForm.gameId = data.gamerecordshisanshuifangka.gameId
                this.dataForm.gameName = data.gamerecordshisanshuifangka.gameName
                this.dataForm.gradeId = data.gamerecordshisanshuifangka.gradeId
                this.dataForm.gradeName = data.gamerecordshisanshuifangka.gradeName
                this.dataForm.roomId = data.gamerecordshisanshuifangka.roomId
                this.dataForm.roomName = data.gamerecordshisanshuifangka.roomName
                this.dataForm.baseScore = data.gamerecordshisanshuifangka.baseScore
                this.dataForm.coinsBefore = data.gamerecordshisanshuifangka.coinsBefore
                this.dataForm.betCoins = data.gamerecordshisanshuifangka.betCoins
                this.dataForm.coins = data.gamerecordshisanshuifangka.coins
                this.dataForm.coinsAfter = data.gamerecordshisanshuifangka.coinsAfter
                this.dataForm.headCardsInt = data.gamerecordshisanshuifangka.headCardsInt
                this.dataForm.headCardsStr = data.gamerecordshisanshuifangka.headCardsStr
                this.dataForm.middleCardsInt = data.gamerecordshisanshuifangka.middleCardsInt
                this.dataForm.middleCardsStr = data.gamerecordshisanshuifangka.middleCardsStr
                this.dataForm.bottomCardsInt = data.gamerecordshisanshuifangka.bottomCardsInt
                this.dataForm.bottomCardsStr = data.gamerecordshisanshuifangka.bottomCardsStr
                this.dataForm.multiple = data.gamerecordshisanshuifangka.multiple
                this.dataForm.robot = data.gamerecordshisanshuifangka.robot
                this.dataForm.gameRoundNo = data.gamerecordshisanshuifangka.gameRoundNo
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
              url: this.$http.adornUrl(`/gamerecordshisanshuifangka/gamerecordshisanshuifangka/${!this.dataForm.id ? 'save' : 'update'}`),
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

          'baseScore': this.dataForm.baseScore ,

          'coinsBefore': this.dataForm.coinsBefore ,

          'betCoins': this.dataForm.betCoins ,

          'coins': this.dataForm.coins ,

          'coinsAfter': this.dataForm.coinsAfter ,

          'headCardsInt': this.dataForm.headCardsInt ,

          'headCardsStr': this.dataForm.headCardsStr ,

          'middleCardsInt': this.dataForm.middleCardsInt ,

          'middleCardsStr': this.dataForm.middleCardsStr ,

          'bottomCardsInt': this.dataForm.bottomCardsInt ,

          'bottomCardsStr': this.dataForm.bottomCardsStr ,

          'multiple': this.dataForm.multiple ,

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
