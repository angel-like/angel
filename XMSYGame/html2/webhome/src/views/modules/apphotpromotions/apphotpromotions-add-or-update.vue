<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <!--<el-tooltip class="item" effect="dark" content="该活动所展示的菜单类型" placement="top-start">
        <el-form-item label="菜单类型" prop="type">
          <el-select v-model="dataForm.type" placeholder="菜单类型" clearable>
            <el-option
              v-for="item in typeOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>-->
      <el-tooltip class="item" effect="dark" content="分类" placement="top-start">
        <el-form-item label="分类" prop="classify">
          <el-select v-model="dataForm.classify" placeholder="分类" clearable>
            <el-option
              v-for="item in classifyOptions"
              :key="item.name"
              :label="item.label"
              :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <!-- <el-form-item label="代码" prop="content">
              <el-input  type="textarea" v-model="dataForm.content" placeholder="代码"></el-input>
          </el-form-item> -->
      <!-- <el-form-item label="备用字段" prop="remake">
         <el-input v-model="dataForm.remake" placeholder="备用字段"></el-input>
       </el-form-item>-->
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <!-- <el-form-item label="链接" prop="content">
         <el-input v-model="dataForm.content" placeholder="链接"></el-input>
       </el-form-item>-->
      <el-form-item label="排序号" prop="orderNum">
        <el-input-number v-model="dataForm.orderNum" :min="0" label="排序号"></el-input-number>
      </el-form-item>
      <el-form-item label="图片" prop="content" v-if="show">
        <el-input v-model="dataForm.content" placeholder="内容图片" style="width: 200px;" readOnly></el-input>
        <el-button size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
      </el-form-item>
      <!-- <el-form-item label="内容图片" prop="content" v-if="contentShow">
         <el-input v-model="dataForm.content" placeholder="内容图片" style="width: 200px;" readOnly></el-input>
         <el-button size="mini" type="primary" title="查看" @click="getContentUrl()">预览图片</el-button>
       </el-form-item>-->
    </el-form>
    <el-tooltip class="item" effect="dark" content="活动内容图片" placement="top">
      <el-upload
        drag
        :action="UploadUrl()"
        :before-upload="beforeUploadHandle"
        :on-success="successHandle"
        multiple
        :file-list="fileList"
        :data="form"
        style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上内容图片</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>
    <!-- <el-tooltip class="item" effect="dark" content="活动内容图片" placement="top">
       <el-upload
         drag
         :action="UploadUrl()"
         :before-upload="beforeUploadHandle"
         :on-success="contentSuccessHandle"
         multiple
         :file-list="fileList2"
         :data="form"
         style="text-align: center;">
         <i class="el-icon-upload"></i>
         <div class="el-upload__text">将文件拖到此处，或<em>点击上内容图片</em></div>
         <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
       </el-upload>
     </el-tooltip>-->
    <div style="color: red;">{{pictureSize}}</div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  // 	import { quillEditor } from "vue-quill-editor"; //调用编辑器
  // 	import 'quill/dist/quill.core.css';
  // 	import 'quill/dist/quill.snow.css';
  // 	import 'quill/dist/quill.bubble.css';
  export default {
    data() {
      return {
        classifyOptions: [{

          name: 1,
          label: '热门活动'
        }, {
          name: 2,
          label: '活动公告'
        }],

        pictureSize: '',
        visible: false,
        str: '',
        typeOptions: [],
        editorOption: {},
        show: false,
        contentShow: false,
        form: null,
        fileList: [],
        fileList2: [],
        num: 0,
        successNum: 0,
        loadPicture: false,
        dataForm: {
          id: 0,
         /* type: '',*/
          content: '',
          name: '',
          orderNum: '',
          classify: ''
        },
        dataRule: {
         /* type: [
            {required: true, message: '属性ID不能为空', trigger: 'blur'}
          ],*/
          classify: [
            {required: true, message: '分类不能为空', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ],
          content: [
            {required: true, message: '内容不能为空', trigger: 'blur'}
          ],
          orderNum: [
            {required: true, message: '排序号不能为空', trigger: 'blur'}
          ]

        }
      }
    },
    methods: {
      getUrl() {
        this.$emit('getUrl', this.dataForm.content)
      },
     /* getContentUrl() {
        this.$emit('getUrl', this.dataForm.content)
      },*/

      init(id) {
        //下拉获取图片尺寸
        this.$http({
          url: this.$http.adornUrl(`/picturesize/picturesize/select/1/43`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.pictureSize = data.pictureSize
          }
        });
        //获取下拉
        this.$http({
          url: this.$http.adornUrl(`/apppromotionstype/apppromotionstype/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.typeOptions = data.list
          }
        });
        this.fileList = [];
        this.fileList2 = [];
        this.show = false
        /*this.contentShow = false*/
        this.dataForm.content = ''
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/apphotpromotions/apphotpromotions/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.classify = data.apphotpromotions.classify
                /*this.dataForm.type = data.apphotpromotions.type*/
                //this.dataForm.enclosureId = data.apppromotions.enclosureId
                this.dataForm.content = data.apphotpromotions.content
                this.dataForm.name = data.apphotpromotions.name
                /*if (data.apppromotions.enclosureId != null && data.apppromotions.enclosureId != 0) {
                  this.show = true
                }*/
                if (data.apphotpromotions.content != null && data.apphotpromotions.content != 0 && data.apphotpromotions.content != '0') {
                  this.contentShow = true
                }
                this.dataForm.orderNum = data.apphotpromotions.orderNum
              }
            })
          }
        })
      },
      UploadUrl: function () {
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
          this.dataForm.content = response.url
          this.show = true
          this.loadPicture = false
        } else {
          this.$message.error(response.msg)
          this.loadPicture = false
        }
      },
     /* // 照片上传成功
      contentSuccessHandle(response, file, fileList, type) {
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
          this.dataForm.content = response.url
          this.contentShow = true
          this.loadPicture = false
        } else {
          this.$message.error(response.msg)
          this.loadPicture = false
        }
      },*/
      // 表单提交
      dataFormSubmit() {
        var remake = this.dataForm.remake;
        if ("" == remake) {
          remake = " ";
        }
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/apphotpromotions/apphotpromotions/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
               /* 'type': this.dataForm.type,*/
                /*'enclosureId': this.dataForm.enclosureId,*/
                'content': this.dataForm.content,
                /* 'remake': remake,*/
                'name': this.dataForm.name,
                /*  'sorts': this.dataForm.sorts,*/
                'orderNum': this.dataForm.orderNum,
                'classify': this.dataForm.classify
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
