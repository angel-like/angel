<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('gamerecordkaiyuan:gamerecordkaiyuan:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('gamerecordkaiyuan:gamerecordkaiyuan:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        label="">
      </el-table-column>
      <el-table-column
        prop="gameId"
        header-align="center"
        align="center"
        label="游戏局号列表">
      </el-table-column>
      <el-table-column
        prop="accounts"
        header-align="center"
        align="center"
        label="玩家帐号列表">
      </el-table-column>
      <el-table-column
        prop="serverId"
        header-align="center"
        align="center"
        label="房间 ID 列表">
      </el-table-column>
      <el-table-column
        prop="kindId"
        header-align="center"
        align="center"
        label="游戏 ID 列表">
      </el-table-column>
      <el-table-column
        prop="tableId"
        header-align="center"
        align="center"
        label="桌子号列表">
      </el-table-column>
      <el-table-column
        prop="chairId"
        header-align="center"
        align="center"
        label="椅子号列表">
      </el-table-column>
      <el-table-column
        prop="userCount"
        header-align="center"
        align="center"
        label="玩家数量列表">
      </el-table-column>
      <el-table-column
        prop="cardValue"
        header-align="center"
        align="center"
        label="手牌公共牌">
      </el-table-column>
      <el-table-column
        prop="cellScore"
        header-align="center"
        align="center"
        label="有效下注">
      </el-table-column>
      <el-table-column
        prop="allBet"
        header-align="center"
        align="center"
        label="总下注列表">
      </el-table-column>
      <el-table-column
        prop="profit"
        header-align="center"
        align="center"
        label="盈利列表">
      </el-table-column>
      <el-table-column
        prop="revenue"
        header-align="center"
        align="center"
        label="抽水列表">
      </el-table-column>
      <el-table-column
        prop="gameStartTime"
        header-align="center"
        align="center"
        label="游戏开始时间列表">
      </el-table-column>
      <el-table-column
        prop="gameEndTime"
        header-align="center"
        align="center"
        label="游戏结束时间列表">
      </el-table-column>
      <el-table-column
        prop="channelId"
        header-align="center"
        align="center"
        label="渠道 ID 列表">
      </el-table-column>
      <el-table-column
        prop="lineCode"
        header-align="center"
        align="center"
        label="游戏结果对应玩家所属站点">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
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
  import AddOrUpdate from './gamerecordkaiyuan-add-or-update'
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
          url: this.$http.adornUrl('/gamerecordkaiyuan/gamerecordkaiyuan/list'),
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
            url: this.$http.adornUrl('/gamerecordkaiyuan/gamerecordkaiyuan/delete'),
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
