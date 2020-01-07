<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
      <el-tab-pane label="银行维护" v-if="isAuth('pay:tabs:userBank')" name="userBank">
        <user-bank ref="userBank"></user-bank>
      </el-tab-pane>
      <el-tab-pane label="支付类型配置" v-if="isAuth('pay:tabs:paymenttypeconfiguration')" name="paymenttypeConfiguration">
        <paymenttype-configuration ref="paymenttypeConfiguration"></paymenttype-configuration>
      </el-tab-pane>
      <el-tab-pane label="银行卡收款" v-if="isAuth('pay:tabs:cashBank')" name="cashBank">
        <cash-bank ref="cashBank"></cash-bank>
      </el-tab-pane>
      <el-tab-pane label="二维码收款" v-if="isAuth('pay:tabs:cashqrcode') " name="cashqrCode">
        <CashqrCode ref="cashqrCode"></CashqrCode>
      </el-tab-pane>
      <el-tab-pane label="快捷第三方支付" v-if="isAuth('pay:tabs:payconfig') " name="payconfig">
        <payconfig ref="payconfig"></payconfig>
      </el-tab-pane>
      <el-tab-pane label="支付方式管理" v-if="isAuth('pay:tabs:rechargechannel')" name="rechargechannel">
        <rechargechannel ref="rechargechannel"></rechargechannel>
      </el-tab-pane>
      <el-tab-pane label="支付通道配置" v-if="isAuth('pay:tabs:paychannelset')" name="paychannelset">
        <pay-channel-set ref="paychannelset"></pay-channel-set>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import UserBank from './userbank'
  import CashBank from './cashbank'
  import PaymenttypeConfiguration from './paymenttypeconfiguration'
  import CashqrCode from './cashqrcode'
  import Payconfig from './payconfig'
  import Rechargechannel from './rechargechannel'
  import PayChannelSet from './paychannelset'

  export default {
    data() {
      return {
        dataForm: {
          activeName: 'userBank',
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
      UserBank,
      PaymenttypeConfiguration,
      CashBank,
      CashqrCode,
      Payconfig,
      Rechargechannel,
      PayChannelSet
    },
    activated() {
      this.getDataList()
    },
    methods: {
      handleTab(tab, event) {
        if (tab.name == "userBank") {
          this.$nextTick(() => {
            this.$refs.UserBank.getDataList()
          })
        } else if (tab.name == "paymenttypeConfiguration") {
          this.$nextTick(() => {
            this.$refs.PaymenttypeConfiguration.getDataList()
          })
        } else if (tab.name == "cashBank") {
          this.$nextTick(() => {
            this.$refs.CashBank.getDataList()
          })
        } else if (tab.name == "cashqrCode") {
          this.$nextTick(() => {
            this.$refs.CashqrCode.getDataList()
          })
        } else if (tab.name == "payconfig") {
          this.$nextTick(() => {
            this.$refs.payconfig.getDataList()
          })
        } else if (tab.name == "rechargechannel") {
          this.$nextTick(() => {
            this.$refs.rechargechannel.getDataList()
          })
        } else if (tab.name == "paychannelset") {
          this.$nextTick(() => {
            this.$refs.paychannelset.getDataList()
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
