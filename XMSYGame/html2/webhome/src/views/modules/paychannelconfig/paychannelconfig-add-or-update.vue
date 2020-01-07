<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">

    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-tooltip class="item" effect="dark" content="选择哪家公司进行支付" placement="top-start">
				<el-form-item label="支付公司" prop="payId" v-if="dataForm.id">
					<el-select  v-model="dataForm.payId" placeholder="请选择支付公司">
						<el-option
							v-for="item in payList"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="支付公司" prop="payId" v-else>
					<el-select  v-model="dataForm.payId" placeholder="请选择支付公司">
						<el-option
							v-for="item in payList"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>

      <el-tooltip class="item" effect="dark" content="选择支付渠道(例如支付宝)" placement="top-start">
      	<el-form-item label="支付渠道" prop="channelId" v-if="dataForm.id">
      		<el-select  v-model="dataForm.channelId" disabled="true" placeholder="请选择支付渠道">
      			<el-option
      				v-for="item in channelList"
      				:key="item.id"
      				:label="item.name"
      				:value="item.id">
      			</el-option>
      		</el-select>
      	</el-form-item>
      	<el-form-item label="支付渠道" prop="channelId" v-else>
      		<el-select  v-model="dataForm.channelId" placeholder="请选择支付渠道">
      			<el-option
      				v-for="item in channelList"
      				:key="item.id"
      				:label="item.name"
      				:value="item.id">
      			</el-option>
      		</el-select>
      	</el-form-item>
      </el-tooltip>

     <el-tooltip class="item" effect="dark" content="限制支付的最低金额" placement="top-start">
      	<el-form-item label="最低支付金额" prop="lowLimit">
      		<el-input v-model="dataForm.lowLimit" placeholder="限制最低支付金额"></el-input>
      	</el-form-item>
      </el-tooltip>

    <el-tooltip class="item" effect="dark" content="限制支付的最高金额" placement="top-start">
     	<el-form-item label="最高支付金额" prop="highLimit">
     	  <el-input v-model="dataForm.highLimit" placeholder="限制最高支付金额"></el-input>
     	</el-form-item>
     </el-tooltip>

     <el-tooltip class="item" effect="dark" content="是否禁用" placement="top-start">
      	<el-form-item label="状态" size="mini" prop="enable">
      		<el-radio-group v-model="dataForm.enable">
      			<el-radio :label="0">禁用</el-radio>
      			<el-radio :label="1">正常</el-radio>
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
	import { isNumber } from '@/utils/validate'
  export default {
    data () {
			var validateHighLimit = (rule, value, callback) => {
				var reg = /^\d+$/;
				if (!reg.test(value)) {
					callback(new Error('必须是整数'))
				} else {
					callback()
				}
			}
			var validateLowLimit = (rule, value, callback) => {
 				var reg = /^\d+$/;
				if (!reg.test(value)) {
					callback(new Error('必须是整数'))
				} else {
					callback()
				}

			}
      return {
        visible: false,
				payList:[],
				channelList:[],
        dataForm: {
          id: 0,
          payId: '',
          enable: 1,
          channelId: '',
          highLimit: '',
          lowLimit: '',
        },
        dataRule: {
          payId: [
            { required: true, message: '支付公司不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          channelId: [
            { required: true, message: '支付渠道不能为空', trigger: 'blur' }
          ],
          highLimit: [
            { validator: validateHighLimit,  trigger: 'blur'}
          ],
          lowLimit: [
            { validator: validateLowLimit,  trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
				this.$http({
					url: this.$http.adornUrl(`/payconfig/payconfig/Payconfigs?enable=true`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.payList = data.list
					}
				})
				this.$http({
					url: this.$http.adornUrl(`/rechargechannel/rechargechannel/Rechargechannels?enable=true`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.channelList = data.list
					}
				})
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/paychannelconfig/paychannelconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.payId = data.paychannelconfig.payId
								if(data.paychannelconfig.enable){
									this.dataForm.enable=1
								}else{
									this.dataForm.enable=0
								}
                this.dataForm.channelId = data.paychannelconfig.channelId
                this.dataForm.highLimit = data.paychannelconfig.highLimit
                this.dataForm.lowLimit = data.paychannelconfig.lowLimit
              }
            })
          }else{
						this.dataForm.enable=1
					}
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/paychannelconfig/paychannelconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'payId': this.dataForm.payId ,

          'enable': this.dataForm.enable ,

          'channelId': this.dataForm.channelId ,

          'highLimit': this.dataForm.highLimit ,

          'lowLimit': this.dataForm.lowLimit ,

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
