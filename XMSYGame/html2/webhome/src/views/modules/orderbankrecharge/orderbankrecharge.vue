<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
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
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="存款人">
      </el-table-column>
      <el-table-column
        prop="depositDate"
        header-align="center"
        align="center"
        label="存款时间">
      </el-table-column>
      <el-table-column
        prop="depositAmount"
        header-align="center"
        align="center"
        label="存款金额">
      </el-table-column>
      <el-table-column
        prop="depositBlank"
        header-align="center"
        align="center"
        label="存款银行">
      </el-table-column>
      <el-table-column
        prop="incomeBank"
        header-align="center"
        align="center"
        label="收款银行">
      </el-table-column>
      <el-table-column
        prop="bankAccount"
        header-align="center"
        align="center"
        label="收款账号">
      </el-table-column>
      <el-table-column
        prop="payee"
        header-align="center"
        align="center"
        label="收款人">
      </el-table-column>
      <el-table-column
        prop="openBank"
        header-align="center"
        align="center"
        label="开户网点">
      </el-table-column>
      <el-table-column
        prop="depositType"
        header-align="center"
        align="center"
        label="存款方式">
				<template slot-scope="scope">
						<div v-if="scope.row.depositType==1">
							银行转账
						</div>
						<div v-if="scope.row.depositType==2">
							网银转账
						</div>
						<div v-if="scope.row.depositType==3">
							第三方转账
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="account"
        header-align="center"
        align="center"
        label="充值账号">
      </el-table-column>
      <el-table-column
        prop="discount"
        header-align="center"
        align="center"
        label="优惠金额">
      </el-table-column>
      <el-table-column
        prop="rechargeDate"
        header-align="center"
        align="center"
        label="充值时间">
      </el-table-column>
			<el-table-column
				prop="status"
				header-align="center"
				align="center"
				label="订单状态">
				<template slot-scope="scope">
						<div v-if="scope.row.status=='confirmed'">
							已确认
						</div>
						<div v-if="scope.row.status=='unConfirmed'">
							待确认
						</div>
				</template>
			</el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="confirmedHandle(scope.row.id)">确认订单</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './orderbankrecharge-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
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
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/orderbankrecharge/orderbankrecharge/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
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
      // 确认订单
      confirmedHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '确认订单' : '批量确认'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/orderbankrecharge/orderbankrecharge/confirmed'),
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
