<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="分组" prop="groupId">
        <el-select v-model="dataForm.groupId" placeholder="不选默认为不分组" clearable>
          <el-option
            v-for="item in Options"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="文件名" prop="name">
        <el-input v-model="dataForm.name" placeholder="文件名"></el-input>
      </el-form-item>
    </el-form>
    <el-tooltip class="item" effect="dark" content="对应类型展示的图片" placement="top">
      <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
                 :file-list="fileList" :data="form" style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>
    <div style="color: red;">{{pictureSize}}</div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        Options: [],
        visible: false,
        pictureSize: '',
        fileList: [],
        num: 0,
        show: false,
        showPrise: false,
        form: null,
        successNum: 0,
        dataForm: {
          id: 0,
          name: '',
          url: '',
          type: '',
          groupId: ''
        },
        dataRule: {
          name: [{
            required: true,
            message: '请上传文件',
            trigger: 'blur'
          }]

          // groupId: [
          //   { required: true, message: '分组id不能为空', trigger: 'blur' }
          // ]
        }
      }
    },
    methods: {
      init(id) {
        //下拉获取图片尺寸
        this.$http({
          url: this.$http.adornUrl(`/picturesize/picturesize/select/0/15`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.pictureSize = data.pictureSize
          }
        })
        this.show = false
        this.dataForm.enclosureId = ''
        this.visible = true
        this.fileList = []
        this.dataForm.id = id || 0
        this.visible = true
        // this.$nextTick(() => {
        //   this.$refs['dataForm'].resetFields()
        //   if (this.dataForm.id) {
        //     this.$http({
        //       url: this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/info/${this.dataForm.id}`),
        //       method: 'get',
        //       params: this.$http.adornParams()
        //     }).then(({data}) => {
        //       if (data && data.code === 200) {
        //         this.dataForm.name = data.webhomeenclosure.name
        //         this.dataForm.url = data.webhomeenclosure.url
        //         this.dataForm.type = data.webhomeenclosure.type
        //         this.dataForm.groupId = data.webhomeenclosure.groupId
        //       }
        //     })
        //   }
        // })
        this.$http({
          url: this.$http.adornUrl(`/enclosuregroup/enclosuregroup/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.Options = data.list
          }
        })
      },
      UploadUrl: function () {
        this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
        return this.url;
      },
      // 照片上传之前
      beforeUploadHandle(file) {
        this.num++
      },
      // 照片上传成功
      successHandle(response, file, fileList, type) {

        this.type = type
        this.fileList = fileList
        this.successNum++
        if (response && response.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
          this.dataForm.id = response.id
          this.dataForm.name = response.name
          this.show = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,

                'url': this.dataForm.url,

                'type': this.dataForm.type,

                'groupId': this.dataForm.groupId,

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
