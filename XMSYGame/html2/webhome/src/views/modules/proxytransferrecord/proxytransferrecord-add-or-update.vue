<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="会员ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="会员ID"></el-input>
    </el-form-item>
    <el-form-item label="会员账户" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="会员账户"></el-input>
    </el-form-item>
    <el-form-item label="会员名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="会员名称"></el-input>
    </el-form-item>
    <el-form-item label="业务ID 划拨编号" prop="orderNo">
      <el-input v-model="dataForm.orderNo" placeholder="业务ID 划拨编号"></el-input>
    </el-form-item>
    <el-form-item label="划拨金币" prop="transferCoin">
      <el-input v-model="dataForm.transferCoin" placeholder="划拨金币"></el-input>
    </el-form-item>
    <el-form-item label="代理商ID" prop="proxyId">
      <el-input v-model="dataForm.proxyId" placeholder="代理商ID"></el-input>
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
          userName: '',
          orderNo: '',
          transferCoin: '',
          proxyId: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '会员ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '会员账户不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '会员名称不能为空', trigger: 'blur' }
          ],
          orderNo: [
            { required: true, message: '业务ID 划拨编号不能为空', trigger: 'blur' }
          ],
          transferCoin: [
            { required: true, message: '划拨金币不能为空', trigger: 'blur' }
          ],
          proxyId: [
            { required: true, message: '代理商ID不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/proxytransferrecord/proxytransferrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.proxytransferrecord.userId
                this.dataForm.userAccount = data.proxytransferrecord.userAccount
                this.dataForm.userName = data.proxytransferrecord.userName
                this.dataForm.orderNo = data.proxytransferrecord.orderNo
                this.dataForm.transferCoin = data.proxytransferrecord.transferCoin
                this.dataForm.proxyId = data.proxytransferrecord.proxyId
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
              url: this.$http.adornUrl(`/proxytransferrecord/proxytransferrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'userName': this.dataForm.userName ,

          'orderNo': this.dataForm.orderNo ,

          'transferCoin': this.dataForm.transferCoin ,

          'proxyId': this.dataForm.proxyId ,

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
