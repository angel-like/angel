<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-tooltip class="item" effect="dark" content="弹窗配置名称" placement="top-start">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="图片id" v-if="show">
        <el-input v-model="dataForm.enclosureId" placeholder="图片id" style="width: 200px;" readOnly></el-input>
        <el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
      </el-form-item>
      <el-tooltip class="item" effect="dark" content="图片已加密" placement="top-start">
        <el-form-item label="MD5" v-if="show">
          <el-input v-model="dataForm.md5" placeholder="MD5" readOnly></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="选择跳转类型" placement="top-start">
        <el-form-item label="跳转类型" prop="type">
          <el-select v-model="dataForm.type" placeholder="请选择跳转类型">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="点击图片,跳转路径" placement="top-start" v-if="urlShow">
        <el-form-item label="路径" prop="url">
          <el-input v-model="dataForm.url" placeholder="路径"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-form-item label="游戏" v-if="gameShow">
        <el-select v-model="dataForm.game" placeholder="请选择游戏">
          <el-option v-for="item in gameOptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="功能" v-if="functionShow">
        <el-select v-model="dataForm.transferFunction" placeholder="请选择功能">
          <el-option v-for="item in transferFunctionOptions" :key="item.code" :label="item.name" :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-tooltip class="item" effect="dark" content="弹窗图片" placement="top">
      <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
                 :file-list="fileList" :data="form" style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>
    <!-- <div style="color: red;">上传的图片大小为：965*554</div> -->
		<div style="color: red;">{{pictureSize}}</div>
    <span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">确定</el-button>
		</span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
				pictureSize:'',
        gameShow: false,
        urlShow: false,
        functionShow: false,
        gameOptions: [],
        transferFunctionOptions: [],
        options: [{
          value: 1,
          label: '不调整'
        }, {
          value: 2,
          label: '指定页面地址'
        }, {
          value: 3,
          label: '跳游戏'
        }, {
          value: 4,
          label: 'app内指定功能'
        }],
        loadPicture: false,
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          enclosureId: '',
          type: '',
          url: '',
          md5: '',
          game: null,
          transferFunction: null
        },
        fileList: [],
        num: 0,
        show: false,
        form: null,
        successNum: 0,
        dataRule: {
          type: [{
            required: true,
            message: '类型不能为空',
            trigger: 'blur'
          }],
          name: [{
            required: true,
            message: '名称不能为空',
            trigger: 'blur'
          }],
          enclosureId: [{
            required: true,
            message: '图片不能为空',
            trigger: 'blur'
          }]

        }
      }
    },
    watch: {
      //监听
      dataForm: {
        handler(val, oldval) {
          if (val.type == 1) {
            this.urlShow = false
            this.gameShow = false
            this.functionShow = false
          } else if (val.type == 2) {
            this.urlShow = true
            this.gameShow = false
            this.functionShow = false
          } else if (val.type == 3) {
            this.urlShow = false
            this.gameShow = true
            this.functionShow = false
          } else if (val.type == 4) {
            this.urlShow = false
            this.gameShow = false
            this.functionShow = true
          } else {
            this.urlShow = false
            this.gameShow = false
            this.functionShow = false
          }
        },
        deep: true
      }
    },
    methods: {
      init(id) {
				//下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/1/39`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
        //为游戏下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.gameOptions = data.data
          }
        });
        //为功能下拉获取数据
        this.$http({
          url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/transferFunction`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.transferFunctionOptions = data.data
          }
        });
        this.show = false
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/appalertcofig/appalertcofig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({
                       data
                     }) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.appalertcofig.name
                this.dataForm.enclosureId = data.appalertcofig.enclosureId
                this.dataForm.md5 = data.appalertcofig.md5
                this.dataForm.type = data.appalertcofig.type
                if(this.dataForm.type==2){
                  this.gameShow=false
                  this.urlShow=true
                  this.functionShow=false
                  this.dataForm.url=data.appalertcofig.url
                }else if(this.dataForm.type==3){
                  this.gameShow=true
                  this.urlShow=false
                  this.functionShow=false
                  this.dataForm.game=parseInt(data.appalertcofig.url)
                }else if(this.dataForm.type==4){
                  this.gameShow=false
                  this.urlShow=false
                  this.functionShow=true
                  this.dataForm.transferFunction=data.appalertcofig.url
                }else{
                  this.gameShow=false
                  this.urlShow=false
                  this.functionShow=false
                }
              }
            })
          }
        })
      },
      getUrl() {
        this.$emit('getUrl', this.dataForm.enclosureId)
      },

      UploadUrl: function() {
        this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
        return this.url;
      },
      // 照片上传之前
      beforeUploadHandle(file) {
        this.loadPicture = true
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
          this.dataForm.md5 = response.Md5Val
          this.show = true
          this.loadPicture = false
        } else {
          this.loadPicture = false
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit() {
        var jumpUrl;
        if (this.dataForm.enclosureId == null || this.dataForm.enclosureId === '') {
          this.$message.error("图片附件不能为空")
          return
        }
        if (this.dataForm.type == 2) {
          console.log("this.dataForm.type==1");
          if (this.dataForm.url == null || this.dataForm.url === '') {
            this.$message.error("跳转路径不可为空")
            return
          } else {
            jumpUrl = this.dataForm.url
          }
        } else if (this.dataForm.type == 3) {
          if (this.dataForm.game == null || this.dataForm.game == '') {
            this.$message.error("游戏不可为空")
            return
          } else {
            jumpUrl = this.dataForm.game
          }
        } else if (this.dataForm.type == 4) {
          if (this.dataForm.transferFunction == null || this.dataForm.transferFunction == '') {
            this.$message.error("功能不可为空")
            return
          } else {
            jumpUrl = this.dataForm.transferFunction
          }
        }
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/appalertcofig/appalertcofig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'enclosureId': this.dataForm.enclosureId,
                'url': jumpUrl,
                'md5': this.dataForm.md5,
                'type': this.dataForm.type

              })
            }).then(({
                       data
                     }) => {
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
