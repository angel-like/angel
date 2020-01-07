<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userid">
      <el-input v-model="dataForm.userid" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="任务id" prop="taskId">
      <el-input v-model="dataForm.taskId" placeholder="任务id"></el-input>
    </el-form-item>
    <el-form-item label="派奖任务标题" prop="taskTitle">
      <el-input v-model="dataForm.taskTitle" placeholder="派奖任务标题"></el-input>
    </el-form-item>
    <el-form-item label="派奖奖项id" prop="taskDetailId">
      <el-input v-model="dataForm.taskDetailId" placeholder="派奖奖项id"></el-input>
    </el-form-item>
    <el-form-item label="派奖明细标题" prop="detailTitle">
      <el-input v-model="dataForm.detailTitle" placeholder="派奖明细标题"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="派发时间" prop="dispatchTime">
      <el-input v-model="dataForm.dispatchTime" placeholder="派发时间"></el-input>
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
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          userid: '',
          taskId: '',
          taskTitle: '',
          taskDetailId: '',
          detailTitle: '',
          amount: '',
          dispatchTime: '',
        },
        dataRule: {
          userid: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          taskId: [
            { required: true, message: '任务id不能为空', trigger: 'blur' }
          ],
          taskTitle: [
            { required: true, message: '派奖任务标题不能为空', trigger: 'blur' }
          ],
          taskDetailId: [
            { required: true, message: '派奖奖项id不能为空', trigger: 'blur' }
          ],
          detailTitle: [
            { required: true, message: '派奖明细标题不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          dispatchTime: [
            { required: true, message: '派发时间不能为空', trigger: 'blur' }
          ],
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
              url: this.$http.adornUrl(`/pooldispatchdetaillist/pooldispatchdetaillist/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userid = data.pooldispatchdetaillist.userid
                this.dataForm.taskId = data.pooldispatchdetaillist.taskId
                this.dataForm.taskTitle = data.pooldispatchdetaillist.taskTitle
                this.dataForm.taskDetailId = data.pooldispatchdetaillist.taskDetailId
                this.dataForm.detailTitle = data.pooldispatchdetaillist.detailTitle
                this.dataForm.amount = data.pooldispatchdetaillist.amount
                this.dataForm.dispatchTime = data.pooldispatchdetaillist.dispatchTime
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
              url: this.$http.adornUrl(`/pooldispatchdetaillist/pooldispatchdetaillist/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userid': this.dataForm.userid ,

          'taskId': this.dataForm.taskId ,

          'taskTitle': this.dataForm.taskTitle ,

          'taskDetailId': this.dataForm.taskDetailId ,

          'detailTitle': this.dataForm.detailTitle ,

          'amount': this.dataForm.amount ,

          'dispatchTime': this.dataForm.dispatchTime ,

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
