<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="会员的活动类型" placement="top-start">
				<el-form-item label="奖励类型" prop="type">
						<el-select v-model="dataForm.type" placeholder="奖励类型 " clearable>
							<el-option
								v-for="item in options"
								:key="item.code"
								:label="item.name"
								:value="item.code">
							</el-option>
						</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="满足奖励的条件" placement="top-start">
				<el-form-item label="条件" prop="num">
				  <el-input v-model="dataForm.num" placeholder="条件"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="每周最多可以发放的次数" placement="top-start">
				<el-form-item label="每周期最多次数" prop="maxNum">
				  <el-input v-model="dataForm.maxNum" placeholder="每周期最多次数"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="发送的模式,是用户自己领取还是后台统一发放" placement="top-start">
				<el-form-item label="发送模式" prop="sendType">
					<el-radio-group v-model="dataForm.sendType">
						<el-radio :label="0">后台发送</el-radio>
						<el-radio :label="1">客户领取</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="站内信的邮件模板" placement="top-start">
				<el-form-item label="模板选择" prop="templateId">
					<el-select v-model="dataForm.templateId" clearable  placeholder="模板选择">
						<el-option
							v-for="item in optionsType"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="是指多少天为一个周期" placement="top-start">
				<el-form-item label="周期（天）" prop="cycle">
				  <el-input v-model="dataForm.cycle" placeholder="周期（天）" ></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="该奖励类型对应的奖励" placement="top-start">
				<el-form-item label="奖励金币" prop="coin">
				  <el-input v-model="dataForm.coin" placeholder="奖励金币"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="符合及以上的vip等级" placement="top-start">
				<el-form-item label="VIP等级" prop="vipId">
					<el-select v-model="dataForm.vipId" clearable  placeholder="VIP等级">
						<el-option
							v-for="item in optionsVip"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
				<div style="color: red;font-size: 16px;">注：会员满足相应的等级及以上就送奖励</div>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="该奖励类型是否可用" placement="top-start">
				<el-form-item label="状态" prop="enable">
					<el-radio-group v-model="dataForm.enable">
						<el-radio :label="true">启用</el-radio>
						<el-radio :label="false">禁用</el-radio>
					</el-radio-group>
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
				options: [],
				optionsType: [],
				optionsVip:[],
				rate:100,
        dataForm: {
          id: 0,
          type: '',
          num: '',
					maxNum: '',
					sendType: 0,
					cycle: 1,
          coin: '',
          enable: true,
					templateId:'',
					vipId: '',
        },
        dataRule: {
					templateId: [
					  { required: true, message: '模板id不能为空', trigger: 'blur' }
					],
          type: [
            { required: true, message: '奖励类型不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '条件次数|人数不能为空', trigger: 'blur' },
            { validator: validateInteger,message: '条件次数|人数格式不正确', trigger: 'blur' }
          ],
          coin: [
            { required: true, message: '奖励的金币值不能为空', trigger: 'blur' },
						{ validator: validateInteger,message: '奖励的金币值格式不正确', trigger: 'blur' }
          ],
          maxNum: [

            { validator: validateInteger,message: '每周期最多次数格式不正确', trigger: 'blur' }
          ],
          cycle: [

            { validator: validateInteger,message: '周期格式不正确', trigger: 'blur' }
          ],
					vipId: [
					  { required: true, message: 'vipId不能为空', trigger: 'blur' }
					],
          enable: [
            { required: true, message: '状态（1.启用，0：禁用）不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				//vip等级选择
				this.$http({
					url: this.$http.adornUrl(`/uservip/uservip/getVIP`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.optionsVip = data.userVipEntity
					}
				});
				//为用户活动奖励下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/`+"UserActivityAward"),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.data
					}
				});
				//模板选择
				this.$http({
					url: this.$http.adornUrl(`/sysmessagemodel/sysmessagemodel/getTemplate`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.optionsType = data.templateList
					}
				});
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/giftcoinconfig/giftcoinconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.type = data.giftcoinconfig.type.toString()
                this.dataForm.num = data.giftcoinconfig.num
								this.dataForm.maxNum = data.giftcoinconfig.maxNum
								this.dataForm.sendType = data.giftcoinconfig.sendType
							  this.dataForm.cycle = data.giftcoinconfig.cycle
                this.dataForm.coin = data.giftcoinconfig.coin/this.rate
                this.dataForm.enable = data.giftcoinconfig.enable
								this.dataForm.templateId = data.giftcoinconfig.templateId
								this.dataForm.vipId = data.giftcoinconfig.vipId
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
              url: this.$http.adornUrl(`/giftcoinconfig/giftcoinconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'type': this.dataForm.type,
								'num': this.dataForm.num,
								'maxNum': this.dataForm.maxNum,
								'sendType': this.dataForm.sendType,
								'cycle': this.dataForm.cycle,
								'coin': this.dataForm.coin*this.rate,
								'enable': this.dataForm.enable,
								'templateId': this.dataForm.templateId ,
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
</script>
