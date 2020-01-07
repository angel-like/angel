<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.payName" placeholder="公司名称" clearable></el-input>
      </el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.enable" placeholder="请选择状态" clearable>
					<el-option
						v-for="item in enableOptions"
						:key="item.name"
						:label="item.label"
						:value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('payconfig:payconfig:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('payconfig:payconfig:delete')" type="danger" @click="deleteHandle()"
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
        prop="payName"
        header-align="center"
        align="center"
        label="公司名称">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="显示名称">
      </el-table-column>
      <el-table-column
        prop="aliasName"
        header-align="center"
        align="center"
        label="别名">
      </el-table-column>
      <el-table-column
        prop="paymentMethodName"
        header-align="center"
        align="center"
        label="支付渠道">
      </el-table-column>
      <el-table-column
        prop="enable"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <div v-if="scope.row.enable=='1'">
            启用
          </div>
          <div v-if="scope.row.enable=='0'">
            禁用
          </div>
        </template>
      </el-table-column>
      <!-- <el-table-column
                prop="firstPush"
                header-align="center"
                align="center"
                label="是否首推">
                <template slot-scope="scope">
                    <div v-if="scope.row.firstPush=='1'">
                        是
                    </div>
                    <div v-if="scope.row.firstPush=='0'">
                        否
                    </div>
                </template>
            </el-table-column> -->
     <!-- <el-table-column
        prop="appIconId"
        header-align="center"
        align="center"
        label="APP图标ID">
        <template slot-scope="scope">
          <div v-if="scope.row.appIconId!=null">
            <el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.appIconId)">
              预览图片
            </el-button>
          </div>
        </template>
      </el-table-column>-->
     <!-- <el-table-column
        prop="appIconMd5"
        header-align="center"
        align="center"
        label="appMD5">
      </el-table-column>-->
     <!-- <el-table-column
        prop="webIconId"
        header-align="center"
        align="center"
        label="web端图标">
        <template slot-scope="scope">
          <div v-if="scope.row.webIconId!=null">
            <el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.webIconId)">
              预览图片
            </el-button>
          </div>
        </template>
      </el-table-column>-->
     <!-- <el-table-column
        prop="webIconMd5"
        header-align="center"
        align="center"
        label="webMD5">
      </el-table-column>-->
      <!--<el-table-column
        prop="enclosureId"
        header-align="center"
        align="center"
        label="附件">
        <template slot-scope="scope">
          <div v-if="scope.row.enclosureId!=null">
            <el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.enclosureId)">
              预览图片
            </el-button>
          </div>
        </template>
      </el-table-column>-->
     <!-- <el-table-column
        prop="enclosureMd5"
        header-align="center"
        align="center"
        label="MD5">
      </el-table-column>-->
      <el-table-column
        prop="orderNum"
        header-align="center"
        align="center"
        label="排序">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <!-- <el-button type="text" v-if="scope.row.firstPush=='0'" size="small" @click="firstPush(scope.row.id,scope.row.name)">设为首推</el-button> -->
          <el-button type="text" v-if="scope.row.enable=='0'" size="small" @click="enable(scope.row.id,scope.row.name)">
            启用
          </el-button>
          <el-button type="text" v-if="scope.row.enable=='1'" size="small"
                     @click="disable(scope.row.id,scope.row.name)">禁用
          </el-button>
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          <!--<el-button type="text" size="small" @click="updatePayConfigParam(scope.row.aliasName,scope.row.name)">修改支付参数-->
          <!--</el-button>-->
          <el-button type="text"   v-if="scope.row.enable=='1'"   size="small" @click="setPayConfig(scope.row.id,scope.row.aliasName)">设置支付</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" @getUrl="getUrl" ref="addOrUpdate"
                   @refreshDataList="getDataList"></add-or-update>
    <update-pay-config-param v-if="updatePayConfigParamVisible" @getUrl="getUrl" ref="UpdatePayConfigParam"
                             @refreshDataList="getDataList"></update-pay-config-param>
    <set-pay-config v-if="setPayConfigVisible" @getUrl="getUrl" ref="setPayConfig"
                    @refreshDataList="getDataList"></set-pay-config>
    <!-- 获取url图片的弹框 -->
    <el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
      <img :src="url"/>
    </el-dialog>
  </div>
</template>

<script>
  import AddOrUpdate from './payconfig-add-or-update'
  import UpdatePayConfigParam from './payconfig-update-pay-param'
  import SetPayConfig from './payconfig-set'

  export default {
    data() {
      return {
        dataForm: {
          payName: '',
					enable: '',
        },
				enableOptions: [{
					name: 1,
					label: '启用'
				}, {
					name: 0,
					label: '禁用'
				}],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        updatePayConfigParamVisible: false,
        setPayConfigVisible: false,
        url: null,
        visible: false
      }
    },
    components: {
      AddOrUpdate,
      UpdatePayConfigParam,
      SetPayConfig
    },
    activated() {
      this.getDataList()
    },
    created() {
      this.keyupSubmit()
    },
    methods: {
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/payconfig/payconfig/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'payName': this.dataForm.payName,
						'enable': this.dataForm.enable,
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
      // 获取url
      getUrl(id) {
        let that = this
        this.$http({
          url: this.$http.adornUrl('/webhomeenclosure/webhomeenclosure/enclosure/' + id),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'key': this.dataForm.key
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            that.url = data.url
            this.visible = true
          } else {
            this.$message.error(data.msg)
          }
        })
      },
      //绑定回车事件
      keyupSubmit() {
        document.onkeydown = e => {
          let _key = window.event.keyCode;
          if (_key === 13) {
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
      //批量修改或者新增支付配置参数
      updatePayConfigParam(aliasName, name) {
        this.updatePayConfigParamVisible = true
        this.$nextTick(() => {
          this.$refs.UpdatePayConfigParam.init(aliasName, name)
        })
      },
      //设置支付
      setPayConfig(id,aliasName) {
        this.setPayConfigVisible = true
        this.$nextTick(() => {
          this.$refs.setPayConfig.init(id,aliasName)
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
            url: this.$http.adornUrl('/payconfig/payconfig/delete'),
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
      },
      // 设为首推
      firstPush(id, name) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定将[` + name + `]设为首推?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/payconfig/payconfig/firstPush/' + id),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'key': this.dataForm.key
            })
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
      },
      // 启用
      enable(id, name) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定启用[` + name + `]?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/payconfig/payconfig/enable/' + id),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'key': this.dataForm.key
            })
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
      },
      // 禁用
      disable(id, name) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定禁用[` + name + `]?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/payconfig/payconfig/disable/' + id),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'key': this.dataForm.key
            })
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
