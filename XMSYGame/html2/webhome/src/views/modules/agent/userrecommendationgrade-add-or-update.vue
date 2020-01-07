<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="代理等级的名称" placement="top-start">
    	<el-form-item label="名称" prop="name">
    	  <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    	</el-form-item>
    </el-tooltip>
    <el-tooltip class="item" effect="dark" content="总打码开始值" placement="top-start">
    	<el-form-item label="最小值" prop="miniValue">
    	  <el-input v-model="dataForm.miniValue" placeholder="最小值"></el-input>
    	</el-form-item>
    </el-tooltip>
    <el-tooltip class="item" effect="dark" content="总打码结束值" placement="top-start">
    	<el-form-item label="最大值" prop="maxValue">
    	  <el-input v-model="dataForm.maxValue" placeholder="最大值"></el-input>
    	</el-form-item>
    </el-tooltip>
    <el-tooltip class="item" effect="dark" content="每打码一万时所抽的金额(例如一万抽取50时设置为0.005)" placement="top-start">
    	<el-form-item label="返利优惠" prop="rewardRate">
    	  <el-input v-model="dataForm.rewardRate" placeholder="返利优惠"></el-input>
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
				var res= /^0$|^[1-9]\d{0,15}$|^[1-9]\d{0,15}\.{1}\d{1,4}$|^0\.{1}\d{1,4}$/g;
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
        dataForm: {
          id: 0,
          name: '',
          miniValue: 0,
          maxValue: 0,
          rewardRate: 0
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          miniValue: [
            { required: true, validator: validateInteger, trigger: 'blur' }
          ],
          maxValue: [
            { required: true, validator: validateInteger, trigger: 'blur' }
          ],
          rewardRate: [
            { required: true, validator: validateInteger, trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userrecommendationgrade/userrecommendationgrade/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.userrecommendationgrade.name
                this.dataForm.miniValue = data.userrecommendationgrade.miniValue
                this.dataForm.maxValue = data.userrecommendationgrade.maxValue
                this.dataForm.rewardRate = data.userrecommendationgrade.rewardRate
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
				if(this.dataForm.maxValue!=0){
					if((this.dataForm.maxValue-this.dataForm.miniValue)<0){
						this.$message.error("最小值不可大于最大值")
						return;
					}
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/userrecommendationgrade/userrecommendationgrade/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'miniValue': this.dataForm.miniValue ,

          'maxValue': this.dataForm.maxValue ,

          'rewardRate': this.dataForm.rewardRate


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
