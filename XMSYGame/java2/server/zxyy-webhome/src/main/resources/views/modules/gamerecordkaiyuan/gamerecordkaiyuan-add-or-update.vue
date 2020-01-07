<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="游戏局号列表" prop="gameId">
      <el-input v-model="dataForm.gameId" placeholder="游戏局号列表"></el-input>
    </el-form-item>
    <el-form-item label="玩家帐号列表" prop="accounts">
      <el-input v-model="dataForm.accounts" placeholder="玩家帐号列表"></el-input>
    </el-form-item>
    <el-form-item label="房间 ID 列表" prop="serverId">
      <el-input v-model="dataForm.serverId" placeholder="房间 ID 列表"></el-input>
    </el-form-item>
    <el-form-item label="游戏 ID 列表" prop="kindId">
      <el-input v-model="dataForm.kindId" placeholder="游戏 ID 列表"></el-input>
    </el-form-item>
    <el-form-item label="桌子号列表" prop="tableId">
      <el-input v-model="dataForm.tableId" placeholder="桌子号列表"></el-input>
    </el-form-item>
    <el-form-item label="椅子号列表" prop="chairId">
      <el-input v-model="dataForm.chairId" placeholder="椅子号列表"></el-input>
    </el-form-item>
    <el-form-item label="玩家数量列表" prop="userCount">
      <el-input v-model="dataForm.userCount" placeholder="玩家数量列表"></el-input>
    </el-form-item>
    <el-form-item label="手牌公共牌" prop="cardValue">
      <el-input v-model="dataForm.cardValue" placeholder="手牌公共牌"></el-input>
    </el-form-item>
    <el-form-item label="有效下注" prop="cellScore">
      <el-input v-model="dataForm.cellScore" placeholder="有效下注"></el-input>
    </el-form-item>
    <el-form-item label="总下注列表" prop="allBet">
      <el-input v-model="dataForm.allBet" placeholder="总下注列表"></el-input>
    </el-form-item>
    <el-form-item label="盈利列表" prop="profit">
      <el-input v-model="dataForm.profit" placeholder="盈利列表"></el-input>
    </el-form-item>
    <el-form-item label="抽水列表" prop="revenue">
      <el-input v-model="dataForm.revenue" placeholder="抽水列表"></el-input>
    </el-form-item>
    <el-form-item label="游戏开始时间列表" prop="gameStartTime">
      <el-input v-model="dataForm.gameStartTime" placeholder="游戏开始时间列表"></el-input>
    </el-form-item>
    <el-form-item label="游戏结束时间列表" prop="gameEndTime">
      <el-input v-model="dataForm.gameEndTime" placeholder="游戏结束时间列表"></el-input>
    </el-form-item>
    <el-form-item label="渠道 ID 列表" prop="channelId">
      <el-input v-model="dataForm.channelId" placeholder="渠道 ID 列表"></el-input>
    </el-form-item>
    <el-form-item label="游戏结果对应玩家所属站点" prop="lineCode">
      <el-input v-model="dataForm.lineCode" placeholder="游戏结果对应玩家所属站点"></el-input>
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
          gameId: '',
          accounts: '',
          serverId: '',
          kindId: '',
          tableId: '',
          chairId: '',
          userCount: '',
          cardValue: '',
          cellScore: '',
          allBet: '',
          profit: '',
          revenue: '',
          gameStartTime: '',
          gameEndTime: '',
          channelId: '',
          lineCode: '',
        },
        dataRule: {
          gameId: [
            { required: true, message: '游戏局号列表不能为空', trigger: 'blur' }
          ],
          accounts: [
            { required: true, message: '玩家帐号列表不能为空', trigger: 'blur' }
          ],
          serverId: [
            { required: true, message: '房间 ID 列表不能为空', trigger: 'blur' }
          ],
          kindId: [
            { required: true, message: '游戏 ID 列表不能为空', trigger: 'blur' }
          ],
          tableId: [
            { required: true, message: '桌子号列表不能为空', trigger: 'blur' }
          ],
          chairId: [
            { required: true, message: '椅子号列表不能为空', trigger: 'blur' }
          ],
          userCount: [
            { required: true, message: '玩家数量列表不能为空', trigger: 'blur' }
          ],
          cardValue: [
            { required: true, message: '手牌公共牌不能为空', trigger: 'blur' }
          ],
          cellScore: [
            { required: true, message: '有效下注不能为空', trigger: 'blur' }
          ],
          allBet: [
            { required: true, message: '总下注列表不能为空', trigger: 'blur' }
          ],
          profit: [
            { required: true, message: '盈利列表不能为空', trigger: 'blur' }
          ],
          revenue: [
            { required: true, message: '抽水列表不能为空', trigger: 'blur' }
          ],
          gameStartTime: [
            { required: true, message: '游戏开始时间列表不能为空', trigger: 'blur' }
          ],
          gameEndTime: [
            { required: true, message: '游戏结束时间列表不能为空', trigger: 'blur' }
          ],
          channelId: [
            { required: true, message: '渠道 ID 列表不能为空', trigger: 'blur' }
          ],
          lineCode: [
            { required: true, message: '游戏结果对应玩家所属站点不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerecordkaiyuan/gamerecordkaiyuan/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.gameId = data.gamerecordkaiyuan.gameId
                this.dataForm.accounts = data.gamerecordkaiyuan.accounts
                this.dataForm.serverId = data.gamerecordkaiyuan.serverId
                this.dataForm.kindId = data.gamerecordkaiyuan.kindId
                this.dataForm.tableId = data.gamerecordkaiyuan.tableId
                this.dataForm.chairId = data.gamerecordkaiyuan.chairId
                this.dataForm.userCount = data.gamerecordkaiyuan.userCount
                this.dataForm.cardValue = data.gamerecordkaiyuan.cardValue
                this.dataForm.cellScore = data.gamerecordkaiyuan.cellScore
                this.dataForm.allBet = data.gamerecordkaiyuan.allBet
                this.dataForm.profit = data.gamerecordkaiyuan.profit
                this.dataForm.revenue = data.gamerecordkaiyuan.revenue
                this.dataForm.gameStartTime = data.gamerecordkaiyuan.gameStartTime
                this.dataForm.gameEndTime = data.gamerecordkaiyuan.gameEndTime
                this.dataForm.channelId = data.gamerecordkaiyuan.channelId
                this.dataForm.lineCode = data.gamerecordkaiyuan.lineCode
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
              url: this.$http.adornUrl(`/gamerecordkaiyuan/gamerecordkaiyuan/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'gameId': this.dataForm.gameId ,

          'accounts': this.dataForm.accounts ,

          'serverId': this.dataForm.serverId ,

          'kindId': this.dataForm.kindId ,

          'tableId': this.dataForm.tableId ,

          'chairId': this.dataForm.chairId ,

          'userCount': this.dataForm.userCount ,

          'cardValue': this.dataForm.cardValue ,

          'cellScore': this.dataForm.cellScore ,

          'allBet': this.dataForm.allBet ,

          'profit': this.dataForm.profit ,

          'revenue': this.dataForm.revenue ,

          'gameStartTime': this.dataForm.gameStartTime ,

          'gameEndTime': this.dataForm.gameEndTime ,

          'channelId': this.dataForm.channelId ,

          'lineCode': this.dataForm.lineCode ,

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
