<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="菜单名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('webhomemenu:webhomemenu:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('webhomemenu:webhomemenu:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
				<el-button @click="returnMenu()">返回上一层</el-button>
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
        label="菜单名称">
      </el-table-column>
			<el-table-column
				prop="type"
				header-align="center"
				align="center"
				label="类型">
					<template slot-scope="scope">
						<div v-if="scope.row.type=='0'">
							一级菜单
						</div>
						<div v-if="scope.row.type=='1'">
							二级菜单
						</div>
						<div v-if="scope.row.type=='2'">
							三级菜单
						</div>
					</template>
			</el-table-column>
      <el-table-column
        prop="icon"
        header-align="center"
        align="center"
        label="菜单图标">
				<template slot-scope="scope">
					<div v-if="scope.row.icon!=0">
						<el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.icon)">预览图片</el-button>
					</div>
					<div v-if="scope.row.icon==0">
						无图片
					</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="gameName"
        header-align="center"
        align="center"
        label="关联游戏">
      </el-table-column>
			<el-table-column
				prop="parentName"
				header-align="center"
				align="center"
				label="上级菜单">
			</el-table-column>
			<el-table-column
				prop="orderNum"
				header-align="center"
				align="center"
				label="排序">
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
    <add-or-update v-if="addOrUpdateVisible"  @getUrl="getUrl" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		<!-- 获取url图片的弹框 -->
		<el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
			<img :src="pohoteUrl" />
		</el-dialog>
  </div>
</template>

<script>
  import AddOrUpdate from './webhomemenuchildren-add-or-update'
  export default {
		name:'webhomemenuchildren',
    data () {
      return {
        dataForm: {
          name: ''
        },
				parent:'',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
				pohoteUrl:'',
				visible: false,
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
          url: this.$http.adornUrl('/webhomemenu/webhomemenu/childrenList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.dataForm.name,
						'parentId': this.$route.query.parentId
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
						that.pohoteUrl = data.url
						this.visible = true
					} else {
						this.$message.error(data.msg)
					}
				})
			},
      // 返回上一页
      returnMenu () {
				this.$router.go(-1)
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
          this.$refs.addOrUpdate.init(id,this.$route.query.parentId)
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
            url: this.$http.adornUrl('/webhomemenu/webhomemenu/delete'),
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
