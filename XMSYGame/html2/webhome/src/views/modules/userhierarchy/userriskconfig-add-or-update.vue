<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="风控的层级" placement="top-start">
				<el-form-item label="层级" prop="hierarchyId">
					<el-select v-model="dataForm.hierarchyId" clearable  placeholder="请选择层级">
						<el-option
							v-for="item in optionsType"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				 </el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="选择风控的类型" placement="top-start">
				<el-form-item label="风控类型" prop="riskType">
						<el-select v-model="dataForm.riskType" placeholder="风控类型 " clearable>
							<el-option
								v-for="item in options"
								:key="item.code"
								:label="item.name"
								:value="item.code">
							</el-option>
						</el-select>
				</el-form-item>
        </el-tooltip>
      <el-tooltip class="item" effect="dark" content="设置风控系数" placement="top-start">
				<el-form-item label="风控系数" prop="riskVal">
				  <el-input v-model="dataForm.riskVal" placeholder="风控系数"></el-input>
				</el-form-item>
      </el-tooltip>
   <!-- <el-form-item label="层级id" prop="hierarchyId">
      <el-input v-model="dataForm.hierarchyId" placeholder="层级id"></el-input>
    </el-form-item> -->
    <!-- <el-form-item label="风控类型" prop="riskType">
      <el-input v-model="dataForm.riskType" placeholder="风控类型"></el-input>
    </el-form-item> -->

		<div style="color: red;">充值风控：用户当前余额超出充值金额*风控系数，会标记为当前风控层级并且账号状态会修改为风控</div>
		<div style="color: red;">未充值余额风控：未充值用户当前余额超出风控系数，会标记为当前风控层级并且账号状态会修改为风控</div>
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
			var validateInteger = (rule, value, callback) => {
				var res= /^[0-9]*[1-9][0-9]*$/;
				if (value === '') {
					callback();
				} else {
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}else{
						callback();
					}
				}
			};
      return {
        visible: false,
				options: [],
				optionsType: [],
        dataForm: {
          id: 0,
          hierarchyId: '',
          riskType: '',
          riskVal: '',
        },
        dataRule: {
          hierarchyId: [
            { required: true, message: '层级id不能为空', trigger: 'blur' }
          ],
          riskType: [
            { required: true, message: '风控类型不能为空', trigger: 'blur' }
          ],
          riskVal: [
            { required: true, message: '风控系数不能为空', trigger: 'blur' },
						{ validator: validateInteger, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//为风控类型下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/`+"riskConfig"),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.data
					}
				});
				//为层级下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
					method: 'get',
					params: this.$http.adornParams({"hierarchyType":1})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.optionsType = data.hierarchyList
					}
				});
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/userriskconfig/userriskconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.hierarchyId = data.userriskconfig.hierarchyId
                this.dataForm.riskType = data.userriskconfig.riskType
                this.dataForm.riskVal = data.userriskconfig.riskVal
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
              url: this.$http.adornUrl(`/userriskconfig/userriskconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'hierarchyId': this.dataForm.hierarchyId ,
								'riskType': this.dataForm.riskType ,
								'riskVal': this.dataForm.riskVal ,

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
