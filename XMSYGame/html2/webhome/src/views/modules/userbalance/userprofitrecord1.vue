<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="dataForm.queryTime" type="datetimerange" clearable align="right" unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2"
                        value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
      <!--  <el-button v-if="isAuth('userprofitrecord:userprofitrecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('userprofitrecord:userprofitrecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      -->
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
 <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="ID">
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
        label="理财产品">
      </el-table-column>
      <el-table-column
        prop="settlementType"
        header-align="center"
        align="center"
        label="结算类型">
        <template slot-scope="scope">
          <div v-if="scope.row.settlementType==0">循环结算</div>
          <div v-else>一次结算</div>
        </template>
      </el-table-column>


      <el-table-column
        prop="money"
        header-align="center"
        align="center"
        label="金额">
					<template slot-scope="scope">
						<div>{{scope.row.money/100}}</div>
					</template>
      </el-table-column>
      <el-table-column
        prop="rate"
        header-align="center"
				:formatter="formatterRate"
        align="center"
        label="利率%（万元化）">
      </el-table-column>
      <el-table-column
        prop="profit"
        header-align="center"
        align="center"
        label="收益">
				<template slot-scope="scope">
					<div>{{scope.row.profit/100}}</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="incomeDate"
        header-align="center"
        align="center"
        label="收益日期">
      </el-table-column>


  <!--<el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
         <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>-->
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
  </div>
</template>

<script>
  import AddOrUpdate from './userprofitrecord-add-or-update'
  import moment from 'moment';
  import dateutil from '@/utils/datechonse'
  export default {
    data () {
      return {
        typeOptions:[{
          id:0,
          name:'循环结算'
        },{
          id:1,
          name:'一次结算'
        }],
        dataForm: {
          userAccount: '',
          queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime],
        },
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
      AddOrUpdate
    },
    activated () {
      this.dataForm.queryTime = [];
      this.getDataList()
    },
    clearTime(time) {
      if (time != null) {
        this.dataForm.queryTime = [];
      }
    },
		created(){
		 this.keyupSubmit()
		},
    methods: {
			//控制利率
			formatterRate(row,column,cellvalue,index){
				return Number(cellvalue*10000).toFixed(4);
			},
      // 获取数据列表
      getDataList () {
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
          }
        }
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/userprofitrecord/userprofitrecord/getList'),
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
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
				//绑定回车事件
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery(){
				this.pageIndex=1;
				this.getDataList();
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
            url: this.$http.adornUrl('/userprofitrecord/userprofitrecord/delete'),
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
