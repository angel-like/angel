<template>
  <div class="mod-config">

			<div class="tab-flex" v-if="lists!==null">
				<span :class="{activeClass:activeClass===index}" v-for="(labels,index) in lists" @click="getDataList(labels.id,index)">{{labels.name}}</span>
			</div>
			<div class="from">
				<el-form :inline="true" :model="dataForm" >
						<el-form-item>
							<el-input v-model="dataForm.menuId" placeholder="菜单ID" style="display: none;" clearable></el-input>
						</el-form-item>
						<!-- <el-form-item>
					<el-input v-model="dataForm.key" placeholder="参数名"  clearable></el-input>
					</el-form-item> -->
					<el-form-item>
					<!-- <el-button @click="getDataList()">查询</el-button> -->
					<el-button v-if="isAuth('webhomemenutemplate:webhomemenutemplate:save')"  type="primary" @click="addOrUpdateHandle()">新增</el-button>
					<!-- <el-button v-if="isAuth('webhomemenutemplate:webhomemenutemplate:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
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
					prop="title"
					header-align="center"
					align="center"
					label="标题">
					</el-table-column>
					<el-table-column
					prop="synopsis"
					header-align="center"
					align="center"
					label="简介">
					</el-table-column>
					<el-table-column
					prop="enclosureId"
					header-align="center"
					align="center"
					label="附件ID">
							<template slot-scope="scope">
								<div v-if="scope.row.enclosureId!=0">
									<el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.enclosureId)">预览图片</el-button>
								</div>
								<div v-if="scope.row.enclosureId==0">
									无图片
								</div>
							</template>
					</el-table-column>
					<el-table-column
					prop="orderNum"
					header-align="center"
					align="center"
					label="排序">
					</el-table-column>
					<el-table-column
					prop="gameName"
					header-align="center"
					align="center"
					label="游戏">
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
				<add-or-update v-if="addOrUpdateVisible" @getUrl="getUrl" ref="addOrUpdate"  @refreshDataList="getDataList"></add-or-update>
					<!-- 获取url图片的弹框 -->
					<el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
						<img :src="pohoteUrl" />
					</el-dialog>
				</div>
			</div>

</template>

<script>
	import AddOrUpdate from './webhomemenutemplate-add-or-update'
  export default {
    data () {
      return {
				dataForm: {
							menuId:'',
							key: ''
				},
				pageIndex: 1,
				pageSize: 10,
						pohoteUrl:'',
				totalPage: 0,
						visible: false,
						dataList: [],
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				activeClass:0,
				dataList: null,
				lists:[],
				activeName: null,
      }
    },
    components: {
			AddOrUpdate
    },
    activated () {
			this.getMenuList();
    },
    methods: {
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
				this.$refs.addOrUpdate.init(id,this.dataForm.menuId,this.activeClass)
			})
			},
			getDataList (id,index) {
			  if(id!=null&&id!=''){
          this.dataForm.menuId =id
          this.pageIndex=1
          this.pageSize=10
        }
			  if(index!=null&&index!=''){
          this.activeClass =index
        }

				this.dataListLoading = true

        console.log(this.dataForm.menuId)
				this.$http({
					url: this.$http.adornUrl('/webhomemenutemplate/webhomemenutemplate/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'menuId': this.dataForm.menuId
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
			getMenuList(id){
				this.$http({
					url: this.$http.adornUrl('/webhomemenu/webhomemenu/gameMenuList'),
					method: 'get',
					params:''
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.lists = data.data
						//name需要为string类型，所以前面拼接menu
						this.activeName='menu'+data.data[0].id
						this.dataForm.menuId=data.data[0].id
						this.getDataList(data.data[0].id,0)
					}
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
						url: this.$http.adornUrl('/webhomemenutemplate/webhomemenutemplate/delete'),
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
			// 获取url
			getUrl(id) {
				let that = this
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
			}
    }
  }
</script>

<style lang="scss" scoped>
	.tab-flex{
		display: flex;
		margin-bottom: 20px;
		border-bottom: 1px solid #ccc;
		span{
			display: inline-block;
			padding: 0 20px;
			line-height: 40px;
			cursor: pointer;
		}
		span:hover{
			border-bottom: 1px solid #17B3A3;
		}
		span.activeClass{
			border-bottom: 1px solid #17B3A3;
			color: #17B3A3;
		}
	}
</style>
