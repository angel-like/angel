<template>
  <div class="mod-config">
   <el-tabs v-model="activeName" @tab-click="handleClick">
			<el-tab-pane label="APP" name="APP">
				<Imappdownloadmanage  ref="Imappdownloadmanage" ></Imappdownloadmanage>
			</el-tab-pane>
			<el-tab-pane label="PC" name="PC" v-if="false"><!--先隐藏掉-->
				<Imwebdownloadmanage  ref="Imwebdownloadmanage" ></Imwebdownloadmanage>
		</el-tab-pane>
		</el-tabs>
  </div>
</template>

<script>
  import Imappdownloadmanage from './imappdownloadmanage'
	import Imwebdownloadmanage from './imwebdownloadmanage'
  export default {
    data () {
      return {
				activeName: 'APP',
      }
    },
    components: {
      Imappdownloadmanage,
			Imwebdownloadmanage
    },
    methods: {
//       addOrUpdateHandle (id) {
//         this.addOrUpdateVisible = true
//         this.$nextTick(() => {
//           this.$refs.addOrUpdate.init(id)
//         })
//       },
			handleClick(tab, event) {
				if(this.activeName='APP'){
					this.$nextTick(() => {
					this.$refs.Imappdownloadmanage.getDataList()
					})
				}
				if(this.activeName='PC'){
					this.$nextTick(() => {
					this.$refs.Imwebdownloadmanage.getDataList()
					})
				}
			},
    }
  }
</script>
