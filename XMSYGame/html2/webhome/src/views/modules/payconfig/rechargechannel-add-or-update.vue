<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="支付渠道的名称" placement="top-start">
				<el-form-item label="渠道名称" prop="name">
				  <el-input v-model="dataForm.name" placeholder="渠道名称"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="支付渠道其他的名字(不可更改)" placement="top-start">
      		<el-form-item label="渠道别名" prop="alias">
      			<el-input v-model="dataForm.alias" placeholder="渠道别名"></el-input>
      		</el-form-item>
      	</el-tooltip>
        <el-tooltip class="item" effect="dark" content="什么样的支付方式" placement="top-start">
        		<el-form-item label="类型" prop="type" v-if="dataForm.id">
        			<el-select  v-model="dataForm.type" disabled placeholder="类型">
        				<el-option
        					v-for="item in options"
        					:key="item.value"
        					:label="item.label"
        					:value="item.value">
        				</el-option>
        			</el-select>
        		</el-form-item>
        		<el-form-item label="类型" prop="type" v-else>
        			<el-select  v-model="dataForm.type" placeholder="类型">
        				<el-option
        					v-for="item in options"
        					:key="item.value"
        					:label="item.label"
        					:value="item.value">
        				</el-option>
        			</el-select>
        		</el-form-item>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="是否启用" placement="top-start">
          		<el-form-item label="状态"  prop="enable">
          			<el-radio-group v-model="dataForm.enable">
          				<el-radio :label="true">启用</el-radio>
          				<el-radio :label="false">禁用</el-radio>
          			</el-radio-group>
                </el-form-item>
        </el-tooltip>



 <!--   <el-form-item label="类型" prop="type">
    	<el-select v-model="dataForm.type" placeholder="请选择类型">
    		<el-option
    			v-for="item in options"
    			:key="item.value"
    			:label="item.label"
    			:value="item.value">
    		</el-option>
    	</el-select>
    </el-form-item> -->

<!--    <el-form-item label="最小金额" prop="minAmount">
      <el-input v-model="dataForm.minAmount" placeholder="最小金额"></el-input>
    </el-form-item> -->
    	<el-form-item label="app图标ID"  v-if="appIconShow">
    	  <el-input v-model="dataForm.appIconId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
    	  <el-button  size="mini" type="primary" title="查看" @click="getAppIconUrl()">预览APP图标</el-button>
    	</el-form-item>
    	<el-form-item label="appMD5"  v-if="appIconShow">
    	  <el-input v-model="dataForm.appIconMd5" placeholder="appMD5"></el-input>
    	</el-form-item>
    	  <el-form-item label="web端图标" prop="webIconId" v-if="webIconShow">
    	 <el-input v-model="dataForm.webIconId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
    	 <el-button  size="mini" type="primary" title="查看" @click="getWebIconUrl()">预览WEB图标</el-button>
    	</el-form-item>
    	<el-form-item label="webMD5" prop="webIconMd5" v-if="webIconShow">
    	  <el-input v-model="dataForm.webIconMd5" placeholder="webMD5"></el-input>
    	</el-form-item>
    	<el-form-item label="附件" prop="enclosureId" v-if="show">
    	  <el-input v-model="dataForm.enclosureId" placeholder="附件ID"   style="width: 200px;"  readOnly></el-input>
    	  <el-button  size="mini" type="primary" title="查看" @click="getUrl()">预览图片</el-button>
    	</el-form-item>
    	<el-form-item label="MD5" prop="md5" v-if="show">
    	  <el-input v-model="dataForm.md5" placeholder="MD5"></el-input>
    	</el-form-item>
    </el-form>
    <el-form-item label="排序号" prop="orderNo">
      <el-input-number v-model="dataForm.orderNo" controls-position="right" :min="0" label="排序号"></el-input-number>
    </el-form-item>
     <el-tooltip class="item" effect="dark" content="上传支付方式的app图标" placement="top-start">
    	<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="iconSuccessHandle" multiple
    	:file-list="fileList" :data="form" style="text-align: center;">
    		<i class="el-icon-upload"></i>
    		<div class="el-upload__text">将文件拖到此处，或<em>点击上传APP图标</em></div>
    		<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
    	</el-upload>
    </el-tooltip>

    <el-tooltip class="item" effect="dark" content="上传支付方式的web图标" placement="top-start">
     <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="webIconSuccessHandle" multiple
                :file-list="fileList2" :data="form" style="text-align: center;">
     	<i class="el-icon-upload"></i>
     	<div class="el-upload__text">将文件拖到此处，或<em>点击上传WEB图标</em></div>
     	<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
     </el-upload>
    </el-tooltip>

     <el-tooltip class="item" effect="dark" content="上传支付方式的图片" placement="top-start">
    	<el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
                 :file-list="fileList3" :data="form" style="text-align: center;">
    		<i class="el-icon-upload"></i>
    		<div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
    		<div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
    	</el-upload>
    </el-tooltip>
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
			var validateRate = (rule, value, callback) => {
				var res= /^[0-9]*[1-9][0-9]*$/;
					if(!res.test(value)){
						callback(new Error('请输入正整数'));
					}
					callback();

			};
      return {
				fileList: [],
        fileList2: [],
        fileList3: [],
				num: 0,
				show: false,
				appIconShow:false,
				webIconShow:false,
				form:null,
				successNum: 0,
				options: [{
					value: 12,
					label: '支付宝充值',
				}, {
					value: 13,
					label: '微信充值'
				}, {
					value: 29,
					label: 'QQ充值'
				}, {
					value: 30,
					label: '京东充值'
				}, {
					value: 31,
					label: '快捷支付'
				}, {
					value: 32,
					label: '银联支付'
				}, {
					value: 1001,
					label: '银联云闪付'
				}],
        visible: false,
        dataForm: {
          id: 0,
          name: '',
					alias: '',
          type: '',
          minAmount: '',
          enable: true,
          appIconId: '',
          appIconMd5: '',
          webIconId: '',
          webIconMd5: '',
          enclosureId: '',
          md5: '',
          orderNo:0
        },
        dataRule: {
          name: [
            { required: true, message: '渠道名称不能为空', trigger: 'blur' }
          ],
					alias: [
						{ required: true, message: '渠道别名不能为空', trigger: 'blur' }
					],
          type: [
            { required: true, message: '导航类型不能为空', trigger: 'blur' }
          ],
          minAmount: [
          	{ validator: validateRate, trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          appIconId: [
            { required: true, message: 'app图标ID不能为空', trigger: 'blur' }
          ],
          webIconId: [
            { required: true, message: 'web端图标不能为空', trigger: 'blur' }
          ],
          orderNo: [{
            required: true,
            message: '排序不能为空',
            trigger: 'blur'
          }],
        }
      }
    },
    methods: {
      init (id) {
				this.show=false
				this.appIconShow=false
				this.webIconShow=false
				this.fileList = []
        this.fileList2 = []
        this.fileList3 = []
				this.dataForm.appIconId = ''
				this.dataForm.appIconMd5 = ''
				this.dataForm.webIconId = ''
				this.dataForm.webIconMd5 = ''
				this.dataForm.enclosureId = ''
				this.dataForm.md5 = ''
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/rechargechannel/rechargechannel/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.rechargechannel.name
                this.dataForm.type = data.rechargechannel.type
                this.dataForm.minAmount = data.rechargechannel.minAmount
                this.dataForm.enable = data.rechargechannel.enable
                this.dataForm.appIconId = data.rechargechannel.appIconId
                this.dataForm.appIconMd5 = data.rechargechannel.appIconMd5
                this.dataForm.webIconId = data.rechargechannel.webIconId
                this.dataForm.webIconMd5 = data.rechargechannel.webIconMd5
                this.dataForm.enclosureId = data.rechargechannel.enclosureId
                this.dataForm.md5 = data.rechargechannel.md5
                this.dataForm.orderNo = data.rechargechannel.orderNo
								this.dataForm.alias = data.rechargechannel.alias
								if(data.rechargechannel.appIconId!=null){
									this.appIconShow=true
								}
								if(data.rechargechannel.webIconId!=null){
									this.webIconShow=true
								}
								if(data.rechargechannel.enclosureId!=null){
									this.show=true
								}
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
              url: this.$http.adornUrl(`/rechargechannel/rechargechannel/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'name': this.dataForm.name ,

          'type': this.dataForm.type ,

          'minAmount': this.dataForm.minAmount ,

          'enable': this.dataForm.enable ,

          'appIconId': this.dataForm.appIconId ,

          'appIconMd5': this.dataForm.appIconMd5 ,

          'webIconId': this.dataForm.webIconId ,

          'webIconMd5': this.dataForm.webIconMd5 ,

          'enclosureId': this.dataForm.enclosureId ,
          'alias': this.dataForm.alias ,
          'md5': this.dataForm.md5 ,
                'orderNo': this.dataForm.orderNo,

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
      },
			getWebIconUrl(){
							this.$emit('getUrl',this.dataForm.webIconId)
			},
			getUrl(){
							this.$emit('getUrl',this.dataForm.enclosureId)
			},
			getAppIconUrl(){
							this.$emit('getUrl',this.dataForm.appIconId)
			},

			UploadUrl: function() {
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
					this.dataForm.md5 = response.Md5Val
					this.show=true
				} else {
					this.$message.error(response.msg)
				}
			},
			// 照片上传成功
			iconSuccessHandle(response, file, fileList, type) {
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
					this.appIconShow=true
				} else {
					this.$message.error(response.msg)
				}
			},
			// 照片上传成功
			webIconSuccessHandle(response, file, fileList, type) {
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
					this.webIconShow=true
				} else {
					this.$message.error(response.msg)
				}
			}
    }
  }
</script>
