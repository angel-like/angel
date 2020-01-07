<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="账号名称" prop="account">
      <el-input v-model="dataForm.account" placeholder="账号名称"></el-input>
    </el-form-item>
    <el-form-item label="用户姓名" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户姓名"></el-input>
    </el-form-item>
    <el-form-item label="身份证号码" prop="userIdentity">
      <el-input v-model="dataForm.userIdentity" placeholder="身份证号码"></el-input>
    </el-form-item>
    <el-form-item label="电话" prop="userPhone">
      <el-input v-model="dataForm.userPhone" placeholder="电话"></el-input>
    </el-form-item>
    <el-form-item label="电子邮箱" prop="userEmail">
      <el-input v-model="dataForm.userEmail" placeholder="电子邮箱"></el-input>
    </el-form-item>
    <el-form-item label="邮编" prop="postCode">
      <el-input v-model="dataForm.postCode" placeholder="邮编"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="userAddress">
      <el-input v-model="dataForm.userAddress" placeholder="地址"></el-input>
    </el-form-item>
    <el-form-item label="QQ" prop="userQq">
      <el-input v-model="dataForm.userQq" placeholder="QQ"></el-input>
    </el-form-item>
    <el-form-item label="生日" prop="userBirthday">
      <el-input v-model="dataForm.userBirthday" placeholder="生日"></el-input>
    </el-form-item>
    <el-form-item label="银行名称" prop="bankName">
      <el-input v-model="dataForm.bankName" placeholder="银行名称"></el-input>
    </el-form-item>
    <el-form-item label="银行开户地址" prop="bankAddress">
      <el-input v-model="dataForm.bankAddress" placeholder="银行开户地址"></el-input>
    </el-form-item>
    <el-form-item label="账号类型（试玩账号，普通会员，vip）" prop="userType">
      <el-input v-model="dataForm.userType" placeholder="账号类型（试玩账号，普通会员，vip）"></el-input>
    </el-form-item>
    <el-form-item label="token验证Id" prop="token">
      <el-input v-model="dataForm.token" placeholder="token验证Id"></el-input>
    </el-form-item>
    <el-form-item label="用户注册IP" prop="registerIp">
      <el-input v-model="dataForm.registerIp" placeholder="用户注册IP"></el-input>
    </el-form-item>
    <el-form-item label="注册设备码" prop="registerDeviceCode">
      <el-input v-model="dataForm.registerDeviceCode" placeholder="注册设备码"></el-input>
    </el-form-item>
    <el-form-item label="第三方ID" prop="unionId">
      <el-input v-model="dataForm.unionId" placeholder="第三方ID"></el-input>
    </el-form-item>
    <el-form-item label="上级ID" prop="superiorsId">
      <el-input v-model="dataForm.superiorsId" placeholder="上级ID"></el-input>
    </el-form-item>
    <el-form-item label="账号状态（停押，停用，冻结）" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="账号状态（停押，停用，冻结）"></el-input>
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
          account: '',          userName: '',          userIdentity: '',          userPhone: '',          userEmail: '',          postCode: '',          userAddress: '',          userQq: '',          userBirthday: '',          bankName: '',          bankAddress: '',          userType: '',          token: '',          registerIp: ''    ,      registerDeviceCode: ''      ,    unionId: ''     ,     superiorsId: ''     ,     enable: ''        },
        dataRule: {
          account: [
            { required: true, message: '账号名称不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '用户姓名不能为空', trigger: 'blur' }
          ],
          userIdentity: [
            { required: true, message: '身份证号码不能为空', trigger: 'blur' }
          ],
          userPhone: [
            { required: true, message: '电话不能为空', trigger: 'blur' }
          ],
          userEmail: [
            { required: true, message: '电子邮箱不能为空', trigger: 'blur' }
          ],
          postCode: [
            { required: true, message: '邮编不能为空', trigger: 'blur' }
          ],
          userAddress: [
            { required: true, message: '地址不能为空', trigger: 'blur' }
          ],
          userQq: [
            { required: true, message: 'QQ不能为空', trigger: 'blur' }
          ],
          userBirthday: [
            { required: true, message: '生日不能为空', trigger: 'blur' }
          ],
          bankName: [
            { required: true, message: '银行名称不能为空', trigger: 'blur' }
          ],
          bankAddress: [
            { required: true, message: '银行开户地址不能为空', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '账号类型（试玩账号，普通会员，vip）不能为空', trigger: 'blur' }
          ],
          token: [
            { required: true, message: 'token验证Id不能为空', trigger: 'blur' }
          ],
          registerIp: [
            { required: true, message: '用户注册IP不能为空', trigger: 'blur' }
          ],
          registerDeviceCode: [
            { required: true, message: '注册设备码不能为空', trigger: 'blur' }
          ],
          unionId: [
            { required: true, message: '第三方ID不能为空', trigger: 'blur' }
          ],
          superiorsId: [
            { required: true, message: '上级ID不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '账号状态（停押，停用，冻结）不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/user/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.account = data.user.account
                this.dataForm.userName = data.user.userName
                this.dataForm.userIdentity = data.user.userIdentity
                this.dataForm.userPhone = data.user.userPhone
                this.dataForm.userEmail = data.user.userEmail
                this.dataForm.postCode = data.user.postCode
                this.dataForm.userAddress = data.user.userAddress
                this.dataForm.userQq = data.user.userQq
                this.dataForm.userBirthday = data.user.userBirthday
                this.dataForm.bankName = data.user.bankName
                this.dataForm.bankAddress = data.user.bankAddress
                this.dataForm.userType = data.user.userType
                this.dataForm.token = data.user.token
                this.dataForm.registerIp = data.user.registerIp
                this.dataForm.registerDeviceCode = data.user.registerDeviceCode
                this.dataForm.unionId = data.user.unionId
                this.dataForm.superiorsId = data.user.superiorsId
                this.dataForm.enable = data.user.enable
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
              url: this.$http.adornUrl(`/user/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'account': this.dataForm.account ,
          'userName': this.dataForm.userName ,
          'userIdentity': this.dataForm.userIdentity ,
          'userPhone': this.dataForm.userPhone ,
          'userEmail': this.dataForm.userEmail ,
          'postCode': this.dataForm.postCode ,
          'userAddress': this.dataForm.userAddress ,
          'userQq': this.dataForm.userQq ,
          'userBirthday': this.dataForm.userBirthday ,
          'bankName': this.dataForm.bankName ,
          'bankAddress': this.dataForm.bankAddress ,
          'userType': this.dataForm.userType ,
          'token': this.dataForm.token ,
          'registerIp': this.dataForm.registerIp ,
          'registerDeviceCode': this.dataForm.registerDeviceCode ,
          'unionId': this.dataForm.unionId ,
          'superiorsId': this.dataForm.superiorsId ,
          'enable': this.dataForm.enable 
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
