<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="用户账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.gameRoundNo" placeholder="游戏局号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
             type="selection"
             header-align="center"
             align="center"
             width="50">
           </el-table-column>
           <!-- <el-table-column
             type="index"
             width="50">
           </el-table-column> -->
           <!--<el-table-column
             prop="id"
             header-align="center"
             align="center"
             label="">
           </el-table-column>-->
           <el-table-column
             prop="gameId"
             header-align="center"
             align="center"
             label="游戏局号">
           </el-table-column>
           <el-table-column
             prop="accounts"
             header-align="center"
             align="center"
             label="玩家帐号">
           </el-table-column>
           <el-table-column
             prop="serverId"
             header-align="center"
             align="center"
             label="房间 ID ">
           </el-table-column>
           <el-table-column
             prop="kindId"
             header-align="center"
             align="center"
             label="游戏 ID ">
           </el-table-column>
           <el-table-column
             prop="tableId"
             header-align="center"
             align="center"
             label="桌子号">
           </el-table-column>
           <el-table-column
             prop="chairId"
             header-align="center"
             align="center"
             label="椅子号">
           </el-table-column>
           <el-table-column
             prop="userCount"
             header-align="center"
             align="center"
             label="玩家数量">
           </el-table-column>
           <el-table-column
             prop="cardValue"
             header-align="center"
             align="center"
             label="手牌公共牌">
           </el-table-column>
           <el-table-column
             prop="cellScore"
             header-align="center"
             align="center"
             label="有效下注">
             <template slot-scope="scope">
             	<div>
             			{{scope.row.cellScore/100}}
             	</div>
             </template>
           </el-table-column>
           <el-table-column
             prop="allBet"
             header-align="center"
             align="center"
             label="总下注">
             <template slot-scope="scope">
             	<div>
             			{{scope.row.allBet/100}}
             	</div>
             </template>
           </el-table-column>
           <el-table-column
             prop="profit"
             header-align="center"
             align="center"
             label="盈利">
             <template slot-scope="scope">
             	<div>
             			{{scope.row.profit/100}}
             	</div>
             </template>
           </el-table-column>
           <el-table-column
             prop="revenue"
             header-align="center"
             align="center"
             label="抽水">
             <template slot-scope="scope">
             	<div>
             			{{scope.row.revenue/100}}
             	</div>
             </template>
           </el-table-column>
           <el-table-column
             prop="gameStartTime"
             header-align="center"
             align="center"
             label="游戏开始时间">
           </el-table-column>
           <el-table-column
             prop="gameEndTime"
             header-align="center"
             align="center"
             label="游戏结束时间">
           </el-table-column>
           <el-table-column
             fixed="right"
             header-align="center"
             align="center"
             width="150"
             label="操作">
             <template slot-scope="scope">
               <!-- <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button> -->
               <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
             </template>
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
    <add-or-update v-if="addOrUpdateVisible" ref=" " @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import moment from 'moment';
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
        dataForm: {
          userAccount: '',
          gameName: '',
          gameRoundNo: '',
          queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime]
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
   activated() {
     this.getDataList()
   },
   created() {
     this.keyupSubmit()
   },
   methods: {
     // 获取数据列表
     getDataList () {
   		var startTime = "";
   		var endTime = "";
   		var timeArr = this.dataForm.queryTime;
   		if (timeArr != null && timeArr.length > 0) {
   		  startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
   		  if (timeArr.length > 1) {
   		    endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
   		  }
   		}
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/gamerecordkaiyuan/gamerecordkaiyuan/list/bjl'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'sort': 'create_time',
            'direction': false,
            'gameId': this.dataForm.gameRoundNo,
            'accounts': this.dataForm.userAccount,
            'startTime': startTime,
            'endTime': endTime
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
            url: this.$http.adornUrl('/gamerecordkaiyuan/gamerecordkaiyuan/delete'),
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
	.el-table-column-css {
  white-space: pre-line;
}
</style>
