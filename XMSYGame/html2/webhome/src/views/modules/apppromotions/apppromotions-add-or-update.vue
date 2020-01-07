<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="该活动所展示的菜单类型" placement="top-start">
    <el-form-item label="菜单类型" prop="typeId">
			<el-select v-model="dataForm.typeId" placeholder="菜单类型" clearable>
				<el-option
					v-for="item in typeOptions"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
    </el-form-item>
	</el-tooltip>
		<!-- <el-form-item label="代码" prop="content">
			<el-input  type="textarea" v-model="dataForm.content" placeholder="代码"></el-input>
		</el-form-item> -->
	<el-form-item label="活动名称" prop="remake">
      <el-input v-model="dataForm.remake" placeholder="活动名称"></el-input>
    </el-form-item>
	<el-form-item label="排序号" prop="sorts">
	 	<el-input-number v-model="dataForm.sorts"  :min="1"  label="排序号"></el-input-number>
	</el-form-item>
	<el-form-item label="标题图片" prop="enclosureId" v-if="show">
		<el-input v-model="dataForm.enclosureId" placeholder="标题图片"  style="width: 200px;" readOnly></el-input>
		<el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
	</el-form-item>
	<el-form-item label="内容图片" prop="content" v-if="contentShow">
		<el-input v-model="dataForm.content" placeholder="内容图片"  style="width: 200px;" readOnly></el-input>
		<el-button  size="mini" type="primary" title="查看" @click="getContentUrl()">预览图片</el-button>
	</el-form-item>
    </el-form>
	<el-tooltip class="item" effect="dark" content="活动标题图片" placement="top">
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
			<div class="el-upload__text">将文件拖到此处，或<em>点击上标题图片</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="活动内容图片" placement="top">
		<el-upload
			drag
			:action="UploadUrl()"
			:before-upload="beforeUploadHandle"
			:on-success="contentSuccessHandle"
			multiple
			:file-list="fileList"
			:data="form"
			style="text-align: center;">
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上内容图片</em></div>
			<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
		</el-upload>
		</el-tooltip>
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
    data () {
      return {
		  pictureSize:'',
		  visible: false,
			str: '',
			typeOptions:[],
			editorOption: {},
			show:false,
			contentShow:false,
			form:null,
			fileList: [],
			num: 0,
			successNum: 0,
			loadPicture:false,
        dataForm: {
          id: 0,
          typeId: '',
          enclosureId: '',
          content: '',
          remake: '',
		  sorts: '',
        },
        dataRule: {
          typeId: [
            { required: true, message: '属性ID不能为空', trigger: 'blur' }
          ],
          enclosureId: [
            { required: true, message: '附件ID不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
		  sorts: [
		    { required: true, message: '排序号不能为空', trigger: 'blur' }
		  ],
		  remake: [
		    { required: true, message: '活动名称不能为空', trigger: 'blur' }
		  ]
         
        }
      }
    },
// 		computed: {
// 			editor() {
// 						return this.$refs.myQuillEditor.quill;
// 				}
// 		},
// 		mounted() {
// 				let content = '';  // 请求后台返回的内容字符串
// 				this.str = this.escapeStringHTML(content);
// 		},
// 		components: {
// 			quillEditor
// 		},
    methods: {
// 			
// 			onEditorReady(editor) { // 准备编辑器
// 			
// 			},
// 			onEditorBlur(){}, // 失去焦点事件
// 			onEditorFocus(){}, // 获得焦点事件
// 			onEditorChange(){}, // 内容改变事件
// 			// 转码
// 			escapeStringHTML(str) {
// 					str = str.replace(/</g,'<');
// 					str = str.replace(/>/g,'>');
// 					return str;
// 			},
			getUrl(){
				this.$emit('getUrl',this.dataForm.enclosureId)
			},
			getContentUrl(){
				this.$emit('getUrl',this.dataForm.content)
			},
			
      init (id) {
		  //下拉获取图片尺寸
		  this.$http({
		  	url: this.$http.adornUrl(`/picturesize/picturesize/select/1/38`),
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
				this.fileList=[];
				this.show=false
				this.contentShow=false
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/apppromotions/apppromotions/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.typeId = data.apppromotions.typeId
                this.dataForm.enclosureId = data.apppromotions.enclosureId
                this.dataForm.content = data.apppromotions.content
                this.dataForm.remake = data.apppromotions.remake.trim()
				if(data.apppromotions.enclosureId!=null&&data.apppromotions.enclosureId!=0){
					this.show=true
				}
				if(data.apppromotions.content!=null&&data.apppromotions.content!=0){
					this.contentShow=true
				}
				this.dataForm.sorts = data.webhomepopulargames.sorts
              }
            })
          }
        })
      },
			UploadUrl:function(){
				this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
				return this.url;     
			},  
				// 照片上传之前
				beforeUploadHandle (file) {
					this.loadPicture=true
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
						this.loadPicture=false
					} else {
						this.$message.error(response.msg)
						this.loadPicture=false
					}
				},
				// 照片上传成功
				contentSuccessHandle (response, file, fileList,type) {
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
						this.dataForm.content=response.id
						this.contentShow=true
						this.loadPicture=false
					} else {
						this.$message.error(response.msg)
						this.loadPicture=false
					}
				},
      // 表单提交
      dataFormSubmit () {
				var remake = this.dataForm.remake;
				if(""==remake ){
					remake = " ";
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/apppromotions/apppromotions/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
				'typeId': this.dataForm.typeId ,
				'enclosureId': this.dataForm.enclosureId ,
				'content': this.dataForm.content ,
				'remake': remake,
				'sorts': this.dataForm.sorts ,
						
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
