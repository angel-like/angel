<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" ref="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.account" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="exportCSV()">下载Excel</el-button>
				<el-button type="primary" @click="addOrUpdateHandle()">试玩配置</el-button>
				<!--        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
 -->
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="account" fixed="left" header-align="center" align="center" label="会员账号">
			</el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="创建时间">
			</el-table-column>
			<el-table-column prop="enable" header-align="center" align="center" label="账号状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.forbiddenEnable=='0'">正常</el-tag>
					<el-tag v-else>停用</el-tag>
					<el-tag v-if="scope.row.nobetEnable=='0'">未停压</el-tag>
					<el-tag v-else>停压</el-tag>
					<el-tag v-if="scope.row.frozenEnable=='0'">未冻结</el-tag>
					<el-tag v-else>冻结</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="remark" header-align="center" align="center" label="登录状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.online">{{scope.row.deviceType}}在线</el-tag>
					<el-tag v-else>离线</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="remark" header-align="center" align="left" min-width="200" label="详情">
				<template slot-scope="scope">
					<div>最后登录ip:{{scope.row.LoginIp}}</div>
					<div>上次登录ip:{{scope.row.LoginIp}}</div>
					<div>注册ip:{{scope.row.registerIp}}</div>
					<div>设备码:{{scope.row.deviceCode}}</div>
					<!-- <el-button size="mini" type="text"  @click="deleteHandle(scope.row.id)"  >查看详情</el-button> -->
				</template>
			</el-table-column>
			<el-table-column prop="money" header-align="center" align="center" label="余额">
			</el-table-column>

			<el-table-column prop="coin" header-align="center" align="center" label="金币">
				<template slot-scope="scope">
					{{scope.row.coin}}
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="160" label="操作">
				<template slot-scope="scope">
					<el-button-group>
						<el-dropdown v-if="isAuth('user:user:more')">
							<el-button size="mini" type="primary" title="更多" icon="el-icon-menu"></el-button>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item @click.native="forbidden(scope.row.id,scope.row.account)" v-if="scope.row.forbiddenEnable=='0'">停用</el-dropdown-item>
								<el-dropdown-item @click.native="relieveForbidden(scope.row.id,scope.row.account)" v-else>解除停用</el-dropdown-item>
								<el-dropdown-item @click.native="frozen(scope.row.id,scope.row.account)" v-if="scope.row.frozenEnable=='0'">冻结</el-dropdown-item>
								<el-dropdown-item @click.native="relieveFrozen(scope.row.id,scope.row.account)" v-else>解除冻结</el-dropdown-item>
								<el-dropdown-item @click.native="nobet(scope.row.id,scope.row.account)" v-if="scope.row.nobetEnable=='0'">停压</el-dropdown-item>
								<el-dropdown-item @click.native="relieveNobet(scope.row.id,scope.row.account)" v-else>解除停压</el-dropdown-item>
								<!-- <el-dropdown-item @click.native="LoginOutMember(scope.row.id,scope.row.account)" >登出</el-dropdown-item> -->
							</el-dropdown-menu>
						</el-dropdown>

					</el-button-group>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
	</div>
</template>

<script>
	import AddOrUpdate from './usertrialconfig-add-or-update'
	import json2csv from 'json2csv'
	import dateutil from '@/utils/datechonse'
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
					}, {
						text: '本月',
						onClick(picker) {
							const end = dateutil.getCurrMonthDays().endtime;
							const start = dateutil.getCurrMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '上月',
						onClick(picker) {
							const end = dateutil.getLastMonthDays().endtime;
							const start = dateutil.getLastMonthDays().starttime;
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去7天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去30天',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '过去二月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 60);
							picker.$emit('pick', [start, end]);
						}
					}, {
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
					superiorsAccount: '',
					superMan: '',
					superiorsId: 0,
					status: '',
					userName: '',
					bankCard: '',
					hierarchyId: null,
					registerIp: '',
					loginIp: '',
					deviceCode: '',
					deviceType: '',
					userType: '',
					startTime: '',
					endTime: '',
					forbiddenEnable: '',
					nobetEnable: '',
					frozenEnable: '',
					queryTime: [],
				},
				options: [],
				userTypeList: [],
				statusOptions: [{
						key: 1,
						label: "未停用"
					},
					{
						key: 2,
						label: "停用"
					},
					{
						key: 3,
						label: "未冻结"
					},
					{
						key: 4,
						label: "冻结"
					},
					{
						key: 5,
						label: "未停压"
					},
					{
						key: 6,
						label: "停压"
					}
				],
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
			AddOrUpdate,
			json2csv
		},
		activated() {
			this.getDataList();
		},
		created() {
			this.keyupSubmit()
		},
		methods: {
			// 获取层级列表
			getHierarchy() {
				this.$http({
					url: this.$http.adornUrl(`/user/user/getHierarchy`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.options = data.hierarchyList
					}
				});
			},
			// 获取会员类型列表
			getUseType() {
				this.$http({
					url: this.$http.adornUrl(`/user/user/getUseType`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.userTypeList = data.userTypeList
					}
				});
			},
			handleChange(index, row) {
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
			exportCSV() {
				this.downLoadMix2("会员名单.csv");
			},
			downLoadMix2(title) {
				var startDate = null;
				var endDate = null;
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				this.$http({
					url: this.$http.adornUrl('/user/user/exportTrialCSVData'),
					method: 'get',
					params: this.$http.adornParams({
						'account': this.dataForm.account,
						'startTime': startDate,
						'endTime': endDate,
						'trial': '1'
					})
				}).then(({
					data
				}) => {
					let blob = new Blob([data], {
						type: 'text/csv,charset=UTF-8、'
					});
					let link = document.createElement('a');
					link.href = window.URL.createObjectURL(blob);
					link.download = title;
					link.click();
					URL.revokeObjectURL(blob);
					/*var fields_ = [{
							label: "ID",
							value: "id"
						}, {
							label: "会员账号",
							value: "account"
						},
						{
							label: "用户类型",
							value: "userType"
						}, {
							label: "登录IP",
							value: "LoginIp"
						},
						{
							label: "余额",
							value: "money"
						}, {
							label: "金币",
							value: "coin"
						}, {
							label: "备注",
							value: "remake"
						}
					];
					var opt = {
						fields: fields_,
						excelStrings: true
					}
					if (data && data.code === 200) {
						const result = json2csv.parse(data.dataList, opt);
						const csvContent = 'data:text/csv;charset=utf-8,\uFEFF' + result
						const link = document.createElement('a')
						link.href = encodeURI(csvContent)
						link.download = title;
						document.body.appendChild(link) // Required for FF
						link.click()
						document.body.removeChild(link) // Required for FF
					}*/
				})
			},
			getDataList() {
				this.dataListLoading = true
				var startDate = null;
				var endDate = null;
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
					startDate = timeArr[0];
					if (timeArr.length > 1) {
						endDate = timeArr[1];
					}
				}
				this.$http({
					url: this.$http.adornUrl('/user/user/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'account': this.dataForm.account,
						'startTime': startDate,
						'endTime': endDate,
						'trial': '1'
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						// 						for(var i = 0; i < this.dataList.length; i++) {
						// 							this.showEdit[i] = false;
						// 							this.showBtn[i] = false;
						// // 							this.$set(this.showEdit[i], false);
						// // 							this.$set(this.showBtn[i], false);
						// 					}
						this.totalPage = data.page.totalCount
					} else {
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			//绑定回车事件
			keyupSubmit() {
				document.onkeydown = e => {
					let _key = window.event.keyCode;
					if (_key === 13) {
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery() {
				this.pageIndex = 1;
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
			//修改备注
			updateRemark(param) {
				this.$http({
					url: this.$http.adornUrl(`/user/user/update`),
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
					//           this.$http({
					//             url: this.$http.adornUrl('/sys/config/delete'),
					//             method: 'post',
					//             data: this.$http.adornData(ids, false)
					//           }).then(({data}) => {
					//             if (data && data.code === 200) {
					//               this.$message({
					//                 message: '操作成功',
					//                 type: 'success',
					//                 duration: 1500,
					//                 onClose: () => {
					//                   this.getDataList()
					//                 }
					//               })
					//             } else {
					//               this.$message.error(data.msg)
					//             }
					//           })
				}).catch(() => {})
			},
			forbidden(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "会员状态设置为停用";

				this.$confirm(`确定对[id=${id}]进行[停用]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editForbidden`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'forbiddenEnable': '1',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			},
			relieveForbidden(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "会员状态设置为解除停用";
				this.$confirm(`确定对[id=${id}]进行[解除停用]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editForbidden`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'forbiddenEnable': '0',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			},
			nobet(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "会员状态设置为停押";
				this.$confirm(`确定对[id=${id}]进行[停押]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editNobet`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'nobetEnable': '1',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			},
			relieveNobet(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "会员状态设置为解除停押";
				this.$confirm(`确定对[id=${id}]进行[解除停押]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editNobet`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'nobetEnable': '0',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			},
			frozen(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "会员状态设置为冻结";
				this.$confirm(`确定对[id=${id}]进行[冻结]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editFrozen`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'frozenEnable': '1',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			},
			relieveFrozen(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "会员状态设置为解除冻结";
				this.$confirm(`确定对[id=${id}]进行[解除冻结]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editFrozen`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'frozenEnable': '0',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			},
			LoginOutMember(id, userAccount) {
				var userOperater = {};
				userOperater.memberId = id;
				userOperater.memberAccount = userAccount;
				userOperater.remark = "强制会员登出";
				this.$confirm(`确定对[id=${id}]进行[强制会员登出]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/LoginOutMember`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'frozenEnable': '0',
							'userOperater': userOperater
						})
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
				}).catch(() => {})
			}
		}
	}
</script>
