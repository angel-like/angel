<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <div style="margin: -64px 60px 40px 60px;">
			<el-tooltip class="item" effect="dark" content="这个模块是后台app充值的按钮;如果点击充值多少,后台就要有相对应数值的设置,若没有设置则无效" placement="top">
					<i class="el-icon-question" style="color:blue" ></i>
			</el-tooltip>
		</div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="设置不同金额的按钮(app充值的按钮)" placement="top-start">
				<el-form-item label="单价" prop="unitPrice">
					<el-input type="number" v-model="dataForm.unitPrice" placeholder="单价"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="对应的价格" placement="top-start">
				<el-form-item label="售价" prop="price">
				  <el-input type="number" v-model="dataForm.price" placeholder="售价"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="这个按钮是当做充值用还是提现用" placement="top-start">
				<el-form-item label="类型" prop="type">
				  <el-radio-group v-model="dataForm.type">
				  	<el-radio :label="0">充值</el-radio>
				  	<el-radio :label="1">提现</el-radio>
				  </el-radio-group>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="要不要用户看到" placement="top-start">
				<el-form-item label="是否展示" prop="enable">
				  <el-radio-group v-model="dataForm.enable">
				  	<el-radio :label="true">是</el-radio>
				  	<el-radio :label="false">否</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-tooltip>
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
        dataForm: {
          id: 0,
          price: '',
          unitPrice: '',
          type: 0,
          enable: true,
        },
        dataRule: {
          price: [
            { required: true, message: '售价不能为空', trigger: 'blur' }
          ],
          unitPrice: [
            { required: true, message: '单价不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '是否展示不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/cashpriceconfig/cashpriceconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.price = data.cashpriceconfig.price
                this.dataForm.unitPrice = data.cashpriceconfig.unitPrice
                this.dataForm.type = data.cashpriceconfig.type
                this.dataForm.enable = data.cashpriceconfig.enable
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/cashpriceconfig/cashpriceconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'price': this.dataForm.price ,
								'unitPrice': this.dataForm.unitPrice ,
								'type': this.dataForm.type ,
								'enable': this.dataForm.enable ,
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
