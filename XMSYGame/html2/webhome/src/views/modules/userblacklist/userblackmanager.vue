<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="ip地址黑名单" v-if="isAuth('userhierarchy:tabs:ipblacklist')" name="ipblacklist">
				<panl-ipblacklist ref="panlIpblacklist"></panl-ipblacklist>
			</el-tab-pane>
			<el-tab-pane label="会员账号黑名单" v-if="isAuth('userhierarchy:tabs:userblacklist')" name="userblacklist">
				<panl-userblacklist ref="panlUserblacklist"></panl-userblacklist>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlUserblacklist from './userblacklist'
	import PanlIpblacklist from './ipblacklist'

	export default {
		data() {
			return {
				dataForm: {
					activeName: 'ipblacklist',
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
			PanlUserblacklist,
			PanlIpblacklist,
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "userblacklist") {
					this.$nextTick(() => {
						this.$refs.panlUserblacklist.getDataList()
					})
				} else if (tab.name == "ipblacklist") {
					this.$nextTick(() => {
						this.$refs.panlIpblacklist.getDataList()
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
