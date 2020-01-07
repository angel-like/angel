<template>
  <div class="mod-config">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="200px">
    <el-form-item label="会员账号"  >
      <el-input   style="width: 150px" clearable v-model="dataForm.userAccountQuery" @change="getDiscount()" placeholder="会员账号"></el-input>
			<el-button @click="getUser()">查找</el-button>
    </el-form-item>
		<el-form-item label="待操作会员账号" prop="userAccount" >
			<div v-clipboard:copy="dataForm.userAccount"  
			 v-clipboard:success="onCopy" 
			  v-clipboard:error="onError" 
			style="color: blue;cursor:pointer;">
			{{dataForm.userAccount}}
			<!-- <button type="button" class="btn" 
					v-clipboard:copy="dataForm.userAccount" 
					>
					复制
			</button> -->
			<i class="el-icon-document"    ></i>
			</div>
		</el-form-item>
		<el-form-item label="真实姓名" >
			<div>{{dataForm.userName}}</div>
		</el-form-item>
		<!-- <el-form-item label="余额" >
			<div>{{dataForm.money}}</div>
		</el-form-item>
		<el-form-item label="金币" >
			<span style="margin-right: 10px;">{{dataForm.coin}}</span>
		</el-form-item> -->
		<el-form-item label="总余额" >
			<span style="margin-right: 10px;">{{dataForm.totalMoney}}</span>
		</el-form-item>
		<el-form-item label="用户层级" >
			<span style="margin-right: 10px;">{{dataForm.hierarchyName}}</span>
		</el-form-item>
		<el-form-item label="操作类型" prop="operationType">
			<el-radio-group v-model="dataForm.operationType">
				<!--<el-radio :label="0" type="checkbox" :disable="true">存款</el-radio>-->
				<el-radio :label="1">取款</el-radio>
			</el-radio-group>
		</el-form-item>
	<!-- 	<el-form-item label="层级优惠" prop="hierarchyId" v-if="dataForm.operationType==0">
			<el-select v-model="dataForm.hierarchyId"  @change="getDiscount()" placeholder="请选择层级" clearable>
				<el-option
					v-for="item in hierarchyOptions"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
		</el-form-item> -->
		<el-form-item label="操作金额(元)" prop="amount">
			<el-input type= "number" style="width: 200px"  @change="getDiscount()" v-model="dataForm.amount" placeholder="金额"></el-input>
		</el-form-item>
		<el-form-item label="存入金额打码倍数" prop="rechargeMultiple"  v-if="dataForm.operationType==0">
			<el-input-number v-model="dataForm.rechargeMultiple" :precision="2" :step="0.1" :min="0" @change="countCodingCode" placeholder="存入金额打码倍数"></el-input-number>
		</el-form-item>
		<el-form-item label="优惠金额(元)" prop="discountAmount"  v-if="dataForm.operationType==0">
			<el-input type= "number"  style="width: 200px" v-model="dataForm.discountAmount" readonly placeholder="优惠金额"></el-input>
		</el-form-item>
		<el-form-item label="VIP优惠金额(元)" prop="vipDiscount"  v-if="dataForm.operationType==0">
			<el-input type= "number"  style="width: 150px" v-model="dataForm.vipDiscount" readonly placeholder="VIP优惠金额"></el-input>
		</el-form-item>
		<el-form-item label="优惠金额打码倍数" prop="discountMultiple"  v-if="dataForm.operationType==0">
			<el-input-number v-model="dataForm.discountMultiple" :precision="2" :step="0.1" :min="0" @change="countCoinNum" placeholder="优惠金额打码倍数"></el-input-number>
		</el-form-item>
    <el-form-item label="订单备注" prop="remake">
      <el-input type="textarea" style="width: 200px"   v-model="dataForm.remake" placeholder="订单备注"></el-input>
    </el-form-item>
		<div v-if="dataForm.operationType==0" style="color:red;margin-left: 30%;margin-right: 30%;">
			将增加打码量：{{codingCoin}}
		</div>
    </el-form>
    <span slot="footer"  style="float:left; padding-left:250px; " class="dialog-footer">

      <el-button v-if="showButton" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
		
  </div>
</template>

<script>
  export default {
    data () {
			var checkAmount = (rule, value, callback) => {
					if (value) {
						var retgex=/^\d+$/;
						if (!retgex.test(value)) {
								callback(new Error('请输入正整数'));
							}else if( Number(value)<=0){
								callback(new Error('操作金额不能小于等于0'));
							}else{
								callback();
							}
					} else {
						callback();
					}
				
			};
			var checkCoin = (rule, value, callback) => {
					if (value) {
						var retgex=/^\d+$/;
						if (!retgex.test(value)) {
								callback(new Error('请输入正整数'));
							}else if( Number(value)<0){
								callback(new Error('打码倍数不能小于0'));
							}else{
								callback();
							}
					} else {
						callback();
					}
				
			};
			var checkContent = (rule, value, callback) => {
					if (this.dataForm.sendMessage && !value) {
						callback(new Error('发送站内信的内容不能为空'));
					} else {
						callback();
					}
				
			};
      return {
        visible: false,
        popoverVisible: false,
				codingCoin:0,
        dataForm: {
          id: 0,
          amount: '',
          discountAmount: 0,
					vipDiscount: 0,
          takeCoin: '',
          remake: '',
          userId: '',
          hierarchyId: '',
          userAccount: '',
          userAccountQuery: '',
          userName: '无',
          money: 0,
          rechargeType: 0,
          discountId: 0,
					discountMultiple: 0,
					rechargeMultiple: 0,
					hierarchyName: '',
					totalMoney: 0,
					coin: 0,
					targetObject:1,
					operationType:1,
					designated:true
        },
				checkAll: false,
				checkOptions : [],
				checkAllOptions : [],
				isIndeterminate: true,
				showButton: true,
				hierarchyOptions:[],
        dataRule: {
          amount: [
            { required: true, message: '操作金额不能为空', trigger: 'blur' },
						{ validator: checkAmount, trigger: 'blur' }
          ],
// 					hierarchyId: [
//             { required: true, message: '层级优惠不能为空', trigger: 'blur' },
//           ],
          userAccount: [
						{ required: true, message: '待操作会员账号不能为空', trigger: 'blur' },
          ]
        }
      }
    },
    methods: {
			 onCopy(e) {
					if(this.dataForm.userAccount==''){
						this.$message({
							message: '复制内容为空！',
							type: 'warning',
							duration: 1000
						});
					}else{
						this.$message({
							message: '复制成功！',
							type: 'success',
							duration: 1000
						});
					}
			},
			onError(e) {
					this.$message.error("复制失败")
			},
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.dataForm.userName = '无';
					this.dataForm.money = 0;
					this.dataForm.coin = 0;
					this.dataForm.totalMoney = 0;
					this.dataForm.discountAmount = 0;
					this.dataForm.userAccountQuery="";
// 					this.$http({
// 						url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
// 						method: 'get',
// 						params: this.$http.adornParams({"hierarchyType":0})
// 					}).then(({data}) => {
// 						if (data && data.code === 200) {
// 							this.hierarchyOptions = data.hierarchyList
// 						}
// 					});
        })
      },
			getUser(){
				if(this.dataForm.userAccountQuery==''){
					this.$message.error("请先输入会员账号")
					return;
				}
				this.dataForm.discountAmount = 0;
				this.dataForm.vipDiscount = 0;
				this.dataForm.amount = 0;
				this.codingCoin = 0;

				this.$http({
					url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/getUser`),
					method: 'get',
					params: this.$http.adornParams({"account":this.dataForm.userAccountQuery})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.dataForm.userId = data.data.userId;
						if(data.data.userName){
							this.dataForm.userName = data.data.userName;
						}else{
							this.dataForm.userName="无";
						}
						this.dataForm.coin = data.data.coin/100;
						this.dataForm.money = data.data.money;
						this.dataForm.totalMoney = data.data.totalMoney;
						this.dataForm.discountMultiple = data.data.discountMultiple;
						this.dataForm.hierarchyId = data.data.hierarchyId;
						this.dataForm.hierarchyName = data.data.hierarchyName;
						this.dataForm.rechargeMultiple = data.data.rechargeMultiple;
						this.dataForm.userAccount=this.dataForm.userAccountQuery;
					}else{
						this.dataForm.userId="";
						this.dataForm.userAccount="";
						this.dataForm.userAccountQuery="";
						this.dataForm.userName="无";
						this.dataForm.money = 0;
						this.dataForm.totalMoney = 0;
						this.dataForm.coin = 0;
						this.dataForm.discountMultiple = 0;
						this.dataForm.hierarchyId = '';
						this.dataForm.hierarchyName = '';
						this.dataForm.rechargeMultiple = 0;
						this.$message.error(data.msg)
					}
				});
			},
			getDiscount(){
				if(this.dataForm.amount=='' || this.dataForm.hierarchyId==''|| this.dataForm.userAccount==''){
					return;
				}
				this.$http({
					url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/getDiscount`),
					method: 'get',
					params: this.$http.adornParams({
						"hierarchyId":this.dataForm.hierarchyId,
						"amount":this.dataForm.amount,
						"userAccount":this.dataForm.userAccount
						})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.dataForm.discountAmount = data.data.discountAmount;
						this.dataForm.vipDiscount = data.data.vipDiscountAmount;
						this.dataForm.discountId = data.data.discountId;
					}else{
						this.dataForm.discountAmount = 0;
						this.dataForm.vipDiscount = 0;
						this.dataForm.discountId = 0;
						this.$message.error(data.msg)
					}
					this.countCoinCode();
				});
				
				
			},
			countCoinCode(){//输入金额触发的
				//console.log("存入:"+this.dataForm.rechargeMultiple+"优惠："+this.dataForm.discountMultiple)
				this.codingCoin=(this.dataForm.discountAmount+this.dataForm.vipDiscount)*this.dataForm.discountMultiple
				+
				this.dataForm.amount*this.dataForm.rechargeMultiple;
			},
			countCodingCode(currentValue, oldValue){//存入金额打码倍数触发
				//console.log("存入:"+this.dataForm.rechargeMultiple+"---当前："+currentValue+"--旧的："+oldValue)
				this.codingCoin=(this.dataForm.discountAmount+this.dataForm.vipDiscount)*this.dataForm.discountMultiple
				+
				this.dataForm.amount*currentValue;
				
			},
			countCoinNum(currentValue, oldValue){//优惠金额打码倍数触发
			//console.log("优惠："+this.dataForm.discountMultiple+"当前："+currentValue+"旧的："+oldValue)
				this.codingCoin=(this.dataForm.discountAmount+this.dataForm.vipDiscount)*currentValue
				+
				this.dataForm.amount*this.dataForm.rechargeMultiple;
			},
			handleCheckAllChange(val) {
				this.dataForm.hierarchy = val ? this.checkAllOptions : [];
				this.isIndeterminate = false;
			},
			handleCheckedHierarchyChange(value) {
				let checkedCount = value.length;
				this.checkAll = checkedCount === this.hierarchyOptions.length;
				this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyOptions.length;
			},
			getHierarchyName(){
				this.dataForm.hierarchyName=[];
				if(this.checkAll){
					for(var i=0;i<this.hierarchyOptions.length;i++){
						this.dataForm.hierarchyName.push(this.hierarchyOptions[i].name);
					}
				}else{
					for(var j=0;j<this.dataForm.hierarchy.length;j++){
							for(var i=0;i<this.hierarchyOptions.length;i++){
								if(this.dataForm.hierarchy[j]==this.hierarchyOptions[i].id){
									this.dataForm.hierarchyName.push(this.hierarchyOptions[i].name);
									break;
								}
							}
					}
				}
			},
      // 表单提交
      dataFormSubmit () {
				//this.showButton=false;
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
						var confimMessage="";
						var remark="";
						var operation="";
						if(this.dataForm.operationType==0){
							operation="人工存款";
							remark=`为会员账号【${this.dataForm.userAccount}】${operation}【${this.dataForm.amount}】优惠金额【${this.dataForm.discountAmount}】Vip优惠金额【${this.dataForm.vipDiscount}】`;
						}else{
							operation="人工取款";
							remark=`为会员账号【${this.dataForm.userAccount}】${operation}【${this.dataForm.amount}】`;
						}
						// confimMessage=`确定对会员账号【${this.dataForm.userAccount}]进行${operation}操作?`;
						confimMessage=remark;
						var userOperater={};
						userOperater.memberId=this.dataForm.userId;
						userOperater.memberAccount=this.dataForm.userAccount ;
						userOperater.remark=remark;
						this.$confirm(confimMessage, '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							type: 'warning'
						}).then(() => {
								this.$http({
									url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/accessMoney`),
									method: 'post',
									data: this.$http.adornData({
										'id': this.dataForm.id || undefined,
										'amount': this.dataForm.amount ,
										'takeCoin': this.dataForm.takeCoin*100 ,
										'remake': this.dataForm.remake ,
										'account': this.dataForm.userAccount ,
										'targetObject': this.dataForm.targetObject ,
										'discountId': this.dataForm.discountId ,
										'discountAmount': this.dataForm.discountAmount ,
										'vipDiscount': this.dataForm.vipDiscount,
										'discountMultiple': this.dataForm.discountMultiple ,
										'rechargeMultiple': this.dataForm.rechargeMultiple ,
										'operationType': this.dataForm.operationType ,
										'designated': this.dataForm.designated ,
										'sendMessage':false ,
										'userOperater': userOperater
										
									})
								}).then(({data}) => {
									if (data && data.code === 200) {
										this.$message({
											message: '操作成功',
											type: 'success',
											duration: 1500,
											onClose: () => {
												this.visible = false
												this.$emit('refreshDataList')
											}
										})
									} else {
										this.$message.error(data.msg)
									}
									this.showButton=true;
                  this.$refs['dataForm'].resetFields()
								})
						})
          }else{
						this.showButton=true;
					}
					
        })
      }
    }
  }
</script>
