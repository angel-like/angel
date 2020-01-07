<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <div style="margin: -64px 60px 40px 60px;">
      <el-tooltip class="item" effect="dark" content="图片管理" placement="top">
        <i class="el-icon-question" style="color:blue"></i>
      </el-tooltip>
    </div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-tooltip class="item" effect="dark" content="请选择图片类型,不同的图片类型在不同的页面展示" placement="top-start">
        <el-form-item label="图片类型" prop="type">
          <el-select v-model="dataForm.type" clearable placeholder="请选择图片类型">
            <el-option
              v-for="item in pictureType" :key="item.id" :label="item.type" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="是否展示" prop="enable">
        <el-radio-group v-model="dataForm.enable">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-tooltip class="item" effect="dark" content="点击图片,跳转路径" placement="top-start">
        <el-form-item label="跳转路径" prop="num">
          <el-input v-model="dataForm.url" placeholder="跳转路径"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="排序号" prop="num">
        <el-input-number v-model="dataForm.num" :min="1" label="排序号"></el-input-number>
      </el-form-item>

      <el-form-item label="附件ID" v-if="show">
        <el-input v-model="dataForm.enclosureId" placeholder="附件ID" style="width: 200px;" readOnly></el-input>
        <el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
      </el-form-item>

    </el-form>
    <el-tooltip class="item" effect="dark" content="对应类型展示的图片" placement="top">
      <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
                 :file-list="fileList" :data="form" style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>
    <!-- <div style="color: red;">上传的图片尺寸为： 1280*400</div> -->
    <div style="color: red;">{{pictureSize}}</div>
    <span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        pictureType: [{
          id: 'logo',
          type: 'logo图',
        }, {
          id: 'domain',
          type: '域名图片'
        }, {
          id: 'exhibition',
          type: '轮播图'
        }, {
          id: 'pay',
          type: '首页支付图'
        }, {
          id: 'couplets',
          type: '对联图'
        }, {
          id: 'gameRecord',
          type: '游戏记录轮播图'
        }, {
          id: 'transactionRecord',
          type: '交易记录轮播图'
        }, {
          id: 'recharge',
          type: '充值轮播图'
        }, {
          id: 'promotions',
          type: '优惠活动图'
        }, {
          id: 'cancel',
          type: '取消按钮'
        }, {
          id: 'bottom',
          type: '底部图片'
        },
          {
            id: 'AppDownload',
            type: 'APP下载图片'
          },
          {
            id: 'appTutorial',
            type: 'APP教程'
          }],
        pictureSize: '',
        visible: false,
        fileList: [],
        num: 0,
        show: false,
        form: null,
        successNum: 0,
        dataForm: {
          id: 0,
          enable: '1',
          enclosureId: '',
          type: '',
          num: '',
          url: ''
        },
        dataRule: {
          type: [{
            required: true,
            message: '图片类型不能为空',
            trigger: 'blur'
          }],
          num: [{
            required: true,
            message: '排序号不能为空',
            trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      init(id) {
        //下拉获取图片尺寸
        this.$http({
          url: this.$http.adornUrl(`/picturesize/picturesize/select/0/15`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.pictureSize = data.pictureSize
          }
        });
        this.show = false
        this.dataForm.enclosureId = ''
        this.visible = true
        this.fileList = []
        this.$nextTick(() => {
          this.dataForm.id = id || 0
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomeimage/webhomeimage/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({
                       data
                     }) => {
              if (data && data.code === 200) {
                this.dataForm.type = data.imageManagement.type
                this.dataForm.num = data.imageManagement.num
                this.dataForm.enclosureId = data.imageManagement.enclosureId
                this.dataForm.url = data.imageManagement.url
                if (data.imageManagement.enclosureId != null && data.imageManagement.enclosureId != 0) {
                  this.show = true
                }
                if (data.imageManagement.enable) {
                  this.dataForm.enable = "1"
                } else {
                  this.dataForm.enable = "0"
                }
              }
            })
          }
        })
      },


      getUrl() {
        this.$emit('getUrl', this.dataForm.enclosureId)
      },

      UploadUrl: function () {
        this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
        return this.url;
      },
      // 照片上传之前
      beforeUploadHandle(file) {

        this.num++
      },
      // 照片上传成功
      successHandle(response, file, fileList, type) {
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
          this.dataForm.enclosureId = response.id
          this.show = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomeimage/webhomeimage/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'type': this.dataForm.type,
                'enclosureId': this.dataForm.enclosureId,
                'enable': this.dataForm.enable,
                'url': this.dataForm.url,
                'num': this.dataForm.num,
              })
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.fileList = []
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

