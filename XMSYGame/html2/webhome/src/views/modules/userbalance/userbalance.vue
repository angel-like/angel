<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item >
      <el-date-picker v-model="dataForm.queryTime" type="datetimerange" range-separator="至"
                    start-placeholder="开始日期"  end-placeholder="结束日期"  :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
      </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
    <!--    <el-button v-if="isAuth('userbalance:userbalance:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('userbalance:userbalance:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
     -->
	  </el-form-item>
    <br />
    <el-form-item>
          <div style="margin-bottom: 20px;font-size: 16px; color: red;">
          总金额：<span>{{AllMoney/100}}</span> &nbsp;&nbsp;
          总收益：<span>{{AllPrize/100}}</span> &nbsp;&nbsp;
          昨日总收益：<span>{{AllProfitYesterday/100}}</span> </div>
    </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
      	type="index"
      	width="120"
      	header-align="center"
      	align="center"
      	label="序号">
      </el-table-column>
      <!-- <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员ID">
      </el-table-column> -->
      <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        label="会员名称">
        </el-table-column>
	  <el-table-column
	    prop="userAccount"
	    header-align="center"
	    align="center"
	    label="会员账号">
	  </el-table-column>
    <el-table-column
      prop="productName"
      header-align="center"
      align="center"
      label="方案名称">
    </el-table-column>
      <el-table-column
        prop="money"
        header-align="center"
        align="center"
        label="总金额">
				<template slot-scope="scope">
					<div>{{scope.row.money/100}}</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="unprofitMoney"
        header-align="center"
        align="center"
        label="未计算收益">
				<template slot-scope="scope">
					<div>{{scope.row.unprofitMoney/100}}</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="profit"
        header-align="center"
        align="center"
        label="总收益">
				<template slot-scope="scope">
					<div>{{scope.row.profit/100}}</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="profitYesterday"
        header-align="center"
        align="center"
        label="昨日收益">
				<template slot-scope="scope">
					<div>{{scope.row.profitYesterday/100}}</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="rate"
        header-align="center"
        align="center"
        label="利率">
      </el-table-column>
			<el-table-column
			  prop="countDay"
			  header-align="center"
			  align="center"
			  label="计算日期">
			</el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
				<template slot-scope="scope">
					<el-button style="margin-right: 3px;" size="mini" type="text" title="查看存取记录" @click="readUserBalanceRecord(scope.row.userId)" >存取记录</el-button>
					<el-button style="margin-right: 13px;" size="mini" type="text" title="查看收益明细" @click="readUserProfitRecord(scope.row.userId)" >收益明细</el-button>
				</template>
      <!--  <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">（金额）存取记录</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">（余额宝）收益明细</el-button>
        </template>		-->
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		<!-- <read-userbalancerecord v-if="readUserBalanceRecordVisbel" ref="userBalanceRecord" @refreshDataList="getDataList"> </read-userbalancerecord> -->
		<user-balance-record v-if="readUserBalanceRecordVisbel" ref="userBalanceRecord" @refreshDataList="getDataList"></user-balance-record>
		<user-profit-record v-if="readUserProfitRecordVisbel" ref="userProfitRecord" @refreshDataList="getDataList"></user-profit-record>
  </div>
</template>

<script>
  import AddOrUpdate from './userbalance-add-or-update'
import userBalanceRecord from './read_userbalancerecord'
import userProfitRecord from './read_userprofitrecord'
  import dateutil from '@/utils/datechonse'
  import moment from 'moment'
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
        dataForm: {
          userAccount: '',
          userName: '',
          rate: '',
          productName: '',
          queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime],
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        readUserBalanceRecordVisbel: false,
        readUserProfitRecordVisbel: false,
        AllMoney: 0,
        AllPrize: 0,
        AllProfitYesterday: 0
      }
    },
    components: {
      userProfitRecord,
      userBalanceRecord,
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
  created () {
		 this.keyupSubmit()
},
    methods: {
      // 获取数据列表
      getDataList () {
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
        this.$http({
          url: this.$http.adornUrl('/userbalance/userbalance/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userAccount': this.dataForm.userAccount,
            'startTime': startDate,
            'endTime': endDate

          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
            if (data.AllMoney == null || data.AllMoney == '') {
              this.AllMoney = 0
            } else {
              this.AllMoney = data.AllMoney
            }
            if (data.AllPrize == null || data.AllPrize == '') {
              this.AllPrize = 0
            } else {
              this.AllPrize = data.AllPrize
            }
            if (data.AllProfitYesterday == null || data.AllProfitYesterday == '') {
              this.AllProfitYesterday = 0
            } else {
              this.AllProfitYesterday = data.AllProfitYesterday
            }
          } else {
            this.dataList = []
            this.totalPage = 0
            this.AllMoney = 0
            this.AllPrize = 0
            this.AllProfitYesterday = 0
          }
          this.dataListLoading = false
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
			// 弹窗出用户账户金额存取记录表
      readUserBalanceRecord (id) {
        this.readUserBalanceRecordVisbel = true
        this.$nextTick(() => {
          this.$refs.userBalanceRecord.init(id)
        })
      },
		// 弹窗出用户账户金额收益记录表
      readUserProfitRecord (id) {
        this.readUserProfitRecordVisbel = true
        this.$nextTick(() => {
          this.$refs.userProfitRecord.init(id)
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
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
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
            url: this.$http.adornUrl('/userbalance/userbalance/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
            }
          })
        })
      }
    }
  }
</script>
