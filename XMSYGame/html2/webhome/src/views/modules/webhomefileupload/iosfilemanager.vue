<template>
	<div class="mod-config">
			<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
				<el-tab-pane label="苹果证书管理" v-if="isAuth('iosfileupload:tabs:normal')" name="normal">
					<panl-normal ref="panlNormal"></panl-normal><!--第二步修改权限路径     第三步进数据库添加-->
				</el-tab-pane>
				<el-tab-pane label="安装包" v-if="isAuth('filepackage:tabs:record')" name="record">
					<panl-record ref="panlRecord"></panl-record>
				</el-tab-pane>
			</el-tabs>
	</div>
</template>
<script>
	import PanlNormal from './webhomefileupload'
	import PanlRecord from './webhomefilepackage'
	//第一步修改导入的文件
  export default {
    data () {
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
	  PanlRecord
    },
    activated () {
      this.getDataList()
    },
    methods: {
		handleTab(tab, event) {
			if(tab.name=="normal"){
				this.$nextTick(() => {
					this.$refs.panlNormal.getDataList()
				})
			} else if(tab.name=="record"){
				this.$nextTick(() => {
					this.$refs.panlRecord.getDataList()
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