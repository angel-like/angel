<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="现有实名返利和用户下注返水" placement="top-start">
				<el-form-item label="返利类型" prop="type">
					<el-select v-model="dataForm.type" placeholder="返利类型 " clearable>
						<el-option
							v-for="item in options"
							:key="item.code"
							:label="item.name"
							:value="item.code">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="针对实名返利的，直接输入相应的金币" placement="top-start">
				<el-form-item label="返利金币" prop="coin">
				  <el-input v-model="dataForm.coin" placeholder="返利金币"></el-input>
				</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="针对用户下注返水的，输入值为0~100" placement="top-start">
				<el-form-item label="返利比例" prop="waterRate">
				  <el-input v-model="dataForm.waterRate" placeholder="返利比例" max="100" min="1" ></el-input>
				</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="所得金币需要打多少码提现才能免行政费" placement="top-start">
				<el-form-item label="打码倍数" prop="codeMultiple">
					<el-input v-model="dataForm.codeMultiple" placeholder="打码倍数"  ></el-input>
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
			var validateInteger = (rule, value, callback) => {
				var res= /^\d+$/;
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
			var validateMultiple = (rule, value, callback) => {
				var res= /^[0-9]+(.[0-9]{0,2})?$/;
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
        dataForm: {
          id: 0,
          type: '',
          coin: '',
          waterRate: '',
					codeMultiple:''
        },
        dataRule: {
          type: [
            { required: true, message: '返利类型不能为空', trigger: 'blur' }
          ],
          coin: [
            { validator: validateInteger, trigger: 'blur' }
          ],
          waterRate: [
            { validator: validateMultiple, trigger: 'blur' }
          ],
					codeMultiple: [
						{ required: true, message: '打码倍数不能为空', trigger: 'blur' },
            { validator: validateMultiple, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//为游戏下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/`+"RewardType"),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.data
					}
				});
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/userrebate/userrebate/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.type = data.userrebate.type.toString()
                this.dataForm.coin = data.userrebate.coin/100
                this.dataForm.waterRate = data.userrebate.waterRate*100
                this.dataForm.codeMultiple = data.userrebate.codeMultiple
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
              url: this.$http.adornUrl(`/userrebate/userrebate/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'type': this.dataForm.type ,
								'coin': this.dataForm.coin *100,
								'waterRate': this.dataForm.waterRate/100 ,
								'codeMultiple': this.dataForm.codeMultiple,


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
