<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
      v-model="dataForm.queryTime"
      type="datetimerange"
      align="right"
      unlink-panels
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
    </el-date-picker>
      </el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.operator" placeholder="操作员" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.operationType" placeholder="操作类型" clearable></el-input>
			</el-form-item>
			
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
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
        prop="memberAccount"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
			<el-table-column
				prop="username"
				header-align="center"
				align="center"
				label="操作员">
			</el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
			<el-table-column
				prop="createDate"
				header-align="center"
				align="center"
				label="操作时间">
			</el-table-column> <el-table-column
        prop="operation"
        header-align="center"
        align="center"
        label="操作类型">
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
  </div>
</template>

<script>
	import dateutil from '@/utils/datechonse'
  export default {
    data () {
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
					},{
						text: '本月',
						onClick(picker) {
							const end = dateutil.getCurrMonthDays().endtime;
							const start = dateutil.getCurrMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '上月',
						onClick(picker) {
							const end = dateutil.getLastMonthDays().endtime;
							const start = dateutil.getLastMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去7天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去30天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					},{
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
					}]
				},
        dataForm: {
					// queryTime: [],
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)],
          userAccount: '',
          operationType: '',
          operator: ''
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
    },
    activated () {
      //this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
				var dateArr=[];
				if(this.dataForm.queryTime!=null){
					dateArr=this.dataForm.queryTime;
				}
				var startDate=dateArr.length>0?dateArr[0]:null;
				var endDate=dateArr.length>1?dateArr[1]:null;
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/userlog/userlog/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
						'direction': "desc",
						'sort': "create_date",
            'userAccount': this.dataForm.userAccount,
            'operationType': this.dataForm.operationType,
            'operator': this.dataForm.operator,
						'endTime': endDate,
            'startTime': startDate,
            
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
    }
  }
</script>
