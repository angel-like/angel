<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" ref="dataForm" @submit.native.prevent
		 @keyup.enter.native="getDataList()">
				<!-- <el-form-item>
					<el-date-picker
				v-model="dataForm.queryTime"
				type="datetimerange"
				align="right"
				unlink-panels
				range-separator="至"
				start-placeholder="开始日期"
				end-placeholder="结束日期"
				:picker-options="pickerOptions2">
			</el-date-picker>
				</el-form-item> -->
      <el-form-item>
        <el-input v-model="dataForm.account" placeholder="会员账号" clearable></el-input>
      </el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.hierarchyId" clearable  placeholder="请选择风控层级">
					<el-option
						v-for="item in options"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userName" placeholder="真实姓名" clearable></el-input>
			</el-form-item>
			
				<!-- <el-select v-model="dataForm.sortOption" clearable  placeholder="请选择排序方式">
					<el-option
						v-for="item in sortOptions"
						:key="item.key"
						:label="item.label"
						:value="item.key">
					</el-option>
				</el-select> -->
			</el-form-item> 
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
		<!-- <el-button @click="getDataList()">刷新</el-button> -->
     </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
     <el-table-column
     	type="index"
     	width="120"
     	header-align="center"
     	align="center"
     	label="序号">
      </el-table-column>
      <el-table-column
        prop="account"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
			<el-table-column
			prop="userName"
			header-align="center"
			align="center"
			label="真实姓名">
			</el-table-column>
			<el-table-column
			prop="hierarchyName"
			header-align="center"
			align="center"
			label="风控层级">
			</el-table-column>
			<el-table-column
			prop="userType"
			header-align="center"
			align="center"
			label="会员类型">
			<template slot-scope="scope">
				<div v-if="scope.row.userType=='TRIAL'">试玩账号</div>
				<div v-else-if="scope.row.userType=='USER'">普通会员</div>
				<div v-else-if="scope.row.userType=='VIP'">VIP</div>
			</template>
			</el-table-column>
			<el-table-column
        prop="enable"
        header-align="center"
        align="center"
        label="账号状态">
			<template slot-scope="scope">
					<el-tag type="danger">风控</el-tag>
			</template>
      </el-table-column>
			<el-table-column
        prop="noScan"
        header-align="center"
        align="center"
        label="是否检索">
			<template slot-scope="scope">
				<el-tag v-if="scope.row.noScan" size="small" type="success">否</el-tag>
				<el-tag v-else size="small" type="info">是</el-tag>
			</template>
				</el-table-column>
			<!-- <el-table-column
				prop="money"
				header-align="center"
				align="center"
				label="余额">
			</el-table-column> -->
			<el-table-column
			prop="coin"
			header-align="center"
			align="center"
			label="金币">
			 <template slot-scope="scope">
				 {{scope.row.coin/100}}
			</template>
		 </el-table-column>
     <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
			<el-button type="text" size="small" @click.native="editHierarchy(scope.row.id)" v-if="isAuth('user:user:editRiskHierarchy')">修改用户层级</el-button>
			<el-button type="text" size="small" @click="userRiskRecord(scope.row.id)">用户风控记录</el-button>
		</template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
		<edit-hierarchy v-if="editHierarchyVisible" ref="editHierarchy" @refreshDataList="getDataList"></edit-hierarchy>
		<user-risk-record v-if="userRiskRecordVisible" ref="userRiskRecord" @refreshDataList="getDataList"></user-risk-record>
		</div>
  </div>
</template>

<script>
	import editHierarchy from './edit-risk-hierarchy'
	import userRiskRecord from './userriskrecord'
	import json2csv from 'json2csv'
	import { isAuth } from '@/utils'
  export default {
    data () {
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
					queryTime:[],
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
					abnormalEnable: '',
					sortOption: '',
					orientation: 'desc',
          frozenEnable: ''
        },
				options:[],
				showEdit: [], //显示编辑框
				showBtn: [],
				totalNum:0,
				pcNum:0,
				androidNum:0,
				iphoneNum:0,
				otherNum:0,
				userTypeList:[],
				statusOptions:[
					{key:1,label:"未拉黑"},
					{key:2,label:"拉黑"},
					{key:3,label:"未冻结"},
					{key:4,label:"冻结"},
					{key:5,label:"未停压"},
					{key:6,label:"停压"},
					{key:7,label:"正常"},
					{key:8,label:"异常"}
				],
				sortOptions:[
					{key: "",label:"游戏中"},
					{key: "id",label:"最近注册"}
				],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
		userRiskRecordVisible: false,
		editLoginPasswdVisible:false,
		editBankPasswdVisible:false,
		editHierarchyVisible:false,
		editUserMoneyVisible:false,
		queryUserInfoVisible:false
      }
    },
    components: {
			editHierarchy,
			json2csv,
			userRiskRecord,
    },
    activated () {
				this.getDataList();
				this.getHierarchy();
				this.getUseType();
    },
	created(){
	 this.keyupSubmit()
	},
    methods: {
      // 获取层级列表
			getHierarchy(){
				this.$http({
					url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
					method: 'get',
					params: this.$http.adornParams({"hierarchyType":1})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.hierarchyList
					}
				});
			},
			// 获取会员类型列表
			getUseType(){
				this.$http({
					url: this.$http.adornUrl(`/user/user/getUseType`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.userTypeList = data.userTypeList
					}
				});
			},
			handleChange(index, row) {
					if(row.oldRemark!=row.remark){
						var remark="";
						var userOperater={};
						var remark="备注";
						if(row.oldRemark){
							remark+="由【"+row.oldRemark+"】修改为【";
						}else{
							remark+="由【空】修改为【";
						}
						if(row.remark){
							remark+=row.remark+"】";
						}else{
							remark+="空】";
						}
						userOperater.memberId=row.id;
						userOperater.memberAccount=row.account ;
						userOperater.remark=remark;
						var parm={};
						parm.id=row.id;
						parm.remark=row.remark;
						parm.userOperater=userOperater;
						this.updateRemark(parm);
					}
					row.showBtn=false;
					row.showEdit=false;
      },
			thirdMoneyHandle(id){
				
			},
			//点击编辑
		handleEdit(index, row) {
				row.showBtn=true;
				row.showEdit=true;
				this.$set(row,true)
				
		},
			exportCSV(){
				this.downLoadMix2("会员名单.csv");
			},
			downLoadMix(title) {
				var startDate=null;
				var endDate=null;
				var timeArr=this.dataForm.queryTime;
				if(timeArr!=null && timeArr.length>0){
					startDate=timeArr[0];
					if( timeArr.length>1){
						endDate=timeArr[1];
					}
				}
				this.$http({
					url: this.$http.adornUrl('/user/user/exportCSV'),
					method: 'get',
					responseType: 'arraybuffer',
					params: this.$http.adornParams({
						'account': this.dataForm.account,
						'superiorsAccount': this.dataForm.superiorsAccount,
						'status': this.dataForm.status.join(','),
						'startTime': startDate,
						'endTime': endDate,
						'userName': this.dataForm.userName,
						'bankCard': this.dataForm.bankCard,
						'hierarchyId': this.dataForm.hierarchyId,
						'registerIp': this.dataForm.registerIp,
						'loginIp': this.dataForm.loginIp,
						'deviceCode': this.dataForm.deviceCode,
						'superiorsId': this.dataForm.superiorsId,
						'deviceType': this.dataForm.deviceType,
						'userType': this.dataForm.userType,
						'sortOption': this.dataForm.sortOption,
						'orientation': this.dataForm.orientation,
						'trial': '0'

					})
				}).then(({data}) => {
					// if (data && data.code === 200) {
						let blob = new Blob([data], {
							type: 'text/csv,charset=UTF-8、'
							});
						let link = document.createElement('a');
						link.href = window.URL.createObjectURL(blob);
						link.download = title;
						link.click();
						URL.revokeObjectURL(blob);
// 					} else {
// 						
// 					}
				})
					},
					downLoadMix2(title) {
									var startDate="";
									var endDate="";
									var timeArr=this.dataForm.queryTime;
									if(timeArr!=null && timeArr.length>0){
										startDate=timeArr[0];
										if( timeArr.length>1){
											endDate=timeArr[1];
										}
									}
									this.$http({
										url: this.$http.adornUrl('/user/user/exportCSVData'),
										method: 'get',
										params: this.$http.adornParams({
											'account': this.dataForm.account,
											'superiorsAccount': this.dataForm.superiorsAccount,
											'status': this.dataForm.status.join(','),
											'startTime': startDate,
											'endTime': endDate,
											'userName': this.dataForm.userName,
											'bankCard': this.dataForm.bankCard,
											'hierarchyId': this.dataForm.hierarchyId,
											'registerIp': this.dataForm.registerIp,
											'loginIp': this.dataForm.loginIp,
											'deviceCode': this.dataForm.deviceCode,
											'superiorsId': this.dataForm.superiorsId,
											'deviceType': this.dataForm.deviceType,
											'userType': this.dataForm.userType,
											'sortOption': this.dataForm.sortOption,
											'orientation': this.dataForm.orientation,
											'trial': '0'
										})
									}).then(({data}) => {
										var fields_=[
											{label:"ID",value:"id"},{label:"会员账号",value:"account"},{label:"真实姓名",value:"userName"},{label:"上级",value:"superiorsAccount"},
											{label:"层级",value:"hierarchyName"},{label:"用户类型",value:"userType"},{label:"注册IP",value:"registerIp"},{label:"登录IP",value:"LoginIp"},
											{label:"设备码",value:"deviceCode"},{label:"余额",value:"money"},{label:"金币",value:"coin"},{label:"备注",value:"remake"}
										];
										var opt={ fields: fields_,excelStrings:true}
										 if (data && data.code === 200) {
											 const result =json2csv.parse(data.dataList, opt);
											const csvContent = 'data:text/csv;charset=utf-8,\uFEFF' + result
											const link = document.createElement('a')
											link.href = encodeURI(csvContent)
											link.download = title;
											document.body.appendChild(link) // Required for FF
											link.click()
											document.body.removeChild(link) // Required for FF
					 					} 
									})
										},
      getDataList () {
        this.dataListLoading = true
				var startDate=null;
				var endDate=null;
				var timeArr=this.dataForm.queryTime;
				if(timeArr!=null && timeArr.length>0){
					startDate=timeArr[0];
					if( timeArr.length>1){
						endDate=timeArr[1];
					}
				}
        this.$http({
          url: this.$http.adornUrl('/user/user/risklist'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'account': this.dataForm.account,
            'superiorsAccount': this.dataForm.superiorsAccount,
            'startTime': startDate,
            'endTime': endDate,
            'userName': this.dataForm.userName,
            'bankCard': this.dataForm.bankCard,
            'riskHierarchyId': this.dataForm.hierarchyId,
            'registerIp': this.dataForm.registerIp,
            'loginIp': this.dataForm.loginIp,
            'deviceCode': this.dataForm.deviceCode,
            'superiorsId': this.dataForm.superiorsId,
            'deviceType': this.dataForm.deviceType,
            'userType': this.dataForm.userType,
						'sortOption': this.dataForm.sortOption,
						'orientation': this.dataForm.orientation,
						'trial': '0'
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
// 						this.pcNum=data.onlineData.pcNum;
// 						this.androidNum=data.onlineData.androidNum;
// 						this.iphoneNum=data.onlineData.iphoneNum;
// 						this.otherNum=data.onlineData.otherNum;
						// this.totalNum=data.onlineData.totalNum;
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
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
	  
	  // 查看用户风控记录
	  userRiskRecord (id) {
	  	this.userRiskRecordVisible = true
	  	this.$nextTick(() => {
	  	  this.$refs.userRiskRecord.init(id)
	  	})
	  },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
			//  查看会员信息
			queryUserInfoHandle (id) {
				this.queryUserInfoVisible = true
				this.$nextTick(() => {
					this.$refs.queryUserInfo.init(id)
				})
			},
			//修改会员余额
			editUserMoney (id) {
				this.editUserMoneyVisible = true
				this.$nextTick(() => {
					this.$refs.editUserMoney.init(id)
				})
			},	//修改登录密码
			editLoginPassword (id,userAccount) {
				this.editLoginPasswdVisible = true
				this.$nextTick(() => {
					this.$refs.editLoginPasswd.init(id,userAccount)
				})
			},
			//  修改取款密码
			editBankPassword (id,userAccount) {
				this.editBankPasswdVisible = true
				this.$nextTick(() => {
					this.$refs.editBankPasswd.init(id,userAccount)
				})
			},
			//  修改层级
			editHierarchy (id) {
				this.editHierarchyVisible = true
				this.$nextTick(() => {
					this.$refs.editHierarchy.init(id)
				})
			},
			//修改备注
			updateRemark (param) {
						this.$http({
							url: this.$http.adornUrl(`/user/user/update`),
							method: 'post',
							data: this.$http.adornData(param)
						}).then(({data}) => {
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
			//下一级
			nextLevelHandle(id,account){
				this.$refs['dataForm'].resetFields();
				this.dataForm.superiorsId=id;
				this.dataForm.superMan=account;
				this.getDataList();
			},//上一级
			upperLevelHandle(){
				this.$refs['dataForm'].resetFields()
				this.$http({
					url: this.$http.adornUrl(`/user/user/surperinfo/${this.dataForm.superiorsId}`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.dataForm.superiorsId=data.user.superiorsId;
						this.dataForm.superMan=data.user.superiorsAccount;
						this.getDataList();
					}
				})
			
			},
      // 删除
      deleteHandle (id) {
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
			forbidden(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为停用";
				
				this.$confirm(`确定对[id=${id}]进行[停用]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editForbidden`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'forbiddenEnable': '1',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			relieveForbidden(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为解除停用";
				this.$confirm(`确定对[id=${id}]进行[解除停用]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editForbidden`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'forbiddenEnable': '0',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			nobet(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为停押";
				this.$confirm(`确定对[id=${id}]进行[停押]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editNobet`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'nobetEnable': '1',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			relieveNobet(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为解除停押";
				this.$confirm(`确定对[id=${id}]进行[解除停押]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editNobet`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'nobetEnable': '0',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			//异常状态
			abnormal(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为异常状态吗";
				this.$confirm(`确定对[id=${id}]进行[异常]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editAbnormal`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'abnormalEnable': '1',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			//正常状态
			normal(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为正常状态";
				this.$confirm(`确定对[id=${id}]进行[解除异常]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editAbnormal`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'abnormalEnable': '0',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			frozen(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为冻结";
				this.$confirm(`确定对[id=${id}]进行[冻结]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/editFrozen`),
						method: 'post',
						data: this.$http.adornData({
							'id': id ,
							'frozenEnable': '1',
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
			relieveFrozen(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="会员状态设置为解除冻结";
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
					}).then(({data}) => {
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
			LoginOutMember(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="强制会员登出";
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
					}).then(({data}) => {
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
			deleteMember(id,userAccount){
				var userOperater={};
				userOperater.memberId=id;
				userOperater.memberAccount=userAccount;
				userOperater.remark="删除会员";
				this.$confirm(`确定对[id=${id}]进行[删除会员]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl(`/user/operation/deleteUser`),
						method: 'post',
						data: this.$http.adornData({
							'id': id,
							'userOperater': userOperater
						})
					}).then(({data}) => {
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
    }
  }
</script>
