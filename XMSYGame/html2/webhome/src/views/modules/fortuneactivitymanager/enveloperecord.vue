<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
       <el-button v-if="isAuth('enveloperecord:enveloperecord:list')" type="primary" @click="count()">玩家活动统计</el-button>
        <!--  <el-button v-if="isAuth('enveloperecord:enveloperecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      --> </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <!--<el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>-->
      <el-table-column
        prop="activityName"
        header-align="center"
        align="center"
        label="活动名称">
      </el-table-column>
      <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员id">
      </el-table-column>
      <el-table-column
        prop="userAccount"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
      <el-table-column
        prop="openTime"
        header-align="center"
        align="center"
        label="开启时间">
      </el-table-column>
     <!-- <el-table-column
        prop="openNum"
        header-align="center"
        align="center"
        label="开启数量">
      </el-table-column>-->
      <el-table-column
        prop="receiveCoin"
        header-align="center"
        align="center"
        label="获得的金币">
        <template slot-scope="scope">
          <div v-if="scope.row.receiveCoin!=null">{{scope.row.receiveCoin/100}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="beforeOpenCoin"
        header-align="center"
        align="center"
        label="开启前金币">
        <template slot-scope="scope">
          <div v-if="scope.row.beforeOpenCoin!=null">{{scope.row.beforeOpenCoin/100}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="afterOpenCoin"
        header-align="center"
        align="center"
        label="开启后金币">
        <template slot-scope="scope">
          <div v-if="scope.row.afterOpenCoin!=null">{{scope.row.afterOpenCoin/100}}</div>
        </template>
      </el-table-column>
      <!--<el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="是否开启">
        <template slot-scope="scope">
          <div v-if="scope.row.status==0">否</div>
          <div v-if="scope.row.status==1">是</div>
        </template>
      </el-table-column>-->
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
      </el-table-column>-->
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
    <count v-if="count" ref="count" @refreshDataList="getDataList"></count>
    <!--<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>-->
  </div>
</template>

<script>
  import AddOrUpdate from './enveloperecord-add-or-update'
  import Count from './enveloperecordcount'
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
        addOrUpdateVisible: false,
        countVisible: false
      }
    },
    components: {
      AddOrUpdate,
      Count
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/enveloperecord/enveloperecord/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'sort': 'open_time',
            'direction': false,
            'limit': this.pageSize,
            'status': 1,
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
      // 统计
      count () {
        this.countVisible = true
        this.$nextTick(() => {
          this.$refs.count.init()
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
            url: this.$http.adornUrl('/enveloperecord/enveloperecord/delete'),
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
