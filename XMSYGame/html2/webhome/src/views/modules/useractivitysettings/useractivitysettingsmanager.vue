<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
      <el-tab-pane label="游戏任务设置" v-if="isAuth('useractivitysettings:tabs:playertasks')" name="playertasks">
        <panl-playertasks ref="panlPlayertasks"></panl-playertasks>
      </el-tab-pane>
      <el-tab-pane label="返利奖励设置" v-if="isAuth('useractivitysettings:tabs:userrebate')" name="userrebate">
        <panl-userrebate ref="panlUserrebate"></panl-userrebate>
      </el-tab-pane>
      <el-tab-pane label="充值优惠设置" v-if="isAuth('useractivitysettings:tabs:orderpreferential')" name="orderpreferential">
        <panl-orderpreferential ref="panlOrderpreferential"></panl-orderpreferential>
      </el-tab-pane>
      <!-- <el-tab-pane label="连续签到奖励设置" v-if="isAuth('useractivitysettings:tabs:signrewardcontinuous')" name="signrewardcontinuous">
                <panl-signrewardcontinuous ref="panlSignrewardcontinuous"></panl-signrewardcontinuous>
            </el-tab-pane> -->
      <el-tab-pane label="金币奖励设置" v-if="isAuth('useractivitysettings:tabs:giftcoinconfig')" name="giftcoinconfig">
        <panl-giftcoinconfig ref="panlGiftcoinconfig"></panl-giftcoinconfig>
      </el-tab-pane>
      <el-tab-pane label="日常签到奖励设置" v-if="isAuth('useractivitysettings:tabs:signrewardcontinuouseveryday')"
                   name="signrewardcontinuouseveryday">
        <panl-signrewardcontinuouseveryday ref="panlSignrewardcontinuouseveryday"></panl-signrewardcontinuouseveryday>
      </el-tab-pane>
      <el-tab-pane label="幸运转盘" v-if="isAuth('useractivitysettings:tabs:lucky')" name="lucky">
        <panlucky ref="lucky"></panlucky>
        <!--<panl-signrewardcontinuouseveryday ref="panlSignrewardcontinuouseveryday"></panl-signrewardcontinuouseveryday>-->
      </el-tab-pane>
      <el-tab-pane label="天降财神" v-if="isAuth('useractivitysettings:tabs:fortune')" name="fortune">
        <fortune ref="fortune"></fortune>
        <!--<panl-signrewardcontinuouseveryday ref="panlSignrewardcontinuouseveryday"></panl-signrewardcontinuouseveryday>-->
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import PanlUserrebate from './userrebate'
  import PanlSignrewardcontinuous from './signrewardcontinuous'
  import PanlGiftcoinconfig from './giftcoinconfig'
  import PanlOrderpreferential from './orderpreferential'
  import PanlSignrewardcontinuouseveryday from './signrewardcontinuouseveryday'
  import PanlPlayertasks from './playertasks'
  import Panlucky from '../lucky/lucky'
  import Fortune from '../fortuneactivitymanager/fortuneactiviconfig'

  export default {
    data() {
      return {
        dataForm: {
          activeName: 'playertasks',
          paramKey: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      PanlUserrebate,
      PanlSignrewardcontinuous,
      PanlGiftcoinconfig,
      PanlOrderpreferential,
      PanlSignrewardcontinuouseveryday,
      PanlPlayertasks,
      Panlucky,
      Fortune
    },
    activated() {
      //this.getDataList()
    },
    methods: {
      handleTab(tab, event) {
        if (tab.name == "userrebate") {
          this.$nextTick(() => {
            this.$refs.panlUserrebate.getDataList()
          })
        } else if (tab.name == "signrewardcontinuous") {
          this.$nextTick(() => {
            this.$refs.panlSignrewardcontinuous.getDataList()
          })
        } else if (tab.name == "giftcoinconfig") {
          this.$nextTick(() => {
            this.$refs.panlGiftcoinconfig.getDataList()
          })
        } else if (tab.name == "orderpreferential") {
          this.$nextTick(() => {
            this.$refs.panlOrderpreferential.getDataList()
          })
        } else if (tab.name == "signrewardcontinuouseveryday") {
          this.$nextTick(() => {
            this.$refs.panlSignrewardcontinuouseveryday.getDataList()
          })
        } else if (tab.name == "playertasks") {
          this.$nextTick(() => {
            this.$refs.panlPlayertasks.getDataList()
          })
        } else if (tab.name == "lucky") {
          this.$nextTick(() => {
            this.$refs.panlucky.getDataList()
          })
        } else if (tab.name == "fortune") {
          this.$nextTick(() => {
            this.$refs.fortune.getDataList()
          })
        }
      },
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
      }

    }
  }
</script>
