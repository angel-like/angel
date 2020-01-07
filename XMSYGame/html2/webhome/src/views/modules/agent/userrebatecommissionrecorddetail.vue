<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.userId" placeholder="ID" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.recommendationCode" placeholder="邀请码" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
        <el-button @click="exportCSV()">下载Excel</el-button>
			</el-form-item>
		</el-form>
		<!-- <el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item>
				<a style="cursor:pointer;color: #17B3A3;" @click="getHome()">
					首页
				</a>
			</el-breadcrumb-item>
			<el-breadcrumb-item v-for="list in accountList">
				<a style="cursor:pointer;color: #17B3A3;" @click="getList(list)">
					{{list}}
				</a>
			</el-breadcrumb-item>
		</el-breadcrumb> -->
		<div style="color: red;padding-bottom: 10px;">
			<span>显示当前页的【</span>
			<span>总佣金：{{obtainCommission}}（元）</span>
			<span>总佣金取款：{{takeAmount}}（元）</span>
			<span>总佣金余额：{{commission}}（元）】</span>

		</div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column prop="userId" header-align="center" align="center" label="用户ID">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="用户账号">
			</el-table-column>
			<el-table-column prop="recommendationCode" header-align="center" align="center" label="邀请码">
			</el-table-column>
			<el-table-column prop="money" header-align="center" align="center" label="账户余额">
			</el-table-column>
			<el-table-column prop="num" header-align="center" align="center" label="下线总人数">
				<template slot-scope="scope">
          <div v-if="scope.row.num>0">
					{{scope.row.num-1}}
          </div>
          <div v-if="scope.row.num==0">
            {{scope.row.num}}
          </div>
				</template>
			</el-table-column>
			<el-table-column prop="subordinateNum" header-align="center" align="center" label="直属下线人数">
				<template slot-scope="scope">
					<div v-if="scope.row.subordinateNum>0">
						<a type="text" size="small" style="cursor:pointer " @click="getSubordinateList(scope.row.userId,scope.row.userAccount)"><u>{{scope.row.subordinateNum}}</u></a>
					</div>
					<div v-if="scope.row.subordinateNum==0">
						0
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="betCoins" header-align="center" align="center" label="总打码">
				<template slot-scope="scope">
					{{scope.row.betCoins/100}}
				</template>
			</el-table-column>
			<el-table-column prop="obtainCommission" header-align="center" align="center" label="代理总佣金">
			</el-table-column>
			<el-table-column prop="" header-align="center" align="center" label="佣金取款额">
			<template slot-scope="scope">
				{{scope.row.obtainCommission-scope.row.commission}}
			</template>
			</el-table-column>
			<el-table-column prop="commission" header-align="center" align="center" label="代理佣金余额">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="recordDateHandle(scope.row.userId)">详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!--每日详情 -->
		<Userrebatecommissionrecorddatedetail v-if="dateDetailVisible" ref="userrebatecommissionrecorddatedetail"></Userrebatecommissionrecorddatedetail>
		<!--返佣详情 -->
		<Userrebatecommissionrecord v-if="recordVisible" ref="userrebatecommissionrecord"></Userrebatecommissionrecord>
		<!--下级打码详情 -->
		<Userrebatecommissionrecordsubordinate v-if="subordinateVisible" ref="userrebatecommissionrecordsubordinate"></Userrebatecommissionrecordsubordinate>
	</div>
</template>

<script>
	import Userrebatecommissionrecorddatedetail from './userrebatecommissionrecorddatedetail'
	import Userrebatecommissionrecord from './userrebatecommissionrecord'
	import Userrebatecommissionrecordsubordinate from './userrebatecommissionrecorddetail-subordinate'

	export default {
		data() {
			return {
				dataForm: {
					userAccount: '',
					userId: null,
					recommendationCode: ''
				},
				dataList: [],
				obtainCommission:'',
				takeAmount:'',
				commission:'',
				accountList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				dateDetailVisible: false,
				recordVisible: false,
				subordinateVisible: false
			}
		},
		components: {
			Userrebatecommissionrecorddatedetail,
			Userrebatecommissionrecord,
			Userrebatecommissionrecordsubordinate
		},
		activated() {
			this.getDataList()
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
      exportCSV() {
        this.downLoadMix("推广记录.csv");
      },
      downLoadMix(title) {
        this.$http({
          url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/exportCSV'),
          method: 'post',
          responseType: 'arraybuffer',
          params: this.$http.adornParams({
            'userAccount': this.dataForm.userAccount,
            'userId': this.dataForm.userId,
            'recommendationCode': this.dataForm.recommendationCode
          })
        }).then(({
                   data
                 }) => {

          let blob = new Blob([data],
            {
              type: 'text/csv,charset=gbk'
            });
          let link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.download = title;
          link.click();
          URL.revokeObjectURL(blob);

        })
      },


			// 获取数据列表

			getDataList() {
				if(this.dataForm.userAccount==null||this.dataForm.userAccount==''){
					this.accountList=[]
				}
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'userId': this.dataForm.userId,
						'recommendationCode': this.dataForm.recommendationCode
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
						this.obtainCommission = data.obtainCommission
						this.takeAmount = data.takeAmount
						this.commission = data.commission
					} else {
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			addList(account, userAccount) {
				if (this.accountList.length == 0) {
					this.accountList.push(userAccount)
				}
				if (this.accountList.indexOf(account) === -1) {
					this.accountList.push(account)
					this.dataForm.userAccount = account
					this.getDataList()
				} else {
					this.dataForm.userAccount = account
					this.getDataList()
				}
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
			getList(account) {
				this.accountList.splice(this.accountList.indexOf(account) + 1)
				this.dataForm.userAccount = account
				this.getDataList()
			},
			fristList(account) {
				this.accountList.push(account)
			},
			getHome() {
				this.accountList = []
				this.dataForm.userAccount = null
				this.getDataList()
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
			recordDateHandle(id) {
				this.dateDetailVisible = true
				this.$nextTick(() => {
					this.$refs.userrebatecommissionrecorddatedetail.init(id)
				})
			},
			recordHandle(date, userId) {
				this.recordVisible = true
				this.$nextTick(() => {
					this.$refs.userrebatecommissionrecord.getDataList(date, userId)
				})
			},
			// 获取下线列表
			getSubordinateList(id, userAccount) {
				this.subordinateVisible = true
				this.$nextTick(() => {
					this.$refs.userrebatecommissionrecordsubordinate.init(id, userAccount)
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
						url: this.$http.adornUrl('/userrebatecommissionrecord/userrebatecommissionrecord/delete'),
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
