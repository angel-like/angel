<template>

  <div class="dashboard-editor-container">
    <div class="mod-config">
      <el-container>
        <el-aside width="800px">
          <el-row :gutter="32">
            <el-col :xs="24" :sm="24" :lg="23">
              <div class="chart-wrapper">
                <pie-chart :chart-data="pieChartData"/>
              </div>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <div class="card-panel-text"><font size="5"><strong>累计会员人数:{{userNumber}}</strong></font></div>
          <br/>
          <div class="card-panel-text"><font size="5"><strong>会员有效人数:{{validUserNumber}}</strong></font></div>
          <br/>
          <div class="card-panel-text"><font size="5"><strong>PC端在线人数:{{pcNum}}</strong></font></div>
          <br/>
          <div class="card-panel-text"><font size="5"><strong>安卓端在线人数:{{androidNum}}</strong></font></div>
          <br/>
          <div class="card-panel-text"><font size="5"><strong>苹果端在线人数:{{iphoneNum}}</strong></font></div>
          <br/>
          <div class="card-panel-text"><font size="5"><strong>其他在线人数:{{otherNum}}</strong></font></div>
        </el-main>
      </el-container>

    </div>
    <br/><br/><br/>
    <div class="mod-config">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
                          range-separator="至"
                          start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="24">
        <div class="chart-wrapper">
          <line-chart :chart-data="lineChartData"/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  // 曲线图
  import PanelGroup from './component4/PanelGroup'
  import LineChart from './component4/LineChart'
  // 饼状图
  import PanelGroup2 from './component1/PanelGroup'
  import PieChart from './component4/PieChart'

  import dateutil from '@/utils/datechonse'
  import moment from 'moment';

  const lineChartData = {
    newVisitis: {
      title: '1',
      xaxisData: [],
      yaxislData: []
    }
  }
  const pieChartData = {
    newVisitis: {
      title: '终端在线分布',
      expectedData: ['Android', 'iphone', 'pc', '其他'],
      actualData: [{
        value: 0,
        name: 'Android'
      },
        {
          value: 0,
          name: 'iphone'
        },
        {
          value: 0,
          name: 'pc'
        },
        {
          value: 0,
          name: '其他'
        }
      ]
    }
  }
  export default {
    name: 'DashboardAdmin',
    components: {
      PanelGroup,
      LineChart,
      PanelGroup2,
      PieChart
    },
    data() {
      return {
        //饼状图参数、
        objAmount: null,
        pieChartData: pieChartData.newVisitis,

        //会员统计参数
        userNumber: 0,
        validUserNumber: 0,
        pcNum: 0,
        androidNum: 0,
        iphoneNum: 0,
        otherNum: 0,

        //曲线图参数
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
            text: '最近7天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近30天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
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
          }
            /*,{
                                                text: '过去二月',
                                                onClick(picker) {
                                                    const end = new Date();
                                                    const start = new Date();
                                                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
                                                    picker.$emit('pick', [start, end]);
                                                }
                                            },{
                                                text: '过去三月',
                                                onClick(picker) {
                                                    const end = new Date();
                                                    const start = new Date();
                                                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                                                    picker.$emit('pick', [start, end]);
                                                }
                                            }*/
          ]
        },
        lineChartData: lineChartData.newVisitis,
        dataForm: {
          queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime() - 60 * 60 * 1000 * 24 * 7), new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1)],
        }


      }
    },
    mounted() {
      this.getDataList();
    },
    methods: {
      getDataList() {
        var startTime = "";
        var endTime = "";
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startTime = moment(timeArr[0]).format("YYYY-MM-DD");
          if (timeArr.length > 1) {
            endTime = moment(timeArr[1]).format("YYYY-MM-DD");
          }
        }
        this.$http({
          url: this.$http.adornUrl('/userstatistics/userstatistics/UserNumberAnalysis'),
          method: 'get',
          params: this.$http.adornParams({
            'startTime': startTime,
            'endTime': endTime
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.lineChartData = data.data
            this.userNumber = data.map.userNumber
            this.validUserNumber = data.map.validUserNumber
            this.pcNum = data.map.pcNum
            this.androidNum = data.map.androidNum
            this.iphoneNum = data.map.iphoneNum
            this.otherNum = data.map.otherNum
            this.pieChartData = {
              title: '终端在线分布',
              expectedData: ['Android', 'iphone', 'pc', '其他'],
              actualData: [{
                value: this.androidNum,
                name: 'Android'
              },
                {
                  value: this.iphoneNum,
                  name: 'iphone'
                },
                {
                  value: this.pcNum,
                  name: 'pc'
                },
                {
                  value: this.otherNum,
                  name: '其他'
                }

              ]
            }
          } else {
            this.lineChartData = {}
            this.$message.error(data.msg)
          }
        })
      },
      handleSetPerChartData() {
        this.pieChartData = {
          title: '终端在线分布',
          expectedData: ['Android', 'iphone', '其他'],
          actualData: [{
            value: 1,
            name: 'Android'
          },
            {
              value: 2,
              name: 'iphone'
            },
            {
              value: 3,
              name: '其他'
            }
          ]
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      border-radius: 15px;
    }

  }
</style>
