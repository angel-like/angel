<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账户" clearable></el-input>
			</el-form-item>
			<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
			 start-placeholder="开始时间" end-placeholder="结束时间" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
			</el-date-picker>


    	<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="index" width="120" header-align="center" align="center" label="序号">
			</el-table-column>
			<!-- <el-table-column prop="userId" header-align="center" align="center" label="兑换会员id">
			</el-table-column> -->
			<el-table-column prop="userAccount" header-align="center" align="center" label="会员账户">
			</el-table-column>
			<el-table-column prop="userName" header-align="center" align="center" label="会员名称">
			</el-table-column>
			<el-table-column prop="exchangeCode" header-align="center" align="center" label="兑换码">
			</el-table-column>
			<el-table-column prop="exchangeTime" header-align="center" align="center" label="兑换时间">
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
	</div>
</template>

<script>
	import moment from 'moment'
export default {
	  data () {
    return {
	      pickerOptions2: {
	        shortcuts: [{
	          text: '最近一周',
	          onClick (picker) {
	            const end = new Date()
	            const start = new Date()
	            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
	            picker.$emit('pick', [start, end])
	          }
        }, {
	          text: '最近一个月',
	          onClick (picker) {
	            const end = new Date()
	            const start = new Date()
	            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
	            picker.$emit('pick', [start, end])
	          }
        }, {
	          text: '最近三个月',
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
        time: [new Date().toLocaleDateString(), new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1)],

        queryTime: [new Date().toLocaleDateString(), new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1)]

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
	    this.getDataList()
  },
	  created () {
		 this.keyupSubmit()
  },
	  methods: {
			// 获取数据列表
	    getDataList () {
	      var startDate = null
	      var endDate = null
	      var timeArr = this.dataForm.queryTime
	      if (timeArr != null && timeArr.length > 0) {
	        startDate = moment(timeArr[0]).format('YYYY-MM-DD HH:mm:ss')
	        if (timeArr.length > 1) {
	          endDate = moment(timeArr[1]).format('YYYY-MM-DD HH:mm:ss')
        }
      }
	      this.dataListLoading = true
	      this.$http({
	        url: this.$http.adornUrl('/giftbagexchangerecord/giftbagexchangerecord/list'),
	        method: 'get',
	        params: this.$http.adornParams({
	          'page': this.pageIndex,
	          'limit': this.pageSize,
	          'userAccount': this.dataForm.userAccount,
	          'sTime': startDate,
	          'eTime': endDate
        })
      }).then(({
					data
				}) => {
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
    }
  }
	}
</script>
