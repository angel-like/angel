<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="代理的等级名称" placement="top-start">
				<el-form-item label="层级名称" prop="name">
				  <el-input v-model="dataForm.name" placeholder="层级名称"></el-input>
				</el-form-item>
			</el-tooltip>
    <el-tooltip class="item" effect="dark" content="抽取代理线下的金钱返给代理商的比例" placement="top-start">
				<el-form-item label="佣金比例" prop="proportion" disabled="false">
				  <el-input style="width: 180px" v-model="dataForm.proportion"  type="text" placeholder="抽佣比例" clearable>
				  		<template slot="append">%</template>
				  </el-input>
				</el-form-item>
        </el-tooltip>
      <el-tooltip class="item" effect="dark" content="代理下线充值按照返佣比例返给代理商" placement="top-start">
				<el-form-item label="充值返佣比例" prop="commission">
				 <el-input style="width: 180px" v-model="dataForm.commission"  type="text" placeholder="充值返佣比例" clearable>
							<template slot="append">%</template>
				</el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="代理下线实名赠送给代理商的金币(单位:分)" placement="top-start">
				<el-form-item label="金币" prop="coin">
					<el-input v-model="dataForm.coin" placeholder="整数"></el-input>
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
			var validateNum = (rule, value, callback) => {
				var res= /^[0-9]+(.[0-9]{1,2})?$/;
				if (value === '') {
					callback(new Error('不可为空'));
				} else {
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}
					callback();
				}
			};
			//验证正整数
			var validateInteger = (rule, value, callback) => {
				var res= /^[0-9]*$/;
				if (value === '') {
					callback(new Error('不可为空'));
				} else {
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}
					callback();
				}
			};
      return {
        visible: false,
        dataForm: {
          id: 0,
          name: '',
					commission:'',
					proportion:'',
					coin:'',
        },
        dataRule: {
          name: [
            { required: true, message: '层级名称不能为空', trigger: 'blur' }
          ],
          proportion: [
            { validator: validateNum, trigger: 'blur' }
          ],
					commission: [
						{ validator: validateNum, trigger: 'blur' }
					],
					coin: [
						{ validator: validateInteger, trigger: 'blur' }
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
              url: this.$http.adornUrl(`/useragenthierarchy/useragenthierarchy/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.useragenthierarchy.name
                this.dataForm.proportion = data.useragenthierarchy.proportion*100
								this.dataForm.commission = data.useragenthierarchy.commission*100
								this.dataForm.coin = data.useragenthierarchy.coin
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
              url: this.$http.adornUrl(`/useragenthierarchy/useragenthierarchy/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'name': this.dataForm.name ,
								'proportion': this.dataForm.proportion/100 ,
								'commission': this.dataForm.commission/100 ,
								'coin': this.dataForm.coin
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
