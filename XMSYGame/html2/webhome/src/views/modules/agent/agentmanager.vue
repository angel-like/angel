<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="推广员账号" v-if="isAuth('agent:tabs:agent')" name="agent">
				<panl-agent ref="panlAgent"></panl-agent>
			</el-tab-pane>
			<el-tab-pane label="等级设定" v-if="isAuth('agent:tabs:useragenthierarchy')" name="useragenthierarchy">
				<panl-useragenthierarchy ref="panlUseragenthierarchy"></panl-useragenthierarchy>
			</el-tab-pane>
			<el-tab-pane label="推广员等级管理" v-if="isAuth('agent:tabs:userrecommendationgrade')" name="userrecommendationgrade">
				<panl-userrecommendationgrade ref="panlUserrecommendationgrade"></panl-userrecommendationgrade>
			</el-tab-pane>
			<el-tab-pane label="推广记录" v-if="isAuth('agent:tabs:userrebatecommissionrecorddetail')" name="userrebatecommissionrecorddetail">
				<panl-userrebatecommissionrecorddetail ref="panlUserrebatecommissionrecorddetail"></panl-userrebatecommissionrecorddetail>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlAgent from './agent'
	import PanlUseragenthierarchy from './useragenthierarchy'
	import PanlUserrecommendationgrade from './userrecommendationgrade'
	import PanlUserrebatecommissionrecorddetail from './userrebatecommissionrecorddetail'
	export default {
		data() {
			return {
				dataForm: {
					activeName: 'agent',
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
			PanlAgent ,
			PanlUseragenthierarchy,
			PanlUserrecommendationgrade,
			PanlUserrebatecommissionrecorddetail,
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "agent") {
					this.$nextTick(() => {
						this.$refs.panlAgent.getDataList()
					})
				} else if (tab.name == "useragenthierarchy") {
					this.$nextTick(() => {
						this.$refs.panlUseragenthierarchy.getDataList()
					})
				} else if (tab.name == "userrecommendationgrade") {
					this.$nextTick(() => {
						this.$refs.panlUserrecommendationgrade.getDataList()
					})
				}else if (tab.name == "userrebatecommissionrecorddetail") {
					this.$nextTick(() => {
						this.$refs.panlUserrebatecommissionrecorddetail.getDataList()
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
