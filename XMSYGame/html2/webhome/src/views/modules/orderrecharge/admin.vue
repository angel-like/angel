<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">

      <el-tab-pane label="人工存入" v-if="isAuth('order:tabs:adminenter')" name="orderadministrator">
        <Orderadministrator ref="orderadministrator"></Orderadministrator>
      </el-tab-pane>
      <el-tab-pane label="人工取出" v-if="isAuth('order:tabs:adminout')" name="orderadmin">
        <Orderadmin ref="orderadmin"></Orderadmin>
      </el-tab-pane>
      <el-tab-pane label="存取历史记录" v-if="isAuth('order:tabs:adminhistory')"   name="adminhistory">
        <Orderadminhistory ref="adminhistory"></Orderadminhistory>
      </el-tab-pane>
      <el-tab-pane label="修改打码量"  v-if="isAuth('order:tabs:ordercashrecord')"     name="ordercashrecord">
      <Ordercashrecord ref="ordercashrecord" ></Ordercashrecord>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>
<script>

  import Orderadministrator from '../orderadministrator/orderadministrator-create'
  import Orderadmin from '../orderadministrator/orderadmin-create'
  import  Orderadminhistory from '../orderadministrator/orderadminhistory'
  import   Ordercashrecord from  '../orderadministrator/ordercashrecord-add-or-update'
  export default {
    data() {
      return {
        dataForm: {
          activeName: 'orderadministrator',
          paramKey: ''
        },
        dataList: [],
        option: null,
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {

      Orderadministrator,
      Orderadmin,
      Orderadminhistory,
      Ordercashrecord,

      // CashBank,
    },
    activated() {
      this.dataForm.userAccount = this.$route.query.account
      if(this.$route.query.name !=null){
        this.dataForm.activeName = this.$route.query.name
      }
      if(this.$route.query.option !=null){
        this.option = this.$route.query.option
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
        }else if (tab.name == "Orderadminhistory") {
          this.$nextTick(() => {
            this.$refs.OrderthirdRecharge.getDataList()
          })
        }else if (tab.name == "orderadministrator") {
          this.$nextTick(() => {
            this.$refs.Orderadministrator.getDataList()
          })
        } else if (tab.name == "orderadmin") {
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
