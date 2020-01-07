<template>
  <div class="mod-config">
		<div style="color: red;height: 36px;line-height: 36px;">风控库存值:游戏预存库存。</div>
		<div style="color: red;height: 36px;line-height: 36px;">风控库存阀值:风控库存上限，往上调整，玩家胜率降低；往下调整，玩家胜率升高。</div>
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()" style="margin-bottom: 10px;">
      <!--<el-form-item>-->
        <!--<el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>-->
      <!--</el-form-item>-->
      <!--<el-form-item>-->
        <el-button @click="getDataList()">刷新</el-button>
				<el-button @click="operationRecord()">操作记录</el-button>
        <!-- <el-button v-if="isAuth('gamestock:gamestock:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
        <!-- <el-button v-if="isAuth('gamestock:gamestock:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      <!--</el-form-item>-->
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
        label="ID">
      </el-table-column>
      <el-table-column
        prop="roomName"
        header-align="center"
        align="center"
        label="风控对象">
      </el-table-column>
      <el-table-column
        prop="stock"
        header-align="center"
        align="center"
				:formatter="formaterStock" 
        label="当前风控库存值">
      </el-table-column>
      <el-table-column
        prop="stockLimit"
        header-align="center"
        align="center"
				:formatter="formaterStockLimit" 
        label="风控库存阀值">
      </el-table-column>
			<el-table-column
				prop="tax"
				header-align="center"
				align="center"
				:formatter="formaterUserRate" 
				label="玩家胜率">
			</el-table-column>
      <el-table-column
        prop="tax"
        header-align="center"
        align="center"
				:formatter="formaterTax" 
        label="运营净利润值">
      </el-table-column>
			<el-table-column
				prop="enable"
				header-align="center"
				align="center"
				label="状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.enable" size="small" >是</el-tag>
					<el-tag v-else size="small" type="danger">否</el-tag>
				</template>
			</el-table-column>
      <el-table-column
        prop="taxRate"
        header-align="center"
        align="center"
				:formatter="formaterTaxRate" 
        label="利润收益比例">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <!-- <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button> -->
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
		<operation-record v-if="operationRecordVisible" ref="operationRecord" @refreshDataList="getDataList"></operation-record>
  </div>
</template>

<script>
  import AddOrUpdate from './gamestock-add-or-update'
	import OperationRecord from '../gamestockoperationrecord/gamestockoperationrecord'
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
        addOrUpdateVisible: false,
				operationRecordVisible: false,
      }
    },
    components: {
      AddOrUpdate,
			OperationRecord
    },
    activated () {
      this.getDataList()
    },
    methods: {
			formaterStock(row, column){
				return (row.stock/100).toFixed(2);
			},
			formaterStockLimit(row, column){
				return (row.stockLimit/100).toFixed(2);
			},
			formaterTax(row, column){
				return (row.tax/100).toFixed(2);
			},
			formaterUserRate(row, column){
				return (row.stock/row.stockLimit*100).toFixed(2)+"%";
			},
			formaterTaxRate(row, column){
				return (row.taxRate*100).toFixed(2)+"%";
			},
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/gamestock/gamestock/list'),
          method: 'post',
          data: this.$http.adornParams({
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
			operationRecord () {
			  this.operationRecordVisible = true
			  this.$nextTick(() => {
			    this.$refs.operationRecord.init()
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
            url: this.$http.adornUrl('/gamestock/gamestock/delete'),
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
