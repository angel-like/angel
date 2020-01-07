<template>
  <div class="mod-config">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="200px">
      <el-form-item label="会员账号">
        <el-input style="width: 150px" clearable v-model="dataForm.userAccountQuery" @change="getDiscount()"
                  placeholder="会员账号"></el-input>
        <el-button @click="getUser()">查找</el-button>
      </el-form-item>
      <el-form-item label="待操作会员账号" prop="userAccount">
        <div v-clipboard:copy="dataForm.userAccount"
             v-clipboard:success="onCopy"
             v-clipboard:error="onError"
             style="color: blue;cursor:pointer;">
          {{dataForm.userAccount}}
          <!-- <button type="button" class="btn"
                        v-clipboard:copy="dataForm.userAccount"
                        >
                        复制
                </button> -->
          <i class="el-icon-document"></i>
        </div>
      </el-form-item>

      <el-form-item label="当前需要打码量" prop="userNeedBet"
                    >
        <div v-clipboard:copy="dataForm.userNeedBet"
             v-clipboard:success="onCopy"
             v-clipboard:error="onError"
             style="color: blue;cursor:pointer;">
          {{dataForm.userNeedBet}}
          <!-- <button type="button" class="btn"
                        v-clipboard:copy="dataForm.userAccount"
                        >
                        复制
                </button> -->
          <i class="el-icon-document"></i>
        </div>
      </el-form-item>
      <el-form-item label="当前打码量" prop="userValidBet"
      >
        <div v-clipboard:copy="dataForm.userValidBet"
             v-clipboard:success="onCopy"
             v-clipboard:error="onError"
             style="color: blue;cursor:pointer;">
          {{dataForm.userValidBet}}
          <!-- <button type="button" class="btn"
                        v-clipboard:copy="dataForm.userAccount"
                        >
                        复制
                </button> -->
          <i class="el-icon-document"></i>
        </div>
      </el-form-item>
      <el-form-item label="操作类型" prop="rechargeType">
        <el-select v-model="dataForm.type" placeholder="操作类型" clearable>
          <el-option v-for="item in typeOptions" :key="item.value" :label="item.value" :value="item.key">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="修改打码量" prop="amount" v-if="dataForm.type==1||dataForm.type==2">
        <el-input-number   :min="0"  style="width: 200px" v-model="dataForm.amount" placeholder="操作金额"></el-input-number>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" style="width: 200px" v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" style="float:left; padding-left:250px; " class="dialog-footer">
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </div>
</template>

<script>
  export default {
    data() {
      var checkUserNeedBet = (rule, value, callback) => {
        if (value) {
        if (Number(value) <= 0) {
            callback(new Error('用户需要打码量为零不能操作'));
          } else {
            callback();
          }
        } else {
          callback();
        }

      };
      return {

        typeOptions: [
          {key: 0, value: "清空"},
          {key: 1, value: "增加"},
          {key: 2, value: "减少"}
        ],

        dataForm: {
          id: 0,
          type: '',
          userId: '',
          amount: '',
          sysUserAccount: '',
          sysUserId: '',
          userAccount: '',
          type: 1,
          userValidBet: 0,
          userNeedBet: 0,
          remark: '',
        },
        dataRule: {
          type: [
            {required: true, message: '操作类型不能为空', trigger: 'blur'}
          ],
          userId: [
            {required: true, message: '用户id不能为空', trigger: 'blur'}
          ],
          userAccount: [
            {required: true, message: '不能为空', trigger: 'blur'}
          ],
          userNeedBet: [
            {required: true, message: '不能为空', trigger: 'blur'},
            {validator: checkUserNeedBet, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/ordercashrecord/ordercashrecord/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.type = data.ordercashrecord.type
                this.dataForm.userId = data.ordercashrecord.userId
                this.dataForm.amount = data.ordercashrecord.amount
                this.dataForm.sysUserAccount = data.ordercashrecord.sysUserAccount
                this.dataForm.sysUserId = data.ordercashrecord.sysUserId
                this.dataForm.userAccount = data.ordercashrecord.userAccount
              }
            })
          }
        })
      },
      getUser() {
        if (this.dataForm.userAccountQuery == '') {
          this.$message.error("请先输入会员账号")
          return;
        }
        this.dataForm.discountAmount = 0;
        this.dataForm.vipDiscount = 0;
        this.dataForm.amount = 0;
        this.codingCoin = 0;

        this.$http({
          url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/getUser`),
          method: 'get',
          params: this.$http.adornParams({"account": this.dataForm.userAccountQuery})
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataForm.userId = data.data.userId;
            if (data.data.userName) {
              this.dataForm.userName = data.data.userName;
            } else {
              this.dataForm.userName = "无";
            }
            this.dataForm.coin = data.data.coin / 100;
            this.dataForm.money = data.data.money;
            this.dataForm.totalMoney = data.data.totalMoney;
            this.dataForm.discountMultiple = data.data.discountMultiple;
            this.dataForm.hierarchyId = data.data.hierarchyId;
            this.dataForm.hierarchyName = data.data.hierarchyName;
            this.dataForm.rechargeMultiple = data.data.rechargeMultiple;
            this.dataForm.userAccount = this.dataForm.userAccountQuery;
            this.dataForm.userValidBet = (data.data.userValidBet /100).toFixed(2);
            this.dataForm.userNeedBet = (data.data.userNeedBet ).toFixed(2);
          } else {
            this.dataForm.userId = "";
            this.dataForm.userAccount = "";
            this.dataForm.userAccountQuery = "";
            this.dataForm.userName = "无";
            this.dataForm.money = 0;
            this.dataForm.totalMoney = 0;
            this.dataForm.coin = 0;
            this.dataForm.discountMultiple = 0;
            this.dataForm.hierarchyId = '';
            this.dataForm.hierarchyName = '';
            this.dataForm.rechargeMultiple = 0;
            this.dataForm.userValidBet = 0;
            this.dataForm.userNeedBet = 0;


            this.$message.error(data.msg)
          }
        });
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ordercashrecord/ordercashrecord/save`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'type': this.dataForm.type,

                'userId': this.dataForm.userId,

                'amount': this.dataForm.amount*100,

                'sysUserAccount': this.dataForm.sysUserAccount,

                'sysUserId': this.dataForm.sysUserId,

                'userAccount': this.dataForm.userAccount,
                'remark': this.dataForm.remark,

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
              this.$refs['dataForm'].resetFields()
            })
          }
        })
      }
    }
  }
</script>
