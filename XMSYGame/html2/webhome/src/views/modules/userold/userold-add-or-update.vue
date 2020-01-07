<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="账号名称" prop="account">
      <el-input v-model="dataForm.account" placeholder="账号名称"></el-input>
    </el-form-item>
    <el-form-item label="头像" prop="portrait">
      <el-input v-model="dataForm.portrait" placeholder="头像"></el-input>
    </el-form-item>
    <el-form-item label="头像框id" prop="headframeId">
      <el-input v-model="dataForm.headframeId" placeholder="头像框id"></el-input>
    </el-form-item>
    <el-form-item label="性别(0:女，1：男)" prop="sex">
      <el-input v-model="dataForm.sex" placeholder="性别(0:女，1：男)"></el-input>
    </el-form-item>
    <el-form-item label="账号类型（试玩账号，普通会员，vip）" prop="userType">
      <el-input v-model="dataForm.userType" placeholder="账号类型（试玩账号，普通会员，vip）"></el-input>
    </el-form-item>
    <el-form-item label="token验证Id" prop="token">
      <el-input v-model="dataForm.token" placeholder="token验证Id"></el-input>
    </el-form-item>
    <el-form-item label="第三方平台" prop="unionType">
      <el-input v-model="dataForm.unionType" placeholder="第三方平台"></el-input>
    </el-form-item>
    <el-form-item label="第三方交互ID" prop="openId">
      <el-input v-model="dataForm.openId" placeholder="第三方交互ID"></el-input>
    </el-form-item>
    <el-form-item label="第三方ID" prop="unionId">
      <el-input v-model="dataForm.unionId" placeholder="第三方ID"></el-input>
    </el-form-item>
    <el-form-item label="上级ID" prop="superiorsId">
      <el-input v-model="dataForm.superiorsId" placeholder="上级ID"></el-input>
    </el-form-item>
    <el-form-item label="禁用（正常）" prop="forbiddenEnable">
      <el-input v-model="dataForm.forbiddenEnable" placeholder="禁用（正常）"></el-input>
    </el-form-item>
    <el-form-item label="停押（正常）" prop="nobetEnable">
      <el-input v-model="dataForm.nobetEnable" placeholder="停押（正常）"></el-input>
    </el-form-item>
    <el-form-item label="冻结(正常)" prop="frozenEnable">
      <el-input v-model="dataForm.frozenEnable" placeholder="冻结(正常)"></el-input>
    </el-form-item>
    <el-form-item label="是否异常" prop="abnormalEnable">
      <el-input v-model="dataForm.abnormalEnable" placeholder="是否异常"></el-input>
    </el-form-item>
    <el-form-item label="层级id" prop="hierarchyId">
      <el-input v-model="dataForm.hierarchyId" placeholder="层级id"></el-input>
    </el-form-item>
    <el-form-item label="风控层级id" prop="riskHierarchyId">
      <el-input v-model="dataForm.riskHierarchyId" placeholder="风控层级id"></el-input>
    </el-form-item>
    <el-form-item label="VIP等级id" prop="vipId">
      <el-input v-model="dataForm.vipId" placeholder="VIP等级id"></el-input>
    </el-form-item>
    <el-form-item label="用户金币" prop="coin">
      <el-input v-model="dataForm.coin" placeholder="用户金币"></el-input>
    </el-form-item>
    <el-form-item label="房卡" prop="roomCard">
      <el-input v-model="dataForm.roomCard" placeholder="房卡"></el-input>
    </el-form-item>
    <el-form-item label="佣金" prop="commission">
      <el-input v-model="dataForm.commission" placeholder="佣金"></el-input>
    </el-form-item>
    <el-form-item label="金钱" prop="money">
      <el-input v-model="dataForm.money" placeholder="金钱"></el-input>
    </el-form-item>
    <el-form-item label="注册ip" prop="registerIp">
      <el-input v-model="dataForm.registerIp" placeholder="注册ip"></el-input>
    </el-form-item>
    <el-form-item label="注册ip地址" prop="registerIpAddress">
      <el-input v-model="dataForm.registerIpAddress" placeholder="注册ip地址"></el-input>
    </el-form-item>
    <el-form-item label="注册机器码" prop="registerDeviceCode">
      <el-input v-model="dataForm.registerDeviceCode" placeholder="注册机器码"></el-input>
    </el-form-item>
    <el-form-item label="真实姓名" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="真实姓名"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="状态（1：是代理。0：禁用代理）" prop="agentEnable">
      <el-input v-model="dataForm.agentEnable" placeholder="状态（1：是代理。0：禁用代理）"></el-input>
    </el-form-item>
    <el-form-item label="是否首冲过" prop="firstRecharge">
      <el-input v-model="dataForm.firstRecharge" placeholder="是否首冲过"></el-input>
    </el-form-item>
    <el-form-item label="在玩游戏信息id" prop="gameInfoId">
      <el-input v-model="dataForm.gameInfoId" placeholder="在玩游戏信息id"></el-input>
    </el-form-item>
    <el-form-item label="游戏服务id" prop="gameServerId">
      <el-input v-model="dataForm.gameServerId" placeholder="游戏服务id"></el-input>
    </el-form-item>
    <el-form-item label="0:检索风控  1：不检索风控" prop="noScan">
      <el-input v-model="dataForm.noScan" placeholder="0:检索风控  1：不检索风控"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="手机号"></el-input>
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
          account: '',
          portrait: '',
          headframeId: '',
          sex: '',
          userType: '',
          token: '',
          unionType: '',
          openId: '',
          unionId: '',
          superiorsId: '',
          forbiddenEnable: '',
          nobetEnable: '',
          frozenEnable: '',
          abnormalEnable: '',
          hierarchyId: '',
          riskHierarchyId: '',
          vipId: '',
          coin: '',
          roomCard: '',
          commission: '',
          money: '',
          registerIp: '',
          registerIpAddress: '',
          registerDeviceCode: '',
          userName: '',
          remark: '',
          agentEnable: '',
          firstRecharge: '',
          gameInfoId: '',
          gameServerId: '',
          noScan: '',
          phone: '',
        },
        dataRule: {
          account: [
            { required: true, message: '账号名称不能为空', trigger: 'blur' }
          ],
          portrait: [
            { required: true, message: '头像不能为空', trigger: 'blur' }
          ],
          headframeId: [
            { required: true, message: '头像框id不能为空', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '性别(0:女，1：男)不能为空', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '账号类型（试玩账号，普通会员，vip）不能为空', trigger: 'blur' }
          ],
          token: [
            { required: true, message: 'token验证Id不能为空', trigger: 'blur' }
          ],
          unionType: [
            { required: true, message: '第三方平台不能为空', trigger: 'blur' }
          ],
          openId: [
            { required: true, message: '第三方交互ID不能为空', trigger: 'blur' }
          ],
          unionId: [
            { required: true, message: '第三方ID不能为空', trigger: 'blur' }
          ],
          superiorsId: [
            { required: true, message: '上级ID不能为空', trigger: 'blur' }
          ],
          forbiddenEnable: [
            { required: true, message: '禁用（正常）不能为空', trigger: 'blur' }
          ],
          nobetEnable: [
            { required: true, message: '停押（正常）不能为空', trigger: 'blur' }
          ],
          frozenEnable: [
            { required: true, message: '冻结(正常)不能为空', trigger: 'blur' }
          ],
          abnormalEnable: [
            { required: true, message: '是否异常不能为空', trigger: 'blur' }
          ],
          hierarchyId: [
            { required: true, message: '层级id不能为空', trigger: 'blur' }
          ],
          riskHierarchyId: [
            { required: true, message: '风控层级id不能为空', trigger: 'blur' }
          ],
          vipId: [
            { required: true, message: 'VIP等级id不能为空', trigger: 'blur' }
          ],
          coin: [
            { required: true, message: '用户金币不能为空', trigger: 'blur' }
          ],
          roomCard: [
            { required: true, message: '房卡不能为空', trigger: 'blur' }
          ],
          commission: [
            { required: true, message: '佣金不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '金钱不能为空', trigger: 'blur' }
          ],
          registerIp: [
            { required: true, message: '注册ip不能为空', trigger: 'blur' }
          ],
          registerIpAddress: [
            { required: true, message: '注册ip地址不能为空', trigger: 'blur' }
          ],
          registerDeviceCode: [
            { required: true, message: '注册机器码不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '真实姓名不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          agentEnable: [
            { required: true, message: '状态（1：是代理。0：禁用代理）不能为空', trigger: 'blur' }
          ],
          firstRecharge: [
            { required: true, message: '是否首冲过不能为空', trigger: 'blur' }
          ],
          gameInfoId: [
            { required: true, message: '在玩游戏信息id不能为空', trigger: 'blur' }
          ],
          gameServerId: [
            { required: true, message: '游戏服务id不能为空', trigger: 'blur' }
          ],
          noScan: [
            { required: true, message: '0:检索风控  1：不检索风控不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/userold/userold/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.account = data.userold.account
                this.dataForm.portrait = data.userold.portrait
                this.dataForm.headframeId = data.userold.headframeId
                this.dataForm.sex = data.userold.sex
                this.dataForm.userType = data.userold.userType
                this.dataForm.token = data.userold.token
                this.dataForm.unionType = data.userold.unionType
                this.dataForm.openId = data.userold.openId
                this.dataForm.unionId = data.userold.unionId
                this.dataForm.superiorsId = data.userold.superiorsId
                this.dataForm.forbiddenEnable = data.userold.forbiddenEnable
                this.dataForm.nobetEnable = data.userold.nobetEnable
                this.dataForm.frozenEnable = data.userold.frozenEnable
                this.dataForm.abnormalEnable = data.userold.abnormalEnable
                this.dataForm.hierarchyId = data.userold.hierarchyId
                this.dataForm.riskHierarchyId = data.userold.riskHierarchyId
                this.dataForm.vipId = data.userold.vipId
                this.dataForm.coin = data.userold.coin
                this.dataForm.roomCard = data.userold.roomCard
                this.dataForm.commission = data.userold.commission
                this.dataForm.money = data.userold.money
                this.dataForm.registerIp = data.userold.registerIp
                this.dataForm.registerIpAddress = data.userold.registerIpAddress
                this.dataForm.registerDeviceCode = data.userold.registerDeviceCode
                this.dataForm.userName = data.userold.userName
                this.dataForm.remark = data.userold.remark
                this.dataForm.agentEnable = data.userold.agentEnable
                this.dataForm.firstRecharge = data.userold.firstRecharge
                this.dataForm.gameInfoId = data.userold.gameInfoId
                this.dataForm.gameServerId = data.userold.gameServerId
                this.dataForm.noScan = data.userold.noScan
                this.dataForm.phone = data.userold.phone
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
              url: this.$http.adornUrl(`/userold/userold/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'account': this.dataForm.account ,

          'portrait': this.dataForm.portrait ,

          'headframeId': this.dataForm.headframeId ,

          'sex': this.dataForm.sex ,

          'userType': this.dataForm.userType ,

          'token': this.dataForm.token ,

          'unionType': this.dataForm.unionType ,

          'openId': this.dataForm.openId ,

          'unionId': this.dataForm.unionId ,

          'superiorsId': this.dataForm.superiorsId ,

          'forbiddenEnable': this.dataForm.forbiddenEnable ,

          'nobetEnable': this.dataForm.nobetEnable ,

          'frozenEnable': this.dataForm.frozenEnable ,

          'abnormalEnable': this.dataForm.abnormalEnable ,

          'hierarchyId': this.dataForm.hierarchyId ,

          'riskHierarchyId': this.dataForm.riskHierarchyId ,

          'vipId': this.dataForm.vipId ,

          'coin': this.dataForm.coin ,

          'roomCard': this.dataForm.roomCard ,

          'commission': this.dataForm.commission ,

          'money': this.dataForm.money ,

          'registerIp': this.dataForm.registerIp ,

          'registerIpAddress': this.dataForm.registerIpAddress ,

          'registerDeviceCode': this.dataForm.registerDeviceCode ,

          'userName': this.dataForm.userName ,

          'remark': this.dataForm.remark ,

          'agentEnable': this.dataForm.agentEnable ,

          'firstRecharge': this.dataForm.firstRecharge ,

          'gameInfoId': this.dataForm.gameInfoId ,

          'gameServerId': this.dataForm.gameServerId ,

          'noScan': this.dataForm.noScan ,

          'phone': this.dataForm.phone ,

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
