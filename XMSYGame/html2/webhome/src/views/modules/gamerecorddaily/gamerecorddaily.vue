<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userId" placeholder="会员id" clearable></el-input>
      </el-form-item>
			<el-form-item>
			  <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <!-- <el-button v-if="isAuth('gamerecorddaily:gamerecorddaily:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
        <!-- <el-button v-if="isAuth('gamerecorddaily:gamerecorddaily:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
      <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员ID">
      </el-table-column>
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
        label="会员盈亏金币">
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
     <!-- <el-table-column
        prop="profitCoins"
        header-align="center"
        align="center"
        label="代理商盈利金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.profitCoins/100}}
						</div>
				</template>
      </el-table-column> -->
      <el-table-column
        prop="countDay"
        header-align="center"
        align="center"
        label="结算日期">
      </el-table-column>
      <el-table-column
        prop="userWaterProfit"
        header-align="center"
        align="center"
				:formatter="formatterColumn"
        label="会员返水">
			<!-- 	<template slot-scope="scope">
						<div>
								{{scope.row.userWaterProfit/100}}
						</div>
				</template> -->
      </el-table-column>
      <el-table-column
        prop="userWaterRate"
        header-align="center"
        align="center"
        label="会员返水比例">
      </el-table-column>
      <el-table-column
        prop="userReturn"
        header-align="center"
        align="center"
        label="是否计算返水">
				<template slot-scope="scope">
						<div v-if="scope.row.userReturn">
								是
						</div>
						<div v-else>
							否
						</div>
				</template>
      </el-table-column>
      <!-- <el-table-column
        prop="agentReturn"
        header-align="center"
        align="center"
        label="代理商是否返佣">
				<template slot-scope="scope">
						<div v-if="scope.row.agentReturn">
								是
						</div>
						<div v-else>
							否
						</div>
				</template>
      </el-table-column> -->
      <el-table-column
        prop="abnormal"
        header-align="center"
        align="center"
        label="会员返水是否异常">
					<template slot-scope="scope">
						<div v-if="scope.row.abnormal">
								是
						</div>
						<div v-else>
							否
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="abnormalContent"
        header-align="center"
        align="center"
        label="会员返水异常内容">
      </el-table-column>
      <!-- <el-table-column
        prop="agentAbnormal"
        header-align="center"
        align="center"
        label="代理商返佣是否异常">
				<template slot-scope="scope">
						<div v-if="scope.row.agentAbnormal">
								是
						</div>
						<div v-else>
							否
						</div>
				</template>
      </el-table-column> -->
     <!-- <el-table-column
        prop="agentAbnormalContent"
        header-align="center"
        align="center"
        label="代理商返佣异常内容">
      </el-table-column> -->
     <!-- <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column> -->
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
  export default {
    data () {
      return {
        dataForm: {
          userAccount: '',
					userId: '',
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
        this.$http({
          url: this.$http.adornUrl('/gamerecorddaily/gamerecorddaily/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'sort': 'create_time',
            'direction': false,
            'userAccount': this.dataForm.userAccount,
						'userId': this.dataForm.userId,
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
