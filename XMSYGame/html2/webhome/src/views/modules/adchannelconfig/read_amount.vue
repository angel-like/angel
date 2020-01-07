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
				</el-table-column>-->
				<el-table-column prop="userId" header-align="center" align="center" label="会员id">
				</el-table-column>-->
				<el-table-column prop="userAccount" header-align="center" align="center" label="会员账号">
				</el-table-column>
				<el-table-column prop="userAmount" header-align="center" align="center" label="充值金额">
					<template slot-scope="scope">
						<div>{{scope.row.userAmount/100}}</div>
					</template>
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
			init(channelCode,startTime,endTime) {
				this.visible = true
				if (channelCode != null) {
					this.dataForm.channelCode = channelCode
					this.dataForm.startTime = startTime
					this.dataForm.endTime = endTime
				}
				this.getDataList();
			},
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/channelstatistics/channelstatistics/listamount'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'channelCode': this.dataForm.channelCode,
						'startTime': this.dataForm.startTime,
						'endTime': this.dataForm.endTime,
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
			}

		}
	}
</script>
