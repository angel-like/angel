<template>
  <el-dialog
    :title=" '批量修改会员层级'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="层级" prop="hierarchyId">
        <el-select v-model="dataForm.hierarchyId" placeholder="请选择层级">
					<el-option
						v-for="item in options"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
				</el-select>
      </el-form-item>
			<el-form-item label="会员账号" prop="account">
				<el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="dataForm.account"> </el-input>
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
				options:[],
        dataForm: {
          id: [],
          hierarchyId: '',
					account:[],
        },
        dataRule: {
          hierarchyId: [
            { required: true, message: '层级不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id,accounts) {
				this.dataForm.id = id;
				this.visible = true;
				this.dataForm.account = accounts.join(',');
				this.$nextTick(() => {
				this.$refs['dataForm'].resetFields();
					this.$http({
						url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
						method: 'get',
						params: this.$http.adornParams({"hierarchyType":0})
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.options = data.hierarchyList
						}
					});
				})
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/user/user/batchUpdateHierarchy`),
              method: 'post',
              data: this.$http.adornData({
                'hierarchyId': this.dataForm.hierarchyId,
                'ids': this.dataForm.id,
								
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
