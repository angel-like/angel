<template>
  <div class="mod-config page-parent">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item v-if="false">
        <el-input v-model="dataForm.parentId" placeholder="上级" clearable></el-input>
      </el-form-item>
			<el-form-item >
				<el-input v-model="dataForm.paramKey" placeholder="名称" clearable></el-input>
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('sysconfig:sysconfig:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('sysconfig:sysconfig:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
    </el-form>
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item><a style="cursor:pointer;color: #17B3A3;" @click="getHome()">
					首页
				</a></el-breadcrumb-item>
			<el-breadcrumb-item v-for="list in idList"><a style="cursor:pointer;color: #17B3A3;" @click="getList(list)">
					{{list.key}}
				</a>
			</el-breadcrumb-item>
		</el-breadcrumb>
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
        prop="paramKey"
        header-align="center"
        align="center"
        label="名称">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="nextLevelHandle(scope.row.id,scope.row.paramKey)">{{scope.row.paramKey}}</el-button>
				</template>
      </el-table-column>
      <el-table-column
        prop="paramValue"
        header-align="center"
        align="center"
        label="值">
				<template slot-scope="scope">
					<div v-if="scope.row.paramValue.length>30">
				
						<el-tooltip placement="top">
							<div slot="content">{{scope.row.paramValue}}</div>
							<div>{{scope.row.paramValue.substring(0,30)}}.......</div>
						</el-tooltip>
					</div>
					<div v-else>
						{{scope.row.paramValue}}
					</div>
				</template>
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
  import AddOrUpdate from './sysconfig-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          parentId: '',
					parentKey:'',
					paramKey:''
        },
				idList:[],
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
			this.dataForm.parentId= this.$route.query.parentId
			this.dataForm.parentKey= this.$route.query.parentKey
			this.idList.push({id:this.dataForm.parentId,key:this.dataForm.parentKey});
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sysconfig/sysconfig/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'parentId':  this.dataForm.parentId,
						'paramKey':this.dataForm.paramKey
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
			getHome() {
				this.idList=[]
				this.$router.push({path:'/sysconfig'})
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
          this.$refs.addOrUpdate.init(id,this.dataForm.parentId)
        })
      },
			getList(obj) {
				this.idList.splice(this.idList.indexOf(obj) + 1)
				this.dataForm.parentId = obj.id
				this.getDataList()
			},
			nextLevelHandle(id,key){
					this.pageIndex=1
					if (this.idList.indexOf({id:id,key:key}) === -1) {
						this.idList.push({id:id,key:key})
						this.dataForm.parentId = id
						this.getDataList()
					} else {
						this.dataForm.parentId = id
						this.getDataList()
					}
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
            url: this.$http.adornUrl('/sysconfig/sysconfig/delete'),
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
<style>
	.el-tooltip__popper.is-dark{
		width: 220px;
		white-space:initial;
		word-break:break-all;
	}

.page-parent .el-button{
  /* white-space: initial; */
}
  .box {
    width: 400px;

    .top {
      text-align: center;
    }

    .left {
      float: left;
      width: 60px;
    }

    .right {
      float: right;
      width: 60px;
    }

    .bottom {
      clear: both;
      text-align: center;
    }

    .item {
      margin: 4px;
    }

    .left .el-tooltip__popper,
    .right .el-tooltip__popper {
      padding: 8px 10px;
    }
  }
</style>
