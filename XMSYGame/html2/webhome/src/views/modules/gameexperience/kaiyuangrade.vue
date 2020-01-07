<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
    	<el-form-item>
    		<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
    		start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2">
    		</el-date-picker>
    	</el-form-item>
    <el-form-item>
    	<el-input v-model="dataForm.userAccount" placeholder="用户账号" clearable></el-input>
    </el-form-item>
    <el-form-item>
    	<el-input v-model="dataForm.gameRoundNo" placeholder="游戏局号" clearable></el-input>
    </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <!-- <el-button v-if="isAuth('gameexperience:kaiyuangrade:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('gameexperience:kaiyuangrade:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
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
        prop="gameStartTime"
        header-align="center"
        align="center"
        label="游戏时间">
      </el-table-column>
      <el-table-column
        prop="gameId"
        header-align="center"
        align="center"
        label="牌局编号">
      </el-table-column>
      <el-table-column
        prop="accounts"
        header-align="center"
        align="center"
        label="用户账号">
      </el-table-column>
      <el-table-column
        prop="gameName"
        header-align="center"
        align="center"
        label="游戏名称">
      </el-table-column>
      <el-table-column
        prop="gradeName"
        header-align="center"
        align="center"
        label="场次名称">
      </el-table-column>
      <el-table-column
        prop="profit"
        header-align="center"
        align="center"
        label="输赢金币">
      </el-table-column>
      <el-table-column
        prop="cardValue"
        header-align="center"
        align="center"
        label="结算牌型">
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
  import AddOrUpdate from './kaiyuangrade-add-or-update'
  import moment from 'moment';

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
          queryTime: [],
          serviceId: '',
          gameId: '',
          gradeId: '',
          name: ''
        },
        gameList:[],
        gradeList:[],
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
      this.getSelectList()
      this.getDataList()
    },
    methods: {
      clearTime(time) {
        if (time != null) {
          this.dataForm.queryTime = [];
        }
      },
      // 获取下拉数据源
      getSelectList() {
        this.dataForm.userAccount= this.$route.query.account
        this.$http({
          url: this.$http.adornUrl('/user/gemerecord/selectList'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.gameList = data.gameList
            this.gradeList = data.gradeList
          } else {
            this.gameList = []
            this.gradeList = []
          }
        })
      },

      // 获取数据列表
      getDataList () {
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
        this.$http({
          url: this.$http.adornUrl('/gamerecordkaiyuan/gamerecordkaiyuan/list'),
          method: 'post',
          data: this.$http.adornData({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'startTime': startDate,
            'endTime': endDate,
           
            'gameId': this.dataForm.gameId,
           
            'account': this.dataForm.userAccount
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
            url: this.$http.adornUrl('/gameexperience/kaiyuangrade/delete'),
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
