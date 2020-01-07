<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
		<div style="margin: -64px 60px 40px 60px;">
					<el-tooltip class="item" effect="dark" content="在网站头部添加修改内容" placement="top">
						<i class="el-icon-question" style="color:blue" ></i>
					</el-tooltip>
		</div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="请选择导航栏类型" placement="top-start">
		<el-form-item label="导航类型" prop="type">
			<el-select v-model="dataForm.type" clearable placeholder="请导航类型">
				<el-option
					v-for="item in options"
					:key="item.name"
					:label="item.label"
					:value="item.name">
				</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="点击图片,跳转路径" placement="top-start">
    <el-form-item label="url" prop="url">
      <el-input v-model="dataForm.url" placeholder="url"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="文本编辑器,与word文档操作一样" placement="top-start">
		<el-form-item label="内容" prop="content">
        <div class="edit_container">
					<quill-editor 
							v-model="dataForm.content" 
							ref="myQuillEditor" 
							:options="editorOption" 
							@blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
							@change="onEditorChange($event)">
					</quill-editor>
			  </div>
     </el-form-item>
		 </el-tooltip>
    <el-form-item label="附件ID" prop="enclosureId" v-if="show">
      <el-input v-model="dataForm.enclosureId" placeholder="附件ID"  style="width: 200px;" readOnly></el-input>
			<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
    </el-form-item>
    </el-form>
		<el-tooltip class="item" effect="dark" content="网站图片" placement="top">
		<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
		:file-list="fileList" :data="form" style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<div style="color: red;">{{pictureSize}}</div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { quillEditor } from "vue-quill-editor"; //调用编辑器
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
  export default {
    data () {
      return {
        str: '',
				pictureSize: '',
        editorOption: {},
        visible: false,
				fileList: [],
				num: 0,
				form:null,
				show:false,
				options: [{
					name: 'KJZF',
					label: '快捷支付'
				}, {
					name: 'DLZC',
					label: '代理注册'
				}, {
					name: 'APP',
					label: 'APP下载'
				}, {
					name: 'HDDT',
					label: '活动大厅'
					}],
				successNum: 0,
        dataForm: {
          id: 0,
					type:'',
          url: '',
          content: '',
          enclosureId: ''					
        },
        dataRule: {
          url: [
            { required: true, message: 'url不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          enclosureId: [
            { required: true, message: '附件ID不能为空', trigger: 'blur' }
          ]
        }
      }
    },
		 computed: {
       editor() {
            return this.$refs.myQuillEditor.quill;
        }
    },
		mounted() {
        let content = '';  // 请求后台返回的内容字符串
        this.str = this.escapeStringHTML(content);
    },
		components: {
			quillEditor
		},
		
    methods: {
			onEditorReady(editor) { // 准备编辑器
 
        },
        onEditorBlur(){}, // 失去焦点事件
        onEditorFocus(){}, // 获得焦点事件
        onEditorChange(){}, // 内容改变事件
        // 转码
        escapeStringHTML(str) {
            str = str.replace(/</g,'<');
            str = str.replace(/>/g,'>');
            return str;
        },
      init (id) {
				 //下拉获取图片尺寸
				this.$http({
					url: this.$http.adornUrl(`/picturesize/picturesize/select/0/19`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.pictureSize = data.pictureSize
					}
				});
				this.show=false
				this.dataForm.enclosureId=''
				this.visible = true
				this.fileList = []
        this.dataForm.id = id || 0
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/webhomehead/webhomehead/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.url = data.webhomehead.url
                this.dataForm.content = data.webhomehead.content
                this.dataForm.enclosureId = data.webhomehead.enclosureId
								this.dataForm.type = data.webhomehead.type
								if(data.webhomehead.enclosureId!=null){
									this.show=true
								}
              }
            })
          }
        })
      },

				getUrl(){
					this.$emit('getUrl',this.dataForm.enclosureId)
				},
			
			
			UploadUrl:function(){
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;     
			},  
				// 照片上传之前
				beforeUploadHandle (file) {
					
					this.num++
				},
				// 照片上传成功
				successHandle (response, file, fileList,type) {
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
						this.dataForm.enclosureId=response.id
						this.show=true
					} else {
						this.$message.error(response.msg)
					}
				},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/webhomehead/webhomehead/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,

          'url': this.dataForm.url ,

          'content': this.dataForm.content ,

          'enclosureId': this.dataForm.enclosureId ,
					'type': this.dataForm.type 

              })
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
										this.fileList=[]
                    this.$emit('refreshDataList');
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
