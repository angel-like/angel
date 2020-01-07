<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="请选择支付公司" placement="top-start">
				<el-form-item label="支付公司" prop="payId">
					<el-select v-model="dataForm.payId" placeholder="请选择支付公司">
						<el-option
							v-for="item in options"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
			<el-tooltip class="item" effect="dark" content="通过什么方式支付" placement="top-start">
				<el-form-item label="支付方式" prop="paymentMethodId">
					<el-select v-model="dataForm.paymentMethodId"  placeholder="请选择支付方式">
						<el-option
							v-for="item in optionsPayment"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="限定的金额" placement="top-start">
				<el-form-item label="金额" prop="amount">
				  <el-input v-model="dataForm.amount" placeholder="金额"></el-input>
				</el-form-item>
			</el-tooltip>
    </el-form>
    <div class="alsrtInfo" :style="{display: displayStsates}" ref="alertMsg">
      <div class="profPrompt_test">{{aletMsg}}</div>
    </div>
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
        dataForm: {
          id: 0,
          amount: '',
          payId: '',
					paymentMethodId: '',
        },
        aletMsg: '', // 弹出框中的提示语
        displayStsates: 'none',
				options:[],
				optionsPayment: [],
        dataRule: {
          amount: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          payId: [
            { required: true, message: '支付方式不能为空', trigger: 'blur' }
          ],
					paymentMethodId: [
					  { required: true, message: '支付渠道不能为空', trigger: 'blur' }
					]
        }
      }
    },
    methods: {
      init (id) {
				//为下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/rechargechannel/rechargechannel/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.optionsPayment = data.list
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
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/rechargeamount/rechargeamount/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.amount = data.rechargeamount.amount
                this.dataForm.payId = data.rechargeamount.payId
								this.dataForm.paymentMethodId = data.rechargeamount.paymentMethodId
              }
            })
          }
        })
      },
      alertDia (msg) {
        this.displayStsates = 'block'
        this.aletMsg = msg
        // 延迟2秒后消失 自己可以更改时间
        window.setTimeout(() => {
          this.displayStsates = 'none'
        }, 2000)
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/rechargeamount/rechargeamount/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'amount': this.dataForm.amount ,
							'paymentMethodId': this.dataForm.paymentMethodId,
							'payId': this.dataForm.payId ,

              })
            }).then(({data}) => {
              if (data && data.code === 200) {
                // this.$message({
                //   message: '操作成功',
                //   type: 'success',
                //   duration: 1500,
                //   onClose: () => {
                //     this.visible = false
                //     this.$emit('refreshDataList')
                //   }
                // })
                this.aletMsg('操作成功'),
                  this.visible = false
                this.$emit('refreshDataList')

              } else {
                this.aletMsg(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
