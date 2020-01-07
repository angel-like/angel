<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item label="订单号">
        <el-input v-model="dataForm.orderNo" placeholder="订单号" clearable></el-input>
      </el-form-item>
      <el-form-item label="存款人">
        <el-input v-model="dataForm.depositName" placeholder="存款人" clearable></el-input>
      </el-form-item>
      <el-form-item label="充值账号">
        <el-input v-model="dataForm.userAccount" placeholder="支付人账号" clearable></el-input>
      </el-form-item>
      <el-form-item label="操作人">
        <el-input v-model="dataForm.sysUserAccount" placeholder="操作人" clearable></el-input>
      </el-form-item>
      <el-form-item label="订单状态">
        <el-select v-model="dataForm.status" clearable placeholder="请选择订单状态">
          <el-option v-for="item in options" :key="item.status" :label="item.label" :value="item.status">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="存款方式">
        <el-select v-model="dataForm.depositBank" clearable placeholder="请选择存款方式">
          <el-option v-for="item in bankOptions" :key="item.name" :label="item.name" :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收款账号">
        <el-input v-model="dataForm.bankAccount" placeholder="收款账号" clearable></el-input>
      </el-form-item>
      <el-form-item label="充值金额范围">
        <el-input v-model="dataForm.amountMin" placeholder="最小" style="width: 80px;" clearable></el-input>
        ~
        <el-input v-model="dataForm.amountMax" placeholder="最大" style="width: 80px;" clearable></el-input>
      </el-form-item>
      <el-form-item label="优惠金额范围">
        <el-input v-model="dataForm.discountAmountMin" placeholder="最小" style="width: 80px;" clearable></el-input>
        ~
        <el-input v-model="dataForm.discountAmountMax" placeholder="最大" style="width: 80px;" clearable></el-input>
      </el-form-item>
      <el-form-item label="存款日期">
        <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
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
      <el-table-column prop="depositDate" header-align="center" align="center" label="提交时间">
      </el-table-column>
      <el-table-column prop="rechargeTime" header-align="center" align="center" label="充值时间">
      </el-table-column>
      <!--<el-table-column prop="createTime" header-align="center" align="center" label="存款时间">-->
      <!--</el-table-column>-->
      <el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作人">
      </el-table-column>
      <el-table-column prop="amount" header-align="center" align="center" label="金额">
      </el-table-column>
      <el-table-column prop="depositName" header-align="center" align="center" label="存款人">
      </el-table-column>
      <el-table-column prop="depositBank" header-align="center" align="center" label="存款方式">
      </el-table-column>
      <el-table-column prop="incomeBank" header-align="center" align="center" label="收款银行">
      </el-table-column>
      <el-table-column prop="incomeBankAccount" header-align="center" align="center" label="收款账号">
      </el-table-column>
      <el-table-column prop="payee" header-align="center" align="center" label="收款人">
      </el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="订单状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
          <el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
          <el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status==4" size="small" type="danger">已锁定</el-tag>
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
      <el-table-column prop="discountAmount" header-align="center" align="center" label="优惠金额">
      </el-table-column>
      <el-table-column prop="remark" header-align="center" align="center" label="备注">
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <div v-show="scope.row.status=='0'">
            <el-button v-if="isAuth('orderbankrecharge:orderbankrecharge:locking')" type="text" size="small"
                       @click="lockingHandle(scope.row.id)">锁定订单
            </el-button>
          </div>
          <div v-show="scope.row.status=='4'">
            <el-button v-if="isAuth('orderbankrecharge:orderbankrecharge:confirmed')" type="text" size="small"
                       @click="confirmedHandle(scope.row.id)">确认订单
            </el-button>
            <el-button v-if="isAuth('orderbankrecharge:orderbankrecharge:canceled')" type="text" size="small"
                       @click="canceledHandle(scope.row.id)">取消订单
            </el-button>
            <el-button v-if="isAuth('orderbankrecharge:orderbankrecharge:emancipateLocking')" type="text" size="small"
                       @click="emancipateLockingHandle(scope.row.id)">解除锁定
            </el-button>
          </div>
          <div v-show="scope.row.status!='0' &&scope.row.status!='4'">
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
          depositName: '',
          orderNo: '',
          sysUserAccount: '',
          queryTime: [],
          amountMin: null,
          amountMax: null,
          discountAmountMin: null,
          discountAmountMax: null,
          status: '',
          depositBank: '',
          bankAccount: '',
          term: 'id',
          enable: false

        },
        yesterdayAmount: 0,
        todayAmount: 0,
        options: [{
          status: 0,
          label: '未确认'
        },
          {
            status: 1,
            label: '取消'
          },
          {
            status: 2,
            label: '完成'
          },
          {
            status: 4,
            label: '已锁定'
          }
        ],
        bankOptions: [],
        termOptions: [{
          term: "id",
          label: '创建时间'
        },
          {
            term: "sys_user_account",
            label: '操作人'
          },
          {
            term: "amount",
            label: '充值金额'
          },
          {
            term: "income_bank_account",
            label: '收款账号'
          },
          {
            term: "deposit_type",
            label: '存款方式'
          },
          {
            term: "payee",
            label: '收款人'
          },
          {
            term: "income_bank",
            label: '收款银行'
          },
          {
            term: "fristrecharge",
            label: '是否首充'
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
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      json2csv
    },
    activated() {
			if(this.$route.query.queryTime!=null){
				this.dataForm.queryTime = this.$route.query.queryTime
			}else{
				 this.dataForm.queryTime=[dateutil.getToday().starttime, dateutil.getToday().endtime];
			}
      this.dataForm.userAccount = this.$route.query.account
      //this.dataForm.queryTime=[dateutil.getToday().starttime, dateutil.getToday().endtime];
      this.getDataList()
    },
    created() {
      this.keyupSubmit()
    },
    methods: {
      // 获取数据列表
      getDataList() {
				//为下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/`+"DepositMethod"),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.bankOptions = data.data
					}
				});
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
        // if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
        // 	time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
        // }
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

        var discountRes = /^(([1-9][0-9]*)|[0]|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        //===============================验证优惠最小金额格式==================================
        if (this.dataForm.discountAmountMin != null && this.dataForm.discountAmountMin != '') {
          if (!discountRes.test(this.dataForm.discountAmountMin)) {
            result = false;
            this.$message.error("优惠最小金额格式有误(最多两位小数)")
            this.dataListLoading = false
            return;
          }
        }
        //验证优惠最大金额格式
        if (this.dataForm.discountAmountMax != null && this.dataForm.discountAmountMax != '') {
          if (!discountRes.test(this.dataForm.discountAmountMax)) {
            result = false;
            this.$message.error("优惠最大金额必须输入正整数(最多两位小数)")
            this.dataListLoading = false
            return;
          }
        }
        if (this.dataForm.discountAmountMax != null && this.dataForm.discountAmountMax != '' && this.dataForm.discountAmountMin !=
          null && this.dataForm.discountAmountMin != '') {
          if (Number(this.dataForm.discountAmountMax) < Number(this.dataForm.discountAmountMin)) {
            result = false;
            this.$message.error("优惠最大金额不可小于最小金额")
            this.dataListLoading = false
            return;

          }
        }
        if (result) {
          this.$http({
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/list'),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'limit': this.pageSize,
              'rechargeType': 3,
              'userAccount': this.dataForm.userAccount,
              'depositName': this.dataForm.depositName,
              'orderNo': this.dataForm.orderNo,
              'sysUserAccount': this.dataForm.sysUserAccount,
              'startTime': startDate,
              'endTime': endDate,
              'amountMax': this.dataForm.amountMax,
              'amountMin': this.dataForm.amountMin,
              'discountAmountMax': this.dataForm.discountAmountMax,
              'discountAmountMin': this.dataForm.discountAmountMin,
              'status': this.dataForm.status,
              'depositBank': this.dataForm.depositBank,
              'bankAccount': this.dataForm.bankAccount,
              'term': this.dataForm.term,
              'enable': this.dataForm.enable
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
        }

      },
      exportCSV() {
        this.downLoadMix("银行卡存款订单.csv");
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

        var discountRes = /^(([1-9][0-9]*)|[0]|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        //===============================验证优惠最小金额格式==================================
        if (this.dataForm.discountAmountMin != null && this.dataForm.discountAmountMin != '') {
          if (!discountRes.test(this.dataForm.discountAmountMin)) {
            result = false;
            this.$message.error("优惠最小金额格式有误(最多两位小数)")
            this.dataListLoading = false
            return;
          }
        }
        //验证优惠最大金额格式
        if (this.dataForm.discountAmountMax != null && this.dataForm.discountAmountMax != '') {
          if (!discountRes.test(this.dataForm.discountAmountMax)) {
            result = false;
            this.$message.error("优惠最大金额必须输入正整数(最多两位小数)")
            this.dataListLoading = false
            return;
          }
        }
        if (this.dataForm.discountAmountMax != null && this.dataForm.discountAmountMax != '' && this.dataForm.discountAmountMin !=
          null && this.dataForm.discountAmountMin != '') {
          if (Number(this.dataForm.discountAmountMax) < Number(this.dataForm.discountAmountMin)) {
            result = false;
            this.$message.error("优惠最大金额不可小于最小金额")
            this.dataListLoading = false
            return;

          }
        }
        this.$http({
          url: this.$http.adornUrl('/orderrecharge/orderrecharge/exportBankCSVData'),
          method: 'get',
          responseType: 'arraybuffer',
          params: this.$http.adornParams({
            'rechargeType': 3,
            'userAccount': this.dataForm.userAccount,
            'depositName': this.dataForm.depositName,
            'orderNo': this.dataForm.orderNo,
            'sysUserAccount': this.dataForm.sysUserAccount,
						'startTime': startDate,
						'endTime': endDate,
            'amountMax': this.dataForm.amountMax,
            'amountMin': this.dataForm.amountMin,
            'discountAmountMax': this.dataForm.discountAmountMax,
            'discountAmountMin': this.dataForm.discountAmountMin,
            'status': this.dataForm.status,
            'depositBank': this.dataForm.depositBank,
            'bankAccount': this.dataForm.bankAccount,
            'term': this.dataForm.term,
            'enable': this.dataForm.enable
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
                            label: "提交时间",
                            value: "depositDate"
                        }, {
                            label: "充值时间",
                            value: "rechargeTime"
                        }, {
                            label: "操作人",
                            value: "sysUserAccount"
                        }, {
                            label: "金额",
                            value: "amount"
                        },
                        {
                            label: "存款人",
                            value: "depositName"
                        }, {
                            label: "存款方式",
                            value: "depositBank"
                        },
                        {
                            label: "收款银行",
                            value: "incomeBank"
                        }, {
                            label: "收款账号",
                            value: "incomeBankAccount"
                        }, {
                            label: "订单状态",
                            value: "status"
                        }, {
                            label: "支付人账号",
                            value: "userAccount"
                        }, {
                            label: "是否首充",
                            value: "fristrecharge"
                        }, {
                            label: "优惠金额",
                            value: "discountAmount"
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
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/confirmed/' + id),
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
      // 锁定订单
      lockingHandle(id) {
        this.$confirm(`确定锁定订单操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/locking/' + id),
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
      emancipateLockingHandle(id) {
        this.$confirm(`确定要解除该订单锁定状态?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/orderrecharge/orderrecharge/emancipateLocking/' + id),
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
              this.getDataList()
            }
          })
        })
      }
    }
  }
</script>
