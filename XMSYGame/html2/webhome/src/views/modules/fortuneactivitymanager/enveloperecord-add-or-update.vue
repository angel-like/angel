<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="玩家id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="玩家id"></el-input>
    </el-form-item>
    <el-form-item label="玩家名称" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="玩家名称"></el-input>
    </el-form-item>
    <el-form-item label="开启时间" prop="openTime">
      <el-input v-model="dataForm.openTime" placeholder="开启时间"></el-input>
    </el-form-item>
    <el-form-item label="开启数量" prop="openNum">
      <el-input v-model="dataForm.openNum" placeholder="开启数量"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="修改时间"></el-input>
    </el-form-item>
    <el-form-item label="乐观锁版本号" prop="version">
      <el-input v-model="dataForm.version" placeholder="乐观锁版本号"></el-input>
    </el-form-item>
    <el-form-item label="删除状态（假删除）" prop="deleteStatus">
      <el-input v-model="dataForm.deleteStatus" placeholder="删除状态（假删除）"></el-input>
    </el-form-item>
    <el-form-item label="获得的金币：玩家一次性消耗红包个数获得的金币数" prop="receiveCoin">
      <el-input v-model="dataForm.receiveCoin" placeholder="获得的金币：玩家一次性消耗红包个数获得的金币数"></el-input>
    </el-form-item>
    <el-form-item label="开启前金币：玩家开启红包前自身携带的金币" prop="beforeOpenCoin">
      <el-input v-model="dataForm.beforeOpenCoin" placeholder="开启前金币：玩家开启红包前自身携带的金币"></el-input>
    </el-form-item>
    <el-form-item label="开启后金币：玩家开启红包后自身携带的金币" prop="afterOpenCoin">
      <el-input v-model="dataForm.afterOpenCoin" placeholder="开启后金币：玩家开启红包后自身携带的金币"></el-input>
    </el-form-item>
    <el-form-item label="是否开启 0-未开启  1-已开启" prop="status">
      <el-input v-model="dataForm.status" placeholder="是否开启 0-未开启  1-已开启"></el-input>
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
          userId: '',
          userAccount: '',
          openTime: '',
          openNum: '',
          createTime: '',
          updateTime: '',
          version: '',
          deleteStatus: '',
          receiveCoin: '',
          beforeOpenCoin: '',
          afterOpenCoin: '',
          status: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '玩家id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '玩家名称不能为空', trigger: 'blur' }
          ],
          openTime: [
            { required: true, message: '开启时间不能为空', trigger: 'blur' }
          ],
          openNum: [
            { required: true, message: '开启数量不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ],
          version: [
            { required: true, message: '乐观锁版本号不能为空', trigger: 'blur' }
          ],
          deleteStatus: [
            { required: true, message: '删除状态（假删除）不能为空', trigger: 'blur' }
          ],
          receiveCoin: [
            { required: true, message: '获得的金币：玩家一次性消耗红包个数获得的金币数不能为空', trigger: 'blur' }
          ],
          beforeOpenCoin: [
            { required: true, message: '开启前金币：玩家开启红包前自身携带的金币不能为空', trigger: 'blur' }
          ],
          afterOpenCoin: [
            { required: true, message: '开启后金币：玩家开启红包后自身携带的金币不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '是否开启 0-未开启  1-已开启不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/gameexperience/enveloperecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.enveloperecord.userId
                this.dataForm.userAccount = data.enveloperecord.userAccount
                this.dataForm.openTime = data.enveloperecord.openTime
                this.dataForm.openNum = data.enveloperecord.openNum
                this.dataForm.createTime = data.enveloperecord.createTime
                this.dataForm.updateTime = data.enveloperecord.updateTime
                this.dataForm.version = data.enveloperecord.version
                this.dataForm.deleteStatus = data.enveloperecord.deleteStatus
                this.dataForm.receiveCoin = data.enveloperecord.receiveCoin
                this.dataForm.beforeOpenCoin = data.enveloperecord.beforeOpenCoin
                this.dataForm.afterOpenCoin = data.enveloperecord.afterOpenCoin
                this.dataForm.status = data.enveloperecord.status
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
              url: this.$http.adornUrl(`/gameexperience/enveloperecord/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'userAccount': this.dataForm.userAccount,
                'openTime': this.dataForm.openTime,
                'openNum': this.dataForm.openNum,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime,
                'version': this.dataForm.version,
                'deleteStatus': this.dataForm.deleteStatus,
                'receiveCoin': this.dataForm.receiveCoin,
                'beforeOpenCoin': this.dataForm.beforeOpenCoin,
                'afterOpenCoin': this.dataForm.afterOpenCoin,
                'status': this.dataForm.status
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
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
