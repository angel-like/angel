<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-tooltip class="item" effect="dark" content="新增支付公司的名称" placement="top-start">
        <el-form-item label="公司名称" prop="payName">
          <el-input v-model="dataForm.payName" type="text" placeholder="公司名称" maxlength="10" show-word-limit></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="在客户端显示的名称" placement="top-start">
        <el-form-item label="显示名称" prop="name">
          <el-input v-model="dataForm.name" type="text" placeholder="客户端显示名称" maxlength="10" show-word-limit></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="支付公司别名(不可更改)" placement="top-start" v-if="flag">
        <el-form-item label=" 别名
      " prop="aliasName">
          <el-input v-model="dataForm.aliasName" placeholder="别名"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="通过什么渠道支付" placement="top-start">
        <el-form-item label="渠道" prop="paymentMethod">
          <el-select v-model="dataForm.paymentMethod" multiple placeholder="请选择渠道">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="商户编号" prop="uid">
        <el-input v-model="dataForm.uid" placeholder="uid"></el-input>
      </el-form-item>
      <el-form-item label="商户密钥" prop="secret">
        <el-input v-model="dataForm.secret" placeholder="secret"></el-input>
      </el-form-item>
      <el-form-item label="公钥" prop="publicKey">
        <el-input v-model="dataForm.publicKey" placeholder="publicKey"></el-input>
      </el-form-item>
      <el-form-item label="私钥" prop="privateKey">
        <el-input v-model="dataForm.privateKey" placeholder="privateKey"></el-input>
      </el-form-item>
      <!--<el-form-item label="回调地址" prop="callbackUrl">
        <el-input v-model="dataForm.callbackUrl" placeholder="回调地址"></el-input>
      </el-form-item>-->
      <el-form-item label="支付地址" prop="payUrl">
        <el-input v-model="dataForm.payUrl" placeholder="支付地址"></el-input>
      </el-form-item>
      <el-tooltip class="item" effect="dark" content="支付成功回调函数允许访问的IP,多个以逗号分割" placement="top-start">
        <el-form-item label="回调IP白名单" prop="payUrl">
          <el-input v-model="dataForm.callbackIp" placeholder="回调IP白名单,多个值用逗号分割"></el-input>
        </el-form-item>
      </el-tooltip>
      <!--<el-form-item label="app图标ID" v-if="appIconShow">
        <el-input v-model="dataForm.appIconId" placeholder="附件ID" style="width: 200px;" readOnly></el-input>
        <el-button size="mini" type="primary" title="查看" @click="getAppIconUrl()">预览APP图标</el-button>
      </el-form-item>


      <el-form-item label="appMD5" v-if="appIconShow">
        <el-input v-model="dataForm.appIconMd5" placeholder="appMD5"></el-input>
      </el-form-item>-->
      <!--
            <el-form-item label="web端图标" prop="webIconId" v-if="webIconShow">
              <el-input v-model="dataForm.webIconId" placeholder="附件ID" style="width: 200px;" readOnly></el-input>
              <el-button size="mini" type="primary" title="查看" @click="getWebIconUrl()">预览WEB图标</el-button>
            </el-form-item>-->


      <!--<el-form-item label="webMD5" prop="webIconMd5" v-if="webIconShow">
        <el-input v-model="dataForm.webIconMd5" placeholder="webMD5"></el-input>
      </el-form-item>-->


      <!-- <el-form-item label="附件" prop="enclosureId" v-if="show">
         <el-input v-model="dataForm.enclosureId" placeholder="附件ID" style="width: 200px;" readOnly></el-input>
         <el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
       </el-form-item>-->


      <!--<el-form-item label="MD5" prop="enclosureMd5" v-if="show">
        <el-input v-model="dataForm.enclosureMd5" placeholder="MD5"></el-input>
      </el-form-item>-->
      <el-form-item label="排序" prop="orderNum">
        <el-input v-model="dataForm.orderNum" placeholder="排序"></el-input>
      </el-form-item>
      <el-tooltip class="item" effect="dark" content="是否启用" placement="top-start">
        <el-form-item label="启用/禁用" prop="enable">
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
    <!-- <el-tooltip class="item" effect="dark" content="上传支付方式的app图标" placement="top-start">
       <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="iconSuccessHandle" multiple
                  :file-list="fileList" :data="form" style="text-align: center;">
         <i class="el-icon-upload"></i>
         <div class="el-upload__text">将文件拖到此处，或<em>点击上传APP图标</em></div>
         <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
       </el-upload>
     </el-tooltip>-->

    <!--<el-tooltip class="item" effect="dark" content="上传支付方式的web图标" placement="top-start">
      <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="webIconSuccessHandle"
                 multiple
                 :file-list="fileList2" :data="form" style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传WEB图标</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>-->

    <!--<el-tooltip class="item" effect="dark" content="上传支付方式的图片" placement="top-start">
      <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
                 :file-list="fileList3" :data="form" style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>-->

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      //验证正整数
      var validateInteger = (rule, value, callback) => {
        var res = /^[0-9]*[1-9][0-9]*$/
        if (value === '') {
          callback(new Error('不可为空'))
        } else {
          if (!res.test(value)) {
            callback(new Error('格式有误必须为整数'))
          }
          callback()
        }
      }
      return {
        fileList: [],
        fileList2: [],
        fileList3: [],
        num: 0,
        show: false,
        flag: false,
        appIconShow: false,
        webIconShow: false,
        form: null,
        successNum: 0,
        options: [],
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          payName: '',
          paymentMethod: [],
          enable: true,
          firstPush: false,
          appIconId: '',
          appIconMd5: '',
          webIconId: '',
          webIconMd5: '',
          enclosureId: '',
          enclosureMd5: '',
          aliasName: '',
          orderNum: '',
          // alipay: '', //6个产品编码字段
          // weixin: '',
          // unionpay: '',
          // quickpay: '',
          // qqpay: '',
          // jindongpay: '',
          uid: '', //4个秘钥
          secret: '',
          publicKey: '',
          privateKey: '',
          callbackUrl: '', //回调地址
          callbackIp: '',
          payUrl: '', //支付地址

          // alipayId: '', //6个产品编码字段Id
          // weixinId: '',
          // unionpayId: '',
          // quickpayId: '',
          // qqpayId: '',
          // jindongpayId: '',
          uidId: '', //4个秘钥Id
          secretId: '',
          publicKeyId: '',
          privateKeyId: '',
          callbackUrlId: '', //回调地址Id
          payUrlId: '', //支付地址Id
        },
        dataRule: {
          payName: [
            {required: true, message: '公司名称不能为空', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '显示名称不能为空', trigger: 'blur'}
          ],
          paymentMethod: [
            {required: true, message: '支付方式不能为空', trigger: 'blur'}
          ],
          enable: [
            {required: true, message: '状态不能为空', trigger: 'blur'}
          ],
          firstPush: [
            {required: true, message: '是否为首推不能为空', trigger: 'blur'}
          ],
          orderNum: [
            {validator: validateInteger, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id) {
        //为下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/rechargechannel/rechargechannel/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.options = data.list
            console.log(this.options)
          }
        })

        this.show = false
        this.appIconShow = false
        this.webIconShow = false
        this.fileList = []
        this.fileList2 = []
        this.fileList3 = []
        this.dataForm.appIconId = ''
        this.dataForm.appIconMd5 = ''
        this.dataForm.webIconId = ''
        this.dataForm.webIconMd5 = ''
        this.dataForm.enclosureId = ''
        this.dataForm.enclosureMd5 = ''
        this.dataForm.id = id || 0
        console.log(this.dataForm.id)
        if (this.dataForm.id === 0) {
          this.flag = true
        } else if (this.dataForm.id) {
          this.flag = false
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/payconfig/payconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.payName = data.payconfig.payName
                this.dataForm.callbackIp = data.payconfig.callbackIp
                this.dataForm.name = data.payconfig.name
                this.dataForm.enable = data.payconfig.enable
                this.dataForm.firstPush = data.payconfig.firstPush
                let dataStrArr = data.payconfig.paymentMethod.split(',')  //分割成字符串数组
                let dataIntArr = []//保存转换后的整型字符串
                dataStrArr.forEach(item => {
                  dataIntArr.push(+item)
                })
                this.dataForm.paymentMethod = dataIntArr

                this.dataForm.appIconId = data.payconfig.appIconId
                this.dataForm.appIconMd5 = data.payconfig.appIconMd5
                this.dataForm.webIconId = data.payconfig.webIconId
                this.dataForm.webIconMd5 = data.payconfig.webIconMd5
                this.dataForm.enclosureId = data.payconfig.enclosureId
                this.dataForm.enclosureMd5 = data.payconfig.enclosureMd5
                this.dataForm.aliasName = data.payconfig.aliasName

                if (this.dataForm.aliasName) {
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
                        this.dataForm.alipayId = ''
                      }
                      if (data.sysConfigPayParam.weixinId != null) {
                        this.dataForm.weixinId = data.sysConfigPayParam.weixinId
                      } else {
                        this.dataForm.weixinId = ''
                      }
                      if (data.sysConfigPayParam.unionpayId != null) {
                        this.dataForm.unionpayId = data.sysConfigPayParam.unionpayId
                      } else {
                        this.dataForm.unionpayId = ''
                      }
                      if (data.sysConfigPayParam.quickpayId != null) {
                        this.dataForm.quickpayId = data.sysConfigPayParam.quickpayId
                      } else {
                        this.dataForm.quickpayId = ''
                      }
                      if (data.sysConfigPayParam.qqpayId != null) {
                        this.dataForm.qqpayId = data.sysConfigPayParam.qqpayId
                      } else {
                        this.dataForm.qqpayId = ''
                      }
                      if (data.sysConfigPayParam.jindongpayId != null) {
                        this.dataForm.jindongpayId = data.sysConfigPayParam.jindongpayId
                      } else {
                        this.dataForm.jindongpayId = ''
                      }

                      if (data.sysConfigPayParam.uidId != null) {
                        this.dataForm.uidId = data.sysConfigPayParam.uidId
                      } else {
                        this.dataForm.uidId = ''
                      }
                      if (data.sysConfigPayParam.secretId != null) {
                        this.dataForm.secretId = data.sysConfigPayParam.secretId
                      } else {
                        this.dataForm.secretId = ''
                      }
                      if (data.sysConfigPayParam.publicKeyId != null) {
                        this.dataForm.publicKeyId = data.sysConfigPayParam.publicKeyId
                      } else {
                        this.dataForm.publicKeyId = ''
                      }
                      if (data.sysConfigPayParam.privateKeyId != null) {
                        this.dataForm.privateKeyId = data.sysConfigPayParam.privateKeyId
                      } else {
                        this.dataForm.privateKeyId = ''
                      }

                      if (data.sysConfigPayParam.callbackUrlId != null) {
                        this.dataForm.callbackUrlId = data.sysConfigPayParam.callbackUrlId
                      } else {
                        this.dataForm.callbackUrlId = ''
                      }
                      if (data.sysConfigPayParam.payUrlId != null) {
                        this.dataForm.payUrlId = data.sysConfigPayParam.payUrlId
                      } else {
                        this.dataForm.payUrlId = ''
                      }
                      /*console.log("alipayId:" + this.dataForm.alipayId + "--weixinId:" + this.dataForm.weixinId + "--unionpayId:" +
                                          this.dataForm.unionpayId + "--quickpayId:" + this.dataForm.quickpayId + "--qqpayId:" +
                                          this.dataForm.qqpayId + "--jindongpayId:" + this.dataForm.jindongpayId);
                                      console.log("uidId:" + this.dataForm.uidId + "--secretId:" + this.dataForm.secretId + "--publicKeyId:" +
                                          this.dataForm.publicKeyId + "--privateKeyId:" + this.dataForm.privateKeyId);
                                      console.log("callbackUrlId:" + this.dataForm.callbackUrlId + "--payUrlId:" + this.dataForm.payUrlId);*/
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
          }
        })
      },
      getWebIconUrl () {
        this.$emit('getUrl', this.dataForm.webIconId)
      },
      getUrl () {
        this.$emit('getUrl', this.dataForm.enclosureId)
      },
      getAppIconUrl () {
        this.$emit('getUrl', this.dataForm.appIconId)
      },

      UploadUrl: function () {
        this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
        return this.url
      },

      // 照片上传之前
      beforeUploadHandle (file) {
        this.num++
      },
      // 照片上传成功
      successHandle (response, file, fileList, type) {
        this.type = type
        this.fileList3 = fileList
        this.successNum++
        if (response && response.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
          this.dataForm.enclosureId = response.id
          this.dataForm.enclosureMd5 = response.Md5Val
          this.show = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 照片上传成功
      iconSuccessHandle (response, file, fileList, type) {
        this.type = type
        this.fileList = fileList
        this.successNum++
        if (response && response.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
          this.dataForm.appIconId = response.id
          this.dataForm.appIconMd5 = response.Md5Val
          this.appIconShow = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 照片上传成功
      webIconSuccessHandle (response, file, fileList, type) {
        this.type = type
        this.fileList2 = fileList
        this.successNum++
        if (response && response.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
          this.dataForm.webIconId = response.id
          this.dataForm.webIconMd5 = response.Md5Val
          this.webIconShow = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit () {
        var payName = ''
        if (this.dataForm.paymentMethod.length > 0) {
          this.dataForm.paymentMethod.forEach((payid, i) => {
            this.options.forEach((option, j) => {
              if (option.id == payid) {
                if (i == this.dataForm.paymentMethod.length - 1) {
                  payName += option.name
                } else {
                  payName += option.name + ','
                }
              }
            })
          })

        }
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/payconfig/payconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({

                'payconfig': {
                  'id': this.dataForm.id || undefined,
                  'payName': this.dataForm.payName,
                  'name': this.dataForm.name,
                  'paymentMethod': this.dataForm.paymentMethod.join(','),
                  'enable': this.dataForm.enable,
                  'firstPush': this.dataForm.firstPush,
                  'paymentMethodName': payName,
                  'appIconId': this.dataForm.appIconId,
                  'appIconMd5': this.dataForm.appIconMd5,
                  'webIconId': this.dataForm.webIconId,
                  'webIconMd5': this.dataForm.webIconMd5,
                  'enclosureId': this.dataForm.enclosureId,
                  'enclosureMd5': this.dataForm.enclosureMd5,
                  'aliasName': this.dataForm.aliasName,
                  'callbackIp':this.dataForm.callbackIp,
                  'orderNum': this.dataForm.orderNum
                },
                'sysConfigPayParam': {
                  'name': this.dataForm.payName,//公司名称
                  'aliasName': this.dataForm.aliasName,//别名
                  'alipay': this.dataForm.alipay,
                  'weixin': this.dataForm.weixin,
                  'unionpay': this.dataForm.unionpay,
                  'quickpay': this.dataForm.quickpay,
                  'qqpay': this.dataForm.qqpay,
                  'jindongpay': this.dataForm.jindongpay,
                  'uid': this.dataForm.uid,
                  'secret': this.dataForm.secret,
                  'publicKey': this.dataForm.publicKey,
                  'privateKey': this.dataForm.privateKey,
                  'callbackUrl': this.dataForm.callbackUrl,
                  'payUrl': this.dataForm.payUrl,
                  'alipayId': this.dataForm.alipayId,
                  'weixinId': this.dataForm.weixinId,
                  'unionpayId': this.dataForm.unionpayId,
                  'quickpayId': this.dataForm.quickpayId,
                  'qqpayId': this.dataForm.qqpayId,
                  'jindongpayId': this.dataForm.jindongpayId,
                  'uidId': this.dataForm.uidId,
                  'secretId': this.dataForm.secretId,
                  'publicKeyId': this.dataForm.publicKeyId,
                  'privateKeyId': this.dataForm.privateKeyId,
                  'callbackUrlId': this.dataForm.callbackUrlId,
                  'payUrlId': this.dataForm.payUrlId
                }
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
