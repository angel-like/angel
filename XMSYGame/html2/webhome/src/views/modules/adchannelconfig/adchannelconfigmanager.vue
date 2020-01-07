<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="渠道配置" v-if="isAuth('adchannelconfig:tabs:adchannelconfig')" name="adchannelconfig">
				<panl-adchannelconfig ref="panlAdchannelconfig"></panl-adchannelconfig>
			</el-tab-pane>
			<el-tab-pane label="渠道统计" v-if="isAuth('adchannelconfig:tabs:channelstatistics')" name="channelstatistics">
				<panl-channelstatistics ref="panlChannelstatistics"></panl-channelstatistics>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlAdchannelconfig from './adchannelconfig'
	import PanlChannelstatistics from './channelstatistics'

	export default {
		data() {
			return {
				dataForm: {
					activeName: 'adchannelconfig',
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
			PanlAdchannelconfig,
			PanlChannelstatistics,
		},
		activated() {
			//this.getDataList()
		},
		methods: {
			handleTab(tab, event) {
				if (tab.name == "adchannelconfig") {
					this.$nextTick(() => {
						this.$refs.panlAdchannelconfig.getDataList()
					})
				} else if (tab.name == "channelstatistics") {
					this.$nextTick(() => {
						this.$refs.panlChannelstatistics.getDataList()
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
