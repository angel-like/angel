<template>
  <div class="mod-config">
    <el-tabs v-model="dataForm.activeName" @tab-click="handleTab">
      <el-tab-pane label="基本设置" v-if="isAuth('setting:tabs:basicsetup')" name="basicsetup">
        <basicsetup ref="basicsetup"></basicsetup>
      </el-tab-pane>
      <!-- 	<el-tab-pane label="真实姓名审核" v-if="isAuth('account:tabs:username')" name="username">
                    <panl-username></panl-username>
                </el-tab-pane> -->
      <el-tab-pane label="联系方式" v-if="isAuth('webhomecontact:tabs:webhomecontact')" name="webhomecontact">
        <Webhomecontact ref="webhomecontact"></Webhomecontact>
      </el-tab-pane>
      <el-tab-pane label="友情链接" v-if="isAuth('webhomefriendship:tabs:webhomefriendship')" name="webhomefriendship">
       <Webhomefriendship ref="webhomefriendship"></Webhomefriendship>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import basicsetup from'../setting/basicsetup'
  import Webhomecontact from '../webhomecontact/webhomecontact'
  import Webhomefriendship from '../webhomefriendship/webhomefriendship'
  export default {
    data() {
      return {
        dataForm: {
          activeName: 'basicsetup',
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
      basicsetup,
      Webhomecontact,
      Webhomefriendship,
      // PanlInvitation,
      // // PanlUserRegistration,
      // PanlOperationRecord,
      // PanlBatchRechargeProp,
      // PanlUservip,
    },
    activated() {
      //this.getDataList()
    },
    methods: {
      handleTab(tab, event) {
        if (tab.name == "webhomecontact") {
          this.$nextTick(() => {
            this.$refs.webhomecontact.getDataList()
          })
        }
        else if(tab.name=="webhomefriendship"){
          this.$nextTick(() => {
            this.$refs.webhomefriendship.getDataList()
          })
        } else if(tab.name=="basicsetup"){
          this.$nextTick(() => {
            this.$refs.basicsetup.getDataList()
          })
        }
        // else if(tab.name=="uservip"){
        //   this.$nextTick(() => {
        //     this.$refs.panlUservip.getDataList()
        //   })
        // }

      },
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true

      }

    }
  }
</script>
