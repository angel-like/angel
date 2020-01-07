<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="daterange" align="right" unlink-panels range-separator="至"
				start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
				</el-date-picker>
			</el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="用户账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <!-- <el-button v-if="isAuth('gamerecorddaily:gamerecorddaily:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
        <!-- <el-button v-if="isAuth('gamerecorddaily:gamerecorddaily:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
    </el-form>
		<div style="color: red;padding-bottom: 10px;">抽水总计（元）：{{waterProfitSum/100}}</div>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
				type="index"
				label="id"
				width="100"
				align="center">
      </el-table-column>
      <!-- <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="用户ID">
      </el-table-column> -->
      <el-table-column
        prop="userAccount"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
      <el-table-column
        prop="validBet"
        header-align="center"
        align="center"
        label="有效下注">
				<template slot-scope="scope">
						<div>
								{{scope.row.validBet/100}}
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="prizeCoins"
        header-align="center"
        align="center"
        label="盈亏金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.prizeCoins/100}}
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="waterProfit"
        header-align="center"
        align="center"
        label="抽水金额">
				<template slot-scope="scope">
						<div>
								{{scope.row.waterProfit/100}}
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="userWaterProfit"
        header-align="center"
        align="center"
				:formatter="formatterColumn"
        label="会员返水">
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
  import AddOrUpdate from './gamerecorddaily-add-or-update'
	import moment from 'moment';
  export default {
    data () {
      return {
				pickerOptions2: {
  			shortcuts: [{
  				text: '最近一周',
  				onClick(picker) {
  					const end = new Date();
  					const start = new Date();
  					start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
  					picker.$emit('pick', [start, end]);
  				}
  			}, {
  				text: '最近一个月',
  				onClick(picker) {
  					const end = new Date();
  					const start = new Date();
  					start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
  					picker.$emit('pick', [start, end]);
  				}
  			}],
				onPick: ({ maxDate, minDate }) => {
					this.pickerMinDate = minDate.getTime()
					if (maxDate) {
					  this.pickerMinDate = ''
					}
				  },
				  disabledDate: (time) => {
					if (this.pickerMinDate !== '') {
					  const day30 = (31 - 1) * 24 * 3600 * 1000
					  let maxTime = this.pickerMinDate + day30
					  let minTime = this.pickerMinDate - day30
					  if (maxTime > new Date()) {
						maxTime = new Date()
					  }
					  return time.getTime() > maxTime ||  time.getTime() < minTime
					}
					return time.getTime() > Date.now()
				  }
  		},
        dataForm: {
          userAccount: '',
					queryTime: [],
        },
        dataList: [],
				waterProfitSum:'',
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
		created(){
		 this.keyupSubmit()
		},
    methods: {
			formatterColumn(row, column,val){
				return (val/100).toFixed(2);
			},
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD");
					}
				}
        this.$http({
          url: this.$http.adornUrl('/gamerecorddaily/gamerecorddaily/userPumpList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'sort': 'create_time',
            'direction': false,
            'userAccount': this.dataForm.userAccount,
						'startTime': startTime,
						'endTime': endTime
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
						this.waterProfitSum = data.waterProfitSum
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
            url: this.$http.adornUrl('/gamerecorddaily/gamerecorddaily/delete'),
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


<style>
	.el-date-editor .el-range-separator {
    padding: 0 0px;
}
</style>
