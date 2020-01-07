<template>
  <el-dialog
    :title="'新增存款权限'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
		<el-form-item label="存款权限" prop="paymentId">
		  <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
		  <div style="margin: 15px 0;"></div>
		  <el-checkbox-group v-model="dataForm.paymentId" @change="handleCheckedPaymentTypesListChange">
		  	<el-checkbox v-for="paymentType in paymentTypeList" :label="paymentType.id" :key="paymentType.id">{{paymentType.name}}</el-checkbox>
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
    data () {
      return {
				visible: false,
				isIndeterminate: true,
				checkAll: false,
				paymentTypeList: [],
				checkAllOptions : [],
				checkOptions : [],
				paymentId:'',
        dataForm: {
						id:0,
						paymentId: [],
						paymentTypesName:[],
        },
        dataRule: {
					 paymentId: [
					  { required: true, message: '存款权限不能为空', trigger: 'blur' }
					],
        }
      }
    },
	
    methods: {
      init (id) {
        this.dataForm.id = id || 0;
        this.visible = true
        this.$nextTick(() => {
            this.$refs['dataForm'].resetFields()
						this.checkAll=false;
				this.$http({
				url: this.$http.adornUrl(`/paymenttypeconfiguration/paymenttypeconfiguration/select`),
				method: 'get',
				params: this.$http.adornParams()
			}).then(({data}) => {
				if (data && data.code === 200) {
					this.checkOptions = data.dataList,
					this.paymentTypeList= data.dataList,
					this.checkAllOptions = data.ids
				}
			});
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/hierarchypaymentrelationship/hierarchypaymentrelationship/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
									this.dataForm.paymentId = data.hierarchypaymentrelationship
									let checkedCount = this.dataForm.paymentId.length;
									this.checkAll = checkedCount === this.paymentTypeList.length;
									this.isIndeterminate = checkedCount > 0 && checkedCount < this.paymentTypeList.length;
              }else{
									this.dataForm.paymentId =[];
							}
            })
          }
        })
      },
	  handleCheckAllChange(val) {
	  	this.dataForm.paymentId = val ? this.checkAllOptions : [];
	  	this.isIndeterminate = false;
	  },
	  handleCheckedPaymentTypesListChange(value) {
	  	let checkedCount = value.length;
	  	this.checkAll = checkedCount === this.paymentTypeList.length;
	  	this.isIndeterminate = checkedCount > 0 && checkedCount < this.paymentTypeList.length;
	  },
	  getpaymentTypesListName(){
	  	this.dataForm.paymentTypesName=[];
	  	if(this.checkAll){
	  		for(var i=0;i<this.paymentTypeList.length;i++){
	  			this.dataForm.paymentTypesName.push(this.paymentTypeList[i].name);
	  		}
	  	}else{
	  		for(var j=0;j<this.dataForm.paymentId.length;j++){
	  				for(var i=0;i<this.paymentTypeList.length;i++){
	  					if(this.dataForm.paymentId[j]==this.paymentTypeList[i].id){
	  						this.dataForm.paymentTypesName.push(this.paymentTypeList[i].name);
	  						break;
	  					}
	  				}
	  		}
	  	}
	  },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
			this.getpaymentTypesListName();
            this.$http({
              url: this.$http.adornUrl(`/hierarchypaymentrelationship/hierarchypaymentrelationship/save`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
				'paymentId': this.dataForm.paymentId ,
				'hierarchyId': this.dataForm.id,
				
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
