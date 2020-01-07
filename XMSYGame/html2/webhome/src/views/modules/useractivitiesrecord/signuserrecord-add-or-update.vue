<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="签到奖励ID" prop="dayId">
      <el-input v-model="dataForm.dayId" placeholder="签到奖励ID"></el-input>
    </el-form-item>
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="奖励" prop="reward">
      <el-input v-model="dataForm.reward" placeholder="奖励"></el-input>
    </el-form-item>
    <el-form-item label="连续签到天数" prop="continuousNum">
      <el-input v-model="dataForm.continuousNum" placeholder="连续签到天数"></el-input>
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
          dayId: '',
          userId: '',
          reward: '',
          continuousNum: '',
        },
        dataRule: {
          dayId: [
            { required: true, message: '签到奖励ID不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          reward: [
            { required: true, message: '奖励不能为空', trigger: 'blur' }
          ],
          continuousNum: [
            { required: true, message: '连续签到天数不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/signuserrecord/signuserrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.dayId = data.signuserrecord.dayId
                this.dataForm.userId = data.signuserrecord.userId
                this.dataForm.reward = data.signuserrecord.reward/100
                this.dataForm.continuousNum = data.signuserrecord.continuousNum
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
              url: this.$http.adornUrl(`/signuserrecord/signuserrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'dayId': this.dataForm.dayId ,

          'userId': this.dataForm.userId ,

          'reward': this.dataForm.reward *100,

          'continuousNum': this.dataForm.continuousNum ,

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
