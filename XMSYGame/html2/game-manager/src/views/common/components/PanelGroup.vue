<template>
	
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('userNumber')">
        <div class="card-panel-icon-wrapper icon-people">
          <i class="iconfont icon-Member"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">新增会员数</div>
          <count-to :start-val="0" :end-val="data.list.userNumber" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('firstchargeAmount')">
        <div class="card-panel-icon-wrapper icon-message">
          <i class="iconfont icon-jine1"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">首充总额</div>
          <count-to :start-val="0" :end-val="data.list.firstchargeAmount" :duration="3000" class="card-panel-num"/>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('auxiliaryExpenses')">
        <div class="card-panel-icon-wrapper icon-money">
          <i class="iconfont icon-feiyongguanli"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">手续费</div>
          <count-to :start-val="0" :end-val="data.list.auxiliaryExpenses" :duration="3200" class="card-panel-num"/>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('takeAmount')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <i class="iconfont icon-jine"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">取款总金额</div>
          <count-to :start-val="0" :end-val="data.list.takeAmount" :duration="3600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <!-- 增加 -->
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('notPassAmount')">
        <div class="card-panel-icon-wrapper icon-people">
          <i class="iconfont icon-quxiao"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">取消取款总金额</div>
          <count-to :start-val="0" :end-val="data.list.notPassAmount" :duration="3600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('avalibleBet')">
        <div class="card-panel-icon-wrapper icon-message">
          <i class="iconfont icon-qian"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">有效投注</div>
          <count-to :start-val="0" :end-val="data.list.avalibleBet" :duration="3600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('commisionProfit')">
        <div class="card-panel-icon-wrapper icon-money">
          <i class="iconfont icon-haoyouyongjinxishu"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">佣金</div>
          <count-to :start-val="0" :end-val="data.list.commisionProfit" :duration="3600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('prizeCoins')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <i class="iconfont icon-yonghucuxiao"></i>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">用户总盈利</div>
          <count-to :start-val="0" :end-val="data.list.prizeCoins" :duration="3600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
	data(){
		return{
			userNumber:'0',
			firstchargeAmount:'0',
			auxiliaryExpenses:'0',
			takeAmount:'0',
			notPassAmount:'0',
			avalibleBet:'0',
			commisionProfit:'0',
			prizeCoins:'0'
		}
	},
	props:['data'],
  components: {
    CountTo
  },
  methods: {
    handleSetLineChartData (type) {
      this.$emit('handleSetLineChartData', type)
    }
		
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;

      }
      .icon-people {
         background: #40c9c6;
      }
      .icon-message {
        background: #36a3f7;
      }
      .icon-money {
        background: #f4516c;
      }
      .icon-shopping {
        background: #34bfa3
      }
    }
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
      i{
        font-size: 40px;
      }
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 14px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
