<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="推广层级管理" v-if="isAuth('userhierarchy:tabs:normal')" name="normal">
				<panl-normal ref="panlNormal"></panl-normal>
			</el-tab-pane>
			<!--<el-tab-pane label="风控层级管理" v-if="isAuth('userhierarchy:tabs:risk')" name="risk">-->
				<!--<panl-risk ref="panlRisk"></panl-risk>-->
			<!--</el-tab-pane>-->
			<!--<el-tab-pane label="会员风控配置" v-if="isAuth('userhierarchy:tabs:riskconfig')" name="riskConfig">-->
				<!--<panl-riskConfig ref="panlRiskConfig"></panl-riskConfig>-->
			<!--</el-tab-pane>-->
		</el-tabs>
	</div>
</template>
<script>
	import PanlNormal from './usernormalhierarchy'
	import PanlRisk from './userhierarchyrisk'
	import PanlRiskConfig from './userriskconfig'

	export default {
		data() {
			return {
				dataForm: {
					activeName: 'normal',
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
			PanlNormal,
			PanlRisk,
			PanlRiskConfig
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "normal") {
					this.$nextTick(() => {
						this.$refs.panlNormal.getDataList()
					})
				} else if (tab.name == "risk") {
					this.$nextTick(() => {
						this.$refs.panlRisk.getDataList()
					})
				} else if (tab.name == "riskConfig") {
					this.$nextTick(() => {
						this.$refs.panlRiskConfig.getDataList()
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
