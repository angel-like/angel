<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>

        <el-button v-if="isAuth('useragenthierarchy:useragenthierarchy:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>

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
        prop="name"
        header-align="center"
        align="center"
        label="等级名称">
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="是否默认等级">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type" size="small" type="success">是</el-tag>
          <el-tag v-else size="small" type="info">否</el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column
        prop="proportion"
        header-align="center"
        align="center"
        label="返佣比例">
					<template slot-scope="scope">
					{{scope.row.proportion*100}}%
				</template>
      </el-table-column>-->
      <el-table-column
        prop="commission"
        header-align="center"
        align="center"
        label="充值返佣比例">
        <template slot-scope="scope">
          {{scope.row.commission*100}}%
        </template>
      </el-table-column>
      <el-table-column
        prop="coin"
        header-align="center"
        align="center"
        label="金币">
        <template slot-scope="scope">
          {{scope.row.coin/100}}
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.type" type="text" size="small" @click="setDefaultHandle(scope.row.id)">设置默认</el-button>
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './useragenthierarchy-add-or-update'
  // import AgentTeam from './agent-team'
  export default {
    data() {
      return {
        // activeName: 'agent',
        dataForm: {
        },
        visible: false,
        options: [],
        agentId: '',
        userId: '',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        teamVisible:false
      }
    },
    components: {
      AddOrUpdate,
      // AgentTeam
    },
    activated() {
      this.getDataList()
    },
    created(){
      this.keyupSubmit()
    },
    methods: {
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },

      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/useragenthierarchy/useragenthierarchy/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'account': this.dataForm.account
          })
        }).then(({
                   data
                 }) => {
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
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      //修改团队权限滑块触发事件
      updateTeamEnable(userId,teamEnable) {
        this.$http({
          url: this.$http.adornUrl('/agent/agent/updateTeamEnable'),
          method: 'get',
          params: this.$http.adornParams({
            'userId': userId,
            'teamEnable': teamEnable
          })
        }).then(({
                   data
                 }) => {
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
      },
      //取消代理
      cancelAgencyAuthority(id) {
        this.$http({
          url: this.$http.adornUrl(`/agent/agent/AgencyAuthority/` + id),
          method: 'post'
        }).then(({
                   data
                 }) => {
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
      },
      //修改代理代理
      updateAgenHierarchy(id) {
        this.userId = id
        this.visible = true
      },
      handleClick(tab, event) {
        this.getDataList()
        this.$nextTick(() => {
          this.$refs.AgentTeam.getDataList()
        })
      },
      //保存
      dataFormSubmit() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/agent/agent/save'),
          method: 'get',
          params: this.$http.adornParams({
            'userId': this.userId,
            'agentId': this.agentId
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.visible = false
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
          this.dataListLoading = false

        })
      },
      // 获取下线列表
      getSubordinateList(id) {
        this.subordinateVisible = true
        this.$nextTick(() => {
          this.$refs.subordinate.init(id)
        })
      },
      setDefaultHandle (id) {
        this.$http({
          url: this.$http.adornUrl(`/useragenthierarchy/useragenthierarchy/setdefault/${id}`),
          method: 'post',
          data: this.$http.adornData()
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
            url: this.$http.adornUrl('/useragenthierarchy/useragenthierarchy/delete'),
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
