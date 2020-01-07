<template>
	<div>
		<el-dialog :title=" '会员统计详情'" :close-on-click-modal="false" :visible.sync="visible" append-to-body>
			
			
			<el-row>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-tag>基本信息</el-tag>
             <!-- <el-button size="mini" type="primary" round style="float: right;" >操作按钮</el-button> -->
            </div>
            <div class="text item">
              <span>账号：</span>{{account}}
            </div>
            <div class="text item">
              <span>真实姓名：</span>{{userName}}
            </div>
						<div class="text item">
							<span>手机号码：</span>{{phone}}
						</div>
            <div class="text item">
              <span>注册时间：</span>{{createTime}}
            </div>
            <div class="text item">
              <span>银行卡号：</span>{{bankCard}}
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-tag>会员钱包</el-tag>
			  <el-button size="mini" type="primary" round @click="transactionRecord()" style="float: right;" >交易记录</el-button>
            </div>
            <div class="text item">
              <span>账户金币：</span>{{coin}}
            </div>
            <div class="text item">
              <span>账户余额：</span>{{money}}
            </div>
			<div class="text item">
			  <span>房卡数量：</span>{{roomCard}}
			</div>
            <!-- <div class="text item">
              &nbsp;
            </div> -->
            <div class="text item">
              &nbsp;
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix" v-if="rechargeNum>0">
              <el-tag>存款记录</el-tag>
			  <el-dropdown style="float: right;">
				<el-button size="mini" type="primary" round  style="float: right;" v-if="rechargeNum>0">存款记录</el-button>
			  	<el-dropdown-menu slot="dropdown">
			  		<el-dropdown-item @click.native="thirdRechargeMoney()">第三方支付</el-dropdown-item>
			  		<el-dropdown-item @click.native="bankRechargeMoney()">银行卡存款</el-dropdown-item>
			  		<el-dropdown-item @click.native="adminRechargeMoney()">人工充值</el-dropdown-item>
			  	</el-dropdown-menu>
			  </el-dropdown>
            </div>
			<div slot="header" class="clearfix" v-else>
			<el-tag>存款记录</el-tag>
				<el-button size="mini" type="primary" round  style="float: right;" disabled>存款记录</el-button>
			</div>
            <div class="text item">
              <span>上次存款：</span>{{lastRechargeAmount}}
            </div>
            <div class="text item">
              <span>存款时间：</span>{{lastRechargeAmountDate}}
            </div>
            <div class="text item">
              <span>存款次数：</span>{{rechargeNum}}
            </div>
            <div class="text item">
              <span>存款总额：</span>{{rechargeAmount}}
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-tag>取款记录</el-tag>
			  <el-button size="mini" type="primary" round  style="float: right;" @click="takeMoney()" v-if="takeNum>0">取款记录</el-button>
			  <el-button size="mini" type="primary" round  style="float: right;" @click="takeMoney()" v-else disabled>取款记录</el-button>
            </div>
            <div class="text item">
              <span>上次取款：</span>{{lastTakeAmount}}
            </div>
            <div class="text item">
              <span>取款时间：</span>{{lastTakeAmountDate}}
            </div>
            <div class="text item">
              <span>取款次数：</span>{{takeNum}}
            </div>
            <div class="text item">
              <span>取款总额：</span>{{takeAmount}}
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-tag>概率统计</el-tag>
			  <el-button size="mini" type="primary" round  style="float: right;"  @click="gameRecord()" v-if="(matchWinNum+matchLoseNum+matchDrawNum)>0||(hundredWinNum+hundredLoseNum+hundredDrawNum)>0">游戏记录</el-button>
			  <el-button size="mini" type="primary" round  style="float: right;"  @click="gameRecord()" v-else disabled>游戏记录</el-button>
            
			</div>
            <div class="text item">
              <span>百人场输赢：</span>赢{{matchWinNum}}/输{{matchLoseNum}}/和{{matchDrawNum}}
            </div>
            <div class="text item">
              <span>百人场胜率：</span>{{matchProbability}}%
            </div>
            <div class="text item">
              <span>匹配场输赢：</span>赢{{hundredWinNum}}/输{{hundredLoseNum}}/和{{hundredDrawNum}}
            </div>
            <div class="text item">
              <span>匹配场胜率：</span>{{hundredProbability}}%
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <el-tag>会员代理</el-tag>
			  <el-button size="mini" type="primary" round  style="float: right;"@click="getSubordinateList()" v-if="num>0">邀请记录</el-button>
			  <el-button size="mini" type="primary" round  style="float: right;"@click="getSubordinateList()" v-else disabled>邀请记录</el-button>
            </div>
            <div class="text item">
              <span>邀请码：</span>{{recommendationCode}}
            </div>
            <div class="text item">
              <span>下级人数：</span>{{num}}
            </div>
            <div class="text item">
              <span>代理等级：</span>{{agentHierarchyName}}
            </div>
            <div class="text item">
              <span>佣金余额：</span>{{commission}}
            </div>
          </el-card>
        </el-col>
      </el-row>
			
			
			
			
			
			<!-- <el-card class="box-card">
				<div slot="header" class="clearfix">
					<span>基本信息</span>
				</div>
				<div class="text item">
					<el-row :gutter="20">
						<el-col :span="10">
							账号：{{account}}
						</el-col>
						<el-col :span="10">
							注册时间：{{createTime}}
						</el-col>
						<el-col :span="10">
							真实姓名：{{userName}}
						</el-col>
						<el-col :span="10">
							银行卡号：{{bankCard}}
						</el-col>
						<div v-if="online" style="font-size: 10px; color:red;">({{deviceType}})在线:/{{gameName}}</div>
						<div v-else style="color: #6F7180;">
							离线
						</div>
					</el-row>
				</div>
			</el-card> -->
			<!-- <el-card class="box-card">
				<el-row :gutter="20">
					<el-col :span="10">
						金币余额：{{coin}}
					</el-col>
					<el-col :span="6">
						账户余额：{{money}}
					</el-col>
					<el-col :span="6">
						<a style="cursor:pointer " @click="transactionRecord()">交易记录</a>
					</el-col>
				</el-row>
			</el-card>
			<el-card class="box-card">
				<el-row :gutter="20">
					<el-col :span="10">
						上次存款：{{lastRechargeAmount}}
					</el-col>
					<el-col :span="10">
						存款时间：{{lastRechargeAmountDate}}
					</el-col>
					<el-col :span="10">
						存款次数：{{rechargeNum}}
					</el-col>
					<el-col :span="6">
						存款总额：{{takeAmount}}
					</el-col>
					<el-col :span="5">
						<el-dropdown>
							<a style="cursor:pointer " v-if="rechargeNum>0">存款记录</a>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item @click.native="thirdRechargeMoney()">第三方支付</el-dropdown-item>
								<el-dropdown-item @click.native="bankRechargeMoney()">银行卡存款</el-dropdown-item>
								<el-dropdown-item @click.native="adminRechargeMoney()">人工充值</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</el-col>
				</el-row>
			</el-card>
			<el-card class="box-card">
				<el-row :gutter="20">
					<el-col :span="10">
						上次取款：{{lastTakeAmount}}
					</el-col>
					<el-col :span="10">
						取款时间：{{lastTakeAmountDate}}
					</el-col>
					<el-col :span="10">
						取款次数：{{takeNum}}
					</el-col>
					<el-col :span="6">
						取款总额：{{takeAmount}}
					</el-col>
					<el-col :span="5">
						<a style="cursor:pointer " @click="takeMoney()" v-if="takeNum>0">取款记录</a>
					</el-col>
				</el-row>
			</el-card> -->
			<!-- <el-card class="box-card">
				<el-row :gutter="20">
					<el-col :span="10">
						百人场输赢：赢{{matchWinNum}}/输{{matchLoseNum}}/和{{matchDrawNum}}
					</el-col>
					<el-col :span="10">
						百人场概率：{{matchProbability}}%
					</el-col>
					<el-col :span="10">
						匹配场输赢：赢{{hundredWinNum}}/输{{hundredLoseNum}}/和{{hundredDrawNum}}
					</el-col>
					<el-col :span="6">
						匹配场概率：{{hundredProbability}}%
					</el-col>
					<el-col :span="5">
						<a style="cursor:pointer " @click="gameRecord()" v-if="(matchWinNum+matchLoseNum+matchDrawNum)>0||(hundredWinNum+hundredLoseNum+hundredDrawNum)>0">游戏记录</a>
					</el-col>
				</el-row>
			</el-card> -->
			<!-- <el-card class="box-card">
				<el-row :gutter="20">
					<el-col :span="10">
						邀请码：{{recommendationCode}}
					</el-col>
					<el-col :span="10">
						代理等级：{{agentHierarchyName}}
					</el-col>
					<el-col :span="10">
						下级人数：{{num}}
					</el-col>
					<el-col :span="6">
						佣金余额：{{commission}}
					</el-col>
					<el-col :span="5">
						<a style="cursor:pointer " @click="getSubordinateList()" v-if="num>0">邀请记录</a>
					</el-col>
				</el-row>
			</el-card> -->
		</el-dialog>
		
	</div>

</template>
<!-- 弹窗, 下级代理 -->

<script>
	
	export default {
		
		data() {
			return {
				visible: false,
				account: '',
				userName: '',
				phone: '',
				coin: 0,
				money: 0,
				roomCard:0,
				lastTakeAmount: 0,
				lastRechargeAmount: 0,
				rechargeNum: 0,
				rechargeAmount: 0,
				takeAmount: 0,
				takeNum: 0,
				createTime: '',
				bankCard: '',
				lastTakeAmountDate: '',
				lastRechargeAmountDate: '',
				online: null,
				deviceType: '',
				matchWinNum: 0,
				matchLoseNum: 0,
				matchDrawNum: 0,
				matchProbability: 0,
				hundredWinNum: 0,
				hundredLoseNum: 0,
				hundredDrawNum: 0,
				hundredProbability: 0,
				gameName: null,
				commission: 0,
				agentHierarchyName: '',
				num: 0,
				recommendationCode: '',
				subordinateVisible: true,
				userId: null
			}
		},
		methods: {
			initEdit(user) {
				console.log(user)
				this.visible = true
				this.userId = user.id
				this.account = user.account
				this.userName = user.userName
				this.phone = user.phone
				this.money = user.money
				this.roomCard = user.roomCard
				this.coin = user.coin
				this.lastRechargeAmount = user.lastRechargeAmount
				this.rechargeNum = user.rechargeNum
				this.rechargeAmount = user.rechargeAmount
				this.takeAmount = user.takeAmount
				this.takeAmount = user.takeAmount
				this.lastTakeAmount = user.lastTakeAmount
				this.takeNum = user.takeNum
				this.createTime = user.createTime
				this.bankCard = user.bankCard
				this.lastTakeAmountDate = user.lastTakeAmountDate
				this.lastRechargeAmountDate = user.lastRechargeAmountDate
				this.online = user.online
				this.deviceType = user.deviceType
				this.gameName = user.gameName
				this.commission = user.commission
				this.$http({
					url: this.$http.adornUrl(`/user/user/userGameRecordStatics`),
					method: 'get',
					params: this.$http.adornParams({
						"userId": user.id
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						//匹配场
						this.matchWinNum = data.matchMap.winNum
						this.matchLoseNum = data.matchMap.loseNum
						this.matchDrawNum = data.matchMap.drawNum
						//总场次
						var matchnum = data.matchMap.winNum + data.matchMap.loseNum + data.matchMap.drawNum
						if (0 >= matchnum) {
							this.matchProbability = 0
						} else {
							this.matchProbability = Math.round(data.matchMap.winNum / matchnum * 100)
						}
						//百人场
						this.hundredWinNum = data.hundredMap.winNum
						this.hundredLoseNum = data.hundredMap.loseNum
						this.hundredDrawNum = data.hundredMap.drawNum
						//总场次
						var hundrednum = data.hundredMap.winNum + data.hundredMap.loseNum + data.hundredMap.drawNum
						if (0 >= hundrednum) {
							this.hundredProbability = 0
						} else {
							console.log(hundrednum)
							this.hundredProbability = Math.round(data.hundredMap.winNum / hundrednum * 100)
						}
					}
				});
				this.$http({
					url: this.$http.adornUrl(`/recommendationcode/recommendationcode/userRecommendationDetails`),
					method: 'get',
					params: this.$http.adornParams({
						"userId": user.id
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.agentHierarchyName = data.data.agentHierarchyName
						this.num = data.data.num
						this.recommendationCode = data.data.recommendationCode
					}
				});
			},
			//人工订单跳转跳转
			adminRechargeMoney() {
				this.visible = false
				this.$router.push({
					path: 'orderrecharge-enter',
					query: {
						account: this.account,
            name:'orderadministrator'
					}
				})
			},
			//银行卡订单跳转
			bankRechargeMoney() {
				this.visible = false
				this.$router.push({
					path: 'orderrecharge-enter',
					query: {
						account: this.account,
            name:'orderRecharge'
					}
				})
			},
			//第三方订单跳转
			thirdRechargeMoney() {
				this.visible = false
				this.$router.push({
					path: 'orderrecharge-enter',
					query: {
						account: this.account,
            name:'orderthirdRecharge'
					}
				})
			},
			//交易记录跳转gameRecord
			transactionRecord() {
				this.visible = false
				this.$router.push({
					path: 'usertransactionrecord-usertransactionrecord',
					query: {
						account: this.account
					}
				})
			},
			//游戏记录跳转
			gameRecord() {
				this.visible = false
				this.$router.push({
					path: 'account-usergamerecord',
					query: {
						account: this.account
					}
				})
			},
			//取款记录跳转
			takeMoney() {
				this.visible = false
				this.$router.push({
					path: 'orderrecharge-out',
					query: {
						account: this.account
					}
				})
			},
			// 获取下线列表
			getSubordinateList() {
				this.$emit('recordingFlag',this.userId)
			}
		}
	}
</script>
<style >
 .statistical-details .el-dialog__body{
  padding: 0;
}
.statistical-details  .box-card{
  margin: 10px;
}
.statistical-details .el-card__header{
  padding: 8px 20px;
}
.statistical-details .item span{
  display: inline-block;
  text-align: right;
  width: 90px;
}

</style>
