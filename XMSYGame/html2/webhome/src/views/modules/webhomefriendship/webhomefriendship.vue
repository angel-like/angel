<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.type" clearable placeholder="请选择跳转类型">
					<el-option 
					v-for="item in options" 
					:key="item.id" 
					:label="item.type" 
					:value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('webhomefriendship:webhomefriendship:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id"></el-table-column>
			<el-table-column prop="name" header-align="center" align="center" label="名称"></el-table-column>
			
			<el-table-column prop="url" header-align="center" align="center" label="链接">
				<template slot-scope="scope">
					<div v-if="scope.row.type==1">
						{{scope.row.url}}
					</div>
					<div v-if="scope.row.type==2">
						<el-button slot="reference" size="mini" type="primary" title="查看" @click="nextLevelHandle(scope.row.id)">查看内容</el-button>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="color" header-align="center" align="center" label="颜色"></el-table-column>
			<el-table-column prop="icon" header-align="center" align="center" label="图标">
				
				<template slot-scope="scope">
					<div v-if="scope.row.icon!=null&&scope.row.icon!=0">
						<el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.icon)">预览</el-button>
					</div>
				</template>
				
			</el-table-column>
			<el-table-column prop="type" header-align="center" align="center" label="跳转类型">
				<template slot-scope="scope">
					<div v-if="scope.row.type==1">
						路径跳转
					</div>
					<div v-if="scope.row.type==2">
						内容页跳转
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="orderNum" header-align="center" align="center" label="排序"></el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @getUrl="getUrl" @refreshDataList="getDataList"></add-or-update>
		
		<!-- 获取url图片的弹框 -->
		<el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
			<img :src="url" />
		</el-dialog>
	</div>
</template>

<script>
	import AddOrUpdate from './webhomefriendship-add-or-update'
	export default {
		data() {
			return {
				dataForm: {
					name: '',
					type:''
				},
				options: [{
					id: 1,
					type: '路径跳转',
				}, {
					id: 2,
					type: '内容跳转'
				}],
				dataList: [],
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
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 跳转页面
			nextLevelHandle (parentId) {
			this.$router.push({path:'/webhomefriendshiptemplate',query:{parentId:parentId}})
			},
			// 获取url
			getUrl(id) {
				let that = this
				this.$http({
					url: this.$http.adornUrl('/webhomeenclosure/webhomeenclosure/enclosure/' + id),
					method: 'get',
					params: this.$http.adornParams({
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
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/webhomefriendship/webhomefriendship/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'name': this.dataForm.name,
						'type': this.dataForm.type
					})
				}).then(({
					data
				}) => {
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
			//绑定回车事件
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery(){
				this.pageIndex=1;
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
						url: this.$http.adornUrl('/webhomefriendship/webhomefriendship/delete'),
						method: 'post',
						data: this.$http.adornData(ids, false)
					}).then(({
						data
					}) => {
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
