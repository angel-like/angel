<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="活动类型 1：奖励金" prop="type">
      <el-input v-model="dataForm.type" placeholder="活动类型 1：奖励金"></el-input>
    </el-form-item>
    <el-form-item label="具体类型 " prop="detailType">
      <el-input v-model="dataForm.detailType" placeholder="具体类型 "></el-input>
    </el-form-item>
    <el-form-item label="数量" prop="num">
      <el-input v-model="dataForm.num" placeholder="数量"></el-input>
    </el-form-item>
    <el-form-item label="道具id" prop="propId">
      <el-input v-model="dataForm.propId" placeholder="道具id"></el-input>
    </el-form-item>
    <el-form-item label="发送模式 0：后台发放 1：客户领取" prop="sendType">
      <el-input v-model="dataForm.sendType" placeholder="发送模式 0：后台发放 1：客户领取"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="用户账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="是否领取" prop="receive">
      <el-input v-model="dataForm.receive" placeholder="是否领取"></el-input>
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
          type: '',
          detailType: '',
          num: '',
          propId: '',
          sendType: '',
          userId: '',
          userAccount: '',
          receive: '',
        },
        dataRule: {
          type: [
            { required: true, message: '活动类型 1：奖励金不能为空', trigger: 'blur' }
          ],
          detailType: [
            { required: true, message: '具体类型 不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '数量不能为空', trigger: 'blur' }
          ],
          propId: [
            { required: true, message: '道具id不能为空', trigger: 'blur' }
          ],
          sendType: [
            { required: true, message: '发送模式 0：后台发放 1：客户领取不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          receive: [
            { required: true, message: '是否领取不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/usergiftrecord/usergiftrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.type = data.usergiftrecord.type
                this.dataForm.detailType = data.usergiftrecord.detailType
                this.dataForm.num = data.usergiftrecord.num
                this.dataForm.propId = data.usergiftrecord.propId
                this.dataForm.sendType = data.usergiftrecord.sendType
                this.dataForm.userId = data.usergiftrecord.userId
                this.dataForm.userAccount = data.usergiftrecord.userAccount
                this.dataForm.receive = data.usergiftrecord.receive
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
              url: this.$http.adornUrl(`/usergiftrecord/usergiftrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'type': this.dataForm.type ,

          'detailType': this.dataForm.detailType ,

          'num': this.dataForm.num ,

          'propId': this.dataForm.propId ,

          'sendType': this.dataForm.sendType ,

          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

          'receive': this.dataForm.receive ,

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
