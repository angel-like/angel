<template>
  <div class="mod-config">
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="名称">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    data () {
      return {
    dataList: [],
    dataListLoading: false,
      }
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/ordertakemoney/ordertakemoney/channel'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.data
          } else {
            this.dataList = []
          }
          this.dataListLoading = false
        })
      }
    }
  }
</script>
