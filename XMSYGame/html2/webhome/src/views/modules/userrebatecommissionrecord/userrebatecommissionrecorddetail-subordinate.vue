<template>
  <el-dialog
    title="代理下线列表"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="75%">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.account" placeholder="账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      height="460"
      style="width: 100%;">
      <el-table-column
      	prop="id"
      	header-align="center"
      	align="center"
      	label="id">
      </el-table-column>
      <el-table-column
      	prop="account"
      	header-align="center"
      	align="center"
      	label="账号名称">
		<template slot-scope="scope">
			<div v-if="scope.row.account!=null&&scope.row.account!=''">
				<a type="text" size="small" style="cursor:pointer "  @click="getList(scope.row.account)"><u>{{scope.row.account}}</u></a>
			</div>
			<div v-else>
				{{scope.row.account}}
			</div>
		</template>
      </el-table-column>
			<el-table-column
				prop="validBet"
				header-align="center"
				align="center"
				label="打码量">
				<template slot-scope="scope">
					{{scope.row.validBet/100}}
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
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
				id: 0,
				userAccount:'',
        dataForm: {
          account: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false
      }
    },
    methods: {
      init (id,userAccount) {
        this.visible = true
				this.id = id
				this.userAccount=userAccount
        this.getDataList(id)
      },
      // 获取数据列表
      getDataList (id) {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/agent/agent/SubordinateListForCommission?id='+this.id),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'account': this.dataForm.account
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
	  getList(account){
		  this.visible=false
		  this.$parent.addList(account,this.userAccount)
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
      }
    }
  }
</script>
