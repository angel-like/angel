<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col">
      <div :class="activeClass==='a1'?'card-panel card-panel-active':'card-panel'" >
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people" style="font-size: 40px;">
            <!-- <i class="iconfont icon-Member"></i> -->
							<icon-svg  name="huiyuan" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">会员人数</div>
          <count-to :start-val="0" :end-val="userNumber" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
		<el-col :xs="24" :sm="24" :lg="24" class="card-panel-col" style="margin-top: 60px;">
      <div :class="activeClass==='a1'?'card-panel card-panel-active':'card-panel'" >
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people" style="font-size: 40px;">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  name="youxiaohuiyuan" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">会员有效人数</div>
          <count-to :start-val="0" :end-val="validUserNumber" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
		<el-col :xs="24" :sm="24" :lg="24" class="card-panel-col" style="margin-top: 60px;">
      <div :class="activeClass==='a1'?'card-panel card-panel-active':'card-panel'" >
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people" style="font-size: 40px;">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  name="youxiaohuiyuan" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">PC端在线人数</div>
          <count-to :start-val="0" :end-val="pcNum" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col" style="margin-top: 60px;">
      <div :class="activeClass==='a1'?'card-panel card-panel-active':'card-panel'" >
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people" style="font-size: 40px;">
            <!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  name="youxiaohuiyuan" />
          </div>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">安卓端在线人数</div>
          <count-to :start-val="0" :end-val="androidNum" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="24" :sm="24" :lg="24" class="card-panel-col" style="margin-top: 60px;">
    	<div :class="activeClass==='a1'?'card-panel card-panel-active':'card-panel'" >
    		<div class="div-flex">
    			<div class="card-panel-icon-wrapper icon-people" style="font-size: 40px;">
    				<!-- <i class="iconfont icon-Member"></i> -->
    				<icon-svg  name="youxiaohuiyuan" />
    			</div>
    		</div>
    		<div class="card-panel-description">
    			<div class="card-panel-text">苹果端在线人数</div>
    			<count-to :start-val="0" :end-val="iphoneNum" :duration="2600" class="card-panel-num"/>
    		</div>
    	</div>
    </el-col>
		<el-col :xs="24" :sm="24" :lg="24" class="card-panel-col" style="margin-top: 60px;">
			<div :class="activeClass==='a1'?'card-panel card-panel-active':'card-panel'" >
				<div class="div-flex">
					<div class="card-panel-icon-wrapper icon-people" style="font-size: 40px;">
						<!-- <i class="iconfont icon-Member"></i> -->
						<icon-svg  name="youxiaohuiyuan" />
					</div>
				</div>
				<div class="card-panel-description">
					<div class="card-panel-text">其他在线人数</div>
					<count-to :start-val="0" :end-val="otherNum" :duration="2600" class="card-panel-num"/>
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
			userNumber:0,
			validUserNumber:0,
			pcNum:0,
			androidNum:0,
			iphoneNum:0,
			otherNum:0,
      activeClass: null
    }
  },
	activated () {
		this.getDataList()
	},
  methods: {
   // 获取数据列表
   getDataList () {
   	this.$http({
   		url: this.$http.adornUrl('/userstatistics/userstatistics/list'),
   		method: 'get',
   		params: this.$http.adornParams({
   		})
   	}).then(({data}) => {
   		if (data && data.code === 200) {
   			this.userNumber = data.data.userNumber
   			this.validUserNumber = data.data.validUserNumber
   			this.pcNum = data.data.pcNum
   			this.androidNum = data.data.androidNum
   			this.iphoneNum = data.data.iphoneNum
   			this.otherNum = data.data.otherNum
   		} 
   	})
   },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  .card-panel-col{
    margin-bottom: 20px;
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
