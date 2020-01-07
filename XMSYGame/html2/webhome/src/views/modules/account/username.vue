<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
			<el-form-item>
				  <el-select v-model="value" placeholder="请选择状态">
						<el-option
							v-for="item in options"	:key="item.value" :label="item.label"
							:value="item.value">
						</el-option>
					</el-select>
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
        prop="paramValue"
        header-align="center"
        align="center"
        label="编号">
      </el-table-column>
			<el-table-column
				prop="paramValue"
				header-align="center"
				align="center"
				label="用户ID">
			</el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="用户姓名">
      </el-table-column>
			<el-table-column
				prop="remark"
				header-align="center"
				align="center"
				label="修改前姓名">
			</el-table-column> <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="修改后姓名">
      </el-table-column>
			<el-table-column
				prop="remark"
				header-align="center"
				align="center"
				label="申请时间">
			</el-table-column>
			<el-table-column
				prop="remark"
				header-align="center"
				align="center"
				label="状态">
			</el-table-column> 
			<el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="操作者账号">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">通过</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">撤销</el-button>
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
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataForm: {
          paramKey: ''
        },
				options:[{ value: '1',label: '初始状态'},
				{ value: '2',label: '已通过'},
				{ value: '3',label: '已撤销'}],
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
      //this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sys/config/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'paramKey': this.dataForm.paramKey
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
            url: this.$http.adornUrl('/sys/config/delete'),
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
        }).catch(() => {})
      }
    }
  }
</script>
