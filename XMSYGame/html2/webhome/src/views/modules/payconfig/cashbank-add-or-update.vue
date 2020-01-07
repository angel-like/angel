<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
				<el-form-item label="银行名称" prop="name">
				  <el-input v-model="dataForm.name" placeholder="银行名称"></el-input>
				</el-form-item>
      <el-tooltip class="item" effect="dark" content="指定推广层级下的用户才能往此银行卡转账存款" placement="top-start">
				<el-form-item label="所属层级ID" prop="hierarchyId">
					<el-select v-model="dataForm.hierarchyId" multiple placeholder="请选择层级">
						<el-option
							v-for="item in options"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
				<el-form-item label="银行卡号" prop="bankAccount">
				  <el-input v-model="dataForm.bankAccount" placeholder="银行卡号"></el-input>
				</el-form-item>
      <el-tooltip class="item" effect="dark" content="必须是持卡人真实姓名" placement="top-start">
				<el-form-item label="收款人姓名" prop="bankUser">
				  <el-input v-model="dataForm.bankUser" placeholder="收款人姓名"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="在哪里开的这张卡" placement="top-start">
				<el-form-item label="开户行" prop="bankAddress">
				  <el-input v-model="dataForm.bankAddress" placeholder="开户行"></el-input>
				</el-form-item>
			</el-tooltip>
      	<el-form-item  label="排序号" prop="sorts">
      	  <el-input-number v-model="dataForm.sorts" controls-position="right" :min="0" label="排序号"></el-input-number>
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
				options:[],
        dataForm: {
          id: 0,
          name: '',
          hierarchyId: [],
          bankAccount: '',
          bankUser: '',
          bankAddress: '',
					sorts: '',
        },
        dataRule: {
          name: [
            { required: true, message: '银行名称不能为空', trigger: 'blur' }
          ],
          hierarchyId: [
            { required: true, message: '所属层级ID不能为空', trigger: 'blur' }
          ],
          bankAccount: [
            { required: true, message: '银行卡号不能为空', trigger: 'blur' }
          ],
          bankUser: [
            { required: true, message: '收款人姓名不能为空', trigger: 'blur' }
          ],
					sorts: [
					  { required: true, message: '排序号不能为空', trigger: 'blur' }
					],
          bankAddress: [
            { required: true, message: '开户行不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				this.$http({
					url: this.$http.adornUrl(`/userhierarchy/userhierarchy/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options=data.list
					}
				})

        this.dataForm.id = id || 0
        this.visible = true

        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/cashbank/cashbank/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.cashbank.name
								
								let dataStrArr=data.cashbank.hierarchyId.split(",");  //分割成字符串数组
								let dataIntArr=[];//保存转换后的整型字符串
								dataStrArr.forEach(item => {
										dataIntArr.push(+item);
								});
								this.dataForm.hierarchyId=dataIntArr
								
                this.dataForm.bankAccount = data.cashbank.bankAccount
                this.dataForm.bankUser = data.cashbank.bankUser
                this.dataForm.bankAddress = data.cashbank.bankAddress
								this.dataForm.sorts = data.cashbank.sorts

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
              url: this.$http.adornUrl(`/cashbank/cashbank/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'name': this.dataForm.name ,
							'hierarchyId': this.dataForm.hierarchyId.join(",") ,
							'bankAccount': this.dataForm.bankAccount ,
							'bankUser': this.dataForm.bankUser ,
							'bankAddress': this.dataForm.bankAddress ,
							'sorts': this.dataForm.sorts ,


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
