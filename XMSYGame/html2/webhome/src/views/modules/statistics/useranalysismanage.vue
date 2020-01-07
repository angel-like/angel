<template>
	<div class="mod-config">
		<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
			<el-tab-pane label="会员分析" v-if="isAuth('useranalysis:tabs:useranalysis')" name="useranalysis">
				<panl-useranalysis ref="panlUseranalysis"></panl-useranalysis>
			</el-tab-pane>
			<el-tab-pane label="单个会员分析" v-if="isAuth('useranalysis:tabs:usersingleanalysis')" name="usersingleanalysis">
				<panl-usersingleanalysis ref="panlUsersingleanalysis"></panl-usersingleanalysis>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>
<script>
	import PanlUseranalysis from './useranalysis'
	import PanlUsersingleanalysis from './usersingleanalysis'
	
  export default {
    data () {
      return {
        dataForm: {
			activeName: 'useranalysis',
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
      PanlUseranalysis,
	  PanlUsersingleanalysis,
    },
    activated () {
      //this.getDataList()
    },
    methods: {
		handleTab(tab, event) {
			if(tab.name=="useranalysis"){
				this.$nextTick(() => {
					this.$refs.panlUseranalysis.getDataList()
				})
			} else if(tab.name=="usersingleanalysis"){
				this.$nextTick(() => {
					this.$refs.panlUsersingleanalysis.getDataList()
				})
			}
			
		},
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
      }
     
    }
  }
</script>