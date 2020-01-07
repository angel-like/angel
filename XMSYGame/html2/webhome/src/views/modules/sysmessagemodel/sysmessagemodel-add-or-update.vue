<template>

  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="站内信邮件模板伪标题(给其他模板选用的标题)" placement="top-start">
				<el-form-item label="伪标题" prop="name">
				  <el-input v-model="dataForm.name" placeholder="伪标题"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="站内信邮件模板标题" placement="top-start">
				<el-form-item label="标题" prop="title">
				  <el-input v-model="dataForm.title" placeholder="标题"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="站内信邮件模板内容" placement="top-start">
				<el-form-item label="内容" prop="content">
				  <el-input v-model="dataForm.content" placeholder="内容"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="站内信邮件模板是否启用" placement="top-start">
				<el-form-item label="状态" prop="enable">
				  <el-radio-group v-model="dataForm.enable">
				  	<el-radio :label="true">启用</el-radio>
				  	<el-radio :label="false">禁用</el-radio>
				  </el-radio-group>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="如果为否，在会员邮件中会显示领取按钮，反之不显示领取按钮" placement="top-start">
				<el-form-item label="是否只读" prop="readonly">
				  <el-radio-group v-model="dataForm.readonly">
				  	<el-radio :label="true">是</el-radio>
				  	<el-radio :label="false">否</el-radio>
				  </el-radio-group>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="设置邮件可以在邮箱中保存多久,超过期限,邮件就不会在邮箱中显示" placement="top-start">
				<el-form-item label="有效期限（天）" prop="effectTime">
				  <el-input v-model="dataForm.effectTime" placeholder="有效期限（天）"></el-input>
				</el-form-item>
			</el-tooltip>
   <!-- <el-form-item label="类型" prop="contentType">
      <el-radio-group v-model="dataForm.contentType">
      	<el-radio :label="1">会员站内信</el-radio>
      	<el-radio :label="2">管理员站内信</el-radio>
      </el-radio-group>
    </el-form-item> -->
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
          title: '',
          content: '',
          contentType: 1,
          enable: true,
          readonly: true,
          effectTime: '',
        },
        dataRule: {
          name: [
            { required: true, message: '伪标题不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          contentType: [
            { required: true, message: '类型(1：会员站内信 2:管理员站内信）不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态（0：禁用  1：启用）不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '模式 0：后台发放 1：客户领取不能为空', trigger: 'blur' }
          ],
          effectTime: [
            { required: true, message: '有效期限（天），0是无限制不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/sysmessagemodel/sysmessagemodel/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.sysmessagemodel.name
                this.dataForm.title = data.sysmessagemodel.title
                this.dataForm.content = data.sysmessagemodel.content
                this.dataForm.contentType = data.sysmessagemodel.contentType
                this.dataForm.enable = data.sysmessagemodel.enable
                this.dataForm.readonly = data.sysmessagemodel.readonly
                this.dataForm.effectTime = data.sysmessagemodel.effectTime
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
              url: this.$http.adornUrl(`/sysmessagemodel/sysmessagemodel/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'name': this.dataForm.name ,
							'title': this.dataForm.title ,
							'content': this.dataForm.content ,
							'contentType': this.dataForm.contentType ,
							'enable': this.dataForm.enable ,
							'readonly': this.dataForm.readonly ,
							'effectTime': this.dataForm.effectTime ,

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
