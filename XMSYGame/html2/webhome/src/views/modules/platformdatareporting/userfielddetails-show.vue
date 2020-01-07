<template>
  <el-dialog
    :title="'游戏下注'" width="70%"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <div>
      <span>游戏局号：{{dataForm.gameRoundNo}}</span> <span v-if="show">游戏局数:{{dataForm.round}}</span> <span>游戏Id：{{dataForm.gameId}}</span>
      <span> 房间id：{{dataForm.roomId}}</span>
    </div>
    <el-table :data="showRoundList" border v-loading="showDataListLoading"
              style="width: 100%;">
      <el-table-column prop="userId" header-align="center" align="center" label="用户ID">
      </el-table-column>
      <el-table-column prop="userAccount" header-align="center" align="center" label="用户账号">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="游戏时间">
      </el-table-column>
      <el-table-column prop="gameName" header-align="center" align="center" label="游戏名称">
      </el-table-column>
      <el-table-column prop="gradeName" header-align="center" align="center" label="场次名称">
      </el-table-column>
      <el-table-column prop="roomName" header-align="center" align="center" label="房间名称">
      </el-table-column>
      <el-table-column prop="validBet" header-align="center" align="center" label="有效投注金币">
        <template slot-scope="scope">
          <div>
            {{scope.row.validBet/100}}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="prizeCoins" header-align="center" align="center" label="中奖金币">
        <template slot-scope="scope">
          <div>
            {{scope.row.prizeCoins/100}}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="mini" header-align="center" align="center" label="是否小游戏">
        <template slot-scope="scope">
          <div v-if="scope.row.mini">
            是
          </div>
          <div v-else>
            否
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="waterProfit" header-align="center" align="center" label="抽水金币">
        <template slot-scope="scope">
          <div>
            {{scope.row.waterProfit/100}}
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==1" prop="cardsStr" header-align="center" align="center" label="牌型">
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==3" prop="betAreaStr" header-align="center" align="center" label="下注区域">
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==3 && dataForm.gameId==9 " prop="openCardStr" header-align="center"
                       align="center" label="丢弃的牌">
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==3 && dataForm.gameId==9 " prop="dragonCardStr" header-align="center"
                       align="center" label="龙牌">
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==3 && dataForm.gameId==9 " prop="tigerCardStr" header-align="center"
                       align="center" label="虎牌">
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==3 && dataForm.gameId!=9 " prop="idleCardStr" header-align="center"
                       align="center" label="玩家牌型">
      </el-table-column>
      <el-table-column v-if="dataForm.roomId==3 && dataForm.gameId!=9" prop="bankerCardStr" header-align="center"
                       align="center" label="庄家牌型">
      </el-table-column>

    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        visible: false,
        show: true,
        options: [],
        dataForm: {
          gameRoundNo: '',
          round: 0,
          gameId: 0,
          roomId: 0,
        },
        showRoundList: [],
        showDataListLoading: false
      }
    },
    methods: {
      init(gameRoundNo, round, gameId, roomId) {
        this.visible = true;
        this.showDataListLoading = true
        this.dataForm.gameRoundNo = gameRoundNo;
        this.dataForm.round = round;
        this.dataForm.gameId = gameId;
        this.dataForm.roomId = roomId;
        this.$http({
          url: this.$http.adornUrl('/user/gemerecord/showRoundList'),
          method: 'get',
          params: this.$http.adornParams({
            'gameRoundNo': this.dataForm.gameRoundNo,
            'round': this.dataForm.round,
            'gameId': this.dataForm.gameId,
            'roomId': this.dataForm.roomId,
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.showRoundList = data.gameRecordList
            if (!this.dataForm.round) {
              this.show = false
            }
          } else {
            this.showRoundList = []
          }
          this.showDataListLoading = false
        })


      }
    }
  }
</script>
