<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="不能修改" placement="top-start">
				<el-form-item label="开放层级" prop="hierarchyId" >
					<el-select v-model="dataForm.hierarchyId"  placeholder="请选择开放层级" disabled>
						<el-option
							v-for="item in options"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
          </el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="不能修改" placement="top-start">
      	<el-form-item label="充值金额" prop="firstRecharge">
      		<el-select v-model="dataForm.firstRecharge" clearable placeholder="请选择优惠类型" disabled>
      			<el-option
      				v-for="item in typeOptions"
      				:key="item.name"
      				:label="item.label"
      				:value="item.name">
      			</el-option>
      		</el-select>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="修改充值多少送多少金额,例如我现在100改成200到时间就要充值到200" placement="top-start">
      	<el-form-item label="充值金额" prop="rechargeAmount">
      	  <el-input v-model="dataForm.rechargeAmount" placeholder="充值金额(最低要求)" clearable></el-input>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="充值赠送比例" placement="top-start">
      	<el-form-item label="返利比例" prop="giftProportion" >
      	  <el-input style="width: 120px" v-model="dataForm.giftProportion"  type="text" placeholder="整数" clearable>
      				<template slot="append">%</template>
      		</el-input>
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
				var res= /^[0-9]*[1-9][0-9]*$/;
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
				options:[],
				typeOptions: [{
					name: false,
					label: '满送'
				}, {
					name: true,
					label: '首充'
				}],
        dataForm: {
          id: 0,
          rechargeAmount: '',
          giftProportion: '',
					firstRecharge:'',
					hierarchyId:'',
        },
        dataRule: {
          hierarchyId: [
            { required: true, message:'开放层级不能为空', trigger: 'blur' }
          ],
					firstRecharge: [
						{ required: true, message:'优惠类型不能为空', trigger: 'blur' }
					],
					giftProportion: [
						{ validator: validateRate, trigger: 'blur' }
					],
          rechargeAmount: [
            { validator: validateRate, trigger: 'blur' }
          ]

        }
      }
    },
    methods: {
      init (id) {
				this.$http({
					url: this.$http.adornUrl(`/userhierarchy/userhierarchy/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.list
					}
				});
        this.dataForm.id = id || 0
        this.visible = true
				this.fileList = []
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/orderpreferential/orderpreferential/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.rechargeAmount = data.orderpreferential.rechargeAmount
                this.dataForm.giftProportion = data.orderpreferential.giftProportion*100
								this.dataForm.hierarchyId =data.orderpreferential.hierarchyId
								this.dataForm.firstRecharge =data.orderpreferential.firstRecharge
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
              url: this.$http.adornUrl(`/orderpreferential/orderpreferential/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'rechargeAmount': this.dataForm.rechargeAmount ,
          'giftProportion': this.dataForm.giftProportion/100 ,
					'hierarchyId': this.dataForm.hierarchyId,
					'firstRecharge': this.dataForm.firstRecharge
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
      },
    }
  }
</script>
