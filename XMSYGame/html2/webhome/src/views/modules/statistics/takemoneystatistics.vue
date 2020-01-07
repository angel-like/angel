<template>

  <div class="dashboard-editor-container">
    <el-table
      :data="tableData"
      style="width: 100%;font-size: 22px">
      <el-table-column
        prop="takeAmount"
        label="取款总额"
        align="center"
        header-align="center"
      >
      </el-table-column>
      <el-table-column
        prop="moneyAmount"
        label="余额取款总额"
        header-align="center"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="commissionAmount"
        header-align="center"
        align="center"
        label="佣金取款总额">
      </el-table-column>
      <el-table-column
        prop="poundage"
        header-align="center"
        align="center"
        label="行政费"
      >
      </el-table-column>
    </el-table>
    <br>
    <div style="margin-bottom: 15px;">
      <el-date-picker style="width: auto"
                      v-model="time"
                      type="daterange"
                      range-separator="至"
                      :picker-options="pickerOptions2"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期">
      </el-date-picker>
      <el-button @click="getDataList()">查询</el-button>
    </div>

    <el-row :gutter="32">
      <!--<el-col :xs="24" :sm="24" :lg="6">
                <div style="padding-right:25px">
                    <panel-group :objAmount="objAmount" :objGroup="objGroup" @handleSetLineChartData="handleSetLineChartData" />
                </div>
            </el-col>-->
      <el-col :xs="24" :sm="24" :lg="24">
        <div class="chart-wrapper">
          <bar-chart :chart-data="lineChartData"/>
        </div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
  // 饼状图
  import PanelGroup from './component2/PanelGroup'
  import PieChart from './component1/PieChart'
  import moment from 'moment'
  import BarChart from './component1/BarChart2'
  import dateutil from '@/utils/datechonse'
  const lineChartData = {}
  export default {
    name: 'DashboardAdmin',
    components: {
      PanelGroup,
      PieChart,
      BarChart
    },
    data() {
      return {
        pickerOptions2: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              const end = dateutil.getToday().endtime;
              const start = dateutil.getToday().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const end = dateutil.getYesterday().endtime;
              const start = dateutil.getYesterday().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本周',
            onClick(picker) {
              const end = dateutil.getCurrWeekDays().endtime;
              const start = dateutil.getCurrWeekDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '上周',
            onClick(picker) {
              const end = dateutil.getLastWeekDays().endtime;
              const start = dateutil.getLastWeekDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本月',
            onClick(picker) {
              const end = dateutil.getCurrMonthDays().endtime;
              const start = dateutil.getCurrMonthDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '上月',
            onClick(picker) {
              const end = dateutil.getLastMonthDays().endtime;
              const start = dateutil.getLastMonthDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去7天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去30天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去二月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去三月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },

        seriesData3: [],
        lineChartData: {},
        objAmount: null,
        objGroup: {
          "title1": "取款总额",
          "title2": "余额取款总额",
          "title3": "佣金取款总额",
          "title4": "行政费",
          "title5": "实际出款",
          "svg1": "zongjukuan",
          "svg2": "yue",
          "svg3": "yongjin",
          "svg4": "xingzheng",
          "svg5": "shijichukuan"

        },
        data: {
          takeAmount: '',
          moneyAmount: '',
          commissionAmount: '',
          poundage: '',
          moneyObtainAmount: ''
        },
        tableData: [],
        takeAmount: '',
        moneyAmount:
          '',
        moneyWaitAmount:
          '',
        moneyCancelAmount:
          '',
        moneySuccessAmount:
          '',


        commissionAmount:
          '',
        commissionWaitAmount:
          '',
        commissionCancelAmount:
          '',
        commissionSuccessAmount:
          '',

        poundage:
          '',
        moneyWaitPoundage:
          '',
        moneyCancelPoundage:
          '',
        moneySuccessPoundage:
          '',


        moneyObtainAmount:
          '',
        moneyWaitObtainAmount:
          '',
        moneyCancelObtainAmount:
          '',
        moneySuccessObtainAmount:
          '',
        time: [new Date(new Date(new Date().toLocaleDateString()).getTime()- 3600 * 1000 * 24 * 30), new Date()]
      }
    },
    activated() {
      this.getDataList()
    },

    methods: {
      // 获取数据列表
      getDataList() {
        var startTime = "";
        var endTime = "";
        var timeArr = this.time;
        if (timeArr != null && timeArr.length > 0) {
          startTime = moment(timeArr[0]).format("YYYY-MM-DD");
          if (timeArr.length > 1) {
            endTime = moment(timeArr[1]).format("YYYY-MM-DD");
          }
        }
        this.$http({
          url: this.$http.adornUrl('/takemoneystatistics/takemoneystatistics/list'),
          method: 'get',
          params: this.$http.adornParams({
            'startTime': startTime,
            'endTime': endTime
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.objAmount = data.takeMoneyReport;

            this.takeAmount = data.takeMoneyReport.takeAmount
            this.data.takeAmount =(data.takeMoneyReport.moneySuccessAmount)+(data.takeMoneyReport.commissionSuccessAmount)
            this.data.moneyAmount = data.takeMoneyReport.moneySuccessAmount
            this.data.commissionAmount = data.takeMoneyReport.commissionSuccessAmount
            this.data.poundage = data.takeMoneyReport.moneySuccessPoundage
            this.data.moneyObtainAmount = data.takeMoneyReport.moneySuccessObtainAmount


            this.tableData = [this.data]
            this.moneyAmount = data.takeMoneyReport.moneyAmount
            this.moneyWaitAmount = data.takeMoneyReport.moneyWaitAmount
            this.moneyCancelAmount = data.takeMoneyReport.moneyCancelAmount
            this.moneySuccessAmount = data.takeMoneyReport.moneySuccessAmount

            this.commissionAmount = data.takeMoneyReport.commissionAmount
            this.commissionWaitAmount = data.takeMoneyReport.commissionWaitAmount
            this.commissionCancelAmount = data.takeMoneyReport.commissionCancelAmount
            this.commissionSuccessAmount = data.takeMoneyReport.commissionSuccessAmount

            this.poundage = data.takeMoneyReport.poundage
            this.moneyWaitPoundage = data.takeMoneyReport.moneyWaitPoundage
            this.moneyCancelPoundage = data.takeMoneyReport.moneyCancelPoundage
            this.moneySuccessPoundage = data.takeMoneyReport.moneySuccessPoundage

            this.moneyObtainAmount = data.takeMoneyReport.moneyObtainAmount
            this.moneyWaitObtainAmount = data.takeMoneyReport.moneyWaitObtainAmount
            this.moneyCancelObtainAmount = data.takeMoneyReport.moneyCancelObtainAmount
            this.moneySuccessObtainAmount = data.takeMoneyReport.moneySuccessObtainAmount

            this.seriesData3 = [this.moneySuccessAmount, this.commissionSuccessAmount, this.moneySuccessPoundage]
            this.handleSetLineChartData();
          }
        })
      },
      handleSetLineChartData() {
        this.lineChartData = {
          maxNumber: 10000,
          xAxisData: ['余额取款', '佣金取款', '行政费'],
          legendData: [ '已出款'],
          seriesData: [

            {
              name: '已出款',
              type: 'bar',
              barWidth : 50,
              data: this.seriesData3,
              itemStyle: {
                normal: {
                  //这里是重点
                  color: '#61a0a8'
                }
              }
            }]
        }
      }
    }

  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard-editor-container {
    padding: 10px;
    background-color: rgb(240, 242, 245);

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      border-radius: 15px;
    }

  }

  .el-date-editor .el-range-separator {
    padding: 0 0px;
  }
</style>


