<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="道具名称id" prop="sysPropId">
      <el-input v-model="dataForm.sysPropId" placeholder="道具名称id"></el-input>
    </el-form-item>
    <el-form-item label="道具数量" prop="propNumber">
      <el-input v-model="dataForm.propNumber" placeholder="道具数量"></el-input>
    </el-form-item>
    <el-form-item label="类型0：购买   1：使用" prop="type">
      <el-input v-model="dataForm.type" placeholder="类型0：购买   1：使用"></el-input>
    </el-form-item>
    <el-form-item label="产品id" prop="produceId">
      <el-input v-model="dataForm.produceId" placeholder="产品id"></el-input>
    </el-form-item>
    <el-form-item label="会员id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="会员id"></el-input>
    </el-form-item>
    <el-form-item label="会员账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="会员账号"></el-input>
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
          sysPropId: '',
          propNumber: '',
          type: '',
          produceId: '',
          userId: '',
          userAccount: '',
        },
        dataRule: {
          sysPropId: [
            { required: true, message: '道具名称id不能为空', trigger: 'blur' }
          ],
          propNumber: [
            { required: true, message: '道具数量不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型0：购买   1：使用不能为空', trigger: 'blur' }
          ],
          produceId: [
            { required: true, message: '产品id不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '会员id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '会员账号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/shopproprecord/shopproprecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.sysPropId = data.shopproprecord.sysPropId
                this.dataForm.propNumber = data.shopproprecord.propNumber
                this.dataForm.type = data.shopproprecord.type
                this.dataForm.produceId = data.shopproprecord.produceId
                this.dataForm.userId = data.shopproprecord.userId
                this.dataForm.userAccount = data.shopproprecord.userAccount
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
              url: this.$http.adornUrl(`/shopproprecord/shopproprecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'sysPropId': this.dataForm.sysPropId ,

          'propNumber': this.dataForm.propNumber ,

          'type': this.dataForm.type ,

          'produceId': this.dataForm.produceId ,

          'userId': this.dataForm.userId ,

          'userAccount': this.dataForm.userAccount ,

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
