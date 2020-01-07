<template>
	<el-dialog :title="'详情'" :close-on-click-modal="false" :visible.sync="visible">
		<div class="mod-config">
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item>
					<el-input v-model="dataForm.userAccount" placeholder="用户账户" clearable></el-input>
				</el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="生效时间" end-placeholder="结束时间" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
				<el-form-item>
					<el-button @click="getDataList()">查询</el-button>
				</el-form-item>
			</el-form>
			<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
				<el-table-column type="selection" header-align="center" align="center" width="50">
				</el-table-column>
				<el-table-column prop="userId" header-align="center" align="center" label="兑换用户id">
				</el-table-column>
				<el-table-column prop="userAccount" header-align="center" align="center" label="用户账户">
				</el-table-column>
				<el-table-column prop="userName" header-align="center" align="center" label="用户名称">
				</el-table-column>
				<el-table-column prop="exchangeCode" header-align="center" align="center" label="兑换码">
				</el-table-column>
				<el-table-column prop="exchangeTime" header-align="center" align="center" label="兑换时间">
				</el-table-column>
			</el-table>
			<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
			 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
			</el-pagination>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			return {
				pickerOptions2: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				dataForm: {
					userAccount: ''
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				visible: false,
			}
		},
		components: {

		},
		activated() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				var startDate = null;
				var endDate = null;
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = timeArr[0];
					//console.log(startDate);
					if (timeArr.length > 1) {
						endDate = timeArr[1];
						//console.log(endDate);
					}
				}
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/giftbagexchangerecord/giftbagexchangerecord/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'exchangeCode': this.dataForm.exchangeCode,
						'sTime': startDate,
						'eTime': endDate
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
			init(exchangeCode) {
				this.visible = true
				this.dataForm.queryTime=null//查询时间参数初始化
				if (exchangeCode != null) {
					this.dataForm.exchangeCode = exchangeCode;
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
			}
		}
	}
</script>
