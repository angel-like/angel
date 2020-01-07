<template>
  <div class="dashboard-editor-container">

    <el-row :gutter="32">
		
      <el-col :xs="24" :sm="24" :lg="6">
         <div style="padding-right:25px;">
           <panel-group  />
         </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="18">
        <div class="chart-wrapper">
          <bar-chart/>
        </div>
      </el-col>
    </el-row>



  </div>
</template>

<script>
// 饼状图
import PanelGroup from './component3/PanelGroup'
import BarChart from './component3/BarChart'
import BoxCard from './component3/BoxCard'

export default {
  name: 'DashboardAdmin',
  components: {
    BoxCard,
    BarChart,
		PanelGroup
  },
  data () {
    return {

    }
  },
	created(){
	},
  methods: {

  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    border-radius: 15px;
  }
}
</style>
