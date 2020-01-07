<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="会员姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.productName" placeholder="理财产品" clearable></el-input>
      </el-form-item>
      <el-select v-model="dataForm.settlementType" clearable placeholder="结算类型">
       	<el-option v-for="item in settlementTypeOptions" :key="item.id" :label="item.name" :value="item.id">
       	</el-option>
      </el-select>
			<el-select v-model="dataForm.type" clearable placeholder="存取类型">
			 	<el-option v-for="item in typeOptions" :key="item.id" :label="item.name" :value="item.id">
			 	</el-option>
			</el-select>
      <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
         start-placeholder="开始时间" end-placeholder="结束时间" :picker-options="pickerOptions2"
                        value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
      </el-date-picker>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
		<!--		<el-button v-if="isAuth('userbalancerecord:userbalancerecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('userbalancerecord:userbalancerecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        label="会员姓名">
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
        prop="rate"
        header-align="center"
        align="center"
        label="利率">
      </el-table-column>
      <el-table-column
        prop="money"
        header-align="center"
        align="center"
        label="交易金额">
				<template slot-scope="scope">
					<div>{{scope.row.money/100}}</div>
				</template>
      </el-table-column>
			<!--
      <el-table-column
        prop="takeMoney"
        header-align="center"
        align="center"
        label="取出金额">
					<template slot-scope="scope">
						<div>{{scope.row.takeMoney/100}}</div>
					</template>
      </el-table-column>
      <el-table-column
        prop="effect"
        header-align="center"
        align="center"
        label="是否生效">
				<template slot-scope="scope">
					<div v-if="scope.row.effect">是</div>
					<div v-else>否</div>
				</template>
      </el-table-column>-->
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="存取类型">
				<template slot-scope="scope">
						<div v-if="scope.row.type==0">存入</div>
						<div v-else>取出</div>
				</template>
      </el-table-column>
			<el-table-column
			  prop="createTime"
			  header-align="center"
			  align="center"
			  label="存取时间">
			</el-table-column>
     <!--  <el-table-column
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
  import AddOrUpdate from './userbalancerecord-add-or-update'
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
        settlementTypeOptions: [{
          id: 0,
          name: '循环结算'
        }, {
          id: 1,
          name: '一次结算'
        }],
        typeOptions: [{
          id: 0,
          name: '存入'
        }, {
          id: 1,
          name: '取出'
        }],
        dataForm: {
          userAccount: '',
          queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime],
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
      this.getDataList()
    },
  created () {
		 this.keyupSubmit()
},
    methods: {
      // 获取数据列表
      getDataList () {
        var sTime = null
        var eTime = null
        var timeArr = this.dataForm.queryTime
        if (timeArr != null && timeArr.length > 0) {
        	sTime = timeArr[0]
      	if (timeArr.length > 1) {
        		eTime = timeArr[1]
      	}
        }
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/userbalancerecord/userbalancerecord/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userAccount': this.dataForm.userAccount,
            'type': this.dataForm.type,
            'userName': this.dataForm.userName,
            'productName': this.dataForm.productName,
            'settlementType': this.dataForm.settlementType,
            'rate': this.dataForm.rate,
            'sTime': sTime,
            'eTime': eTime
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
            url: this.$http.adornUrl('/userbalancerecord/userbalancerecord/delete'),
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
