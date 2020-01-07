<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker
        	v-model="time"
        	type="daterange"
        	range-separator="至"
        	start-placeholder="开始日期"
        	end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="getDataList('today')">今天</el-button>
				<el-button @click="getDataList('yesterday')">昨天</el-button>
				<el-button @click="getDataList('week')">近7天</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      
			<el-table-column
        prop="rechargeTypeName"
        header-align="center"
        align="center"
        label="取款类型">
				<template slot-scope="scope">
					{{scope.row.typeName}}
				</template>
      </el-table-column>  
			<el-table-column
				prop="totalAmount"
				header-align="center"
				align="center"
				label="累计总额">
			</el-table-column>
			<el-table-column
        prop="topNum"
        header-align="center"
        align="center"
        label="订单数量">
				<template slot-scope="scope">
					{{scope.row.unconfirmedNum+scope.row.cancelNum+scope.row.confirmedNum}}
				</template>
      </el-table-column>
      <el-table-column
        prop="moneyWaitAmount"
        header-align="center"
        align="center"
        label="待出款">
      </el-table-column>
			<el-table-column
				prop="moneyCancelAmount"
				header-align="center"
				align="center"
				label="取消出款">
			</el-table-column>
      <el-table-column
      	prop="moneySuccessAmount"
      	header-align="center"
      	align="center"
      	label="已出款">
      </el-table-column>
			<el-table-column
				prop="confirmedPoundage"
				header-align="center"
				align="center"
				label="行政费">
			</el-table-column>
			<el-table-column
				prop="obtainAmount"
				header-align="center"
				align="center"
				label="实际出款">
			</el-table-column>
    </el-table>
  </div>
</template>

<script>
	import moment from 'moment';
  export default {
    data () {
      return {
        dataForm: {
          key: '',
					enable:''
        },
				time:'',
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
      this.getDataList()
    },
		created(){
		 this.keyupSubmit()
		},
    methods: {
      // 获取数据列表
      getDataList (time) {
        this.dataListLoading = true
				var startTime = "";
				var endTime = "";
				var timeArr = this.time;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD");
					}
				}
				if ('today' == time) {
					timeArr=[];
					startTime=moment().locale('zh-cn').format('YYYY-MM-DD 00:00:00');
					endTime=moment().locale('zh-cn').format('YYYY-MM-DD HH:mm:ss');
					timeArr.push(startTime);
					timeArr.push(endTime);
					this.time=timeArr;
				} else if ('yesterday' == time) {
					timeArr=[];
					startTime=moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 00:00:00');
					endTime=moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 23:59:59');
					timeArr.push(startTime);
					timeArr.push(endTime);
					this.time=timeArr;
				} else if ('week' == time) {
					timeArr=[];
					startTime=moment().locale('zh-cn').subtract(7, 'days').format('YYYY-MM-DD 00:00:00');
					endTime=moment().locale('zh-cn').subtract(1, 'days').format('YYYY-MM-DD 23:59:59');
					timeArr.push(startTime);
					timeArr.push(endTime);
					this.time=timeArr;
				}
        this.$http({
          url: this.$http.adornUrl('/takemoneystatistics/takemoneystatistics/tableList'),
          method: 'get',
          params: this.$http.adornParams({
            'startTime':startTime,
            'endTime':endTime
						
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.takeMoneyReportList
          } else {
            this.dataList = []
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
     
    }
  }
</script>


<style>
	.el-date-editor .el-range-separator {
    padding: 0 0px;
}
</style>
