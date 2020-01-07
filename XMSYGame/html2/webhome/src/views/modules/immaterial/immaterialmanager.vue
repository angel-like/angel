<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="活动优惠" v-if="isAuth('immaterial:tabs:activity')" name="activity">
				<panl-activity ref="panlActivity"></panl-activity>
				<!--第二步修改权限路径     第三步进数据库添加-->
			</el-tab-pane>
			<el-tab-pane label="公告类" v-if="isAuth('immaterial:tabs:notice')" name="notice">
				<panl-notice ref="PanlNotice"></panl-notice>
			</el-tab-pane>
			<el-tab-pane label="帮助中心" v-if="isAuth('immaterial:tabs:help')" name="help">
				<panl-help ref="panlHelp"></panl-help>
			</el-tab-pane>
			<el-tab-pane label="游戏管理" v-if="isAuth('immaterial:tabs:game')" name="game">
				<panl-game ref="panlGame"></panl-game>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	//第一步修改导入的文件
	import PanlActivity from './immaterial-activity'
	import PanlNotice from './immaterial-notice'
	import panlHelp from './immaterial-help'
	import panlGame from './immaterial-game'
	//import PanlRecord from './giftbagexchangerecord'

	export default {
		data() {
			return {
				dataForm: {
					activeName: 'activity',
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
			PanlActivity,
			PanlNotice,
			panlHelp,
			panlGame
		},
		activated() {
			this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "activity") {
					this.$nextTick(() => {
						this.$refs.panlActivity.getDataList()
					})
				} else if (tab.name == "notice") {
					this.$nextTick(() => {
						this.$refs.PanlNotice.getDataList()
					})
				} else if (tab.name == "help") {
					this.$nextTick(() => {
						this.$refs.panlHelp.getDataList()
					})
				} else if (tab.name == "game") {
					this.$nextTick(() => {
						this.$refs.panlGame.getDataList()
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
