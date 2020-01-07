<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
		<div style="margin: -64px 60px 40px 60px;">
					<el-tooltip class="item" effect="dark" content="服务器管理" placement="top">
						<i class="el-icon-question" style="color:blue" ></i>
					</el-tooltip>
		</div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="服务器名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="服务器名称"></el-input>
    </el-form-item>
    <el-form-item label="服务器路径" prop="serviceUrl">
      <el-input v-model="dataForm.serviceUrl" placeholder="服务器路径"></el-input>
    </el-form-item>
    <el-form-item label="是否启用" prop="enable">
				<el-switch
					v-model="dataForm.enable"
					active-color="#13ce66"
					inactive-color="#ff4949">
				</el-switch>
    </el-form-item>
    <el-form-item label="排序号" prop="orderNo">
			<el-input-number v-model="dataForm.orderNo"  :min="1"  label="排序号"></el-input-number>
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
          serviceUrl: '',
          enable: true,
          orderNo: '',
        },
        dataRule: {
          name: [
            { required: true, message: '服务器名称不能为空', trigger: 'blur' }
          ],
          serviceUrl: [
            { required: true, message: '服务器路径不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '是否启用：1启用不能为空', trigger: 'blur' }
          ],
          orderNo: [
            { required: true, message: '排序号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/imservicemanager/imservicemanager/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.imservicemanager.name
                this.dataForm.serviceUrl = data.imservicemanager.serviceUrl
                this.dataForm.enable = data.imservicemanager.enable
                this.dataForm.orderNo = data.imservicemanager.orderNo
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
              url: this.$http.adornUrl(`/imservicemanager/imservicemanager/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'serviceUrl': this.dataForm.serviceUrl ,

          'enable': this.dataForm.enable ,

          'orderNo': this.dataForm.orderNo ,

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
