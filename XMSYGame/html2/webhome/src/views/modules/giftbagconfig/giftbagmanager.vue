<template>
	<div class="mod-config">
			<el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
				<el-tab-pane label="兑换码管理" v-if="isAuth('giftbagconfig:tabs:normal')" name="normal">
					<panl-normal ref="panlNormal"></panl-normal><!--第二步修改权限路径     第三步进数据库添加-->
				</el-tab-pane>
				<el-tab-pane label="金币兑换道具" v-if="isAuth('giftbagconfig:tabs:shoproomcardproportion')" name="shoproomcardproportion">
					<panl-shoproomcardproportion ref="panlShoproomcardproportion"></panl-shoproomcardproportion><!--第二步修改权限路径     第三步进数据库添加-->
				</el-tab-pane>
			</el-tabs>
	</div>
</template>
<script>
	import PanlNormal from './giftbagconfig'
	import PanlShoproomcardproportion from './shoproomcardproportion'
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
			PanlShoproomcardproportion,
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
			}else if(tab.name=="shoproomcardproportion"){
				this.$nextTick(() => {
					this.$refs.panlShoproomcardproportion.getDataList()
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