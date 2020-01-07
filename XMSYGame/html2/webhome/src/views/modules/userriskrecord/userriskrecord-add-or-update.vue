<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="用户账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="风控类型" prop="riskType">
      <el-input v-model="dataForm.riskType" placeholder="风控类型"></el-input>
    </el-form-item>
    <el-form-item label="金币值" prop="coin">
      <el-input v-model="dataForm.coin" placeholder="金币值"></el-input>
    </el-form-item>
    <el-form-item label="层级id" prop="hierarchyId">
      <el-input v-model="dataForm.hierarchyId" placeholder="层级id"></el-input>
    </el-form-item>
    <el-form-item label="风控值" prop="riskVal">
      <el-input v-model="dataForm.riskVal" placeholder="风控值"></el-input>
    </el-form-item>
    <el-form-item label="充值钱" prop="rechargeVal">
      <el-input v-model="dataForm.rechargeVal" placeholder="充值钱"></el-input>
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
        dataForm: {
          id: 0,
          userId: '',
          userAccount: '',
          riskType: '',
          coin: '',
          hierarchyId: '',
          riskVal: '',
          rechargeVal: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户类型不能为空', trigger: 'blur' }
          ],
          riskType: [
            { required: true, message: '风控类型不能为空', trigger: 'blur' }
          ],
          coin: [
            { required: true, message: '金币值不能为空', trigger: 'blur' }
          ],
          hierarchyId: [
            { required: true, message: '层级id不能为空', trigger: 'blur' }
          ],
          riskVal: [
            { required: true, message: '风控值不能为空', trigger: 'blur' }
          ],
          rechargeVal: [
            { required: true, message: '充值钱不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userriskrecord/userriskrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.userriskrecord.userId
                this.dataForm.userAccount = data.userriskrecord.userAccount
                this.dataForm.riskType = data.userriskrecord.riskType
                this.dataForm.coin = data.userriskrecord.coin
                this.dataForm.hierarchyId = data.userriskrecord.hierarchyId
                this.dataForm.riskVal = data.userriskrecord.riskVal
                this.dataForm.rechargeVal = data.userriskrecord.rechargeVal
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
              url: this.$http.adornUrl(`/userriskrecord/userriskrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'riskType': this.dataForm.riskType ,

          'coin': this.dataForm.coin ,

          'hierarchyId': this.dataForm.hierarchyId ,

          'riskVal': this.dataForm.riskVal ,

          'rechargeVal': this.dataForm.rechargeVal ,

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
