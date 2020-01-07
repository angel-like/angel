<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="签到的名称,例如:本周内第一次签到,就叫第一天" placement="top-start">
				<el-form-item label="签到名称" prop="dayName">
				  <el-input v-model="dataForm.dayName" placeholder="签到名称"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="这周的第几天" placement="top-start">
				<el-form-item label="第几天" prop="dayNum">
				  <el-input v-model="dataForm.dayNum" placeholder="第几天"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="对应的奖励" placement="top-start">
				<el-form-item label="奖励" prop="reward">
				  <el-input v-model="dataForm.reward" placeholder="奖励"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="选择vip等级" placement="top-start">
				<el-form-item label="VIP等级" prop="vipId">
					<el-select v-model="dataForm.vipId" clearable  placeholder="VIP等级">
						<el-option
							v-for="item in options"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
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
      return {
        visible: false,
				options:[],
        dataForm: {
          id: 0,
          dayName: '',
          reward: '',
					vipId: '',
					dayNum: '',
        },
        dataRule: {
          dayName: [
            { required: true, message: '签到名称不能为空', trigger: 'blur' }
          ],
					vipId: [
					  { required: true, message: 'vipId不能为空', trigger: 'blur' }
					],
					dayNum: [
					  { required: true, message: '第几天不能为空', trigger: 'blur' },
            { validator: validateInteger,message: '第几天格式不正确', trigger: 'blur' }
					],
          reward: [
            { required: true, message: '奖励不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//vip等级选择
				this.$http({
					url: this.$http.adornUrl(`/uservip/uservip/getVIPGrade`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.userVipEntity
					}
				});
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/signrewardcontinuouseveryday/signrewardcontinuouseveryday/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.dayName = data.signrewardcontinuouseveryday.dayName
                this.dataForm.reward = data.signrewardcontinuouseveryday.reward/100
								this.dataForm.dayNum = data.signrewardcontinuouseveryday.dayNum
								this.dataForm.vipId = data.signrewardcontinuouseveryday.vipId
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
					var rewardV = toNum2(this.dataForm.reward);
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/signrewardcontinuouseveryday/signrewardcontinuouseveryday/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'dayName': this.dataForm.dayName ,
								'reward': rewardV,
								'dayNum': this.dataForm.dayNum,
								'vipId': this.dataForm.vipId ,

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
	function toNum2(floatNum) {
			return parseInt(Math.abs(floatNum) * Math.pow(10, 2) + 0.5, 10)
    }
</script>
