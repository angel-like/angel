<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="栏目id" prop="columnId">
      <el-input v-model="dataForm.columnId" placeholder="栏目id"></el-input>
    </el-form-item>
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="链接地址" prop="url">
      <el-input v-model="dataForm.url" placeholder="链接地址"></el-input>
    </el-form-item>
    <el-form-item label="图片ID" prop="iconId">
      <el-input v-model="dataForm.iconId" placeholder="图片ID"></el-input>
    </el-form-item>
    <el-form-item label="图片MD5" prop="iconMd5">
      <el-input v-model="dataForm.iconMd5" placeholder="图片MD5"></el-input>
    </el-form-item>
    <el-form-item label="状态（0：禁用  1：启用）" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="状态（0：禁用  1：启用）"></el-input>
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
          columnId: '',
          name: '',
          url: '',
          iconId: '',
          iconMd5: '',
          enable: '',
        },
        dataRule: {
          columnId: [
            { required: true, message: '栏目id不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '链接地址不能为空', trigger: 'blur' }
          ],
          iconId: [
            { required: true, message: '图片ID不能为空', trigger: 'blur' }
          ],
          iconMd5: [
            { required: true, message: '图片MD5不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态（0：禁用  1：启用）不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/friendshiplink/friendshiplink/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.columnId = data.friendshiplink.columnId
                this.dataForm.name = data.friendshiplink.name
                this.dataForm.url = data.friendshiplink.url
                this.dataForm.iconId = data.friendshiplink.iconId
                this.dataForm.iconMd5 = data.friendshiplink.iconMd5
                this.dataForm.enable = data.friendshiplink.enable
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
              url: this.$http.adornUrl(`/friendshiplink/friendshiplink/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'columnId': this.dataForm.columnId ,

          'name': this.dataForm.name ,

          'url': this.dataForm.url ,

          'iconId': this.dataForm.iconId ,

          'iconMd5': this.dataForm.iconMd5 ,

          'enable': this.dataForm.enable ,

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
