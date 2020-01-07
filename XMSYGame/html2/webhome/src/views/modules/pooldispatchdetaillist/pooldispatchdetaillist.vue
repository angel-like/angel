<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="会员账号" clearable></el-input>
      </el-form-item>
			<el-form-item>
				<el-select  v-model="dataForm.taskId" clearable  placeholder="请选择派奖任务">
				<el-option
					v-for="item in taskList"
					:key="item.id"
					:label="item.title"
					:value="item.id">
				</el-option>
				</el-select>
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <!-- <el-button v-if="isAuth('pooldispatchdetaillist:pooldispatchdetaillist:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
        <!-- <el-button v-if="isAuth('pooldispatchdetaillist:pooldispatchdetaillist:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
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
        label="ID">
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
        prop="taskId"
        header-align="center"
        align="center"
        label="任务id">
      </el-table-column>
      <el-table-column
        prop="taskTitle"
        header-align="center"
        align="center"
        label="派奖任务标题">
      </el-table-column>
      <el-table-column
        prop="taskDetailId"
        header-align="center"
        align="center"
        label="派奖奖项id">
      </el-table-column>
      <el-table-column
        prop="detailTitle"
        header-align="center"
        align="center"
        label="派奖明细标题">
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="金额">
      </el-table-column>
      <el-table-column
        prop="dispatchTime"
        header-align="center"
        align="center"
        label="派发时间">
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
  import AddOrUpdate from './pooldispatchdetaillist-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: '',
					taskId:''
        },
        taskList: [],
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
			this.$http({
				url: this.$http.adornUrl(`/pooldispatchdetaillist/pooldispatchdetaillist/taskList`),
				method: 'get',
				params: this.$http.adornParams()
			}).then(({data}) => {
				if (data && data.code === 200) {
					this.taskList = data.taskList
				}
			});
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
          url: this.$http.adornUrl('/pooldispatchdetaillist/pooldispatchdetaillist/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'taskId': this.dataForm.taskId
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
            url: this.$http.adornUrl('/pooldispatchdetaillist/pooldispatchdetaillist/delete'),
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
