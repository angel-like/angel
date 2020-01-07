<template>
	<div class="mod-config">
			<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
				<el-tab-pane label="游戏记录" v-if="isAuth('gamerecord:gamerecord:list')" name="record">
					<panl-gamerecord></panl-gamerecord>
				</el-tab-pane>
				<!-- <el-tab-pane label="游戏分析" v-if="isAuth('gamerecord:gamerecord:list')" name="analysis">
					<panl-gameanalysis></panl-gameanalysis>
				</el-tab-pane> -->
			</el-tabs>
	</div>
</template>
<script>
/* 	import PanlGameanalysis from './gameanalysis'
 */	import PanlGamerecord from './gamerecord'
  export default {
    data () {
      return {
        dataForm: {
					activeName: 'record',
					paramKey: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
      }
    },
    components: {
/*    PanlGameanalysis,
 */	  PanlGamerecord
    },
    activated () {
      //this.getDataList()
    },
    methods: {
		handleTab(tab, event) {
			
		},
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        
      }
     
    }
  }
</script>