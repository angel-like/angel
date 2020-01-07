<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="点杀监管" v-if="isAuth('userpointkill:tabs:userpointkillmanage')" name="userpointkillmanage">
				<panl-userpointkillmanage ref="panlUserbalance"></panl-userpointkillmanage><!--第二步修改权限路径     第三步进数据库添加-->
			</el-tab-pane>
			<el-tab-pane label="点杀名单" v-if="isAuth('userpointkill:tabs:userpointkill')" name="userpointkill">
				<panl-userpointkill ref="panlUserbalancerecord"></panl-userpointkill>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlUserpointkillmanage from './userpointkillmanage'
	import PanlUserpointkill from './userpointkill'
	//第一步修改导入的文件
	export default {
		data() {
			return {
				dataForm: {
					activeName: 'userpointkillmanage',
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
			PanlUserpointkillmanage ,
			PanlUserpointkill,
		},
		activated() {
			this.getDataList()
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
				}

			},
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
			}

		}
	}
</script>
