<template>
  <div class="mod-config">
    <div style="color: red;padding: 10px 0px;">点击查询前：先输入会员账号（会员账号必填）</div>
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.account" placeholder="要查询的会员账号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.loginStatus" clearable placeholder="登陆状态">
          <el-option
            v-for="item in options"
            :key="item.code"
            :label="item.name"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('userlogin:userlogin:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('userlogin:userlogin:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
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
        prop="account"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
      <el-table-column
        prop="ip"
        header-align="center"
        align="center"
        label="登陆ip">
      </el-table-column>
      <el-table-column
        prop="ipAddress"
        header-align="center"
        align="center"
        label="地理位置">
      </el-table-column>
      <!-- <el-table-column
        prop="deviceCode"
        header-align="center"
        align="center"
        label="机器码">
      </el-table-column> -->
      <!-- <el-table-column
        prop="deviceType"
        header-align="center"
        align="center"
        label="设备类型">
      </el-table-column>
      <el-table-column
        prop="region"
        header-align="center"
        align="center"
        label="地区">
      </el-table-column>
      <el-table-column
        prop="nation"
        header-align="center"
        align="center"
        label="国家">
      </el-table-column>
      <el-table-column
        prop="domain"
        header-align="center"
        align="center"
        label="域名">
      </el-table-column>
      <el-table-column
        prop="edition"
        header-align="center"
        align="center"
        label="版本号">
      </el-table-column> -->
      <!-- <el-table-column
        prop="browser"
        header-align="center"
        align="center"
        label="浏览器版本">
      </el-table-column> -->
      <el-table-column
        prop="loginStatus"
        header-align="center"
        align="center"
        label="登陆状态">
        <template slot-scope="scope">
          <div v-if="scope.row.loginStatus=='success'">
            成功
          </div>
          <div v-if="scope.row.loginStatus=='fail'">
            失败
          </div>
        </template>
      </el-table-column>

      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="登录时间">
      </el-table-column>
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
  import AddOrUpdate from './userlogin-add-or-update'
  import moment from 'moment';
  import dateutil from '@/utils/datechonse'

  export default {
    data() {
      return {
        pickerOptions2: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              const end = dateutil.getToday().endtime;
              const start = dateutil.getToday().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const end = dateutil.getYesterday().endtime;
              const start = dateutil.getYesterday().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本周',
            onClick(picker) {
              const end = dateutil.getCurrWeekDays().endtime;
              const start = dateutil.getCurrWeekDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '上周',
            onClick(picker) {
              const end = dateutil.getLastWeekDays().endtime;
              const start = dateutil.getLastWeekDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本月',
            onClick(picker) {
              const end = dateutil.getCurrMonthDays().endtime;
              const start = dateutil.getCurrMonthDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '上月',
            onClick(picker) {
              const end = dateutil.getLastMonthDays().endtime;
              const start = dateutil.getLastMonthDays().starttime;
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去7天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去30天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去二月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '过去三月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        dataForm: {
          account: '',
          time: [new Date().toLocaleDateString(), new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1)],
          queryTime: [],
          loginStatus: ''
        },
        dataList: [],
        options: [
          {
            "code": "success",
            "name": "登录成功"
          },
          {
            "code": "fail",
            "name": "登录失败"
          },

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
//     activated () {
//       this.getDataList()
//     },
    created() {
      this.keyupSubmit()
    },
    methods: {
      // 获取数据列表
      getDataList() {
        if (this.dataForm.account != null && this.dataForm.account != '') {
          this.dataListLoading = true
          var startTime = "";
          var endTime = "";
          var timeArr = this.dataForm.time;
          if (timeArr != null && timeArr.length > 0) {
            startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
            if (timeArr.length > 1) {
              endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
            }
          }
          this.$http({
            url: this.$http.adornUrl('/userlogin/userlogin/list'),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'limit': this.pageSize,
              'account': this.dataForm.account,
              'startTime': startTime,
              'endTime': endTime,
              'loginStatus': this.dataForm.loginStatus
            })
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.dataList = data.page.list
              this.totalPage = data.page.totalCount
            } else {
              this.dataList = []
              this.totalPage = 0
              this.$message.error(data.msg)
            }
            this.dataListLoading = false

          })
        } else {
          this.$message.error("请输入要查询的会员账号")
        }

      },
      init (){},
      //绑定回车事件
      keyupSubmit() {
        document.onkeydown = e => {
          let _key = window.event.keyCode;
          if (_key === 13) {
            if (this.dataForm.account == null || this.dataForm.account === '') {
              this.dataForm.account='admin'

            }
            this.getDataListQuery()
          }
        }
      },
      //查询
      getDataListQuery() {
        this.pageIndex = 1;
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
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
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
            url: this.$http.adornUrl('/userlogin/userlogin/delete'),
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
