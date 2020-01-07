<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="头像框标题" placement="top-start">
    <el-form-item label="标题" prop="title">
    	<el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="头像框类型" placement="top-start">
		<el-form-item label="类型 " prop="type">
			<el-select v-model="dataForm.type" placeholder="类型 " @change="getTypeId()"  clearable>
				<el-option
					v-for="item in dictionaryOptions"
					:key="item.code"
					:label="item.name"
					:value="item.code">
				</el-option>
			</el-select>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="头像框id(根据上个字段的改变而改变)" placement="top-start">
    <el-form-item label="类型id" prop="typeId">
      <el-select v-model="dataForm.typeId" placeholder="类型id " clearable>
      	<el-option
      		v-for="item in typeIdOptions"
      		:key="item.id"
      		:label="item.name"
      		:value="item.id">
      	</el-option>
      </el-select>
    </el-form-item>
		</el-tooltip>
		<el-form-item label="是否可用"  prop="enable">
			<el-radio-group v-model="dataForm.enable">
				<el-radio :label="true">是</el-radio>
				<el-radio :label="false">否</el-radio>
			</el-radio-group>
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
				dictionary: 'headframeType',
				dictionaryOptions:[],
				typeIdOptions:[],
				typeIdPre:[{"id":0,"name":"VIP0"}],
        dataForm: {
          id: 0,
          type: '',
          typeId: '',
          enable: true,
          title: '',
        },
        dataRule: {
          type: [
            { required: true, message: '类型 不能为空', trigger: 'blur' }
          ],
          typeId: [
            { required: true, message: '类型id不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
			getTypeId(){
				this.$http({
					url: this.$http.adornUrl(`/headframe/headframe/typeIdList/${this.dataForm.type}`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.typeIdOptions = this.typeIdPre.concat(data.list);
					}else{
						this.typeIdOptions = this.typeIdPre;
					}
				})
			},
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.$http({
						url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/${this.dictionary}`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.dictionaryOptions = data.data;
						}else{
							this.dictionaryOptions = [];
						}
					})
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/headframe/headframe/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.type = data.headframe.type
                this.dataForm.typeId = data.headframe.typeId
                this.dataForm.title = data.headframe.title
                this.dataForm.enable = data.headframe.enable
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
              url: this.$http.adornUrl(`/headframe/headframe/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'type': this.dataForm.type ,
								'typeId': this.dataForm.typeId ,
								'title': this.dataForm.title ,
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
