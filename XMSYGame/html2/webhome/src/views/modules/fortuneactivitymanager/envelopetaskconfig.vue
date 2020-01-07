<template>
  <el-dialog title="配置事件" :close-on-click-modal="false" :visible.sync="visible">
    <div class="mod-config">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-select v-model="dataForm.eventCode" placeholder="事件 " clearable>
            <el-option
              v-for="item in options"
              :key="item.code"
              :label="item.name"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
          <el-button v-if="isAuth('envelopetaskconfig:envelopetaskconfig:save')" type="primary"
                     @click="addOrUpdateHandle()">新增
          </el-button>
        <!--  <el-button v-if="isAuth('envelopetaskconfig:envelopetaskconfig:delete')" type="danger" @click="deleteHandle()"
                     :disabled="dataListSelections.length <= 0">批量删除
          </el-button>-->
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
          prop="id"
          header-align="center"
          align="center"
          label="ID">
        </el-table-column>
        <el-table-column
          prop="eventName"
          header-align="center"
          align="center"
          label="事件名称">
        </el-table-column>
        <el-table-column
          prop="eventParam"
          header-align="center"
          align="center"
          label="事件参数值">
          <template slot-scope="scope">
            <div v-if="scope.row.eventCode.indexOf('coin')>-1">{{scope.row.eventParam/100}}</div>
            <div v-if="scope.row.eventCode.indexOf('coin')<=-1">{{scope.row.eventParam}}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="rewards"
          header-align="center"
          align="center"
          label="奖励数量">
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
      <edit-event v-if="editEventVisible" ref="editEvent" @refreshDataList="getDataList"></edit-event>
    </div>
  </el-dialog>
</template>

<script>
  import EditEvent from './envelopetaskconfig-add-or-update'

  export default {
    data() {
      return {
        dataForm: {
          eventCode: ''
        },
        options: [],
        visible: false,
        activityId: '',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        editEventVisible: false
      }
    },
    components: {
      EditEvent
    },
    activated() {
      this.getDataList()
    },
    methods: {
      init(id) {
        this.activityId = id
        this.visible = true
        this.getDataList()
        //为任务下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "fortuneEvent"),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.options = data.data
          }
        })
      },
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/envelopetaskconfig/envelopetaskconfig/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'eventCode': this.dataForm.eventCode,
            'activityId': this.activityId
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
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.editEventVisible = true
        this.$nextTick(() => {
          this.$refs.editEvent.init(id,this.activityId)
        })
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/envelopetaskconfig/envelopetaskconfig/delete'),
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
