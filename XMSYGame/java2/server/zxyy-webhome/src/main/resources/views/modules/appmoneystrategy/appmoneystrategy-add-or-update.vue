<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="路径" prop="url">
      <el-input v-model="dataForm.url" placeholder="路径"></el-input>
    </el-form-item>
    <el-form-item label="图片id" prop="enclosureId">
      <el-input v-model="dataForm.enclosureId" placeholder="图片id"></el-input>
    </el-form-item>
    <el-form-item label="状态（1.不可点击，2：可跳转，3，跳游戏）" prop="type">
      <el-input v-model="dataForm.type" placeholder="状态（1.不可点击，2：可跳转，3，跳游戏）"></el-input>
    </el-form-item>
    <el-form-item label="md5" prop="md5">
      <el-input v-model="dataForm.md5" placeholder="md5"></el-input>
    </el-form-item>
    <el-form-item label="是否可用" prop="availability">
      <el-input v-model="dataForm.availability" placeholder="是否可用"></el-input>
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
          name: '',
          url: '',
          enclosureId: '',
          type: '',
          md5: '',
          availability: '',
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '路径不能为空', trigger: 'blur' }
          ],
          enclosureId: [
            { required: true, message: '图片id不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '状态（1.不可点击，2：可跳转，3，跳游戏）不能为空', trigger: 'blur' }
          ],
          md5: [
            { required: true, message: 'md5不能为空', trigger: 'blur' }
          ],
          availability: [
            { required: true, message: '是否可用不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/appmoneystrategy/appmoneystrategy/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.appmoneystrategy.name
                this.dataForm.url = data.appmoneystrategy.url
                this.dataForm.enclosureId = data.appmoneystrategy.enclosureId
                this.dataForm.type = data.appmoneystrategy.type
                this.dataForm.md5 = data.appmoneystrategy.md5
                this.dataForm.availability = data.appmoneystrategy.availability
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
              url: this.$http.adornUrl(`/appmoneystrategy/appmoneystrategy/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'url': this.dataForm.url ,

          'enclosureId': this.dataForm.enclosureId ,

          'type': this.dataForm.type ,

          'md5': this.dataForm.md5 ,

          'availability': this.dataForm.availability ,

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
