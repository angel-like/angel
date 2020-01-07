<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.orderNo" placeholder="订单号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="取款用户账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.incomeNo" placeholder="入款账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.sysUserAccount" placeholder="操作人" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.status" clearable placeholder="请选择订单状态">
					<el-option v-for="item in options" :key="item.status" :label="item.label" :value="item.status">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="金额范围">
				<el-input v-model="dataForm.amountMin" placeholder="最小范围" style="width: 100px;"></el-input>
				~
				<el-input v-model="dataForm.amountMax" placeholder="最大范围" style="width: 100px;"></el-input>
			</el-form-item>
			<el-form-item label="取款日期">
				<el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
				 start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button @click="exportCSV()">下载Excel</el-button>
				<el-button @click="getDataList()">刷新</el-button>
				<el-button v-if="isAuth('ordertakemoney:ordertakemoney:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<!-- <el-button v-if="isAuth('ordertakemoney:ordertakemoney:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
			</el-form-item>
      <br />
      <el-form-item>
        <span style="color: red;"> 总取款金额:{{takeMoneytotal}}</span>
        &nbsp;&nbsp;
        <span style="color: red;"> 总手续费:{{poundageTotal}}</span>
      </el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column
        type="index"
        width="120"
        header-align="center"
        align="center"
        label="序号">
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
				<template slot-scope="scope">
					<span  style="color: red;font-weight: bold;">{{scope.row.takeAmount}}</span>
				</template>
			</el-table-column>
			<el-table-column prop="poundage" header-align="center" align="center" label="手续费">
			</el-table-column>
			<el-table-column prop="incomeNo" header-align="center" align="center" label="入款账号">
			</el-table-column>
			<el-table-column prop="bankName" header-align="center" align="center" label="银行名称">
			</el-table-column>
			<el-table-column prop="accountName" header-align="center" align="center" label="开户人">
			</el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
			<el-table-column prop="status" header-align="center" align="center" label="状态">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.status==0" size="small" type="danger">未确认</el-tag>
					<el-tag v-if="scope.row.status==1" size="small" type="info">已取消</el-tag>
					<el-tag v-if="scope.row.status==2" size="small" type="success">已完成</el-tag>
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
					<div v-if="scope.row.accountType==2">
						微信
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="sysUserAccount" header-align="center" align="center" label="操作管理员">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">

					<div v-show="scope.row.status=='0'">
						<el-button type="text" size="small" @click="confirmedHandle(scope.row.id)">确认订单</el-button>
						<el-button type="text" size="small" @click="canceledHandle(scope.row.id)">取消订单</el-button>
					</div>
					<div v-show="scope.row.status!='0'">
						<el-button type="info" size="small">已处理</el-button>
					</div>
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
	import moment from 'moment';
	import json2csv from 'json2csv'
	import dateutil from '@/utils/datechonse'
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
					userAccount: '',
					orderNo: '',
					status: '',
					incomeNo: '',
					sysUserAccount: '',
					amountMin: null,
					amountMax: null,
					// queryTime: []
					queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)]
				},
				options: [{
						status: 0,
						label: '未确认'
					},
					{
						status: 1,
						label: '已取消'
					},
					{
						status: 2,
						label: '已完成'
					}
				],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
        takeMoneytotal:0,
        poundageTotal:0,
			}
		},
		activated() {
			this.getDataList()
			//this.dataForm.queryTime=[dateutil.getToday().starttime, dateutil.getToday().endtime];
		},
		components: {
			json2csv
		},
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
				//验证时间是否为空
				 var time;
				 var result = true;
				 if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
				 	time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
				 }
				 var res = /^[0-9]*[1-9][0-9]*$/;
			//	===============================验证取款最小金额格式==================================
				if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
					if (!res.test(this.dataForm.amountMin)) {
						result = false;
						this.$message.error("取款最小金额必须输入正整数")
						this.dataListLoading = false
						return;
					}
				}
				//验证取款最大金额格式
				if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
					if (!res.test(this.dataForm.amountMax)) {
						result = false;
						this.$message.error("取款最大金额必须输入正整数")
						this.dataListLoading = false
						return;
					}
				}
				if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
					.amountMin != '') {
					if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
						result = false;
						this.$message.error("取款最大金额不可小于最小金额")
						this.dataListLoading = false
						return;
					}
				}
				this.$http({
					url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'type': 1,
						'userAccount': this.dataForm.userAccount,
						'orderNo': this.dataForm.orderNo,
						'status': this.dataForm.status,
						'incomeNo': this.dataForm.incomeNo,
						'sysUserAccount': this.dataForm.sysUserAccount,
						'startTime': startDate,
						'endTime': endDate,
						'amountMax': this.dataForm.amountMax,
						'amountMin': this.dataForm.amountMin,
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
            this.takeMoneytotal = data.takeMoneytotal
            this.poundageTotal = data.poundageTotal
					} else {
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			exportCSV() {
				this.downLoadMix("佣金提现.csv");
			},
			downLoadMix(title) {
				//验证时间是否为空
				var time;
				var resultCheck = true;
				if (this.dataForm.queryTime != null && this.dataForm.queryTime != '') {
					time = moment(this.dataForm.queryTime).format("YYYY-MM-DD")
				}
				var res = /^[0-9]*[1-9][0-9]*$/;
				//===============================验证取款最小金额格式==================================
				if (this.dataForm.amountMin != null && this.dataForm.amountMin != '') {
					if (!res.test(this.dataForm.amountMin)) {
						resultCheck = false;
						this.$message.error("取款最小金额必须输入正整数")
						this.dataListLoading = false
						return;
					}
				}
				//验证取款最大金额格式
				if (this.dataForm.amountMax != null && this.dataForm.amountMax != '') {
					if (!res.test(this.dataForm.amountMax)) {
						resultCheck = false;
						this.$message.error("取款最大金额必须输入正整数")
						this.dataListLoading = false
						return;
					}
				}
				if (this.dataForm.amountMax != null && this.dataForm.amountMax != '' && this.dataForm.amountMin != null && this.dataForm
					.amountMin != '') {
					if (Number(this.dataForm.amountMax) < Number(this.dataForm.amountMin)) {
						resultCheck = false;
						this.$message.error("取款最大金额不可小于最小金额")
						this.dataListLoading = false
						return;
					}
				}
				this.$http({
					url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/exportCommissionCSVData'),
					method: 'get',
          responseType: 'arraybuffer',
					params: this.$http.adornParams({
						'type': 1,
						'userAccount': this.dataForm.userAccount,
						'orderNo': this.dataForm.orderNo,
						'status': this.dataForm.status,
						'incomeNo': this.dataForm.incomeNo,
						'sysUserAccount': this.dataForm.sysUserAccount,
						'queryTime': time,
						'amountMax': this.dataForm.amountMax,
						'amountMin': this.dataForm.amountMin,
					})
				}).then(({
					data
				}) => {
					let blob = new Blob([data],
					{
						type: 'text/csv,charset=UTF-8、'
					});
					let link = document.createElement('a');
					link.href = window.URL.createObjectURL(blob);
					link.download = title;
					link.click();
					URL.revokeObjectURL(blob);
					/*var fields_ = [{
							label: "订单号",
							value: "orderNo"
						}, {
							label: "取款时间",
							value: "createTime"
						},{
							label: "操作时间",
							value: "updateTime"
						},
						{
							label: "取款用户账号",
							value: "userAccount"
						}, {
							label: "取款金额",
							value: "takeAmount"
						},
						{
							label: "手续费",
							value: "poundage"
						}, {
							label: "入款账号",
							value: "incomeNo"
						}, {
							label: "银行名称",
							value: "bankName"
						}, {
							label: "开户人",
							value: "accountName"
						}, {
							label: "状态",
							value: "status"
						}, {
							label: "取款类型",
							value: "accountType"
						}, {
							label: "操作管理员",
							value: "sysUserAccount"
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
			// 确认订单
			confirmedHandle(id) {
				this.$confirm(`确定完成订单操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/confirmed/' + id),
						method: 'post'
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
							this.getDataList()
						}
					})
				})
			},
      // 取消订单
      canceledHandle (id) {
        this.$prompt(`确定取消订单操作?(可在下框中输入备注)`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^.{0,200}$/,
          inputErrorMessage: '备注输入不得超过200个字'
        }).then(({ value }) => {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/canceled'),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'remark': value
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
              this.getDataList()
            }
          })
          this.dataListLoading = false
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
						url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/delete'),
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
							this.getDataList()
						}
					})
				})
			}
		}
	}
</script>
