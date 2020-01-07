<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="利率" prop="rate">
      <el-input v-model="dataForm.rate" placeholder="利率"></el-input>
    </el-form-item>
    <el-form-item label="收益" prop="profit">
      <el-input v-model="dataForm.profit" placeholder="收益"></el-input>
    </el-form-item>
    <el-form-item label="收益日期" prop="incomeDate">
      <el-input v-model="dataForm.incomeDate" placeholder="收益日期"></el-input>
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
          money: '',
          rate: '',
          profit: '',
          incomeDate: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          rate: [
            { required: true, message: '利率不能为空', trigger: 'blur' }
          ],
          profit: [
            { required: true, message: '收益不能为空', trigger: 'blur' }
          ],
          incomeDate: [
            { required: true, message: '收益日期不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userprofitrecord/userprofitrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.userprofitrecord.userId
                this.dataForm.money = data.userprofitrecord.money
                this.dataForm.rate = data.userprofitrecord.rate
                this.dataForm.profit = data.userprofitrecord.profit
                this.dataForm.incomeDate = data.userprofitrecord.incomeDate
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
              url: this.$http.adornUrl(`/userprofitrecord/userprofitrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'money': this.dataForm.money ,

          'rate': this.dataForm.rate ,

          'profit': this.dataForm.profit ,

          'incomeDate': this.dataForm.incomeDate ,

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
