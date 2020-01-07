<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="排行榜" v-if="isAuth('rankingmanager:tabs:rankinglist')" name="rankinglist">
				<panl-rankinglist ref="panlRankinglist"></panl-rankinglist>
			</el-tab-pane>
			<el-tab-pane label="日排行" v-if="isAuth('rankingmanager:tabs:rankinglistday')" name="rankinglistday">
				<panl-rankinglistday ref="panlRankinglistday"></panl-rankinglistday>
			</el-tab-pane>
			<el-tab-pane label="周排行" v-if="isAuth('rankingmanager:tabs:rankinglistweek')" name="rankinglistweek">
				<panl-rankinglistweek ref="panlRankinglistweek"></panl-rankinglistweek>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlRankinglist from './rankinglist'
	import PanlRankinglistday from './rankinglistday'
	import PanlRankinglistweek from './rankinglistweek'

	export default {
		data() {
			return {
				dataForm: {
					activeName: 'rankinglist',
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
			PanlRankinglist,
			PanlRankinglistday,
			PanlRankinglistweek
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "rankinglist") {
					this.$nextTick(() => {
						this.$refs.panlRankinglist.getDataList()
					})
				} else if (tab.name == "rankinglistday") {
					this.$nextTick(() => {
						this.$refs.panlRankinglistday.getDataList()
					})
				} else if (tab.name == "rankinglistweek") {
					this.$nextTick(() => {
						this.$refs.panlRankinglistweek.getDataList()
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
