<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房间id" prop="roomId">
      <el-input v-model="dataForm.roomId" placeholder="房间id"></el-input>
    </el-form-item>
    <el-form-item label="实际有效库存" prop="stock">
      <el-input v-model="dataForm.stock" placeholder="实际有效库存"></el-input>
    </el-form-item>
    <el-form-item label="库存上限" prop="stockLimit">
      <el-input v-model="dataForm.stockLimit" placeholder="库存上限"></el-input>
    </el-form-item>
    <el-form-item label="库存标准值" prop="stockStandard">
      <el-input v-model="dataForm.stockStandard" placeholder="库存标准值"></el-input>
    </el-form-item>
    <el-form-item label="库存上限标准值" prop="stockLimitStandard">
      <el-input v-model="dataForm.stockLimitStandard" placeholder="库存上限标准值"></el-input>
    </el-form-item>
    <el-form-item label="抽税" prop="tax">
      <el-input v-model="dataForm.tax" placeholder="抽税"></el-input>
    </el-form-item>
    <el-form-item label="抽税比例" prop="taxRate">
      <el-input v-model="dataForm.taxRate" placeholder="抽税比例"></el-input>
    </el-form-item>
    <el-form-item label="是否启动" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="是否启动"></el-input>
    </el-form-item>
    <el-form-item label="操作者id" prop="sysUserId">
      <el-input v-model="dataForm.sysUserId" placeholder="操作者id"></el-input>
    </el-form-item>
    <el-form-item label="操作者名称" prop="sysUserName">
      <el-input v-model="dataForm.sysUserName" placeholder="操作者名称"></el-input>
    </el-form-item>
    <el-form-item label="操作内容" prop="operationContent">
      <el-input v-model="dataForm.operationContent" placeholder="操作内容"></el-input>
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
          roomId: '',
          stock: '',
          stockLimit: '',
          stockStandard: '',
          stockLimitStandard: '',
          tax: '',
          taxRate: '',
          enable: '',
          sysUserId: '',
          sysUserName: '',
          operationContent: '',
        },
        dataRule: {
          roomId: [
            { required: true, message: '房间id不能为空', trigger: 'blur' }
          ],
          stock: [
            { required: true, message: '实际有效库存不能为空', trigger: 'blur' }
          ],
          stockLimit: [
            { required: true, message: '库存上限不能为空', trigger: 'blur' }
          ],
          stockStandard: [
            { required: true, message: '库存标准值不能为空', trigger: 'blur' }
          ],
          stockLimitStandard: [
            { required: true, message: '库存上限标准值不能为空', trigger: 'blur' }
          ],
          tax: [
            { required: true, message: '抽税不能为空', trigger: 'blur' }
          ],
          taxRate: [
            { required: true, message: '抽税比例不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '是否启动不能为空', trigger: 'blur' }
          ],
          sysUserId: [
            { required: true, message: '操作者id不能为空', trigger: 'blur' }
          ],
          sysUserName: [
            { required: true, message: '操作者名称不能为空', trigger: 'blur' }
          ],
          operationContent: [
            { required: true, message: '操作内容不能为空', trigger: 'blur' }
          ],
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
              url: this.$http.adornUrl(`/gamestockoperationrecord/gamestockoperationrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.roomId = data.gamestockoperationrecord.roomId
                this.dataForm.stock = data.gamestockoperationrecord.stock
                this.dataForm.stockLimit = data.gamestockoperationrecord.stockLimit
                this.dataForm.stockStandard = data.gamestockoperationrecord.stockStandard
                this.dataForm.stockLimitStandard = data.gamestockoperationrecord.stockLimitStandard
                this.dataForm.tax = data.gamestockoperationrecord.tax
                this.dataForm.taxRate = data.gamestockoperationrecord.taxRate
                this.dataForm.enable = data.gamestockoperationrecord.enable
                this.dataForm.sysUserId = data.gamestockoperationrecord.sysUserId
                this.dataForm.sysUserName = data.gamestockoperationrecord.sysUserName
                this.dataForm.operationContent = data.gamestockoperationrecord.operationContent
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
              url: this.$http.adornUrl(`/gamestockoperationrecord/gamestockoperationrecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'roomId': this.dataForm.roomId ,

          'stock': this.dataForm.stock ,

          'stockLimit': this.dataForm.stockLimit ,

          'stockStandard': this.dataForm.stockStandard ,

          'stockLimitStandard': this.dataForm.stockLimitStandard ,

          'tax': this.dataForm.tax ,

          'taxRate': this.dataForm.taxRate ,

          'enable': this.dataForm.enable ,

          'sysUserId': this.dataForm.sysUserId ,

          'sysUserName': this.dataForm.sysUserName ,

          'operationContent': this.dataForm.operationContent ,

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
