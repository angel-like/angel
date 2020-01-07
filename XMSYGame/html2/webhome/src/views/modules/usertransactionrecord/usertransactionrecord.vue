<template>
	<div class="mod-config">
		<div style="color: red;padding: 10px 0px;">点击查询前：先输入会员账号（会员账号必填）</div>
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.orderNo" placeholder="订单编号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.type" clearable placeholder="请选择交易类型">
					<el-option
					v-for="item in typeOptions"
					:key="item.code"
					:label="item.name"
					:value="item.code">
					</el-option>
				</el-select>
			</el-form-item>
			<!-- <el-form-item>
				<el-select v-model="dataForm.detailType" clearable placeholder="请选择操作类型">
					<el-option
					v-for="item in detailTypeOptions"
					:key="item.code"
					:label="item.name"
					:value="item.code">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.fristrecharge" clearable placeholder="是否是首充">
					<el-option
					v-for="item in fristrechargeOptions"
					:key="item.code"
					:label="item.name"
					:value="item.code">
					</el-option>
				</el-select>
			</el-form-item> -->
			<el-form-item>
				<el-date-picker
					v-model="dataForm.time"
					type="datetimerange"
					range-separator="至"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					:picker-options="pickerOptions2"
          :default-time="['00:00:00', '23:59:59']">
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<!-- <el-button v-if="isAuth('usertransactionrecord:usertransactionrecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('usertransactionrecord:usertransactionrecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
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
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="userAccount" header-align="center" align="center" label="用户账号">
			</el-table-column>
			<el-table-column prop="type" header-align="center" align="center" label="交易类型">
				<template slot-scope="scope">
					<div v-if="scope.row.type==0">
						存款
					</div>
					<div v-if="scope.row.type==1">
						取款
					</div>
					<div v-if="scope.row.type==2">
						冲销
					</div>
					<div v-if="scope.row.type==3">
						返利
					</div>
					<div v-if="scope.row.type==4">
						派奖
					</div>
					<div v-if="scope.row.type==5">
						额度转换
					</div>
					<div v-if="scope.row.type==6">
						归集
					</div>
					<div v-if="scope.row.type==8">
						取款回退
					</div>
					<div v-if="scope.row.type==9">
						撤销充值
					</div>
					<div v-if="scope.row.type==10">
						任务领取
					</div>
					<div v-if="scope.row.type==11">
						余额宝
					</div>
					<div v-if="scope.row.type==12">
						划拨
					</div>
          <div v-if="scope.row.type==14">
            抽奖
          </div>
				</template>

			</el-table-column>
      <el-table-column prop="detailType" header-align="center" align="center" label="操作类型">
        <template slot-scope="scope">
          <div v-if="scope.row.detailType==11">
            银行充值
          </div>
          <div v-if="scope.row.detailType==12">
            支付宝充值
          </div>
          <div v-if="scope.row.detailType==13">
            微信充值
          </div>
          <div v-if="scope.row.detailType==14">
            人工充值
          </div>
          <div v-if="scope.row.detailType==15">
            佣金取款
          </div>
          <div v-if="scope.row.detailType==16">
            账户取款
          </div>
          <div v-if="scope.row.detailType==17">
            额度转换
          </div>
          <div v-if="scope.row.detailType==18">
            签到返利
          </div>
          <div v-if="scope.row.detailType==19">
            推荐返利
          </div>
          <div v-if="scope.row.detailType==20">
            奖池派奖
          </div>
          <div v-if="scope.row.detailType==21">
            资金归集
          </div>
          <div v-if="scope.row.detailType==22">
            取消取款订单
          </div>
          <div v-if="scope.row.detailType==23">
            佣金转金币
          </div>
          <div v-if="scope.row.detailType==24">
            实名返利
          </div>
          <div v-if="scope.row.detailType==25">
            取消佣金取现
          </div>
          <div v-if="scope.row.detailType==26">
            游戏返利
          </div>
          <div v-if="scope.row.detailType==27">
            撤销充值
          </div>
          <div v-if="scope.row.detailType==28">
            手续费返还
          </div>
          <div v-if="scope.row.detailType==29">
            QQ充值
          </div>
          <div v-if="scope.row.detailType==30">
            京东充值
          </div>
          <div v-if="scope.row.detailType==31">
            快捷充值
          </div>
          <div v-if="scope.row.detailType==32">
            银联充值
          </div>
          <div v-if="scope.row.detailType==33">
            会员下注返水
          </div>
          <div v-if="scope.row.detailType==34">
            代理商下线下注返佣
          </div>
          <div v-if="scope.row.detailType==35">
            代理商充值返返佣
          </div>
          <div v-if="scope.row.detailType==36">
            金币转余额
          </div>
          <div v-if="scope.row.detailType==37">
            用户充值代理商返佣撤销
          </div>
          <div v-if="scope.row.detailType==38">
            用户对局奖励
          </div>
          <div v-if="scope.row.detailType==39">
            用户分享App奖励
          </div>
          <div v-if="scope.row.detailType==40">
            用户救济金
          </div>
          <div v-if="scope.row.detailType==41">
            用户鼓励金
          </div>
          <div v-if="scope.row.detailType==42">
            人工取款
          </div>
          <div v-if="scope.row.detailType==43">
            任务领取
          </div>
          <div v-if="scope.row.detailType==44">
            余额宝转入
          </div>
          <div v-if="scope.row.detailType==45">
            余额宝转出
          </div>
          <div v-if="scope.row.detailType==46">
            兑换码兑换
          </div>
          <div v-if="scope.row.detailType==47">
            代理商划拨
          </div>
          <div v-if="scope.row.detailType==48">
            邮件领取
          </div>
          <div v-if="scope.row.detailType==49">
            幸运抽奖(单次)
          </div>
          <div v-if="scope.row.detailType==53">
            幸运十连抽
          </div>
          <div v-if="scope.row.detailType==50">
            充值返利
          </div>
          <div v-if="scope.row.detailType==501">
            注册优惠
          </div>
          <div v-if="scope.row.detailType==502">
            充值优惠
          </div>
          <div v-if="scope.row.detailType==503">
            退佣优惠
          </div>
          <div v-if="scope.row.detailType==504">
            活动优惠
          </div>
          <div v-if="scope.row.detailType==505">
            其它优惠
          </div>
          <div v-if="scope.row.detailType==506">
            推广优惠
          </div>
          <div v-if="scope.row.detailType==1001">
            银联云闪付
          </div>
        </template>
      </el-table-column>
			<el-table-column prop="createTime" header-align="center" align="center" label="时间">
			</el-table-column>
			<el-table-column prop="orderNo" header-align="center" align="center" label="订单编号">
			</el-table-column>
			<el-table-column prop="amount" header-align="center" align="center" label="交易额">
			</el-table-column>
			<!-- <el-table-column
        prop="remake"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column> -->
<!-- 			<el-table-column prop="fristrecharge" header-align="center" align="center" label="是否是首充">
				<template slot-scope="scope">
					<div v-if="scope.row.fristrecharge=='1'">
						是
					</div>
					<div v-if="scope.row.fristrecharge=='0'">
						否
					</div>
				</template>
			</el-table-column> -->
			<el-table-column prop="coin" header-align="center" align="center" label="金币余额">
				<template slot-scope="scope">
					{{scope.row.coin/100}}
				</template>
			</el-table-column>
			<el-table-column prop="commission" header-align="center" align="center" label="佣金余额">
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
	import AddOrUpdate from './usertransactionrecord-add-or-update'
	import moment from 'moment';
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
					type: '',
					detailType:'',
					fristrecharge:'',
					orderNo:'',
					time: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date(new Date(new Date().toLocaleDateString()).getTime()+24*60*60*1000-1)],
					//time: [new Date().toLocaleDateString(), new Date()],
				},
				typeOptions: [],
				detailTypeOptions: [],
				fristrechargeOptions:[
					{
					"code":true,
					"name":"是"
					},
					{
					"code":false,
					"name":"否"
					}],
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
			this.dataForm.userAccount= this.$route.query.account
			this.selectList()
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.time;
				if (timeArr != null && timeArr.length > 0) {
					startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
					if (timeArr.length > 1) {
						endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
					}
				}
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/usertransactionrecord/usertransactionrecord/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'userAccount': this.dataForm.userAccount,
						'type': this.dataForm.type,
						'detailType': this.dataForm.detailType,
						'fristrecharge': this.dataForm.fristrecharge,
						'orderNo':this.dataForm.orderNo,
						'startTime':startTime,
						'endTime':endTime,
						'sort': 'id',
						'direction': false
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
					} else if(data && data.code === 100){
						this.$message.error(data.msg)
					}else{
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			//搜索框类型加载数据
			selectList() {
				this.dataListLoading = true
				// 交易类型下拉
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + '003'),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.typeOptions = data.data
						if(this.dataForm.userAccount!=null&&this.dataForm.userAccount!=''){
							this.getDataList()
						}
					}
				})

				// 操作类型下拉
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + '004'),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.detailTypeOptions = data.data
					}
				})
				this.dataListLoading = false
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
						url: this.$http.adornUrl('/usertransactionrecord/usertransactionrecord/delete'),
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
