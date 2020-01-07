<template xmlns:text-align="http://www.w3.org/1999/xhtml">
  <el-dialog
    :title="'支付设置'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-tooltip class="item" effect="dark" content="支付公司" placement="top-start">
        <el-form-item label="支付公司" prop="payId">
          <el-select v-model="dataForm.payId" @change="handlerechargechannelChange" placeholder="请选择支付公司" clearable>
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.payName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="产品代码" prop="productCode">
        <el-input v-model=" dataForm.productCode" placeholder="产品代码"></el-input>
      </el-form-item>


      <el-tooltip class="item" effect="dark" content="限制支付的最高金额" placement="top-start">
        <el-form-item label="最大充值金额" prop="highLimit">
          <el-input v-model="dataForm.highLimit" placeholder="限制最高支付金额"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="限制支付的最低金额" placement="top-start">
        <el-form-item label="最小充值金额" prop="lowLimit">
          <el-input v-model="dataForm.lowLimit" placeholder="限制最低支付金额"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-form-item  label="显示名称" prop="showName" >
        <el-input v-model="dataForm.showName" placeholder="显示名称"></el-input>
      </el-form-item>
      <el-form-item label="推广层级" prop="hierarchyId">
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="dataForm.hierarchyId" @change="handleCheckedhierarchyTypesListChange">
          <el-checkbox v-for="hierarchyType in hierarchyTypeList" :label="hierarchyType.id" :key="hierarchyType.id">
            {{hierarchyType.name}}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label="排序" prop="orderNo">
        <el-input-number v-model="dataForm.orderNo" type="number" placeholder="排序"></el-input-number>
      </el-form-item>

      <el-tooltip class="item" effect="dark" content="可选充值金额(请输入数字多个值请以逗号隔开)" placement="top-start">
        <el-form-item label="充值限额" prop="amount">
          <el-input v-model="dataForm.amount" placeholder="多个值请以逗号隔开"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="状态" placement="top-start">
        <el-form-item label="状态" prop="enable">
          <el-radio-group v-model="dataForm.enable">
            <el-radio :label="true">启用</el-radio>
            <el-radio :label="false">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-tooltip>
      <!--  <el-tooltip class="item" effect="dark" content="是否设置首个推用" placement="top-start">
            <el-form-item label="是否为首推" prop="enable">
                <el-radio-group v-model="dataForm.firstPush">
                    <el-radio :label="true">是</el-radio>
                    <el-radio :label="false">否</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-tooltip> -->
    </el-form>


    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>
<script>
  export default {
    data() {
      //验证正整数
      var validateInteger = (rule, value, callback) => {
        var res = /^[0-9]*[1-9][0-9]*$/;
        if (value === '') {
          callback(new Error('不可为空'));
        } else {
          if (!res.test(value)) {
            callback(new Error('格式有误必须为整数'));
          }
          callback();
        }
      };
      var validateAmount = (rule, value, callback) => {
        if (value) {

          var retgex = /^\d+(,\d+)*$/;
          if (!retgex.test(value)) {
            console.log("true")

            callback(new Error('限制输入数字和逗号，不能以逗号结尾'));
          } else {
            console.log("false")
            callback();
          }
        } else {
          callback();
        }
      }
      return {
        options: [],
        channeloptions: [],
        visible: false,
        dataForm: {
          amount: '',
          id: 0,
          orderNo: 0,
          payId: '',
          productCode: '',
          channelId: '',
          highLimit: '',
          lowLimit: '',
          showName: '',
          enable: true,
          hierarchyId: [],
          hierarchyIds: [],
          hierarchyTypesName: [],
          channelAlias: ''
        },
        isIndeterminate: true,
        checkAll: false,
        hierarchyTypeList: [],
        checkAllOptions: [],
        checkOptions: [],
        showButton: true,
        newOptions: [],
        hierarchyOptions: [],
        dataRule: {
          payId: [
            {required: true, message: '请选择支付公司', trigger: 'blur'}
          ],
          showName: [
            {required: true, message: '请填写显示名称', trigger: 'blur'}
          ],
          productCode: [
            {required: true, message: '请填写产品代码', trigger: 'blur'}
          ],
          highLimit: [
            {required: true, message: '请填写最大充值金额', trigger: 'blur'},
            {validator: validateInteger, trigger: 'blur'}
          ],
          lowLimit: [
            {required: true, message: '请填写最小充值金额', trigger: 'blur'},
            {validator: validateInteger, trigger: 'blur'}
          ],
          enable: [
            {required: true, message: '请选择状态', trigger: 'blur'}
          ],
          amount: [
            {validator: validateAmount, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {

      init(id, channelAlias, channelId) {
        this.dataForm.channelId = channelId
        this.dataForm.channelAlias = channelAlias
        //为下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/rechargechannel/rechargechannel/select`),
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


        this.$http({
          url: this.$http.adornUrl(`/userhierarchy/userhierarchy/selectHierarchy`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.checkOptions = data.dataList,
              this.hierarchyTypeList = data.dataList,
              this.checkAllOptions = data.ids
          }
        });
        this.dataForm.id = id || 0


        this.visible = true
       /* if(id){*/
          this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
            if(this.dataForm.id&&this.dataForm.id!=0){
            this.$http({
              url: this.$http.adornUrl(`/paychannelconfig/paychannelconfig/getById/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm = data.data
                this.dataForm.hierarchyId=this.dataForm.hierarchyIds
                let checkedCount = this.dataForm.hierarchyIds.length;
                this.checkAll = (checkedCount === this.hierarchyTypeList.length);
                this.isIndeterminate = checkedCount >= 0 && checkedCount < this.hierarchyTypeList.length;
              }
            })}
          })
       /* }*/

      },
      handlerechargechannelChange(value) {

        this.dataForm.payId=value
        this.$http({
          url: this.$http.adornUrl(`/paychannelconfig/paychannelconfig/getByPayIdAndChannelId`),
          method: 'post',
          data: this.$http.adornData({
            'payId': this.dataForm.payId,
            'channelId': this.dataForm.channelId
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            if(data.data.hierarchyIds.length<=0){
             // this.$refs['dataForm'].resetFields()
              this.dataForm ={
                amount: '',
                id: 0,
                orderNo: 0,
                payId: data.data.payId,
                productCode: '',
                channelId: data.data.channelId,
                highLimit: '',
                lowLimit: '',
                enable: true,
                showName: '',
                hierarchyId: [],
                hierarchyIds: [],
                hierarchyTypesName: [],
                channelAlias: ''
              }
            }else{
              this.dataForm = data.data
            }
            this.dataForm.hierarchyId=this.dataForm.hierarchyIds
            let checkedCount = this.dataForm.hierarchyIds.length;
            this.checkAll = (checkedCount === this.hierarchyTypeList.length);
            this.isIndeterminate = checkedCount >= 0 && checkedCount < this.hierarchyTypeList.length;
          }
        })
      },
      handleCheckAllChange(val) {
        this.dataForm.hierarchyId = val ? this.checkAllOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedhierarchyTypesListChange(value) {

        let checkedCount = value.length;
        this.checkAll = checkedCount === this.hierarchyTypeList.length;
        this.isIndeterminate = checkedCount >= 0 && checkedCount < this.hierarchyTypeList.length;

      },
      gethierarchyTypesListName() {
        this.dataForm.hierarchyTypesName = [];
        if (this.checkAll) {
          for (var i = 0; i < this.hierarchyTypeList.length; i++) {
            this.dataForm.hierarchyTypesName.push(this.hierarchyTypeList[i].name);
          }
        } else {
          for (var j = 0; j < this.dataForm.hierarchyId.length; j++) {
            for (var i = 0; i < this.hierarchyTypeList.length; i++) {
              if (this.dataForm.hierarchyId[j] == this.hierarchyTypeList[i].id) {
                this.dataForm.hierarchyTypesName.push(this.hierarchyTypeList[i].name);
                break;
              }
            }
          }
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              // url: this.$http.adornUrl(`/payconfig/payconfig/${!this.dataForm.id ? 'saveAll' : 'update'}`),
              url: this.$http.adornUrl(`/paychannelconfig/paychannelconfig/saveOrUpdate`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'payId': this.dataForm.payId,
                'enable': this.dataForm.enable,
                'channelId': this.dataForm.channelId,
                'highLimit': this.dataForm.highLimit,
                'lowLimit': this.dataForm.lowLimit,
                'amount': this.dataForm.amount,
                'showName': this.dataForm.showName,
                'orderNo': this.dataForm.orderNo,
                'hierarchyId': this.dataForm.hierarchyId,
                'productCode': this.dataForm.productCode
              })
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>


<style scoped>
  .table-tr {
    display: flex;
    justify-content: space-around;
    margin-bottom: 10px;
  }

  .td-end {
    justify-content: space-between !important;
  }

  .radio-wrap {
    display: flex;
    justify-content: center;
  }

  .item-input {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
  }

  .item-input >>> .el-form-item__content {
    margin-left: 10px !important;
  }

  .item-input >>> .el-form-item__label {
    width: 120px !important;
  }

  .item-input >>> .el-input__inner {
    width: 265px;
  }
</style>
