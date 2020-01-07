<template>
	<el-dialog :title="'详情'" :close-on-click-modal="false" :visible.sync="visible">
		<div class="mod-config">
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item>
					<el-button @click="getDataList()">刷新</el-button>
				</el-form-item>
			</el-form>
			<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
				<!--<el-table-column prop="channelName" header-align="center" align="center" label="渠道名称">
				</el-table-column>
				<el-table-column prop="channelCode" header-align="center" align="center" label="渠道代码">
				</el-table-column>-->
				<el-table-column prop="channelAddress" header-align="center" align="center" label="渠道地址">
				</el-table-column>
				<el-table-column prop="remake" header-align="center" align="center" label="备注">
				</el-table-column>
			</el-table>
			<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
			 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
			</el-pagination>
		</div>
	</el-dialog>
</template>

<script>
	//import AddOrUpdate from './adchannelconfig-add-or-update'
	export default {
		data() {
			return {
				dataForm: {
					channelName: ''
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				visible: false
			}
		},
		components: {
			//AddOrUpdate
		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/adchannelconfig/adchannelconfig/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'channelName': this.dataForm.channelName
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
			init(channelName) {
				this.visible = true
				if (channelName != null) {
					this.dataForm.channelName = channelName;
				}
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
						url: this.$http.adornUrl('/adchannelconfig/adchannelconfig/delete'),
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
