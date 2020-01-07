<template xmlns:text-align="http://www.w3.org/1999/xhtml">
  <el-dialog
    :title="'支付设置'"
    :close-on-click-modal="false"
    :visible.sync="visible">


    <el-form :inline="true"  class="demo-form-inline">
      <el-form-item label="公司名称 :">
        {{this.dataForm.payName}}
      </el-form-item>
      <el-form-item label="商户编号 :">
        {{this.dataForm.uid}}
      </el-form-item>
      <el-form-item label="商户密钥 :">
        {{this.dataForm.secret}}
      </el-form-item>
    </el-form>



    <el-form :inline="true"  class="demo-form-inline">
     <!-- <el-form-item label="回调地址: ">
        {{dataForm.callbackUrl}}
      </el-form-item>-->
      <el-form-item label="支付地址: ">
        {{dataForm.payUrl}}
      </el-form-item>

    </el-form>

    <el-form :inline="true"  class="demo-form-inline">
      <el-form-item>
        <el-radio-group v-model="radio" @change="handlerechargechannelChange" class="radio-wrap">
          <el-radio v-for="rechargechannel in newOptions" :label="rechargechannel.id">
            {{rechargechannel.name}}
          </el-radio> </el-radio-group>
      </el-form-item>


    </el-form>

    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">

      <el-form-item style="justify-content: left" label="产品代码" prop="alipay" class="item-input" v-if="dataForm.rechargechannel.alias=='alipay'">
        <el-input v-model=" dataForm.alipay" placeholder="alipay"></el-input>
      </el-form-item>
      <el-form-item style="justify-content: left" label="产品代码" prop="weixin" v-if="dataForm.rechargechannel.alias=='weixin'" class="item-input">
        <el-input v-model="dataForm.weixin" placeholder="weixin"></el-input>
      </el-form-item>
      <el-form-item style="justify-content: left" label="产品代码" prop="unionpay" class="item-input"
                    v-if="dataForm.rechargechannel.alias+''=='unionpay'">
        <el-input v-model="dataForm.unionpay" placeholder="unionpay"></el-input>
      </el-form-item>
      <el-form-item style="justify-content: left" label="产品代码" prop="quickpay" class="item-input"
                    v-if="dataForm.rechargechannel.alias+''=='quickpay'">
        <el-input v-model="dataForm.quickpay" placeholder="quickpay"></el-input>
      </el-form-item>
      <el-form-item style="justify-content: left" label="产品代码" prop="qqpay" class="item-input" v-if="dataForm.rechargechannel.alias+''=='qqpay'">
        <el-input v-model="dataForm.qqpay" placeholder="qqpay"></el-input>
      </el-form-item>
      <el-form-item style="justify-content: left" label="产品代码" prop="jindongpay" class="item-input"
                    v-if="dataForm.rechargechannel.alias+''=='jindongpay'">
        <el-input v-model="dataForm.jindongpay" placeholder="jindongpay"></el-input>
      </el-form-item>
      <el-tooltip class="item" effect="dark" content="限制支付的最低金额" placement="top-start">
        <el-form-item style="justify-content: left"  label="最大充值金额" prop="highLimit" class="item-input">
          <el-input v-model="dataForm.rechargechannel.highLimit" placeholder="限制最低支付金额"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip  class="item" effect="dark" content="限制支付的最高金额" placement="top-start">
        <el-form-item style="justify-content: left" label="最小充值金额" prop="lowLimit" class="item-input">
          <el-input v-model="dataForm.rechargechannel.lowLimit" placeholder="限制最高支付金额"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-form-item style="justify-content: left"  label="显示名称" prop="showName" class="item-input">
        <el-input v-model="dataForm.rechargechannel.showName" placeholder="显示名称"></el-input>
      </el-form-item>
      <el-form-item style="justify-content: left"  label="排序" prop="orderNo" class="item-input">
        <el-input v-model="dataForm.rechargechannel.orderNo" placeholder="排序"></el-input>
      </el-form-item>
      <!--<el-form-item label="推广层级" prop="hierarchy">-->
      <!--<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>-->
      <!--<div style="margin: 15px 0;"></div>-->
      <!--<el-checkbox-group v-model="dataForm.hierarchy" @change="handleCheckedHierarchyChange">-->
      <!--<el-checkbox v-for="hierarchy in hierarchyOptions" :label="hierarchy.id" :key="hierarchy.name">-->
      <!--{{hierarchy.name}}-->
      <!--</el-checkbox>-->
      <!--</el-checkbox-group>-->
      <!--</el-form-item>-->
      <el-form-item style="justify-content: left" label="层级" prop="hierarchyId">
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="dataForm.hierarchyId" @change="handleCheckedhierarchyTypesListChange">
          <el-checkbox v-for="hierarchyType in hierarchyTypeList" :label="hierarchyType.id" :key="hierarchyType.id">
            {{hierarchyType.name}}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-tooltip class="item" effect="dark" content="可选充值金额(请输入数字多个值请以逗号隔开)" placement="top-start">
        <el-form-item style="justify-content: left" label="可选充值金额" prop="amount" class="item-input">
          <el-input v-model="dataForm.amount"  placeholder="多个值请以逗号隔开"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="状态" placement="top-start">
        <el-form-item style="justify-content: left" label="状态" prop="enable">
          <el-radio-group v-model="dataForm.enable">
            <el-radio :label="true">启用</el-radio>
            <el-radio :label="false">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-tooltip>
      <!--  <el-tooltip class="item" effect="dark" content="是否设置首个推用" placement="top-start">
            <el-form-item label="是否为首推" prop="enable">
                <el-radio-group v-model="dataForm.firstPush">
                    <el-radio :label="true">是</el-radio>
                    <el-radio :label="false">否</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-tooltip> -->
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
      var validateInteger = (rule, value, callback) => {
        var res = /^[0-9]*[1-9][0-9]*$/;
        if (value === '') {
          callback(new Error('不可为空'));
        } else {
          if (!res.test(value)) {
            callback(new Error('格式有误必须为整数'));
          }
          callback();
        }
      };
      var validateAmount = (rule, value, callback) => {
        if (value) {

          var retgex=/^\d+(,\d+)*$/;
          if (!retgex.test(value)) {
            console.log("true")

            callback(new Error('限制输入数字和逗号，不能以逗号结尾'));
          }else{
            console.log("false")
            callback();
          }
        } else {
          callback();
        }
      }
      return {
        fileList: [],
        fileList2: [],
        fileList3: [],
        regObject: {
         mon: /[^\d+(,\d\d\d)*.\d+$]/g
        },
        num: 0,
        show: false,
        flag: false,
        appIconShow: false,
        webIconShow: false,
        form: null,
        successNum: 0,
        options: [],
        rechargechannel: [],
        visible: false,
        radio: '',
        isIndeterminate: true,
        checkAll: false,
        hierarchyTypeList: [],
        checkAllOptions: [],
        checkOptions: [],
        hierarchyId: '',
        dataForm: {
          amount: '',
          id: 0,
          showName: '',
          orderNo: '',
          name: '',
          payName: '',
          paymentMethod: [],
          channelId: '',
          enable: true,
          // firstPush: false,
          // appIconId: '',
          // appIconMd5: '',
          // webIconId: '',
          // webIconMd5: '',
          // enclosureId: '',
          // enclosureMd5: '',
          hierarchyId: [],
          hierarchyTypesName: [],
          aliasName: '',
          orderNum: '',
          hierarchy: [],
          rechargechannel: [],
          hierarchyName: [],
          alipay: '', //6个产品编码字段
          weixin: '',
          unionpay: '',
          quickpay: '',
          qqpay: '',
          jindongpay: '',
          uid: '', //4个秘钥
          secret: '',
          publicKey: '',
          privateKey: '',
          callbackUrl: '', //回调地址
          payUrl: '', //支付地址

          alipayId: '', //6个产品编码字段Id
          weixinId: '',
          unionpayId: '',
          quickpayId: '',
          qqpayId: '',
          jindongpayId: '',
          uidId: '', //4个秘钥Id
          secretId: '',
          publicKeyId: '',
          privateKeyId: '',
          callbackUrlId: '', //回调地址Id
          payUrlId: '', //支付地址Id
        },
        showButton: true,
        newOptions: [],
        hierarchyOptions: [],
        dataRule: {
          hierarchyId: [
            {required: true, message: '请选择层级', trigger: 'blur'}
          ],
          amount: [
            { validator: validateAmount, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init(id, aliasName) {
        this.dataForm.aliasName = aliasName
        this.dataForm.id = id
        this.$http({
          url: this.$http.adornUrl(`/userhierarchy/userhierarchy/selectHierarchy`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.checkOptions = data.dataList,
              this.hierarchyTypeList = data.dataList,
              this.checkAllOptions = data.ids
          }
        });
        this.fileList = []
        this.visible = true
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/payconfig/payconfig/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.dataForm.payName = data.payconfig.payName
              this.dataForm.name = data.payconfig.name

              this.dataForm.firstPush = data.payconfig.firstPush
              let dataStrArr = data.payconfig.paymentMethod.split(",");  //分割成字符串数组
              let dataIntArr = [];//保存转换后的整型字符串
              dataStrArr.forEach(item => {
                dataIntArr.push(+item);
              });
              this.dataForm.paymentMethod = dataIntArr
              this.dataForm.aliasName = data.payconfig.aliasName
              if (this.dataForm.aliasName) {
               // this.$refs['dataForm'].resetFields()
                this.$http({
                  url: this.$http.adornUrl(`/sysconfig/sysconfig/sysConfigParam/${this.dataForm.aliasName}`),
                  method: 'get',
                  params: this.$http.adornParams()
                }).then(({
                           data
                         }) => {
                  if (data && data.code === 200) {
                    this.dataForm.alipay = data.sysConfigPayParam.alipay
                    this.dataForm.weixin = data.sysConfigPayParam.weixin
                    this.dataForm.unionpay = data.sysConfigPayParam.unionpay
                    this.dataForm.quickpay = data.sysConfigPayParam.quickpay
                    this.dataForm.qqpay = data.sysConfigPayParam.qqpay
                    this.dataForm.jindongpay = data.sysConfigPayParam.jindongpay
                    this.dataForm.uid = data.sysConfigPayParam.uid
                    this.dataForm.secret = data.sysConfigPayParam.secret
                    this.dataForm.publicKey = data.sysConfigPayParam.publicKey
                    this.dataForm.privateKey = data.sysConfigPayParam.privateKey
                    this.dataForm.callbackUrl = data.sysConfigPayParam.callbackUrl
                    this.dataForm.payUrl = data.sysConfigPayParam.payUrl
                    if (data.sysConfigPayParam.alipayId != null) {
                      this.dataForm.alipayId = data.sysConfigPayParam.alipayId
                    } else {
                      this.dataForm.alipayId = '';
                    }
                    if (data.sysConfigPayParam.weixinId != null) {
                      this.dataForm.weixinId = data.sysConfigPayParam.weixinId
                    } else {
                      this.dataForm.weixinId = '';
                    }
                    if (data.sysConfigPayParam.unionpayId != null) {
                      this.dataForm.unionpayId = data.sysConfigPayParam.unionpayId
                    } else {
                      this.dataForm.unionpayId = '';
                    }
                    if (data.sysConfigPayParam.quickpayId != null) {
                      this.dataForm.quickpayId = data.sysConfigPayParam.quickpayId
                    } else {
                      this.dataForm.quickpayId = '';
                    }
                    if (data.sysConfigPayParam.qqpayId != null) {
                      this.dataForm.qqpayId = data.sysConfigPayParam.qqpayId
                    } else {
                      this.dataForm.qqpayId = '';
                    }
                    if (data.sysConfigPayParam.jindongpayId != null) {
                      this.dataForm.jindongpayId = data.sysConfigPayParam.jindongpayId
                    } else {
                      this.dataForm.jindongpayId = '';
                    }

                    if (data.sysConfigPayParam.uidId != null) {
                      this.dataForm.uidId = data.sysConfigPayParam.uidId
                    } else {
                      this.dataForm.uidId = '';
                    }
                    if (data.sysConfigPayParam.secretId != null) {
                      this.dataForm.secretId = data.sysConfigPayParam.secretId
                    } else {
                      this.dataForm.secretId = '';
                    }
                    if (data.sysConfigPayParam.publicKeyId != null) {
                      this.dataForm.publicKeyId = data.sysConfigPayParam.publicKeyId
                    } else {
                      this.dataForm.publicKeyId = '';
                    }
                    if (data.sysConfigPayParam.privateKeyId != null) {
                      this.dataForm.privateKeyId = data.sysConfigPayParam.privateKeyId
                    } else {
                      this.dataForm.privateKeyId = '';
                    }

                    if (data.sysConfigPayParam.callbackUrlId != null) {
                      this.dataForm.callbackUrlId = data.sysConfigPayParam.callbackUrlId
                    } else {
                      this.dataForm.callbackUrlId = '';
                    }
                    if (data.sysConfigPayParam.payUrlId != null) {
                      this.dataForm.payUrlId = data.sysConfigPayParam.payUrlId
                    } else {
                      this.dataForm.payUrlId = '';
                    }

                  }
                })
              }
              this.dataForm.orderNum = data.payconfig.orderNum
              if (data.payconfig.appIconId != null) {
                this.appIconShow = true
              }
              if (data.payconfig.webIconId != null) {
                this.webIconShow = true
              }
              if (data.payconfig.enclosureId != null) {
                this.show = true
              }
            }
          })
          if (this.dataForm.id) {
            //this.$refs['dataForm'].resetFields()
            this.$http({
              url: this.$http.adornUrl(`/rechargechannel/rechargechannel/selectByPayId/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.newOptions = data.list
                this.dataForm.rechargechannel = this.newOptions[0]
                this.dataForm.channelId = this.dataForm.rechargechannel.id
                this.radio = this.dataForm.rechargechannel.id
                this.dataForm.pccId = this.dataForm.rechargechannel.pccId
                this.dataForm.amount = this.dataForm.rechargechannel.amount
                this.dataForm.enable = this.dataForm.rechargechannel.enable
                if (this.dataForm.pccId) {
                  this.$http({
                    url: this.$http.adornUrl(`/hierarchypaychannelconfigrelationship/hierarchypaychannelconfigrelationship/info/${this.dataForm.pccId}`),
                    method: 'get',
                    params: this.$http.adornParams()
                  }).then(({data}) => {
                    if (data && data.code === 200) {
                      this.dataForm.hierarchyId = data.hierarchypaychannelconfigrelationship
                      let checkedCount = this.dataForm.hierarchyId.length;
                      this.checkAll = checkedCount === this.hierarchyTypeList.length;
                      this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyTypeList.length;
                    } else {
                      this.dataForm.hierarchyId = [];
                    }
                  })
                }

              }
            });
          }
        })
      },
      handlerechargechannelChange(value) {
        this.dataForm.channelId = value
        this.dataForm.paymentMethodId = value
        this.$refs['dataForm'].resetFields()
        this.$http({
          url: this.$http.adornUrl(`/rechargechannel/rechargechannel/selectByPayIdAndChannelId`),
          method: 'post',
          data: this.$http.adornData(
            {
              "payId": this.dataForm.id,
              "channelId": this.dataForm.channelId

            }
          )
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataForm.rechargechannel = data.rechargechannel
            this.dataForm.pccId = this.dataForm.rechargechannel.pccId
            this.dataForm.amount = this.dataForm.rechargechannel.amount
            this.dataForm.enable = this.dataForm.rechargechannel.enable
            //console.log(this.dataForm.pccId)
            if (this.dataForm.pccId) {
              this.$http({
                url: this.$http.adornUrl(`/hierarchypaychannelconfigrelationship/hierarchypaychannelconfigrelationship/info/${this.dataForm.pccId}`),
                method: 'get',
                params: this.$http.adornParams()
              }).then(({data}) => {
                if (data && data.code === 200) {
                  this.dataForm.hierarchyId = data.hierarchypaychannelconfigrelationship
                  let checkedCount = this.dataForm.hierarchyId.length;
                  this.checkAll = checkedCount === this.hierarchyTypeList.length;
                  this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyTypeList.length;
                } else {
                  this.dataForm.hierarchyId = [];
                }
              })
            }
            if (this.dataForm.aliasName) {
              // this.$refs['dataForm'].resetFields()
              this.$http({
                url: this.$http.adornUrl(`/sysconfig/sysconfig/sysConfigParam/${this.dataForm.aliasName}`),
                method: 'get',
                params: this.$http.adornParams()
              }).then(({
                         data
                       }) => {
                if (data && data.code === 200) {
                  this.dataForm.alipay = data.sysConfigPayParam.alipay
                  this.dataForm.weixin = data.sysConfigPayParam.weixin
                  this.dataForm.unionpay = data.sysConfigPayParam.unionpay
                  this.dataForm.quickpay = data.sysConfigPayParam.quickpay
                  this.dataForm.qqpay = data.sysConfigPayParam.qqpay
                  this.dataForm.jindongpay = data.sysConfigPayParam.jindongpay
                  this.dataForm.uid = data.sysConfigPayParam.uid
                  this.dataForm.secret = data.sysConfigPayParam.secret
                  this.dataForm.publicKey = data.sysConfigPayParam.publicKey
                  this.dataForm.privateKey = data.sysConfigPayParam.privateKey
                  this.dataForm.callbackUrl = data.sysConfigPayParam.callbackUrl
                  this.dataForm.payUrl = data.sysConfigPayParam.payUrl
                  if (data.sysConfigPayParam.alipayId != null) {
                    this.dataForm.alipayId = data.sysConfigPayParam.alipayId
                  } else {
                    this.dataForm.alipayId = '';
                  }
                  if (data.sysConfigPayParam.weixinId != null) {
                    this.dataForm.weixinId = data.sysConfigPayParam.weixinId
                  } else {
                    this.dataForm.weixinId = '';
                  }
                  if (data.sysConfigPayParam.unionpayId != null) {
                    this.dataForm.unionpayId = data.sysConfigPayParam.unionpayId
                  } else {
                    this.dataForm.unionpayId = '';
                  }
                  if (data.sysConfigPayParam.quickpayId != null) {
                    this.dataForm.quickpayId = data.sysConfigPayParam.quickpayId
                  } else {
                    this.dataForm.quickpayId = '';
                  }
                  if (data.sysConfigPayParam.qqpayId != null) {
                    this.dataForm.qqpayId = data.sysConfigPayParam.qqpayId
                  } else {
                    this.dataForm.qqpayId = '';
                  }
                  if (data.sysConfigPayParam.jindongpayId != null) {
                    this.dataForm.jindongpayId = data.sysConfigPayParam.jindongpayId
                  } else {
                    this.dataForm.jindongpayId = '';
                  }

                  if (data.sysConfigPayParam.uidId != null) {
                    this.dataForm.uidId = data.sysConfigPayParam.uidId
                  } else {
                    this.dataForm.uidId = '';
                  }
                  if (data.sysConfigPayParam.secretId != null) {
                    this.dataForm.secretId = data.sysConfigPayParam.secretId
                  } else {
                    this.dataForm.secretId = '';
                  }
                  if (data.sysConfigPayParam.publicKeyId != null) {
                    this.dataForm.publicKeyId = data.sysConfigPayParam.publicKeyId
                  } else {
                    this.dataForm.publicKeyId = '';
                  }
                  if (data.sysConfigPayParam.privateKeyId != null) {
                    this.dataForm.privateKeyId = data.sysConfigPayParam.privateKeyId
                  } else {
                    this.dataForm.privateKeyId = '';
                  }

                  if (data.sysConfigPayParam.callbackUrlId != null) {
                    this.dataForm.callbackUrlId = data.sysConfigPayParam.callbackUrlId
                  } else {
                    this.dataForm.callbackUrlId = '';
                  }
                  if (data.sysConfigPayParam.payUrlId != null) {
                    this.dataForm.payUrlId = data.sysConfigPayParam.payUrlId
                  } else {
                    this.dataForm.payUrlId = '';
                  }

                }
              })
            }
          }
        });
      },
      handleCheckAllChange(val) {
        this.dataForm.hierarchyId = val ? this.checkAllOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedhierarchyTypesListChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.hierarchyTypeList.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyTypeList.length;
      },
      gethierarchyTypesListName() {
        this.dataForm.hierarchyTypesName = [];
        if (this.checkAll) {
          for (var i = 0; i < this.hierarchyTypeList.length; i++) {
            this.dataForm.hierarchyTypesName.push(this.hierarchyTypeList[i].name);
          }
        } else {
          for (var j = 0; j < this.dataForm.hierarchyId.length; j++) {
            for (var i = 0; i < this.hierarchyTypeList.length; i++) {
              if (this.dataForm.hierarchyId[j] == this.hierarchyTypeList[i].id) {
                this.dataForm.hierarchyTypesName.push(this.hierarchyTypeList[i].name);
                break;
              }
            }
          }
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {

            this.$http({
              // url: this.$http.adornUrl(`/payconfig/payconfig/${!this.dataForm.id ? 'saveAll' : 'update'}`),
              url: this.$http.adornUrl(`/payconfig/payconfig/saveAll`),
              method: 'post',
              data: this.$http.adornData({

                "sysConfigPayParam": {
                  "name": this.dataForm.name,//公司名称
                  "aliasName": this.dataForm.aliasName,//别名
                  "alipay": this.dataForm.alipay,
                  "weixin": this.dataForm.weixin,
                  "unionpay": this.dataForm.unionpay,
                  "quickpay": this.dataForm.quickpay,
                  "qqpay": this.dataForm.qqpay,
                  "jindongpay": this.dataForm.jindongpay,
                  "uid": this.dataForm.uid,
                  "secret": this.dataForm.secret,
                  "publicKey": this.dataForm.publicKey,
                  "privateKey": this.dataForm.privateKey,
                  "callbackUrl": this.dataForm.callbackUrl,
                  "payUrl": this.dataForm.payUrl,
                  "alipayId": this.dataForm.alipayId,
                  "weixinId": this.dataForm.weixinId,
                  "unionpayId": this.dataForm.unionpayId,
                  "quickpayId": this.dataForm.quickpayId,
                  "qqpayId": this.dataForm.qqpayId,
                  "jindongpayId": this.dataForm.jindongpayId,
                  "uidId": this.dataForm.uidId,
                  "secretId": this.dataForm.secretId,
                  "publicKeyId": this.dataForm.publicKeyId,
                  "privateKeyId": this.dataForm.privateKeyId,
                  "callbackUrlId": this.dataForm.callbackUrlId,
                  "payUrlId": this.dataForm.payUrlId
                }, "payChannelConfigEntity": {
                  'payId': this.dataForm.id,

                  'enable': this.dataForm.enable,

                  'channelId': this.dataForm.channelId,

                  'highLimit': this.dataForm.rechargechannel.highLimit,

                  'lowLimit': this.dataForm.rechargechannel.lowLimit,
                  'amount': this.dataForm.amount,
                  'showName': this.dataForm.rechargechannel.showName,
                  'orderNo': this.dataForm.rechargechannel.orderNo
                },
                "hierarchyId": this.dataForm.hierarchyId


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


<style scoped>
  .table-tr {
    display: flex;
    justify-content: space-around;
    margin-bottom: 10px;
  }

  .td-end {
    justify-content: space-between !important;
  }

  .radio-wrap {
    display: flex;
    justify-content: center;
  }

  .item-input {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
  }

  .item-input >>> .el-form-item__content {
    margin-left: 10px !important;
  }

  .item-input >>> .el-form-item__label {
    width: 120px !important;
  }

  .item-input >>> .el-input__inner {
    width: 265px;
  }
</style>
