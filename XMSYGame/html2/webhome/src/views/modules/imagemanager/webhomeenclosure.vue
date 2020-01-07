<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <div style="margin-bottom: 20px;font-size: 16px;float: left" v-for="item in groupList">
          <el-button v-model="dataForm.groupId" style="padding-right: 25px; color: red;" :value="item.groupId"
                     @click="getDataListQuery(item.groupId)">{{item.name}}：{{item.num}}
          </el-button>
        </div>
        <div style="margin-bottom: 20px;font-size: 16px;float: right;">
          <el-button v-if="isAuth('webhomeenclosure:webhomeenclosure:save')" type="primary" @click="addGroupShow()">
            新增分组
          </el-button>
          <!--<div v-show="show">-->
            <!--<el-form-item>-->
              <!--<el-input v-model="dataForm.name" placeholder="分组名称" clearable></el-input>-->
            <!--</el-form-item>-->
            <!--<span slot="footer" class="dialog-footer">-->
      <!--<el-button @click="visible = false">取消</el-button>-->
      <!--<el-button type="primary" @click="dataFormSubmit()">确定</el-button>-->
    <!--</span>-->
          <!--</div>-->
          <el-button v-if="isAuth('webhomeenclosure:webhomeenclosure:save')" type="primary"
                     @click="addOrUpdateHandle()">
            本地上传
          </el-button>
          <el-button v-if="isAuth('webhomeenclosure:webhomeenclosure:delete')" type="danger" @click="deleteHandle()"
                     :disabled="dataListSelections.length <= 0">批量删除
          </el-button>
        </div>
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
        prop="name"
        header-align="center"
        align="center"
        label="文件名">
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="文件类型">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.id)">预览图片
          </el-button>
          <!--<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">移动</el-button>-->
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

    <!-- 获取url图片的弹框 -->
    <el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
      <img :src="url"/>
    </el-dialog>
  </div>
</template>

<script>
  import AddOrUpdate from './webhomeenclosure-add-or-update'

  export default {
    data() {
      return {
        dataForm: {
          name: ''
        },
        show: false,
        dataList: [],
        groupList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        visible: false,
        url: null
      }
    },
    components: {
      AddOrUpdate
    },
    activated() {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/webhomeenclosure/webhomeenclosure/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'groupId': this.dataForm.groupId
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.groupList = data.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      addGroupShow() {
        if (!this.show) {
          this.show = true
        }else {
          this.show=false
        }
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
      //查询
      getDataListQuery(id) {
        this.pageIndex = 1;
        console.log(id)
        this.dataForm.groupId = id
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
            url: this.$http.adornUrl('/webhomeenclosure/webhomeenclosure/delete'),
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
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/enclosuregroup/enclosuregroup/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
              })
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.fileList = []
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }

    }
  }
</script>
