<template>
  <el-dialog
    :title=" '修改银行卡'"
    :close-on-click-modal="false"
    :visible.sync="visible" append-to-body>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="开户银行" prop="bankName">
        <el-select filterable v-model="dataForm.bankName" placeholder="请选择开户银行">
					<el-option
						v-for="item in options"
						:key="item.name"
						:label="item.name"
						:value="item.name">
					</el-option>
				</el-select>
      </el-form-item>
			<el-form-item label="开户名" prop="bankAccountName">
					<el-input v-model="dataForm.bankAccountName" placeholder="开户名" disabled></el-input>
			</el-form-item>
			<el-form-item label="开户支行" prop="bankAddress">
				<el-input v-model="dataForm.bankAddress" placeholder="开户支行"></el-input>
			</el-form-item>
			<el-form-item label="卡号" prop="bankCard">
				<el-input v-model="dataForm.bankCard" placeholder="卡号"></el-input>
			</el-form-item>
			<!-- <el-form-item label="阿里账号" prop="alipayAccount">
				<el-input v-model="dataForm.alipayAccount" placeholder="卡号"></el-input>
			</el-form-item> -->

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
        options: [],
        oldBank: {},
        dataForm: {
          id: 0,
          account: '',
          bankName: '',
          bankCard: '',
					// alipayAccount:"",
          bankAddress: '',
          bankAccountName: ''
        },
        dataRule: {
          bankName: [
            { required: true, message: '开户银行不能为空', trigger: 'blur' }
          ],
          bankCard: [
            { required: true, message: '卡号不能为空', trigger: 'blur' }
          ],
// 					alipayAccount: [
// 							{ required: true, message: '阿里账号不能为空', trigger: 'blur' }
// 						],
          bankAddress: [
            { required: true, message: '开户支行不能为空', trigger: 'blur' }
          ],
          bankAccountName: [
            { required: true, message: '开户名不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      initEdit (id, userName) {
        this.dataForm.id = id || 0
        this.dataForm.bankAccountName = userName || 0
        var userNickName = userName
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
       	this.$http({
       		url: this.$http.adornUrl(`/user/user/getBank`),
       		method: 'get',
       		params: this.$http.adornParams()
       	}).then(({data}) => {
       		if (data && data.code === 200) {
       			this.options = data.bankList
       }
       	})
     	if (this.dataForm.id) {
       		this.$http({
       			url: this.$http.adornUrl(`/user/user/allinfo/${this.dataForm.id}`),
       			method: 'get',
       			params: this.$http.adornParams()
       		}).then(({data}) => {
       			if (data && data.code === 200) {
         this.oldBank = data.userinfo
         this.dataForm.account = data.user.account
         this.dataForm.bankName = data.userinfo.bankName
         this.dataForm.bankCard = data.userinfo.bankCard
							// this.dataForm.alipayAccount=data.userinfo.alipayAccount
         this.dataForm.bankAddress = data.userinfo.bankAddress
         this.dataForm.bankAccountName = userNickName
							// this.dataForm.bankAccountName=data.userinfo.bankAccountName
       			}
       		})
       	}
        })
      },
      getModifyContent (option, oldVal, newVal) {
        var result = ''
        if (oldVal != newVal) {
          result = option
          if (oldVal) {
            result += '由【' + oldVal + '】修改为【'
          } else {
            result += '由【空】修改为【'
          }
          if (newVal) {
            result += newVal + '】;'
          } else {
            result += '空】;'
          }
        }
        return result
      },
      // 表单提交
      dataFormSubmit () {
        if (this.dataForm.bankName == this.oldBank.bankName &&
						this.dataForm.bankCard == this.oldBank.bankCard &&
						// this.dataForm.alipayAccount==this.oldBank.alipayAccount &&
						this.dataForm.bankAddress == this.oldBank.bankAddress &&
						this.dataForm.bankAccountName == this.oldBank.bankAccountName) {
          this.$message({
            message: '内容没有变更',
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.visible = false
              this.$emit('refreshDataList')
            }
          })
        }
        var modifyContent = ''
// 				modifyContent="用户银行信息由【"+
// 				this.oldBank.bankName+this.oldBank.bankAddress+this.oldBank.bankAccountName+this.oldBank.bankCard
// 				"】修改为【"+
				// this.dataForm.bankName+this.dataForm.bankAddress+this.dataForm.bankAccountName+this.dataForm.bankCard+"】";
        var userOperater = {}
        var remark = ''
        remark += '用户银行信息修改：'
        remark += this.getModifyContent('用户银行卡号', this.oldBank.bankCard, this.dataForm.bankCard)
        remark += this.getModifyContent('银行名称', this.oldBank.bankName, this.dataForm.bankName)
        remark += this.getModifyContent('银行开户名', this.oldBank.bankAddress, this.dataForm.bankAddress)
        remark += this.getModifyContent('开户支行', this.oldBank.bankAccountName, this.dataForm.bankAccountName)
        userOperater.memberId = this.dataForm.id
        userOperater.memberAccount = this.dataForm.account
        userOperater.remark = remark
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/user/operation/editBankInfo`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.oldBank.id,
                'bankName': this.dataForm.bankName,
                'bankCard': this.dataForm.bankCard,
								// 'alipayAccount': this.dataForm.alipayAccount,
                'bankAddress': this.dataForm.bankAddress,
                'bankAccountName': this.dataForm.bankAccountName,
                'userOperater': userOperater
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
