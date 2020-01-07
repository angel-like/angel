<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-select v-model="dataForm.type" placeholder="图片类型" clearable>
					<el-option v-for="item in pictureType" :key="item.id" :label="item.type" :value="item.id"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('webhomeimage:webhomeimage:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('webhomeimage:webhomeimage:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
				<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="type" header-align="center" align="center" label="图片类型">
				<template slot-scope="scope">
					<div v-if="scope.row.type=='logo'">
						logo
					</div>
					<div v-if="scope.row.type=='exhibition'">
						轮播图
					</div>
					<div v-if="scope.row.type=='couplets'">
						对联
					</div>
					<div v-if="scope.row.type=='gameRecord'">
						游戏记录轮播图
					</div>
					<div v-if="scope.row.type=='transactionRecord'">
						交易记录轮播图
					</div>
					<div v-if="scope.row.type=='recharge'">
						充值轮播图
					</div>
					<div v-if="scope.row.type=='promotions'">
						优惠活动图
					</div>
					<div v-if="scope.row.type=='pay'">
						首页支付图
					</div>
					<div v-if="scope.row.type=='cancel'">
						取消按钮
					</div>
					<div v-if="scope.row.type=='bottom'">
						底部图片
					</div>
					<div v-if="scope.row.type=='domain'">
						域名图片
					</div>
					<div v-if="scope.row.type=='AppDownload'">
						APP下载图片
					</div>
					<div v-if="scope.row.type=='appTutorial'">
						APP教程
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="enable" header-align="center" align="center" label="状态(启用，禁用)">
				<template slot-scope="scope">
					<div v-if="scope.row.enable=='1'">
						是
					</div>
					<div v-if="scope.row.enable=='0'">
						否
					</div>
				</template>
			</el-table-column>
				<el-table-column prop="url" header-align="center" align="center" label="跳转路径">
			</el-table-column>
			
			<el-table-column prop="num" header-align="center" align="center" label="排序号">
			</el-table-column>
			<el-table-column prop="enclosureId" header-align="center" align="center" label="图片">
				<template slot-scope="scope">
					<div v-if="scope.row.enclosureId!=null">
						<el-button slot="reference" size="mini" type="primary" title="查看" @click="getUrl(scope.row.enclosureId)">预览图片</el-button>
					</div>
				</template>
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
		<add-or-update v-if="addOrUpdateVisible" @getUrl="getUrl" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

		<!-- 获取url图片的弹框 -->
		<el-dialog title="预览" :close-on-click-modal="false" :visible.sync="visible">
			<img :src="url" />
		</el-dialog>
	</div>
</template>

<script>
	import AddOrUpdate from './webhomeimage-add-or-update'
	export default {
		data() {
			return {
				dataForm: {
					key: '',
					type: '',
				},
				pictureType: [{
					id: 'logo',
					type: 'logo图',
				}, {
					id: 'domain',
					type: '域名图片'
				}, {
					id: 'exhibition',
					type: '轮播图'
				}, {
					id: 'pay',
					type: '首页支付图'
				}, {
					id: 'couplets',
					type: '对联图'
				}, {
					id: 'gameRecord',
					type: '游戏记录轮播图'
				}, {
					id: 'transactionRecord',
					type: '交易记录轮播图'
				},{
					id: 'recharge',
					type: '充值轮播图'
				},{
					id: 'promotions',
					type: '优惠活动图'
				},{
					id: 'cancel',
					type: '取消按钮'
				},{
					id: 'bottom',
					type: '底部图片'
				},
				{
					id: 'AppDownload',
					type: 'APP下载图片'
				},
				{
					id: 'appTutorial',
					type: 'APP教程'
				}
				
				],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				visible: false,
        url: null,
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
					url: this.$http.adornUrl('/webhomeimage/webhomeimage/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'key': this.dataForm.key,
						'type': this.dataForm.type,
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
						url: this.$http.adornUrl('/webhomeimage/webhomeimage/delete'),
						method: 'post',
						data: this.$http.adornData(ids, false)
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.$message({
								message: '删除成功',
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
