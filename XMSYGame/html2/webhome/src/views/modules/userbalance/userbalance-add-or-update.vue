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
    <el-form-item label="未计算收益的金额" prop="unprofitMoney">
      <el-input v-model="dataForm.unprofitMoney" placeholder="未计算收益的金额"></el-input>
    </el-form-item>
    <el-form-item label="总收益" prop="profit">
      <el-input v-model="dataForm.profit" placeholder="总收益"></el-input>
    </el-form-item>
    <el-form-item label="昨日收益" prop="profitYesterday">
      <el-input v-model="dataForm.profitYesterday" placeholder="昨日收益"></el-input>
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
          unprofitMoney: '',
          profit: '',
          profitYesterday: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          unprofitMoney: [
            { required: true, message: '未计算收益的金额不能为空', trigger: 'blur' }
          ],
          profit: [
            { required: true, message: '总收益不能为空', trigger: 'blur' }
          ],
          profitYesterday: [
            { required: true, message: '昨日收益不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userbalance/userbalance/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.userbalance.userId
                this.dataForm.money = data.userbalance.money
                this.dataForm.unprofitMoney = data.userbalance.unprofitMoney
                this.dataForm.profit = data.userbalance.profit
                this.dataForm.profitYesterday = data.userbalance.profitYesterday
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
              url: this.$http.adornUrl(`/userbalance/userbalance/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'money': this.dataForm.money ,

          'unprofitMoney': this.dataForm.unprofitMoney ,

          'profit': this.dataForm.profit ,

          'profitYesterday': this.dataForm.profitYesterday ,

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
