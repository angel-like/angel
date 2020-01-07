<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
      <el-tab-pane label="站内信" v-if="isAuth('message:tabs:messagemanagement')" name="messagemanagement">
        <Messagemanagement ref="messagemanagement"></Messagemanagement>
      </el-tab-pane>
      <el-tab-pane label="站内信模版" v-if="isAuth('message:tabs:sysmessagemodel')" name="sysmessagemodel">
        <Sysmessagemodel ref="sysmessagemodel"></Sysmessagemodel>
      </el-tab-pane>
      <el-tab-pane label="会员站内信" v-if="isAuth('message:tabs:messageuser')" name="messageuser">
      <Messageuser ref="Messageuser"></Messageuser>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>
<script>
  import Messagemanagement from './messagemanagement'
  import Sysmessagemodel from '../sysmessagemodel/sysmessagemodel'
  import Messageuser from './messageuser'
  export default {
    data() {
      return {
        dataForm: {
          activeName: 'messagemanagement',
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
      Messagemanagement,
      Sysmessagemodel,
      Messageuser
      // CashBank,
    },
    activated() {
      this.getDataList()
    },
    methods: {
      handleTab(tab, event) {
        if (tab.name == "messagemanagement") {
          this.$nextTick(() => {
            this.$refs.OrderRecharge.getDataList()
          })
        } else if (tab.name == "sysmessagemodel") {
          this.$nextTick(() => {
            this.$refs.OrderthirdRecharge.getDataList()
          })
        }
        else if (tab.name == "messageuser") {
          this.$nextTick(() => {
            this.$refs.OrderthirdRecharge.getDataList()
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
