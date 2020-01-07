<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="用户账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="转盘名称" prop="luckyName">
      <el-input v-model="dataForm.luckyName" placeholder="转盘名称"></el-input>
    </el-form-item>
    <el-form-item label="奖励名称" prop="propName">
      <el-input v-model="dataForm.propName" placeholder="奖励名称"></el-input>
    </el-form-item>
    <el-form-item label="奖励数量" prop="propNum">
      <el-input v-model="dataForm.propNum" placeholder="奖励数量"></el-input>
    </el-form-item>
    <el-form-item label="是否大奖" prop="grand">
      <el-input v-model="dataForm.grand" placeholder="是否大奖"></el-input>
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
          luckyName: '',
          propName: '',
          propNum: '',
          grand: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          luckyName: [
            { required: true, message: '转盘名称不能为空', trigger: 'blur' }
          ],
          propName: [
            { required: true, message: '奖励名称不能为空', trigger: 'blur' }
          ],
          propNum: [
            { required: true, message: '奖励数量不能为空', trigger: 'blur' }
          ],
          grand: [
            { required: true, message: '是否大奖不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/luckyuserrecord/luckyuserrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.luckyuserrecord.userId
                this.dataForm.userAccount = data.luckyuserrecord.userAccount
                this.dataForm.luckyName = data.luckyuserrecord.luckyName
                this.dataForm.propName = data.luckyuserrecord.propName
                this.dataForm.propNum = data.luckyuserrecord.propNum
                this.dataForm.grand = data.luckyuserrecord.grand
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
              url: this.$http.adornUrl(`/luckyuserrecord/luckyuserrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'luckyName': this.dataForm.luckyName ,

          'propName': this.dataForm.propName ,

          'propNum': this.dataForm.propNum ,

          'grand': this.dataForm.grand ,

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
