<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-select v-model="dataForm.sysPropId" clearable placeholder="道具名称">
					<el-option v-for="item in sysPropIdOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.sell" clearable placeholder="是否上架">
					<el-option v-for="item in sellOptions" :key="item.name" :label="item.label" :value="item.name">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('shopproduct:shopproduct:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('shopproduct:shopproduct:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="sysPropName" header-align="center" align="center" label="道具名称">
			</el-table-column>
			<el-table-column prop="productNumber" header-align="center" align="center" label="产品数量">
			</el-table-column>
			<el-table-column prop="productOnePrice" header-align="center" align="center" label="产品单价">
				<template slot-scope="scope">
					<div v-if="scope.row.sysPropId==2&&scope.row.productOnePrice!=null">{{scope.row.productOnePrice/100}}</div>
					<div v-else>{{scope.row.productOnePrice}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="productTotalPrice" header-align="center" align="center" label="产品总价">
				<template slot-scope="scope">
					<div v-if="scope.row.sysPropId==2&&scope.row.productTotalPrice!=null">{{scope.row.productTotalPrice/100}}</div>
					<div v-else>{{scope.row.productTotalPrice}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="discount" header-align="center" align="center" label="折扣">
			</el-table-column>
			<el-table-column prop="productPrice" header-align="center" align="center" label="产品优惠价">
				<template slot-scope="scope">
					<div v-if="scope.row.sysPropId==2&&scope.row.productPrice!=null">{{scope.row.productPrice/100}}</div>
					<div v-else>{{scope.row.productPrice}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="sell" header-align="center" align="center" label="是否上架">
				<template slot-scope="scope">
					<div v-if="scope.row.sell">是</div>
					<div v-else>否</div>
				</template>
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="创建时间"></el-table-column>
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
	import AddOrUpdate from './shopproduct-add-or-update'
	export default {
		data() {
			return {
				sellOptions: [{
					name: 1,
					label: '是'
				}, {
					name: 0,
					label: '否'
				}],
				dataForm: {
					key: ''
				},
				sysPropIdOptions:'',
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
				/*	this.$http({
			 		url: this.$http.adornUrl(`/shopproduct/shopproduct/select`),
		 			method: 'get',
		 			params: this.$http.adornParams()
		 		}).then(({data}) => {
		 			if (data && data.code === 200) {
		 				this.sysPropIdOptions = data.list
					}
		 		});*/
				//道具选择
				this.$http({
					url: this.$http.adornUrl(`/sysprop/sysprop/getProp`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.sysPropIdOptions = data.propList
					}
				});

				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/shopproduct/shopproduct/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'sysPropId': this.dataForm.sysPropId,
						'sell': this.dataForm.sell
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
					//判断产品id  为房卡是，显示金额除以100
					/*if(this.data.sysPropId==2){
							this.data.productPrice=this.data.productPrice/100
						}*/
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
						url: this.$http.adornUrl('/shopproduct/shopproduct/delete'),
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
