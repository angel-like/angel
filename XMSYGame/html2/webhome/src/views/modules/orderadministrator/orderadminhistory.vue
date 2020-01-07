<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.orderNo" placeholder="订单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.sysUserAccount" placeholder="操作人" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.account" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-select v-model="dataForm.operationType" placeholder="请选择操作类型" clearable>
        <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-form-item>
        <el-input v-model="dataForm.amountMin" placeholder="最小范围" style="width: 100px;" clearable></el-input>
        ~
        <el-input v-model="dataForm.amountMax" placeholder="最大范围" style="width: 100px;" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2"
                        :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button @click="exportCSV()">下载Excel</el-button>
        <el-button v-if="isAuth('orderadministratorrecharge:orderadministratorrecharge:delete')" type="danger"
                   @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
      </el-form-item>
      <el-form-item>
        <span style="color: red;"> 存款笔数:{{map.rechargeTotal}}</span>
        &nbsp;
        <span style="color: red;"> 取款笔数:{{map.takeTotal}}</span>
        &nbsp;
        <span style="color: red;"> 存款总额:{{map.rechargeMoney}}</span>
        &nbsp;
        <span style="color: red;"> 取款总额:{{map.takeMoney}}</span>
        &nbsp;
        <span style="color: red;"> 优惠总额:{{map.disMoney}}</span>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <el-table-column
        type="index"
        width="120"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="id">
      </el-table-column>
      <el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
      </el-table-column>
      <el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作人用户名">
      </el-table-column>
      <el-table-column prop="sysUserId" header-align="center" align="center" label="操作人id">
      </el-table-column>
      <el-table-column prop="account" header-align="center" align="center" label="会员账号">
      </el-table-column>
      <el-table-column prop="operationType" header-align="center" align="center" min-width="180" label="操作类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.operationType==0">存款</el-tag>
          <el-tag v-else-if="scope.row.operationType==1">取款</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="操作金额">
      </el-table-column>
      <el-table-column prop="discountAmount" header-align="center" align="center" label="优惠金额(元)">
      </el-table-column>
      <el-table-column prop="vipDiscount" header-align="center" align="center" label="VIP优惠金额(元)">
      </el-table-column>
      <el-table-column prop="rechargeMultiple" header-align="center" align="center" label="存款打码倍数">
      </el-table-column>
      <el-table-column prop="discountMultiple" header-align="center" align="center" label="优惠金额打码倍数">
      </el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="订单状态">
        <template slot-scope="scope">
          <div v-if="scope.row.orderStatus==0">
            未确认
          </div>
          <div v-else-if="scope.row.orderStatus==1">
            订单取消
          </div>
          <div v-else-if="scope.row.orderStatus==2">
            订单完成
          </div>
          <div v-else-if="scope.row.orderStatus==3">
            订单撤销
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="remake" header-align="center" align="center" label="订单备注">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="操作时间">
      </el-table-column>
      <!-- <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="revokeHandle(scope.row.orderNo,scope.row.operationType)">查看</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <revoke v-if="revokeVisible" ref="revoke" @refreshDataList="getDataList"></revoke>
    <create v-if="createVisible" ref="create" @refreshDataList="getDataList"></create>
  </div>
</template>

<script>
  import revoke from './orderadministrator-list'
  import create from './orderadmin-create'
  import moment from 'moment';
  import json2csv from 'json2csv';
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
          orderNo: '',
          sysUserAccount: '',
          hierarchyId: '',
          operationType: '',
          amountMin: null,
          amountMax: null,
          queryTime: []
        },
        map: {
          rechargeTotal: 0,
          takeTotal: 0,
          rechargeMoney: 0,
          takeMoney: 0,
          disMoney: 0,
        },
        dataList: [],
        pageIndex: 1,
        options: [{
          "id": 0,
          "name": "存款"
        },
          {
            "id": 1,
            "name": "取款"
          }
        ],
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        revokeVisible: false,
        createVisible: false

      }
    },
    components: {
      revoke,
      create
    },
    activated() {
      if (this.$route.query.queryTime != null) {
        this.dataForm.queryTime = this.$route.query.queryTime
      } else {
        this.dataForm.queryTime = [dateutil.getToday().starttime, dateutil.getToday().endtime]
      }
      this.dataForm.account = this.$route.query.account
      this.dataForm.operationType = this.$route.query.operationType
      this.getDataList()
    },
    created() {
      this.keyupSubmit()
    },
    methods: {
      // 获取数据列表

      getDataList() {


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
        //验证时间是否为空
        // var time;
        var result = true;
        // if(this.dataForm.queryTime!=null&&this.dataForm.queryTime!=''){
        // 	time=moment(this.dataForm.queryTime).format("YYYY-MM-DD")
        // }
        var res = /^[0-9]*[1-9][0-9]*$/;
        //===============================验证充值最小金额格式==================================
        if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
          if (!res.test(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最小金额必须输入正整数")
            this.dataListLoading = false
          }
        }
        //验证充值最大金额格式
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
          if (!res.test(this.dataForm.amountMax)) {
            result = false;
            this.$message.error("充值最大金额必须输入正整数")
            this.dataListLoading = false
          }
        }
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
          .amountMin != '') {
          if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最大金额不可小于最小金额")
            this.dataListLoading = false

          }
        }
        this.$http({
          url: this.$http.adornUrl('/orderadministratorrecharge/orderadministratorrecharge/orderList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'sort': 'id',
            'direction': false,
            'orderNo': this.dataForm.orderNo,
            'sysUserAccount': this.dataForm.sysUserAccount,
            'account': this.dataForm.account,
            'operationType': this.dataForm.operationType,
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
            this.map=data.total
            console.log(data.total)
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      exportCSV() {
        this.downLoadMix("存取款订单.csv");
      },
      downLoadMix(title) {
        //验证时间是否为空
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = timeArr[0];
          if (timeArr.length > 1) {
            endDate = timeArr[1];
          }
        }
        var res = /^[0-9]*[1-9][0-9]*$/;
        //===============================验证充值最小金额格式==================================
        if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
          if (!res.test(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最小金额必须输入正整数")
            this.dataListLoading = false
          }
        }
        //验证充值最大金额格式
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
          if (!res.test(this.dataForm.amountMax)) {
            result = false;
            this.$message.error("充值最大金额必须输入正整数")
            this.dataListLoading = false
          }
        }
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
          .amountMin != '') {
          if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
            result = false;
            this.$message.error("充值最大金额不可小于最小金额")
            this.dataListLoading = false

          }
        }
        this.$http({
          url: this.$http.adornUrl('/orderadministratorrecharge/orderadministratorrecharge/exportCSVData'),
          method: 'get',
          responseType: 'arraybuffer',
          params: this.$http.adornParams({
            'orderNo': this.dataForm.orderNo,
            'sysUserAccount': this.dataForm.sysUserAccount,
            'account': this.dataForm.account,
            'operationType': 1,
            'rechargeTypeName': 'rechargeType',
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
                            label: "id",
                            value: "id"
                        }, {
                            label: "订单号",
                            value: "orderNo"
                        }, {
                            label: "操作人用户名",
                            value: "sysUserAccount"
                        }, {
                            label: "操作人id",
                            value: "sysUserId"
                        },
                        {
                            label: "用户账号",
                            value: "account"
                        }, {
                            label: "操作类型",
                            value: "operationType"
                        }, {
                            label: "操作金额",
                            value: "amount"
                        }, {
                            label: "优惠金额（元）",
                            value: "discountAmount"
                        },
                        {
                            label: "VIP优惠金额（元）",
                            value: "vipDiscount"
                        }, {
                            label: "订单状态",
                            value: "status"
                        }, {
                            label: "订单备注",
                            value: "remake"
                        }, {
                            label: "操作时间",
                            value: "createTime"
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
      // 新增 / 修改
      revokeHandle(orderNo, operationType) {
        this.revokeVisible = true
        this.$nextTick(() => {
          this.$refs.revoke.init(orderNo, operationType)
        })
      },
      // 创建
      createHandle(id) {
        this.createVisible = true
        this.$nextTick(() => {
          this.$refs.create.init(id)
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
            url: this.$http.adornUrl('/orderadministratorrecharge/orderadministratorrecharge/delete'),
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
