<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="赠送金币" prop="coin">
      <el-input v-model="dataForm.coin" placeholder="赠送金币"></el-input>
    </el-form-item>
		<el-form-item label="赠送佣金" prop="commission">
			<el-input v-model="dataForm.commission" placeholder="赠送佣金"></el-input>
		</el-form-item>
		<el-form-item label="返佣比例" prop="proportion">
			<el-input style="width: 120px" v-model="dataForm.proportion"  type="text" placeholder="返佣比例" clearable>
				<template slot="append">%</template>
			</el-input>
		</el-form-item>
		<el-form-item label="代理层级" prop="agentHierarchyId">
			<el-select v-model="dataForm.agentHierarchyId" placeholder="请选择代理层级" clearable>
				<el-option
					v-for="item in options"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
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
          id: 0,
          coin: '',
					commission: '',
					proportion: '',
					agentHierarchyId: ''
        },
        dataRule: {
          coin: [
            { required: true, message: '赠送金币不能为空', trigger: 'blur' }
          ],
					commission: [
						{ required: true, message: '赠送佣金不能为空', trigger: 'blur' }
					],
					proportion: [
						{ required: true, message: '返佣比例不能为空', trigger: 'blur' }
					],
					agentHierarchyId: [
						{ required: true, message: '代理层级不能为空', trigger: 'blur' }
					]
        }
      }
    },
    methods: {
      init (id) {
				console.log(1)
				//为游戏下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/useragenthierarchy/useragenthierarchy/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.list
						console.log(data.list);
					}
				});
				
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/userregistergift/userregistergift/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.coin = data.userregistergift.coin
								this.dataForm.commission = data.userregistergift.commission
								this.dataForm.proportion = data.userregistergift.proportion*100
								this.dataForm.agentHierarchyId = data.userregistergift.agentHierarchyId
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
              url: this.$http.adornUrl(`/userregistergift/userregistergift/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'coin': this.dataForm.coin ,
					'commission': this.dataForm.commission ,
					'proportion': this.dataForm.proportion/100 ,
					'agentHierarchyId': this.dataForm.agentHierarchyId

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
