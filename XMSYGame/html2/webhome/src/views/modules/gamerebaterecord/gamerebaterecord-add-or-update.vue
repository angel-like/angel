<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="游戏ID" prop="gameId">
      <el-input v-model="dataForm.gameId" placeholder="游戏ID"></el-input>
    </el-form-item>
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="返利金额" prop="rebateCoin">
      <el-input v-model="dataForm.rebateCoin" placeholder="返利金额"></el-input>
    </el-form-item>
    <el-form-item label="返利时间" prop="rebateTime">
      <el-input v-model="dataForm.rebateTime" placeholder="返利时间"></el-input>
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
          gameId: '',
          userId: '',
          rebateCoin: '',
          rebateTime: '',
        },
        dataRule: {
          gameId: [
            { required: true, message: '游戏ID不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          rebateCoin: [
            { required: true, message: '返利金额不能为空', trigger: 'blur' }
          ],
          rebateTime: [
            { required: true, message: '返利时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gamerebaterecord/gamerebaterecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.gameId = data.gamerebaterecord.gameId
                this.dataForm.userId = data.gamerebaterecord.userId
                this.dataForm.rebateCoin = data.gamerebaterecord.rebateCoin
                this.dataForm.rebateTime = data.gamerebaterecord.rebateTime
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
              url: this.$http.adornUrl(`/gamerebaterecord/gamerebaterecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'gameId': this.dataForm.gameId ,

          'userId': this.dataForm.userId ,

          'rebateCoin': this.dataForm.rebateCoin ,

          'rebateTime': this.dataForm.rebateTime ,

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
