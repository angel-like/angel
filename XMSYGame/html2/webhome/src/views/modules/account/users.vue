<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
      <el-tab-pane label="会员列表" v-if="isAuth('account:tabs:account')" name="account">
        <panl-user ref="panlUser"></panl-user>
      </el-tab-pane>
      <!-- 	<el-tab-pane label="真实姓名审核" v-if="isAuth('account:tabs:username')" name="username">
                    <panl-username></panl-username>
                </el-tab-pane> -->
      <el-tab-pane label="人工赠送会员道具" v-if="isAuth('account:tabs:batchRechargeProp')" name="batchRechargeProp">
        <panl-batchRechargeProp ref="panlBatchrechargeprop"></panl-batchRechargeProp>
      </el-tab-pane>
      <el-tab-pane label="邀请码查询" v-if="isAuth('account:tabs:invitation')" name="invitation">
        <panl-invitation ref="panlInvitation"></panl-invitation>
      </el-tab-pane>
      <el-tab-pane label="VIP等级设置" v-if="isAuth('account:tabs:uservip')" name="uservip">
        <panl-uservip ref="panlUservip"></panl-uservip>
      </el-tab-pane>
      <!-- 	<el-tab-pane label="用户注册管理" v-if="isAuth('account:tabs:userRegistration')" name="userRegistration">
                    <panl-userRegistration></panl-userRegistration>
                </el-tab-pane> -->
      <el-tab-pane label="操作记录" v-if="isAuth('account:tabs:operationRecord')" name="operationRecord">
        <panl-operationRecord ref="panlOperationRecord"></panl-operationRecord>
      </el-tab-pane>
      <el-tab-pane label="注册管理" v-if="isAuth('account:tabs:registernecessary')" name="registernecessary">
        <panl-registernecessary ref="panlRegisternecessary"></panl-registernecessary>
      </el-tab-pane>
      <el-tab-pane label="真实姓名审核" v-if="isAuth('account:tabs:usernamemodify')" name="usernamemodify">
        <panl-usernamemodify ref="panlUsernamemodify"></panl-usernamemodify>
      </el-tab-pane>
      <el-tab-pane label="密保问题" v-if="isAuth('account:tabs:userquetion')" name="userquetion">
        <panl-userquestion ref="panlUserquetion"></panl-userquestion>

      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import PanlUser from './user'
  // import PanlUsername from './username'
  import PanlInvitation from '../recommendationcode/recommendationcode'
  // import PanlUserRegistration from './userRegistration'
  import PanlOperationRecord from './operationRecord'
  import PanlBatchRechargeProp from './batchrechargeprop'
  import PanlUservip from './uservip'
  import PanlRegisternecessary from './sysregisternecessary'
  import PanlUsernamemodify from './usernamemodify'
  import PanlUserquestion from '../userquestion/userquestion'


  export default {
    data() {
      return {
        dataForm: {
          activeName: 'account',
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
      PanlUser,
      // PanlUsername,
      PanlInvitation,
      // PanlUserRegistration,
      PanlOperationRecord,
      PanlBatchRechargeProp,
      PanlUservip,
      PanlRegisternecessary,
      PanlUsernamemodify,
      PanlUserquestion,

    },
    activated() {
      //this.getDataList()
    },
    methods: {
      handleTab(tab, event) {
        if (tab.name == "invitation") {
          this.$nextTick(() => {
            this.$refs.panlInvitation.getDataList()
          })
        } else if (tab.name == "operationRecord") {
          this.$nextTick(() => {
            this.$refs.panlOperationRecord.getDataList()
          })
        } else if (tab.name == "account") {
          this.$nextTick(() => {
            this.$refs.panlUser.getDataList()
          })
        } else if (tab.name == "batchRechargeProp") {
          this.$nextTick(() => {
            this.$refs.panlBatchRechargeProp.getDataList()
          })
        } else if (tab.name == "uservip") {
          this.$nextTick(() => {
            this.$refs.panlUservip.getDataList()
          })
        } else if (tab.name == "registernecessary") {
          this.$nextTick(() => {
            this.$refs.panlRegisternecessary.getDataList()
          })
        } else if (tab.name == "usernamemodify") {
          this.$nextTick(() => {
            this.$refs.panlUsernamemodify.getDataList()
          })
        } else if (tab.name == "userquetion") {
          this.$nextTick(() => {
            this.$refs.panlUserquetion.getDataList()
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
