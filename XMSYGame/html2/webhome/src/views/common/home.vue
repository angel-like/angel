<template>
  <div class="mod-home">
    <h3>欢迎使用棋牌管理后台系统</h3>
    <!--<div class="mod-home-all" v-if="isAuth('userstatistics:userstatistics:getLoginCountByDeviceType')">
            <el-tag>
                在线总人数：{{totalNum}}
            </el-tag>
            <el-tag>
                WEB：{{pcNum}}
            </el-tag>
            <el-tag>
                安卓：{{androidNum}}
            </el-tag>
            <el-tag>
                IOS：{{iphoneNum}}
            </el-tag>
        </div>-->
    <el-row :gutter="12">
      <el-col :span="8" style="margin-top:20px;">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }"
                 v-if="isAuth('userstatistics:userstatistics:sumInsertUserNumber')">
          <div class="home-card-tit">
						<span>
							会员统计
						</span>
            <span style="display: block;float: right;color: #0080FF;">
							有效用户数:{{validUserNumber}}
						</span>
            <span style="display: block;float: right; padding-right: 10px;color: #0080FF;">
							总用户数：{{userNumber}}
						</span>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="12" class="el-col-css box-card-name">
                <span>新注册会员</span>
              </el-col>
              <el-col :span="12" class="el-col-css box-card-name">
                <span>有效会员</span>
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="12" class="el-col-css">
                今日:{{todayUserNumber}}
              </el-col>
              <el-col :span="12" class="el-col-css">
                今日:{{validTodayUserNumber}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="12" class="el-col-css">
                昨日:{{yesterdayUserNumber}}
              </el-col>
              <el-col :span="12" class="el-col-css">
                昨日:{{validYesterdayUserNumber}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="12" class="el-col-css">
                近七日:{{weekUserNumber}}
              </el-col>
              <el-col :span="12" class="el-col-css">
                近七日:{{validWeekUserNumber}}
              </el-col>
            </el-row>
          </div>
        </el-card>
        <div class="all-counts" v-for="items in playerInfoList" :key="items.id">
        <el-row>
          <el-col :span="24">
            <div class="counts-tit">游戏盈利</div>
          </el-col>
          <el-col :span="12" >
            <el-tag>总盈利:{{items.profitListTotal/100}}</el-tag>
          </el-col>
          <el-col :span="12">
            <el-tag>玩家总数:{{items.playerNumListTotal}}</el-tag>
          </el-col>

        </el-row>
        </div>
      </el-col>
      <el-col :span="16" style="margin-top:20px;">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }"
                 v-if="isAuth('userstatistics:userstatistics:sumInsertUserNumber')">
          <div class="home-card-tit">
						<span>
							充值金额统计
						</span>
            <span style="display: block;float: right;color: #0080FF;">
							<el-tag>累计充值总金额:{{sumAmount}}</el-tag>
						</span>
            <span style="display: block;float: right;color: #0080FF;padding-right: 10px;">
							<el-tag>在线累计存款:{{sumBankAmount}}</el-tag>
						</span>
            <span style="display: block;float: right;color: #0080FF;padding-right: 10px;">
							<el-tag>第三方充累计值金额:{{sumThirdAmount}}</el-tag>
						</span> <span style="display: block;float: right;color: #0080FF;padding-right: 10px;">
							<el-tag>优惠累计金额:{{sumDisAmount}}</el-tag>
						</span>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css box-card-name">
                <span>今日充值数据</span>
              </el-col>
              <el-col :span="8" class="el-col-css box-card-name">
                <span>昨日充值数据</span>
              </el-col>
              <el-col :span="8" class="el-col-css box-card-name">
                <span>近七日充值数据</span>
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css" style="color: #0080FF;">
                总订单：{{sumToday}},成功订单：{{sumTodaySuccess}},总金额：{{sumTodayAmount}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                昨日充值订单:{{sumYesterday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                近七日充值订单:{{sumWeek}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                在线充值：{{sumBankToday}} 成功：{{sumBankTodaySuccess}} 总金额：{{sumBankTodayAmount}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                成功订单:{{sumYesterdaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                成功订单:{{sumWeekSuccess}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                人工充值：{{sumManToday}} 成功：{{sumManTodaySuccess}} 总金额：{{sumManTodayAmount}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                订单总金额:{{sumYesterdayAmount}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                订单总金额:{{sumWeekAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                第三方充值：{{sumThirdToday}} 成功：{{sumThirdTodaySuccess}} 总金额：{{sumThirdTodayAmount}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                昨日优惠总金额:{{sumYesterdayDisAmount}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                近七日优惠总金额:{{sumWeekDisAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                优惠金额累计：{{sumTodayDisAmount}}
              </el-col>

            </el-row>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <el-row :gutter="12">
      <el-col :span="24" style="margin-top:20px;">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }"
                 v-if="isAuth('userstatistics:userstatistics:sumInsertUserNumber')">
          <div class="home-card-tit">
						<span>
							取款订单统计
						</span>
            <span style="display: block;float: right;color: #0080FF;">
							累计取款金额:{{sumTakeMoney}}
						</span>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css box-card-name">
                <span>取款订单笔数</span>
              </el-col>
              <el-col :span="8" class="el-col-css box-card-name">
                <span>成功订单笔数</span>
              </el-col>
              <el-col :span="8" class="el-col-css box-card-name">
                <span>累计取款金额</span>
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                今日: {{sumTakeMoneyToday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                今日: {{sumTakeMoneyTodaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                今日: {{sumTakeMoneyTodayAmount}}
              </el-col>
            </el-row>
          </div>

          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyToday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyTodaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyTodayAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyToday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyTodaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyTodayAmount}}
              </el-col>
            </el-row>
          </div>
          <hr>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                昨日:{{sumTakeMoneyYesterday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                昨日:{{sumTakeMoneyYesterdaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                昨日:{{sumTakeMoneyYesterdayAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyYesterday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyYesterdaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyYesterdayAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyYesterday}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyYesterdaySuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyYesterdayAmount}}
              </el-col>
            </el-row>
          </div>
          <hr>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                近七日:{{sumTakeMoneyWeek}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                近七日:{{sumTakeMoneyWeekSuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                近七日:{{sumTakeMoneyWeekAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyWeek}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyWeekSuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                线上取款: {{sumOnlineTakeMoneyWeekAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyWeek}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyWeekSuccess}}
              </el-col>
              <el-col :span="8" class="el-col-css">
                人工取款: {{sumAdmiTakeMoneyWeekAmount}}
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" v-for="items in playerInfoList">
      <el-col :span="24" style="margin-top:20px;" v-if="isAuth('reportgamedaily:reportgamedaily:sumPlayerProfit')">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }">
          <div class="home-card-tit">
						<span>
							匹配场
						</span>
            <span style="display: block;float: right;color: #0080FF;">总盈利:{{items.profitListRoomMate/100}}</span>
            <span style="display: block;float: right;padding-right: 10px;color: #0080FF;">玩家总数:{{items.playerNumListRoomMate}}</span>
          </div>
          <div class="game_div" v-for="item in dataListRoomMate">
            <div class="box-card-name" style="width: 200px;">
              {{item.gameName}}
            </div>
            <div style="width: 200px;">
              玩家:{{item.playerNum}}
            </div>
            <div style="width: 200px;">
              平台盈利:{{item.profit/100}}
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" v-for="items in playerInfoList">
      <el-col :span="24" style="margin-top:20px;" v-if="isAuth('reportgamedaily:reportgamedaily:sumPlayerProfit')">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }">
          <div class="home-card-tit">
						<span>
							百人场
						</span>
            <span style="display: block;float: right;color: #0080FF;">总盈利:{{items.profitListRoomHundred/100}}</span>
            <span style="display: block;float: right;padding-right: 10px;color: #0080FF;">玩家总数:{{items.playerNumListRoomHundred}}</span>
          </div>
          <div class="game_div" v-for="item in dataListRoomHundred">
            <div class="box-card-name" style="width: 200px;">
              {{item.gameName}}
            </div>
            <div style="width: 200px;">
              玩家:{{item.playerNum}}
            </div>
            <div style="width: 200px;">
              平台盈利:{{item.profit/100}}
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" v-for="items in playerInfoList">
      <el-col :span="24" style="margin-top:20px;" v-if="isAuth('reportgamedaily:reportgamedaily:sumPlayerProfit')">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }">
          <div class="home-card-tit">
						<span>
							房卡场
						</span>
            <span style="display: block;float: right;color: #0080FF;">总盈利:{{items.profitListRoomFaka/100}}</span>
            <span style="display: block;float: right;padding-right: 10px;color: #0080FF;">玩家总数:{{items.playerNumListRoomFaka}}</span>
          </div>
          <div class="game_div" v-for="item in dataListRoomFaka">
            <div class="box-card-name" style="width: 200px;">
              {{item.gameName}}
            </div>
            <div style="width: 200px;">
              玩家:{{item.playerNum}}
            </div>
            <div style="width: 200px;">
              平台盈利:{{item.profit/100}}
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" v-for="items in playerInfoList">
      <el-col :span="24" style="margin-top:20px;" v-if="isAuth('reportgamedaily:reportgamedaily:sumPlayerProfit')">
        <el-card class="box-card" :body-style="{ padding: '8px 0' }">
          <div class="home-card-tit">
						<span>
							拉霸场
						</span>
            <span style="display: block;float: right;color: #0080FF;">总盈利:{{items.profitListRoomLaBa/100}}</span>
            <span style="display: block;float: right;padding-right: 10px;color: #0080FF;">玩家总数:{{items.playerNumListRoomLaBa}}</span>
          </div>
          <div class="game_div" v-for="item in dataListRoomLaBa">
            <div class="box-card-name" style="width: 200px;">
              {{item.gameName}}
            </div>
            <div style="width: 200px;">
              玩家:{{item.playerNum}}
            </div>
            <div style="width: 200px;">
              平台盈利:{{item.profit/100}}
            </div>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <!--
 <el-row :gutter="20">

    <el-col :span="20" style="margin-top:20px;" v-if="isAuth('userstatistics:userstatistics:statisticsRiskNum')">
      <el-card class="box-card" :body-style="{ padding: '8px 0' }">
        <div class="home-card-tit">
          <span>
            风控账号
          </span>
        </div>
        <div class="home-card-item">
          <el-row :gutter="20">
            <el-col :span="12" style="line-height: 40px;margin-left: 10px;">
              风控账号数量:{{riskTotalNum}}
            </el-col>
          </el-row>
        </div>
        <div class="home-card-item" v-for="item in dataRiskNum">
          <el-row :gutter="20">
            <el-col :span="12" style="line-height: 40px;margin-left: 10px;">
              {{item.riskName}}:{{item.count}}
            </el-col>
          </el-row>
        </div>
      </el-card>
    </el-col>
  </el-row>
  -->
    <el-row>
      <!--代理统计-->
      <el-col :span="10" style="margin-top:20px;" v-if=visibleProxy>
        <el-card class="box-card" :body-style="{ padding: '8px 0' }">
          <div class="home-card-tit">
						<span>
							<el-tag><i class="el-icon-tickets"></i>代理账户:{{proxyAccount}}</el-tag>
						</span>
            <span>
							<el-tag><i class="el-icon-tickets"></i>代理账户名称:{{proxyName}}</el-tag>
						</span>
            <span>
							<el-tag><i class="el-icon-tickets"></i>余额:{{proxyBalance/100}}</el-tag>
						</span>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8">
                今日划拨金额: {{sumTransferMoneyToday/100}}
              </el-col>
              <el-col :span="8">
                今日划拨次数: {{sumTransferMoneyTodayAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8">
                昨日划拨金额:{{sumTransferMoneyYesterday/100}}
              </el-col>
              <el-col :span="8">
                昨日划拨次数:{{sumTransferMoneyYesterdayAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8">
                近七日划拨金额:{{sumTransferMoneyWeek/100}}
              </el-col>
              <el-col :span="8">
                近七日划拨次数:{{sumTransferMoneyWeekAmount}}
              </el-col>
            </el-row>
          </div>
          <div class="home-card-item">
            <el-row :gutter="20">
              <el-col :span="8">
                总划拨金额: {{sumTransferMoney/100}}
              </el-col>
              <el-col :span="8">
                总划拨次数: {{sumTransferMoneyAmount}}
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import moment from 'moment'
  import {
    isAuth
  } from '@/utils'

  export default {
    data () {
      return {
        userNumber: 0,
        validUserNumber: 0,
        todayUserNumber: 0,
        validTodayUserNumber: 0,
        yesterdayUserNumber: 0,
        validYesterdayUserNumber: 0,
        weekUserNumber: 0,
        validWeekUserNumber: 0,
        sumTodayDisAmount: 0,
        sumWeekDisAmount: 0,
        sumYesterdayDisAmount: 0,
        sumAmount: 0,
        sumDisAmount: 0,
        sumThirdAmount: 0,
        sumBankAmount: 0,
        sumBankToday: 0,
        sumBankTodaySuccess: 0,
        sumBankTodayAmount: 0,
        sumManToday: 0,
        sumManTodaySuccess: 0,
        sumManTodayAmount: 0,
        sumThirdToday: 0,
        sumThirdTodaySuccess: 0,
        sumThirdTodayAmount: 0,
        sumTodayAmount: 0,
        sumToday: 0,
        sumTodaySuccess: 0,
        sumYesterdayAmount: 0,
        sumYesterday: 0,
        sumYesterdaySuccess: 0,
        sumWeekAmount: 0,
        sumWeek: 0,
        sumWeekSuccess: 0,

        sumOnlineTakeMoneyToday: 0,
        sumOnlineTakeMoneyTodaySuccess: 0,
        sumOnlineTakeMoneyTodayAmount: 0,
        sumAdmiTakeMoneyToday: 0,
        sumAdmiTakeMoneyTodaySuccess: 0,
        sumAdmiTakeMoneyTodayAmount: 0,
        sumOnlineTakeMoneyWeek: 0,
        sumOnlineTakeMoneyWeekSuccess: 0,
        sumOnlineTakeMoneyWeekAmount: 0,
        sumAdmiTakeMoneyWeek: 0,
        sumAdmiTakeMoneyWeekSuccess: 0,
        sumAdmiTakeMoneyWeekAmount: 0,
        sumOnlineTakeMoneyYesterday: 0,
        sumOnlineTakeMoneyYesterdaySuccess: 0,
        sumOnlineTakeMoneyYesterdayAmount: 0,
        sumAdmiTakeMoneyYesterday: 0,
        sumAdmiTakeMoneyYesterdaySuccess: 0,
        sumAdmiTakeMoneyYesterdayAmount: 0,

        sumTakeMoneyToday: 0,
        sumTakeMoneyTodaySuccess: 0,
        sumTakeMoneyTodayAmount: 0,
        sumTakeMoneyYesterday: 0,
        sumTakeMoneyYesterdaySuccess: 0,
        sumTakeMoneyYesterdayAmount: 0,
        sumTakeMoneyWeek: 0,
        sumTakeMoneyWeekSuccess: 0,
        sumTakeMoneyWeekAmount: 0,
        sumTakeMoney: 0,

        pcNum: 0,
        androidNum: 0,
        iphoneNum: 0,
        totalNum: 0,

        dataRiskNum: [],
        riskTotalNum: 0,

        dataListRoomMate: [],
        dataListRoomHundred: [],
        dataListRoomLaBa: [],
        playerInfoList: [],
        dataListRoomFaka: [],
        visibleProxy: false,
        proxyAccount: '',
        proxyName: '',
        proxyBalance: 0,
        sumTransferMoneyToday: 0,
        sumTransferMoneyTodayAmount: 0,
        sumTransferMoneyYesterday: 0,
        sumTransferMoneyYesterdayAmount: 0,
        sumTransferMoneyWeek: 0,
        sumTransferMoneyWeekAmount: 0,
        sumTransferMoney: 0,
        sumTransferMoneyAmount: 0
      }
    },
    activated () {
      /* if (isAuth('userstatistics:userstatistics:getLoginCountByDeviceType')) {
                this.getLoginCountByDeviceType() //获取在线人数
            } */
      if (isAuth('userstatistics:userstatistics:sumInsertUserNumber')) {
        this.getUserNumber() // 获取用户数量
      }
      if (isAuth('rechargestatistics:rechargestatistics:sumRechargeAmount')) {
        this.getRechargeAmount() // 获取充值统计
      }
      if (isAuth('takemoneystatistics:takemoneystatistics:sumTakeMoney')) {
        this.getTakeMoneyAmount() // 获取取款统计
      }
      if (isAuth('userstatistics:userstatistics:statisticsRiskNum')) {
        this.getRiskUserNum() // 获取风控人数
      }
      if (isAuth('reportgamedaily:reportgamedaily:sumPlayerProfit')) {
        this.getPlayerProfit() // 获取每日游戏盈利
      }
      this.getProxy()
    },
    methods: {
      /* //获取在线人数
            getLoginCountByDeviceType() {
                this.$http({
                    url: this.$http.adornUrl('/userstatistics/userstatistics/getLoginCountByDeviceType'),
                    method: 'get',
                    params: this.$http.adornParams({})
                }).then(({
                    data
                }) => {
                    if (data && data.code === 200) {
                        this.pcNum = data.data.pcNum;
                        this.androidNum = data.data.androidNum;
                        this.iphoneNum = data.data.iphoneNum;
                        this.totalNum = data.data.pcNum + data.data.androidNum + data.data.iphoneNum
                    } else {
                        this.$message.error(data.msg)
                    }

                })
            }, */
      // 获取用户数量
      getUserNumber () {
        this.sumInsertUserNumber() // 获取注册用户总人数
        this.todayInsertUserNumber() // 获取今日注册用户总人数
        this.yesterdayInsertUserNumber() // 获取昨日总人数
        this.weekInsertUserNumber() // 获取近一周总人数
      },
      // 获取充值信息
      getRechargeAmount () {
        this.sumRechargeAmount() // 获取充值总额
        this.sumThirdRechargeAmount() // 获取第三方充值金额
        this.sumBankRechargeAmount() // 银行卡充值金额
        this.sumBankTodayRechargeAmount() // 今日线下银行充值详情
        this.sumManTodayRechargeAmount() // 今日人工充值详情
        this.sumThirdTodayRechargeAmount() // 今日第三方充值详情
        this.sumTodayRechargeAmount() // 今日充值详情
        this.sumYesterdayRechargeAmount() // 昨日充值详情
        this.sumWeekRechargeAmount() // 七日充值详情
      },
      // 获取取款信息
      getTakeMoneyAmount () {
        this.sumTodayTakeMoneyAmount() // 今日取款详情
        this.sumYesterdayTakeMoneyAmount() // 昨日取款详情
        this.sumWeekTakeMoneyAmount() // 七日取款详情
        this.sumTakeMoneyAmount() // 获取取款总额
      },
      // 获取风控人数
      getRiskUserNum () {
        this.getStatisticsRiskNum()// 用户风控层级统计
        this.getStatisticsRiskTotalNum()// 用户风控总数统计
      },
      getStatisticsRiskNum () { // 用户风控层级统计
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/statisticsRiskNum'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.dataRiskNum = data.list
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      getStatisticsRiskTotalNum () { // 用户风控总数统计
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/statisticsRiskTotalNum'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.riskTotalNum = data.data
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 获取每日游戏盈利
      getPlayerProfit () {
        this.$http({
          url: this.$http.adornUrl('/reportgamedaily/reportgamedaily/sumPlayerProfit'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            console.log(data)
            this.dataListRoomMate = data.list[0]
            this.dataListRoomHundred = data.list[1]
            this.dataListRoomLaBa = data.list[2]
            this.dataListRoomFaka = data.list[3]
            this.playerInfoList = data.list[4]
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      getProxy () {
        this.proxyLoginInfo()// 获取代理账号信息
        this.sumTodayTransferMoneyAmount() // 今日划拨详情
        this.sumYesterdayTransferMoneyAmount() // 昨日划拨详情
        this.sumWeekTransferMoneyAmount() // 七日划拨详情
        this.sumTotalTransferMoneyAmount() // 获取划拨总额
      },
      proxyLoginInfo () { // 获取代理账号信息
        this.$http({
          url: this.$http.adornUrl('/sys/user/proxylogininfo'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.proxyAccount = data.map.proxyAccount
            this.proxyName = data.map.proxyName
            if (data.map.proxyBalance != null) {
              this.proxyBalance = data.map.proxyBalance
              this.visibleProxy = true
            }
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      sumTodayTransferMoneyAmount () { // 今日划拨详情
        this.$http({
          url: this.$http.adornUrl('/sys/user/sumTodayRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            if (data.map.transferCoin != null) {
              this.sumTransferMoneyToday = data.map.transferCoin
            }
            this.sumTransferMoneyTodayAmount = data.map.stransferNum
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      sumYesterdayTransferMoneyAmount () { // 昨日划拨详情
        this.$http({
          url: this.$http.adornUrl('/sys/user/sumYesterdayTransferMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            if (data.map.transferCoin != null) {
              this.sumTransferMoneyYesterday = data.map.transferCoin
            }
            this.sumTransferMoneyYesterdayAmount = data.map.stransferNum
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      sumWeekTransferMoneyAmount () { // 七日划拨详情
        this.$http({
          url: this.$http.adornUrl('/sys/user/sumWeekTransferMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            if (data.map.transferCoin != null) {
              this.sumTransferMoneyWeek = data.map.transferCoin
            }
            this.sumTransferMoneyWeekAmount = data.map.stransferNum
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      sumTotalTransferMoneyAmount () { // 获取划拨总额
        this.$http({
          url: this.$http.adornUrl('/sys/user/sumTotalTransferMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            if (data.map.transferCoin != null) {
              this.sumTransferMoney = data.map.transferCoin
            }
            this.sumTransferMoneyAmount = data.map.stransferNum
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 今日取款详情
      sumTodayTakeMoneyAmount () {
        this.$http({
          url: this.$http.adornUrl('/takemoneystatistics/takemoneystatistics/sumTodayTakeMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumTakeMoneyToday = data.map.orderNum
            this.sumTakeMoneyTodaySuccess = data.map.successOrderNum
            this.sumTakeMoneyTodayAmount = data.map.takeAmount
            this.sumOnlineTakeMoneyToday = data.map.orderNumOnline
            this.sumOnlineTakeMoneyTodaySuccess = data.map.successOrderNumOnline
            this.sumOnlineTakeMoneyTodayAmount = data.map.takeAmountOnline
            this.sumAdmiTakeMoneyToday = data.map.orderNumAdmi
            this.sumAdmiTakeMoneyTodaySuccess = data.map.successOrderNumAdmi
            this.sumAdmiTakeMoneyTodayAmount = data.map.takeAmountAdmi
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 昨日取款详情
      sumYesterdayTakeMoneyAmount () {
        this.$http({
          url: this.$http.adornUrl('/takemoneystatistics/takemoneystatistics/sumYesterdayTakeMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumTakeMoneyYesterday = data.map.orderNum
            this.sumTakeMoneyYesterdaySuccess = data.map.successOrderNum
            this.sumTakeMoneyYesterdayAmount = data.map.takeAmount
            this.sumOnlineTakeMoneyYesterday = data.map.orderNumOnline
            this.sumOnlineTakeMoneyYesterdaySuccess = data.map.successOrderNumOnline
            this.sumOnlineTakeMoneyYesterdayAmount = data.map.takeAmountOnline
            this.sumAdmiTakeMoneyYesterday = data.map.orderNumAdmi
            this.sumAdmiTakeMoneyYesterdaySuccess = data.map.successOrderNumAdmi
            this.sumAdmiTakeMoneyYesterdayAmount = data.map.takeAmountAdmi
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 七日取款详情
      sumWeekTakeMoneyAmount () {
        this.$http({
          url: this.$http.adornUrl('/takemoneystatistics/takemoneystatistics/sumWeekTakeMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumTakeMoneyWeek = data.map.orderNum
            this.sumTakeMoneyWeekSuccess = data.map.successOrderNum
            this.sumTakeMoneyWeekAmount = data.map.takeAmount
            this.sumOnlineTakeMoneyWeek = data.map.orderNumOnline
            this.sumOnlineTakeMoneyWeekSuccess = data.map.successOrderNumOnline
            this.sumOnlineTakeMoneyWeekAmount = data.map.takeAmountOnline
            this.sumAdmiTakeMoneyWeek = data.map.orderNumAdmi
            this.sumAdmiTakeMoneyWeekSuccess = data.map.successOrderNumAdmi
            this.sumAdmiTakeMoneyWeekAmount = data.map.takeAmountAdmi
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 获取取款总额
      sumTakeMoneyAmount () {
        this.$http({
          url: this.$http.adornUrl('/takemoneystatistics/takemoneystatistics/sumTakeMoneyAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            // this.sumTakeMoney = data.map.orderNum//总订单数    	暂时不用
            // this.sumTakeMoneySuccess = data.map.successOrderNum//总订单成功数   	暂时不用
            this.sumTakeMoney = data.map.takeAmount
          } else {
            this.$message.error(data.msg)
          }
        })
      },

      // 七日充值详情
      sumWeekRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumWeekRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumWeek = data.data.num
            this.sumWeekSuccess = data.data.successAmount
            this.sumWeekAmount = data.data.amount
            this.sumWeekDisAmount = data.data.disAmount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 昨日充值详情
      sumYesterdayRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumYesterdayRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumYesterday = data.data.num
            this.sumYesterdaySuccess = data.data.successAmount
            this.sumYesterdayAmount = data.data.amount
            this.sumYesterdayDisAmount = data.data.disAmount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 今日线下银行充值详情
      sumBankTodayRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumBankTodayRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumBankToday = data.data.num
            this.sumBankTodaySuccess = data.data.successAmount
            this.sumBankTodayAmount = data.data.amount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 今日人工充值详情
      sumManTodayRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumManTodayRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumManToday = data.data.num
            this.sumManTodaySuccess = data.data.successAmount
            this.sumManTodayAmount = data.data.amount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 今日第三方充值详情
      sumThirdTodayRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumThirdTodayRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumThirdToday = data.data.num
            this.sumThirdTodaySuccess = data.data.successAmount
            this.sumThirdTodayAmount = data.data.amount
            this.sumTodayDisAmount = data.data.disAmount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 今日充值详情
      sumTodayRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumTodayRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumToday = data.data.num
            this.sumTodaySuccess = data.data.successAmount
            this.sumTodayAmount = data.data.amount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 银行卡充值金额
      sumBankRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumBankRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumBankAmount = data.data.amount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 第三方充值金额
      sumThirdRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumThirdRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumThirdAmount = data.data.amount
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 总充值金额
      sumRechargeAmount () {
        this.$http({
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/sumRechargeAmount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.sumAmount = data.data.amount
            this.sumDisAmount = data.data.disAmount
          } else {
            this.$message.error(data.msg)
          }
        })
      },

      // 获取注册用户总人数
      sumInsertUserNumber () {
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/sumInsertUserNumber'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.userNumber = data.data.userNumber
            this.validUserNumber = data.data.validUserNumber
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 获取今日注册用户总人数
      todayInsertUserNumber () {
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/todayInsertUserNumber'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.todayUserNumber = data.data.userNumber
            this.validTodayUserNumber = data.data.validUserNumber
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 获取昨日总人数
      yesterdayInsertUserNumber () {
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/yesterdayInsertUserNumber'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.yesterdayUserNumber = data.data.userNumber
            this.validYesterdayUserNumber = data.data.validUserNumber
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 获取近一周总人数
      weekInsertUserNumber () {
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/weekInsertUserNumber'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.weekUserNumber = data.data.userNumber
            this.validWeekUserNumber = data.data.validUserNumber
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      // 查看在线充值订单详情  跳转
      orderBankRecharge () {
        const end = new Date()
        const start = new Date()
        this.$router.push({
          path: 'orderrecharge-orderbankrecharge',
          query: {
            queryTime: [moment(start).format('YYYY-MM-DD 00:00:00'), moment(end).format('YYYY-MM-DD 23:59:59')]
          }
        })
      },
      // 查看人工充值订单详情  跳转
      orderManRecharge () {
        const end = new Date()
        const start = new Date()
        start.setTime(start.getTime())
        this.$router.push({
          path: 'orderadministrator-orderadministrator',
          query: {
            queryTime: [moment(start).format('YYYY-MM-DD 00:00:00'), moment(end).format('YYYY-MM-DD 23:59:59')],
            operationType: 0
          }
        })
      },
      // 查看第三方充值订单详情  跳转
      orderThirdRecharge () {
        const end = new Date()
        const start = new Date()
        start.setTime(start.getTime())
        this.$router.push({
          path: 'orderrecharge-orderthirdrecharge',
          query: {
            queryTime: [moment(start).format('YYYY-MM-DD 00:00:00'), moment(end).format('YYYY-MM-DD 23:59:59')]
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .mod-home {
    line-height: 1.5;

    .mod-home-all {
      margin-bottom: 10px;
    }

    .home-card-tit {
      padding: 8px 15px;
      border-bottom: 1px solid #ebeef5;
    }

    .home-card-items {
      padding-left: 15px;
      padding-top: 10px;
    }

    .el-col-css {
      border-right: 1px solid #ebeef5;
      line-height: 40px;
      text-align: center;
    }

    .el-col-css span {
      color: black;
      font-size: 18px;
    }
    hr{
      border: none;
      background-color: #ebeef5;
      height: 1px;
    }
    .game_div {
      float: left;
      border-right: 1px solid #ebeef5;
      text-align: center;
      line-height: 50px;
    }

    .game_div:last-of-type {
      border-right: none;
    }
    .all-counts{
      text-align: center;
      .el-col-12{
        line-height: 40px;
      }
    }
    .counts-tit{
      font-size: 18px;
      font-weight: 600;
      line-height: 45px;
    }
    .box-card-name {
      font-size: 18px;
      font-weight: bold;
      padding-bottom: 10px;
      padding-top: 10px;
    }
  }
</style>
