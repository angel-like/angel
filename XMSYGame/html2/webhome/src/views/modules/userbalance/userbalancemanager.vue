<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="余额宝" v-if="isAuth('userbalance:tabs:userbalance')" name="userbalance">
				<panl-userbalance ref="panlUserbalance"></panl-userbalance>
			</el-tab-pane>
			<el-tab-pane label="存取记录" v-if="isAuth('userbalance:tabs:userbalancerecord')" name="userbalancerecord">
				<panl-userbalancerecord ref="panlUserbalancerecord"></panl-userbalancerecord>
			</el-tab-pane>
			<el-tab-pane label="收益明细" v-if="isAuth('userbalance:tabs:userprofitrecord')" name="userprofitrecord">
				<panl-userprofitrecord ref="panlUserprofitrecord"></panl-userprofitrecord>
			</el-tab-pane>
			<el-tab-pane label="利率设置" v-if="isAuth('userbalance:tabs:userbalancerate')" name="userbalancerate">
				<panl-userbalancerate ref="panlUserbalancerate"></panl-userbalancerate>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlUserbalance from './userbalance'
	import PanlUserbalancerate from './userbalancerate'
	import PanlUserbalancerecord from './userbalancerecord'
	import PanlUserprofitrecord from './userprofitrecord'
	export default {
		data() {
			return {
				dataForm: {
					activeName: 'userbalance',
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
			PanlUserbalance ,
			PanlUserbalancerate,
			PanlUserbalancerecord,
			PanlUserprofitrecord,
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "userbalance") {
					this.$nextTick(() => {
						this.$refs.panlUserbalance.getDataList()
					})
				} else if (tab.name == "userbalancerate") {
					this.$nextTick(() => {
						this.$refs.panlUserbalancerate.getDataList()
					})
				} else if (tab.name == "userbalancerecord") {
					this.$nextTick(() => {
						this.$refs.panlUserbalancerecord.getDataList()
					})
				}else if (tab.name == "userprofitrecord") {
					this.$nextTick(() => {
						this.$refs.panlUserprofitrecord.getDataList()
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
