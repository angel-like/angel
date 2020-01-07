<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-tooltip class="item" effect="dark" content="填写支付类型的名称(在客户端显示相应的名称)" placement="top-start">
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="支付类型名称"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="选择充值的类型,例如:微信,支付宝等" placement="top-start">
        <el-form-item label="充值类型" prop="type">
          <el-select v-model="dataForm.type" placeholder="充值类型 " clearable>
            <el-option
              v-for="item in options"
              :key="item.code"
              :label="item.name"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="支付类型" prop="paymentType" v-if="dataForm.type==1">
        <el-select v-model="dataForm.paymentType" placeholder="请选择支付类型" clearable>
          <el-option
            v-for="item in paymentTypeOptions"
            :key="item.code"
            :label="item.name"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="是否启用" prop="enable">
        <el-radio-group v-model="dataForm.enable">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="排序号" prop="orderNum">
        <el-input-number v-model="dataForm.orderNum" controls-position="right" :min="0" label="排序号"></el-input-number>
      </el-form-item>

      <el-form-item label="推广层级" prop="hierarchy">
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="dataForm.hierarchy" @change="handleCheckedHierarchyChange">
          <el-checkbox v-for="hierarchy in hierarchyOptions" :label="hierarchy.id" :key="hierarchy.name">
            {{hierarchy.name}}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>


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
      return {
        visible: false,
        options: [],
        paymentTypeOptions: [],
        dataForm: {
          id: 0,
          name: '',
          enable: false,
          orderNum: 0,
          type: '',
          paymentType: '',
          hierarchyId: '',
          hierarchy: [],
          hierarchyName: []
        },
        checkAll: false,
        checkOptions: [],
        checkAllOptions: [],
        isIndeterminate: true,
        showButton: true,
        hierarchyOptions: [],
        dataRule: {
          name: [
            {required: true, message: '支付类型名称不能为空', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择充值类型', trigger: 'blur'}
          ],
          enable: [
            {required: true, message: '是否启用不能为空', trigger: 'blur'}
          ],
          orderNum: [
            {required: true, message: '排序号不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init(id) {
        //支付类型下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "RechargeClassify"),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.options = data.data
          }
        });
        this.$http({
          url: this.$http.adornUrl(`/messagemanagement/messagemanagement/getHierarchy`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.checkOptions = data.hierarchyList
            this.hierarchyOptions = data.hierarchyList
            this.checkAllOptions = data.ids
          }
        });
        //支付类型下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "qrcodeType"),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.paymentTypeOptions = data.data
          }
        });
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/paymenttypeconfiguration/paymenttypeconfiguration/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.paymenttypeconfiguration.name
                this.dataForm.enable = data.paymenttypeconfiguration.enable
                this.dataForm.orderNum = data.paymenttypeconfiguration.orderNum
                this.dataForm.type = data.paymenttypeconfiguration.type.toString()
                this.dataForm.paymentType = data.paymenttypeconfiguration.paymentType.toString()
                this.dataForm.hierarchy = data.hlist
                let checkedCount = this.dataForm.hierarchy.length
                this.checkAll = checkedCount === this.hierarchyOptions.length
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyOptions.length
              }
            })
          }
        })
      },
      handleCheckAllChange(val) {

        this.dataForm.hierarchy = val ? this.checkAllOptions : [];

        this.isIndeterminate = false;
      },
      handleCheckedHierarchyChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.hierarchyOptions.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyOptions.length;
      },
      getHierarchyName() {
        this.dataForm.hierarchyName = [];
        if (this.checkAll) {
          for (var i = 0; i < this.hierarchyOptions.length; i++) {
            this.dataForm.hierarchyName.push(this.hierarchyOptions[i].name);
          }
        } else {
          for (var j = 0; j < this.dataForm.hierarchy.length; j++) {
            for (var i = 0; i < this.hierarchyOptions.length; i++) {
              if (this.dataForm.hierarchy[j] == this.hierarchyOptions[i].id) {
                this.dataForm.hierarchyName.push(this.hierarchyOptions[i].name);
                break;
              }
            }
          }
        }
      },
      // 表单提交
      dataFormSubmit() {
        if(this.dataForm.hierarchy.length === 0){
          this.dataForm.hierarchy=[1];
        }
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/paymenttypeconfiguration/paymenttypeconfiguration/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'enable': this.dataForm.enable,
                'orderNum': this.dataForm.orderNum,
                'type': this.dataForm.type,
                'paymentType': this.dataForm.paymentType,
                'hierarchyId': this.dataForm.hierarchy.join(',')

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
