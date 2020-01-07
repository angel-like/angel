<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
       <el-select v-model="dataForm.typeId" placeholder="活动类型" clearable>
       	<el-option
       		v-for="item in typeOptions"
       		:key="item.id"
       		:label="item.name"
       		:value="item.id">
       	</el-option>
       </el-select>
      </el-form-item>
			<!-- <el-form-item>
			<el-input v-model="dataForm.content" placeholder="内容" clearable></el-input>
			</el-form-item> -->
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('apppromotions:apppromotions:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('apppromotions:apppromotions:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="typeName"
        header-align="center"
        align="center"
        label="活动类型">
      </el-table-column>
			<el-table-column
			  prop="remake"
			  header-align="center"
			  align="center"
			  label="活动名称">
			</el-table-column>
      <el-table-column
        prop="enclosureId"
        header-align="center"
        align="center"
        label="附件ID">
				<template slot-scope="scope">
					<div v-if="scope.row.enclosureId!=0&&scope.row.enclosureId!=null">
						<el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.enclosureId)">预览图片</el-button>
					</div>
					<div v-if="scope.row.enclosureId==0||scope.row.enclosureId==''">
						无图片
					</div>
				</template>
      </el-table-column>
     <el-table-column 
        prop="content"
        header-align="center"
        align="center"
        label="内容">
					<template slot-scope="scope">
						<div v-if="scope.row.content!=0&&scope.row.content!=null">
							<el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.content)">预览图片</el-button>
						</div>
						<div v-if="scope.row.content==0||scope.row.content==''">
							无图片
						</div>
					</template>
      </el-table-column> 
			<el-table-column
			  prop="sorts"
			  header-align="center"
			  align="center"
			  label="排序号">
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
    <add-or-update v-if="addOrUpdateVisible" @getUrl="getUrl" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		<!-- 获取url图片的弹框 -->
		<el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
			<img :src="pohoteUrl" />
		</el-dialog>
  </div>
</template>

<script>
  import AddOrUpdate from './apppromotions-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          typeId: '',
					content:''
        },
				typeOptions:[],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
				visible:false,
				pohoteUrl:''
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
		created(){
		 this.keyupSubmit()
		},
    methods: {
      // 获取数据列表
      getDataList () {
				//获取下拉
				this.$http({
					url: this.$http.adornUrl(`/apppromotionstype/apppromotionstype/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.typeOptions = data.list
					}
				});
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/apppromotions/apppromotions/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'typeId': this.dataForm.typeId,
						'content':this.dataForm.content
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
				that.pohoteUrl='';
				this.$http({
					url: this.$http.adornUrl('/webhomeenclosure/webhomeenclosure/enclosure/' + id),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
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
			//绑定回车键
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询按钮事件
			getDataListQuery(){
				this.pageIndex=1;
				this.getDataList();
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
            url: this.$http.adornUrl('/apppromotions/apppromotions/delete'),
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
