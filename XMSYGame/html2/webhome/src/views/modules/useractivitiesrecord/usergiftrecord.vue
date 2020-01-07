<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <!-- <el-button v-if="isAuth('usergiftrecord:usergiftrecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('usergiftrecord:usergiftrecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
      	type="index"
      	width="120"
      	header-align="center"
      	align="center"
      	label="序号">
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="活动类型">
				<template slot-scope="scope">
					<div v-if="scope.row.type==1">
						奖励金
					</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="detailTypeName"
        header-align="center"
        align="center"
        label="具体类型">
      </el-table-column>
      <el-table-column
        prop="num"
        header-align="center"
        align="center"
        label="数量">
				<template slot-scope="scope">
					<div v-if="scope.row.type == 1 && scope.row.num !=null">
						{{scope.row.num/100}}
					</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="propName"
        header-align="center"
        align="center"
        label="道具名称">
      </el-table-column>
      <el-table-column
        prop="sendType"
        header-align="center"
        align="center"
        label="发送模式">
				<template slot-scope="scope">
					<div v-if="scope.row.sendType==0">
						后台发放
					</div>
					<div v-if="scope.row.sendType==1">
						客户领取
					</div>
				</template>
      </el-table-column>
      <!-- <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员id">
      </el-table-column> -->
      <el-table-column
        prop="userAccount"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
      <el-table-column
        prop="receive"
        header-align="center"
        align="center"
        label="是否领取">
				<template slot-scope="scope">
					<div v-if="scope.row.receive==1">
						是
					</div>
					<div v-if="scope.row.receive==0">
						否
					</div>
				</template>
      </el-table-column>
    <!--  <el-table-column
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
  import AddOrUpdate from './usergiftrecord-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          userAccount: ''
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
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/usergiftrecord/usergiftrecord/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userAccount': this.dataForm.userAccount
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
            url: this.$http.adornUrl('/usergiftrecord/usergiftrecord/delete'),
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
