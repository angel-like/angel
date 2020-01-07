<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
		:append-to-body="true"
		:modal-append-to-body="false"
		:close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-form-item label="道具名称" prop="propId">
				<el-select v-model="dataForm.propId" placeholder="道具名称 " clearable>
					<el-option
						v-for="item in options"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
				</el-select>
		</el-form-item>
    <el-form-item label="道具数量" prop="propNum">
      <el-input v-model="dataForm.propNum" placeholder="道具数量"></el-input>
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
				rate: 100,
        dataForm: {
          id: 0,
          messageId: '',
          propId: '',
          propNum: '',
        },
				options: [],
        dataRule: {
          messageId: [
            { required: true, message: '模板邮件id不能为空', trigger: 'blur' }
          ],
          propId: [
            { required: true, message: '道具id不能为空', trigger: 'blur' }
          ],
          propNum: [
            { required: true, message: '道具数量不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id,messageId) {
				//道具选择
				this.$http({
					url: this.$http.adornUrl(`/sysprop/sysprop/getProp`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.propList
					}
				});
        this.dataForm.id = id || 0
				this.dataForm.messageId = messageId
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/sysmessagemodelprop/sysmessagemodelprop/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.messageId = data.sysmessagemodelprop.messageId
                this.dataForm.propId = data.sysmessagemodelprop.propId
								if( this.dataForm.propId == 1){
									this.dataForm.propNum = data.sysmessagemodelprop.propNum/this.rate
								}else{
									this.dataForm.propNum = data.sysmessagemodelprop.propNum
								}

              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
				var ss=this.dataForm.propNum;
				if(this.dataForm.propId == 1){
					ss = this.dataForm.propNum * this.rate
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/sysmessagemodelprop/sysmessagemodelprop/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'messageId': this.dataForm.messageId ,
							'propId': this.dataForm.propId ,
							'propNum': ss ,
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
