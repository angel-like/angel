<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.orderNo" placeholder="订单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.merchantOrderNo" placeholder="第三方订单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="充值账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" placeholder="订单状态" clearable>
          <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.rechargePlatform" placeholder="充值平台" clearable>
          <el-option v-for="item in payOptions" :key="item.id" :label="item.name" :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.rechargeChannel" placeholder="充值渠道" clearable>
          <el-option v-for="item in channelOptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.amountMin" placeholder="最小范围" style="width: 100px;" clearable></el-input>
        ~
        <el-input v-model="dataForm.amountMax" placeholder="最大范围" style="width: 100px;" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2"
                        value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button @click="exportCSV()">下载Excel</el-button>
        <el-button @click="getDataList()">刷新</el-button>
        <el-button v-if="isAuth('orderrecharge:orderrecharge:save')" type="primary" @click="addOrUpdateHandle()">新增
        </el-button>
        <el-button v-if="isAuth('orderrecharge:orderrecharge:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
      </el-form-item>
      <el-form-item>
        <span style="color: red;"> 昨日充值总额:{{yesterdayAmount}}</span>
        &nbsp;&nbsp;
        <span style="color: red;"> 今日充值总额:{{todayAmount}}</span>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column
        type="index"
        width="120"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>
      <el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="存款时间">
      </el-table-column>
      <el-table-column prop="rechargeTime" header-align="center" align="center" label="完成时间">
      </el-table-column>
      <el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作人">
      </el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="金额">
      </el-table-column>

      <el-table-column prop="status" header-align="center" align="center" label="订单状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
          <el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
          <el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="userAccount" header-align="center" align="center" label="支付人账号">
      </el-table-column>
      <el-table-column prop="fristrecharge" header-align="center" align="center" label="是否首充">
        <template slot-scope="scope">
          <div v-if="scope.row.fristrecharge==false">
            否
          </div>
          <div v-if="scope.row.fristrecharge==true">
            是
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="merchantOrderNo" header-align="center" align="center" label="第三方支付平台订单号">
      </el-table-column>
      <el-table-column prop="rechargePlatform" header-align="center" align="center" label="第三方支付平台">
      </el-table-column>
      <el-table-column prop="rechargeTerminal" header-align="center" align="center" label="充值终端WEB,PC">
      </el-table-column>
      <el-table-column prop="preMoney" header-align="center" align="center" label="充值前主账户金额">
      </el-table-column>
      <el-table-column prop="discountAmount" header-align="center" align="center" label="优惠金额">
      </el-table-column>
      <el-table-column prop="rechargeChannelName" header-align="center" align="center" label="充值渠道">
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <div v-show="scope.row.status=='0'">
            <el-button type="text" size="small" @click="confirmedHandle(scope.row.id)">确认订单</el-button>
          </div>
          <div v-show="scope.row.status!='0'">
            <el-button type="info" size="small">已处理</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import moment from 'moment';
  import json2csv from 'json2csv'
  import dateutil from '@/utils/datechonse'

  export default {
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
        dataForm: {
          userAccount: '',
          orderNo: '',
          merchantOrderNo: '',
          status: '',
          rechargePlatform: '',
          rechargeChannel: '',
          // queryTime: [],
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)],
          amountMin: null,
          amountMax: null
        },
        options: [{
          "id": 0,
          "name": "未确认"
        },
          {
            "id": 1,
            "name": "已取消"
          },
          {
            "id": 2,
            "name": "已完成"
          }
        ],
        payOptions: [],
        channelOptions: [],
        yesterdayAmount: 0,
        todayAmount: 0,
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated() {
			if(this.$route.query.queryTime!=null){
				this.dataForm.queryTime = this.$route.query.queryTime
			}else{
				 this.dataForm.queryTime = [dateutil.getToday().starttime, dateutil.getToday().endtime]
			}
      this.dataForm.userAccount = this.$route.query.account
      this.getDataList()
    },
    components: {
      json2csv
    },
    created() {
      this.keyupSubmit()
    },
    methods: {
      // 获取数据列表
      getDataList() {

        this.dataListLoading = true
        this.dataListLoading = true
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
          }
        }
        //payconfig:payconfig:select
        this.$http({
          url: this.$http.adornUrl(`/payconfig/payconfig/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            // console.log(data.list);

            this.payOptions = [];
            for (var i = 0; i < data.list.length; i++) {
              if (data.list[i].payName == '') {
                continue;
              }
              var obj = {};
              obj.id = data.list[i].id;
              obj.name = data.list[i].payName;
              this.payOptions.push(obj);
            }
            // console.log(this.payOptions);
            //this.payOptions = data.list
          }
        });

        this.$http({
          url: this.$http.adornUrl(`/rechargechannel/rechargechannel/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.channelOptions = data.list
          }
        });

        //验证时间是否为空
        var time;
        var result = true;
        if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
          time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
        }
        var res = /^[0-9]*[1-9][0-9]*$/;
        //===============================验证充值最小金额格式==================================
        if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
          if (!res.test(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最小金额必须输入正整数")
            this.dataListLoading = false
            return;
          }
        }
        //验证充值最大金额格式
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
          if (!res.test(this.dataForm.amountMax)) {
            result = false;
            this.$message.error("充值最大金额必须输入正整数")
            this.dataListLoading = false
            return;
          }
        }
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
          .amountMin != '') {
          if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最大金额不可小于最小金额")
            this.dataListLoading = false
            return;

          }
        }
        console.log(this.dataForm)
        this.$http({
          url: this.$http.adornUrl('/orderrecharge/orderrecharge/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'rechargeType': 2,
            'userAccount': this.dataForm.userAccount,
            'orderNo': this.dataForm.orderNo,
            'merchantOrderNo': this.dataForm.merchantOrderNo,
            'status': this.dataForm.status,
            'rechargePlatform': this.dataForm.rechargePlatform.toString(),
            'rechargeChannel': this.dataForm.rechargeChannel,
            'startTime': startDate,
            'endTime': endDate,
            'amountMax': this.dataForm.amountMax,
            'amountMin': this.dataForm.amountMin
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            this.yesterdayAmount = data.yesterdayAmount
            this.todayAmount = data.todayAmount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      exportCSV() {
        this.downLoadMix("第三方支付订单.csv");
      },
      downLoadMix(title) {
        //验证时间是否为空
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
          }
        }
        var res = /^[0-9]*[1-9][0-9]*$/;
        //===============================验证充值最小金额格式==================================
        if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
          if (!res.test(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最小金额必须输入正整数")
            this.dataListLoading = false
            return;
          }
        }
        //验证充值最大金额格式
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
          if (!res.test(this.dataForm.amountMax)) {
            result = false;
            this.$message.error("充值最大金额必须输入正整数")
            this.dataListLoading = false
            return;
          }
        }
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
          .amountMin != '') {
          if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最大金额不可小于最小金额")
            this.dataListLoading = false
            return;

          }
        }
        this.$http({
          url: this.$http.adornUrl('/orderrecharge/orderrecharge/exportThirdCSVData'),
          method: 'get',
          responseType: 'arraybuffer',
          params: this.$http.adornParams({
            'rechargeType': 2,
            'userAccount': this.dataForm.userAccount,
            'orderNo': this.dataForm.orderNo,
            'merchantOrderNo': this.dataForm.merchantOrderNo,
            'status': this.dataForm.status,
            'rechargePlatform': this.dataForm.rechargePlatform.toString(),
            'rechargeChannel': this.dataForm.rechargeChannel,
            'startTime': startDate,
            'endTime': endDate,
            'amountMax': this.dataForm.amountMax,
            'amountMin': this.dataForm.amountMin
          })
        }).then(({
                   data
                 }) => {
          let blob = new Blob([data],
            {
              type: 'text/csv,charset=UTF-8、'
            });
          let link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.download = title;
          link.click();
          URL.revokeObjectURL(blob);
          /*var fields_ = [{
                            label: "订单号",
                            value: "orderNo"
                        }, {
                            label: "存款时间",
                            value: "createTime"
                        }, {
                            label: "金额",
                            value: "amount"
                        },
                        {
                            label: "订单状态",
                            value: "status"
                        }, {
                            label: "支付人账号",
                            value: "userAccount"
                        },
                        {
                            label: "是否首充",
                            value: "fristrecharge"
                        }, {
                            label: "第三方支付平台订单号",
                            value: "merchantOrderNo"
                        }, {
                            label: "第三方支付平台",
                            value: "rechargePlatform"
                        }, {
                            label: "充值终端WEB,PC",
                            value: "rechargeTerminal"
                        }, {
                            label: "充值前主账户金额",
                            value: "preMoney"
                        }, {
                            label: "优惠金额",
                            value: "discountAmount"
                        }, {
                            label: "充值渠道",
                            value: "rechargeChannelName"
                        }
                    ];
                    var opt = {
                        fields: fields_,
                        excelStrings: true
                    }
                    if (data && data.code === 200) {
                        const result = json2csv.parse(data.dataList, opt);
                        const csvContent = 'data:text/csv;charset=utf-8,\uFEFF' + result
                        const link = document.createElement('a')
                        link.href = encodeURI(csvContent)
                        link.download = title;
                        document.body.appendChild(link) // Required for FF
                        link.click()
                        document.body.removeChild(link) // Required for FF
                    }*/
        })
      },
      //绑定回车事件
      keyupSubmit() {
        document.onkeydown = e => {
          let _key = window.event.keyCode;
          if (_key === 13) {
            this.getDataListQuery()
          }
        }
      },
      //查询
      getDataListQuery() {
        this.pageIndex = 1;
        this.getDataList();
      },
      // 每页数
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      // 确认订单
      confirmedHandle(id) {
        this.$confirm(`确定完成订单操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/confirmed-third/' + id),
            method: 'post'
          }).then(({
                     data
                   }) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
              this.getDataList()
            }
          })
          this.dataListLoading = false
        })
      },
      // 取消订单
      canceledHandle(id) {
        this.$confirm(`确定取消订单操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/canceled/' + id),
            method: 'post'
          }).then(({
                     data
                   }) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
              this.getDataList()
            }
          })
          this.dataListLoading = false
        })
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({
                     data
                   }) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
