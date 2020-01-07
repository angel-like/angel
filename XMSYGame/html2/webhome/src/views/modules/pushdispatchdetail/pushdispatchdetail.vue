<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item label="状态">
       <el-select  v-model="dataForm.status" clearable placeholder="请选择状态">
       <el-option
       	v-for="item in statusOption"
       	:key="item.id"
       	:label="item.name"
       	:value="item.id">
       </el-option>
       </el-select>
      </el-form-item>
			<el-form-item label="范围">
				<el-select  v-model="dataForm.scope" clearable placeholder="请选择范围">
				<el-option
				v-for="item in scopeOption"
				:key="item.id"
				:label="item.name"
				:value="item.id">
				</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="类型">
				<el-select  v-model="dataForm.type" clearable placeholder="请选择类型">
				<el-option
				v-for="item in typeOption"
				:key="item.id"
				:label="item.name"
				:value="item.id">
				</el-option>
				</el-select>
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('pushdispatchdetail:pushdispatchdetail:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('pushdispatchdetail:pushdispatchdetail:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="operator"
        header-align="center"
        align="center"
        label="操作人">
      </el-table-column>
      <el-table-column
        prop="recipient"
        header-align="center"
        align="center"
        label="接收者">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
					prop="status"
					header-align="center"
					align="center"
					label="状态">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.status==0"  type="info">未执行</el-tag>
						<el-tag v-else-if="scope.row.status==1"  type="success">成功</el-tag>
						<el-tag v-else  type="danger">失败</el-tag>
					</template>
      </el-table-column>
      <el-table-column
        prop="scope"
        header-align="center"
        align="center"
        label="范围">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.scope==1"  type="info">发送所有用户</el-tag>
					<el-tag v-else-if="scope.row.scope==2"  type="info">指定层级发送</el-tag>
					<el-tag v-else type="info">指定用户发送</el-tag>
				</template>
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="类型">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.type==1"  type="info">立即发送</el-tag>
					<el-tag v-else-if="scope.row.type==2"  type="info">定时发送</el-tag>
					<!-- <el-tag v-else  type="info">循环定时发送</el-tag> -->
				</template>
      </el-table-column>
      <el-table-column
        prop="content"
        header-align="center"
        align="center"
        label="发送内容">
      </el-table-column>
			<el-table-column
				prop="failReason"
				header-align="center"
				align="center"
				label="执行详情">
			</el-table-column>
			<el-table-column
				prop="executeTime"
				header-align="center"
				align="center"
				label="执行时间">
			</el-table-column>
<!--      <el-table-column
        prop="failReason"
        header-align="center"
        align="center"
        label="失败原因">
      </el-table-column> -->
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-if="scope.row.type!=1 && scope.row.status!=1" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
  import AddOrUpdate from './pushdispatchdetail-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          status: -1,
					scope: 0,
					type: 0,
        },
        dataList: [],
				statusOption: [
					{
						id: -1,
						name: "--请选择--"
					},
					{
						id: 0,
						name: "未执行"
					},
					{
						id: 1,
						name: "成功"
					},
					{
						id: 2,
						name: "失败"
					}
				],
				scopeOption: [
					{
						id: 0,
						name: "--请选择--"
					},
					{
						id: 1,
						name: "发送所有用户"
					},
					{
						id: 2,
						name: "指定层级发送"
					},
					{
						id: 3,
						name: "指定用户发送"
					}
				],
				typeOption: [
					{
						id: 0,
						name: "--请选择--"
					},
					{
						id: 1,
						name: "立即发送"
					},
					{
						id: 2,
						name: "定时发送"
					}
// 					,
// 					{
// 						id: 3,
// 						name: "循环定时发送"
// 					}
				],
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
          url: this.$http.adornUrl('/pushdispatchdetail/pushdispatchdetail/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'status': this.dataForm.status==-1?null:this.dataForm.status,
						'type': this.dataForm.type==0?null:this.dataForm.type,
						'scope': this.dataForm.scope==0?null:this.dataForm.scope
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
            url: this.$http.adornUrl('/pushdispatchdetail/pushdispatchdetail/delete'),
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
