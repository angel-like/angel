<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
      <el-tab-pane label="银行卡存款订单" v-if="isAuth('order:tabs:bank')" name="orderRecharge">
        <OrderRecharge ref="orderRecharge"></OrderRecharge>
      </el-tab-pane>
      <el-tab-pane label="第三方存款订单" v-if="isAuth('order:tabs:third')" name="orderthirdRecharge">
        <OrderthirdRecharge ref="orderthirdRecharge"></OrderthirdRecharge>
      </el-tab-pane>
      <el-tab-pane label="首充查询" v-if="isAuth('pay:tabs:userfistcharge')" name="userfistcharge">
        <Userfistcharge ref="userfistcharge"></Userfistcharge>
      </el-tab-pane>
      <el-tab-pane label="线上取款订单" v-if="isAuth('order:tabs:take')"  name="OrderTakeMoney">
        <OrderTakeMoney ref="OrderTakeMoney" @refreshDataList="getDataList"></OrderTakeMoney>
      </el-tab-pane>
      <!-- 处理订单 -->
      <el-tab-pane label="取款订单处理" v-if="isAuth('order:tabs:work')"  name="Ordertakemoneywork">
        <Ordertakemoneywork ref="Ordertakemoneywork" @refreshDataList="getDataList"></Ordertakemoneywork>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import OrderRecharge from './orderbankrecharge'
  import OrderthirdRecharge from './orderthirdrecharge'
  import OrderTakeMoney from './out'
  import Userfistcharge from './userfistcharge'
import Ordertakemoneywork from './ordertakemoneywork'
  export default {
    data() {
      return {
        dataForm: {
          activeName: 'orderRecharge',
          paramKey: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        editUserDetailsVisible: false,
        addOrUpdateVisible: false
      }
    },
    components: {
      OrderRecharge,
      OrderthirdRecharge,
      OrderTakeMoney,
      Userfistcharge,
      Ordertakemoneywork
      // CashBank,
    },
    activated() {
      this.dataForm.userAccount = this.$route.query.account
      if (this.$route.query.name != null) {
        this.dataForm.activeName = this.$route.query.name
      }

      this.getDataList()
    },
    methods: {
      handleTab(tab, event) {
        console.log(tab)
        if (tab.name == "orderRecharge") {
          this.$nextTick(() => {
            this.$refs.OrderRecharge.getDataList()
          })
        } else if (tab.name == "orderthirdRecharge") {
          this.$nextTick(() => {
            this.$refs.OrderthirdRecharge.getDataList()
          })
        } else if (tab.name == "userfistcharge") {
          this.$nextTick(() => {
            this.$refs.Userfistcharge.getDataList()
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
