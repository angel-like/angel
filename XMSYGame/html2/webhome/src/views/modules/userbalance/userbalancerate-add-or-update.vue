<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

     <el-tooltip class="item" effect="dark" content="利率是万分之几" placement="top-start">
				<el-form-item label="利率%" prop="rate">
				  <el-input v-model="dataForm.rate" placeholder="利率%"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="什么时间生效" placement="top-start">
      		<el-form-item label="生效时间" prop="effectDate">
      		  <el-date-picker v-model="dataForm.effectDate" type="date" placeholder="生效时间" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      		</el-form-item>
      	</el-tooltip>




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
			var checkAmount = (rule, value, callback) => {
					if (value) {
						//var retgex=/^(1|1\.[0]*|0?\.(?!0+$)[\d]+)$/im;//限制0到1之间
						var retgex=/^(\d(\.\d{1,4})?|10)$/;//限制0到10之间.最多4位小数
						if (!retgex.test(value)) {
								callback(new Error('请输入0到10之间的数，最多包含4位小数'));
							}else{
								callback();
							}
					} else {
						callback();
					}
			};
      return {
        visible: false,
				multiple:10000,
        dataForm: {
          id: 0,
          rate: '',
          effectDate: '',
        },
        dataRule: {
          rate: [
            { required: true, message: '利率不能为空', trigger: 'blur' },
						{validator: checkAmount, trigger: 'blur'}
          ],
          effectDate: [
            { required: true, message: '生效时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userbalancerate/userbalancerate/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.rate = data.userbalancerate.rate*this.multiple
                this.dataForm.effectDate = data.userbalancerate.effectDate
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
					var ss=this.dataForm.rate;
					ss=this.dataForm.rate/this.multiple;
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/userbalancerate/userbalancerate/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'rate': ss ,
								'effectDate': this.dataForm.effectDate ,

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
