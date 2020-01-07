<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('user:user:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('user:user:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="account"
        header-align="center"
        align="center"
        label="账号名称">
      </el-table-column>
      <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        label="用户姓名">
      </el-table-column>
      <el-table-column
        prop="userIdentity"
        header-align="center"
        align="center"
        label="身份证号码">
      </el-table-column>
      <el-table-column
        prop="userPhone"
        header-align="center"
        align="center"
        label="电话">
      </el-table-column>
      <el-table-column
        prop="userEmail"
        header-align="center"
        align="center"
        label="电子邮箱">
      </el-table-column>
      <el-table-column
        prop="postCode"
        header-align="center"
        align="center"
        label="邮编">
      </el-table-column>
      <el-table-column
        prop="userAddress"
        header-align="center"
        align="center"
        label="地址">
      </el-table-column>
      <el-table-column
        prop="userQq"
        header-align="center"
        align="center"
        label="QQ">
      </el-table-column>
      <el-table-column
        prop="userBirthday"
        header-align="center"
        align="center"
        label="生日">
      </el-table-column>
      <el-table-column
        prop="bankName"
        header-align="center"
        align="center"
        label="银行名称">
      </el-table-column>
      <el-table-column
        prop="bankAddress"
        header-align="center"
        align="center"
        label="银行开户地址">
      </el-table-column>
      <el-table-column
        prop="userType"
        header-align="center"
        align="center"
        label="账号类型（试玩账号，普通会员，vip）">
      </el-table-column>
      <el-table-column
        prop="token"
        header-align="center"
        align="center"
        label="token验证Id">
      </el-table-column>
      <el-table-column
        prop="registerIp"
        header-align="center"
        align="center"
        label="用户注册IP">
      </el-table-column>
      <el-table-column
        prop="registerDeviceCode"
        header-align="center"
        align="center"
        label="注册设备码">
      </el-table-column>
      <el-table-column
        prop="unionId"
        header-align="center"
        align="center"
        label="第三方ID">
      </el-table-column>
      <el-table-column
        prop="superiorsId"
        header-align="center"
        align="center"
        label="上级ID">
      </el-table-column>
      <el-table-column
        prop="enable"
        header-align="center"
        align="center"
        label="账号状态（停押，停用，冻结）">
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
  import AddOrUpdate from './user-add-or-update'
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
          url: this.$http.adornUrl('/user/user/list'),
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
            url: this.$http.adornUrl('/user/user/delete'),
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
