<template>
	<el-dialog
		:title="'查看'"
		:close-on-click-modal="false"
		:visible.sync="visible">
	<div class="mod-config">
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="orderNo" header-align="center" align="center" label="订单号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="取款时间">
			</el-table-column>
			<el-table-column prop="updateTime" header-align="center" align="center" label="操作时间">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="取款用户账号">
			</el-table-column>
			<el-table-column prop="takeAmount" header-align="center" align="center" label="取款金额">
			</el-table-column>
			<el-table-column prop="userNeedBet" header-align="center" align="center" label="取款需要打码">
			</el-table-column>
			<el-table-column prop="userValidBet" header-align="center" align="center" label="用户有效打码">
			</el-table-column>
			<el-table-column prop="poundage" header-align="center" align="center" label="手续费">
			</el-table-column>
			<el-table-column prop="incomeNo" header-align="center" align="center" label="入款账号">
			</el-table-column>
			<el-table-column prop="bankName" header-align="center" align="center" label="银行名称">
			</el-table-column>
			<el-table-column prop="accountName" header-align="center" align="center" label="开户名">
			</el-table-column>
			<el-table-column prop="status" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
					<el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
					<el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
					<el-tag v-if="scope.row.status==4" size="small" type="success">已锁定</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="accountType" header-align="center" align="center" label="取款类型">
				<template slot-scope="scope">
					<div v-if="scope.row.accountType==0">
						支付宝
					</div>
					<div v-if="scope.row.accountType==1">
						银行
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作管理员">
			</el-table-column>
			<el-table-column prop="betCancel" header-align="center" align="center" label="是否取消打码">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.betCancel" size="small" type="info">是</el-tag>
					<el-tag v-if="!scope.row.betCancel" size="small" type="info">否</el-tag>
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
	export default {
		data() {
			return {
				visible: false,
				dataForm: {
					num: 3,
					status: 4,
					sysUserId:null
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false
			}
		},
		methods: {
			init(status){
				this.dataForm.status=status
				//如果是已完成，就根据操作人查询
				if(status==2){
					this.dataForm.sysUserId=this.$store.state.user.id
				}else{
					this.dataForm.sysUserId=null
				}
				this.getDataList()
			},
			// 获取数据列表
			getDataList() {
				this.visible=true
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'type': 0,
						'status':this.dataForm.status,
						'sysUserId': 	this.dataForm.sysUserId
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
		}
	}
</script>
