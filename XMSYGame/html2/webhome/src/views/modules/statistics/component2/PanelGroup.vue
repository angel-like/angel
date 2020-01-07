<template>
  <el-row :gutter="40" class="panel-group" v-if="objAmount!=null">
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='takeAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('takeAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people svg-size">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  :name="objGroup.svg1" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">{{objGroup.title1}}</div>
					<span class="span-size">{{objAmount.takeAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.takeAmount" :duration="2600" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='moneyAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('moneyAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-message svg-size">
            <!-- <i class="iconfont icon-jine1"></i> -->
						<icon-svg  :name="objGroup.svg2" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">{{objGroup.title2}}</div>
					<span class="span-size">{{objAmount.moneyAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.moneyAmount" :duration="3000" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="23" :lg="24" class="card-panel-col">
      <div :class="activeClass==='commissionAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('commissionAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people svg-size">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  :name="objGroup.svg3" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">{{objGroup.title3}}</div>
					<span class="span-size">{{objAmount.commissionAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.commissionAmount" :duration="2600" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='poundage'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('poundage')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-message svg-size" >
            <!-- <i class="iconfont icon-jine1"></i> -->
						<icon-svg  :name="objGroup.svg4" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">{{objGroup.title4}}</div>
					<span class="span-size">{{objAmount.poundage}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.poundage" :duration="3000" class="card-panel-num"/> -->
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="23" :lg="24" class="card-panel-col">
      <div :class="activeClass==='moneyObtainAmount'?'card-panel card-panel-active':'card-panel'" @click="handleSetLineChartData('moneyObtainAmount')">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people svg-size">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  :name="objGroup.svg5" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">{{objGroup.title5}}</div>
					<span class="span-size">{{objAmount.moneyObtainAmount}}</span>
          <!-- <count-to :start-val="0" :end-val="objAmount.moneyObtainAmount" :duration="2600" class="card-panel-num"/> -->
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
	props:['objAmount','objGroup'],
  methods: {
    handleSetLineChartData (type) {
      console.log(this)
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
	.span-size{
		font-size: 20px;
		font-weight: 600;
	}
	.svg-size{
		font-size: 40px;
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
