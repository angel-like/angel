<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="分享记录" v-if="isAuth('useractivitiesmanager:tabs:sharerecord')" name="sharerecord">
				<panl-sharerecord ref="panlSharerecord"></panl-sharerecord>
			</el-tab-pane>
			<el-tab-pane label="签到记录" v-if="isAuth('useractivitiesmanager:tabs:signuserrecord')" name="signuserrecord">
				<panl-signuserrecord ref="panlSignuserrecord"></panl-signuserrecord>
			</el-tab-pane>
			<el-tab-pane label="游戏任务领取记录" v-if="isAuth('useractivitiesmanager:tabs:playertasksreceiverecord')" name="playertasksreceiverecord">
				<panl-playertasksreceiverecord ref="panlPlayertasksreceiverecord"></panl-playertasksreceiverecord>
			</el-tab-pane>
			<el-tab-pane label="道具领取记录" v-if="isAuth('useractivitiesmanager:tabs:usergiftrecord')" name="usergiftrecord">
				<panl-usergiftrecord ref="panlUsergiftrecord"></panl-usergiftrecord>
			</el-tab-pane>
			<el-tab-pane label="道具交易记录" v-if="isAuth('useractivitiesmanager:tabs:shopproprecord')" name="shopproprecord">
				<panl-shopproprecord ref="panlShopproprecord"></panl-shopproprecord>
			</el-tab-pane>
			<el-tab-pane label="兑换码兑换记录" v-if="isAuth('useractivitiesmanager:tabs:giftbagexchangerecord')" name="giftbagexchangerecord">
				<panl-giftbagexchangerecord ref="panlGiftbagexchangerecord"></panl-giftbagexchangerecord>
			</el-tab-pane>
			<el-tab-pane label="登录记录" v-if="isAuth('useractivitiesmanager:tabs:userlogin')" name="userlogin">
				<panl-userlogin ref="panlUserlogin"></panl-userlogin>
			</el-tab-pane>
      <el-tab-pane label="会员抽奖记录" v-if="isAuth('useractivitiesmanager:tabs:luckyrecord')" name="luckyrecord">
        <panl-luckyrecord ref="panlLuckyrecord"></panl-luckyrecord>
        <!--<panl-userlogin ref="panlUserlogin"></panl-userlogin>-->
      </el-tab-pane>
      <el-tab-pane label="天降财神活动记录" v-if="isAuth('useractivitiesmanager:tabs:fortune')" name="fortune">
        <panl-fortune ref="panlFortune"></panl-fortune>
        <!--<panl-userlogin ref="panlUserlogin"></panl-userlogin>-->
      </el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlSharerecord from './sharerecord'
	import PanlUsergiftrecord from './usergiftrecord'
	import PanlSignuserrecord from './signuserrecord'
	import PanlUserlogin from './userlogin'
	import PanlShopproprecord from './shopproprecord'
	import PanlPlayertasksreceiverecord from './playertasksreceiverecord'
	import PanlGiftbagexchangerecord from './giftbagexchangerecord'
  import PanlLuckyrecord  from  '../luckyuserrecord/luckyuserrecord'
  import PanlFortune from  '../fortuneactivitymanager/enveloperecord'
	export default {
		data() {
			return {
				dataForm: {
					activeName: 'sharerecord',
					paramKey: ''
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
		components: {
			PanlSharerecord,
			PanlUsergiftrecord,
			PanlSignuserrecord,
			PanlUserlogin,
			PanlShopproprecord,
			PanlPlayertasksreceiverecord,
			PanlGiftbagexchangerecord,
      PanlLuckyrecord,
      PanlFortune
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "sharerecord") {
					this.$nextTick(() => {
						this.$refs.panlSharerecord.getDataList()
					})
				} else if (tab.name == "signuserrecord") {
					this.$nextTick(() => {
						this.$refs.panlSignuserrecord.getDataList()
					})
				} else if (tab.name == "playertasksreceiverecord") {
					this.$nextTick(() => {
						this.$refs.panlPlayertasksreceiverecord.getDataList()
					})
				}else if (tab.name == "usergiftrecord") {
					this.$nextTick(() => {
						this.$refs.panlUsergiftrecord.getDataList()
					})
				}else if (tab.name == "shopproprecord") {
					this.$nextTick(() => {
						this.$refs.panlShopproprecord.getDataList()
					})
				}else if (tab.name == "userlogin") {
					this.$nextTick(() => {
						this.$refs.panlUserlogin.init()
					})
				}else if (tab.name == "giftbagexchangerecord") {
					this.$nextTick(() => {
						this.$refs.panlGiftbagexchangerecord.getDataList()
					})
				}else if (tab.name == "luckyrecord") {
          this.$nextTick(() => {
            this.$refs.panlLuckyrecord.getDataList()
          })
        }
        else if (tab.name == "fortune") {
          this.$nextTick(() => {
            this.$refs.panlFortune.getDataList()
          })
        }
			},
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
			}

		}
	}
</script>
