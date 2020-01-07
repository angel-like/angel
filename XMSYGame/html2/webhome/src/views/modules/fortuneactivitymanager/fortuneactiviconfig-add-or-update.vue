<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="活动名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="活动名称"></el-input>
      </el-form-item>
      <el-form-item label="活动详情" prop="detail">
        <el-input type="textarea" :rows="3" v-model="dataForm.detail" placeholder="活动详情"></el-input>
      </el-form-item>
      <!-- maxlength="500"-->
      <el-tooltip class="item" effect="dark" content="开启时间" placement="top-start">
        <el-form-item label="开启时间" prop="startTime">
          <el-date-picker v-model="dataForm.startTime" type="datetime" :editable="false"
                          format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="开启时间" default-time="00:00:00">
             
          </el-date-picker>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="结束时间" placement="top-start">
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="dataForm.endTime" type="datetime" :editable="false"
                          format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="结束时间" default-time="00:00:00">
             
          </el-date-picker>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="奖励上限 " prop="maxnumReward">
        <el-input v-model="dataForm.maxnumReward" placeholder="奖励上限 "></el-input>
      </el-form-item>
      <el-form-item label="奖励下限" prop="minnumReward">
        <el-input v-model="dataForm.minnumReward" placeholder="奖励下限"></el-input>
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
      var checkFailureTime = (rule, value, callback) => {
        if (value && this.dataForm.startTime) {
          if (value <= this.dataForm.startTime) {
            callback(new Error('结束时间不能早于开始时间'));
          } else {
            this.$nextTick(() => {
              this.$http({
                url: this.$http.adornUrl(`/fortuneactiviconfig/fortuneactiviconfig/checkEndTime/${this.dataForm.startTime}/${value}/${this.dataForm.id}`),
                method: 'get',
                params: this.$http.adornParams()
              }).then(({data}) => {
                if (data && data.code === 200) {
                  if (!data.after) {
                    callback(new Error('该时间区间已存在活动!请重新选择时间'))
                  }
                  callback()
                }
              })
            })
          }
        } else {
          this.$nextTick(() => {
            this.$http({
              url: this.$http.adornUrl(`/fortuneactiviconfig/fortuneactiviconfig/checkTime/${value}/${this.dataForm.id}/1`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                if (!data.after) {
                  callback(new Error('该时间区间已存在活动!请重新选择时间'))
                }
                callback()
              }
            })
          })
        }
      };
      var checkStartTime = (rule, value, callback) => {

        if (value != null) {
          if (value < this.defaultStartTime()) {
            if (this.startT != value) {
              value = this.defaultStartTime()
              this.dataForm.startTime = this.defaultStartTime()
            }

          }
          this.$nextTick(() => {
            this.$http({
              url: this.$http.adornUrl(`/fortuneactiviconfig/fortuneactiviconfig/checkTime/${value}/${this.dataForm.id}/0`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                if (!data.after) {
                  callback(new Error('该时间区间已存在活动!请重新选择时间'))
                }

                callback()
              }
            })
          })
        }
      };
      //验证正整数
      var validateMin = (rule, value, callback) => {
        var res = /^\d+(\.\d{1,2})?$/;
        if (value === '') {
          callback();
        } else {
          if (!res.test(value)) {
            callback(new Error('请输入正确格式！'));
          } else {
            if (value < 0.01) {
              callback(new Error('最低金额不得小于0.01！'));
            }
            callback();
          }
        }
      };
      //验证正整数
      var validateInteger = (rule, value, callback) => {
        var res = /^\d+(\.\d{1,2})?$/;
        if (value === '') {
          callback();
        } else {
          if (!res.test(value)) {
            callback(new Error('请输入正确格式！'));
          } else {
            callback();
          }
        }
      };
      return {
        visible: false,
        time: new Date(),
        startT: '',
        dataForm: {
          id: 0,
          name: '',
          detail: '',
          startTime: '',
          endTime: '',
          maxnumReward: '',
          minnumReward: ''
        },
        dataRule: {
          name: [
            {required: true, message: '活动名称不能为空', trigger: 'blur'}
          ],
          detail: [
            {required: true, message: '活动详情不能为空', trigger: 'blur'}
          ],
          startTime: [
            {required: true, message: '开启时间不能为空', trigger: 'blur'},
            {validator: checkStartTime, trigger: 'blur'}
          ],
          endTime: [
            {required: true, message: '结束时间不能为空', trigger: 'blur'},
            {validator: checkFailureTime, trigger: 'blur'}
          ],
          maxnumReward: [
            {required: true, message: '奖励上限不能为空', trigger: 'blur'},
            {validator: validateInteger, trigger: 'blur'}
          ],
          minnumReward: [
            {required: true, message: '奖励下限不能为空', trigger: 'blur'},
            {validator: validateMin, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      checkTime(value) {
        let aft = true
        if (value != null) {
          this.$nextTick(() => {
            this.$http({
              url: this.$http.adornUrl(`/fortuneactiviconfig/fortuneactiviconfig/checkTime/${value}/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                aft = data.after
                return aft
              } else {
                aft = false
                return aft
              }
            })
          })
          return aft
        }

      },
      defaultEffectTime() {
        let date = new Date()
        let y = date.getFullYear()
        let m = date.getMonth() + 1
        let d = date.getDate()
        let h = date.getHours()
        let t = date.getMinutes()
        let s = date.getSeconds()

        if (m < 10) {
          m = '0' + m
        }
        if (d < 10) {
          d = '0' + d
        }
        if (h < 10) {
          h = '0' + h
        }
        if (t < 10) {
          t = '0' + t
        }
        if (d < 10) {
          s = '0' + s
        }
        let time = y + '-' + m + '-' + d
        return time + " " + h + ":" + t + ":" + s;
      },
      defaultStartTime() {
        let date = new Date()
        let y = date.getFullYear()
        let m = date.getMonth() + 1
        let d = date.getDate()
        let h = date.getHours()
        let t = date.getMinutes()
        let s = date.getSeconds()

        if (m < 10) {
          m = '0' + m
        }
        if (d < 10) {
          d = '0' + d
        }
        if (h < 10) {
          h = '0' + h
        }
        if (t < 10) {
          t = '0' + t
        }
        if (d < 10) {
          s = '0' + s
        }
        let time = y + '-' + m + '-' + d
        // return time + " 00:00:00";
        return time + " " + h + ":" + t + ":" + s;
      },
      defaultFailureTime() {
        let date = new Date()
        let y = date.getFullYear()
        let m = date.getMonth() + 2
        let d = date.getDate()
        if (m < 10) {
          m = '0' + m
        }
        if (d < 10) {
          d = '0' + d
        }

        let time = y + '-' + m + '-' + d
        return time + " 00:00:00";
      },
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/fortuneactiviconfig/fortuneactiviconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.startT = data.fortuneActiviConfig.startTime
                this.dataForm = data.fortuneActiviConfig
                this.dataForm.maxnumReward = data.fortuneActiviConfig.maxnumReward / 100
                this.dataForm.minnumReward = data.fortuneActiviConfig.minnumReward / 100

              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {

            /*if (this.dataForm.startTime < this.defaultStartTime()) {

              this.dataForm.startTime = this.defaultStartTime()
            }*/
            this.$http({
              url: this.$http.adornUrl(`/fortuneactiviconfig/fortuneactiviconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'detail': this.dataForm.detail,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'maxnumReward': this.dataForm.maxnumReward * 100,
                'minnumReward': this.dataForm.minnumReward * 100
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
