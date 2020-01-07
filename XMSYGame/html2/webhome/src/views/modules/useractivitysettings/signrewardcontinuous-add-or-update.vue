<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="用户连续登陆的天数" placement="top-start">
    	<el-form-item label="连续登陆天数" prop="dayNum">
    	  <el-input v-model="dataForm.dayNum" placeholder="连续登陆天数"></el-input>
    	</el-form-item>
    </el-tooltip>
    <el-tooltip class="item" effect="dark" content="连续登陆的天数对应的奖励" placement="top-start">
    	<el-form-item label="奖励" prop="reward">
    	  <el-input v-model="dataForm.reward" placeholder="奖励"></el-input>
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
          dayNum: '',
          reward: '',
        },
        dataRule: {
          dayNum: [
            { required: true, message: '连续登陆天数不能为空', trigger: 'blur' }
          ],
          reward: [
            { required: true, message: '奖励不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/signrewardcontinuous/signrewardcontinuous/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.dayNum = data.signrewardcontinuous.dayNum
                this.dataForm.reward = data.signrewardcontinuous.reward/100
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
              url: this.$http.adornUrl(`/signrewardcontinuous/signrewardcontinuous/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'dayNum': this.dataForm.dayNum ,

          'reward': this.dataForm.reward *100

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
