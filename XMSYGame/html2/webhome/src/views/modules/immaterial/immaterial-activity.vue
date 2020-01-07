<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<!---->
			<el-form-item label="类别">
				<el-select v-model="dataForm.category" clearable placeholder="请选择类别">
					<el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('immaterial:immaterial:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('immaterial:immaterial:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="title" header-align="center" align="center" label="标题">
			</el-table-column>
			<el-table-column prop="content" header-align="center" align="center" label="内容" :show-overflow-tooltip="true">
				<template slot-scope="scope">
					<div v-if="!scope.row.content">
						--
					</div>
					<div v-else>
						{{scope.row.content}}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="type" header-align="center" align="center" label="素材类型">
				<template slot-scope="scope">
					<div v-if="scope.row.type==0">
						富文本
					</div>
					<div v-if="scope.row.type==1">
						图片
					</div>
					<div v-if="scope.row.type==2">
						图片+富文本
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="category" header-align="center" align="center" label="类别">
				<template slot-scope="scope">
					<div v-if="scope.row.category==1">
						新闻
					</div>
					<div v-if="scope.row.category==2">
						特色
					</div>
					<div v-if="scope.row.category==3">
						活动
					</div>
					<div v-if="scope.row.category==4">
						维护
					</div>
					<div v-if="scope.row.category==5">
						防盗
					</div>
					<div v-if="scope.row.category==6">
						游戏截图
					</div>
					<div v-if="scope.row.category==7">
						新手入门
					</div>
					<div v-if="scope.row.category==8">
						充值帮助
					</div>
					<div v-if="scope.row.category==9">
						玩家福利
					</div>
					<div v-if="scope.row.category==10">
						取款规则
					</div>
					<div v-if="scope.row.category==11">
						背景轮播
					</div>
					<div v-if="scope.row.category==12">
						VIP段位介绍
					</div>
					<div v-if="scope.row.category==13">
						最新消息
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="imageUrl" header-align="center" align="center" label="图片路径">
				<template slot-scope="scope">
					<div v-if="!scope.row.imageUrl">
						--
					</div>
					<div v-else>
						{{scope.row.imageUrl}}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="jumpUrl" header-align="center" align="center" label="跳转url">
				<template slot-scope="scope">
					<div v-if="!scope.row.jumpUrl">
						--
					</div>
					<div v-else>
						{{scope.row.jumpUrl}}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="orderNum" header-align="center" align="center" label="排序">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import AddOrUpdate from './immaterial-add-or-update-four'
	export default {
		data() {
			return {
				dataForm: {
					category: ''
				},
				categoryOptions: [{
						id: 3,
						name: "活动",
					}
				],
				fourTypes:'activity',
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
		activated() {
			this.getDataList()
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/immaterial/immaterial/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'category': this.dataForm.category,
						'fourTypes':this.fourTypes
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
					this.$refs.addOrUpdate.init(id,this.fourTypes)
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
						url: this.$http.adornUrl('/immaterial/immaterial/delete'),
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
