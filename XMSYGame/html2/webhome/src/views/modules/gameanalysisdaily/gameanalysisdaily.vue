<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
     <el-form-item>
     	<el-select  v-model="dataForm.gameId" clearable placeholder="请选择游戏">
     		<el-option
     			v-for="item in gameList"
     			:key="item.id"
     			:label="item.name"
     			:value="item.id">
     		</el-option>
     	</el-select>
      </el-form-item>
			<el-form-item>
				<el-date-picker
					v-model="dataForm.queryTime"
					type="daterange"
					align="right"
					unlink-panels
					range-separator="至"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					:picker-options="pickerOptions2">
				</el-date-picker>
				<!-- <el-date-picker v-model="dataForm.queryTime" type="daterange" range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :clearable=false :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker> -->
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('gameanalysisdaily:gameanalysisdaily:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="roomName"
        header-align="center"
        align="center"
        label="房间">
      </el-table-column>
      <el-table-column
        prop="gameName"
        header-align="center"
        align="center"
        label="游戏">
      </el-table-column>
      <el-table-column
        prop="gradeName"
        header-align="center"
        align="center"
        label="场次">
      </el-table-column>
      <el-table-column
        prop="validBet"
        header-align="center"
        align="center"
        label="有效下注">
      </el-table-column>
      <el-table-column
        prop="prizeCoins"
        header-align="center"
        align="center"
        label="盈亏金币">
      </el-table-column>
      <el-table-column
        prop="userNum"
        header-align="center"
        align="center"
        label="用户数量">
      </el-table-column>
      <el-table-column
        prop="countDay"
        header-align="center"
        align="center"
        label="结算日期">
      </el-table-column>
			<el-table-column
				prop="waterProfit"
				header-align="center"
				align="center"
				label="抽水金额">
			</el-table-column>
      <el-table-column
        prop="waterRate"
        header-align="center"
        align="center"
        label="抽水比例">
      </el-table-column>
      <el-table-column
        prop="robot"
        header-align="center"
        align="center"
        label="是否机器人">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.robot === false" >否</el-tag>
					<el-tag v-else >是</el-tag>
				</template>
      </el-table-column>
    <!--   <el-table-column
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
					  onClick(picker) {
						const end = new Date();
						const start = new Date();
					    picker.$emit('pick', [start, end]);
					  }
					}, {
					  text: '昨天',
					  onClick(picker) {
						const end = new Date();
						const start = new Date();
						end.setTime(end.getTime() - 3600 * 1000 * 24);
					    start.setTime(start.getTime() - 3600 * 1000 * 24);
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
          gameId: '',
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date()]
        },
        dataList: [],
				gameList: [],
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
			this.getSelectList()
      this.getDataList()
		
    },
		//绑定回车事件
		created(){
		 this.keyupSubmit()
		},
    methods: {
			// 获取下拉数据源
			getSelectList() {
				this.$http({
					url: this.$http.adornUrl('/user/gemerecord/selectList'),
					method: 'get',
					params: this.$http.adornParams({})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.gameList = data.gameList
					} else {
						this.gameList = []
					}
				})
			},
      // 获取数据列表
      getDataList () {
				//验证时间是否为空
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD");
					}
				}
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/gameanalysisdaily/gameanalysisdaily/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'gameId': this.dataForm.gameId,
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
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
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
            url: this.$http.adornUrl('/gameanalysisdaily/gameanalysisdaily/delete'),
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
