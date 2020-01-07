<template>
  <div class="mod-config">

      <!-- 线上取款订单 -->

        <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
          <el-form-item label="取款日期">
            <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
                            start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="订单号">
            <el-input v-model="dataForm.orderNo" placeholder="订单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="取款用户账号">
          <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
         </el-form-item>
          <el-form-item label="入款银行">
            <el-input v-model="dataForm.userBankName" placeholder="入款银行" clearable></el-input>
          </el-form-item>
          <el-form-item label="入款账号">
            <el-input v-model="dataForm.userIncomeNo" placeholder="入款账号" clearable></el-input>
          </el-form-item>
          <el-form-item label="订单状态">
            <el-select v-model="dataForm.status" clearable placeholder="请选择订单状态">
              <el-option v-for="item in options" :key="item.status" :label="item.label" :value="item.status">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="取款类型">
            <el-select v-model="dataForm.accountType" clearable placeholder="请选择取款类型">
              <el-option v-for="item in typeOptions" :key="item.type" :label="item.label" :value="item.type">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="金额范围">
            <el-input v-model="dataForm.amountMin" placeholder="最小范围" style="width: 100px;"></el-input>
            ~
            <el-input v-model="dataForm.amountMax" placeholder="最大范围" style="width: 100px;"></el-input>
          </el-form-item>
          <el-form-item label="手续费范围">
            <el-input v-model="dataForm.poundageMin" placeholder="最小范围" style="width: 100px;"></el-input>
            ~
            <el-input v-model="dataForm.poundageMax" placeholder="最大范围" style="width: 100px;"></el-input>
          </el-form-item>
          <el-form-item label="操作人">
            <el-input v-model="dataForm.sysUserAccount" placeholder="操作人" clearable></el-input>
          </el-form-item>
          <el-form-item label="取消打码">
            <el-select v-model="dataForm.betCancel" clearable placeholder="请选择是否取消打码">
              <el-option v-for="item in cancelOptions" :key="item.type" :label="item.label" :value="item.type">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排序">
            <el-select v-model="dataForm.term" placeholder="排序" style="width: 120px;">
              <el-option v-for="item in termOptions" :key="item.term" :label="item.label" :value="item.term">
              </el-option>
            </el-select>
            <el-select v-model="dataForm.enable" placeholder="升/降" style="width: 80px;">
              <el-option v-for="item in enableOptions" :key="item.enable" :label="item.label" :value="item.enable">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button @click="getDataListQuery()">查询</el-button>
            <el-button @click="exportCSV()">下载Excel</el-button>
            <el-button @click="getDataList()">刷新</el-button>
            <el-button v-if="isAuth('ordertakemoney:ordertakemoney:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
            <el-button v-if="isAuth('ordertakemoney:ordertakemoney:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
          </el-form-item>
        </el-form>
        <div style="color: red;margin-bottom: 10px;">取款笔数总计：{{takeMoneyNum}}    &nbsp;&nbsp;&nbsp;取款金额总计：{{takeMoneytotal}}    &nbsp;&nbsp;&nbsp;已完成笔数总计：{{conutStatus}}    &nbsp;&nbsp;&nbsp;已完成金额：{{sumStatus}}</div>
        <el-table :data="dataList" border aling="center" v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
          <el-table-column
            type="index"
            width="80"
            header-align="center"
            align="center"
            label="序号">
          </el-table-column>

          <el-table-column align="left" width="300" label="订单信息">
            <template slot-scope="scope">
              <!-- 订单号 -->
              <div >
                <span style="display:inline-block;white-space:nowrap;">订单号：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.orderNo}}</span>
              </div>
              <!-- 取款时间 -->
              <div >
                <span style="display:inline-block;white-space:nowrap;">取款时间：</span>
                <span style="display:inline-block;white-space:nowrap;"> {{scope.row.createTime}}</span>
              </div>
              <!-- 操作人 -->
              <div>
                <span style="display:inline-block;white-space:nowrap;">操作人：</span>
                <span style="display:inline-block;white-space:nowrap;"> {{scope.row.sysUserAccount}}</span>
              </div>
              <!-- 操作时间 -->
              <div >
                <span style="display:inline-block;white-space:nowrap;">操作时间：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.updateTime}}</span>
              </div>
              <div v-if="scope.row.status==4">
                <span style="display:inline-block;white-space:nowrap;color: #0000CC;font-weight: bold;">锁定订单时间：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.updateTime}}</span>
              </div>
              <div v-if="scope.row.status==2">
                <span style="display:inline-block;white-space:nowrap;color: #008000;font-weight: bold;">出款时间：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.updateTime}}</span>
              </div>
              <div  v-if="scope.row.status==1">
                <span style="display:inline-block;white-space:nowrap;color: #AA1111;font-weight: bold;">取消订单时间：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.updateTime}}</span>
              </div>
            </template>
          </el-table-column>


          <el-table-column align="left" label="取款信息" width="260">
            <template slot-scope="scope">
              <div >
                <span style="display:inline-block;white-space:nowrap;">取款用户账号：</span>
                <span @click="read(scope.row)" style="cursor:pointer;color:#17b3a3; display:inline-block;white-space:nowrap;"> {{scope.row.userAccount}}</span>
              </div>

              <div >
                <span style="display:inline-block;white-space:nowrap;">取款金额：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.takeAmount}}</span>
              </div>
              <div >
                <span style="display:inline-block;white-space:nowrap;">取款需要打码：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.userNeedBet}}</span>
              </div>
              <div >

                <span style="display:inline-block;white-space:nowrap;">用户有效打码：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.userValidBet}}</span>
              </div>
              <div >
                <span style="display:inline-block;white-space:nowrap;">手续费：</span>
                <span style="display:inline-block;white-space:nowrap;"> {{scope.row.poundage}}</span>
              </div>
            </template>
          </el-table-column>


          <el-table-column align="left" label="银行信息" width="260">
            <template slot-scope="scope">
              <div >
                <span style="display:inline-block;white-space:nowrap;">入款账号：</span>
                <span style="display:inline-block;white-space:nowrap;"> {{scope.row.incomeNo}}</span>
              </div>
              <div >

                <span style="display:inline-block;white-space:nowrap;">银行名称：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.bankName}}</span>
              </div>
              <div >
                <span style="display:inline-block;white-space:nowrap;">开户名：</span>
                <span style="display:inline-block;white-space:nowrap;">{{scope.row.accountName}}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="userSurplusCoin" header-align="center" align="center" label="用户剩余金额" width="300">
            <template slot-scope="scope">
              <div v-if="scope.row.userSurplusCoin!=null">{{scope.row.userSurplusCoin/100}}</div>
              <div v-else>{{scope.row.userSurplusCoin}}</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="remark"
            header-align="center"
            align="center"
            label="备注">
          </el-table-column>
          <el-table-column width="90" prop="status" header-align="center" align="center" label="状态">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
              <el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
              <el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
              <el-tag v-if="scope.row.status==4" size="small" type="success">已锁定</el-tag>
            </template>
          </el-table-column>
          <el-table-column width="90" prop="accountType" header-align="center" align="center" label="类型">
            <template slot-scope="scope">
              <div v-if="scope.row.accountType==0">
                支付宝
              </div>
              <div v-if="scope.row.accountType==1">
                银行
              </div>
              <div v-if="scope.row.accountType==2">
                人工取款
              </div>
            </template>
          </el-table-column>
          <el-table-column width="80" prop="betCancel" header-align="center" align="center" label="取消打码">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.betCancel" size="small" type="info">是</el-tag>
              <el-tag v-if="!scope.row.betCancel" size="small" type="info">否</el-tag>
            </template>
          </el-table-column>
          <el-table-column  fixed="right" header-align="center" align="center" width="120" label="操作">
            <template slot-scope="scope">
              <div v-show="scope.row.status=='0'">
                <el-button v-if="isAuth('ordertakemoney:ordertakemoney:locking')" type="text" size="small" @click="lockingHandle(scope.row.id)">锁定订单</el-button>
                <el-button v-else type="info" size="small">无</el-button>
              </div>
              <div v-show="scope.row.status=='4'">
                <el-button v-if="isAuth('ordertakemoney:ordertakemoney:confirmed')" type="text" size="small" @click="confirmedHandle(scope.row.id)">确认订单</el-button>
                <el-button v-if="isAuth('ordertakemoney:ordertakemoney:canceled')" type="text" size="small" @click="canceledHandle(scope.row.id)">取消订单</el-button>
                <el-button v-if="isAuth('ordertakemoney:ordertakemoney:betCanceled')" type="text" size="small" @click="cancelBetHandle(scope.row.id)">强制确认</el-button>
                <el-button v-if="isAuth('ordertakemoney:ordertakemoney:emancipateLocking')" type="text" size="small" @click="emancipateLockingHandle(scope.row.id)">解除锁定</el-button>
                <el-button v-else type="info" size="small">无</el-button>
              </div>
              <div v-show="scope.row.status!='0' &&scope.row.status!='4'">
                <el-button type="info" size="small">已处理</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                       :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
        <edit-user-details v-if="editUserDetailsVisible" ref="editUserDetails" @refreshDataList="getDataList" @recordingFlag="recordingFlag"></edit-user-details>
    <index-subordinate v-if="subordinateVisible" ref="subordinateRef"></index-subordinate>
  </div>
</template>

<script>
  import moment from 'moment'
import Orderadmin from '../orderadministrator/orderadmin'
  import { isAuth } from '@/utils'
  import Ordertakemoneywork from './ordertakemoneywork'
import editUserDetails from './edit-user-details'
import IndexSubordinate from './agent-subordinate'// 弹出框
import json2csv from 'json2csv'
import dateutil from '@/utils/datechonse'
  export default {
    data () {
      return {
        pickerOptions2: {
          shortcuts: [{
            text: '今天',
            onClick (picker) {
              const end = dateutil.getToday().endtime
              const start = dateutil.getToday().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '昨天',
            onClick (picker) {
              const end = dateutil.getYesterday().endtime
              const start = dateutil.getYesterday().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '本周',
            onClick (picker) {
              const end = dateutil.getCurrWeekDays().endtime
              const start = dateutil.getCurrWeekDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上周',
            onClick (picker) {
              const end = dateutil.getLastWeekDays().endtime
              const start = dateutil.getLastWeekDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '本月',
            onClick (picker) {
              const end = dateutil.getCurrMonthDays().endtime
              const start = dateutil.getCurrMonthDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上月',
            onClick (picker) {
              const end = dateutil.getLastMonthDays().endtime
              const start = dateutil.getLastMonthDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去7天',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去30天',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去二月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 60)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去三月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }]
        },
        subordinateVisible: true,
        dataForm: {
          userAccount: '',
          orderNo: '',
          sysUserAccount: '',
          status: '',
          // queryTime: [],
          queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1)],
          amountMin: null,
          amountMax: null,
          userBankName: '',
          poundageMin: null,
          poundageMax: null,
          userIncomeNo: '',
          accountType: '',
          betCancel: null,
          term: 'create_time',
          enable: false

        },
        sumStatus: 0,
        conutStatus: 0,
        activeName: 'first',
        options: [{
          status: 0,
          label: '未确认'
        },
        {
          status: 1,
          label: '已取消'
        },
        {
          status: 2,
          label: '已完成'
        },
        {
          status: 4,
          label: '锁定'
        }
        ],
        typeOptions: [{
          type: 0,
          label: '支付宝'
        },
        {
          type: 1,
          label: '银行'
        }
        ],
        cancelOptions: [{
          type: false,
          label: '否'
        },
        {
          type: true,
          label: '是'
        }
        ],
        termOptions: [{
          term: 'create_time',
          label: '取款时间'
        },
        {
          term: 'account_type',
          label: '取款类型'
        },
        {
          term: 'bank_name',
          label: '银行名称'
        }
        ],
        enableOptions: [{
          enable: true,
          label: '升序'
        },
        {
          enable: false,
          label: '倒序'
        }
        ],

        takeMoneyNum: 0,
        takeMoneytotal: 0,
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        editUserDetailsVisible: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    activated () {
      this.dataForm.userAccount = this.$route.query.account
      // this.dataForm.queryTime=[dateutil.getToday().starttime, dateutil.getToday().endtime];
      this.getDataList()
    },
    created () {
      this.keyupSubmit()
    },
    components: {
      Ordertakemoneywork,
      editUserDetails,
      IndexSubordinate,
      Orderadmin,
      json2csv
    },
    methods: {
      recordingFlag (id) {
        // this.subordinateVisible =flag
        this.$refs.subordinateRef.init(id)
      },
      // 获取数据列表
      getDataList () {
        // this.dataListLoading = true
        this.dataListLoading = true
        var startDate = null
        var endDate = null
        var timeArr = this.dataForm.queryTime
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format('YYYY-MM-DD HH:mm:ss')
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format('YYYY-MM-DD HH:mm:ss')
          }
        }
        // 验证时间是否为空
        // var time;
        // var result = true;
        // if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
        // 	time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
        // }
        var res = /^[0-9]*[1-9][0-9]*$/
      // ===============================验证取款最小金额格式==================================
        if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
          if (!res.test(this.dataForm.amountMin)) {
            result = false
            this.$message.error('取款最小金额必须输入正整数')
            this.dataListLoading = false
          }
        }
        // 验证取款最大金额格式
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
          if (!res.test(this.dataForm.amountMax)) {
            result = false
            this.$message.error('取款最大金额必须输入正整数')
            this.dataListLoading = false
            return
          }
        }
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
          .amountMin != '') {
          if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
            result = false
            this.$message.error('取款最大金额不可小于最小金额')
            this.dataListLoading = false
            return
          }
        }

        var discountRes = /^(([1-9][0-9]*)|[0]|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/
      // ===============================验证手续费最小金额格式==================================
        if (this.dataForm.poundageMin != null && this.dataForm.poundageMin != '') {
          if (!discountRes.test(this.dataForm.poundageMin)) {
            result = false
            this.$message.error('手续费最小金额格式有误(最多两位小数)')
            this.dataListLoading = false
            return
          }
        }
        // 验证手续费最大金额格式
        if (this.dataForm.poundageMax != null && this.dataForm.poundageMax != '') {
          if (!discountRes.test(this.dataForm.poundageMax)) {
            result = false
            this.$message.error('手续费最大金额必须输入正整数(最多两位小数)')
            this.dataListLoading = false
            return
          }
        }
        if (this.dataForm.poundageMax != null && this.dataForm.poundageMax != '' && this.dataForm.poundageMin != null &&
          this.dataForm.poundageMin != '') {
          if (Number(this.dataForm.poundageMax) < Number(this.dataForm.poundageMin)) {
            result = false
            this.$message.error('手续费最大金额不可小于最小金额')
            this.dataListLoading = false
            return
          }
        }
        this.$http({
          url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'type': 0,
            'userAccount': this.dataForm.userAccount,
            'orderNo': this.dataForm.orderNo,
            'sysUserAccount': this.dataForm.sysUserAccount,
            'status': this.dataForm.status,
            'startTime': startDate,
            'endTime': endDate,
            'amountMax': this.dataForm.amountMax,
            'amountMin': this.dataForm.amountMin,
            'userBankName': this.dataForm.userBankName,
            'poundageMax': this.dataForm.poundageMax,
            'poundageMin': this.dataForm.poundageMin,
            'userIncomeNo': this.dataForm.userIncomeNo,
            'accountType': this.dataForm.accountType,
            'betCancel': this.dataForm.betCancel,
            'term': this.dataForm.term,
            'enable': this.dataForm.enable

          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            this.takeMoneyNum = data.takeMoneyNum
            this.takeMoneytotal = data.takeMoneytotal
            this.sumStatus = data.sumStatus
            this.conutStatus = data.conutStatus
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      exportCSV () {
        this.downLoadMix('取款订单.csv')
      },
      downLoadMix (title) {
        // 验证时间是否为空
        var resultCheck = true
        var startDate = null
        var endDate = null
        var timeArr = this.dataForm.queryTime
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format('YYYY-MM-DD HH:mm:ss')
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format('YYYY-MM-DD HH:mm:ss')
          }
        }
        var res = /^[0-9]*[1-9][0-9]*$/
      // ===============================验证取款最小金额格式==================================
        if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
          if (!res.test(this.dataForm.amountMin)) {
            resultCheck = false
            this.$message.error('取款最小金额必须输入正整数')
            this.dataListLoading = false
          }
        }
        // 验证取款最大金额格式
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
          if (!res.test(this.dataForm.amountMax)) {
            resultCheck = false
            this.$message.error('取款最大金额必须输入正整数')
            this.dataListLoading = false
            return
          }
        }
        if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
          .amountMin != '') {
          if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
            resultCheck = false
            this.$message.error('取款最大金额不可小于最小金额')
            this.dataListLoading = false
            return
          }
        }

        var discountRes = /^(([1-9][0-9]*)|[0]|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/
      // ===============================验证手续费最小金额格式==================================
        if (this.dataForm.poundageMin != null && this.dataForm.poundageMin != '') {
          if (!discountRes.test(this.dataForm.poundageMin)) {
            resultCheck = false
            this.$message.error('手续费最小金额格式有误(最多两位小数)')
            this.dataListLoading = false
            return
          }
        }
        // 验证手续费最大金额格式
        if (this.dataForm.poundageMax != null && this.dataForm.poundageMax != '') {
          if (!discountRes.test(this.dataForm.poundageMax)) {
            resultCheck = false
            this.$message.error('手续费最大金额必须输入正整数(最多两位小数)')
            this.dataListLoading = false
            return
          }
        }
        if (this.dataForm.poundageMax != null && this.dataForm.poundageMax != '' && this.dataForm.poundageMin != null &&
          this.dataForm.poundageMin != '') {
          if (Number(this.dataForm.poundageMax) < Number(this.dataForm.poundageMin)) {
            resultCheck = false
            this.$message.error('手续费最大金额不可小于最小金额')
            this.dataListLoading = false
            return
          }
        }
        this.$http({
          url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/exportCSVData'),
          method: 'get',
          responseType: 'arraybuffer',
          params: this.$http.adornParams({
            'type': 0,
            'userAccount': this.dataForm.userAccount,
            'orderNo': this.dataForm.orderNo,
            'sysUserAccount': this.dataForm.sysUserAccount,
            'status': this.dataForm.status,
            'startTime': startDate,
            'endTime': endDate,
            'amountMax': this.dataForm.amountMax,
            'amountMin': this.dataForm.amountMin,
            'userBankName': this.dataForm.userBankName,
            'poundageMax': this.dataForm.poundageMax,
            'poundageMin': this.dataForm.poundageMin,
            'userIncomeNo': this.dataForm.userIncomeNo,
            'accountType': this.dataForm.accountType,
            'betCancel': this.dataForm.betCancel,
            'term': this.dataForm.term,
            'enable': this.dataForm.enable
          })
        }).then(({
                   data
                 }) => {
          let blob = new Blob([data],
            {
              type: 'text/csv,charset=UTF-8、'
            })
          let link = document.createElement('a')
          link.href = window.URL.createObjectURL(blob)
          link.download = title
          link.click()
          URL.revokeObjectURL(blob)
        /* var fields_ = [{
                          label: "订单号",
                          value: "orderNo"
                      }, {
                          label: "取款时间",
                          value: "createTime"
                      },{
                          label: "操作时间",
                          value: "updateTime"
                      },
                      {
                          label: "取款用户账号",
                          value: "userAccount"
                      }, {
                          label: "取款金额",
                          value: "takeAmount"
                      }, {
                          label: "取款需要打码",
                          value: "userNeedBet"
                      }, {
                          label: "用户有效打码",
                          value: "userValidBet"
                      },
                      {
                          label: "手续费",
                          value: "poundage"
                      }, {
                          label: "入款账号",
                          value: "incomeNo"
                      }, {
                          label: "银行名称",
                          value: "bankName"
                      }, {
                          label: "开户名",
                          value: "accountName"
                      }, {
                          label: "状态",
                          value: "status"
                      }, {
                          label: "取款类型",
                          value: "accountType"
                      }, {
                          label: "操作管理员",
                          value: "sysUserAccount"
                      }, {
                          label: "是否取消打码",
                          value: "betCancel"
                      }
                  ];
                  var opt = {
                      fields: fields_,
                      excelStrings: true,
                      header:true
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
                  } */
        })
      },
      // 绑定回车事件
      keyupSubmit () {
        document.onkeydown = e => {
          let _key = window.event.keyCode
          if (_key === 13) {
            this.getDataListQuery()
          }
        }
      },
      // 查询
      getDataListQuery () {
        this.pageIndex = 1
        this.getDataList()
      },
      // 查询用户信息
      read (user) {
        this.editUserDetailsVisible = true
        this.$nextTick(() => {
          this.$refs.editUserDetails.initEdit(user)
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      handleClick (tab, event) {
        this.getDataList()
        this.$nextTick(() => {
          this.$refs.Ordertakemoneywork.getDataList()
        })
      },
      // 锁定订单
      lockingHandle (id) {
        this.$confirm(`确定锁定订单操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/locking/' + id),
            method: 'post'
          }).then(({data}) => {
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
      emancipateLockingHandle (id) {
        this.$confirm(`确定要解除该订单锁定状态?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/emancipateLocking/' + id),
            method: 'post'
          }).then(({data}) => {
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
      // 确认订单
      confirmedHandle (id) {
        this.$confirm(`确定完成订单操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/confirmed/' + id),
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
      canceledHandle (id) {
        this.$prompt(`确定取消订单操作?(可在下框中输入备注)`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^.{0,200}$/,
          inputErrorMessage: '备注输入不得超过200个字'
        }).then(({ value }) => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/canceled'),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'remark': value
            })
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
      // // 取消订单
      // canceledHandle (id) {
      //   this.$confirm(`确定取消订单操作?`, '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(() => {
      //     this.dataListLoading = true
      //     this.$http({
      //       url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/canceled/' + id),
      //       method: 'post'
      //     }).then(({
      //                data
      //              }) => {
      //       if (data && data.code === 200) {
      //         this.$message({
      //           message: '操作成功',
      //           type: 'success',
      //           duration: 1500,
      //           onClose: () => {
      //             this.getDataList()
      //           }
      //         })
      //       } else {
      //         this.$message.error(data.msg)
      //         this.getDataList()
      //       }
      //     })
      //     this.dataListLoading = false
      //   })
      // },
      // 取消打码订单
      cancelBetHandle (id) {
        this.$confirm(`确定取消订单操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/bet/canceled/' + id),
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
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/delete'),
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
              this.getDataList()
            }
          })
        })
      }
    }
  }
</script>
