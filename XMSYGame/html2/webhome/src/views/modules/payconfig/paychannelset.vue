<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">

      <el-form-item >

        <el-button :autofocus="true" selected="true" @click="butHandler('alipay',12)">支付宝</el-button>
        <el-button  @click="butHandler('weixin',13)">微信</el-button>
        <el-button  @click="butHandler('unionpay',32)">银联支付</el-button>
        <el-button  @click="butHandler('unioncloud',1001)">银联云闪付</el-button>
        <el-button  @click="butHandler('qqpay',29)">QQ支付</el-button>
        <el-button  @click="butHandler('jindongpay',30)">京东支付</el-button>
        <el-button  @click="butHandler('quickpay',31)">快捷支付</el-button>




      </el-form-item>
     <!-- <el-form-item><el-button   @click="getDataList()">全部</el-button></el-form-item>-->
    <br/>

      <el-form-item>
      <el-select v-model="dataForm.payId" placeholder="请选择支付公司" clearable>
      	<el-option
      		v-for="item in options"
      		:key="item.id"
      		:label="item.payName"
      		:value="item.id">
      	</el-option>
      </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.enable" placeholder="请选择状态" clearable>
          <el-option
            v-for="item in enableOptions"
            :key="item.name"
            :label="item.label"
            :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.productCode" placeholder="产品代码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('paychannelconfig:paychannelconfig:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <!--<el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>-->
      <el-table-column
        prop="payName"
        header-align="center"
        align="center"
        label="支付公司">
      </el-table-column>
      <el-table-column
        prop="channelName"
        header-align="center"
        align="center"
        label="渠道名称">
      </el-table-column>
     <el-table-column
        prop="showName"
        header-align="center"
        align="center"
        label="显示名称">
      </el-table-column>
      <el-table-column
        prop="productCode"
        header-align="center"
        align="center"
        label="产品代码">
      </el-table-column>
      <el-table-column
        prop="highLimit"
        header-align="center"
        align="center"
        label="最高支付金额">
      </el-table-column>
      <el-table-column
        prop="lowLimit"
        header-align="center"
        align="center"
        label="最低支付金额">
      </el-table-column>
      <el-table-column
        prop="hierarchyName"
        header-align="center"
        align="center"
        label="推广层级">
      </el-table-column>
      <el-table-column
        prop="orderNo"
        header-align="center"
        align="center"
        label="排序">
      </el-table-column>
      <el-table-column
        prop="enable"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enable" size="small">启用</el-tag>
          <el-tag  v-else size="small" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="充值限额">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
					<!--<el-button type="text" size="small" @click="hierarchyChose(scope.row.id)">层级选择</el-button>-->
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" v-if="scope.row.enable==1" size="small" @click="prohibit(scope.row.id)">禁用</el-button>
          <el-button type="text" v-if="scope.row.enable==0" size="small" @click="enable(scope.row.id)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
		<!--<add-hierarchy v-if="addHierarchyVisible" ref="addHierarchy" @refreshDataList="getDataList"></add-hierarchy>-->
  </div>
</template>

<script>
  import AddOrUpdate from './paychannelset-save-update'
	/*import AddHierarchy from '../paychannelconfig/paychannelconfig-add-hierarchy'*/
  export default {
    data () {
      return {
        channeloptions: [],
        dataForm: {
          payId: '',
          enable: '',
          productCode: ''
        },
        enableOptions: [{
          name: 1,
          label: '启用'
        }, {
          name: 0,
          label: '禁用'
        }],
        alias: 'alipay',
        channelId: 12,
				options: [],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
				addHierarchyVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getselect()
      this.getDataList()
    },
		created(){
		 this.keyupSubmit()
		},
    methods: {
      butHandler(alias,id){
        this.alias =alias
        this.channelId =id
        this.getDataList()
      },
      // 启用
      enable (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '启用' : '批量启用'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/paychannelconfig/paychannelconfig/enable'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
      // 禁用
      prohibit (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '禁用' : '批量禁用'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/paychannelconfig/paychannelconfig/prohibit'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
      getselect(){
        //为下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/rechargechannel/rechargechannel/selects`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.channeloptions = data.list
          }
        });


        //为下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/payconfig/payconfig/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.options = data.list
          }
        });
      },
      // 获取数据列表
      getDataList () {

        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/paychannelconfig/paychannelconfig/channelList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'payId': this.dataForm.payId,
            'alias': this.alias,
            'enable': this.dataForm.enable,
            'productCode': this.dataForm.productCode
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
			hierarchyChose(id){
				this.addHierarchyVisible = true
				this.$nextTick(() => {
				this.$refs.addHierarchy.init(id)
				})
			},
			//绑定回车事件
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery(){
				this.pageIndex=1;
				this.getDataList();
			},
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {

        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id,this.alias,this.channelId)
        })
      },
      // 删除
     /* deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/paychannelconfig/paychannelconfig/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }*/
    }
  }
</script>
