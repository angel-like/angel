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
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('poolgame:poolgame:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <!-- <el-button v-if="isAuth('poolgame:poolgame:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
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
				prop="gameId"
				header-align="center"
				align="center"
				label="游戏id">
			</el-table-column>
			<el-table-column
				prop="gameName"
				header-align="center"
				align="center"
				label="游戏名称">
				<!-- <template slot-scope="scope">
					<el-select  v-model="scope.row.gameId" disabled>
					<el-option
						v-for="item in gameList"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
					</el-select>
				</template> -->
			</el-table-column>
      <el-table-column
        prop="pool"
        header-align="center"
        align="center"
        label="奖金池金额">
      </el-table-column>
      <el-table-column
        prop="enable"
        header-align="center"
        align="center"
        label="状态">
				<template slot-scope="scope">
						<div v-if="scope.row.enable">
							启用
						</div>
						<div v-else>
							禁用
						</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="betRate"
        header-align="center"
        align="center"
        label="下注比例">
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
  </div>
</template>

<script>
  import AddOrUpdate from './poolgame-add-or-update'
  export default {
    data () {
      return {
				gameList:[],
        dataForm: {
          gameId: null
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
		created(){
		 this.keyupSubmit()
		},
    activated () {
			this.getGameList();
      this.getDataList();
    },
    methods: {
			getGameList(){
				this.$http({
					url: this.$http.adornUrl(`/poolgame/poolgame/gameList`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.gameList = data.gameList
					}
				})
			},
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/poolgame/poolgame/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'gameId': this.dataForm.gameId
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
            url: this.$http.adornUrl('/poolgame/poolgame/delete'),
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
