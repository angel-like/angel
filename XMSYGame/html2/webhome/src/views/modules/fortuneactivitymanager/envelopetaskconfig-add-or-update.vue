<template>
  <el-dialog
    :title="!dataForm.id ? '新增事件' : '修改事件'"
    :close-on-click-modal="false" append-to-body
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="100px">
      <!--<el-form-item label="事件" prop="eventCode">
        <el-input v-model="dataForm.eventCode" placeholder="事件"></el-input>
      </el-form-item>-->
      <el-form-item label="事件" prop="eventCode">
        <el-select v-model="dataForm.eventCode" placeholder="事件 " clearable>
          <el-option
            v-for="item in options"
            :key="item.code"
            :label="item.name"
            :value="item.code+','+item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事件参数值" prop="eventParam">
        <el-input style="width: 200px" v-model="dataForm.eventParam" placeholder="事件参数值"></el-input>
      </el-form-item>
      <el-form-item label="奖励数量" prop="rewards">
        <el-input-number :min="1" v-model="dataForm.rewards" placeholder="奖励数量"></el-input-number>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      //验证正整数
      var validateMin = (rule, value, callback) => {
        var res = /^\d+(\.\d{1,2})?$/;
        if (value === '') {
          callback();
        } else {
          if (!res.test(value)) {
            callback(new Error('请输入正确格式！'));
          } else {
            if (value < 0) {
              callback(new Error('最低金额不得小于0！'));
            }
            callback();
          }
        }
      };
      return {
        visible: false,
        dataForm: {
          event: {},
          id: 0,
          eventCode: '',
          eventName: '',
          activityId: '',
          eventParam: 0,
          rewards: 1
        },
        options: [],
        dataRule: {
          eventCode: [
            {required: true, message: '事件不能为空', trigger: 'blur'}
          ],
          /*eventName: [
            {required: true, message: '事件名称不能为空', trigger: 'blur'}
          ],
          activityId: [
            {required: true, message: '活动id不能为空', trigger: 'blur'}
          ],*/
          eventParam: [
            {required: true, message: '事件参数值不能为空', trigger: 'blur'},
            {validator: validateMin, trigger: 'blur'}
          ],
          rewards: [
            {required: true, message: '奖励数量不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init(id, activityId) {
        //为任务下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/` + "fortuneEvent"),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.options = data.data
          }
					this.dataForm.activityId = activityId
					this.dataForm.id = id || 0
					this.visible = true
					this.$nextTick(() => {
						this.$refs['dataForm'].resetFields()
						if (this.dataForm.id) {
							this.$http({
								url: this.$http.adornUrl(`/envelopetaskconfig/envelopetaskconfig/info/${this.dataForm.id}`),
								method: 'get',
								params: this.$http.adornParams()
							}).then(({data}) => {
								console.log(data)
								if (data && data.code === 200) {
									this.dataForm.eventCode = data.envelopeTaskConfig.eventCode+','+data.envelopeTaskConfig.eventName
									this.dataForm.activityId = data.envelopeTaskConfig.activityId
									this.dataForm.eventParam = data.envelopeTaskConfig.eventParam
									if(this.dataForm.eventCode.indexOf('coin')>-1){
										this.dataForm.eventParam =this.dataForm.eventParam /100
									}
									this.dataForm.rewards = data.envelopeTaskConfig.rewards
								}
							})
						}
					})
        });
				
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            var ls = this.dataForm.eventCode.split(',')
            this.dataForm.eventCode = ls[0]
            this.dataForm.eventName = ls[1]
            var pa=this.dataForm.eventParam
            if (this.dataForm.eventCode.indexOf('coin') > -1) {
              pa = this.dataForm.eventParam * 100
            }
            this.$http({
              url: this.$http.adornUrl(`/envelopetaskconfig/envelopetaskconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'eventCode': this.dataForm.eventCode,
                'eventName': this.dataForm.eventName,
                'activityId': this.dataForm.activityId,
                'eventParam': pa,
                'rewards': this.dataForm.rewards
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
