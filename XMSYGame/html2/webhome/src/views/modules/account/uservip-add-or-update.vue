<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="vip对应的等级名称" placement="top-start">
				<el-form-item label="等级名称" prop="name">
				  <el-input v-model="dataForm.name" placeholder="vip等级名称"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="充值的钱达到某个值，自动升级为所对应的等级" placement="top-start">
				<el-form-item label="充值达到" prop="rechargeReached">
				  <el-input v-model="dataForm.rechargeReached" placeholder="充值达到"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="充值优惠比例(充值的钱*充值优惠比例=优惠金额)" placement="top-start">
				<el-form-item label="充值优惠比例" prop="rechargeRate">
					<el-input style="width: 140px" v-model="dataForm.rechargeRate"  type="text" placeholder="正整数" clearable>
							<template slot="append">%</template>
					</el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="每个等级的都有唯一的排序，排序字段不能重复" placement="top-start">
				<el-form-item label="排序" prop="sort">
					<el-input-number v-model="dataForm.sort" controls-position="right" :min="0" label="排序号"></el-input-number>
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
			//验证正整数
			var validateRate = (rule, value, callback) => {
				// var res= /^([1-9]+\\d*)|[0]/;
				var res= /^\d+$/;
					if(!res.test(value)){
						callback(new Error('格式有误,请输入正整数'));
					}else{
						callback();
					}
				// }
			};
      return {
        visible: false,
				rate:100,
        dataForm: {
          id: 0,
          name: '',
          rechargeReached: '',
          rechargeRate: '',
					sort: '',
        },
        dataRule: {
          name: [
            { required: true, message: 'vip等级名称不能为空', trigger: 'blur' }
          ],
          rechargeReached: [
            { required: true, message: '充值达到不能为空', trigger: 'blur' },
						{ validator: validateRate, trigger: 'blur' }
          ],
          rechargeRate: [
            { required: true, message: '充值优惠比例不能为空', trigger: 'blur' },
						{ validator: validateRate, trigger: 'blur' }
          ],
					 sort: [
					  { required: true, message: '排序不能为空', trigger: 'blur' },
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
              url: this.$http.adornUrl(`/uservip/uservip/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.uservip.name
                this.dataForm.rechargeReached = data.uservip.rechargeReached
                this.dataForm.rechargeRate = data.uservip.rechargeRate*this.rate
								this.dataForm.sort = data.uservip.sort

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
              url: this.$http.adornUrl(`/uservip/uservip/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'name': this.dataForm.name ,
								'rechargeReached': this.dataForm.rechargeReached ,
								'rechargeRate': this.dataForm.rechargeRate/this.rate ,
								'sort': this.dataForm.sort ,

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
