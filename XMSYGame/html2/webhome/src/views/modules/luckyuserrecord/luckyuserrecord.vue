<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员帐号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.luckyName" clearable placeholder="转盘名称">
          <el-option
            v-for="item in luckyOptions"
            :key="item.name"
            :label="item.name"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
                      start-placeholder="开始时间" end-placeholder="结束时间" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
      </el-date-picker>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('luckyuserrecord:luckyuserrecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="userAccount"
        header-align="center"
        align="center"
        label="用户账号">
      </el-table-column>
      <el-table-column
        prop="luckyName"
        header-align="center"
        align="center"
        label="转盘名称">
      </el-table-column>
      <el-table-column
        prop="propName"
        header-align="center"
        align="center"
        label="奖励名称">
      </el-table-column>
      <el-table-column
        prop="propNum"
        header-align="center"
        align="center"
        label="奖励数量">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="中奖时间">
      </el-table-column>
      <el-table-column
        prop="grand"
        header-align="center"
        align="center"
        label="是否大奖">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.grand" size="small" >是</el-tag>
          <el-tag v-else size="small" type="danger">否</el-tag>
        </template>
      </el-table-column>
<!--
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import moment from 'moment';
  import AddOrUpdate from './luckyuserrecord-add-or-update'
  export default {
    data () {
      return {
        pickerOptions2: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        dataForm: {
          userAccount: ''
        },
        luckyOptions:[
          {
            name: '白银转盘'
          }
          ,{
            name: '钻石转盘'
          },{
            name: '黄金转盘'
          }
        ],
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
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
          }
        }
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/luckyuserrecord/luckyuserrecord/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'userAccount': this.dataForm.userAccount,
            'luckyName':this.dataForm.luckyName,
            'sTime': startDate,
            'eTime': endDate
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
            url: this.$http.adornUrl('/luckyuserrecord/luckyuserrecord/delete'),
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
