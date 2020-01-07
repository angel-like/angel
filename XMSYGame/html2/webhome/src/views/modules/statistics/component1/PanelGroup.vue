<template>
  <el-row :gutter="40" class="panel-group" v-if="objAmount!== 'undefine'">
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='rechargeAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('rechargeAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people svg-size">
            <!-- <i class="iconfont icon-Member"></i> -->
							<icon-svg  name="chongzhizonge" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">充值总额</div>
					<span class="span-size">{{objAmount.rechargeAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.rechargeAmount" :duration="2600" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='adminAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('adminAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-message svg-size">
            <!-- <i class="iconfont icon-jine1"></i> -->
						<icon-svg  name="rengongchongzhi" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">人工充值</div>
					<span class="span-size">{{objAmount.adminAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.adminAmount" :duration="3000" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="23" :lg="24" class="card-panel-col">
      <div :class="activeClass==='thirdAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('thirdAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people svg-size">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  name="disanfang" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">第三方存款</div>
					<span class="span-size">{{objAmount.thirdAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.thirdAmount" :duration="2600" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='bankAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('bankAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-message svg-size">
            <!-- <i class="iconfont icon-jine1"></i> -->
						<icon-svg  name="xianxiachongzhi" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">线下存款</div>
						<span class="span-size">{{objAmount.bankAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.bankAmount" :duration="3000" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="23" :lg="24" class="card-panel-col">
      <div :class="activeClass==='discountAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('discountAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people svg-size">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  name="youhui" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">优惠</div>
					<span class="span-size">{{objAmount.discountAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.discountAmount" :duration="2600" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  components: {
    CountTo
  },
  data () {
    return {
      activeClass: null
    }
  },
	props:['objAmount'],
  methods: {
    handleSetLineChartData (type) {
      this.$emit('handleSetLineChartData', type)
      this.activeClass = type
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  .card-panel-col{
    margin-bottom: 20px;
  }
	.svg-size{
			font-size: 40px;
	}
.span-size{
		font-size: 20px;
		font-weight: 600;
	}
  .card-panel {
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    border-radius: 15px;
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
    .div-flex{
      display: flex;
    }
    .card-panel-icon-wrapper {
      // width: 78px;
      // height: 78px;
      margin: auto;
      margin-top: 14px;
      margin-bottom: 14px;
      padding: 8px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
      i{
        font-size: 34px;
      }
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      font-weight: bold;
      margin: 7px 26px;
      display: flex;
      justify-content: space-between;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
  .card-panel-active {
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
}
</style>
