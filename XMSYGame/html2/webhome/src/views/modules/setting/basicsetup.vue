<template>
  <div class="mod-config">
    <el-form style="width: 80%" :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>logo</span>
        </div>
        <div class="text item">
          <div style="width: 50%;text-align: center;float: left">
            <el-form-item label-width="30%" label="web端logo: ">
            </el-form-item>

            <div>
              <el-form-item label-width="0" >
                <el-tooltip class="item" effect="dark" content="上传web端logo" placement="top">
                  <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle"
                             multiple
                             :file-list="fileList" :data="form" style="text-align: center;">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
                    <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
                  </el-upload>
                </el-tooltip>

              </el-form-item>

              <div>
                <el-form-item label-width="30%" label="web端logo地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.webUrl"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label-width="30%" label="跳转链接地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.webLink"></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
          <div style="width: 50%;text-align: center;float: right">
            <el-form-item label-width="30%" label=" 移动端logo : ">

            </el-form-item>
            <div>
              <el-form-item label-width="0" >
                <el-tooltip class="item" effect="dark" content="上传移动端logo" placement="top">
                  <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle"
                             multiple
                             :file-list="fileList2" :data="form" style="text-align: center;">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
                    <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
                  </el-upload>
                </el-tooltip>

              </el-form-item>
              <div>
                <el-form-item label-width="30%" label="移动端logo地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.mobileUrl"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label-width="30%" label="跳转链接地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.mobileLink"></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
        </div>
      </el-card>
      <el-card style="text-align: left">
        <div slot="header" class="clearfix">
          <span>站点设置</span>
        </div>
        <div class="text item">
          <div style="width: 50%;text-align: center;float: left">
            <el-form-item label-width="30%" label=" web端域名设置 : " >

            </el-form-item>
            <div>
              <div>
                <el-form-item label-width="30%" label="站点名称: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.webSiteName"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label-width="30%" label="主站域名: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.webDomainName"></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
          <div style="width: 50%;text-align: center;float: right">
            <el-form-item label-width="30%" label=" 移动端域名设置 : " >

            </el-form-item>
            <div>
              <div>
                <el-form-item label-width="30%" label="站点名称: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.mobileSiteName"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label-width="30%" label="主站域名: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.mobileDomainName"></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
        </div>
      </el-card>
      <el-card style="text-align: left">
        <div slot="header" class="clearfix">
          <span>二维码及地址设定</span>
        </div>
        <div class="text item">
          <div style="width: 50%;text-align: center;float: left">
            <el-form-item label-width="30%" label="IOS下载二维码: " >
            </el-form-item>
            <div>
              <el-form-item label-width="0" >
                <el-tooltip class="item" effect="dark" content="上传IOS下载二维码" placement="top">
                  <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle"
                             multiple
                             :file-list="fileList3" :data="form" style="text-align: center;">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
                    <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
                  </el-upload>
                </el-tooltip>

              </el-form-item>
              <div>
                <el-form-item label-width="30%" label="IOS下载二维码地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.iosQrcodeUrl"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label-width="30%" label="IOS下载地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.iosDownloadUrl"></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
          <div style="width: 50%;text-align: center;float: right">
            <el-form-item label-width="30%" label=" 安卓下载二维码 : " >
            </el-form-item>
            <div>
              <el-form-item label-width="0" >
                <el-tooltip class="item" effect="dark" content="上传安卓下载二维码" placement="top">
                  <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle"
                             multiple
                             :file-list="fileList4" :data="form" style="text-align: center;">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
                    <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
                  </el-upload>
                </el-tooltip>

              </el-form-item>
              <div>
                <el-form-item label-width="30%" label="安卓下载二维码地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.androidQrcodeUrl"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label-width="30%" label="安卓下载地址: " prop="num">
                  <el-input style="width: 70%;float: left" v-model="dataForm.androidDownloadUrl"></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
        </div>
      </el-card>
      <el-card style="text-align: center">
        <el-button type="primary" @click="dataFormSubmit()">提交保存</el-button>
      </el-card>
    </el-form>
  </div>
</template>

<script>
  export default {
    data: function () {
      return {
        fileList: [],
        fileList2: [],
        fileList3: [],
        fileList4: [],
        visible: false,
        rate: 100,
        form: null,
        dataForm: {
          id: '',
          webUrl: '',
          mobileUrl: '',
          webLink: '',
          mobileLink: '',
          webSiteName: '',
          mobileSiteName: '',
          webDomainName: '',
          mobileDomainName: '',
          iosQrcodeUrl: '',
          androidQrcodeUrl: '',
          iosDownloadUrl: '',
          androidDownloadU: '',
          deleteStatus: '',
          createTime: '',
          updateTime: '',
          version: ''
        },
        dataRule: {
          androidDownloadUrl: [
            { required: true, message: '安卓下载地址不能为空', trigger: 'blur' }
          ],
          iosDownloadUrl: [
            { required: true, message: 'IOS下载地址不能为空', trigger: 'blur' }
          ],
          androidQrcodeUrl: [
            { required: true, message: '安卓二维码下载地址不能为空', trigger: 'blur' }
          ],
          iosQrcodeUrl: [
            { required: true, message: 'IOS二维码下载地址不能为空', trigger: 'blur' }
          ],
          mobileDomainName: [
            { required: true, message: '移动端主站域名不能为空', trigger: 'blur' }
          ],
          webDomainName: [
            { required: true, message: 'web端主站域名不能为空', trigger: 'blur' }
          ],
          mobileSiteName: [
            { required: true, message: '移动端站点名称不能为空', trigger: 'blur' }
          ],
          webSiteName: [
            { required: true, message: 'web端站点名称不能为空', trigger: 'blur' }
          ],
          mobileLink: [
            { required: true, message: '移动端跳转链接地址不能为空', trigger: 'blur' }
          ],
          webLink: [
            { required: true, message: 'web端跳转链接地址不能为空', trigger: 'blur' }
          ],
          mobileUrl: [
            { required: true, message: '移动端logo地址不能为空', trigger: 'blur' }
          ],
          webUrl: [
            { required: true, message: 'web端logo地址不能为空', trigger: 'blur' }
          ]

        }
      }
    },
    methods: {
      init: function () {
        this.$nextTick(() => {
          this.getDataList()
        })
      },
      getDataList () {
      },
      UploadUrl: function () {
        this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`);
        return this.url
      },
      // 照片上传之前
      beforeUploadHandle (file) {
        this.num++
      },
      // 照片上传成功
      successHandle (response, file, fileList, type) {
        this.type = type;
        this.fileList = fileList;
        this.successNum++;
        if (response && response.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
          }
          this.dataForm.enclosureId = response.id;
          this.show = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit: function () {
      }
    }
  }
</script>
