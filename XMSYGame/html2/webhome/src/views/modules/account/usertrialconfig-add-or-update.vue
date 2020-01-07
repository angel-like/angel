<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="试玩个数" prop="trialNumber">
      <el-input type="number" v-model="dataForm.trialNumber" placeholder="试玩个数"></el-input>
    </el-form-item>
    <el-form-item label="试玩金额" prop="trialAmount">
      <el-input type="number" v-model="dataForm.trialAmount" placeholder="试玩金额"></el-input>
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
				oldConfig:{},
        dataForm: {
          id: 0,
          trialNumber: '',
          trialAmount: '',
        },
        dataRule: {
          trialNumber: [
            { required: true, message: '试玩个数不能为空', trigger: 'blur' }
          ],
          trialAmount: [
            { required: true, message: '试玩金额不能为空', trigger: 'blur' }
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
					this.$http({
						url: this.$http.adornUrl(`/usertrialconfig/usertrialconfig/getinfo`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.oldConfig=data.usertrialconfig;
							this.dataForm.id = data.usertrialconfig.id
							this.dataForm.trialNumber = data.usertrialconfig.trialNumber
							this.dataForm.trialAmount = data.usertrialconfig.trialAmount
						}
					})
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/usertrialconfig/usertrialconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'trialNumber': this.dataForm.trialNumber ,
							'trialAmount': this.dataForm.trialAmount 
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
