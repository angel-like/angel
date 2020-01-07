<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="登陆ip" prop="ip">
      <el-input v-model="dataForm.ip" placeholder="登陆ip"></el-input>
    </el-form-item>
    <el-form-item label="机器码" prop="deviceCode">
      <el-input v-model="dataForm.deviceCode" placeholder="机器码"></el-input>
    </el-form-item>
    <el-form-item label="设备类型" prop="deviceType">
      <el-input v-model="dataForm.deviceType" placeholder="设备类型"></el-input>
    </el-form-item>
    <el-form-item label="地区" prop="region">
      <el-input v-model="dataForm.region" placeholder="地区"></el-input>
    </el-form-item>
    <el-form-item label="国家" prop="nation">
      <el-input v-model="dataForm.nation" placeholder="国家"></el-input>
    </el-form-item>
    <el-form-item label="地理位置" prop="ipAddress">
      <el-input v-model="dataForm.ipAddress" placeholder="地理位置"></el-input>
    </el-form-item>
    <el-form-item label="域名" prop="domain">
      <el-input v-model="dataForm.domain" placeholder="域名"></el-input>
    </el-form-item>
    <el-form-item label="版本号" prop="edition">
      <el-input v-model="dataForm.edition" placeholder="版本号"></el-input>
    </el-form-item>
    <el-form-item label="浏览器版本" prop="browser">
      <el-input v-model="dataForm.browser" placeholder="浏览器版本"></el-input>
    </el-form-item>
    <el-form-item label="登陆状态" prop="loginStatus">
      <el-input v-model="dataForm.loginStatus" placeholder="登陆状态"></el-input>
    </el-form-item>
    <el-form-item label="登陆时token" prop="token">
      <el-input v-model="dataForm.token" placeholder="登陆时token"></el-input>
    </el-form-item>
    <el-form-item label="大厅id" prop="hallId">
      <el-input v-model="dataForm.hallId" placeholder="大厅id"></el-input>
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
          ip: '',
          deviceCode: '',
          deviceType: '',
          region: '',
          nation: '',
          ipAddress: '',
          domain: '',
          edition: '',
          browser: '',
          loginStatus: '',
          token: '',
          hallId: '',
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          ip: [
            { required: true, message: '登陆ip不能为空', trigger: 'blur' }
          ],
          deviceCode: [
            { required: true, message: '机器码不能为空', trigger: 'blur' }
          ],
          deviceType: [
            { required: true, message: '设备类型不能为空', trigger: 'blur' }
          ],
          region: [
            { required: true, message: '地区不能为空', trigger: 'blur' }
          ],
          nation: [
            { required: true, message: '国家不能为空', trigger: 'blur' }
          ],
          ipAddress: [
            { required: true, message: '地理位置不能为空', trigger: 'blur' }
          ],
          domain: [
            { required: true, message: '域名不能为空', trigger: 'blur' }
          ],
          edition: [
            { required: true, message: '版本号不能为空', trigger: 'blur' }
          ],
          browser: [
            { required: true, message: '浏览器版本不能为空', trigger: 'blur' }
          ],
          loginStatus: [
            { required: true, message: '登陆状态不能为空', trigger: 'blur' }
          ],
          token: [
            { required: true, message: '登陆时token不能为空', trigger: 'blur' }
          ],
          hallId: [
            { required: true, message: '大厅id不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userlogin/userlogin/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.userId = data.userlogin.userId
                this.dataForm.ip = data.userlogin.ip
                this.dataForm.deviceCode = data.userlogin.deviceCode
                this.dataForm.deviceType = data.userlogin.deviceType
                this.dataForm.region = data.userlogin.region
                this.dataForm.nation = data.userlogin.nation
                this.dataForm.ipAddress = data.userlogin.ipAddress
                this.dataForm.domain = data.userlogin.domain
                this.dataForm.edition = data.userlogin.edition
                this.dataForm.browser = data.userlogin.browser
                this.dataForm.loginStatus = data.userlogin.loginStatus
                this.dataForm.token = data.userlogin.token
                this.dataForm.hallId = data.userlogin.hallId
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
              url: this.$http.adornUrl(`/userlogin/userlogin/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,

          'ip': this.dataForm.ip ,

          'deviceCode': this.dataForm.deviceCode ,

          'deviceType': this.dataForm.deviceType ,

          'region': this.dataForm.region ,

          'nation': this.dataForm.nation ,

          'ipAddress': this.dataForm.ipAddress ,

          'domain': this.dataForm.domain ,

          'edition': this.dataForm.edition ,

          'browser': this.dataForm.browser ,

          'loginStatus': this.dataForm.loginStatus ,

          'token': this.dataForm.token ,

          'hallId': this.dataForm.hallId ,

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
