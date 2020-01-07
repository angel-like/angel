<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
    	<el-form-item>
    		<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
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
        prop="userAccount"
        header-align="center"
        align="center"
				min-width="5%"
        label="用户账号">
      </el-table-column>
      <el-table-column
        prop="gameName"
        header-align="center"
        align="center"
				min-width="5%"
        label="游戏名称">
      </el-table-column>
      <el-table-column
        prop="gradeName"
        header-align="center"
        align="center"
				min-width="5%"
        label="场次名称">
      </el-table-column>
			<el-table-column
				prop="createTime"
				header-align="center"
				align="center"
				min-width="10%"
				label="时间">
			</el-table-column>
      <el-table-column
        prop="pow"
        header-align="center"
        align="center"
				min-width="5%"
        label="中奖倍数">
      </el-table-column>
     <!-- <el-table-column
        prop="betLines"
        header-align="center"
        align="center"
				min-width="5%"
        label="压线数">
      </el-table-column> -->
      <el-table-column
        prop="lines"
        header-align="center"
        align="center"
				min-width="20%"
        label="中奖线情况">
      </el-table-column>
      <el-table-column
        prop="scenes"
        header-align="center"
        align="center"
				min-width="15%"
        label="图标情况">
      </el-table-column>
      <el-table-column
        prop="bcoins"
        header-align="center"
        align="center"
				min-width="5%"
        label="底分">
				<template slot-scope="scope">
						<div>
								{{scope.row.bcoins/100}}
						</div>
				</template>
      </el-table-column>
			<el-table-column
				prop="betCoins"
				header-align="center"
				align="center"
				min-width="5%"
				label="下注金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.betCoins/100}}
						</div>
				</template>
			</el-table-column>
      <el-table-column
        prop="prizeCoins"
        header-align="center"
        align="center"
				min-width="5%"
        label="输赢金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.prizeCoins/100}}
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="coinsBefore"
        header-align="center"
        align="center"
				min-width="5%"
        label="下注前金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.coinsBefore/100}}
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="coinsAfter"
        header-align="center"
        align="center"
				min-width="5%"
        label="结束后金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.coinsAfter/100}}
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="gameRoundNo"
        header-align="center"
        align="center"
				min-width="10%"
        label="游戏局号">
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
  </div>
</template>

<script>
  import moment from 'moment'
  import dateutil from '@/utils/datechonse'
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
					}, {
						text: '最近三个月',
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
    activated () {
      this.getDataList()
    },
		created(){
		 this.keyupSubmit()
		},
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        var startTime = "";
        var endTime = "";
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
        	startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
        	if (timeArr.length > 1) {
        		endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
        	}
        }
        this.$http({
          url: this.$http.adornUrl('/gamerecordunderseatreasure/gamerecordunderseatreasure/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
						'gameRoundNo': this.dataForm.gameRoundNo,
						'userAccount': this.dataForm.userAccount,
						'startTime': startTime,
						'endTime': endTime,
						'gameId':204,
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
            url: this.$http.adornUrl('/gamerecordunderseatreasure/gamerecordunderseatreasure/delete'),
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
