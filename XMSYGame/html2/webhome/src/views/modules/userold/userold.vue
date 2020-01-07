<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.account" placeholder="账号名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userName" placeholder="姓名" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.bankCard" placeholder="银行卡号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
			<!--	<el-button v-if="isAuth('userold:userold:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('userold:userold:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>-->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<!--	<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>-->
			<el-table-column prop="account" header-align="center" align="center" label="账号名称">
			</el-table-column>
			<!--<el-table-column prop="portrait" header-align="center" align="center" label="头像">
			</el-table-column>
			<el-table-column prop="headframeId" header-align="center" align="center" label="头像框id">
			</el-table-column>
			<el-table-column prop="sex" header-align="center" align="center" label="性别(0:女，1：男)">
			</el-table-column>-->
			<el-table-column prop="userName" header-align="center" align="center" label="真实姓名">
			</el-table-column>
			<el-table-column prop="vipName" header-align="center" align="center" label="VIP等级">
			</el-table-column>
			<el-table-column prop="hierarchyName" header-align="center" align="center" label="推广层级">
			</el-table-column>
			<el-table-column prop="riskHierarchyName" header-align="center" align="center" label="风控层级">
			</el-table-column>
			<el-table-column prop="userType" header-align="center" align="center" label="会员类型">
				<template slot-scope="scope">
					<div v-if="scope.row.userType=='TRIAL'">试玩账号</div>
					<div v-else-if="scope.row.userType=='USER'">普通会员</div>
					<div v-else-if="scope.row.userType=='VIP'">VIP</div>
				</template>
			</el-table-column>
			<el-table-column prop="enable" header-align="center" align="center" label="账号状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.forbiddenEnable=='0' && scope.row.nobetEnable=='0' && scope.row.frozenEnable=='0' && scope.row.abnormalEnable=='0' ">正常</el-tag>
					<el-tag v-else-if="scope.row.forbiddenEnable=='1'" type="danger">已拉黑</el-tag>
					<el-tag v-else-if="scope.row.nobetEnable=='1'" type="danger">已停押</el-tag>
					<el-tag v-else-if="scope.row.frozenEnable=='1'" type="danger">已冻结</el-tag>
					<el-tag v-else-if="scope.row.abnormalEnable=='1'" type="danger">风控</el-tag>
				</template>
			</el-table-column>

			<!--		<el-table-column prop="token" header-align="center" align="center" label="token验证Id">
			</el-table-column>
			<el-table-column prop="unionType" header-align="center" align="center" label="第三方平台">
			</el-table-column>
			<el-table-column prop="openId" header-align="center" align="center" label="第三方交互ID">
			</el-table-column>
			<el-table-column prop="unionId" header-align="center" align="center" label="第三方ID">
			</el-table-column>
			<el-table-column prop="superiorsId" header-align="center" align="center" label="上级ID">
			</el-table-column>
			<el-table-column prop="forbiddenEnable" header-align="center" align="center" label="禁用（正常）">
			</el-table-column>
			<el-table-column prop="nobetEnable" header-align="center" align="center" label="停押（正常）">
			</el-table-column>
			<el-table-column prop="frozenEnable" header-align="center" align="center" label="冻结(正常)">
			</el-table-column>	
		  <el-table-column prop="abnormalEnable" header-align="center" align="center" label="是否异常">
			</el-table-column>
			<el-table-column prop="hierarchyId" header-align="center" align="center" label="层级id">
			</el-table-column>
			<el-table-column prop="riskHierarchyId" header-align="center" align="center" label="风控层级id">
			</el-table-column>-->
			<el-table-column prop="money" header-align="center" align="center" label="余额">
			</el-table-column>
			<el-table-column prop="coin" header-align="center" align="center" label="金币">
			</el-table-column>
			<el-table-column prop="roomCard" header-align="center" align="center" label="房卡">
			</el-table-column>
			<el-table-column prop="commission" header-align="center" align="center" label="佣金">
			</el-table-column>
			<el-table-column prop="bankCard" header-align="center" align="center" label="银行卡号">
			</el-table-column>
			<!--	<el-table-column prop="commission" header-align="center" align="center" label="佣金">
			</el-table-column>
	
			<el-table-column prop="registerIp" header-align="center" align="center" label="注册ip">
			</el-table-column>
			<el-table-column prop="registerIpAddress" header-align="center" align="center" label="注册ip地址">
			</el-table-column>
			<el-table-column prop="registerDeviceCode" header-align="center" align="center" label="注册机器码">
			</el-table-column>-->
			<el-table-column prop="remark" header-align="center" align="center" label="备注">
				<!--<template slot-scope="scope">
					<el-input size="small" v-if="scope.row.showEdit" v-model="scope.row.remark" placeholder="请输入备注" @change="handleChange(scope.$index, scope.row)"></el-input>
					<span v-else>{{scope.row.remark}}</span>
					<el-button size="mini" v-if="!scope.row.showBtn" title="" @click="handleEdit(scope.$index, scope.row)"></el-button>
				</template>-->
			</el-table-column>
			<!--	<el-table-column prop="agentEnable" header-align="center" align="center" label="状态（1：是代理。0：禁用代理）">
			</el-table-column>
			<el-table-column prop="firstRecharge" header-align="center" align="center" label="是否首冲过">
			</el-table-column>
			<el-table-column prop="gameInfoId" header-align="center" align="center" label="在玩游戏信息id">
			</el-table-column>
			<el-table-column prop="gameServerId" header-align="center" align="center" label="游戏服务id">
			</el-table-column>
			<el-table-column prop="noScan" header-align="center" align="center" label="0:检索风控  1：不检索风控">
			</el-table-column>
			<el-table-column prop="phone" header-align="center" align="center" label="手机号">
			</el-table-column>-->
	<!--		<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>-->
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import dateutil from '@/utils/datechonse'
	import AddOrUpdate from './userold-add-or-update'
	import moment from 'moment';
	export default {
		data() {
			return {
					pickerOptions2: {
					shortcuts: [{
					  text: '今天',
					    onClick(picker) {
					  	const end = dateutil.getToday().endtime;
					  	const start = dateutil.getToday().starttime;
					      picker.$emit('pick', [start, end]);
					    }
					  }, {
					    text: '昨天',
					    onClick(picker) {
					  	const end = dateutil.getYesterday().endtime;
					  	const start = dateutil.getYesterday().starttime;
					      picker.$emit('pick', [start, end]);
					    }
					}, {
						text: '本周',
						onClick(picker) {
							const end = dateutil.getCurrWeekDays().endtime;
							const start = dateutil.getCurrWeekDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '上周',
						onClick(picker) {
							const end = dateutil.getLastWeekDays().endtime;
							const start = dateutil.getLastWeekDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '本月',
						onClick(picker) {
							const end = dateutil.getCurrMonthDays().endtime;
							const start = dateutil.getCurrMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '上月',
						onClick(picker) {
							const end = dateutil.getLastMonthDays().endtime;
							const start = dateutil.getLastMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去7天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去30天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去二月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
							picker.$emit('pick', [start, end]);
						}
					},{
						text: '过去三月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				},
				dataForm: {
					account: '',
					userName:'',
					bankCard:'',
					queryTime: []
				},
			//	showEdit: [], //显示编辑框
				//showBtn: [],
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
		//绑定回车事件
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				var startDate = null;
				var endDate = null;
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/userold/userold/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'startTime': startDate,
						'endTime': endDate,
						'account': this.dataForm.account,
						'userName':this.dataForm.userName,
						'bankCard':this.dataForm.bankCard
						
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
			//为了修改备注
		/*	handleChange(index, row) {
				if (row.oldRemark != row.remark) {
					var remark = "";
					var userOperater = {};
					var remark = "备注";
					if (row.oldRemark) {
						remark += "由【" + row.oldRemark + "】修改为【";
					} else {
						remark += "由【空】修改为【";
					}
					if (row.remark) {
						remark += row.remark + "】";
					} else {
						remark += "空】";
					}
					userOperater.memberId = row.id;
					userOperater.memberAccount = row.account;
					userOperater.remark = remark;
					var parm = {};
					parm.id = row.id;
					parm.remark = row.remark;
					parm.userOperater = userOperater;
					this.updateRemark(parm);
				}
				row.showBtn = false;
				row.showEdit = false;
			},
			//点击编辑
			handleEdit(index, row) {
				row.showBtn = true;
				row.showEdit = true;
				this.$set(row, true)
			
			},
			//修改备注
			updateRemark(param) {
				this.$http({
					url: this.$http.adornUrl(`/userold/userold/updateRemark`),
					method: 'post',
					data: this.$http.adornData(param)
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.$message({
							message: '操作成功',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getDataList();
							}
						})
					} else {
						this.$message.error(data.msg)
					}
				})
			
			},*/
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
						url: this.$http.adornUrl('/userold/userold/delete'),
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
