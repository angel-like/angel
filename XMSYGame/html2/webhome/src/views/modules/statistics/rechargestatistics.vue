<template>
  <div class="dashboard-editor-container">
    <el-table
      :data="tableData"
      style="width: 100%;font-size: 22px">
      <el-table-column
        prop="rechargeAmount"
        label="充值总额"
        align="center"
        header-align="center"
      >
      </el-table-column>
      <el-table-column
        prop="adminAmount"
        label="人工充值"
        header-align="center"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="thirdAmount"
        header-align="center"
        align="center"
        label="第三方存款">
      </el-table-column>
      <el-table-column
        prop="bankAmount"
        header-align="center"
        align="center"
        label="线下存款"
      >
      </el-table-column>
      <el-table-column
        prop="discountAmount"
        header-align="center"
        align="center"
        label="优惠">
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
  import PanelGroup from './component1/PanelGroup'
  import PieChart from './component1/PieChart'
  import moment from 'moment';
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
        data: {
          rechargeAmount: '',
          adminAmount: '',
          thirdAmount: '',
          bankAmount: '',
          discountAmount: ''
        },
        tableData: [],
        seriesData3: [],
        pickerOptions2: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(end.getTime() - 3600 * 1000 * 24);
              start.setTime(start.getTime() - 3600 * 1000 * 24);
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
        lineChartData: {},
        objAmount: null,
        unconfirmedAmount: 0,
        adminAnconfirmedAmount: 0,
        thirdAnconfirmedAmount: 0,
        bankAnconfirmedAmount: 0,
        discountAnconfirmedAmount: 0,

        cancelAmount: 0,
        adminCancelAmount: 0,
        thirdCancelAmount: 0,
        bankCancelAmount: 0,
        discountCancelAmount: 0,

        confirmAmount: 0,
        adminConfirmAmount: 0,
        thirdConfirmAmount: 0,
        bankConfirmAmount: 0,
        discountConfirmAmount: 0,

        discountAmount: 0,
        adminDiscountAmount: 0,
        thirdDiscountAmount: 0,
        bankDiscountAmount: 0,
        time:[new Date(new Date(new Date().toLocaleDateString()).getTime()- 3600 * 1000 * 24 * 30), new Date()]
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
          url: this.$http.adornUrl('/rechargestatistics/rechargestatistics/list'),
          method: 'get',
          params: this.$http.adornParams({
            'startTime': startTime,
            'endTime': endTime
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.objAmount = data.rechargeReport

            this.data.rechargeAmount = data.rechargeReport.confirmAmount
            this.data.adminAmount = data.rechargeReport.adminConfirmAmount
            this.data.thirdAmount = data.rechargeReport.thirdConfirmAmount
            this.data.bankAmount = data.rechargeReport.bankConfirmAmount
            this.data.discountAmount = data.rechargeReport.discountConfirmAmount
            this.tableData = [this.data]

            this.unconfirmedAmount = data.rechargeReport.unconfirmedAmount
            this.adminAnconfirmedAmount = data.rechargeReport.adminAnconfirmedAmount
            this.thirdAnconfirmedAmount = data.rechargeReport.thirdAnconfirmedAmount
            this.bankAnconfirmedAmount = data.rechargeReport.bankAnconfirmedAmount
            this.discountAnconfirmedAmount = data.rechargeReport.discountAnconfirmedAmount

            this.cancelAmount = data.rechargeReport.cancelAmount
            this.adminCancelAmount = data.rechargeReport.adminCancelAmount
            this.thirdCancelAmount = data.rechargeReport.thirdCancelAmount
            this.bankCancelAmount = data.rechargeReport.bankCancelAmount
            this.discountCancelAmount = data.rechargeReport.discountCancelAmount

            this.confirmAmount = data.rechargeReport.confirmAmount
            this.adminConfirmAmount = data.rechargeReport.adminConfirmAmount
            this.thirdConfirmAmount = data.rechargeReport.thirdConfirmAmount
            this.bankConfirmAmount = data.rechargeReport.bankConfirmAmount
            this.discountConfirmAmount = data.rechargeReport.discountConfirmAmount

            this.adminDiscountAmount = data.rechargeReport.adminDiscountAmount
            this.thirdDiscountAmount = data.rechargeReport.thirdDiscountAmount
            this.bankDiscountAmount = data.rechargeReport.bankDiscountAmount
            this.seriesData3 = [this.adminConfirmAmount, this.thirdConfirmAmount, this.bankConfirmAmount, this.discountConfirmAmount]
            this.handleSetLineChartData();
          }
        })
      },
      handleSetLineChartData() {


        this.lineChartData = {
          maxNumber: 12000,
          xAxisData: ['人工存款', '第三方存款', '线下存款', '优惠'],
          legendData: [ '已入款'],
          seriesData: [

            {

              name: '已入款',
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

